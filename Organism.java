import java.awt.*;
import java.awt.geom.*;


public interface Organism{

	public int getID();
	public String getType();
	public int getX();
	public int getY();

	public void setX(int x);
	public void setY(int y);
	public void setXY(Point2D.Double point);

	public void randomWalk();

	public String toString();
}

