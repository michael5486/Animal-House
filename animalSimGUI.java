//trooperGUI.java
//
// Author: Michael Esposito
//
// NOTE ABOUT COORDINATES: All the control code will use standard
// Cartesian coordinates with the origin in the lower-left corner.
// The GUI code converts to Java's coordinates where necessary.

import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class animalSimGUI extends JPanel {

	// double velocity, acceleration;

	// double copInitX = 50, copInitY = 150;
	// double speederInitX = 2, speederInitY = 153;

	double delT = 0.1;

	//Animation stuff
	Thread currentThread;

	DecimalFormat df = new DecimalFormat ("##.##");

	String topMessage = "";

	// trooperSimulator copSim = null;
	// trooperSimulator speederSim = null;

	Container cPane = null;





    public void setAnimals() {
    	System.out.println("Animals created...");

        //creates a cop with velocity = 10 and acceleration = 0
		//copSim = new trooperSimulator(copInitX, copInitY, 0.0, 12, delT, true, "cop    ");
        //creates a speeder with v = 100 and a = 0
		//speederSim = new trooperSimulator(speederInitX, speederInitY, 100, 0, delT, false, "speeder");


    }

////////////////////////////////////////////////////
//Drawing

    public void paintComponent(Graphics g) {
       super.paintComponent(g);
       Graphics2D g2 = (Graphics2D) g;

       Dimension D = this.getSize();

       /* Makes center panel light green */
       g.setColor(new Color(150, 243, 152));
       g.fillRect(0,0, D.width, D.height);

///Draws cars onto contentPane

      // if (copSim != null) {
      //     copSim.draw(g2, D);
      // }
      // if (speederSim != null) {
      //     speederSim.draw(g2, D);
      // } 

	// if (isBusted) {
	// 	try {

 //       		BufferedImage busted = ImageIO.read(new File("busted.png"));
 //        	g2.drawImage(busted, D.width/2 - 250, 50, null);
 //        	System.out.println("busted...");

 //            String totalDistance = "totalDistance = " + copSim.getDistanceMoved();

 //            //Draw the point of collision
 //            g2.setColor(Color.black);
 //            String collision = "Caught at (" + speederSim.getX() + ", " + speederSim.getY() + ")\n" + totalDistance;
 //            System.out.println(collision);
 //            g.drawString(collision, D.width/2 - 250, 500);

 //            //Draw functions
 //            copX.show(copX, speederX);

 //            //Find distance traveled



 //        } catch(IOException e) {
 //        	System.out.println("File not found");
 //        }



//Draws topmessage for time
  g.setColor(Color.black);
  g.drawString(topMessage, 20, 30);
}


//Animation

void go () {

        stopAnimationThread ();    // To ensure only one thread.
        
        currentThread = new Thread () {
        	public void run () 
        	{
        		animate ();
        	}
        };
        currentThread.start();
    }

    boolean nextStep() {

    	//Call nextstep for the cop and speedingCar
    	// copSim.nextStep(delT);
    	// speederSim.nextStep(delT);

     //    copX.add(copSim.getTime(), copSim.getX());
     //    speederX.add(speederSim.getTime(), speederSim.getX());

    	//check if cop has caught up to speeder, return true or false
    	return false;
    }

    void animate () {

    	while (true) {

    		boolean done = nextStep ();
    		if (done) {
    			System.out.println ("DONE!");
    			break;
    		}

    		//topMessage = "Time: " + df.format(copSim.getTime());
    		this.repaint ();

    		try {
    			Thread.sleep (25);
    		}
    		catch (InterruptedException e){
    			break;
    		}
        } //endwhile

        
        //topMessage = "Time: " + df.format(copSim.getTime()) + " trooperSpeed: " + df.format(copSim.getV());
        this.repaint ();
    }

    void stopAnimationThread () {
    	if (currentThread != null) {
    		currentThread.interrupt ();
    		currentThread = null;
    	}
    }

//GUI Contructions

    JPanel makeSetupPanel() {
    	JPanel panel = new JPanel();

    	//panel.setLayout(2,1);

    	JButton goButton = new JButton("Go");
    	JButton quitButton = new JButton("Quit");

    	goButton.addActionListener(
    		new ActionListener () {
    			public void actionPerformed (ActionEvent a) {
    				go();
    			}
    		}
    		);

    	quitButton.addActionListener(
    		new ActionListener () {
    			public void actionPerformed (ActionEvent a) {
    				System.exit(0);
    			}
    		}
    		);
    	panel.add(goButton);
    	panel.add(quitButton);
    	return panel;
    }

    JPanel makeBottomPanel () {
    	JPanel panel = new JPanel ();

    	//creates a GridLayout with two rows and one column
    	panel.setLayout (new GridLayout (1,1));

    	//creates a panel with Go and Quit buttons
    	JPanel sPanel = makeSetupPanel ();
    	sPanel.setBorder (BorderFactory.createTitledBorder ("  Controls  "));
    	panel.add (sPanel);
    	return panel;
    }


    void makeFrame () {
    	JFrame frame = new JFrame ();
    	frame.setSize (1000, 700);
    	frame.setTitle ("animalSimGUI");

    	//Obtains the content pane layer so we can add to it
    	//Container is part of Abstract Window Toolkit, used to create GUIs
    	cPane = frame.getContentPane();
    	cPane.add (makeBottomPanel(), BorderLayout.SOUTH);
    	cPane.add (this, BorderLayout.CENTER);
    	//cPane.setBackground(Color.black);
    	//cPane.repaint();
    	frame.setVisible (true);
    }

    public static void main(String[] args) {
    	animalSimGUI gui = new animalSimGUI();
    	gui.setAnimals();
    	gui.makeFrame();

    	//gui.setCars();
    }

}
