import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rooter implements RooterInterface {
	
	private String name;
	private float[] poly;
	private int[] powers;
	private float[] dpoly;
	private int[] dpowers;
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
	
	//List to be formatted [root, iterations, outcome]
	public List<Object> bisect(float a, float b, int max){
		List<Object> record = new ArrayList<Object>(3);
		if((f(a)*f(b))<0) {
			int iterations = 0;
			boolean lneg = false;
			if(f(a)<0)
				lneg = true;
			float c = (a+b)/2;
			//This is where the loop will begin
			while(Math.abs(f(c)) >= Float.MIN_VALUE) {
				if(iterations >= max) {
					record.add(c);
					record.add(iterations);
					record.add("fail");
					return record;
				}
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
	
	//List to be formatted [root, iterations, outcome]
	public List<Object> bisect(float a, float b){
		return bisect(a, b, MAX_ITERATIONS);
	}

	public List<Object> newton(float x0 ,int max) {
		List<Object> record = new ArrayList<Object>(3);
		int iterations = 0;
		float a;
		float b;
		if(Math.abs(f(x0)) < 0.00001) { //This is delta
			record.add(null);
			record.add(null);
			record.add("fail");
		}else {
			while(Math.abs((a = f(x0))) >= Float.MIN_VALUE) {
				if(iterations >= max) {
					record.add(x0);
					record.add(iterations);
					record.add("fail");
					return record;
				}
				b = df(x0);
				x0=(b*x0-a)/b;
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
		if(x0 == x1) {
			record.add(null);
			record.add(null);
			record.add("fail");
		}
		else {
			int iterations = 0;
			float x2 =x1 - (f(x1)/((f(x1) - f(x0))/(x1-x0)));
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
				x0 = x1;
				x1 = x2;
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
		List<Object> record = new ArrayList<Object>(3);
		List<Object> record2 = new ArrayList<Object>(3);

		record = bisect(a,b,6);
		if(record.get(0) != null) {
			record2 = newton((float)record.get(0), MAX_ITERATIONS - 6);
			record.set(0, (float)record2.get(0));
			record.set(1, (int)record.get(1) + (int)record2.get(1));
			record.set(2, (String)record2.get(2));
		}
		return record;
	}
	
	public List<Object> hybrid(float a, float b, int max) {
		List<Object> record = new ArrayList<Object>(3);
		List<Object> record2 = new ArrayList<Object>(3);

		record = bisect(a,b,max/3);
		record2 = newton((float)record.get(0), max-max/3);
		
		record.set(0, (float)record2.get(0));
		record.set(1, (int)record.get(1) + (int)record2.get(1));
		record.set(2, (String)record2.get(2));
		return record;
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

	private void getDerivative(){
		this.dpoly = new float[poly.length - 1];
		for(int i = 0; i < dpoly.length; i++)
			dpoly[i] = poly[i]*powers[i];
		this.dpowers = Arrays.copyOfRange(powers, 1, powers.length);
	}

	public String getName() {
		return name;
	}
}//END OF CLASS
