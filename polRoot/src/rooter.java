import java.util.Arrays;

public class rooter implements rooterInterface {
	
	float[] poly;
	int[] powers;
	float[] dpoly;
	int[] dpowers;
	int iterations;
	private int MAX_ITERATIONS = 10000;
	public rooter(float[] array) {
		powers = new int[array.length];
		for(int i = 0; i < array.length; i++)
			powers[i]= array.length - i - 1;
		this.poly = Arrays.copyOf(array, array.length);
	}

	public float bisect(float a, float b) {
		if((f(a)*f(b))<0) {
			iterations = 0;
			boolean lneg = false;
			if(f(a)<0)
				lneg = true;
			float c = (a+b)/2;
			//This is where the loop will begin
			while(Math.abs(f(c)) > 0.000001) {
				if(iterations >= MAX_ITERATIONS)
					throw new RootDoesNotExistException("The max number of iterations has been reached");
				c=(a+b)/2;
				if((f(c) < 0) && lneg)
					a=c;
				else
					b=c;
				iterations++;
			}
			return c;
		}else
			throw new RootDoesNotExistException("A single sroot does not exist within the bounds " + 
												 a + " and " + b);
	}

	public float newton(float a) {
		return 0;
	}

	public float secant(float a, float b) {
		return 0;
	}

	public float f(float x) {
		float temp = 0;
		for(int i = 0; i < poly.length; i++) {
			temp += poly[i] * Math.pow(x, powers[i]);
		}
		return temp;
	}
	
	private void getDerivative(){
		this.dpoly = new float[poly.length - 1];
		this.dpowers = new int[powers.length -1];
		
	}

}
