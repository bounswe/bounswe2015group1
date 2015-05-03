import org.junit.*;
import static org.junit.Assert.*;

public class MathematicalOperationsTest{
    private MathematicalOperations mo;

    @Before
    public void setUp(){
        mo = new MathematicalOperations();
    }

    @Test
    public void testTwoPlusTwo(){
        assertEquals(4, mo.plus(2,2));
    }

    @Test
    public void testZeroPlus(){
        assertEquals(2, mo.plus(0,2));
    }

    @Test
    public void testSevenMinusNine(){
        assertEquals(-2, mo.minus(7,9));
    }

    @Test
    public void testFourMinusZero(){
        assertEquals(4, mo.minus(4,0));
    }

    @Test
    public void testThreeTimesFour(){
        assertEquals(12, mo.times(3,4));
    }

    @Test
    public void testNegativeTimesPositive(){
	   assertEquals(-3,mo.times(-1,3));	
    }

    @Test
    public void testFourRemainderThree(){
        assertEquals(1, mo.remainder(4,3));
    }

    @Test
    public void testSevenRemainderFive(){
        assertEquals(2, mo.remainder(7,5));
    }
   
}
    
