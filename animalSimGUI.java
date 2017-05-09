
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
    int numPlants = 40;
    int numMice = 30;
    int numFoxes = 6;
    int numRabbits = 10;
    int numBears = 1;

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
    JTextField numPlantsField, numMiceField, numFoxesField, numRabbitsField, numBearsField;
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

        // Get numFoxes from entry field
        try{
            numFoxes = Integer.parseInt(numFoxesField.getText());
            if(numFoxes < 0){
                numFoxes = 0;
                numFoxesField.setText("0");
                System.out.println("--Error: numFoxes cannot be less than 0! Defaulting to 0.");
            }
        }
        catch(NumberFormatException e){
            System.out.println("--Error: Entry fields must have integer values! Defaulting numFoxes to 0.");
            numMice = 0;
            numMiceField.setText("0");
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
        // Get numBears from entry field
        try{
            numBears = Integer.parseInt(numBearsField.getText());
            if(numBears < 0){
                numBears = 0;
                numBearsField.setText("0");
                System.out.println("--Error: numBears cannot be less than 0! Defaulting to 0.");
            }
        }
        catch(NumberFormatException e){
            System.out.println("--Error: Entry fields must have integer values! Defaulting numBears to 0.");
            numBears = 0;
            numBearsField.setText("0");
        }        

        System.out.printf("  numPlants = %d\n  numMice  = %d  \n  numFoxes  = %d\n  numRabbits  = %d\n  numBears = %d\n", numPlants, numMice, numFoxes, numRabbits, numBears);
    }

    boolean nextStep() {
        return animalSimulator.nextStep(); // done = true
    }

    void reset() {
        System.out.println("\nReset:");

        // Read values from bottom entry fields
        getNumOrganismsFromEntryField();

        // Initialize animal simulator
        animalSimulator = new AnimalSimulator(D, true, delT, numPlants, numMice, numFoxes, numRabbits, numBears);

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
            try{
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
                   
                    //draws each organism - this gives concurrent modification exception!
                    if(o.getType() == "Plant"){
                        drawPlant(o, g);
                    }
                    else if(o.getType() == "Mouse"){
                        drawMouse(o, g);
                    }
                    else if (o.getType() == "Fox") {
                        drawFox(o, g);
                    }
                    else if (o.getType() == "Rabbit") {
                        drawRabbit(o, g);
                    }
                    else if (o.getType() == "Bear") {
                        drawBear(o, g);
                    }
                }
            }
            catch(ConcurrentModificationException e){

            }
        }
    }

    public void drawMouse(Organism o, Graphics g){
        int x = o.getX();
        int y = o.getY();
        double health = o.getHealth();
        double maxHealth = o.getMaxHealth();
        int radius = 8;

        Color mouseColor = new Color(128,128,128);
        g.setColor(mouseColor);
        g.fillOval(x-5, y-8, 10, 16); // body

        g.setColor(new Color(110,110,110)); // ear color
        g.fillArc(x-5, y-1, 5, 7, 15, 180); // left ear
        g.fillArc(x, y-1, 5, 7, 345, 180);  // right ear
        g.setColor(Color.BLACK);            // detail color
        g.fillRect(x-1, y+4, 1, 1);         // left eye
        g.fillRect(x+1, y+4, 1, 1);         // right eye
        g.drawArc(x-8,y-10, 8, 6, 0, 180);  // tail
        g.drawLine(x-1, y+6, x-6, y+5);     // left top whisker
        g.drawLine(x-1, y+6, x-5, y+7);     // left bottom whisker
        g.drawLine(x+1, y+6, x+6, y+5);     // right top whisker
        g.drawLine(x+1, y+6, x+5, y+7);     // right bottom whisker

        if(displayHealth){
            // health bar
            g.setColor(Color.RED);
            double healthBarValue = health*radius*2/maxHealth;
            g.fillRect(x-radius, y-radius-4, (int)Math.floor(healthBarValue), 1);

            // health bar edges
            g.setColor(Color.BLACK);
            g.fillRect(x-radius, y-radius-1-4, 1, 3);
            g.fillRect(x+radius, y-radius-1-4, 1, 3);
        }
        if (displayOrganismID) {
            //Organism ID
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(o.getID()), x-radius, y+19);
        }
    }

    public void drawPlant(Organism o, Graphics g){
        int x = o.getX();
        int y = o.getY();
        double health = o.getHealth();
        double maxHealth = o.getMaxHealth();
        int radius = 20;

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
            double healthBarValue = health*radius*2/maxHealth;
            g.fillRect(x-radius, y-radius-10, (int)Math.floor(healthBarValue), 1);

            // health bar edges
            g.setColor(Color.BLACK);
            g.fillRect(x-radius, y-radius-1-10, 1, 3);
            g.fillRect(x+radius, y-radius-1-10, 1, 3);
        }
        if (displayOrganismID) {
            //Organism ID
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(o.getID()), x-8, y+12);
        }
    }

    public void drawFox(Organism o, Graphics g) {
        int x = o.getX();
        int y = o.getY();
        double health = o.getHealth();
        double maxHealth = o.getMaxHealth();
        int radius = 15;

        try {
            BufferedImage open = ImageIO.read(new File("Fox.png"));
            g.drawImage(open, x-radius, y-radius, radius*2, radius*2, null);

        }
        catch (IOException e) {
                System.out.println("Fox.png not found");
        }

        if(displayHealth){
            // health bar
            g.setColor(Color.RED);
            double healthBarValue = health*radius*2/maxHealth;
            g.fillRect(x-radius, y-radius, (int)Math.floor(healthBarValue), 1);

            // health bar edges
            g.setColor(Color.BLACK);
            g.fillRect(x-radius, y-radius-1, 1, 3);
            g.fillRect(x+radius, y-radius-1, 1, 3);
        }
        if (displayOrganismID) {
            //Organism ID
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(o.getID()), x-8, y+21);
        }
    }

    public void drawRabbit(Organism o, Graphics g) {
        int x = o.getX();
        int y = o.getY();
        double health = o.getHealth();
        double maxHealth = o.getMaxHealth();
        int radius = 15;

        try {
            BufferedImage open = ImageIO.read(new File("Rabbit.png"));
            g.drawImage(open, x-radius, y-radius, radius*2, radius*2, null);

        }
        catch (IOException e) {
                System.out.println("Rabbit.png not found");
        }

        if(displayHealth){
            // health bar
            g.setColor(Color.RED);
            double healthBarValue = health*radius*2/maxHealth;
            g.fillRect(x-radius, y-radius, (int)Math.floor(healthBarValue), 1);

            // health bar edges
            g.setColor(Color.BLACK);
            g.fillRect(x-radius, y-radius-1, 1, 3);
            g.fillRect(x+radius, y-radius-1, 1, 3);
        }
        if (displayOrganismID) {
            //Organism ID
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(o.getID()), x-6, y+25);
        }        
    }

    public void drawBear(Organism o, Graphics g) {
        int x = o.getX();
        int y = o.getY();
        double health = o.getHealth();
        double maxHealth = o.getMaxHealth();
        int radius = 20;

        try {
            BufferedImage open = ImageIO.read(new File("Bear.png"));
            g.drawImage(open, x-radius, y-radius, radius*2, radius*2, null);

        }
        catch (IOException e) {
                System.out.println("Bear.png not found");
        }

        if(displayHealth){
            // health bar
            g.setColor(Color.RED);
            double healthBarValue = health*radius*2/maxHealth;
            g.fillRect(x-radius, y-radius, (int)Math.floor(healthBarValue), 1);

            // health bar edges
            g.setColor(Color.BLACK);
            g.fillRect(x-radius, y-radius-1, 1, 3);
            g.fillRect(x+radius, y-radius-1, 1, 3);
        }
        if (displayOrganismID) {
            //Organism ID
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(o.getID()), x-8, y+28);
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

        speedSlider = new JSlider (0, 15, 8);
        speedSlider.setInverted(true);
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(false); // true/false
        speedSlider.addChangeListener (
            new ChangeListener () {
                public void stateChanged (ChangeEvent c) {
                    sleepTime = speedSlider.getValue() * 20;
                    // System.out.println("Sleeptime: " + sleepTime + " ms");
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

        JLabel foxLabel = new JLabel("Foxes");
        panel.add(foxLabel);
        numFoxesField = new JTextField(5);
        numFoxesField.setText(Integer.toString(numFoxes));
        panel.add(numFoxesField);

        JLabel rabbitLabel = new JLabel("Rabbits");
        panel.add(rabbitLabel);
        numRabbitsField = new JTextField(5);
        numRabbitsField.setText(Integer.toString(numRabbits));
        panel.add(numRabbitsField);

        JLabel bearLabel = new JLabel("Bears");
        panel.add(bearLabel);
        numBearsField = new JTextField(5);
        numBearsField.setText(Integer.toString(numBears));
        panel.add(numBearsField);

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
    	frame.setTitle ("animalSimGUI")
;
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
