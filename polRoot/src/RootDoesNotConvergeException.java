
public class RootDoesNotConvergeException extends RootProblemException {
	/**
	 * This exception will be used to indicate when a set of
	 * bounds does not contain a root between them, or the maximum
	 * number of iterations is reached.
	 */
	private static final long serialVersionUID = 1L;
	private float estimate;
	
	public RootDoesNotConvergeException(String errorMessage){
		super(errorMessage);
	}
	
	public RootDoesNotConvergeException(float estimate) {
		super("The root does not converge");
		this.estimate = estimate;
	}

	public RootDoesNotConvergeException(String errorMessage, float estimate){
		super(errorMessage);
		this.estimate = estimate;
	}

	public float getEstimate() {
		return estimate;
	}
}
