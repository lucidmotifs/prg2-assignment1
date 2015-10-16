package lms.model;

import lms.model.exception.OverdrawnCreditException;

public class PremiumMember extends AbstractMember {
	public PremiumMember(String id, String name) {
		super(id, name);
		
		this.initialCredit = this.currentCredit = 45;
	}
	
	public void returnHolding(Holding h) throws OverdrawnCreditException {
		try {
			super.returnHolding(h);
		} catch (OverdrawnCreditException e) {
			// create instance of the Record
			HistoryRecord record_ = this.history.getRecord(h);
			
			// return the holding anyway
			this.holdings.remove(h);
			
			// and force the transaction
			this.adjustCredit( -record_.getLateFee(), true );
		}
	}
	
	public String toString() {
		return String.format("%s:%s", super.toString(), "PREMIUM");
	}
}
