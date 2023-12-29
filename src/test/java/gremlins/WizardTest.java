package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class WizardTest {

    // @BeforeEach
    // public void setup(){
    // }

    @Test
    public void constructor() {
        Wizard w = new Wizard(40,20);
        w.setxpos(40);
        w.setypos(20);
        w.setdir('l');
        assertNotNull(w);
        assertEquals(40, w.getxpos());
        assertEquals(20, w.getypos());
        assertEquals('l', w.getdir());
    }
    @Test
    public void locationTest(){
        Wizard w = new Wizard(40,20);
        w.setxpos(40);
        w.setypos(20);
        w.changexvel(3);
        w.updateicon();
        assertEquals(43, w.getxpos());
        w.changeyvel(5);
        w.updateicon();
        assertEquals(25, w.getypos());
        w.updateicon();
    } 
    @Test
    public void collisionLeftTest(){
        Wizard w = new Wizard(40,20);
        w.setxpos(40);
        w.setypos(20);
        BlockTile tempblock = new BlockTile(20,20);
        w.setdir('l');
        w.changexvel(-3);
        w.updateicon();
        assertTrue(w.collision(w,tempblock));
        assertEquals(40, w.getxpos());
    }
    @Test
    public void collisionRightTest(){
        Wizard w = new Wizard(40,20);
        w.setxpos(40);
        w.setypos(20);
        BlockTile tempblock = new BlockTile(60,20);
        w.setdir('r');
        w.changexvel(3);
        w.updateicon();
        assertTrue(w.collision(w,tempblock));
        assertEquals(40,w.getxpos());
    }
    @Test
    public void collisionUpTest(){
        Wizard w = new Wizard(40,20);
        w.setxpos(40);
        w.setypos(20);
        BlockTile tempblock = new BlockTile(40,0);
        w.setdir('u');
        w.changeyvel(-3);
        w.updateicon();
        assertTrue(w.collision(w,tempblock));
        assertEquals(20,w.getypos());
    }
    @Test
    public void collisionDownTest(){
        Wizard w = new Wizard(40,20);
        w.setxpos(40);
        w.setypos(20);
        BlockTile tempblock = new BlockTile(40,40);
        w.setdir('d');
        w.changeyvel(3);
        w.updateicon();
        assertTrue(w.collision(w,tempblock));
        assertEquals(20,w.getypos());
    }
    @Test
    public void notcollisionTest(){
        Wizard w = new Wizard(40,20);
        w.setxpos(40);
        w.setypos(20);
        Gremlin tempgrem = new Gremlin(100,100);
        BlockTile tempblock = new BlockTile(80,40);
        w.setdir('l');
        w.changeyvel(3);
        w.updateicon();
        assertFalse(w.collision(w, tempblock));
        assertFalse(w.collisionlogicfor2moving(w, tempgrem));
        tempgrem.setxpos(40);
        w.changeyvel(3);
        w.updateicon();
        assertFalse(w.collisionlogicfor2moving(w, tempgrem));
    }

    @Test
    public void twomovingcollisionTest(){
        Wizard w = new Wizard(40,20);
        Gremlin tempgrem = new Gremlin(20,100);
        w.setdir('l');
        w.changeyvel(-3);
        w.updateicon();
        assertTrue(w.collisionlogicfor2moving(w, tempgrem));
    }
    @Test
    public void movingTest(){
        Wizard w= new Wizard(20,20);
        w.setxpos(20);
        w.setypos(20);
        w.move('l', 2);
        assertEquals(18, w.getxpos());
        w.move('r', 2);
        assertEquals(20,w.getxpos());
        w.move('u', 2);
        assertEquals(18, w.getypos());
        w.move('d', 2);
        assertEquals(20,w.getypos());
        w.setspeed(5); 
    }
    @Test
    public void stopTest(){
        Wizard w= new Wizard(20,20);
        w.setxpos(20);
        w.setypos(20);
        w.move('l', 2);
        w.stop('l');
        assertEquals(0, w.getxpos());
        w.move('r', 2);
        w.stop('r');
        assertEquals(20, w.getxpos());
        w.move('u', 2);
        w.stop('u');
        assertEquals(0, w.getypos());
        w.move('d', 2);
        w.stop('d');
        assertEquals(20, w.getypos());
    }
}