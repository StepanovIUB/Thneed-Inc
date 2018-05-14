package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InventoryOrderController {
	
	private final static int DEFAULT_QUANTITY = 1;
	
	@FXML
	private Label GS;
	@FXML
	private Label GM;
	@FXML
	private Label GL;
	@FXML
	private Label GX;
	@FXML
	private Label BS;
	@FXML
	private Label BM;
	@FXML
	private Label BL;
	@FXML
	private Label BX;
	@FXML
	private Label RS;
	@FXML
	private Label RM;
	@FXML
	private Label RL;
	@FXML
	private Label RX;
	@FXML
	private Label YS;
	@FXML
	private Label YM;
	@FXML
	private Label YL;
	@FXML
	private Label YX;
	@FXML
	private ComboBox<String> colorCombo;
	@FXML
	private Button doneButton;
	@FXML
	private TextField quant;
	
	private OrdersController callingController;
	
	
	@FXML
	private ComboBox<String> sizeCombo;
	
	Inventory store;
	
	private HashMap<Integer, Integer> inventoryData = new HashMap<Integer, Integer>();
	
	private ArrayList<Inventory> totalInvent = new ArrayList<Inventory>();
	
	public ArrayList<Inventory> getTotalInvent() {
		return totalInvent;
	}

	public void setTotalInvent(ArrayList<Inventory> totalInvent) {
		this.totalInvent = totalInvent;
	}
	
	public void setCallingController(OrdersController ordersController) {
		this.callingController = ordersController;
		setInvLabels();
	}
	
	@FXML
	public void initialize() {
		System.out.println("Hello.");
		//String[] size = {"S", "M", "L", "XL"};
		ObservableList<String> obSize = FXCollections.observableArrayList();
		obSize.add("S");
		obSize.add("M");
		obSize.add("L");
		obSize.add("XL");
		sizeCombo.setItems(obSize);
		sizeCombo.getSelectionModel().select(0);
		
		ObservableList<String> obColor = FXCollections.observableArrayList();
		obColor.add("Green");
		obColor.add("Blue");
		obColor.add("Red");
		obColor.add("Yellow");
		colorCombo.setItems(obColor);
		colorCombo.getSelectionModel().select(0);
		//setInvLabels();
		
		quant.setText(DEFAULT_QUANTITY+"");
		
	}
	
	public void setInvLabels() {
		//GS.setText(String.valueOf(inventoryData.get(1)));
		//GS.setText(""+ callingController.getInventoryData().get(1));
		GS.setText(""+ callingController.getInventoryData().get(1));
		BS.setText(""+ callingController.getInventoryData().get(2));
		RS.setText(""+ callingController.getInventoryData().get(3));
		YS.setText(""+ callingController.getInventoryData().get(4));
		GM.setText(""+ callingController.getInventoryData().get(5));
		BM.setText(""+ callingController.getInventoryData().get(6));
		RM.setText(""+ callingController.getInventoryData().get(7));
		YM.setText(""+ callingController.getInventoryData().get(8));
		GL.setText(""+ callingController.getInventoryData().get(9));
		BL.setText(""+ callingController.getInventoryData().get(10));
		RL.setText(""+ callingController.getInventoryData().get(11));
		YL.setText(""+ callingController.getInventoryData().get(12));
		GX.setText(""+ callingController.getInventoryData().get(13));
		BX.setText(""+ callingController.getInventoryData().get(14));
		RX.setText(""+ callingController.getInventoryData().get(15));
		YX.setText(""+ callingController.getInventoryData().get(16));
		//BS.setText(""+inventoryData.get(2));
	}
	
	public String getItem(int ID) {
		String item = "";
		if (ID == 1) {
			item = "Green Small";
		}else if (ID == 2) {
			item = "Blue Small";
		}else if (ID == 3) {
			item = "Red Small";
		}else if (ID == 4) {
			item = "Yellow Small";
		}else if (ID == 5) {
			item = "Green Medium";
		}else if (ID == 6) {
			item = "Blue Medium";
		}else if (ID == 7) {
			item = "Red Medium";
		}else if (ID == 8) {
			item = "Yellow Medium";
		}else if (ID == 9) {
			item = "Green Large";
		}else if (ID == 10) {
			item = "Blue Large";
		}else if (ID == 11) {
			item = "Red Large";
		}else if (ID == 12) {
			item = "Yellow Large";
		}else if (ID == 13) {
			item = "Green X-Large";
		}else if (ID == 14) {
			item = "Blue X-Large";
		}else if (ID == 15) {
			item = "Red X-Large";
		}else {
			item = "Yellow X-Large";
		}
		return item;
	}
	
	@FXML
	public void reportButtonClicked(ActionEvent event) {
		int[] backTemp = callingController.backOrderItems;
		HashMap<Integer, Integer> currInv = callingController.getInventoryData();
		String backItems = "";
        for(int i=0; i<backTemp.length; i++) {
    	 		if (backTemp[i] > 0) {
    	 			backItems += getItem(i);
    	 		}
    	 	}
        
        int[] popTemp = callingController.popularItems;
		String popItem = "";
        int min = Arrays.stream(popTemp).min().getAsInt();
        String least = getItem(min);
        int max = Arrays.stream(popTemp).max().getAsInt();
        String most = getItem(max);
        
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Report.fxml"));
		AnchorPane dialogRoot;

		// setting the stage, creating a controller object and loading the window
		try {
			dialogRoot = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(dialogRoot);
			Stage dialogStage = new Stage();
			dialogStage.setScene(dialogScene);
			ReportController reportController = (ReportController) loader.getController();
			//reportController.setCallingController(this);
			reportController.setBackorder(backItems);
			reportController.setMostPop(most);
			reportController.setLeastPop(least);
			dialogStage.show();
			System.out.println("After dialogStage.show(), report details window opened");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@FXML
	public void reStockButtonClicked() {
		
		String strQuantity = quant.getText();
		
		if (strQuantity == null || "".equals(strQuantity)) {
			showAlert("Error", "Quantity should be set");
			return;
		}
		
		int quantset = 0;
		try {
			quantset = Integer.parseInt(strQuantity);
		}
		catch (Exception e) {
			showAlert("Error", "Quantity should be number");
			return;
		}
		

		//Inventory newInvent;
		Date today = new Date();
		System.out.println(today);
		totalInvent.add(new Inventory(colorCombo.getValue().toString(), sizeCombo.getValue().toString(), quantset, today));
		
		//Set Default Value
		quant.setText(DEFAULT_QUANTITY+"");
		sizeCombo.getSelectionModel().select(0);
		colorCombo.getSelectionModel().select(0);
	}
	
	@FXML
	public void doneButtonClicked(ActionEvent event) {
		//Close this window.
		//Open Order History Window.
		
		//HashMap
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("currInv.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(inventoryData);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in currInv.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
		
		//ArrayList
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("schedInv.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(totalInvent);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in schedInv.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
		
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	public HashMap<Integer, Integer> getInventoryData() {
		return inventoryData;
	}
	public void setInventoryData(HashMap<Integer, Integer> inventoryData) {
		this.inventoryData = inventoryData;
	}
/*	public Label getGS() {
		return GS;
	}
	public void setGS() {
		GS.setText(Integer.toString(store.getGs()));
	}
	public Label getGM() {
		return GM;
	}
	public void setGM() {
		GM.setText(Integer.toString(store.getGm()));
	}
	public Label getGL() {
		return GL;
	}
	public void setGL() {
		GL.setText(Integer.toString(store.getGl()));
	}
	public Label getGX() {
		return GX;
	}
	public void setGX() {
		GX.setText(Integer.toString(store.getGx()));
	}
	public Label getBS() {
		return BS;
	}
	public void setBS() {
		BS.setText(Integer.toString(store.getBs()));
	}
	public Label getBM() {
		return BM;
	}
	public void setBM() {
		BM.setText(Integer.toString(store.getBm()));
	}
	public Label getBL() {
		return BL;
	}
	public void setBL() {
		BL.setText(Integer.toString(store.getBl()));
	}
	public Label getBX() {
		return BX;
	}
	public void setBX() {
		BX.setText(Integer.toString(store.getBx()));
	}
	public Label getRS() {
		return RS;
	}
	public void setRS() {
		RS.setText(Integer.toString(store.getRs()));
	}
	public Label getRM() {
		return RM;
	}
	public void setRM() {
		RM.setText(Integer.toString(store.getRm()));
	}
	public Label getRL() {
		return RL;
	}
	public void setRL() {
		RL.setText(Integer.toString(store.getRl()));
	}
	public Label getRX() {
		return RX;
	}
	public void setRX() {
		RX.setText(Integer.toString(store.getRx()));
	}
	public Label getYS() {
		return YS;
	}
	public void setYS() {
		YS.setText(Integer.toString(store.getYs()));
	}
	public Label getYM() {
		return YM;
	}
	public void setYM() {
		YM.setText(Integer.toString(store.getYm()));
	}
	public Label getYL() {
		return YL;
	}
	public void setYL() {
		YL.setText(Integer.toString(store.getYl()));
	}
	public Label getYX() {
		return YX;
	}
	public void setYX() {
		YX.setText(Integer.toString(store.getYx()));
	}*/
	
	private void showAlert(String header, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
	}

}
