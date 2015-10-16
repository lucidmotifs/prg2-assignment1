package lms.model;

import java.util.Collection;

import lms.model.exception.InsufficientCreditException;
import lms.model.exception.MultipleBorrowingException;
import lms.model.exception.OverdrawnCreditException;

public interface Member {
	public Collection<Holding> getCurrentHoldings();
	public BorrowingHistory getBorrowingHistory();
	public void borrowHolding(Holding h) throws MultipleBorrowingException, InsufficientCreditException;
	public void returnHolding(Holding h) throws OverdrawnCreditException;
	public void adjustCredit(int amount) throws InsufficientCreditException;
	public void adjustCredit(int amount, boolean force);
	public void resetCredit();
	public int getRemainingCredit();
	public String toString();
	
}
