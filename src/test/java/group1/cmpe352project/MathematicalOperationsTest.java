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

    @Test
    public void testNegativeOneLogicalShiftRightThirtyOne(){
        assertEquals(1, mo.logicalShiftRight(-1,31));
    }

    @Test
    public void testTenLogicalShiftRightNegativeHundred(){
        assertEquals(0, mo.logicalShiftRight(10,-100));
    }
    
    @Test
    public void testSixDividedByTwo(){
        assertEquals(3, mo.divide(6,2));
    }

    @Test(expected=ArithmeticException.class)
    public void testTwoDividedByZero(){
        mo.divide(2,0);
    }

    @Test(expected=ArithmeticException.class)
    public void testTwoInverseDividedByZero(){
        mo.inverseDivide(0,2);
    }

	@Test
    public void testTwoInverseDividedBySix(){
        assertEquals(3, mo.inverseDivide(2,6));
    }

	@Test
	public void testNegativeThreePowerTwo(){
		assertEquals(9.0, mo.power(-3,2));
	}
	
	@Test
	public void testTwoPowerNegativeThree(){
		assertEquals(0.125, mo.power(2,-3));
	}
	
	@Test
	public void testNegativeTwoPowerZero(){
		assertEquals(1.0, mo.power(-2,0));
	}
	
	@Test
	public void testNegationTrue(){
		assertEquals(false,mo.negation(true));
	}

	@Test
	public void testNegationFalse(){
		assertEquals(true,mo.negation(false));
	}
}
