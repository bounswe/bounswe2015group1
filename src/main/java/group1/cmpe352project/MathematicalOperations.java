public class MathematicalOperations {

    public int plus(int a, int b){
        return a+b;
    }

    public int minus (int a, int b){
    	return a-b;
    }

    public int times (int a, int b){
    	return a*b;
    }

    public int remainder(int a, int b){
	    return a%b;
    }

    public int logicalShiftRight(int n, int shiftAmount) {
      return n >>> shiftAmount;
    }
     
    public int divide(int a, int b){
    	return a/b;
    }

    public int inverseDivide(int a, int b){
    	return b/a;
    }
}
