package lms.model;

public class HistoryRecord {
	// private:
	private Holding holding;
	private int lateFee;
	
	public HistoryRecord() {
		this.lateFee = 0;
	}
	
	public void setHolding(Holding h) {
		this.holding = h;
	}
	
	public Holding getHolding() {
		return this.holding;
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
