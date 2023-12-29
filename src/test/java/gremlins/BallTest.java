package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BallTest {
    @Test
    public void ballconstructor(){
        Ball b = new Ball(20,20);
        assertNotNull(b);
    }
    @Test
    public void deleteTest(){
        Ball b = new Ball(20,20);
        b.setdelete(true);
        assertEquals(true, b.getdelete());
    }
}