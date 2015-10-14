package lms.model;

import java.util.Collection;

public interface Member {
	public Collection<Holding> getCurrentHoldings();
	public BorrowingHistory getBorrowingHistory();
	public void borrowHolding(Holding h);
	public void returnHolding(Holding h);
	public void setCredit(int amount);
	public void resetCredit();
	public int getRemainingCredit();
	public String toString();
}
