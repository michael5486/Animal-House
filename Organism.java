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