package application;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class ReportController {
	@FXML
	private Label fillRate;
	public Label getFillRate() {
		return fillRate;
	}

	public void setFillRate(Label fillRate) {
		this.fillRate = fillRate;
	}

	public Label getBackorder() {
		return backorder;
	}

	public void setBackorder(String item) {
		backorder.setText(item);
	}

	public Label getLeastPop() {
		return leastPop;
	}

	public void setLeastPop(String item) {
		leastPop.setText(item);
	}

	public Label getMostPop() {
		return mostPop;
	}

	public void setMostPop(String item) {
		mostPop.setText(item);
	}

	@FXML
	private Label backorder;
	@FXML
	private Label leastPop;
	@FXML
	private Label mostPop;
	
	private InventoryOrderController callingController;
	
	public void setCallingController(InventoryOrderController inventoryController) {
		this.callingController = inventoryController;
	}
	
	

}
