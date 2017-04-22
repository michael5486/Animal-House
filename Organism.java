import java.awt.*;
import java.awt.geom.*;


public interface Organism{

	public int getID();
	public String getType();
	public int getX();
	public int getY();
	public Point2D.Double getXY();
	public int getHealth();
	public int getSightRadius();

	public void setX(int x);
	public void setY(int y);
	public void setXY(Point2D.Double point);

	public Point2D.Double randomWalk();
	public int generateRandomInitialHealth();

	public String toString();
}

