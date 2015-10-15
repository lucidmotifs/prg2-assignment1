package lms.model;

import lms.model.exception.InsufficientCreditException;
import lms.model.exception.MultipleBorrowingException;

public class StandardMember extends AbstractMember {
	public StandardMember(String id, String name) {
		super(id, name);
		
		this.initialCredit = this.currentCredit = 30;
	}
	
	/* Not necessary to override! TODO: Remove */
	/* Override method for borrowing to ensure correct credit handling */
	@Override
	public void borrowHolding(Holding h) throws MultipleBorrowingException, InsufficientCreditException {
		// Ensure we have enough credit to make loan
		if (this.currentCredit - h.loanFee >= 0) {
			super.borrowHolding(h);
		} else {
			throw new InsufficientCreditException();
		}
	}
	
	public String toString() {
		return String.format("%s:%s", super.toString(), "STANDARD");
	}
}
