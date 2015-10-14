package lms.model.exception;

/**
 * @author Mikhail Perepletchikov
 */
@SuppressWarnings("serial")
public class InsufficientCreditException extends LMSException {
	
	public InsufficientCreditException() {
		super("Insufficient Credit Exception");
	}
	public InsufficientCreditException(String message) {
		super(message);
	}
}