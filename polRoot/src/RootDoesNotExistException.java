
public class RootDoesNotExistException extends RuntimeException {
	/**
	 * This exception will be used to indicate when a set of
	 * bounds does not contain a root between them, or the maximum
	 * number of iterations is reached.
	 */
	private static final long serialVersionUID = 1L;

	public RootDoesNotExistException(String errorMessage){
		super(errorMessage);
	}

}
