package gremlins;

import java.util.Random;
import java.util.ArrayList;
/**
 * Gremlin Class is extended from abstract Icons class. The special properties of this class is 
 * changing the direction randomly and shifting the gremlin to another place. 
 */
public class Gremlin extends Icons{
    /**
     * Initialises a Random object so that random values could be set later in the program.
     */
    private Random rand = new Random();
     /**
     * State of the gremlin, whether it should be shifted to another tile. 
     */
    private boolean shifttrigger = false;

    /**
     * Creates a new gremlin. It needs x and y positions to instantiated. 
     * @param xpos position on the x-axis using pixels as scale. 
     * @param ypos position on the y-axis using pixels as scale. 
     */
    public Gremlin(int xpos, int ypos){
        super(xpos,ypos);
    }
    /**
     * Sends the gremlin to another position. It also checks if it is on the blocktile. 
     * @param wizard Needs the location of the player to make sure gremlin is at least 10 tiles away. 
     * @param block To check if the gremlin is on a blocktile.
     */
    public void shift(Wizard wizard,BlockTile block){
        int min = 40;
        int max = 600;
        // if the gremlin needs to be sent to another position
        while(this.getshift()== true){
            int randomx = (int)Math.floor(Math.random()*(max-min+1)+min);
            int randomy = (int)Math.floor(Math.random()*(max-min+1)+min);
            int gremdix = Math.abs(randomx - wizard.getxpos());
            int gremdiy = Math.abs(randomy - wizard.getypos());
            // if the new location is at least 10 tiles away, continue
            if (gremdix > 200 && gremdiy >200){
                int quotientx = randomx / 20;
                randomx = quotientx*20;
                int quotienty = randomy / 20;
                randomy = quotienty*20;
                this.setxpos(randomx);
                this.setypos(randomy);
                // if the new location is not on the block, finish this loop. New location is setted.
                if (!this.collision(this, block)){ 
                    if (block.getxpos() != this.getxpos() && block.getypos() != this.getypos()){
                        this.setshift(false);
                        return;
                    }
                } 
                // Keeps doing the loop if conditions are unfulfilled.
            }else this.setshift(true);
        }
    }

    /**
     * Change the state of whether the gremlin should be shifted. 
     * @param state new state of gremlin shifttrigger.
     */
    public void setshift(boolean state){
        this.shifttrigger = state;
    }
    /**
     * Gets the state of whether the gremlin should be shifted.
     * @return state of whether gremlin should be shifted.
     */
    public boolean getshift(){
        return this.shifttrigger;
    }
    /**
     * Converts the character direction to a number.
     * @param dir char direction.
     * @return an int number that represents the direction.
     */
    public int convertdir(char dir){
        if (dir == 'l' ) return 0;
        else if (dir == 'r') return 1;
        else if (dir == 'u') return 2;
        else if (dir == 'd') return 3;
        return -1;
    }
    /**
     * Converts the number assigned direction to its representative character.
     * @param num int number that represents the direction.
     * @return char direction.
     */
    public char convertnum(int num){
        if (num == 0) return 'l';
        else if (num == 1) return 'r';
        else if (num == 2) return 'u';
        else if (num == 3) return 'd';
        return 'e';
    }
    /**
     * Chooses a random direction for the gremlin to go to.
     * @return char direction
     */
    public char startdir(){
        int random = rand.nextInt(4);
       // this.dir = convertnum(random);
        return convertnum(random);
    }
    /**
     * Returns a opposite direction of the direction inputted.
     * @param direction current direction.
     * @return the opposite of the current direction.
     */
    public char goback(char direction){
        if (direction == 'l') return 'r';
        else if (direction == 'r') return 'l';
        else if (direction == 'u') return 'd';
        else if (direction == 'd') return 'u';
        return 'e';
    }
    /**
     * Chooses a new random direction for the gremlin object. 
     * @param moving gremlin object.
     * @param notmoving BlockTile that the gremlin is currently colliding.
     * @return direction the gremlin should go to.
     */
    public char choosedir(Gremlin moving, BlockTile notmoving){
        // current direction of the gremlin
        char currentdir = moving.getdir();
        // to make sure the gremlin doesn't go through the wall
        collision(moving,notmoving);
        ArrayList<Integer> numdirarray = new ArrayList<Integer>();
        boolean status = true;
        // if the direction array is less than 3 items and direction is still haven't choosen
        while(numdirarray.size() <3 && status == true){
            // choose a random number
            int rando= rand.nextInt(4);
            // if the random number is not the way the gremlin is coming from, not the direction 
            // it is currently colliding, and not already in the array
            if(rando != convertdir(moving.goback(currentdir)) && rando != convertdir(currentdir) && !numdirarray.contains(rando)){
                numdirarray.add(rando);
                // if it doesn't collide, finish the loop.
                if (!collision(moving, notmoving)){
                    status = false;
                    moving.setdir(convertnum(numdirarray.get(numdirarray.size()-1)));
                    return moving.convertnum(numdirarray.get(numdirarray.size()-1));
                }
            }
        }
        return moving.goback(moving.getdir());
    }
}