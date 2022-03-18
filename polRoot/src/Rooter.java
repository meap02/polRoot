import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Rooter implements RooterInterface {
	
	private String name;
	private float[] poly;
	private int[] powers;
	private float[] dpoly;
	private int[] dpowers;
	private int iterations;
	private final int MAX_ITERATIONS = 10000;
	
	public Rooter(float[] array) {
		powers = new int[array.length];
		for(int i = 0; i < array.length; i++)
			powers[i]= array.length - i - 1;
		this.poly = Arrays.copyOf(array, array.length);
	}
	
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

	public float bisect(float a, float b, int max) throws RootProblemException {
		if((f(a)*f(b))<0) {
			iterations = 0;
			boolean lneg = false;
			if(f(a)<0)
				lneg = true;
			float c = (a+b)/2;
			//This is where the loop will begin
			while(Math.abs(f(c)) >= Float.MIN_VALUE) {
				if(iterations >= max)
					throw new RootDoesNotConvergeException(c);
				c=(a+b)/2;
				if(f(c) < 0)
					if(lneg)
						a=c;
					else
						b=c;
				else if(lneg)
					b=c;
				else
					a=c;
				iterations++;
			}
			return c;
		}else
			throw new RootProblemException("A single root does not exist within the bounds " + 
												 a + " and " + b);
	}
	
	public float bisect(float a, float b) throws RootProblemException {
		return bisect(a, b, MAX_ITERATIONS);
	}

	public float newton(float x0 ,int max) throws RootProblemException {
		iterations = 0;
		float a;
		float b;
		if(Math.abs(f(x0)) < 0.00001)//This is delta
			throw new RootProblemException("The slope at " + x0 + " is too small to use this method");
		while(Math.abs((a = f(x0))) >= Float.MIN_VALUE) {
			if(iterations >= max)
				throw new RootDoesNotConvergeException(x0);
			b = df(x0);
			x0=(b*x0-a)/b;
			iterations++;
		}
		return x0;
	}
	
	public float newton(float x0) throws RootProblemException {
		return newton(x0, MAX_ITERATIONS);
	}

	public float secant(float x0, float x1, int max) throws RootProblemException {
		iterations = 0;
		float x2 =x1 - (f(x1)/((f(x1) - f(x0))/(x1-x0)));
		while(Math.abs(f(x2))>Float.MIN_VALUE) {
			if(iterations > max)
					throw new RootDoesNotConvergeException(x2);
			if(x0 == x1)//This will ensure that f(x1) - f(x0) will not be 0 and cause a NaN error
				break;
			x2 =x1 - (f(x1)/((f(x1) - f(x0))/(x1-x0)));
			x0 = x1;
			x1 = x2;
			iterations++;
		}
		return x2;
	}
	
	public float secant(float x0, float x1) throws RootProblemException {
		return secant(x0, x1, MAX_ITERATIONS);
	}
	
	public float hybrid(float a, float b) throws RootProblemException {
		float c;
		try {
			c = bisect(a,b,6);
		}catch(RootDoesNotConvergeException e){
			c = e.getEstimate();
		}
		int conIter = iterations;
		try {
			c = newton(c, MAX_ITERATIONS - 6);
		} catch (RootDoesNotConvergeException e) {
			c = e.getEstimate();
		}
		iterations += conIter;
		return c;
	}
	
	public float hybrid(float a, float b, int max) throws RootProblemException {
		float c;
		try {
			c = bisect(a,b,max/3);
		}catch(RootDoesNotConvergeException e){
			c = e.getEstimate();
		}
		int conIter = iterations;
		try {
			c = newton(c, max-max/3);
		} catch (RootDoesNotConvergeException e) {
			c = e.getEstimate();
		}
		iterations += conIter;
		return c;
	}


	public float f(float x) {
		float temp = 0;
		for(int i = 0; i < poly.length; i++) {
			temp += poly[i] * Math.pow(x, powers[i]);
		}
		return temp;
	}
	
	public float df(float x) {
		if(dpoly == null || dpowers == null)
			getDerivative();
		float temp = 0;
		for(int i = 0; i < dpoly.length; i++) {
			temp += dpoly[i] * Math.pow(x, dpowers[i]);
		}
		return temp;
	}
	
	public int getIterations() {
		return iterations;
	}

	private void getDerivative(){
		this.dpoly = new float[poly.length - 1];
		for(int i = 0; i < dpoly.length; i++)
			dpoly[i] = poly[i]*powers[i];
		this.dpowers = Arrays.copyOfRange(powers, 1, powers.length);
	}
}//END OF CLASS
