package application;
import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Order implements Serializable{
//
//Instantiate Data Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderID = 0;
	ArrayList<Thneed> prodOrdered = new ArrayList<Thneed>();
	
	int customerID;
	Date dateOrdered;
	Date dateFilled;
	Date projectedDate;
	
	//Constructor with Data Fields
	public Order() {
		this.orderID = DataHolder.getInstance().getLastOrderId() + 1;
		this.dateOrdered = new Date();
		this.dateFilled = null;
		this.projectedDate = addDays(dateOrdered,1);
	}
	
	public Order(int orderId, int customerID, String oDate, String fDate, String pDate) {
		this.customerID = customerID;
		this.orderID = orderId;

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.dateOrdered = sdf.parse(oDate);
		} catch (ParseException e) {
		}
		
		try {
			this.dateFilled = sdf.parse(fDate);
		} catch (ParseException e) {
		}
		
		try {
			this.projectedDate = sdf.parse(pDate);
		} catch (ParseException e) {
		}
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	//list of thneeds accessor
	public ArrayList<Thneed> getProdOrdered(){
		return this.prodOrdered;
	}
	
	public void addThneed(Thneed thneed) {
		prodOrdered.add(thneed);
	}
	
	//Customer accessor
	public int getCustomerID() {
		return this.customerID;
	}
	//date ordered accessor
	public Date getDateOrdered() {	
		return this.dateOrdered;
	}
	//date filled accessor
	public Date getDateFilled() {
		return this.dateFilled;
	}
	
	//date filled accessor
	public Date getProjectedDate() {
		return this.projectedDate;
		}
	
	//set the date the order was filled
	public void setDateFilled() {
		this.dateFilled = new Date();
		System.out.println("DF " + dateFilled);
	}
	
	// method for getting projected fill date
	public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	@Override
	public String toString() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder builder = new StringBuilder("Order:");
		builder.append(orderID).append(",");
		builder.append(customerID).append(",");
		builder.append(dt.format(dateOrdered)).append(",");
		builder.append(dateFilled==null?"null":dt.format(dateFilled)).append(",");
		builder.append(projectedDate==null?"null":dt.format(projectedDate));
		

		return builder.toString();
	}
}