
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class trooperSimulator {

    public double x;
    public double y;
    public double v; 
    public double a;

    public double t = 0;
    public double delT;

    public double totalDistance = 0;

    public boolean isCop;
    public int lightsCount;

    public String type = "";


    public trooperSimulator(double initX, double initY, double initV, double initA, double initDelT, boolean initIsCop, String initType) {
    	x = initX;
        y = initY;
        v = initV;
        a = initA;
        delT = initDelT;
        isCop = initIsCop;
        type = initType;
    }
    
    public void draw (Graphics2D g2, Dimension D) {

    	//cars keep going off screen, multiply x by constant to keep cars
        //relatively the same speed but depict it different graphically

    	System.out.printf("Drawing %s at (%.2f, %.2f)\n", type, x * 0.5, y);
    	
    	if (isCop) {  //cops lights blink red and blue
    		int temp = lightsCount % 10;
    		if (temp < 5) {
    			g2.setColor(Color.red);
    		}
    		else {
    			g2.setColor(Color.blue);
    		}
    	}
    	else { //speeding car is purple
    		g2.setColor(Color.magenta);
    	}
    	lightsCount++;
    	
    	//Draws a colored circle at given x and y
    	//Need to adjust the Y values to switch from Cartesian to Java coords
    	g2.fillOval((int)(x * 0.5), D.height - (int)y, 10, 10);



    }
    
    public void nextStep (double delT) {
        //control 1 is the velocity, control 2 is the steering angle

        //we have our previous x and y coordinates. Need to find the distances travelled
        //in the x and y directions in the time

    	//needs to find the velocity

        v = v + a * delT;
        double changeX = v * delT; 

        x = x + changeX;

        totalDistance += changeX;
        t = t + delT;
    }

    
    public double getX () {
        return x;
    }
    
    public double getY () {
        return y;
    }

    public double getV () {
        return v;
    }

    public double getTime() {
    	return t;
    }

    public double getDistanceMoved () {
    	return totalDistance;
    }

}
