
public class polRoot {
	public static void main(String[] args) {
		float[] testpoly = {1,0,-2};
		float[] testpoly2 = {1,0,3,-1};
		rooter calc = new rooter(testpoly2);
		try {
			System.out.println(calc.bisect(0, 1) + "\nIterations: " + calc.iterations);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			System.out.println(calc.newton(2) + "\nIterations: " + calc.iterations);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
