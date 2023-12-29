package gremlins;
/**
 * Object is an abstract class that is the superclass of every object in the game. It can be anything, either be moving 
 * or not moving.
 */
public abstract class Object{
    /**
     * The location of the object on the x-axis. It should be an integer value. 
     */
    private int xpos;
    /**
     * The location of the object on the y-axis. It should be an integer value. 
     */
    private int ypos;
    /**
     * The basis of constructor for subclasses of this Object abstract class. 
     * @param xpos The location of the object on the x-axis. It should be an integer value.
     * @param ypos The location of the object on the y-axis. It should be an integer value.
     */
    public Object(int xpos, int ypos){
        this.xpos = xpos;
        this.ypos = ypos;
    }
    /**
     * Gets the location of the object on the x-axis.
     * @return position of the object on the x axis using pixels as scale.
     */
    public int getxpos(){
        return xpos;
    }
    /**
     * Gets the location of the object on the y-axis.
     * @return position of the object on the y axis using pixels as scale.
     */
    public int getypos(){
        return ypos;
    }
    /**
     * Sets a new integer value for the location of the object on the x-axis.
     * @param xpos New integer value that the user wants to set of the object for the location on the x-axis.
     */
    public void setxpos(int xpos){
        this.xpos = xpos;
    }
    /**
     * Sets a new integer value for the location of the object on the y-axis.
     * @param ypos New integer value that the user wants to set of the object for the location on the y-axis.
     */
    public void setypos(int ypos){
        this.ypos = ypos;
    }
}