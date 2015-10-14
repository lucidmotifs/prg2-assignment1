package lms.model;

public class PremiumMember extends AbstractMember {
	public PremiumMember(String id, String name) {
		super(id, name);
		
		this.initialCredit = this.currentCredit = 45;
	}
	
	public String toString() {
		return String.format("%s:%s", super.toString(), "PREMIUM");
	}
}
