package application;
import java.io.Serializable;
import java.util.*;


public class Customer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int customerID = 0;
	private String name = "";
	private String address = "";
	private String phone = "";
//	private ArrayList<Order> orders = new ArrayList<Order>();
	
	
	//no-arg constructor
	public Customer() {
	}
	
	public Customer(int id, String name, String address, String phone) {
		
		//set data fields
		this.customerID = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		
	}
	
	//accessor methods
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public ArrayList<Order> getOrders() {
		ArrayList<Order> allOrder = DataHolder.getInstance().getOrders();
		ArrayList<Order> orderList = new ArrayList<>();
		
		for (Order order : allOrder) {
			if (customerID == order.getCustomerID()) {
				orderList.add(order);
			}
		}
		return orderList;
	}
	
	public void addOrder(Order order) {
		order.setCustomerID(getID());
		DataHolder.getInstance().addNewOrder(order);
	}
	
	@Override
	public String toString() {
		//StringBuilder Source code cited: https://docs.oracle.com/javase/tutorial/java/data/buffers.html
		StringBuilder builder = new StringBuilder();
		builder.append(customerID).append(",");
		builder.append(name).append(",");
		builder.append(phone).append(",");
		builder.append(address);
		
		return builder.toString();
	}

	public int getID() {
		return customerID;
	}
}

