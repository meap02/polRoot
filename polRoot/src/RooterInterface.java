import java.util.List;

public interface RooterInterface {
	
	
	/**
	 * This method will use the process of bisection to find 
	 * the roots of this objects function
	 * @param a
	 * The left-most bound of the search
	 * @param b
	 * The right-most bound of the search
	 * @return
	 * The root located between a and b if it exists. If it does not
	 * or the max iterations is reached, this will throw an exception.
	 */
	public List<Object> bisect(float a, float b);
	
	public List<Object> bisect(float a, float b, int max);
	
	/**
	 * This method will use Newton's method to find the root 
	 * of a function nearest to the start point
	 * @param a
	 * The starting point of the process
	 * @return
	 * The root nearest a if it exists. If it does not exist
	 * or the max iterations is reached, this will throw an exception.
	 */
	public List<Object> newton(float a);
	
	public List<Object> newton(float a, int max);

	/**
	 * This method will use the secant method to solve for a root of the equation
	 * @param a
	 * The left-most bound of the search
	 * @param b
	 * The right-most bound of the search
	 * @return
	 * The root in between a and b if it exists. If it does not exist
	 * or the max iterations is reached, this will throw an exception.
	 */
	public List<Object> secant(float a, float b);

	public List<Object> secant(float a, float b, int max);

	/**
	 * This method will use a combination of 
	 * @param a
	 * @param b
	 * @return
	 * @throws RootDoesNotExistException
	 */
	public List<Object> hybrid(float a, float b);

	public List<Object> hybrid(float a, float b, int max);

	/**
	 * This method will evaluate a given x value
	 * within this functions polynomial. 
	 * @param x
	 * @return
	 */
	public float f(float x);
	
	/**
	 * This method will evaluate a given x value
	 * within this functions derivative. 
	 * @param x
	 * @return
	 */
	public float df(float x);
	
	/**
	 * Used to get the file name for writing purposes
	 * @return Name of the file read when initialized
	 */
	public String getName();
	/**
	 * Used to get the iterations that a method took after a single method has run. Will reset when new method is run
	 * @return Number of iterations taken
	 */
}
