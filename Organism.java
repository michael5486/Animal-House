import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public interface Organism{
	/* Organisms can be in various states represented by an integer
	0. idle
	1. eating
	2. beingEaten
	3. hunting
	4. escaping */

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~ Control Methods: To be called by AnimalSimulator ~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	public void updateHealthTime();
	public void updateState(ArrayList<Organism> organisms);
	public Point2D.Double move(ArrayList<Organism> organism);

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~ Utility methods - called by control methods ~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	public Point2D.Double randomWalk();
	public Point2D.Double hunt();
	public Point2D.Double escape();
	public ArrayList<Organism> getOrganismsWithinSightRadius(ArrayList<Organism> organisms);
	public ArrayList<Organism> getNearbyPrey(ArrayList<Organism> organisms);
	public Organism getClosestPrey(ArrayList<Organism> nearbyPrey);
	public ArrayList<Organism> getNearbyPredators(ArrayList<Organism> nearbyOrganisms);
	public Organism getClosestPredator(ArrayList<Organism> nearbyPrey);
	public void eatPrey();
	public boolean isPointWithinBoundary(Point2D.Double point);
	public double generateRandomInitialHealth();

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	/* ~~~~~~~~~~ Get and Set (These should be the same for all organisms) ~~~~~~~~ */
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	// Get
	public int getID();
	public String getType();
	public int getX();
	public int getY();
	public Point2D.Double getXY();
	public double getHealth();
	public double getMaxHealth();
	public int getSightRadius();
	public int getState();
	public boolean isGivingBirth();
	public int getNumBabiesProduced();
	// Set
	public void setX(int x);
	public void setY(int y);
	public void setXY(Point2D.Double point);
	public void setTargetLocation(Point2D.Double point);
	public void setHealth(double newHealth);
	// To String
	public String toString();
}

