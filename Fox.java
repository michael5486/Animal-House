import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public class Fox implements Organism{
	
	// Constants (specific to this animal type)
	static final String type = "Fox";
	static final ArrayList<String> preyTypes = new ArrayList<String>(Arrays.asList("Mouse", "Rabbit"));
	static final ArrayList<String> predatorTypes = new ArrayList<String>(Arrays.asList("Wolf", "Bear"));
	static final double maxHealth = 12.0;
	static final double hungerHealth = 6;
	static final double healthLostPerGameTick = 0.03;
	static final double healthGainedEatingPerGameTick = 0.5;
	static final int maxSpeed = 8;     // pixels
	static final int sightRadius = 60; // pixels
	static final int eatingRadius = 10; // pixels
	static final double probabilityGivingBirth = 0.00008 / 2;
	static final int avgNumBabies = 4;

	// Variables (to be set and changed)
	int id;
	int X, Y;
	int prevX, prevY;
	Dimension D;
	int speed = maxSpeed;
	double health;
	Point2D.Double targetLocation;
	Organism prey;
	Organism predator;
	
	/* 	Organisms can be in various states represented by an integer
		0. idle
		1. eating
		2. beingEaten
		3. hunting
		4. escaping */
	int state = 0;



	// Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Fox(int id, Point2D.Double randomPoint, Dimension D){
		// Create a mouse at a location X,Y
		this.id = id;
		this.X = (int)randomPoint.x;
		this.Y = (int)randomPoint.y;
		this.prevX = this.X;
		this.prevY = this.Y;
		this.D = D;

		// set initial health
		this.health = generateRandomInitialHealth();
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~ Control Methods: To be called by AnimalSimulator ~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	// Step 1: Update Health
	public void updateHealthTime(){
		// lose health due to time
		this.health = this.health - this.healthLostPerGameTick;
	}

	// Step 2: Update State
	public void updateState(ArrayList<Organism> organisms) {
		/* 	
		Organisms can be in various states represented by an integer
			0. idle
			1. eating
			2. beingEaten
			3. hunting
			4. escaping

		If the organism is eating or being eaten, their health 
		will be updated in this method to reflect that.

		*/
		ArrayList<Organism> nearbyOrganisms = getOrganismsWithinSightRadius(organisms);
		ArrayList<Organism> nearbyPrey = getNearbyPrey(nearbyOrganisms);
		ArrayList<Organism> nearbyPredators = getNearbyPredators(nearbyOrganisms);

		if(!nearbyPredators.isEmpty()){
			predator = getClosestPredator(nearbyPredators);
			if(getXY().distance(predator.getXY()) <= eatingRadius){
				// being eaten
				state = 2;
			}
			else{
				// escaping
				state = 4;
			}
		}
		else if(state == 1){ // If organism is already eating, continue eating
			eatPrey();
		}
		else if(health <= hungerHealth){ // Else if organism is below hungerHealth and there exists prey within sight radius
			if(!nearbyPrey.isEmpty()){
				prey = getClosestPrey(nearbyPrey);
				if(getXY().distance(prey.getXY()) <= eatingRadius){
					// eating
					state = 1;
					eatPrey();
				}
				else{
					// hunting
					state = 3;
				}
			}
		}
		else{
			// else - Idle, Random Walk
			state = 0;
		}
	}

	// Step 3: Move
	public Point2D.Double move(ArrayList<Organism> organisms) {
		// System.out.println(toString()+"state="+state+" move()");
		Point2D.Double temp = null;

		/* 	Organisms can be in various states represented by an integer
			0. idle
			1. eating
			2. beingEaten
			3. hunting
			4. escaping */
		switch (state) {
			
			case 0: /* Idle - random walk */
				temp = randomWalk();
				break;
			
			case 1: /* Eating (no movement) */
				temp = getXY();
				break;
			
			case 2: /* Being eaten (no movement) */
				temp = getXY();
				break;
			
			case 3: /* Hunting */
				temp = hunt();
				break;

			case 4: /* Escaping */
				temp = escape();
				break;

			default:
				temp = randomWalk();
				break;
		}

		return temp;
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~ Utility methods - called by control methods ~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	public Point2D.Double randomWalk(){ // Called in move()
		String prevDirection = "none";
		int newX = this.X;
		int newY = this.Y;

		// get previous direction of travel from X,Y and prevX and prevY
		if(this.X - this.prevX == 0 && this.Y - this.prevY < 0){
			prevDirection = "north";
		}
		else if(this.X - this.prevX == 0 && this.Y - this.prevY > 0){
			prevDirection = "south";
		}
		else if(this.X - this.prevX < 0 && this.Y - this.prevY == 0){
			prevDirection = "west";
		}
		else if(this.X - this.prevX > 0 && this.Y - this.prevY == 0){
			prevDirection = "east";
		}

		// chance of this animal moving in the same direction
		double sameDirectionChance = 0.8;

		// chance of this animal staying in the same location
		if(prevDirection == "none"){
			sameDirectionChance = 0.4;
		}

		if(Math.random() < sameDirectionChance){
			// continue moving in same direction as previous move
			if(prevDirection == "north"){
				newY -= speed;
			}
			else if(prevDirection == "south"){
				newY += speed;
			}
			else if(prevDirection == "east"){
				newX += speed;
			}
			else if(prevDirection == "west"){
				newX -= speed;
			}
			else if(prevDirection == "none"){
				// do nothing
			}
		}
		else{
			// change direction
			double newDirection = Math.random();

	        if(newDirection < 0.2){ // north
	        	newY -= speed;
	        }
	        else if(newDirection < 0.4){ // south
	        	newY += speed;
	        }
	        else if(newDirection < 0.6){ // east
	        	newX += speed;
	        }
	        else if(newDirection < 0.8){ // west
	        	newX -= speed;
	        }
	        else if(newDirection < 1.0){ // same spot
	        	// do nothing
	        }
		}
		return (new Point2D.Double(newX,newY));
	}
	public Point2D.Double hunt(){ // Called in move()
		// System.out.println(" this.X="+this.X+" this.Y="+this.Y);
		Point2D.Double preyXY = prey.getXY();

		double xDist = this.X - preyXY.x;
		double yDist = this.Y - preyXY.y;

		if(Math.abs(xDist) > maxSpeed){
			if(xDist < 0){
				xDist = -1*maxSpeed;
			}
			else{
				xDist = maxSpeed;
			}
		}
		if(Math.abs(yDist) > maxSpeed){
			if(yDist < 0){
				yDist = -1*maxSpeed;
			}
			else{
				yDist = maxSpeed;
			}
		}

		// System.out.println("    xDist:"+xDist+" yDist:"+yDist);
		if(Math.abs(xDist) >= Math.abs(yDist)){
			// move along x at top speed
			return (new Point2D.Double(this.X-xDist, this.Y));
		}
		else{
			// move along y at top speed
			return (new Point2D.Double(this.X, this.Y-yDist));
		}
	}
	public Point2D.Double escape(){ // Called in move()
		// System.out.println(" this.X="+this.X+" this.Y="+this.Y);
		ArrayList<Point2D.Double> newLocations = new ArrayList<Point2D.Double>();

		// add 5 locations: NSEW and same spot
		newLocations.add(getXY()); // same spot
		newLocations.add(new Point2D.Double(X+maxSpeed, Y)); // east
		newLocations.add(new Point2D.Double(X-maxSpeed, Y)); // west
		newLocations.add(new Point2D.Double(X, Y+maxSpeed)); // south
		newLocations.add(new Point2D.Double(X, Y-maxSpeed)); // north

		// Cycle through newLocations and locations that are not within the boundaries
		ArrayList<Point2D.Double> invalidLocations = new ArrayList<Point2D.Double>();
        for(Point2D.Double p : newLocations){
            // if out of bounds
            if(!isPointWithinBoundary(p)){
                invalidLocations.add(p); // add to invalidLocations
            }
        }
        // remove invalidLocations from newLocations
        for(Point2D.Double p : invalidLocations){
            for(Point2D.Double n : newLocations){
	            if(n.equals(p)){
	                newLocations.remove(p);
	                break;
	            }
	        }
        }

        // Choose newLocation that is furthest away from predator
		double dist = predator.getXY().distance(newLocations.get(0));
		Point2D.Double furthestPoint = newLocations.get(0);

		// find point furthest from predator
		for(Point2D.Double p : newLocations){
			double d = predator.getXY().distance(p);
			if(d > dist){
				dist = d;
				furthestPoint = p;
			}
		}
		return furthestPoint;
	}
	public ArrayList<Organism> getOrganismsWithinSightRadius(ArrayList<Organism> organisms){
		// Create emtpy list of organisms to fill up then return
		ArrayList<Organism> organismsWithinRadius = new ArrayList<Organism>();
		
		//iterate through all organisms
		for (Organism o : organisms){
			double distToCurrPoint = this.getXY().distance(o.getXY());
			if(distToCurrPoint <= this.getSightRadius() && o.getID() != this.id){
				organismsWithinRadius.add(o);
			}
		}
		return organismsWithinRadius;
	}
	public ArrayList<Organism> getNearbyPrey(ArrayList<Organism> nearbyOrganisms){
		/* Argument is result of calling getOrganismsWithinSightRadius() */

		// create empty list of organisms to fill up then return
		ArrayList<Organism> nearbyPrey = new ArrayList<Organism>();
		
		// iterate through nearbyOrganisms
		for(Organism o : nearbyOrganisms){
			if( this.preyTypes.contains(o.getType()) ){
				nearbyPrey.add(o);
			}
		}
		return nearbyPrey;
	}
	public Organism getClosestPrey(ArrayList<Organism> nearbyPrey){
		Organism org = nearbyPrey.get(0);
		double dist = getXY().distance(org.getXY());

		// find closest prey
		for(Organism o : nearbyPrey){
			double d = getXY().distance(o.getXY());
			if(d < dist){
				dist = d;
				org = o;
			}
		}
		return org;
	}
	public ArrayList<Organism> getNearbyPredators(ArrayList<Organism> nearbyOrganisms){
		/* Argument is result of calling getOrganismsWithinSightRadius() */

		// create empty list of organisms to fill up then return
		ArrayList<Organism> nearbyPredators = new ArrayList<Organism>();
		
		// iterate through nearbyOrganisms
		for(Organism o : nearbyOrganisms){
			if( this.predatorTypes.contains(o.getType()) ){
				nearbyPredators.add(o);
			}
		}
		return nearbyPredators;
	}
	public Organism getClosestPredator(ArrayList<Organism> nearbyPredators){
		Organism org = nearbyPredators.get(0);
		double dist = getXY().distance(org.getXY());

		// find closest predator
		for(Organism o : nearbyPredators){
			double d = getXY().distance(o.getXY());
			if(d < dist){
				dist = d;
				org = o;
			}
		}
		return org;
	}
	public void eatPrey(){
		// System.out.println(this.type+" id:"+this.id + " eating...");

		if(health < maxHealth && prey.getHealth() > 0){
			health += healthGainedEatingPerGameTick;
			double h = prey.getHealth() - healthGainedEatingPerGameTick;
			prey.setHealth(h);
		}
		else{
			// finish eating, go back to random walk
			state = 0;
		}
	}
	public boolean isPointWithinBoundary(Point2D.Double point){
        /* Check to see if a point is within screen boundary */
        int x = (int)point.x;
        int y = (int)point.y;
        int screenWidth = D.width;
        int screenHeight = D.height;

        if(x >= 0 && y >= 0 && x < screenWidth && y < screenHeight){
            return true;
        }
        else{
            return false;
        }
    }
	public double generateRandomInitialHealth(){ // Called by constructor
		// generate a starting health point value between hungerHealth and maxHealth
		Random r = new Random();
		double rangeMin = (hungerHealth);
		double range = maxHealth - rangeMin + 1.0;
		double randomHealth = rangeMin + (range) * r.nextDouble();
		return randomHealth;
	}


	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~ Get and Set (These should be the same for all organisms) ~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	// Get
	public int getID(){
		return this.id;
	}
	public String getType(){
		return this.type;
	}
	public int getX(){
		return this.X;
	}
	public int getY(){
		return this.Y;
	}
	public Point2D.Double getXY(){
		return (new Point2D.Double(this.X, this.Y));
	}
	public double getHealth(){
		return this.health;
	}
	public int getSightRadius(){
		return this.sightRadius;
	}
	public int getState(){
		return state;
	}
	public boolean isGivingBirth(){
		if(Math.random() <= probabilityGivingBirth){
			return true;
		}
		return false;
	}
	public int getNumBabiesProduced(){
		return (int)RandTool.gaussian(avgNumBabies, 1);
	}
	// Set
	public void setX(int x){
		this.X = x;
	}
	public void setY(int y){
		this.Y = y;
	}
	public void setXY(Point2D.Double point){
		// update prevX and prevY
		this.prevX = this.X;
		this.prevY = this.Y;

		// set new X and Y
		this.X = (int)point.x;
		this.Y = (int)point.y;
	}
	public void setTargetLocation(Point2D.Double point){
		targetLocation = new Point2D.Double(point.x, point.y);
	}
	public void setHealth(double health) {
		this.health = health;
	}
	// To String ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String toString(){
		return this.type + " " + this.id;
	}
}


