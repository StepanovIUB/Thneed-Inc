package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class CustomerInfoController {
	@FXML
	private Button okButton;
	@FXML
	private Label customerInfoName;
	@FXML
	private Label customerInfoAddress;
	@FXML
	private Label customerInfoPhone;

	// Event Listener on Button[#okButton].onAction
	@FXML
	public void setCustomerName(String name) {
		customerInfoName.setText(name);
	}
	@FXML
	public void setCustomerAdd(String add) {
		customerInfoAddress.setText(add);
	}
	@FXML
	public void setCustomerPhone(String phone) {
		customerInfoPhone.setText(phone);
	}
	@FXML
	public void closeWindow(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
	}
}
