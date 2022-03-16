
public class polRoot {
	public static void main(String[] args) {
		float[] testpoly = {1,0,-2};
		rooterInterface calc = new rooter(testpoly);
		System.out.println(calc.f(2));
		System.out.println(calc.bisect(-2, 2));
	}

}
