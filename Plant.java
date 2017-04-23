import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Plant implements Organism{
	
	// Constants (specific to this animal type)
	static final String type = "Plant";
	ArrayList<String> preyTypes = null;
	static final int maxHealth = 100;

	// Variables (to be set)
	int id;
	int X, Y;
	int health;



	// Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Plant(int id, Point2D.Double randomPoint){
		// Create a plant at a location X,Y
		this.id = id;
		this.X = (int)randomPoint.x;
		this.Y = (int)randomPoint.y;

		// set initial health
		this.health = generateRandomInitialHealth();
	}

	// Control Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void updateHealthTime(){
		if(this.health < this.maxHealth){
			this.health++;
		}
	}

	public Point2D.Double randomWalk(){
		//do nothing. plants don't move!
		return (new Point2D.Double(this.X, this.Y));
	}

	public int generateRandomInitialHealth(){
		// generate a starting health point value between 0.4*maxHealth and maxHealth
		Random rn = new Random();
		int minimum = (int)(0.4*maxHealth);
		int range = maxHealth - minimum + 1;
		int h = rn.nextInt(range) + minimum;
		return h;
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
		return new Point2D.Double(this.X, this.Y);
	}
	public int getHealth(){
		return this.health;
	}
	public int getSightRadius(){
		return 0;
	}
	public ArrayList<Organism> getIDsWithinSightRadius(ArrayList<Organism> organisms){
		/* 
		Return null. Plants don't have a sight radius.
		This will help us find bugs in code if we ever try to access this list for a Plant.
		*/
		return null;
	}
	public ArrayList<Organism> getNearbyPrey(ArrayList<Organism> organisms){
		/* 
		Return null. Plants don't have prey.
		This will help us find bugs in code if we ever try to access this list for a Plant.
		*/
		return null;
	}

	public int getState(){
		return -1; //TODO
	}


	// Set ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void setX(int x){
		this.X = x;
	}
	public void setY(int y){
		this.Y = y;
	}
	public void setXY(Point2D.Double point){
		this.X = (int)point.x;
		this.Y = (int)point.y;
	}

	// To String
	public String toString(){
		return this.type + ": x=" + this.X + " y="+ this.Y;
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Methods specific to this class. These are not in the Organism interface.

	public void whatIAm(){ // can be called from a Plant object, not from organism object
		System.out.println("I'm a plant! My health is "+health+" / "+maxHealth);
	}
}
