package lms.model;

public abstract class Holding implements Comparable<Holding> {
	// protected:
	protected String title;
	
	protected int code;
	protected int loanFee;		
	protected int loanPeriod;
	protected int penaltyFee;
	
	public Holding(int code, String title) {
		this.code = code;
		this.title = title;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public int getLoanFee() {
		return this.loanFee;
	}
	
	public int calculateLateFee(int loanTime) {
		int lateFee_ = 0;
		
		// if item has been out longer than the set loan period
		if (loanTime > loanPeriod) {
			lateFee_ = (loanTime - loanPeriod) * penaltyFee;
		}
		
		return lateFee_;
	}
	
	public String toString() {
		// potential alternative to adding the 'type' property
		String className = this.getClass().getSimpleName();
		className = className.toUpperCase();
				
		return String.format("%s:%s:%d:%d:%s", this.code, this.title, this.loanFee, this.loanPeriod, className);
	}
	
	/* Method for doing comparisons between Holding objects.
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Holding compareHolding) {		
		int compareCode = ((Holding) compareHolding).getCode(); 
		
		//ascending order
		return this.code - compareCode;		
	}
}
