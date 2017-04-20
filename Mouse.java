import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public class Mouse implements Organism{
	
	// Constants (specific to this animal type)
	String type = "Mouse";
	int maxHealth = 5;
	int speed = 5;

	// Variables (to be set)
	int id;
	int X, Y;
	int prevX, prevY;



	// Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Mouse(int id, int x, int y){
		this.id = id;
		this.X = x;
		this.Y = y;
		this.prevX = x;
		this.prevY = y;
	}

	Mouse(int id, Point2D.Double randomPoint){
		this.id = id;
		this.X = (int)randomPoint.x;
		this.Y = (int)randomPoint.y;
		this.prevX = this.X;
		this.prevY = this.Y;
	}


	// Control Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public void randomWalk(){
		
		String prevDirection = "none";

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
		double sameDirectionChance = 0.99999;

		// chance of this animal staying in the same location
		if(prevDirection == "none"){
			sameDirectionChance = 0.4;
		}

		if(Math.random() < sameDirectionChance){
			// continue moving in same direction as previous move
			if(prevDirection == "north"){
				this.Y -= speed;
			}
			else if(prevDirection == "south"){
				this.Y += speed;
			}
			else if(prevDirection == "east"){
				this.X += speed;
			}
			else if(prevDirection == "west"){
				this.X -= speed;
			}
			else if(prevDirection == "none"){
				// do nothing
			}
		}
		else{
			// change direction
			double newDirection = Math.random();

	        if(newDirection < 0.2){ // north
	        	this.Y -= speed;
	        }
	        else if(newDirection < 0.4){ // south
	        	this.Y += speed;
	        }
	        else if(newDirection < 0.6){ // east
	        	this.X += speed;
	        }
	        else if(newDirection < 0.8){ // west
	        	this.X -= speed;
	        }
	        else if(newDirection < 1.0){ // same spot
	        	// do nothing
	        }
		}

		// update prevX and prevY
		this.prevX = this.X;
		this.prevY = this.Y;
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

	public void whatIAm(){ // can be called from a Mouse object, not from organism object
		System.out.println("I'm a mouse!");
	}
}
