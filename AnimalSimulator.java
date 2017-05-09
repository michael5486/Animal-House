
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class AnimalSimulator {

    static final int maxT = 1000;

    // Constants set from constructor arguments:
    Dimension D;
    boolean gui;

    // Variables set from constructor arguments:
    int numPlants;
    int numMice;
    int numFoxes;
    int numRabbits;
    int numBears;
    
    // Variables 
    int t = 0; // Current time step. Initialized to zero.
    int id = 1; // this will increment

    /* List of all organisms */
    ArrayList<Organism> organisms = new ArrayList<Organism>();

    /* Statics */
    Function plantPopulation = new Function("Plant population vs time");
    Function mousePopulation = new Function("Mouse population vs time");
    Function foxPopulation = new Function("Fox population vs time");
    Function rabbitPopulation = new Function("Rabbit population vs time");
    Function bearPopulation = new Function("Bear population vs time");


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    AnimalSimulator(Dimension D, boolean gui, int initNumPlants, int initNumMice, int initNumFoxes, int initNumRabbits, int initNumBears){
        this.D = D;
        this.gui = gui;
        numPlants = initNumPlants;
        numMice = initNumMice;
        numFoxes = initNumFoxes;
        numRabbits = initNumRabbits;
        numBears = initNumBears;

        // create animals
        createAnimals();
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~ Next Step Method ~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public boolean nextStep () {
        /* Step 1 */ 
        updateHealth();

        /* Step 2 */
        reproduce();

        /* Step 3 */
        updateState();

        /* Step 4 */
        moveOrganisms();
  

        // Update statistics 
        updatePopulationStatistics();


        // Quit
        if(t > maxT){
            if(gui){
                 displayPopulationGraph();
            }
            return true;
        }
        /* Uncomment below and comment above to run until extinction */
        // if(getNumOrganismType("Mouse") == 0 && getNumOrganismType("Fox") == 0 && getNumOrganismType("Rabbit") == 0 && getNumOrganismType("Bear") == 0){
        //     if(gui){
        //          displayPopulationGraph();
        //     }
        //     return true;
        // }
        
        // Update time step
        t++;
        return false;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~ Methods called in nextStep() ~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void updateHealth(){
        // Every animal's health is always updated with time. Regardless of any state that it is in.

        // create empty list to fill up with dead organisms. This avoids concurrentModificationException
        ArrayList<Organism> dead = new ArrayList<Organism>(); 

        // Cycle through all organisms
        for(Organism o : organisms){
            o.updateHealthTime(); // update health
           
            // if dead
            if(o.getHealth() <= 0){
                dead.add(o); // add to list of dead
            }
        }
        // remove dead organisms
        for(Organism o : dead){
            organisms.remove(o);
            if(o.getType() == "Plant"){
                numPlants--;
            }
            else if(o.getType() == "Mouse"){
                numMice--;
            }
            else if(o.getType() == "Rabbit"){
                numRabbits--;
            }
            else if(o.getType() == "Fox"){
                numFoxes--;
            }
            else if(o.getType() == "Bear"){
                numBears--;
            }
        }
    }
    public void updateState() {
        //cycle through all organisms
        for (Organism o : organisms) {
            o.updateState(organisms);
        }
    }
    public void moveOrganisms(){
        // Cycle through all organisms
        for(Organism o : organisms){
            // initialize newLocation point that is not within boundary
            Point2D.Double newLocation = new Point2D.Double(-1,-1); 
            
            // loop until generated point is within boundary
            while(!isPointWithinBoundary(newLocation)){
                newLocation = o.move(organisms);
            }
            o.setXY(newLocation);
        }
    }
    public void reproduce() {
        ArrayList<Organism> temp = new ArrayList<Organism>();
        for (Organism o : organisms) {
            //if organism is giving birth
            if (o.isGivingBirth()) {
                int numBabies = o.getNumBabiesProduced();
                // System.out.printf("%s made %d babies!\n", o.getType(), numBabies);
                for (int i = 0; i < numBabies; i++) {
                    if (o.getType() == "Plant") {
                        temp.add(new Plant(id, createRandomPoint(), D));
                        numPlants++;
                        id++;
                    }
                    else if (o.getType() == "Mouse") {
                        temp.add(new Mouse(id, o.getXY(), D));
                        numMice++;
                        id++;                    }
                    else if (o.getType() == "Rabbit") {
                        temp.add(new Rabbit(id, o.getXY(), D));
                        numRabbits++;
                        id++;
                    }
                    else if (o.getType() == "Fox") {
                        temp.add(new Fox(id, o.getXY(), D));
                        numFoxes++;
                        id++;
                    }
                    else if (o.getType() == "Bear") {
                        temp.add(new Bear(id, o.getXY(), D));
                        numBears++;
                        id++;
                    }
                }
            }
        }
        for (Organism o : temp) {
            organisms.add(o);
        }
    }






// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~ Methods for initializing AnimalSimulator instance ~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void createAnimals() { // called from reset()
        // System.out.println("  Animals created.");

        // Initialize the number of animals
        /* Clears all organisms */
        organisms.clear();

        for(int i = 0; i < numPlants; i++){  // plants
            // create plants
            organisms.add(new Plant(id, createRandomPoint(), D));
            id++;
        }
        for(int i = 0; i < numMice; i++){ // mice
            // create mice
            organisms.add(new Mouse(id, createRandomPoint(), D));
            id++;
        }
        for (int i = 0; i < numFoxes; i++) { // foxes
            //create foxes
            organisms.add(new Fox(id, createRandomPoint(), D));
            id++;
        }
        for (int i = 0; i < numRabbits; i++) { //rabbits
            organisms.add(new Rabbit(id, createRandomPoint(), D));
            id++;
        }
        for (int i = 0; i < numBears; i++) { //bears
            organisms.add(new Bear(id, createRandomPoint(), D));
            id++;
        }
    }

    /* Creates a random cartesian point */
    public Point2D.Double createRandomPoint() {
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





// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~ Utility Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public boolean isPointWithinBoundary(Point2D.Double point){
         /* Check to see if a point is within screen boundary */
        int x = (int)point.x;
        int y = (int)point.y;
        int screenWidth = D.width;
        int screenHeight = D.height;

        if(x >= 0 && y >= 0 && x < screenWidth && y < screenHeight){
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Organism> getOrganisms(){
        return organisms;
    }



// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~ Statistics and Graph Methods ~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public int getNumOrganismType(String type){
        int population = 0;
        for(Organism o : organisms){
            if(o.getType() == type){
                population++;
            }
        }
        return population;
    }

    public void updatePopulationStatistics(){
        if(numPlants > 0){
            plantPopulation.add(t,numPlants);
        }
        if(numMice > 0){
            mousePopulation.add(t,numMice);
        }
        if(numFoxes > 0){
            foxPopulation.add(t,numFoxes);
        }
        if(numRabbits > 0){
            rabbitPopulation.add(t,numRabbits);
        }
        if(numBears > 0){
            bearPopulation.add(t,numBears);
        }
        
        // the code below is a bug fix. just leave it.
        if(t == 0){
            if(numPlants == 0){
                plantPopulation.add(t,1);
            }
            if(numMice == 0){
                mousePopulation.add(t,1);
            }
            if(numFoxes == 0){
                foxPopulation.add(t,1);
            }
            if(numRabbits == 0){
                rabbitPopulation.add(t,1);
            }
            if(numBears == 0){
                bearPopulation.add(t,1);
            } 
        }
    }

    public void displayPopulationGraph(){
        // Need to fix this. 
        // If you close the graph window, then press 'Reset,' a new graph will not appear.

        Thread plotThread = new Thread(){
            public void run () {
                Function.show(plantPopulation, mousePopulation, foxPopulation, rabbitPopulation, bearPopulation);
            }
        };
        plotThread.start();
    }

    public static void main(String[] args) throws InterruptedException{
        Dimension d = new Dimension(1100, 558); // gui window size


        int numTrials = 100;

        /* Statics */
        Function avgPlantPopulation = new Function("Average Plant population vs time");
        Function avgMousePopulation = new Function("Average Mouse population vs time");
        Function avgFoxPopulation = new Function("Average Fox population vs time");
        Function avgRabbitPopulation = new Function("Average Rabbit population vs time");
        Function avgBearPopulation = new Function("Average Bear population vs time");
        double[] totalPlant = new double[maxT];
        double[] totalMouse = new double[maxT];
        double[] totalFox = new double[maxT];
        double[] totalRabbit = new double[maxT];
        double[] totalBear = new double[maxT];

        AnimalSimulator a = null;

        /* No Threads */
        for(int i = 1; i <= numTrials; i++){
            System.out.println("Trial: "+i+"/"+numTrials);
            // create new simulation
            a = new AnimalSimulator(d, false, 40, 30, 6, 10, 1);
            while(!a.nextStep()){
                // hello world
            }

            for(int x = 0; x < maxT; x++){
                totalPlant[x] += a.plantPopulation.get(x);
                totalMouse[x] += a.mousePopulation.get(x);
                totalFox[x] += a.foxPopulation.get(x);
                totalRabbit[x] += a.rabbitPopulation.get(x);
                totalBear[x] += a.bearPopulation.get(x);
            }
            a = null;
        }

        /* Running in threads 
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(numTrials);
        for(int i = 1; i <= numTrials; i++){
            // System.out.println("Trial: "+i);
            Thread thread = new Thread(){
                public void run () {
                    // create new simulation
                    AnimalSimulator a = new AnimalSimulator(d, false, 40, 30, 6, 10, 1);
                    while(!a.nextStep()){
                        // hello world
                    }
                    for(int x = 0; x < maxT; x++){
                        totalPlant[x] += a.plantPopulation.get(x);
                        totalMouse[x] += a.mousePopulation.get(x);
                        totalFox[x] += a.foxPopulation.get(x);
                        totalRabbit[x] += a.rabbitPopulation.get(x);
                        totalBear[x] += a.bearPopulation.get(x);
                    }
                    // System.out.println("latch countdown");
                    latch.countDown();
                }
            };
            thread.start();
            
        }
        latch.await(); // Wait for countdown
        /* end Thread section */

        // find averages
        for(int x = 0; x < maxT; x++){
            avgPlantPopulation.add(x, totalPlant[x]/numTrials);
            avgMousePopulation.add(x, totalMouse[x]/numTrials);
            avgFoxPopulation.add(x, totalFox[x]/numTrials);
            avgRabbitPopulation.add(x, totalRabbit[x]/numTrials);
            avgBearPopulation.add(x, totalBear[x]/numTrials);
        }

        Function.show(avgPlantPopulation, avgMousePopulation, avgFoxPopulation, avgRabbitPopulation, avgBearPopulation);

        
    }
}








