package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataHolder {

	private static DataHolder instance;
	
	private final static String USER_STORE_PATH = "customers.ser";
	private final static String ORDER_STORE_PATH = "orders.ser";
	
	
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	private ArrayList<Order> orderList = new ArrayList<Order>();
	
	private int lastCustomerId = 0;
	private int lastOrderId = 0;
	
	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}

	private DataHolder(){
		initData();
	}
	
	public static DataHolder getInstance() {
		if (instance == null) {  
			instance = new DataHolder();  
		}
		return instance;
	}
	
	public ArrayList<Customer> getCustomers() {
		return customerList;
	}
	
	public Customer getCustomerById(int customerId) {
		for (Customer customer : customerList) {
			if (customer.getID() == customerId) 
				return customer;
		}
		return null;
	}
	
	public Order getOrderById(int orderId) {
		for (Order order : orderList) {
			System.out.println("getOrderById id = " + order.getOrderID());
			if (order.getOrderID() == orderId) return order;
		}
		return null;
	}
	
	public ArrayList<Order> getOrders() {
		return orderList;
	}
	
	private void initData() {
		Object result = readData(USER_STORE_PATH);
		if (result != null) {
			customerList = (ArrayList<Customer>) result;
			
			for (Customer customer : customerList) {
				if (customer.getID() > lastCustomerId) {
					lastCustomerId = customer.getID();
					System.out.println("lastCustomerId = "+lastCustomerId);
				}
			}
		}
		
		result = readData(ORDER_STORE_PATH);
		if (result != null) {
			orderList = (ArrayList<Order>) result;
			for (Order order : orderList) {
				if (order.getOrderID() > lastOrderId) {
					lastOrderId = order.getOrderID();
					System.out.println("lastOrderId = "+lastOrderId);
				}
			}
		}
	}
	
	public void addNewOrder(Order order) {
		orderList.add(order);
	}
	
	public String addNewCustomer(Customer customer) {
		
		for (Customer c : customerList) {
			if (c.getName().equals(customer.getName())) {
				return "Customer alread exists.";
			}
			if (c.getPhone().equals(customer.getPhone())) {
				return "Phone NO alread in use.";
			}
		}
		customerList.add(customer);
		return "success";
	}
	
	public void increaseCustomerId() {
		lastCustomerId++;
	}
	
	public void increaseOrderId() {
		lastOrderId++;
	}
	
	public int getLastCustomerId() {
		return lastCustomerId;
	}
	
	public int getLastOrderId() {
		return lastOrderId;
	}

	public void saveData() {
		
		writeData(customerList, USER_STORE_PATH);
		writeData(orderList, ORDER_STORE_PATH);
	}
	
	private Object readData(String path) {
		Object result = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			result = in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			System.out.println("File: "+path+" not found");
		} catch (Exception e) {
			System.out.println("Read File: "+path+" error");
		}
         

         return result;
	}
	
	private void writeData(Object object, String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			System.out.println("Saving File: "+path+" error");
		}
	}
}
