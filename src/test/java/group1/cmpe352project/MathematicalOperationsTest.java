import org.junit.*;
import static org.junit.Assert.*;

/**
 *  Our mathematical operations test class.
 *  It has a total of 24 test methods for various mathematical operations.
 */
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
    
    /**
     * <p>
     * test#1 for the minus operation.
     * calls the minus method with 2 parameters: 7 and 9.
     * </p>
     *
     * @param 7, minuend. 
     * @param 9, subtrahend.
     * @return -2, difference. 
     */
    @Test
    public void testSevenMinusNine(){
        assertEquals(-2, mo.minus(7,9));
    }
    
    /**
     * <p>
     * test#2 for the minus operation.
     * calls the minus method with 2 parameters: 4 and 0.
     * </p>
     *
     * @param 4, minuend. 
     * @param 0, subtrahend.
     * @return 4, difference. 
     */
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
    /**
     * <p>
     * Test #1 for the Logical Shift Right operation.
     * Calls the logicalShiftRight method with 2 parameters, -1 and 31. It should return 1.
     * </p>
     */
    @Test
    public void testNegativeOneLogicalShiftRightThirtyOne(){
        assertEquals(1, mo.logicalShiftRight(-1,31));
    }
    /**
     * <p>
     * Test #2 for the Logical Shift Right operation.
     * Calls the logicalShiftRight method with 2 parameters, 10 and -100. It should return 0.
     * </p>
     */
    @Test
    public void testTenLogicalShiftRightNegativeHundred(){
        assertEquals(0, mo.logicalShiftRight(10,-100));
    }
    @Test
    public void testNegativeToNegativeUnaryPlus(){
	assertEquals(-10, mo.unaryPlus(-10));
    }
    public void testZeroToZeroUnaryPlus(){
	assertEquals(0, mo.unaryPlus(0));
    }
     public void testPositiveToPositiveUnaryPlus(){
	assertEquals(10, mo.unaryPlus(10));
    }
     /**
      * <p>
      * test#1 for the division operation.
      * Six divided by two (6/2) is tested here. It should result 3 if the method is correct.
      * </p>
      */
      @Test
      public void testSixDividedByTwo(){
          assertEquals(3, mo.divide(6,2));
      }
      /**
       * <p>
       * test#2 for the division operation.
       * Six divided by two (2/0) is tested here. It should result an arithmetic exception if the method is correct.
       * </p>
       */
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
		assertEquals(9.0, mo.power(-3,2), 0.001);
	}
	
	@Test
	public void testTwoPowerNegativeThree(){
		assertEquals(0.125, mo.power(2,-3), 0.001);
	}
	
	@Test
	public void testNegativeTwoPowerZero(){
		assertEquals(1.0, mo.power(-2,0), 0.001);
	}
	
	@Test
	public void testNegationTrue(){
		assertEquals(false,mo.negation(true));
	}

	@Test
	public void testNegationFalse(){
		assertEquals(true,mo.negation(false));
	}
	
	@Test
	public void testEqual(){
		assertEquals(true,mo.equals(1,1));
	}
	
	@Test
	public void testNotEqual(){
		assertEquals(false,mo.equals(1,0));
	}
}
