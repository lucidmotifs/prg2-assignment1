package lms.model.facade;

import java.util.Collection;

import lms.model.Book;
import lms.model.BorrowingHistory;
import lms.model.HistoryRecord;
import lms.model.Holding;
import lms.model.LibraryCollection;
import lms.model.Member;
import lms.model.Video;
import lms.model.Library;
import lms.model.exception.InsufficientCreditException;
import lms.model.exception.MultipleBorrowingException;
import lms.model.exception.OverdrawnCreditException;
import lms.model.util.DateUtil;

public class LMSFacade implements LMSModel {

	@Override
	public boolean addHolding(Holding holding) {
		// retrieve library collection pointer
		LibraryCollection lc = Library.getInstance().getCollection();
		
		// add a new holding, return result
		return lc.addHolding(holding);
	}
	
	
	/* This method removes a holding from the library collection if no
	 * member has it out on loan (or there isn't a member yet)
	 */
	@Override
	public boolean removeHolding(int holdingId) {
		/* Before removing a holding, we must ensure no member has it
		out on loan. */
		
		// get the library member
		Member member_ = Library.getInstance().getMember();
		
		if (null != member_ && null != member_.getCurrentHoldings()) {		
			// get the instance of the holding from the ID
			Holding holding_ = this.getHolding(holdingId);
			
			// check if the holding is currently on load by the member.
			if (member_.getCurrentHoldings().contains(holding_)) {
				// if so, return false immediately.
				return false;
			}
		}
		
		// retrieve library collection pointer
		LibraryCollection lc = Library.getInstance().getCollection();
		
		// remove a holding, return result
		return lc.removeHolding(holdingId);		
	}
	

	@Override
	public Holding getHolding(int holdingId) {
		// retrieve library collection pointer
		LibraryCollection lc = Library.getInstance().getCollection();
		System.out.println(lc.getHolding(holdingId));
		
		return lc.getHolding(holdingId);
	}
	

	@Override
	public Holding[] getAllHoldings() {
		return Library.getInstance().getCollection().getAllHoldings();
	}
	
	
	/* returns number of books in current holding */
	@Override
	public int countBooks() {
		Holding[] holdings_ = this.getAllHoldings();
		
		int numBooks = 0;
		
		for (Holding h: holdings_) {
			if (h instanceof Book) {
				numBooks++;
			}
		}
		return numBooks;
	}

	/* returns number of videos in current holding */
	@Override
	public int countVideos() {
		Holding[] holdings_ = this.getAllHoldings();
		
		int numVideos = 0;
		
		for (Holding h: holdings_) {
			if (h instanceof Video) {
				numVideos++;
			}
		}
		return numVideos;
	}

	@Override
	public void borrowHolding(int holdingId) throws InsufficientCreditException, MultipleBorrowingException {
		// borrow this holding for the member
		this.getMember().borrowHolding(this.getHolding(holdingId));		
	}

	
	/* This method will return a holding and automatically charge a member any late fees
	 * accrued
	 */
	@Override
	public void returnHolding(int holdingId) throws OverdrawnCreditException {
		// return a holding and check for late fees
		this.getMember().returnHolding(this.getHolding(holdingId));
	}
	
	
	/* Convenience class for getting a member's borrowing history */
	@Override
	public HistoryRecord[] getBorrowingHistory() {
		// get the members borrowing history object
		BorrowingHistory history_ = this.getMember().getBorrowingHistory();
		
		// get the records from the history and transform into an Array
		Collection< HistoryRecord > c = history_.getRecords();
		
		if (null == c) {
			return null;
		} else {
			return c.toArray(new HistoryRecord[c.size()]);
		}
	}
	
	
	/* Gets a specific history record from the member using a holding ID */
	@Override
	public HistoryRecord getHistoryRecord(int holdingId) {
		// get the members borrowing history Array
		HistoryRecord[] records_ = getBorrowingHistory();
		
		if (null == records_) {
			return null;
		} else {		
			for (HistoryRecord r: records_) {
				// if we get a match, return immediately
				if (r.getHolding().getCode() == holdingId) {
					return r;
				}
			}
		
			// no results
			return null;
		}
	}
	
	
	/* Returns the currently borrowed holding of the member */
	@Override
	public Holding[] getBorrowedHoldings() {
		Collection< Holding > c = this.getMember().getCurrentHoldings();

		if (null == c) {
			return null;
		} else {
			return c.toArray(new Holding[c.size()]);
		}
	}

	
	/* Resets the member's credit to the initial value */
	@Override
	public void resetMemberCredit() {
		this.getMember().resetCredit();
	}
	

	/* Gets the remaining credit for the library member */
	@Override
	public int calculateRemainingCredit() {
		return this.getMember().getRemainingCredit();
	}

	
	@Override
	public int calculateTotalLateFees() {
		return this.getMember().getBorrowingHistory().calculateTotalLateFees();
	}
	

	/* Sets the current date for calculating late fees - etc. */
	@Override
	public void setDate(String currentDate) {
		DateUtil.getInstance().setDate(currentDate);
	}
	
	
	@Override
	public void addCollection(LibraryCollection collection) {
		Library.getInstance().setCollection(collection);
	}
	
	
	@Override
	public LibraryCollection getCollection() {
		return Library.getInstance().getCollection();
	}
	
	
	@Override
	public void addMember(Member member) {
		Library.getInstance().setMember(member);
	}
	
	
	@Override
	public Member getMember() {
		return Library.getInstance().getMember();
	}
}