package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BlockTileandObjectTest{
    @Test
    public void blocktileconstructor(){
        BlockTile b = new BlockTile(20,20);
        assertNotNull(b);
    }
    @Test
    public void objectTest(){
        Object o = new BlockTile(20,20);
        assertNotNull(o);
        o.setxpos(60);
        o.setypos(80);
        assertEquals(60, o.getxpos());
        assertEquals(80, o.getypos());
    }
    @Test
    public void brickTest(){
        Brick b = new Brick(20,20);
        assertNotNull(b);
        b.setdelete(true);
        assertTrue(b.getdelete());
        assertEquals(0, b.getframe());
        b.addframe();
        assertEquals(1, b.getframe());
    }
}