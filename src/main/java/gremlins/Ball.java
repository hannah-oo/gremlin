package gremlins;
/**
 * Ball class is extended from abstract Icons class. It can be a fireball or slimeball. 
 */
public class Ball extends Icons{

    /**
     *  Stores whether the ball should be deleted from the game. 
     */
    private boolean delete = false;
    /**
     *  Creates a ball object. 
     * @param xpos position on the x-axis using pixels as scale. 
     * @param ypos position on the y-axis using pixels as scale. 
     */
    public Ball(int xpos, int ypos){
        super(xpos, ypos);
    }
    /**
     * Sets the delete value of the Ball object. 
     * @param state Desired state of the Ball.
     */
    public void setdelete(boolean state){
        this.delete = state;
    }
    /**
     * Gets the delete value of the Ball object.
     * @return delete variable of the Ball object. 
     */
    public boolean getdelete(){
        return this.delete;
    }
}