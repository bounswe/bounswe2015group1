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
}

