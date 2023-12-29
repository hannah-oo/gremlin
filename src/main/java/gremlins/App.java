package gremlins;
//importing all the relevant packages
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.io.*;
import java.util.*;
import javax.net.ssl.TrustManagerFactory;
import org.checkerframework.checker.units.qual.K;

/**
 * Game app class that extends from PApplet class. It has all the drawing, setup, and logic of the Game App.
 */
public class App extends PApplet {
    
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public String configPath;

    // creating a variable for each inage. 
    public PImage brickwall;
    public PImage stonewall;
    public PImage gremlin;
    public PImage slime;
    public PImage fireball;
    public PImage doorpic;
    public PImage poweruppic;
    public PImage teleportpic;
    /**
     * PImage of the wizard going to the left direction.
     */
    public PImage leftsprite;
    /**
     * PImage of the wizard going to the right direction.
     */
    public PImage rightsprite;
    /**
     * PImage of the wizard going to the upward direction.
     */
    public PImage upsprite;
    /**
     * PImage of the wizard going to the downward direction.
     */
    public PImage downsprite;
    /**
     * PImage of the first of the brick destroying sequences.
     */
    public PImage brick0;
    /**
     * PImage of the second of the brick destroying sequences.
     */
    public PImage brick1;
    /**
     * PImage of the third of the brick destroying sequences.
     */
    public PImage brick2;
    /**
     * PImage of the final of the brick destroying sequences.
     */
    public PImage brick3;
    /**
     * levellist is for how many levels are there in total. It is a JSONArray object. 
     */
    public JSONArray levellist; 
    public int currentlevel = 0;
    /**
     * the total number of levels in the JSON file 
     */
    public int totallevel;
    /**
     * To read the name of the map file. 
     */
    public String mazefile;
    /**
     * To read the 'lives' in the JSON file.
     */
    public int lives;
    /**
     * To read for wizard fireball cooldown in the JSON file.
     */
    public float wcool;
    /**
     * To read for gremlin slime cooldown in the JSON file.
     */
    public float gcool;
    /**
     * gamestate variable is for whether the game has to restart. It will be false if the game needs to be resetted 
     * without starting the whole game.
     */
    public boolean gamestate = true;
    /**
     * wholegamerestart variable is for whether user has pushed any keys after the game is finished.
     */
    public boolean wholegamerestart = false;
    /**
     * gamefinish variable is for whether the game has ended with either the screen showing 'Game Over' or 'You Win'.
     */
    public boolean gamefinish = false;   
    /**
     * Tracks how many times the player has lost the game. 
     */
    public int gamelosing = 0;
    /**
     * enterdoor is for whether the player has collided with the exit door. 
     */
    public boolean enterdoor;
    /**
     * Counts the number of frames that has passed so that it can be used to see if
     * the cooldown period is over for the gremlins.
     */
    public int counterforslime = 0;
    /**
     * Counts the number of frames that has passed so that it can be used to see if
     * the cooldown period is over for the wizard.
     */
    public float counterforfireball = 0;
    /**
     * startcount is true when the player has shot a fireball. it will be false when the wizard cooldown is over. 
     */
    public boolean startcount = false; // fireball cooldown start
    /**
     * Track for the length of the wizard fireball cooldown bar.
     */
    public float rectlengthreduce= 0;    
    /**
     * Length of the rectangle to add per frame to the wizard cooldown bar.
     */
    public float reducethismuchrectforwcool;
    /**
     * Track for the length of the powerup timer bar.
     */
    public float poweruprectreduce = 0;
    /**
     * Length of the rectangle to add per second to the powerup timer bar.
     */
    public float poweruprectreducethismuch = 10; 
    /**
     * Starts counting for when the wizard collides with the Powerup tile. 
     */
    public int poweruptimer = 0;
    /**
     * Tracks how long the powerup has been in cooldown mode. 
     */
    public int powerupcooldowncounter;
    /**
     * Timer to implement brick destorying sequence.
     */
    public int frametiming = 0;
    /**
     * The number of steps to be taken to get to next tile.
     */
    public int steps;
    /**
     * Reads the map file in the JSON file into a 2-dimensional array. 
     */
    public char[][] layoutarr = new char[36][36];
    /**
     * Contains the Gremlin objects in the game. Gets emptied every time the game is resetted.
     */
    public ArrayList<Gremlin> gremarray = new ArrayList<Gremlin>();
    /**
     * Contains the Brick objects in the game. Gets emptied every time the game is resetted.
     */
    public ArrayList<Brick> bricklist = new ArrayList<Brick>();
    /**
     * Contains the stone wall tiles in the game. Gets emptied every time the game is resetted.
     */
    public ArrayList<BlockTile> stonewalllist = new ArrayList<BlockTile>();
    /**
     * Contains the fireballs in the game. Gets emptied every time the game is resetted.
     */
    public ArrayList<Ball> fireballs = new ArrayList<Ball>();
    /**
     * Contains the slimeballs in the game. Gets emptied every time the game is resetted.
     */
    public ArrayList<Ball> slimeballs = new ArrayList<Ball>();
    /**
     * Creates a wizard object to be used in the game.
     */
    public Wizard wizard = new Wizard(40, 20);
    /**
     * Creates an exit object tile to be collided in the game.
     */
    public BlockTile door = new BlockTile(0,0);
    /**
     * Creates a powerup object to be used in the game.
     */
    public PowerUp powerup = new PowerUp(0,0);
    /**
     * Creates a teleportation door tile to be collided in the game.
     */
    public BlockTile teleport1 = new BlockTile(0,0);
    /**
     * Creates a teleportation door tile to be collided in the game.
     */
    public BlockTile teleport2 = new BlockTile(0,0);
    /**
     * Tracks the direction of the gremlin. 
     */
    public char gremdir;
    /**
     * Speed(in Pixels) of fireballs and slimeballs per frame.
     */
    public int ballspeed = 4;
    /**
     * Tracks if the powerup is present in the JSON map reading file.
     */
    public boolean poweruppresent;
    /**
     * Tracks if the teleportation door 1 is present in the JSON map reading file.
     */
    public boolean teleport1present;
    /**
     * Tracks if the teleportation door 2 is present in the JSON map reading file.
     */
    public boolean teleport2present;

