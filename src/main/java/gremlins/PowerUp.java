package gremlins;

import java.util.Random;
/**
 * PowerUp class is extended from BlockTile class to represent the powerup in the game. The sprite used is a drawing 
 * of an wine alcohol bottle. It causes the wizard to speed up with the power of alcohol. Because of it, it is causes
 * the wizard to skip a few steps like a drunkard when the wizard is using it. 
 */
public class PowerUp extends BlockTile{
    /**
     * Tracks whether the powerup is in cooldown mode. When true, the powerup is visible in the game and powerup is activated. 
     */
    private boolean powerupstate = false ;
     /**
     * Resets in random frame every time after the power up is used. 
     */
    private int powerupsetperiod;
    /**
     * Tracks whether the power up is being used.
     */
    private boolean powerisbeingused = false;
    /**
     * Initialises a Random object so that random values could be set later in the program.
     */
    public Random rand = new Random();
    
    /**
     * Creates a PowerUp object. 
     * @param xpos position on the x-axis using pixels as scale. 
     * @param ypos position on the y-axis using pixels as scale. 
     */
    public PowerUp(int xpos, int ypos){
        super(xpos,ypos);
    }
    /**
     * Sets the powerup state to true or false. If true, the powerup is activated. 
     * @param state power up state to be set.
     */
    public void setpowerupstate(boolean state){
        this.powerupstate = state;
    }
    /**
     * Sets whether the powerup is being used. 
     * @param state powerup is being used state.
     */
    public void setpowerisbeingused(boolean state){
        this.powerisbeingused = state;
    }
    /**
     * Sets the powerup cooldown period to a random variable. It can be from 180 to 480,
     * meaning the power up cooldown period is between 3 to 6 seconds. 
     */
    public void setnewpowerupperiod(){
       this.powerupsetperiod = rand.nextInt(300) + 180;
    }
    /**
     * Gets the state of whether the powerup is activated.
     * @return State of the power up.
     */
    public boolean getpowerupstate(){
        return powerupstate;
    }
    /**
     * Gets the state of whether the powerup is currently being used. 
     * @return the state of whether the playing is used powerup. 
     */
    public boolean getpowerisbeingused(){
        return powerisbeingused;
    }
    /**
     * Changes the speed of the wizard according to how long it has been since power up started being used. 
     * @param wizard player icon
     * @param poweruptimer integer value that is being added up every time the draw loop is called.
     */
    public void powerupspeedmodify(Wizard wizard,int poweruptimer){
        // Before the poweruptimer is almost 10 seconds, set the wizard speed to twice from usual. 
        if (poweruptimer < 600){
            wizard.setspeed(2);  
        }
        // Set the speed back to usual. Set the power cooldown period to a new value. 
        else{
            wizard.setspeed(1);
            setnewpowerupperiod();
        }
    }
    /**
     * Gets the current powerup cooldown period. 
     * @return number of frames the game has to loop through until the powerup is activated
     */
    public int getpowerupperiod(){
        return this.powerupsetperiod;
    }
}
   