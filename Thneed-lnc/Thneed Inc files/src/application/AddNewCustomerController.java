//add new customer controller

package application;


import java.util.ArrayList;

import com.sun.xml.internal.ws.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.io.File;
import javafx.scene.control.TextField;

import javafx.scene.control.Label;

public class AddNewCustomerController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField phoneField;
	@FXML
	private Button addCustomerCancelButton;
	@FXML
	private Button addCustomerButton;
	@FXML
	private Label newCustomerID;
	
	private NewOrderDetailsController callingController;
	//private AddNewCustomerController customerController;

	java.io.File customerFile = new java.io.File("customer.txt");
	//private ArrayList<Customer> customerList;
	
	/*@FXML
	public void initialize() {
		newCustomerID.setText(ordersController.getCustomerList().size() + 1 + "");
	}*/
	
	//Event Listener for cancelButton
	@FXML
	public void cancelButtonClicked(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
		
	}
	
	@FXML
	public void addCustomerButtonClicked(ActionEvent event) {
		
		String name = nameField.getText();
		if ("".equals(name)) {
			showAlert("Error", "Customer name can't be empty");
			return;
		}
		
		String phone = phoneField.getText();
		if ("".equals(phone)) {
			showAlert("Error", "phone NO is needed.");
			return;
		}
		
		int customerId = DataHolder.getInstance().getLastCustomerId() + 1;
		String result = DataHolder.getInstance().addNewCustomer(new Customer(customerId, name, addressField.getText(), phone));
		
		if ("success".equals(result)) {
			showAlert("", result);
			DataHolder.getInstance().increaseCustomerId();
			callingController.setCustomerIDCombo();
			((Node)event.getSource()).getScene().getWindow().hide();
			
		}
		else {
			showAlert("Error", result);
		}
	}
	
	
	public void setCallingController(NewOrderDetailsController newOrderController) {
		this.callingController = newOrderController;
		
	}
	
	public void setCustomerIDLabel(String input) {
		newCustomerID.setText(input);
	}
    
	//Alert Popup Window Source code cited: http://code.makery.ch/blog/javafx-dialogs-official/
	private void showAlert(String header, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
	}

}
