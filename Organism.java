import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public interface Organism{

	// Control Methods
	public void updateHealthTime();
	public Point2D.Double randomWalk();
	public double generateRandomInitialHealth();

	// Get
	public int getID();
	public String getType();
	public int getX();
	public int getY();
	public Point2D.Double getXY();
	public double getHealth();
	public int getSightRadius();
	public ArrayList<Organism> getIDsWithinSightRadius(ArrayList<Organism> organisms);
	public ArrayList<Organism> getNearbyPrey(ArrayList<Organism> organisms);

	/* Organisms can be in various states represented by an integer
	1. idle
	2. eating
	3. beingEaten
	4. hunting
	5. escaping */
	public int getState();


	// Set
	public void setX(int x);
	public void setY(int y);
	public void setXY(Point2D.Double point);
	public void setState(int state);


	// To String
	public String toString();


}

