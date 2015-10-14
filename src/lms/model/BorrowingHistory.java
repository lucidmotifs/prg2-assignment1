package lms.model;

import java.util.List;
import java.util.ArrayList;

public class BorrowingHistory {
	// private:
	private List<HistoryRecord> items = new ArrayList<HistoryRecord>();
	
	public BorrowingHistory() {
		
	}
	
	
	/* Convenience method for adding a HistoryRecord */
	public void addRecord(HistoryRecord r) {
		this.items.add(r);
	}
	
	
	/* Return all the records for this History */
	public List<HistoryRecord> getRecords() {
		if (this.items.isEmpty()) {
			return null;
		}
		
		return this.items;
	}
}
