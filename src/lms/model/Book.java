package lms.model;

public class Book extends Holding {
	public Book(int code, String title) {
		super(code, title);
		
		this.loanFee = 10;
		this.loanPeriod = 28;
	}
}
