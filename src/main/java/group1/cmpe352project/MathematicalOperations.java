/**
 *  Our mathematical operations class.
 *  It has a total of 11 methods for various mathematical operations.
 */
public class MathematicalOperations {

    public int plus(int a, int b){
        return a+b;
    }

    /**
     * <p>
     * returns the remainder of parameters
     * </p>
     *
     * @param a, minuend. It is an integer.
     * @param b, subtrahend. It is an integer.
     * @return a-b, difference. It is also an integer.
     */
    public int minus (int a, int b){
    	return a-b;
    }
    
    /**
     * <p>
     * returns product of parameters
     * </p>
     *
     * @param a the first value which will be multiplied
     * @param b the second value which will be multiplied
     * @return a*b  
     */
    public int times (int a, int b){
    	return a*b;
    }

    public int remainder(int a, int b){
	    return a%b;
    }
    
    /**
     * <p>
     * Returns n logically shifted right by shiftAmount
     * </p>
     *
     * @param n the number whose bits will be shifted
     * @param shiftAmount the amount of shifting
     * @return n>>>shiftAmount as integer
     */
    public int logicalShiftRight(int n, int shiftAmount) {
      return n >>> shiftAmount;
    }
    /**
     * <p>
     * returns divided result of the parameters
     * </p>
     *
     * @param a dividend of the division operation as integer value.
     * @param b divider of the operation as integer value
     * @return a/b the quotient result of the division operation as integer value. The remainder is discarded since operating on integers.
     */      
    public int divide(int a, int b){
    	return a/b;
    }

	/**
 * <p>
 * returns unary plus result of the operand.
 * </p>
 * @param a the operand to be returned since the unary plus does return the same operand.
 */	
	public int unaryPlus(int a){
		return a;
	}



	/**
     * <p>
     * returns division of parameters in reversed order.
     * </p>
     *
     * @param a the divisor value
     * @param b the number which will be divided
     * @return b/a value as integer
     */
    public int inverseDivide(int a, int b){
    	return b/a;
    }
	
	public double power(int a, int b){
		double result=1.0;
		if(b==0){
			return result;
		}
		else if(b<0){
			double tempA=1/(double)a;		
			b=(-1)*b;		
			for(int i=0;i<b;i++){
				result=result*tempA;
			}		
		return result;
		}
		else{
			for(int i=0;i<b;i++){
				result=result*a;
			}		
			return result;
		}
	}

	public boolean negation(boolean b){
		if(b==true){
			return false;
		}else{
			return true;
		}
	}
	
	    /**
     * <p>
     * returns true if a and b are equal, returns false otherwise
     * </p>
     *
     * @param a, the integer to be compared with b.
     * @param b, the integer to be compared with a.
     * @return a==b, equality as boolean
     */
	public boolean equals(int a, int b){
		return a==b;
	}
}

