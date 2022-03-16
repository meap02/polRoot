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
			while(Math.abs(f(c)) >= Float.MIN_VALUE) {
				if(iterations >= MAX_ITERATIONS)
					throw new RootDoesNotExistException("The max number of iterations of " + 
														 MAX_ITERATIONS +
														 " has been reached with a value of " + c);
				c=(a+b)/2;
				if((f(c) < 0) && lneg) //check to make sure this works for both sides later
					a=c;
				else
					b=c;
				iterations++;
			}
			return c;
		}else
			throw new RootDoesNotExistException("A single root does not exist within the bounds " + 
												 a + " and " + b);
	}

	public float newton(float x0) {
		iterations = 0;
		float a;
		float b;
		while((a = f(x0)) >= Float.MIN_VALUE) {
			if(iterations >= MAX_ITERATIONS)
				throw new RootDoesNotExistException("The max number of iterations has been reached with a value of " + x0);
			b = df(x0);
			x0=(b*x0-a)/b;
			iterations++;
		}
		return x0;
	}

	public float secant(float a, float b) {
		return 0;
	}

	public float f(float x) {
		float temp = 0;
		for(int i = 0; i < poly.length; i++) {
			temp += poly[i] * pow(x, powers[i]);
		}
		return temp;
	}
	
	public float df(float x) {
		if(dpoly == null || dpowers == null)
			getDerivative();
		float temp = 0;
		for(int i = 0; i < dpoly.length; i++) {
			temp += dpoly[i] * pow(x, dpowers[i]);
		}
		return temp;
	}
	
	private float pow(float a, int b) {
		if(b != 0){
			float temp = a;
			for(int i = 1; i < b; i++)
				temp *= a;
			return temp;
		}else
			return 1;
	}
	
	
	/*
	public float foo(float a, int b) {
		return pow(a,b);
	}
	*/
	
	
	private void getDerivative(){
		this.dpoly = new float[poly.length - 1];
		for(int i = 0; i < dpoly.length; i++)
			dpoly[i] = poly[i]*powers[i];
		this.dpowers = Arrays.copyOfRange(powers, 1, powers.length);
	}

}
