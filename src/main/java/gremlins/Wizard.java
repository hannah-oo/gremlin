package gremlins;
/**
 * Wizard class is extended from the abstract Icons class, which is an abstract class of Object class.
 */
public class Wizard extends Icons{
/**
 * The Wizard class is created based on the location on the map using the pixels as scale. 
 * @param xpos The location of the Wizard on the x-axis. It should be an integer value.
 * @param ypos The location of the Wizard on the y-axis. It should be an integer value.
 */
    public Wizard(int xpos, int ypos){
        super(xpos,ypos);
    }
}