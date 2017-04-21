
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


    // Initial numbers of organisms
    int numPlants = 10;
    int numMice = 20;

    /* Animation Options */
    boolean drawOrganismAxes = false;


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /* Global variables for the GUI and set up methods */
    int id = 1;
    double delT = 0.1;

    /* GUI Animation stuff */
    Thread currentThread;
    boolean isPaused = false;
    int sleepTime = 150;


    /* GUI Construction stuff */
    Container cPane = null;
    JTextField numPlantsField, numMiceField;
    JSlider speedSlider;
    Dimension D;

    /* List of all organisms */
    ArrayList<Organism> organisms = new ArrayList<Organism>();

    // Note from Karl:
    // We can also make lists of specific organisms.
    // For example:
    //      ArrayList<Plant> plants = new ArrayList<Plant>();
    // Then we can call specific methods from the Plant class that are not defined in the organism interface.



    public void createAnimals() { // called from reset()
    	System.out.println("  Animals created.");

        // Initialize the number of animals
        /* Clears all organisms */
        organisms.clear();

        for(int i = 0; i < numPlants; i++){  // plants
            // create plants
            organisms.add(new Plant(id, createRandomPoint()));
            id++;
        }

        for(int i = 0; i < numMice; i++){ // mice
            // create mice
            organisms.add(new Mouse(id, createRandomPoint())); //create method for random location to initialize!
            id++;
        }
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
        for(Organism o:organisms){
            int x = o.getX();
            int y = o.getY();

            // Draw the x-y axes of the organism.
            if(drawOrganismAxes){
                g.setColor(Color.BLACK);
                g.drawLine(x-20, y, x+20, y); // x-axis
                g.drawLine(x, y-20, x, y+20); // y-axis
            }
           
            // draw organism o
            if(o.getType() == "Plant"){
                drawPlant(x, y, g);
            }
            else if(o.getType() == "Mouse"){
                drawMouse(x, y, g);
            }
        }
    }

    void drawPlant(int x, int y, Graphics g){
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
    }

    void drawMouse(int x, int y, Graphics g){
        Color mouseColor = new Color(128,128,128);
        g.setColor(mouseColor);
        g.fillOval(x-5, y-8, 10, 16); // body

        g.setColor(Color.BLACK);
        g.fillArc(x-5, y-1, 5, 7, 15, 180); // left ear
        g.fillArc(x, y-1, 5, 7, 345, 180);  // right ear
        g.fillRect(x-1, y+4, 1, 1);         // left eye
        g.fillRect(x+1, y+4, 1, 1);         // right eye
        g.drawArc(x-8,y-10, 8, 6, 0, 180);  // tail
        g.drawLine(x-1, y+6, x-6, y+5);     // left top whisker
        g.drawLine(x-1, y+6, x-5, y+7);     // left bottom whisker
        g.drawLine(x+1, y+6, x+6, y+5);     // right top whisker
        g.drawLine(x+1, y+6, x+5, y+7);     // right bottom whisker
    }

    /* Creates a random cartesian point */
    Point2D.Double createRandomPoint() {
        int screenWidth = D.width;
        int screenHeight = D.height;

        /* Creates random x coordinate */
        // int cartX = RandTool.uniform(0, screenWidth);
        int cartX = (int)(Math.random()*screenWidth);

        /* Creates random y coordinate */
        // int javaY = RandTool.uniform(0, screenHeight);
        int javaY = (int)(Math.random()*screenHeight);
        int cartY = D.height - javaY;

        Point2D.Double randPoint = new Point2D.Double(cartX, cartY);

        return randPoint; 

        }

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


    void reset() {
        System.out.println("\nReset:");

        // Read values from bottom entry fields
        getNumOrganismsFromEntryField();

        // Create animals
        createAnimals();

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

    boolean nextStep() {

        for(Organism o : organisms){
            o.randomWalk();
        }

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

        panel.add(buttonPanel); // add button panel to control panel


        /* make a panel for the speed slider */
        JPanel sliderPanel = new JPanel();

        speedSlider = new JSlider (0, 20, 15);
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

        sliderPanel.add(new JLabel("slow"));
        sliderPanel.add(speedSlider);
        sliderPanel.add(new JLabel("fast"));

        panel.add(sliderPanel); // add slider panel to control panel

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

    	/* creates a GridLayout with two rows and one column */
    	panel.setLayout (new GridLayout (1,1));

    	/* creates a control panel with Reset, Go, and Quit buttons as well as slider bar for sleep time*/
    	JPanel sPanel = makeControlPanel ();
    	panel.add (sPanel);


        /* creates a panel with Organism Entry Fields */
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
    	frame.setVisible (true);

        D = this.getSize();

        reset();
    }

    public static void main(String[] args) {
    	animalSimGUI gui = new animalSimGUI();
    	gui.makeFrame();

    }
}
