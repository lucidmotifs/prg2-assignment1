package lms.model;

import java.util.Collection;

import lms.model.exception.InsufficientCreditException;
import lms.model.exception.MultipleBorrowingException;
import lms.model.exception.OverdrawnCreditException;

public interface Member {
	public Collection<Holding> getCurrentHoldings();
	public BorrowingHistory getBorrowingHistory();
	public void borrowHolding(Holding h) throws MultipleBorrowingException, InsufficientCreditException;
	public void returnHolding(Holding h);
	public void returnHolding(Holding holding, String date) throws OverdrawnCreditException;
	public void setCredit(int amount) throws InsufficientCreditException;
	public void resetCredit();
	public int getRemainingCredit();
	public String toString();
	
}