    public App() {
        this.configPath = "config.json";
    }
    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH,  HEIGHT);
    }
    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);
        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.doorpic = loadImage(this.getClass().getResource("door.png").getPath().replace("%20", " "));
        this.poweruppic = loadImage(this.getClass().getResource("powerup.png").getPath().replace("%20", " "));
        this.teleportpic = loadImage(this.getClass().getResource("firedoor.png").getPath().replace("%20", " "));

        this.leftsprite = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.rightsprite = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.upsprite = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.downsprite = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));

        this.brick0= loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        this.brick1 = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        this.brick2 = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        this.brick3 = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));

        getinfo();
    }
    /**
     * Initialise the variables and map every time the game resets. 
     */
    public void getinfo(){
        // create object to read the JSON file
        JSONObject conf = loadJSONObject(new File(this.configPath));
        levellist = conf.getJSONArray("levels");  
        totallevel = levellist.size();    
        lives = conf.getInt("lives");
        lives -= gamelosing;
        gamestate = true;

        // set up the power related variable
        powerup.setnewpowerupperiod();
        poweruptimer = 0;
        powerupcooldowncounter = 0;
        powerup.setpowerupstate(false);
        powerup.setpowerisbeingused(false);
        poweruprectreduce = 0;
        poweruppresent = false;

        // set up variables for wizard and slime;
        counterforslime = 0;
        wizard.setspeed(1);
        wizard.setdir('r');
        steps = 0;
        enterdoor = false;
        teleport1present = false;
        teleport2present = false;

        // set up the variables according to current level the player is in
        if (currentlevel == 0){
            JSONObject curlevel = levellist.getJSONObject(currentlevel);
            this.mazefile =  curlevel.getString("layout");
            this.wcool = curlevel.getFloat("wizard_cooldown");
            this.gcool = curlevel.getFloat("enemy_cooldown");
        }
        else if (currentlevel == 1){
            JSONObject curlevel = levellist.getJSONObject(currentlevel);
            this.mazefile =  curlevel.getString("layout");
            this.wcool = curlevel.getFloat("wizard_cooldown");
            this.gcool = curlevel.getFloat("enemy_cooldown");
        }

        this.reducethismuchrectforwcool = 100 / (this.wcool*FPS) ; // for wizard cool down

        // set the arrays to empty when the game resets
        if (!gremarray.isEmpty()){
            gremarray.clear();
        }
        if(!stonewalllist.isEmpty()){
            stonewalllist.clear();
        }
        if(!bricklist.isEmpty()){
            bricklist.clear();
        }

        // read the file and set the x positions and y positions of the objects depending on the characters in the file 
        try {
            File myObj = new File(mazefile);
            Scanner myReader = new Scanner(myObj);
            int linenum = 0;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                // use the layoutarr variable to put each line of the file into array 
                char[] linearr = line.toCharArray();
                for (int i = 0; i < linearr.length; i++){
                    layoutarr[i][linenum] = linearr[i];
                    if (layoutarr[i][linenum] == 'G'){
                        Gremlin gremname = new Gremlin(i*SPRITESIZE, linenum*SPRITESIZE);
                        gremname.setxpos(i*SPRITESIZE);
                        gremname.setypos(linenum*SPRITESIZE);
                        // set the gremlin's direction randomly
                        gremname.setdir(gremname.startdir());
                        gremarray.add(gremname);
                    }
                    else if (layoutarr[i][linenum] == 'B'){
                        Brick brick = new Brick(i*SPRITESIZE, linenum*SPRITESIZE);
                        bricklist.add(brick);
                    }
                    else if (layoutarr[i][linenum] == 'X'){
                        BlockTile stone = new BlockTile(i*SPRITESIZE, linenum*SPRITESIZE);
                        stonewalllist.add(stone);
                    }
                    else if (layoutarr[i][linenum] == 'W'){
                        wizard.setxpos(i*SPRITESIZE);
                        wizard.setypos(linenum*SPRITESIZE);
                    }
                    else if (layoutarr[i][linenum] == 'E'){
                        door.setxpos(i*SPRITESIZE);
                        door.setypos(linenum*SPRITESIZE);
                    }
                    else if (layoutarr[i][linenum] == 'P'){
                        powerup.setxpos(i*SPRITESIZE);
                        powerup.setypos(linenum*SPRITESIZE);
                        poweruppresent = true;
                    }
                    else if (layoutarr[i][linenum] == 'F'){
                        teleport1.setxpos(i*SPRITESIZE);
                        teleport1.setypos(linenum*SPRITESIZE);
                        teleport1present = true;
                    }
                    else if (layoutarr[i][linenum] == 'S'){
                        teleport2.setxpos(i*SPRITESIZE);
                        teleport2.setypos(linenum*SPRITESIZE);
                        teleport2present = true;
                    }
                }
            linenum += 1;
            } myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
                e.printStackTrace();
        }   
    }
    /**
     * Receive key pressed signal from the keyboard.
    */
    @Override
    public void keyPressed(){
        if (keyCode == 37){
            wizard.changexvel(-2);
            wizard.setdir('l');
        }
        else if (keyCode == 39){
            wizard.changexvel(2);
            wizard.setdir('r');
        }
        else if (keyCode == 38){
            wizard.changeyvel(-2);
            wizard.setdir('u');
        }
        else if (keyCode == 40){
            wizard.changeyvel(2);
            wizard.setdir('d');
        }
        else if (keyCode == 32){
            // if the cooldown is done, if the space key is pressed, create a fireball and add it to fireball array
            if (this.counterforfireball == 0){
                Ball tempfireball = new Ball(wizard.getxpos(), wizard.getypos());
                tempfireball.setxpos(wizard.getxpos());
                tempfireball.setypos(wizard.getypos());
                tempfireball.setdir(wizard.getdir());
                fireballs.add(tempfireball);
                // this is to start the wizard cooldown countdown
                this.startcount = true;
                this.rectlengthreduce = 0;
            }
        } 
        // if any key is pressed after the game has ended, restart
        if (gamefinish == true){
            gamelosing = 0;
            getinfo();
            gamefinish = false;
        }
    }
    /**
     * Receive key released signal from the keyboard.
    */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 37 || key == 38 || key == 39 || key == 40) {
            steps = wizard.stop(wizard.getdir());
        } 
    }
    /**
     * Draw all elements in the game by current frame. It mainly has the logic for whether the game should be resetted,
     * winning and losing display, and loading new level.
	 */
    public void draw(){
       // if the wizard collides with the exit door
        if (this.enterdoor == true){
            // if the level was 0, move to level 1 and reset game info to get level 2 map
            if (this.currentlevel == 0){
                this.currentlevel = 1;
                getinfo();
            }
            // if the level was 1, display win screen, set game finish to true
            else if (this.currentlevel == 1){
                fill(243,227,198);
                rect(0,0,719,719);
                textSize(50);
                fill(0,0,0);
                text("You win", 220, 320);
                this.enterdoor = false;
                this.gamefinish = true;
            }
        }
        else{
            // if the player still have lives and game is not finished, loop through game file 
            if (this.gamelosing < 3 && gamefinish == false){
                tick();
                // if the player lost the game, lose a life and reset the game
                if (this.gamestate == false && currentlevel == 0){
                    gamelosing++;
                    getinfo();
                    this.gamestate = true;
                }
                else if (this.gamestate == false && currentlevel == 1){
                    gamelosing++;
                    currentlevel = 0;
                    getinfo();
                    this.gamestate = true;
                }
            }
            // if the player lost all three lives, display game over screen
            else if (this.gamelosing == 3){
                fill(243,227,198);
                rect(0,0,719,719);
                textSize(50);
                fill(0,0,0);
                text("Game over", 220, 320);
                this.gamestate = false;
                this.gamefinish = true;
            }
        }
    }
    /**
     * Game loop that includes logic of the game and drawing objects. 
     */
    public void tick() {
        // draw the background
        background(243,227,198);
        // draw the texts in the bottom empty space
        fill(0,0,0);
        textSize(15);
        text("Lives:", 15, 690);
        for (int r = 0; r < lives; r++){
            image(rightsprite, 60+(r*20), 680);
        }
        text("Level " + (this.currentlevel + 1)+ "/2", 150, 690);

        //  if (steps > 0){
        //     wizard.move(wizard.getdir(),2);
        //      steps--;
        //  }

        // update the wizard location 
        wizard.updateicon();
        // add 1 to the gremlin cooldown timer
        counterforslime++;

        // for each brick tile, draw and check collision with wizard
        for (Brick brickeach: bricklist){
            image(this.brickwall, brickeach.getxpos(), brickeach.getypos());
            wizard.collision(wizard, brickeach);
        }

        // draw the exit picture
        image(doorpic, door.getxpos(), door.getypos());

        if (poweruppresent == true){
            // add 1 to the powerup cooldown timer
            powerupcooldowncounter++;
            //  if the powerup cooldown timer is achieved, start showing the powerup icon
            if (powerupcooldowncounter == powerup.getpowerupperiod()){
                // powerup is activated
                powerup.setpowerupstate(true);
            }

            // if powerup is activated, draw the picture
            if(powerup.getpowerupstate()){
                image(poweruppic, powerup.getxpos(), powerup.getypos());
                // if the wizard collide with powerup, set the powerisbeingused to true 
                // and make the powerup icon disappear and deactivate it
                if(wizard.collision(wizard,powerup)){
                    powerup.setpowerisbeingused(true);
                    powerup.setpowerupstate(false); 
                }
            }

            // if the powerup is being used, add 1 to the timer
            if (powerup.getpowerisbeingused()){
                poweruptimer++;    
                // draw the cooldown bar
                fill(0,0,0);
                textSize(11);
                text("Speeding Up Timer: ",480,710); 
                fill(255,255,255);
                rect(600, 700, 100, 10);
                fill(246,161,146);
                if (poweruptimer % 60 == 0){
                    this.poweruprectreduce +=  this.poweruprectreducethismuch;
                }
                rect(600,700,this.poweruprectreduce,10);

                // change the wizard's speed according to the timer
                powerup.powerupspeedmodify(wizard, poweruptimer);

                // if the timer is more 600, meaning 10 seconds since it is 60 FPS 
                if(poweruptimer >600){
                    // reset the cooldown timer so that it can start adding again, make the rectangle adding
                    // back to 0, and powerup is being used timer is reseted. 
                    this.powerupcooldowncounter = 0;
                    this.poweruprectreduce = 0;
                    this.poweruptimer = 0;
                    powerup.setpowerisbeingused(false);
                }
            }
        }

        // this is for wizard cooldown starting
        if(this.startcount == true){
            this.counterforfireball++;
        }
        // while the wizard cooldown is ongoing and it is less than times 60 FPS:
        if (this.startcount == true && this.counterforfireball <= this.wcool*60){
            // draw the cooldown bar
            fill(0,0,0);
            textSize(10);
            text("Fireball Cooldown: ",490,680); 
            fill(255,255,255);
            rect(600, 670, 100, 10);
            fill(0,0,0);
            this.rectlengthreduce += this.reducethismuchrectforwcool;
            rect(600,670,this.rectlengthreduce,10);
        }
        // if the wizard cooldown is over, reset them 
        if(this.counterforfireball> wcool*60){
            this.startcount = false;
            this.counterforfireball = 0;
        }

        // if the gremlin cooldown is over, create a slime ball for each gremlin and add it to
        // slime arraylist 
        if (this.counterforslime == gcool*60){
            for (Gremlin gremeach: gremarray){
                Ball tempslimeball = new Ball(gremeach.getxpos(), gremeach.getypos());
                tempslimeball.setxpos(gremeach.getxpos());
                tempslimeball.setypos(gremeach.getypos());
                tempslimeball.setdir(gremeach.getdir());
                slimeballs.add(tempslimeball);
            }
            // reset the gremlin cooldown timer
            this.counterforslime = 0; 
        }

        // if the current level is level 1, draw the two teleportation doors and the extension is activated
        if (teleport1present = true && teleport2present){
            image(teleportpic, teleport1.getxpos(), teleport1.getypos());
            image(teleportpic, teleport2.getxpos(), teleport2.getypos());
            // if the wizard is on top of the doors, teleport next to the other door
            if (wizard.getxpos() == teleport1.getxpos() && wizard.getypos() == teleport1.getypos()){
                wizard.setxpos(teleport2.getxpos());
                wizard.setypos(teleport2.getypos()+20);
            }
            if (wizard.getxpos() == teleport2.getxpos() && wizard.getypos() == teleport2.getypos()){
                wizard.setxpos(teleport1.getxpos()+20);
                wizard.setypos(teleport1.getypos());
            }
        }

        // for every fireball, draw it, fireball start moving/ updating its position
        for (Ball fireballeach: fireballs){
            image(fireball, fireballeach.getxpos(), fireballeach.getypos());
            fireballeach.move(fireballeach.getdir(), ballspeed);
            // for each fireball, if they hit a stone wall, set the fireball to delete 
            // so that it can be removed in a new loop
            for (BlockTile stonewalleach: stonewalllist){
                if (fireballeach.collision(fireballeach, stonewalleach)){
                    fireballeach.setdelete(true);
                }
            }
            // for each fireball, if they hit a brcik wall, set the fireball and brick to be deleted
            for (Brick brickeach: bricklist){
                if (fireballeach.collision(fireballeach, brickeach)){
                    brickeach.setdelete(true);
                    fireballeach.setdelete(true);
                }
            }
            // if the fireball meets a gremlin, update gremlin's location to another place and 
            // set the fireball to be deleted
            for (Gremlin gremeach: gremarray){
                if (fireballeach.collisionlogicfor2moving(gremeach, fireballeach)){
                    gremeach.setshift(true);
                    fireballeach.setdelete(true);
                }
            }
        }

        // for each gremlin, draw and move them
        for (Gremlin gremeach: gremarray){
            image(gremlin, gremeach.getxpos(), gremeach.getypos());
            this.gremdir = gremeach.getdir();
            gremeach.move(this.gremdir, 1);
            // if the gremlin needs to be shifted, check if the gremlin is on stone tiles and shift if they are
            for (BlockTile stonewalleach: stonewalllist){
                if (gremeach.getshift()){
                    gremeach.shift(wizard,stonewalleach);
                    gremeach.setshift(false);
                }
                // if they collide, change direction
                if (gremeach.collision(gremeach, stonewalleach)){
                    gremeach.setdir(gremeach.choosedir(gremeach, stonewalleach));
                }
            }
            // if the gremlin needs to be shifted, check if the gremlin is on brick tiles and shift if they are
            for (Brick brickeach: bricklist){
                if (gremeach.getshift()){
                    gremeach.shift(wizard,brickeach);
                    gremeach.setshift(false);
                }
                // if they collide, change direction
                if (gremeach.collision(gremeach, brickeach)){
                    gremeach.setdir(gremeach.choosedir(gremeach, brickeach));
                }
            }
            // if the gremlin and wizard collide, the game is to be resetted
            if (gremeach.collisionlogicfor2moving(gremeach, wizard)){
                this.gamestate = false;
            }
        }
        
        // for each slimeball, draw and move the ball according to the producer gremlin's direction
        for (Ball slimeeach: slimeballs){
            image(slime, slimeeach.getxpos(), slimeeach.getypos());
            slimeeach.move(slimeeach.getdir(), ballspeed);
            // if they hit the stonewall, set the slimeball object to be deleted
            for (BlockTile stonewalleach: stonewalllist){
                if (slimeeach.collision(slimeeach, stonewalleach)){
                    slimeeach.setdelete(true);
                }
            }
            // if they hit the brick, set the slimeball object to be deleted
            for (Brick brickeach: bricklist){
                if (slimeeach.collision(slimeeach, brickeach)){
                    slimeeach.setdelete(true);
                }
            }
            // if they hit the fireball, set the slimeball object to be deleted
            for (Ball fireballeach: fireballs){
                if (slimeeach.collisionlogicfor2moving(slimeeach, fireballeach)){
                    slimeeach.setdelete(true);
                    fireballeach.setdelete(true);
                }
            }
            // if they hit the wizard, set the game to be resetted
            if (wizard.collisionlogicfor2moving(wizard, slimeeach)){
                this.gamestate = false;
            }
        }
        
        // if the slimeballs are to be deleted, remove them in this loop
        for (int q = 0; q < slimeballs.size(); q++){
            if (slimeballs.get(q).getdelete()){
                slimeballs.remove(q);
            }
        }
        // if the fireballs are to be deleted, remove them in this loop
        for (int m = 0; m < fireballs.size(); m++){
            if (fireballs.get(m).getdelete()){
                fireballs.remove(m);
            }
        }
        // for each stone tile, draw and check collision with wizard
        for (BlockTile stonewalleach : stonewalllist){
            image(this.stonewall, stonewalleach.getxpos(), stonewalleach.getypos()) ;
            wizard.collision(wizard, stonewalleach);
        }

        // draw the wizard according to its direction
        if (wizard.getdir() == 'l'){
            image(leftsprite, wizard.getxpos(), wizard.getypos());
        }
        if (wizard.getdir() == 'r'){
            image(rightsprite, wizard.getxpos(), wizard.getypos());
        }
        if (wizard.getdir() == 'u'){
            image(upsprite, wizard.getxpos(), wizard.getypos());
        }
        if (wizard.getdir() == 'd'){
            image(downsprite, wizard.getxpos(), wizard.getypos());
        }

        // if the wizard collide the exit, set the enterdoor to true to signal to next level
        if (wizard.collision(wizard,door)){
            this.enterdoor = true;
        }

        // if the brick is set to be deleted
        for (int p = 0; p<bricklist.size(); p++){
            if (bricklist.get(p).getdelete()){
                // get the frametiming value from each brick's variables
                frametiming = bricklist.get(p).getframe();
                // according to the frame count, change the brick picture
                if (frametiming<5){
                    image(brick0,bricklist.get(p).getxpos(),bricklist.get(p).getypos());
                }
                else if (frametiming >4 && frametiming<9){
                    image(brick1,bricklist.get(p).getxpos(),bricklist.get(p).getypos());
                }
                else if (frametiming > 8 && frametiming<13){
                    image(brick2,bricklist.get(p).getxpos(),bricklist.get(p).getypos());
                }
                else if (frametiming>12){
                    image(brick3,bricklist.get(p).getxpos(),bricklist.get(p).getypos());
                }
                bricklist.get(p).addframe();
                // after 16 counts, remove the brick from the list
                if (frametiming > 17){
                    bricklist.remove(p);
                }
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}