import java.awt.*;
import java.awt.geom.*;


public class Organism{
	
	int id;
	int X, Y;
	String type;

	Organism(String type, int id, int x, int y){
		this.type = type;
		this.id = id;
		this.X = x;
		this.Y = y;
	}

	Organism(String type, int id, Point2D.Double randPoint) {
		this.type = type;
		this.id = id;
		this.X = (int)randPoint.x;
		this.Y = (int)randPoint.y;
	}

	int getID(){
		return this.id;
	}
	String getType(){
		return this.type;
	}
	int getX(){
		return this.X;
	}
	int getY(){
		return this.Y;
	}
}