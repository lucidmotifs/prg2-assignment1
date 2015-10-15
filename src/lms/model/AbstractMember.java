package lms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lms.model.exception.MultipleBorrowingException;

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
	public void borrowHolding(Holding h) throws MultipleBorrowingException {
		// check loan history for holding
		// throw exception if one exists, as a user can only borrow a holding once.
		if (this.history.getRecords().contains(h)) {
			throw new MultipleBorrowingException();
		}
		
		// otherwise subtract the holding loan fee from current credit
		this.setCredit( -h.loanFee );
		
		// and add this holding to the holdings collection
		this.holdings.add(h);
	}
	
	
	@Override
	public void returnHolding(Holding h) {
		// create a HistoryRecord before returning
		HistoryRecord record_ = new HistoryRecord();
		record_.setHolding(h);
		
		// add to BorrowingHistory 
		this.history.addRecord(record_);
		
		// remove the holding from this member
		this.holdings.remove(h);
	}
	
	
	@Override
	public void setCredit(int amount) {
		this.currentCredit += amount;
	}
	
	@Override
	public void resetCredit() {
		this.currentCredit = this.initialCredit;
	}
	
	@Override
	public int getRemainingCredit() {
		return this.currentCredit;
	}
	
	public String toString() {
		// potential alternative to adding the 'type' string in children
		String className = this.getClass().getSimpleName();
		className = className.replace("Member", "");
		className = className.toUpperCase();
		
		return String.format("%s:%s:%d", this.id, this.fullName, this.getRemainingCredit());
	}
}
