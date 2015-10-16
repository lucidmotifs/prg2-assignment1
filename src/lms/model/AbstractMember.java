package lms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lms.model.exception.InsufficientCreditException;
import lms.model.exception.MultipleBorrowingException;
import lms.model.exception.OverdrawnCreditException;
import lms.model.util.DateUtil;

public abstract class AbstractMember implements Member {
	// protected:
	protected String id;
	protected String fullName;
	protected int initialCredit;
	protected int currentCredit;
	protected List<Holding> holdings = new ArrayList<Holding>();
	protected BorrowingHistory history;
	
	public AbstractMember(String id, String name) {
		this.id = id;
		this.fullName = name;
		
		// for cases where super() gets called
		this.initialCredit = 0;
		
		// create borrowing history instance
		this.history = new BorrowingHistory();
	}
	
	
	/* */
	@Override
	public Collection<Holding> getCurrentHoldings() {
		if (this.holdings.isEmpty()) {
			return null;
		}
		return this.holdings;
	}
	
	
	/* */
	@Override
	public BorrowingHistory getBorrowingHistory() {
		return this.history;
	}
	
	
	/* Loans out a holding. Checks that the holding has not been borrowed before 
	 * and charges user a fee. */
	@Override
	public void borrowHolding(Holding h) throws MultipleBorrowingException, InsufficientCreditException {
		// check loan history for holding
		// throw exception if one exists, as a user can only borrow a holding once.
		if (this.history.hasRecordWithHolding(h)) {
			throw new MultipleBorrowingException();
		}
		
		// subtract the holding loan fee from current credit
		// this call may throw up an InsufficientCreditException
		adjustCredit( -h.loanFee );
		
		// we cannot add a HistoryRecord before a holding is returned,
		// so we must add a new property to holding 'lastBorrowed'
		// and assign it the borrowed date.
		// when a HistoryRecord is created and the holding added, the history record will
		// call setBorrowDate(record.getLastBorrowed())
		h.setLastBorrowed(DateUtil.getInstance().getDate());
		
		// and add this holding to the collection
		this.holdings.add(h);
	}
	
	
	/* */
	@Override
	public void returnHolding(Holding h) throws OverdrawnCreditException {
		// update the HistoryRecord before returning
		HistoryRecord record_ = this.history.getRecord(h);
		record_.setReturnDate(DateUtil.getInstance().getDate());
		
		try {
			// adjust the members credit
			adjustCredit( -record_.getLateFee() );
		} catch (InsufficientCreditException e) {
			// cannot return if there's no credit to pay the late fee
			throw new OverdrawnCreditException();
		}
		
		// remove the holding from this member
		this.holdings.remove(h);
	}
	
	
	/* Adjusts the credit of a member by the given amount. 
	 * TODO: consider removing the need to supply a negative number */
	@Override
	public void adjustCredit(int amount) throws InsufficientCreditException {
		// check the member has enough credit to make the loan
		if (this.currentCredit + amount < 0) {
			throw new InsufficientCreditException();
		}
		
		setCredit(currentCredit + amount);
	}
	
	
	/* Adjusts the credit of a member by a given amount. Can force the transaction
	 * as is necessary is situations like a premium member
	 */
	@Override
	public void adjustCredit(int amount, boolean force) {
		if (force) {
			setCredit(currentCredit + amount);
		} else {
			try {
				adjustCredit(amount);
			} catch (InsufficientCreditException e) {
				//ignore
			}
		}
	}
	
	
	/* */
	protected void setCredit(int amount) {
		this.currentCredit = amount;
	}
	
	/* */	
	@Override
	public void resetCredit() {
		this.currentCredit = this.initialCredit;
	}
	
	/* */	
	@Override
	public int getRemainingCredit() {
		return this.currentCredit;
	}
	
	
	/* */
	public String toString() {
		// potential alternative to adding the 'type' property in children - tightly coupled, not great really.
		String className = this.getClass().getSimpleName();
		className = className.replace("Member", "");
		className = className.toUpperCase();
		
		return String.format("%s:%s:%d", this.id, this.fullName, this.getRemainingCredit());
	}
}
