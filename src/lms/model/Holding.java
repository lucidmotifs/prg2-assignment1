package lms.model;

public abstract class Holding implements Comparable<Holding> {
	// protected:
	protected String title;
	
	protected int loanFee;
	protected int pentaltyFee;	
	protected int loanPeriod;
	protected int code;
	
	public Holding(int code, String title) {
		this.code = code;
		this.title = title;
	}
	
	public int getCode() {
		return this.code;
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
