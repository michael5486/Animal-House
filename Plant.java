import java.awt.*;
import java.awt.geom.*;


public class Plant implements Organism{
	
	// Constants (specific to this animal type)
	String type = "Plant";
	int maxHealth = 100;

	// Variables (to be set)
	int id;
	int X, Y;


	// Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Plant(int id, int x, int y){
		this.id = id;
		this.X = x;
		this.Y = y;
	}

	Plant(int id, Point2D.Double randomPoint){
		this.id = id;
		this.X = (int)randomPoint.x;
		this.Y = (int)randomPoint.y;
	}

	// Control Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public Point2D.Double randomWalk(){
		//do nothing. plants don't move!
		return (new Point2D.Double(this.X, this.Y));
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
		return new Point2D.Double(this.X, this.Y);
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

	// To String
	public String toString(){
		return this.type + ": x=" + this.X + " y="+ this.Y;
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Methods specific to this class. These are not in the Organism interface.

	public void whatIAm(){ // can be called from a Plant object, not from organism object
		System.out.println("I'm a plant!");
	}
}
