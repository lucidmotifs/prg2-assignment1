package lms.model.facade;

import lms.model.Member;
import lms.model.LibraryCollection;
import lms.model.Holding;
import lms.model.HistoryRecord;
import lms.model.exception.MultipleBorrowingException;
import lms.model.exception.OverdrawnCreditException;
import lms.model.exception.InsufficientCreditException;

/**
 * @author Mikhail Perepletchikov
 */

public interface LMSModel {
	
	/**
	 * Adds new member to library
	 */
	public void addMember(Member member);

	/**
	 * Adds new collection to library
	 */
	public void addCollection(LibraryCollection collection);

	/**
	 * Returns the (current) member
	 */
	public Member getMember();

	/**
	 * Returns the (current) library collection
	 */
	public LibraryCollection getCollection();

	/**
	 * Adds new holding to library collection. Returns false if the holding
	 * already exists (i.e. only one copy of a given holding can be added to the
	 * collection). Note that the original insertion order of Holdings must be preserved.
	 * As such, you need to choose the appropriate Collection type for storing Holdings.
	 */
	public boolean addHolding(Holding holding);

	/**
	 * Removes a holding from library collection. Returns false if the holding
	 * is currently on loan and therefore cannot be removed.
	 */
	public boolean removeHolding(int holdingId);

	/**
	 * Retrieves a holding according to the provided holdingId. Returns null if not found.
	 */
	public Holding getHolding(int holdingId);

	/**
	 * Returns all holdings held in the library collection. Returns null if collection is empty.
	 */
	public Holding[] getAllHoldings();
	
	/**
	 * Returns the count of Books in the library collection. NOTE: you are allowed
	 *  to use type checking (e.g. the instanceof operator) to achieve this
	 */
	public int countBooks();  
		
	/**
	 * Returns the count of Videos in the library collection. NOTE: you are allowed
	 *  to use type checking (e.g. the instanceof operator) to achieve this
	 */
	public int countVideos();  
	
	/**
	 * Allows a member to borrow a given holding. Deducts loan fee from member's
	 * borrowing credit. Throws exceptions when the borrowing eligibility is
	 * violated (refer to the assignment specs for further details).
	 */
	public void borrowHolding(int holdingId)
			throws InsufficientCreditException, MultipleBorrowingException;

	/**
	 * Allows a member to return a given holding. Deducts late fee, if
	 * applicable, from member's borrowing credit. Throws exception if the
	 * (Standard) member's borrowing credit is overdrawn (refer to the
	 * assignment specs for further details).
	 */
	public void returnHolding(int holdingId) throws OverdrawnCreditException;

	/**
	 * Returns a borrowing history (collection of all history records) of a
	 * member. Returns null if history is empty.
	 */
	public HistoryRecord[] getBorrowingHistory();

	/**
	 * Returns a specific history record from the borrowing history.
	 * Returns null if not found.
	 */
	public HistoryRecord getHistoryRecord(int holdingId);

	/**
	 * Returns a collection of all currently borrowed holdings.
	 * Returns null if collection is empty.
	 */
	public Holding[] getBorrowedHoldings();

	/**
	 * Resets member's credit to the original MAX credit value
	 */
	public void resetMemberCredit();

	/**
	 * Returns the remaining credit ($ value) for the member
	 */
	public int calculateRemainingCredit();

	/**
	 * Returns the total late fees ($ value) accumulated by the member as
	 * captured in the borrowing history
	 */
	public int calculateTotalLateFees();

	/**
	 * IMPORTANT: This method sets the "current" system date and is provided for
	 * testing purposes (refer to the assignment specs for further details)
	 */
	public void setDate(String currentDate);
}