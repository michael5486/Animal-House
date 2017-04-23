import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public class Mouse implements Organism{
	
	// Constants (specific to this animal type)
	//static final String type = "Mouse";

	public enum OrganismType {
		MOUSE, PLANT
	}
	static final OrganismType type = MOUSE;
	static final int maxHealth = 5;
	static final int maxSpeed = 5;
	static final int sightRadius = 40;

	// Variables (to be set and changed)
	int id;
	int X, Y;
	int prevX, prevY;
	Dimension D;           // current screen size
	int speed = maxSpeed;
	int health;


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

	public int generateRandomInitialHealth(){
		// generate a starting health point value between 0.4*maxHealth and maxHealth
		Random rn = new Random();
		int minimum = (int)(0.4*maxHealth);
		int range = maxHealth - minimum + 1;
		int h = rn.nextInt(range) + minimum;
		return h;
	}

	// Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
	public int getHealth(){
		return this.health;
	}
	public int getSightRadius(){
		return this.sightRadius;
	}
	public ArrayList<Integer> getNearbyPreyIds(ArrayList<Organism> organisms) {
		
		ArrayList<Integer> nearbyOrganisms = new ArrayList<Integer>();

		//iterate through all organisms, if
		for (int i = 0; i < organisms.size(); i++) {

			Point2D.Double currPoint = Point2D.Double(this.X, thix.Y);
			double distanceFromCurrPoint = currPoint.distance(organisms.get(i).getXY());
			
			System.out.printf("Distance from id %d = %f", this.id, distanceFromCurrPoint);

			if (distanceFromCurrPoint < this.sightRadius) { //
				if (organisms.get(i).getType() == PLANT) {
					nearbyOrganisms.add(organisms.get(i).getID());
				}
			}
		}

		return nearbyOrganisms;
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

	// To String
	public String toString(){
		return this.type + ": x=" + this.X + " y="+ this.Y;
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Methods specific to this class. These are not in the Organism interface.

	public void whatIAm(){ // can be called from a Mouse object, not from organism object
		System.out.println("I'm a mouse! My health is "+health+" / "+maxHealth);
	}
}
