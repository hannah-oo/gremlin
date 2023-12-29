package gremlins;
/**
 * Icons is an abstract class for all the moving objects in the game app. It includes wizard,
 * gremlins, fireballs, and slimeballs.
 */
public abstract class Icons extends Object{
    /**
     * The location of the object on the x-axis in pixels. It should be an integer value. 
     */
    private int xpos;
    /**
     * The location of the object on the y-axis in pixels. It should be an integer value. 
     */
    private int ypos;
    /**
     * Stores how much the position on the x-axis of the icon should be changed. 
     */
    private int changex = 0;
    /**
     * Stores how much the position on the y-axis of the icon should be changed.
     */
    private int changey = 0;
    /**
     * Stores how much steps to be taken to be on next tile. 
     */
    private int steps = 0;
    /**
     * Speed of the icon. Used solely for powerup function. 
     */
    private int speed = 1;
    /**
     * Direction of the icon.
     */
    private char dir = 'r';

    /**
     * Abstract Class hence cannot be instantiated. Takes in the x-postion and y-postion of the icon. 
     * @param xpos The location of the object on the x-axis. It should be an integer value.
     * @param ypos The location of the object on the y-axis. It should be an integer value.
     */
    public Icons(int xpos, int ypos){
        super(xpos,ypos);
    }
    public int getxpos(){
        return xpos;
    }
    public int getypos(){
        return ypos;
    }
    public void setxpos(int x){
        this.xpos = x;
    }
    public void setypos(int y){
        this.ypos = y;
    }
    /**
     * Gets the direction of the icon. 
     * @return Direction of the icon.
     */
    public char getdir(){
        return dir;
    }
    /**
     * Sets the direction of the icon.
     * @param dir new direction of the icon.
     */
    public void setdir(char dir){
        this.dir = dir;
    }
    /**
     * Sets how much the icon is moving on the x-axis. 
     * @param xvel number of pixels the icon is moving on x-axis.
     */
    public void changexvel(int xvel){
        this.changex = xvel*speed;
    }
    /**
     * Sets how much the icon is moving on the y-axis. 
     * @param yvel number of pixels the icon is moving on y-axis.
     */
    public void changeyvel(int yvel){
        this.changey = yvel*speed;
    } 
    /**
     * Sets the speed of the icon.
     * @param speed new speed of the icon.
     */
    public void setspeed(int speed){
        this.speed = speed;
    }
    /**
     * Moves the icon to the direction and how much the icon will move. The icon doesn't need to have 
     * positive/negative variables.
     * @param dir new direction of the icon.
     * @param iconspeed how much the icon wants to move.
     */
    public void move(char dir, int iconspeed){
        this.dir = dir;
        if (dir == 'l'){
            setxpos(getxpos() - iconspeed);
        }
        else if (dir == 'r'){
            setxpos(getxpos() + iconspeed);
        }
        else if (dir == 'u'){
            setypos(getypos() - iconspeed);
        }
        else if (dir == 'd'){
            setypos(getypos() + iconspeed);
        } 
    }
    /**
     * Stops the icon depending on where the icon stands. If the icon is 
     * not the the tile, move it to the next tile. 
     * @param dir direction of the icon.
     * @return number of steps the icon needs to be taken to get to next point.
     */
    public int stop(char dir){ 
        // get the quotient when the position is divided by 20.
        int xarr = this.getxpos() / App.SPRITESIZE;
        int yarr = this.getypos() / App.SPRITESIZE;
        // calculate the distance from one whole tile. 
        int disx = this.getxpos()  - (xarr*App.SPRITESIZE);
        int disy = this.getypos()  - (yarr*App.SPRITESIZE);
        if (disx > 0){
            if (dir == 'l'){ 
                //steps = (10-disx)/2;
                setxpos((xarr)*App.SPRITESIZE);
            }
            else if (dir == 'r'){
             //   steps = (10-disx/2);
                setxpos((xarr+1)*App.SPRITESIZE);
            }
        }
        if (disy > 0){
            if (dir == 'u'){ 
             //   steps = ((10-disy)/2);
                setypos((yarr)*App.SPRITESIZE);
        }
            else if (dir == 'd'){
              //  steps = (10-disy)/2;
                setypos((yarr+1)*App.SPRITESIZE);
            }
        }
        // sets the number of changes in both axies to 0.
        refreshvel();
        return steps;
    }
    /**
     * Checks if one moving Icon and one unmovable BlockTile collides. If it does, it will center 
     * the moving Icon to not make the two sprites overlap.
     * @param moving icon that will collide into the tile.
     * @param notmoving tile that is to be collided by the icon.
     * @return true if it collides and false if it doesn't collide. 
     */
    public boolean collision(Icons moving, BlockTile notmoving){
        // get the distance between two objects
        int distancex = (moving.getxpos()) - (notmoving.getxpos());
        int distancey = (moving.getypos()) - (notmoving.getypos());
        // if they are colliding on x-axis
        if (Math.abs(distancex) <20){
            // if they are colliding on y-axis
            if (Math.abs(distancey) <20){
                // get the remaining distance
                int remainx = App.SPRITESIZE - Math.abs(distancex);
                int remainy = App.SPRITESIZE - Math.abs(distancey);
                // set it to appropriate position so that they won't overlap
                if (moving.getdir() == 'l'){
                    moving.setxpos(moving.getxpos()+ remainx);
                }
                else if (moving.getdir() == 'r'){
                    moving.setxpos(moving.getxpos()- remainx);
                }
                else if (moving.getdir() == 'u'){
                    moving.setypos(moving.getypos()+ remainy);
                } 
                else if (moving.getdir() == 'd'){
                    moving.setypos(moving.getypos()-remainy);
                }
                this.refreshvel();
                return true;
            }   
        }
        return false;
    }
    /**
     * Checks if two moving icons collides. Icons can be wizard, gremlins, fireballs, or slimeballs.
     * @param moving1 first icon that is moving. 
     * @param moving2 second icon that is moving.
     * @return true if it collides and false if it doesn't collide. 
     */
    public boolean collisionlogicfor2moving(Icons moving1, Icons moving2){
        int distancex = (moving1.getxpos()+10) - (moving2.getxpos()+10);
        int distancey = (moving1.getypos()+10) - (moving2.getypos()+10);
        if (Math.abs(distancex) <20){
            if (Math.abs(distancey) <20){
                return true;
            } return false;
        } return false;
    }
    /**
     * Updates the location of the icon according to how much it should be moved.
     * which is stored when the keys are clicked.
     */
    public void updateicon(){
        this.xpos += this.changex;
        this.ypos += this.changey;
    }
    /**
     * Updates how much the icon needs to be moved to 0 so icon stops moving. 
     */
    public void refreshvel(){
        this.changex = 0;
        this.changey = 0;
    }
}