public class MathematicalOperations {

    public int plus(int a, int b){
        return a+b;
    }

    public int minus (int a, int b){
    	return a-b;
    }

    public int multiply (int a, int b){
    	return a*b;
    }

    public int remainder(int a, int b){
	    return a%b;
    }

    public int logicalShiftRight(int n, int shiftAmount) {
      return n >>> shiftAmount;
    }
        
}
