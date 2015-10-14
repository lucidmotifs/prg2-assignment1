package lms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		
		// for cases where super() get called
		this.initialCredit = 0;
	}
	
	@Override
	public Collection<Holding> getCurrentHoldings() {
		
		return this.holdings;
	}
	
	@Override
	public BorrowingHistory getBorrowingHistory() {
		return this.history;
	}
	
	@Override
	public void borrowHolding(Holding h) {
		// subtract the holding loan fee from current credit
		this.setCredit(-h.loanFee);
		
		// add this holding to the holdings collection
		this.holdings.add(h);
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
