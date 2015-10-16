package lms.model;

import lms.model.util.DateUtil;

public class HistoryRecord {
	// private:
	private String borrowedDate;
	private String returnDate;
	private Holding holding;
	private int lateFee;
	
	public HistoryRecord() {
		this.lateFee = 0;
	}
	
	public HistoryRecord(Holding h) {
		this();
		this.setHolding(h);		
	}
	
	public void setHolding(Holding h) {
		this.holding = h;
	}
	
	public Holding getHolding() {
		return this.holding;
	}
	
	
	/* Sets the return date of the holding, set the late fee if the holding
	 * complains about one, returns that fee.
	 */
	public int setReturnDate(String date) {
		this.borrowedDate = holding.getLastBorrowed();
		this.returnDate = date;
		
		// This is likely done in facade - but is more correct here anyway.
		DateUtil.getInstance().setDate(date);
		
		// get the total loan time
		int loanTime = DateUtil.getInstance().getElapsedDays(borrowedDate);
		
		// calculate late fees, if any.
		this.setLateFee(this.holding.calculateLateFee(loanTime));
		
		return this.getLateFee();
	}
	
	public void setLateFee(int amount) {
		this.lateFee = amount;
	}
	
	public int getLateFee() {
		return this.lateFee;
	}
	
	public String toString() {
		return String.format("%d:%d", this.holding.code, this.holding.loanFee);
	}
}
