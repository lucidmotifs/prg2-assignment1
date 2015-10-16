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
		
		// create a new history record for this holding and add it to the
		// members history
		HistoryRecord record_ = new HistoryRecord(h);
		// add the current date
		record_.setBorrowDate(DateUtil.getInstance().getDate());
		this.history.addRecord(record_);
		
		// subtract the holding loan fee from current credit
		// this call may throw up an InsufficientCreditException
		this.setCredit( -h.loanFee );
		
		// and add this holding to the collection
		this.holdings.add(h);
	}
	
	
	/* */
	@Override
	public void returnHolding(Holding h) {
		// update the HistoryRecord before returning
		HistoryRecord record_ = this.history.getRecord(h);
		record_.setReturnDate(DateUtil.getInstance().getDate());
		
		// remove the holding from this member
		this.holdings.remove(h);
	}
	
	
	/* Calculates a return for late fees, and then returns the holding */
	public void returnHolding(Holding holding, String date) throws OverdrawnCreditException {
		
	}
	
	
	/* Adjusts the credit of a member by the given amount. 
	 * TODO: consider removing the need to supply a negative number */
	@Override
	public void setCredit(int amount) throws InsufficientCreditException {
		// check the member has enough credit to make the loan
		if (this.currentCredit + amount < 0) {
			throw new InsufficientCreditException();
		}
		
		this.currentCredit += amount;
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
