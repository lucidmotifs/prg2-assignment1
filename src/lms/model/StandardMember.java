package lms.model;

public class StandardMember extends AbstractMember {
	public StandardMember(String id, String name) {
		super(id, name);
		
		this.initialCredit = this.currentCredit = 30;
	}
	
	@Override
	public void borrow(Holding h) {
		super.borrow(h);
	}
	
	public String toString() {
		return String.format("%s:%s", super.toString(), "STANDARD");
	}
}
