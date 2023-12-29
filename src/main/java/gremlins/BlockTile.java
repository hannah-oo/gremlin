package gremlins;
/**
 * BlockTile class is extended from abstract Object class to represent a tile in the game. It can be any tile including
 * stonetiles, brick, powerup tile, exit, and teleport door.
 */
public class BlockTile extends Object{
    /**
     * Creates a tile at a location based on the pixels.
     * @param xpos position on the x-axis using pixels as scale. 
     * @param ypos position on the y-axis using pixels as scale. 
     */
    public BlockTile(int xpos, int ypos){
        super(xpos,ypos);
   }
}