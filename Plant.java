import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Plant implements Organism{
	
	// Constants (specific to this animal type)
	static final String type = "Plant";
	ArrayList<String> preyTypes = null;
	static final double maxHealth = 100;
	static final double healthLostPerGameTick = -1.0; // Plant gains health per game tick

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

	// Control Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void updateHealthTime(){
		if(this.health < this.maxHealth){
			this.health = this.health - this.healthLostPerGameTick; // adds health
			if(this.health > this.maxHealth){
				this.health = this.maxHealth;
			}
		}
	}

	public Point2D.Double randomWalk(){
		//do nothing. plants don't move!
		return (new Point2D.Double(this.X, this.Y));
	}

	public double generateRandomInitialHealth(){
		// generate a starting health point value between 0.5*maxHealth and maxHealth
		Random r = new Random();
		double rangeMin = (maxHealth/2.0);
		double range = maxHealth - rangeMin + 1.0;
		double randomHealth = rangeMin + (range) * r.nextDouble();
		return randomHealth;
	}


	// Get ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
	public ArrayList<Organism> getIDsWithinSightRadius(ArrayList<Organism> organisms){
		/* 
		Return null. Plants don't have a sight radius.
		This will help us find bugs in code if we ever try to access this list for a Plant.
		*/
		return null;
	}
	public ArrayList<Organism> getNearbyPrey(ArrayList<Organism> organisms){
		/* 
		Return null. Plants don't have prey.
		This will help us find bugs in code if we ever try to access this list for a Plant.
		*/
		return null;
	}

	public int getState(){
		return -1; //TODO
	}


	// Set ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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

	public void setState(int state) {
		this.state = state;
	}

	// To String
	public String toString(){
		return this.type + " " + this.id + ": x=" + this.X + " y="+ this.Y;
	}


	// Drawing
	public void drawOrganism(Organism o, Graphics g, boolean displayAxes, boolean displayHealth, boolean displaySightRadius, boolean displayOrganismID){
        int x = o.getX();
        int y = o.getY();
        double health = o.getHealth();

        Color plantColor = new Color(51,102,0);
        g.setColor(plantColor);

        // lower left
        g.drawArc(x-38, y-22, 38, 44, 0, 70);
        g.drawArc(x-36, y-20, 36, 40, 0, 80);
        g.drawArc(x-34, y-18, 34, 36, 0, 80);
        g.drawArc(x-32, y-16, 32, 32, 0, 100);
        g.drawArc(x-30, y-14, 30, 28, 0, 120);
        g.drawArc(x-28, y-12, 28, 24, 0, 140);

        // lower right
        g.drawArc(x, y-22, 38, 44, 110, 70);
        g.drawArc(x, y-20, 36, 40, 100, 80);
        g.drawArc(x, y-18, 34, 36, 100, 80);
        g.drawArc(x, y-16, 32, 32, 80, 100);
        g.drawArc(x, y-14, 30, 28, 60, 120);
        g.drawArc(x, y-12, 28, 24, 40, 140);

        // center left
        g.drawLine(x-1, y, x-9, y-24);
        g.drawLine(x-1, y, x-6, y-25);
        g.drawLine(x, y, x-4, y-27);
        g.drawLine(x, y, x-2, y-25);

        // center right
        g.drawLine(x+1, y, x+9, y-24);
        g.drawLine(x+1, y, x+6, y-25);
        g.drawLine(x, y, x+4, y-27);
        g.drawLine(x, y, x+2, y-25);

        if(displayHealth){
            // health bar
            g.setColor(Color.RED);
            double healthBarValue = health/2.0; // 50 pixels = 100 health for plants (max health)
            g.fillRect(x-25, y-32, (int)healthBarValue, 1);

            // health bar edges
            g.setColor(Color.BLACK);
            g.fillRect(x-26, y-33, 1, 3);
            g.fillRect(x+25, y-33, 1, 3);
        }
        if (displayOrganismID) {
            //Organism ID
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(o.getID()), x-10, y+14);
        }
    }


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Methods specific to this class. These are not in the Organism interface.

	public void whatIAm(){ // can be called from a Plant object, not from organism object
		System.out.println("I'm a plant! My health is "+health+" / "+maxHealth);
	}
}
