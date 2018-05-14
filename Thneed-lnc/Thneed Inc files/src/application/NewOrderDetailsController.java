//New Order Details Controller

package application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.io.File;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.collections.FXCollections;

public class NewOrderDetailsController {
	@FXML
	private TextField quantityField;
	@FXML
	private Button addToCartButton;
	@FXML
	private Button newCustomerButton;
	@FXML
	private Button newOrderDetailsCancelButton;
	@FXML
	private Button placeOrderButton;
	@FXML
	private Label orderID;
	@FXML
	private ComboBox<String> sizeCombo;
	@FXML
	private ComboBox<String> colorCombo;
	@FXML
	private ComboBox<String> customerIDCombo;
	//java.io.File orderFile = new java.io.File("order.txt");

	private OrdersController callingController;

	private ArrayList<Thneed> cart;// = new ArrayList<Thneed>();
	
	/*
	private ArrayList<String> tempCustList= new ArrayList<String>();
	
	public void setTempCustList(ArrayList<String> tempCustList) {
		this.tempCustList = tempCustList;
		System.out.println("tempCustListSize: " + tempCustList.size());
		ObservableList<String> obCust = FXCollections.observableArrayList(tempCustList);
		customerIDCombo.setItems(obCust);
	}
	*/

	public void setCustomerIDCombo() {
		customerIDCombo.getSelectionModel().clearSelection();
		
		ObservableList<String> obCustomer = FXCollections.observableArrayList();
		ArrayList<Customer> customerList = DataHolder.getInstance().getCustomers();
		for (Customer customer : customerList) {
			obCustomer.add(customer.getID()+":"+customer.getName());
		}
		customerIDCombo.setItems(obCustomer);
		
		if (obCustomer.size() > 0) {
			customerIDCombo.getSelectionModel().select(obCustomer.size() - 1);
		}
	}



	//private NewOrderDetailsController orderDetailController;

	//private ArrayList<Customer> customerList = new ArrayList<Customer>();

	

	
	
	public void setCallingController(OrdersController orderController) {
		this.callingController = orderController;
		
	}
	
	/*public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}*/

	/*private String[] sizeArray = {"S", "M", "L","XL"};
	 * 
	ObservableList<String> sizeList = FXCollections.observableArrayList(Arrays.asList(sizeArray));
	sizeCombo.setItems(sizeList);*/
	@FXML
	public void initialize() {
	ArrayList<String> size = new ArrayList<String>(Arrays.asList("S ", "M", "L", "XL"));
	ObservableList<String> obLocation = FXCollections.observableArrayList(size);
	sizeCombo.setItems(obLocation);
	ArrayList<String> color = new ArrayList<String>(Arrays.asList("Green", "Blue", "Red", "Yellow"));
	ObservableList<String> obLoc = FXCollections.observableArrayList(color);
	colorCombo.setItems(obLoc);
	
	//Set Default Value
	quantityField.setText("1");
	sizeCombo.getSelectionModel().select(0);
	colorCombo.getSelectionModel().select(0);
	
	
	/*ArrayList<Order> tempList = new ArrayList<Order>(callingController.getOrderList());
	if (tempList != null) {
		orderID.setText(String.valueOf(callingController.getOrderList().size()));
	}
	else {
		orderID.setText(String.valueOf(0));
	}
	int newID = callingController.getOrderList().size() + 1 ;*/
	//orderID.setText(String.valueOf(newID));//callingController.getOrderList().size() + 1 + "");
	//
	 /*Customer ted = new Customer("Ted", "20", "30", null);
	customerList.add(ted);
	customerList.add(new Customer("Bob", "dfsdfs", "djdlsjdfljs", null));
	ArrayList<String> ID = new ArrayList<String>();
	for(int i = 0; i < customerList.size();i++) {
		ID.add(Integer.toString(customerList.get(i).getID()));
		System.out.println("MADE IT");
	} 
	ObservableList<String> obLocat = FXCollections.observableArrayList(ID);
	customerIDCombo.setItems(obLocat);*/
	
		/*for(Customer c : tempCustList) {
			System.out.println(c.getID());
			custIDList.add(Integer.toString(c.getID()));
		}*/

		setCustomerIDCombo();
	}
	
	public void setOrderIDLabel(String input) {
		orderID.setText(input);
	}

	@FXML
	public void newCustomerButtonClicked(ActionEvent event) {
		
		//Load New Customer Window
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewCustomer.fxml"));
		AnchorPane dialogRoot;
		
	
		// setting the stage, creating a controller object and loading the window
		try {
			dialogRoot = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(dialogRoot);
			Stage dialogStage = new Stage();
			dialogStage.setScene(dialogScene);
			AddNewCustomerController customerController = (AddNewCustomerController) loader.getController();
			
		
			// passing the controller to New customer Details GUI
			customerController.setCallingController(this);
			//customerController.setOrdersController(callingController);
			customerController.setCustomerIDLabel(""+(DataHolder.getInstance().getLastCustomerId()+1));
			dialogStage.show();
			System.out.println("After dialogStage.show(), new customer window opened");
			
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void placeOrderButtonClicked(ActionEvent event) {

		int index = customerIDCombo.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			showAlert("Error", "Customer ID can't be empty");
			return;
		}
		Customer customer = DataHolder.getInstance().getCustomers().get(index);
		
		//SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Order toAdd = new Order();
		toAdd.getProdOrdered().addAll(cart);
		
		customer.addOrder(toAdd);
		DataHolder.getInstance().increaseOrderId();
		cart = null;
		callingController.populateUnfilledOrders();
		System.out.print("Your order was placed! The estimated fill date is: " + toAdd.getProjectedDate());
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	@FXML
	public void addToCartClicked(ActionEvent event) {
		if(cart == null) {
			cart = new ArrayList<Thneed>();
		}

		String sizeHold = sizeCombo.getValue().toString();
		System.out.println(sizeHold);
		String colorHold = colorCombo.getValue().toString();
		System.out.println(colorHold);
		
		//The field of quantity is required
		try {
			int quantHold = Integer.parseInt(quantityField.getText());
			System.out.println(quantHold);
			Thneed toAdd = new Thneed(sizeHold, colorHold, quantHold);
			System.out.println(toAdd.toString());
			cart.add(toAdd);
			//print the cart
			System.out.println("Size of Cart:" + cart.size());
		}
		catch (Exception e) {
			showAlert("Error", "Quantity should be set");
		}
		
		try {
			int index = customerIDCombo.getSelectionModel().getSelectedIndex();
			Customer customer = DataHolder.getInstance().getCustomers().get(index);
		}
		catch (Exception e) {
			showAlert("Error", "Customer ID can't be empty");
		}
		
	}
	
	
	//Event Listener for cancelButton
	@FXML
	public void cancelButtonClicked(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	

	private void showAlert(String header, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
	}
}

