package lms.model.facade;

import java.util.Collection;

import lms.model.Book;
import lms.model.HistoryRecord;
import lms.model.Holding;
import lms.model.LibraryCollection;
import lms.model.Member;
import lms.model.Video;
import lms.model.Library;
import lms.model.exception.InsufficientCreditException;
import lms.model.exception.MultipleBorrowingException;
import lms.model.exception.OverdrawnCreditException;

public class LMSFacade implements LMSModel {
	// private:
	private String date;	
	private Library library = new Library(); 

	@Override
	public boolean addHolding(Holding holding) {
		// retrieve library collection pointer
		LibraryCollection lc = this.library.getCollection();
		
		// add a new holding, return result
		return lc.addHolding(holding);
	}

	@Override
	public boolean removeHolding(int holdingId) {
		// retrieve library collection pointer
		LibraryCollection lc = this.library.getCollection();
		
		// remove a holding, return result
		return lc.removeHolding(holdingId);		
	}

	@Override
	public Holding getHolding(int holdingId) {
		// retrieve library collection pointer
		LibraryCollection lc = this.library.getCollection();
		
		return lc.getHolding(holdingId);
	}

	@Override
	public Holding[] getAllHoldings() {
		return this.library.getCollection().getAllHoldings();
	}

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

	@Override
	public void returnHolding(int holdingId) throws OverdrawnCreditException {
		// return a holding and check for late fees
		this.getMember().returnHolding(this.getHolding(holdingId));
	}

	@Override
	public HistoryRecord[] getBorrowingHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistoryRecord getHistoryRecord(int holdingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Holding[] getBorrowedHoldings() {
		Collection< Holding > c = this.getMember().getCurrentHoldings();
		
		// TODO: refactor this to be clearer/neater
		if (null == c) {
			return null;
		} else {
			return c.toArray(new Holding[c.size()]);
		}
	}

	@Override
	public void resetMemberCredit() {
		this.getMember().resetCredit();
	}

	@Override
	public int calculateRemainingCredit() {
		return this.getMember().getRemainingCredit();
	}

	@Override
	public int calculateTotalLateFees() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDate(String currentDate) {
		this.date = currentDate;
	}
	
	public void addCollection(LibraryCollection collection) {
		this.library.setCollection(collection);
	}
	
	public LibraryCollection getCollection() {
		return this.library.getCollection();
	}
	
	public void addMember(Member member) {
		this.library.setMember(member);
	}
	
	public Member getMember() {
		return this.library.getMember();
	}
}