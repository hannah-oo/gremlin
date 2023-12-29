package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import processing.event.KeyEvent;
import java.util.Arrays;
import javax.lang.model.type.NullType;


public class AppTest{
    @Test
    public void testfor5secondsforslimeballs() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(6050); // to give time to initialise stuff before drawing begins
        assertFalse(!app.slimeballs.isEmpty());
    }
    @Test
    public void testforlevel2(){
        App a = new App();
        a.loop();
        PApplet.runSketch(new String[] { "App" }, a);
        a.currentlevel = 1;
        a.setup();
        a.delay(1000);
        assertEquals(1, a.currentlevel);
    }
    @Test
    public void wizardspriteleft(){
        App a = new App();
        a.loop();
        PApplet.runSketch(new String[] { "App" }, a);
        a.setup();
        a.wizard.setdir('l');
        a.delay(1000);
        a.wizard.setdir('u');
        a.delay(1000);
        a.wizard.setdir('d');
        a.delay(1000);
    }
    @Test
    public void powerupusingtesting(){
        PowerUp p = new PowerUp(20,20);
        Wizard w = new Wizard(80,100);
        assertNotNull(p);
        p.setxpos(100);
        p.setypos(100);
        assertEquals(100, p.getxpos());
        assertEquals(100,p.getypos());
        p.setnewpowerupperiod();
        p.setpowerisbeingused(true);
        assertTrue(p.getpowerisbeingused());
        p.powerupspeedmodify(w,4);
        p.powerupspeedmodify(w,600);
    }
    @Test
    public void winningTest(){
        App a = new App();
        PApplet.runSketch(new String[] { "App" }, a);
        a.setup();
        a.currentlevel = 1;
        a.enterdoor = true;
        a.loop();
        a.delay(1000);
        assertFalse(a.enterdoor);
        assertTrue(a.gamefinish);
    }
    @Test
    public void losingTest(){
        App a = new App();
        PApplet.runSketch(new String[] { "App" }, a);
        a.setup();
        a.currentlevel = 0;
        a.gamelosing = 3;
        a.loop();
        a.delay(1000);
        assertFalse(a.enterdoor);
        assertTrue(a.gamefinish);
    }
    @Test
    public void resettingwhilelevel1Test(){
        App a = new App();
        PApplet.runSketch(new String[] { "App" }, a);
        a.setup();
        a.currentlevel = 0;
        a.gamelosing = 0;
        a.gamestate = false;
        a.gamefinish = false;
        a.loop();
        a.delay(1000);
        assertFalse(a.enterdoor);
        assertFalse(a.gamefinish);
        assertTrue(a.gamestate);
    }

}