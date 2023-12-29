package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GremlinTest{
    @Test
    public void directiontest(){
        Gremlin g = new Gremlin(20,20);
        assertEquals(0, g.convertdir('l'));
        assertEquals(1, g.convertdir('r'));
        assertEquals(2, g.convertdir('u'));
        assertEquals(3, g.convertdir('d'));
        assertEquals(-1, g.convertdir('e'));
        
        assertEquals('l', g.convertnum(0));
        assertEquals('r', g.convertnum(1));
        assertEquals('u', g.convertnum(2));
        assertEquals('d', g.convertnum(3));
        assertEquals('e', g.convertnum(8));

        assertEquals('r', g.goback('l'));
        assertEquals('l', g.goback('r'));
        assertEquals('d', g.goback('u'));
        assertEquals('u', g.goback('d'));
        assertEquals('e', g.goback('e'));
    }

    @Test
    public void startdirTest(){
        Gremlin g = new Gremlin(20,20);
        String dir = g.startdir() + "";
        String dirlist = "lrude";
        assertTrue(dirlist.contains(dir));
    }
    @Test
    public void shiftTest(){
        Gremlin g = new Gremlin(40,40);
        g.setxpos(40);
        g.setypos(40);
        Wizard w = new Wizard(300,300);
        w.setxpos(300);
        w.setypos(300);
        BlockTile b = new BlockTile(50,50);
        g.setshift(true);
        g.shift(w,b);
        assertFalse(g.getshift());
    }
    @Test
    public void choosedirTest(){
        Gremlin g = new Gremlin(40, 40);
        g.move('r', 20);
        g.setdir('r');
        BlockTile b = new BlockTile(60,40);
        String dir = g.choosedir(g, b) + "";
        String dirlist = "ude";
        assertTrue(dirlist.contains(dir));
    }

}