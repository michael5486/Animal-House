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


/* Global variables for the GUI and set up methods */
    int numPlants;
    int numRabbits;
	double delT = 0.1;

/* GUI Animation stuff */
	Thread currentThread;
	DecimalFormat df = new DecimalFormat ("##.##");
	String topMessage = "";

/* GUI Construction stuff */
    Container cPane = null;
    JTextField numPlantsField, numRabbitsField;

	// trooperSimulator copSim = null;
	// trooperSimulator speederSim = null;

	







    public void setAnimals() {
    	System.out.println("\nAnimals created...");

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

    /* Takes input from GUI text boxes */

    //TODO
    //need to make this fail gracefully...capture conversion errors with try/catch
    void reset() {
        System.out.println("\nReset:");

        // Get numPlants from entry field
        try{
            numPlants = Integer.parseInt(numPlantsField.getText());
            if(numPlants < 0){
                numPlants = 0;
                numPlantsField.setText("0");
                System.out.println("--Error: numPlants cannot be less than 0! Defaulting to 0.");
            }
        }
        catch(NumberFormatException e){
            System.out.println("--Error: Entry fields must have integer values! Defaulting numPlants to 0.");
            numPlants = 0;
            numPlantsField.setText("0");
        }

        // Get numRabbits from entry field
        try{
            numRabbits = Integer.parseInt(numRabbitsField.getText());
            if(numRabbits < 0){
                numRabbits = 0;
                numRabbitsField.setText("0");
                System.out.println("--Error: numRabbits cannot be less than 0! Defaulting to 0.");
            }
        }
        catch(NumberFormatException e){
            System.out.println("--Error: Entry fields must have integer values! Defaulting numRabbits to 0.");
            numRabbits = 0;
            numRabbitsField.setText("0");
        }


        System.out.printf("  numRabbits = %d\n  numPlants  = %d  \n", numRabbits, numPlants);
    }

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

    JPanel makeControlPanel() {
    	JPanel panel = new JPanel();

        // set the border and give it a title
        TitledBorder border = new TitledBorder("Controls");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder (border);

    	//panel.setLayout(2,1);
        JButton resetB = new JButton ("Reset");
    	JButton goButton = new JButton("Go");
    	JButton quitButton = new JButton("Quit");

        resetB.addActionListener (
        new ActionListener () {
           public void actionPerformed (ActionEvent a)
           {
               reset ();
           }
           }
        );
        panel.add (resetB);

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

    JPanel makeEntryPanel() {
        JPanel panel = new JPanel();
        
        // set the border and give it a title
        TitledBorder border = new TitledBorder("Enter the number of organisms");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder (border);
        

        // Labels and Entry fields for number of organisms
        
        // Plants
        JLabel plantLabel = new JLabel("Plants");
        panel.add(plantLabel);

        numPlantsField = new JTextField(5);
        numPlantsField.setText("0");
        panel.add(numPlantsField);

        // Mice
        JLabel mouseLabel = new JLabel("Mice");
        panel.add(mouseLabel);
        numRabbitsField = new JTextField(5);
        numRabbitsField.setText("0");
        panel.add(numRabbitsField);

        return panel;
    }



    JPanel makeBottomPanel () {
    	JPanel panel = new JPanel ();

    	//creates a GridLayout with two rows and one column
    	panel.setLayout (new GridLayout (1,1));

    	//creates a panel with Reset, Go, and Quit buttons
    	JPanel sPanel = makeControlPanel ();
    	panel.add (sPanel);


        //creates a panel with Organism Entry Fields
        JPanel ePanel = makeEntryPanel ();
        panel.add (ePanel);

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
