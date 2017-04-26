
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


    // Animal Simulator
    AnimalSimulator animalSimulator;
    static final double delT = 0.1;

    // Initial numbers of organisms
    int numPlants = 20;
    int numMice = 30;

    /* Animation Options */
    boolean displayAxes = false;
    boolean displayHealth = true;
    boolean displaySightRadius = false;
    boolean displayOrganismID = false;


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /* Global variables for the GUI and set up methods */
    
    /* GUI Animation stuff */
    Thread currentThread;
    boolean isPaused = false;
    int sleepTime = 150;

    /* GUI Construction stuff */
    Container cPane = null;
    JTextField numPlantsField, numMiceField;
    JSlider speedSlider;
    Dimension D;



    /* Get input from GUI text boxes */
    void getNumOrganismsFromEntryField(){ // called in reset()
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

        // Get numMice from entry field
        try{
            numMice = Integer.parseInt(numMiceField.getText());
            if(numMice < 0){
                numMice = 0;
                numMiceField.setText("0");
                System.out.println("--Error: numMice cannot be less than 0! Defaulting to 0.");
            }
        }
        catch(NumberFormatException e){
            System.out.println("--Error: Entry fields must have integer values! Defaulting numMice to 0.");
            numMice = 0;
            numMiceField.setText("0");
        }

        System.out.printf("  numPlants = %d\n  numMice  = %d  \n", numPlants, numMice);
    }

    boolean nextStep() {

        //animalSimulator.nextStep(delT);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~`
        //Call nextstep for the cop and speedingCar
        // copSim.nextStep(delT);
        // speederSim.nextStep(delT);

        //    copX.add(copSim.getTime(), copSim.getX());
        //    speederX.add(speederSim.getTime(), speederSim.getX());
        //check if cop has caught up to speeder, return true or false

        return animalSimulator.nextStep(delT); // done = true
    }

    void reset() {
        System.out.println("\nReset:");

        // Read values from bottom entry fields
        getNumOrganismsFromEntryField();

        // Initialize animal simulator
        animalSimulator = new AnimalSimulator(D, delT, numPlants, numMice);

        this.repaint();
    }

    void go () {

        System.out.println("Go:");

        if (isPaused) {
            isPaused = false;
            return;
        }

        stopAnimationThread ();    // To ensure only one thread.
        
        currentThread = new Thread () {
        	public void run () 
        	{
        		animate ();
        	}
        };
        currentThread.start();
    }

    void pause() {
        System.out.println("Pause:");
        isPaused = true;
    }

    void animate () {
    	while (true) {

            if (!isPaused) {
                boolean done = nextStep();
                if (done) {
                    System.out.println ("DONE!");
                    break;
                }
            }

    		// boolean done = nextStep ();
    		// if (done) {
    		// 	System.out.println ("DONE!");
    		// 	break;
    		// }

    		this.repaint ();

    		try {
    			Thread.sleep (sleepTime);
    		}
    		catch (InterruptedException e){
    			break;
    		}
        } //endwhile
        
        this.repaint ();
    }

    void stopAnimationThread () {
    	if (currentThread != null) {
    		currentThread.interrupt ();
    		currentThread = null;
    	}
    }

    void toggleDisplayHealth (){
        if(displayHealth){
            displayHealth = false;
        }
        else{
            displayHealth = true;
        }
        this.repaint();
    }

    void toggleDisplayAxes (){
        if(displayAxes){
            displayAxes = false;
        }
        else{
            displayAxes = true;
        }
        this.repaint();
    }

    void toggleDisplaySightRadius (){
        if(displaySightRadius){
            displaySightRadius = false;
        }
        else{
            displaySightRadius = true;
        }
        this.repaint();
    }

    void toggleDisplayOrganismID() {
        if (displayOrganismID) {
            displayOrganismID = false;
        }
        else {
            displayOrganismID = true;
        }
        this.repaint();
    }

    //Drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        D = this.getSize();

        /* Makes center panel background light green */
        g.setColor(new Color(150, 243, 152));
        g.fillRect(0,0, D.width, D.height);

        // Draw all organisms
        if(animalSimulator != null){
            for(Organism o : animalSimulator.getOrganisms()){
                int x = o.getX();
                int y = o.getY();
                int sightRadius = o.getSightRadius();

                // Draw the x-y axes of the organism.
                if(displayAxes){
                    g.setColor(Color.PINK);
                    g.drawLine(x-20, y, x+20, y); // x-axis
                    g.drawLine(x, y-20, x, y+20); // y-axis
                }

                // Draw the sight radius of the organism.
                if(displaySightRadius && o.getType() != "Plant"){
                    g.setColor(Color.YELLOW);
                    g.drawOval(x-sightRadius, y-sightRadius, sightRadius*2, sightRadius*2);
                }
               
                //draws each organism
                o.drawOrganism(o, g, displayAxes, displayHealth, displaySightRadius, displayOrganismID);
            }
        }
    }

    //GUI Contructions
    JPanel makeControlPanel() {
    	JPanel panel = new JPanel();
        panel.setLayout (new GridLayout(2,1));



        /* set the border and give it a title */
        TitledBorder border = new TitledBorder("Controls");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder (border);


        /* make a panel for the buttons */
        JPanel buttonPanel = new JPanel();

        JButton resetB = new JButton ("Reset");
        JButton goButton = new JButton("Go");
        JButton quitButton = new JButton("Quit");
        JButton pauseButton = new JButton("Pause");

        resetB.addActionListener (
            new ActionListener () {
                public void actionPerformed (ActionEvent a)
                {
                    reset ();
                }
            }
        );
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

        pauseButton.addActionListener(
            new ActionListener () {
                public void actionPerformed (ActionEvent a) {
                    pause();
                }
            }
        );


        buttonPanel.add(quitButton);
        buttonPanel.add(resetB);
        buttonPanel.add(goButton);
        buttonPanel.add(pauseButton);

        // add button panel to control panel
        panel.add(buttonPanel); 


        /* make a panel for the speed slider */
        JPanel sliderPanel = new JPanel();

        speedSlider = new JSlider (0, 15, 10);
        speedSlider.setInverted(true);
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(false); // true/false
        speedSlider.addChangeListener (
            new ChangeListener () {
                public void stateChanged (ChangeEvent c) {
                    sleepTime = speedSlider.getValue() * 20;
                    System.out.println("Sleeptime: " + sleepTime + " ms");
                }
            }
        );

        // sliderPanel.add(new JLabel("Simulation speed:   "));
        sliderPanel.add(new JLabel("slow"));
        sliderPanel.add(speedSlider);
        sliderPanel.add(new JLabel("fast"));

        // add slider panel to control panel
        panel.add(sliderPanel); 

        return panel;
    }

    JPanel makeDisplayOptionPanel (){
        JPanel panel = new JPanel();

        /* creates a GridLayout with two rows and one column */
        panel.setLayout (new GridLayout (2,1));

        /* Set the border and give it a title */
        TitledBorder border = new TitledBorder("Display Options");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder (border);

        // Show health bar check button
        JCheckBox displayHealthCheckBox = new JCheckBox("Health",displayHealth);
        displayHealthCheckBox.addActionListener (
            new ActionListener () {
                public void actionPerformed (ActionEvent a)
                {
                    toggleDisplayHealth();
                }
            }
        );
        panel.add(displayHealthCheckBox);

        // Show axes check button
        JCheckBox displayAxesCheckBox = new JCheckBox("Axes",displayAxes);
        displayAxesCheckBox.addActionListener (
            new ActionListener () {
                public void actionPerformed (ActionEvent a)
                {
                    toggleDisplayAxes();
                }
            }
        );
        panel.add(displayAxesCheckBox);

        // Show sight radius check button
        JCheckBox displaySightRadiusCheckBox = new JCheckBox("Sight radius",displaySightRadius);
        displaySightRadiusCheckBox.addActionListener (
            new ActionListener () {
                public void actionPerformed (ActionEvent a)
                {
                    toggleDisplaySightRadius();
                }
            }
        );
        panel.add(displaySightRadiusCheckBox);
        JCheckBox displayOrganismIDCheckBox = new JCheckBox("Organism ID", displayOrganismID);
        displayOrganismIDCheckBox.addActionListener (
            new ActionListener() {
                public void actionPerformed (ActionEvent a) {
                    toggleDisplayOrganismID();
                }
            }
        );
        panel.add(displayOrganismIDCheckBox);

        return panel;
    }

    JPanel makeEntryPanel() {
        JPanel panel = new JPanel();

        /* Set the border and give it a title */
        TitledBorder border = new TitledBorder("Enter the number of organisms");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder (border);
        
        /* Labels and Entry fields for number of organisms */        
        // Plants
        JLabel plantLabel = new JLabel("Plants");
        panel.add(plantLabel);
        numPlantsField = new JTextField(5);
        numPlantsField.setText(Integer.toString(numPlants));
        panel.add(numPlantsField);

        // Mice
        JLabel mouseLabel = new JLabel("Mice");
        panel.add(mouseLabel);
        numMiceField = new JTextField(5);
        numMiceField.setText(Integer.toString(numMice)); 
        panel.add(numMiceField);

        return panel;
    }

    JPanel makeBottomPanel () {
    	JPanel panel = new JPanel ();

    	/* creates a GridLayout with one rows and one column */
    	panel.setLayout (new GridLayout (1,1));

    	/* creates a control panel with Reset, Go, and Quit buttons as well as slider bar for sleep time*/
    	JPanel sPanel = makeControlPanel ();
    	panel.add (sPanel);

        JPanel dPanel = makeDisplayOptionPanel ();
        panel.add (dPanel);

        /* creates a panel with Organism Entry Fields */
        JPanel ePanel = makeEntryPanel ();
        panel.add (ePanel);

        return panel;
    }

    void makeFrame () {
    	JFrame frame = new JFrame ();
    	frame.setSize (1100, 700);
        frame.setMinimumSize(new Dimension(1100, 700));
        frame.setMaximumSize(new Dimension(1100, 700));
    	frame.setTitle ("animalSimGUI");

    	//Obtains the content pane layer so we can add to it
    	//Container is part of Abstract Window Toolkit, used to create GUIs
    	cPane = frame.getContentPane();
    	cPane.add (makeBottomPanel(), BorderLayout.SOUTH);
    	cPane.add (this, BorderLayout.CENTER);
    	frame.setVisible (true);

        D = this.getSize();

        reset();
    }

    // Main
    public static void main(String[] args) {
    	animalSimGUI gui = new animalSimGUI();
    	gui.makeFrame();

    }
}
