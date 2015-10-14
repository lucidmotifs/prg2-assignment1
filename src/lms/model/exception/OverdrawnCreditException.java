package lms.model.exception;

/**
 * @author Mikhail Perepletchikov
 */
@SuppressWarnings("serial")
public class OverdrawnCreditException extends LMSException {
	
	public OverdrawnCreditException() {
		super("Overdrawn Credit Exception");
	}
	public OverdrawnCreditException(String message) {
		super(message);
	}
}