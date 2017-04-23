
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
    
    // Variables 
    double t = 0; // Current time step. Initialized to zero.
    int id = 1; // this will increment

    /* List of all organisms */
    ArrayList<Organism> organisms = new ArrayList<Organism>();

    // Note from Karl:
    // We can also make lists of specific organisms.
    // For example:
    //      ArrayList<Plant> plants = new ArrayList<Plant>();
    // Then we can call specific methods from the Plant class that are not defined in the organism interface.



    // Constructor

    AnimalSimulator(Dimension D, double initDelT, int initNumPlants, int initNumMice){
        this.D = D;
        delT = initDelT;
        numPlants = initNumPlants;
        numMice = initNumMice;

        // create animals
        createAnimals();

        // For testing purposes
        // for(Organism o : organisms){
        //     if(o.getType() == "Plant"){
        //         Plant p = (Plant)o;
        //         p.whatIAm();
        //     }
        // }
    }


    public void nextStep (double delT) {
        for(Organism o : organisms){
            
            // initialize newLocation point that is not within boundary
            Point2D.Double newLocation = new Point2D.Double(-1,-1); 
            //o.getNearbyPreyIDs(organisms);
            
            // loop until generated point is within boundary
            while(!isPointWithinBoundary(newLocation)){
                newLocation = o.randomWalk();
            }
            o.setXY(newLocation);
        }


        t = t + delT; // we don't use this yet. We will use it when we graph a function of time with organism populations.
    }










//~~~~~~~~~ Methods for initializing AnimalSimulator instance ~~~~~~~~~~~~~~

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
            organisms.add(new Mouse(id, createRandomPoint()));
            id++;
        }
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



// ~~~~~ Utility Methods ~~~~~~~~~~~~~~~~~~~~~`

    /* Check to see if a point is within screen boundary */
    boolean isPointWithinBoundary(Point2D.Double point){
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





}








