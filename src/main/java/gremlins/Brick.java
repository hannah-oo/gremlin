package gremlins;
/**
 * Brick class is extended from BlockTile class to represent the bricks in the game. The difference between it and other tiles
 * is that it has a delete property.
 */
public class Brick extends BlockTile{
    /**
     * Stores whether the brick should be deleted from the game. 
     */
    private boolean delete;
    /**
     * Stores the number of frames that starts adding after being hit by a fireball. 
     */
    private int frametiming = 0;

    /**
     * Creates a brick object. 
     * @param xpos position on the x-axis using pixels as scale. 
     * @param ypos position on the y-axis using pixels as scale. 
     */
    public Brick(int xpos, int ypos){
        super(xpos,ypos);
    }
    /**
     * Sets the delete value of the Brick object. 
     * @param state Desired state of the Brick.
     */
    public void setdelete(boolean state){
        this.delete = state;
    }
    /**
     * Gets the delete value of the Brick object.
     * @return delete variable of the Brick object. 
     */
    public boolean getdelete(){
        return this.delete;
    }
    /**
     * Add 1 to the frametiming every time it is called. It happens when the delete is true and it is being called in game app class.
     */
    public void addframe(){
        frametiming++;
    }
    /**
     * Gets the frametiming of the Brick object.
     * @return frametiming of the Brick object.
     */
    public int getframe(){
        return frametiming;
    }
}