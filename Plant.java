import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Plant implements Organism{
	
	// Constants (specific to this animal type)
	static final String type = "Plant";
	ArrayList<String> preyTypes = null;
	static final double maxHealth = 100;
	static final double healthLostPerGameTick = -0.1; // Plant gains health per game tick

	// Variables (to be set)
	int id;
	int X, Y;
	double health;
	int state = 0;



	// Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Plant(int id, Point2D.Double randomPoint){
		// Create a plant at a location X,Y
		this.id = id;
		this.X = (int)randomPoint.x;
		this.Y = (int)randomPoint.y;

		// set initial health
		this.health = generateRandomInitialHealth();
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~ Control Methods: To be called by AnimalSimulator ~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	// Step 1: Update Health
	public void updateHealthTime(){
		if(this.health < this.maxHealth){
			this.health = this.health - this.healthLostPerGameTick; // adds health
			if(this.health > this.maxHealth){
				this.health = this.maxHealth;
			}
		}
	}

	// Step 2: Update State
	public void updateState(ArrayList<Organism> organisms) {
		// Plants can't move,
		// they never changes state
	}

	// Step 3: Move
	public Point2D.Double move(ArrayList<Organism> organism) {
		//plant doesnt move
		return (new Point2D.Double(this.X, this.Y));
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~ Utility methods - called by control methods ~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	public Point2D.Double randomWalk(){
		//do nothing. plants don't move!
		return (new Point2D.Double(this.X, this.Y));
	}
	public Point2D.Double hunt(){
		//plant doesnt move
		return (new Point2D.Double(this.X, this.Y));
	}
	public ArrayList<Organism> getOrganismsWithinSightRadius(ArrayList<Organism> organisms){
		// Plants can't see
		return null;
	}
	public ArrayList<Organism> getNearbyPrey(ArrayList<Organism> organisms){
		// Plants don't have prey
		return null;
	}
	public Organism getClosestPrey(ArrayList<Organism> nearbyPrey){
		// Plants don't have prey
		return null;
	}
	public void eatPrey(Organism prey){
		// Plants dont eat prey
		// 
	}
	public double generateRandomInitialHealth(){ // Called by constructor
		// generate a starting health point value between 0.5*maxHealth and maxHealth
		Random r = new Random();
		double rangeMin = (maxHealth/2.0);
		double range = maxHealth - rangeMin + 1.0;
		double randomHealth = rangeMin + (range) * r.nextDouble();
		return randomHealth;
	}


	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~ Get and Set (These should be the same for all organisms - except for plant) ~~~~~~~~ */
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
		return new Point2D.Double(this.X, this.Y);
	}
	public double getHealth(){
		return this.health;
	}
	public int getSightRadius(){
		return 0;
	}
	public int getState(){
		return state;
	}
	// Set
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
	public void setTargetLocation(Point2D.Double point){
		// Plants don't have a target location.
	}
	public void setHealth(double health) {
		this.health = health;
	}
	// To String ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String toString(){
		return this.type + " " + this.id;
	}
}


