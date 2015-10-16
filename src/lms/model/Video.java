package lms.model;

public class Video extends Holding {	
	public Video(int code, String title, int loanFee) {
		super(code, title);
		
		this.loanFee = loanFee;
		this.loanPeriod = 7;
		
		// for videos, ate fee is days late * half the standard loan fee.
		this.penaltyFee = loanFee / 2;
	}
}
