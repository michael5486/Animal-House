
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class AnimalSimulator {

    // Constants set from constructor arguments:
    double delT;

    // Variables set from constructor arguments:
    Dimension D;
    int numPlants;
    int numMice;
    int numFoxes;
    int numRabbits;
    
    // Variables 
    double t = 0; // Current time step. Initialized to zero.
    int id = 1; // this will increment

    /* List of all organisms */
    ArrayList<Organism> organisms = new ArrayList<Organism>();
    /* 
    Note from Karl:
        We can also make lists of specific organisms.
        For example:
                ArrayList<Plant> plants = new ArrayList<Plant>();
        Then we can call specific methods from the Plant class that 
        are not defined in the organism interface.
    */


    /* Statics */
    Function plantPopulation = new Function("Plant population vs time");
    Function mousePopulation = new Function("Mouse population vs time");
    Function foxPopulation = new Function("Fox population vs time");
    Function rabbitPopulation = new Function("Rabbit population vs time");
    Function bearPopulation = new Function("Bear population vs time");

    // Constructor
    AnimalSimulator(Dimension D, double initDelT, int initNumPlants, int initNumMice, int initNumFoxes, int initNumRabbits){
        this.D = D;
        delT = initDelT;
        numPlants = initNumPlants;
        numMice = initNumMice;
        numFoxes = initNumFoxes;
        numRabbits = initNumRabbits;

        // create animals
        createAnimals();

        /* --------For testing purposes------- */
        // for(Organism o : organisms){
        // }
    }

// ~~~~~~~~~~~~~~~ Next Step Method ~~~~~~~~~~~~~~~~~~~~~
    public boolean nextStep (double delT) {
        /* Step 1 */ 
        updateHealth();

        /* Step 2 */
        updateState();

        /* Step 3 */
        moveOrganisms();
  

        // ~~~~~~~~~~ Don't touch this, or uncomment it ~~~~~~~~~~~~~
        // Update statistics 
        updatePopulationStatistics();

        if(getNumOrganismType("Mouse") == 0 && getNumOrganismType("Fox") == 0 && getNumOrganismType("Rabbit") == 0 && getNumOrganismType("Bear")){
            displayPopulationGraph();
            return true;
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

        // update time
        t = t + delT;
        return false;
    }


// ~~~~~~~~~~~~~~~ Update State Methods ~~~~~~~~~~~~~~~~~~~~~
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

    public void updateState() {
        //cycle through all organisms
        for (Organism o : organisms) {
            o.updateState(organisms);
        }
    }

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
        }
    }




//~~~~~~~~~ Methods for initializing AnimalSimulator instance ~~~~~~~~~~~~~~

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
        for (int i = 0; i < numRabbits; i++) { //bears
            organism.add(new Bear(id, createRandomPoint(), D));
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



// ~~~~~~~~~~~~~~~ Utility Methods ~~~~~~~~~~~~~~~~~~~~~

    /* Check to see if a point is within screen boundary */
    public boolean isPointWithinBoundary(Point2D.Double point){
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

    public void removeOrganism(Organism organism){
        for(Organism o : organisms){
            if(o.equals(organism)){
                organisms.remove(o);
            }
        }
    }
    public void removeOrganism(int id){
        for(Organism o : organisms){
            if(o.getID() == id){
                organisms.remove(o);
            }
        }
    }

    public ArrayList<Organism> getOrganismsType(String type) {
        ArrayList<Organism> temp = new ArrayList<Organism>();  
        for(Organism o : organisms){
            if(o.getType() == type){
                temp.add(o);
            }
        }
        return temp;
    }



// ~~~~~~~~~~~~~~~~~~ Statistics and Graph Methods ~~~~~~~~~~~~~~~~~~~~~

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
        plantPopulation.add(t,getNumOrganismType("Plant"));
        mousePopulation.add(t,getNumOrganismType("Mouse"));
        foxPopulation.add(t,getNumOrganismType("Fox"));
        rabbitPopulation.add(t, getNumOrganismType("Rabbit"));
        bearPopulation.add(t, getNumOrganismType("Bear"));
    }

    public void displayPopulationGraph(){
        // Need to fix this. 
        // If you close the graph window, then press 'Reset,' a new graph will not appear.

        Thread plotThread = new Thread(){
            public void run () {
                Function.show(plantPopulation,mousePopulation,foxPopulation, rabbitPopulation);
            }
        };
        plotThread.start();
    }
}








