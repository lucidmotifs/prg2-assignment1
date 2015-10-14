package lms.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Mikhail Perepletchikov
 */

public class DateUtil {

	private static DateUtil dateUtil;
	private String currentDate = null;
	// creates two calendars instances
	private Calendar cal1 = Calendar.getInstance();
	private	Calendar cal2 = Calendar.getInstance();

	/** 
	 * NOTE: the following code fragment illustrates example usage of a Singleton design pattern 
	 **/	
	// private constructor prevents instantiation from other classes
	private DateUtil() {}	
	// makes sure that only one instance of DateUtil can be created
	public static DateUtil getInstance() {
		if (dateUtil == null)
			dateUtil = new DateUtil();
		return dateUtil;
	}	
	/** end of Singleton example **/ 
	
	public String getDate() {
		return currentDate;
	}

	public void setDate(String currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * calculates the difference between the provided borrow date and current date. 
	 * NOTE: Make sure that the provided borrow date is kept in
	 * the required format (dd-MM-yyyy e.g. "25-01-2014")
	 * @param borrowDate
	 * @return number of days between two dates
	 */
	public int getElapsedDays(String borrowDate) {
		
		/** sets the dates for both calendar instances **/
		// first date is the holding borrow date
		cal1.setTime(createDate(borrowDate));
		// second date is the return (i.e. current) date
		cal2.setTime(createDate(currentDate));

		// calculates difference in milliseconds
		long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		// calculates difference in days
		long diffDays = diff / (24 * 60 * 60 * 1000);	
		return (int) diffDays;
	}
	
	/**
	 * calculates the difference between the provided borrow date and current date. 
	 * @param borrowDate
	 * @return number of days between two dates
	 */
	public int getElapsedDays(Calendar borrowDate) {
		
		/** sets the dates for both calendar instances **/
		// first date is the holding borrow date
		cal1.setTime(borrowDate.getTime());
		// second date is the return (i.e. current) date
		cal2.setTime(createDate(currentDate));

		// calculates difference in milliseconds
		long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		// calculates difference in days
		long diffDays = diff / (24 * 60 * 60 * 1000);	
		return (int) diffDays;
	}

	/**
	 * creates a Date instance from the provided String value
	 * @param dateString
	 * @return Date created from the dateString
	 */
	private Date createDate(String dateString) {
		DateFormat formatter;
		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.parse(dateString);
		} catch (ParseException e) {
			e.getStackTrace();
		}
		return null;
	}
}
