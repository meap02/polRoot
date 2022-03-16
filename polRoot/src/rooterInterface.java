
public interface rooterInterface {
	
	
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
	public float bisect(float a, float b)throws RootDoesNotExistException;
	
	/**
	 * This method will use Newton's method to find the root 
	 * of a function nearest to the start point
	 * @param a
	 * The starting point of the process
	 * @return
	 * The root nearest a if it exists. If it does not exist
	 * or the max iterations is reached, this will throw an exception.
	 */
	public float newton(float a)throws RootDoesNotExistException;
	
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
	public float secant(float a, float b)throws RootDoesNotExistException;

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
}
