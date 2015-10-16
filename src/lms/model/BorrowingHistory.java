package lms.model;

import java.util.List;
import java.util.ArrayList;

public class BorrowingHistory {
	// private:
	private List<HistoryRecord> items = new ArrayList<HistoryRecord>();
	
	public BorrowingHistory() {
		
	}
	
	/* This method checks to see if a holding belongs to any item of the HistoryRecord List */
	public boolean hasRecordWithHolding(Holding h) {
		for (HistoryRecord r: items) {
			if (r.getHolding().equals(h)) {
				return true;
			}
		}
		
		//no result
		return false;
	}
	
	
	/* Method that returns a specific history record, by holding */
	public HistoryRecord getRecord(Holding h) {
		for (HistoryRecord r: items) {
			if (r.getHolding().equals(h)) {
				// return result
				return r;
			}
		}
		
		// no result
		return null;
	}
	
	
	/* Convenience method for adding a HistoryRecord */
	public void addRecord(HistoryRecord r) {
		this.items.add(r);
	}
	
	
	/* Return all the records for this History */
	public List<HistoryRecord> getRecords() {
		return this.items;
	}
}
