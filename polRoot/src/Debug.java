
public class Debug {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		float[] testpoly = {1,0,-2};
		float[] testpoly2 = {1,0,3,-1};
		Rooter calc = new Rooter(testpoly2);
		try {
			System.out.println("Bisection Method " + calc.bisect(-2, 1) + "\nIterations: " + calc.getIterations());
		} catch (RootDoesNotConvergeException e) {
			System.out.println(e + " " + e.getEstimate());
		}
		try {
			System.out.println("Newton Method: " + calc.newton(0) + "\nIterations: " + calc.getIterations());
		} catch (RootDoesNotConvergeException e) {
			System.out.println(e.getMessage() + " " + e.getEstimate());
		} catch (RootProblemException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			System.out.println("Secant Method: " + calc.secant(1, 0) + "\nIterations: " + calc.getIterations());
		} catch (RootDoesNotConvergeException e) {
			System.out.println(e + " " + e.getEstimate());
		}
		
		try {
			System.out.println("Hybrid Method: " + calc.hybrid(-2, 1) + "\nIterations: " + calc.getIterations());
		} catch (RootDoesNotConvergeException e) {
			System.out.println(e + " " + e.getEstimate());
		}
	}
}
