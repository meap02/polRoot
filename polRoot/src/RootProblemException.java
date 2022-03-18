
public class RootProblemException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6977449520060398852L;

	public RootProblemException(String ErrorMessage) {
		super(ErrorMessage);
	}
	public RootProblemException() {
		super("The specified parameters to find this root are unusable");
	}

}
