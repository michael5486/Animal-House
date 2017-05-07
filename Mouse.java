import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public class Mouse implements Organism{
	
	// Constants (specific to this animal type)
	static final String type = "Mouse";
	static final ArrayList<String> preyTypes = new ArrayList<String>(Arrays.asList("Plant"));
	static final double maxHealth = 5.0;
	static final double hungerHealth = 2.55;
	static final double healthLostPerGameTick = 0.01;
	static final double healthGainedEatingPerGameTick = 0.5;
	static final int maxSpeed = 5;     // pixels
	static final int sightRadius = 40; // pixels
	static final int eatingRadius = 10; // pixels

	// Variables (to be set and changed)
	int id;
	int X, Y;
	int prevX, prevY;
	Dimension D;
	int speed = maxSpeed;
	double health;
	Point2D.Double targetLocation;
	Organism currentPrey;
	
	/* 	Organisms can be in various states represented by an integer
		0. idle
		1. eating
		2. beingEaten
		3. hunting
		4. escaping */
	int state = 0;



	// Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Mouse(int id, Point2D.Double randomPoint){
		// Create a mouse at a location X,Y
		this.id = id;
		this.X = (int)randomPoint.x;
		this.Y = (int)randomPoint.y;
		this.prevX = this.X;
		this.prevY = this.Y;

		// set initial health
		this.health = generateRandomInitialHealth();
	}


	// Control Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void updateHealthTime(){
		this.health = this.health - this.healthLostPerGameTick;
	}

	public Point2D.Double randomWalk(){
		
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

	public double generateRandomInitialHealth(){
		// generate a starting health point value between hungerHealth and maxHealth
		Random r = new Random();
		double rangeMin = (hungerHealth);
		double range = maxHealth - rangeMin + 1.0;
		double randomHealth = rangeMin + (range) * r.nextDouble();
		return randomHealth;
	}

	public Point2D.Double hunt(){
		// System.out.println("hunt()");
		

		if(targetLocation == null){
			System.out.println("we got a huge problem here in hunt()");
			return (new Point2D.Double(this.X, this.Y));
		}
		// System.out.println(" this.X="+this.X+" this.Y="+this.Y);

		double xDist = this.X - targetLocation.x;
		double yDist = this.Y - targetLocation.y;

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


		/*
		//mouse can move one space closer until it overlaps prey
		//gets X value of first prey, can change this to sort prey by closest distance and move to closest prey

		//code would get an error when checking bounds immediately after a plant dies
		if (this.getNearbyPrey(organisms).size() == 0) {
			temp = new Point2D.Double(this.X, this.Y);
			break;
		}
		int preyX = this.getNearbyPrey(organisms).get(0).getX();
		int preyY = this.getNearbyPrey(organisms).get(0).getY();


		if (preyX < this.X) { //west
			if (preyY > this.Y) { //southwest
				temp = new Point2D.Double(this.X - 1, (double)this.Y + 1.0);
			}
			else if (preyY == this.Y) { //west
				temp = new Point2D.Double(this.X - 1.0, this.Y);
			}
			else { //northwest
				temp = new Point2D.Double(this.X - 1.0, this.Y - 1.0);
			}

		}
		else if (preyX == this.X) {
			if (preyY > this.Y) { //south
				temp = new Point2D.Double(this.X, this.Y + 1.0);
			}
			else if (preyY == this.Y) { //same pixel
				temp = new Point2D.Double(this.X, this.Y);
			}
			else { //north
				temp = new Point2D.Double(this.X, this.Y - 1.0);
			}
		}
		else { //prey to the right of organism
			if (preyY > this.Y) { //southeast
				temp = new Point2D.Double(this.X + 1.0, this.Y + 1.0);
			}
			else if (preyY == this.Y) { //east
				temp = new Point2D.Double(this.X + 1.0, this.Y);
			}
			else { //north
				temp = new Point2D.Double(this.X + 1.0, this.Y - 1.0);
			}
		}
		return temp;
		*/
	}

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
				break;

			default:
				temp = randomWalk();
				break;
		}

		return temp;
	}


	public void eatPrey(Organism prey) {
		System.out.println(this.type+" id:"+this.id + " eating...");

		// does this even work ?
		if(health < maxHealth){
			health += healthGainedEatingPerGameTick;
			double h = prey.getHealth() - healthGainedEatingPerGameTick;
			prey.setHealth(h);
		}
		else{
			// finish eating, go back to random walk
			state = 0;
		}

		// //would sometimes crash when mouse tries to eat a plant that has just disappeared
		// if (this.health < this.maxHealth && this.getNearbyPrey(organisms).size() > 0) {
		// 	this.health = this.health + healthGainedEatingPerGameTick;
		// 	double currPreyHealth = this.getNearbyPrey(organisms).get(0).getHealth();
		// 	this.getNearbyPrey(organisms).get(0).setHealth(currPreyHealth - healthGainedEatingPerGameTick);
		// }
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

	//updating state
	public void updateState(ArrayList<Organism> organisms) {
		/* 	
		Organisms can be in various states represented by an integer
			0. idle
			1. eating
			2. beingEaten
			3. hunting
			4. escaping
		*/
		ArrayList<Organism> nearbyOrganisms = getOrganismsWithinSightRadius(organisms);
		ArrayList<Organism> nearbyPrey = getNearbyPrey(nearbyOrganisms);

		
		if(state == 1){ // If organism is already eating, continue eating
			// System.out.println(toString()+" state = 1, continue eating");
			eatPrey(currentPrey);
		}
		else if(health <= hungerHealth){ // Else if organism is below hungerHealth and there exists prey within sight radius
			if(!nearbyPrey.isEmpty()){
				Organism newPrey = getClosestPrey(nearbyPrey);
				if(getXY().distance(newPrey.getXY()) <= eatingRadius){
					// eating
					// System.out.println(toString()+" state = 1, begin eating");
					state = 1;
					currentPrey = newPrey;
					eatPrey(currentPrey);
				}
				else{
					// hunting
					// System.out.println(toString()+" state = 3, hunting");
					state = 3;
					setTargetLocation(newPrey.getXY());
				}
			}
			
		}
		else{
			// else - Idle, Random Walk
			state = 0;
		}
		// ArrayList<Organism> prey = getNearbyPrey(organisms);
		// if (prey.size() > 0) {
		// 	//System.out.printf("ID %d sees prey", this.id);
		// 	double distToPrey = getXY().distance(prey.get(0).getXY());
		// 	if (distToPrey < sightRadius && health < hungerHealth) {
		// 		//eating prey
		// 		this.state = 2;
		// 		return;
		// 	}
		// 	else {
		// 		this.state = 1;
		// 		return;
		// 	}
		// }

		
	}


	// Get ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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

		// call getOrganismsWithinSightRadius()
		// ArrayList<Organism> nearbyOrganisms = this.getOrganismsWithinSightRadius(organisms);
		
		// iterate through nearbyOrganisms
		for(Organism o : nearbyOrganisms){
			if( this.preyTypes.contains(o.getType()) ){
				nearbyPrey.add(o);
			}
		}
		return nearbyPrey;
	}

	public int getState(){
		return state;
	}


	// Set ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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

	// To String
	public String toString(){
		return this.type + " " + this.id + ": ";
	}





	// Drawing ~~~~~~~~~~~~~~~~~~~~~~~~
    public void drawOrganism(Organism o, Graphics g, boolean displayAxes, boolean displayHealth, boolean displaySightRadius, boolean displayOrganismID){
        int x = o.getX();
        int y = o.getY();
        double health = this.getHealth();

        Color mouseColor = new Color(128,128,128);
        g.setColor(mouseColor);
        g.fillOval(x-5, y-8, 10, 16); // body

        g.setColor(new Color(110,110,110)); // ear color
        g.fillArc(x-5, y-1, 5, 7, 15, 180); // left ear
        g.fillArc(x, y-1, 5, 7, 345, 180);  // right ear
        g.setColor(Color.BLACK);            // detail color
        g.fillRect(x-1, y+4, 1, 1);         // left eye
        g.fillRect(x+1, y+4, 1, 1);         // right eye
        g.drawArc(x-8,y-10, 8, 6, 0, 180);  // tail
        g.drawLine(x-1, y+6, x-6, y+5);     // left top whisker
        g.drawLine(x-1, y+6, x-5, y+7);     // left bottom whisker
        g.drawLine(x+1, y+6, x+6, y+5);     // right top whisker
        g.drawLine(x+1, y+6, x+5, y+7);     // right bottom whisker

        if(displayHealth){
            // health bar
            g.setColor(Color.RED);
            double healthBarValue = health*4.0; // 20 pixels = 5 health for mice (max health)
            g.fillRect(x-10, y-14, (int)healthBarValue, 1);

            // health bar edges
            g.setColor(Color.BLACK);
            g.fillRect(x-11, y-15, 1, 3);
            g.fillRect(x+10, y-15, 1, 3);
        }
        if (displayOrganismID) {
            //Organism ID
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(o.getID()), x-10, y+20);
        }
    }

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Methods specific to this class. These are not in the Organism interface.

	public void whatIAm(){ // can be called from a Mouse object, not from organism object
		System.out.println("I'm a mouse! My health is "+health+" / "+maxHealth);
	}
}
