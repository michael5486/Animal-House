import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public interface Organism{

	public int getID();
	public String getType();
	public int getX();
	public int getY();
	public Point2D.Double getXY();
	public int getHealth();
	public int getSightRadius();
	public int getState();
	public ArrayList<Integer> getNearbyPreyIDs(ArrayList<Organism> organisms);

	public void setX(int x);
	public void setY(int y);
	public void setXY(Point2D.Double point);
	//public void setState(State state);

	public Point2D.Double randomWalk();
	public int generateRandomInitialHealth();

	public String toString();


	/* Enum for state */
	public enum State {
		IDLING, EATING, BEING_EATEN, HUNTING, ESCAPING
	}

	/* Enum for all possible organisms */
	public enum OrganismType {
		MOUSE, PLANT
	}

}

