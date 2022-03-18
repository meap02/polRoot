import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rooter implements RooterInterface {
	
	private String name;
	private float[] poly;//keep the coefficients of the polynomial
	private int[] powers;//keep the powers of the polynomial
	private float[] dpoly;//<both these will only be used if derivative is needed to be calculated
	private int[] dpowers;//<
	private final int MAX_ITERATIONS = 10000; //Default maximum iterations
	
	public Rooter(float[] array) { //Used only when testing code
		powers = new int[array.length];
		for(int i = 0; i < array.length; i++)
			powers[i]= array.length - i - 1;
		this.poly = Arrays.copyOf(array, array.length);
	}
	
	/*
	 * Reads data from a file with text
	 */
	public Rooter(String input) throws IOException {
		if (input.contains("\\"))
			this.name = input.substring(input.lastIndexOf("\\", input.length()) + 1, input.lastIndexOf(".", input.length()));
		else if(input.contains("."))
			this.name = input.substring(0, input.lastIndexOf(".", input.length()));
		else
			this.name = input;
		
		Path filename = Path.of(input);
		List<String> lines = Files.readAllLines(filename);
		Object[] wrking = lines.toArray();
		powers = new int[Integer.valueOf((String)wrking[0]) + 1];
		for(int i = 0; i < powers.length; i++)
			powers[i] = powers.length - i -1;
		
		String[] polystr = ((String)wrking[1]).split(" ");
		this.poly = new float[polystr.length];
		for(int i = 0; i < polystr.length; i++)
			poly[i] = Float.valueOf(polystr[i]);
	}
	
	//List to be formatted [root, iterations, outcome]
	public List<Object> bisect(float a, float b, int max){
		List<Object> record = new ArrayList<Object>(3);//This will return all 3 variables that I need
		if((f(a)*f(b))<0) { //Check if a root exists between these points
			int iterations = 0;
			boolean lneg = false;
			if(f(a)<0)
				lneg = true;
			float c = (a+b)/2;
			//This is where the loop will begin
			while(Math.abs(f(c)) >= Float.MIN_VALUE) { //This is our epsilon
				if(iterations >= max) {//ensure that the function does not continue forever
					record.add(c);
					record.add(iterations);
					record.add("fail");
					return record;
				}
				c=(a+b)/2; // calculate midpoint
				if(f(c) < 0)//check if its above or below the x axis
					if(lneg)
						a=c;//<move the a or b accordingly
					else
						b=c;//<
				else if(lneg)
					b=c;//<
				else
					a=c;//<
				iterations++;
			}
			record.add(0, c);
			record.add(1, iterations);
			record.add(2, "success");
		}else {
			 record.add(null);
			 record.add(null);
			 record.add("fail");
		}
		return record;
	}
	
	/*
	 * I use these overloaded methods to take care of the "default" max value when needed
	 */
	public List<Object> bisect(float a, float b){
		return bisect(a, b, MAX_ITERATIONS);
	}

	public List<Object> newton(float x0 ,int max) {
		List<Object> record = new ArrayList<Object>(3);
		int iterations = 0;
		float a;//this will be f(x) for each iteration
		float b;//this will be derivative of f(x) for each iteration
		if(Math.abs(f(x0)) < 0.00001) { //This is delta
			record.add(null);
			record.add(null);
			record.add("fail");
		}else {
			while(Math.abs((a = f(x0))) >= Float.MIN_VALUE) {//this is epsilon
				if(iterations >= max) {
					record.add(x0);
					record.add(iterations);
					record.add("fail");
					return record;
				}
				b = df(x0);//calculate derivative
				x0=(b*x0-a)/b;//fancy algebra to solve for x intercept of a line at point f(x) with slope f'(x)
				iterations++;
			}
			record.add(x0);
			record.add(iterations);
			record.add("success");
		}
		return record;
	}
	
	public List<Object> newton(float x0) {
		return newton(x0, MAX_ITERATIONS);
	}

	public List<Object> secant(float x0, float x1, int max) {
		List<Object> record = new ArrayList<Object>(3);
		if(x0 == x1) { // x0 and x1 cannot be the same on start, creates errors
			record.add(null);
			record.add(null);
			record.add("fail");
		}
		else {
			int iterations = 0;
			float x2 =x1 - (f(x1)/((f(x1) - f(x0))/(x1-x0))); // calculates the x intercept based on the secant line
			while(Math.abs(f(x2))>Float.MIN_VALUE) {
				if(iterations >= max) {
					record.add(x0);
					record.add(iterations);
					record.add("fail");
					return record;
				}
				if(x0 == x1) //This will ensure that f(x1) - f(x0) will not be 0 and cause a NaN error
					break;
				x2 =x1 - (f(x1)/((f(x1) - f(x0))/(x1-x0)));
				x0 = x1;//<shift the new and old positions
				x1 = x2;//<
				iterations++;
			}
			record.add(x2);
			record.add(iterations);
			record.add("success");
		}
		return record;
	}
	
	public List<Object> secant(float x0, float x1) {
		return secant(x0, x1, MAX_ITERATIONS);
	}
	
	//List to be formatted [root, iterations, outcome]
	public List<Object> hybrid(float a, float b) {
		List<Object> record = new ArrayList<Object>(3); //for the bisection method return value
		List<Object> record2 = new ArrayList<Object>(3);//for the newton return value

		record = bisect(a,b,6); //use 6 by default for maximum efficiency 
		if(record.get(0) != null) { //this ensures that the bisection function did not fail
			record2 = newton((float)record.get(0), MAX_ITERATIONS - 6); //run newton until satisfied
			record2.set(1, (int)record.get(1) + (int)record2.get(1));//add iteration count together
		}
		return record2;
	}
	
	public List<Object> hybrid(float a, float b, int max) {
		List<Object> record = new ArrayList<Object>(3);
		List<Object> record2 = new ArrayList<Object>(3);

		record = bisect(a,b,max/3);//if max iterations is specified, then the first 3rd will be bisection
		if(record.get(0) != null) { //this ensures that the bisection function did not fail
			record2 = newton((float)record.get(0), max-max/3);//the rest will be done with newton
			record2.set(1, (int)record.get(1) + (int)record2.get(1));
		}
		return record2;
	}


	public float f(float x) {
		float temp = 0;
		for(int i = 0; i < poly.length; i++) {
			temp += poly[i] * Math.pow(x, powers[i]);
		}
		return temp;
	}
	
	public float df(float x) {
		if(dpoly == null || dpowers == null)//calculate the derivative if not already done so
			getDerivative();
		float temp = 0;
		for(int i = 0; i < dpoly.length; i++) {
			temp += dpoly[i] * Math.pow(x, dpowers[i]);
		}
		return temp;
	}

	private void getDerivative(){ //function to set the derivative variables
		this.dpoly = new float[poly.length - 1];
		for(int i = 0; i < dpoly.length; i++)
			dpoly[i] = poly[i]*powers[i];
		this.dpowers = Arrays.copyOfRange(powers, 1, powers.length);
	}

	public String getName() {
		return name;
	}
}//END OF CLASS
