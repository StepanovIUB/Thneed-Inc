//orders controller

package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OrdersController implements Serializable{
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Button saveButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button newOrderButton;
	@FXML
	private ListView<String> filled;
	@FXML
	private ListView<String> unfilled;
	@FXML
	private Button customerInfo;
	@FXML
	private Button inventory;
	
	private InventoryOrderController inventoryController;
	
	int count = 0;
	
	private HashMap<Integer, Integer> currInventoryData = new HashMap<Integer, Integer>();
	//private HashMap<String, Integer> scheduledInventoryData = new HashMap<String, Integer>();
	private ArrayList<Inventory> scheduledInventory = new ArrayList<Inventory>();
	//private ArrayList<Integer> backorderItems = new ArrayList<Integer>();
	//private HashMap<Inventory> currInv = new HashMap<Inventory>();
	int[] backOrderItems = new int[16];
	int[] popularItems = new int[16];
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {

		DataHolder.getInstance();
		
        populateUnfilledOrders();
        //currInventoryData = getInvData();
        setInventoryData(getInvData());
        scheduledInventory = getSchedData();
        for (int i = 0; i < scheduledInventory.size(); i++) {
    	 		Inventory inv = scheduledInventory.get(i);
    	 		System.out.println(inv.toString());
     }
        
        //create the files separately for backorder.ser and popular.ser
        backOrderItems = loadBackorder();
        popularItems = loadPopular();
        
        Iterator it = currInventoryData.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry<Integer,Integer> pair = (Map.Entry<Integer,Integer>)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    //checking the current date and updating the quantities
	      for(Inventory inv : scheduledInventory) {
	      		Date d = new Date();
	      		if ( d == inv.getScheduledDate()){
	      			int temp = inv.generateID(inv.getColor(), inv.getSize());
	      			int quantity = currInventoryData.get(temp);
	      			quantity += inv.quantity;
	      			currInventoryData.put(temp, quantity);
	      			inventoryController.setInvLabels();
	      			scheduledInventory.remove(inv);
	      			}
	      }System.out.println("Size:" + scheduledInventory.size());	 
	      
	      
	      
	      //Check unfilled orders, update inventory, and set filled order date
	      SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
	      ArrayList<Order> orders = DataHolder.getInstance().getOrders();
			
			for (Order orderObject : orders) {
				if(orderObject.getDateFilled() == null) {
					int orderID = orderObject.getOrderID();
					ArrayList<Thneed> detail = orderObject.getProdOrdered();
					boolean canFill = true;
					for(int i = 0; i < detail.size(); i++)
					{
						Thneed temp = detail.get(i);
						temp.toString();
						String col = temp.getColor();
						String size = temp.getSize();
						int quant = temp.getQuantity();
						int id = temp.generateID(col, size);
						System.out.println("ID: " + id);
						int avail = currInventoryData.get(id);
						if (avail < quant) {
							//currInventoryData.put(id, avail-quant);
							canFill = false;
							if (orderObject.getProjectedDate() == new Date()) {
								backOrderItems[id-1] ++;
								saveBackorder();
							}
							break;
						}
						//Thneed temp = new Thneed();
						
						
					}
					if(canFill) {
						for(int i = 0; i < detail.size(); i++)
						{
							Thneed temp = detail.get(i);
							temp.toString();
							String col = temp.getColor();
							String size = temp.getSize();
							int quant = temp.getQuantity();
							int id = temp.generateID(col, size);
							int avail = currInventoryData.get(id);
							currInventoryData.put(id, avail-quant);
							popularItems[id-1] ++;
							savePopular();
							}
						
					}
					
					orderObject.setDateFilled();
					
					//filled.getItems().add(orderID +"      " +detail+ "      "+ orderObject.getCustomerID() +"      " +dt.format(dateOrdered));
				}

	      
	      System.out.println("Size:" + scheduledInventory.size());	 

	      filled.setOnMouseClicked(new EventHandler<Event>() {

	    	  @Override
	    	  public void handle(Event event) {
	    		  unfilled.getSelectionModel().clearSelection();
	    	  }
	      });
	      unfilled.setOnMouseClicked(new EventHandler<Event>() {

	    	  @Override
	    	  public void handle(Event event) {
	    		  filled.getSelectionModel().clearSelection();
	    	  }
	      });}
	      
	}
	
	public void saveBackorder() {
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("backorder.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(backOrderItems);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in backorder.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	
	public int[] loadBackorder() {
		int[] temp = new int[16];
		try {
	         FileInputStream fileIn = new FileInputStream("backorder.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (int[]) in.readObject();
	         for(int i=0; i<temp.length; i++) {
	        	 	System.out.println(temp[i]);
	         }
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Class not found.");
	         c.printStackTrace();
	         return null;
	      }
		return temp;
	}
	
	public void savePopular() {
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("popular.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(popularItems);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in popular.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	
	public int[] loadPopular() {
		int[] temp = new int[16];
		try {
	         FileInputStream fileIn = new FileInputStream("popular.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (int[]) in.readObject();
	         for(int i=0; i<temp.length; i++) {
	        	 	System.out.println(temp[i]);
	         }
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Class not found.");
	         c.printStackTrace();
	         return null;
	      }
		return temp;
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer,  Integer> getInvData(){
		HashMap<Integer, Integer> temp;
		try {
	         FileInputStream fileIn = new FileInputStream("currInv.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (HashMap<Integer,Integer>) in.readObject();
	         in.close();
	         fileIn.close();
	         System.out.println("HMSize: " + temp.size());
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Class not found.");
	         c.printStackTrace();
	         return null;
	      }
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Inventory> getSchedData() {
		ArrayList<Inventory> temp = new ArrayList<Inventory>();
		try {
	         FileInputStream fileIn = new FileInputStream("schedInv.ser");
	         System.out.println("Hello.");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (ArrayList<Inventory>) in.readObject();
	         //temp.toString();
	         in.close();
	         fileIn.close();
	         System.out.println("Size:" + temp.size());
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null; 
	      } catch (ClassNotFoundException c) {
	         System.out.println("Class not found.");
	         c.printStackTrace();
	         return null;
	      }
		return temp;
	}
	
	@FXML
	public void customerInfoClicked(ActionEvent event) {
		ListView<String> listView = null;
		if(filled.getSelectionModel().getSelectedItem() != null) {
			listView = filled;
		}
		else if(unfilled.getSelectionModel().getSelectedItem() != null) {
			listView = unfilled;
		}
		else {
			showAlert("Error", "Order not selected.");
			return;
		}
		
		
		String temp = listView.getSelectionModel().getSelectedItem().toString();
		String[] custInfo = temp.split("      ");
		Customer cust = DataHolder.getInstance().getCustomerById(Integer.parseInt(custInfo[2]));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerInfo.fxml"));
		AnchorPane dialogRoot;
		try {
			dialogRoot = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(dialogRoot);
			Stage dialogStage = new Stage();
			dialogStage.setScene(dialogScene);
			CustomerInfoController custDetailController = (CustomerInfoController) loader.getController();
			
			custDetailController.setCustomerName(cust.getName());
			custDetailController.setCustomerAdd(cust.getAddress());
			custDetailController.setCustomerPhone(cust.getPhone());
			dialogStage.show();
			System.out.println("After dialogStage.show(), new order details window opened");
			listView.getSelectionModel().clearSelection();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void inventoryClicked(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryOrder.fxml"));
		AnchorPane dialogRoot;

		// setting the stage, creating a controller object and loading the window
		try {
			dialogRoot = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(dialogRoot);
			Stage dialogStage = new Stage();
			dialogStage.setScene(dialogScene);
			InventoryOrderController inventoryController = (InventoryOrderController) loader.getController();
			inventoryController.setCallingController(this);
			inventoryController.setTotalInvent(scheduledInventory);
			inventoryController.setInventoryData(currInventoryData);
			/*inventoryController.setBS();
			inventoryController.setBM();
			inventoryController.setBL();
			inventoryController.setBX();
			inventoryController.setRS();
			inventoryController.setRM();
			inventoryController.setRL();
			inventoryController.setRX();
			inventoryController.setYS();
			inventoryController.setYM();
			inventoryController.setYX();
			inventoryController.setYL();
			inventoryController.setGS();
			inventoryController.setGM();
			inventoryController.setGL();
			inventoryController.setGX();*/
			
		
			// passing the controller to New Order Details GUI
			dialogStage.show();
			System.out.println("After dialogStage.show(), new order details window opened");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateButtonClicked() {
		System.out.println("updateButtonClicked");
		int index = filled.getSelectionModel().getSelectedIndex();
		String orderline =  (String) filled.getSelectionModel().getSelectedItem();
		if (orderline == null) {
			showAlert("Error", "order not selected.");
			return;
		}
		
		int Orderid = 0;
		try {
			Orderid = Integer.parseInt(orderline.split("      ")[0]);
			System.out.println("OrderID: " + Orderid);
			Order order = DataHolder.getInstance().getOrderById(Orderid);
			ArrayList <Thneed> detail = order.getProdOrdered();
			for(int i =0; i < detail.size(); i++) {
				Thneed item = detail.get(i);
				String col = item.getColor();
				String size = item.getSize();
				int quant = item.getQuantity();
				int ID = item.generateID(col, size);
				int avail = currInventoryData.get(ID);
				if (avail >= quant) {
					currInventoryData.put(ID, avail-quant);
					order.setDateFilled();
					popularItems[ID] += 1;
					}
				else {
					backOrderItems[ID] += 1;
					showAlert("Error", "Not enough Inventory");
					return;
				}
			}
		}
		catch (Exception e) {
			showAlert("Error", "Error order info");
			return;
		}

		filled.getItems().remove(index);
		unfilled.getItems().add(orderline);
	}
	
	public void populateUnfilledOrders() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
		
		filled.getItems().clear();
		//fix bug of duplication in Filled Order
		unfilled.getItems().clear();
		ArrayList<Order> orders = DataHolder.getInstance().getOrders();
		
		for (Order orderObject : orders) {
			if(orderObject.getDateFilled() == null) {
				int orderID = orderObject.getOrderID();
				ArrayList<Thneed> detail = orderObject.getProdOrdered();
				Date dateOrdered = orderObject.getDateOrdered();
				//Date dateProjected = orderObject.getProjectedDate();
				filled.getItems().add(orderID +"      " +detail+ "      "+ orderObject.getCustomerID() +"      " +dt.format(dateOrdered)/*+ "      "+ dt.format(dateProjected)*/);
			}
			else {
				int orderID = orderObject.getOrderID();
				ArrayList<Thneed> detail = orderObject.getProdOrdered();
				//Date dateProjected = orderObject.getProjectedDate();
				Date datePlaced = orderObject.getDateFilled();
				unfilled.getItems().add(orderID +"      " +detail+ "      "+ orderObject.getCustomerID() /*+"      " + dt.format(dateProjected)*/+ "      " + dt.format(datePlaced));
			}
		}
	   
	}
		

	//Event Listener for newOrderButton
	@FXML
	public void newOrderButtonClicked(ActionEvent event) {
		//populate dropdowns
		//Load New Order Window
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewOrderDetails.fxml"));
		AnchorPane dialogRoot;

		// setting the stage, creating a controller object and loading the window
		try {
			dialogRoot = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(dialogRoot);
			Stage dialogStage = new Stage();
			dialogStage.setScene(dialogScene);
			NewOrderDetailsController orderDetailController = (NewOrderDetailsController) loader.getController();
			
		
			// passing the controller to New Order Details GUI
			orderDetailController.setCallingController(this);
			orderDetailController.setOrderIDLabel(""+(DataHolder.getInstance().getLastOrderId()+1));
			orderDetailController.setCustomerIDCombo();
			dialogStage.show();
			System.out.println("After dialogStage.show(), new order details window opened");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void saveDataCalled(ActionEvent event) {
		//DataHolder.getInstance().saveData();
		//System.out.println("data saved");
		//DataHolder.getInstance().getOrders();
		//DataHolder.getInstance().getCustomers();
		
		//OrdersList
		DataHolder.getInstance().saveData();
		
	}
	
	private void showAlert(String header, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
	}

	public HashMap<Integer, Integer> getInventoryData() {
		return currInventoryData;
	}
	public void setInventoryData(HashMap<Integer, Integer> inventoryData) {
		this.currInventoryData = inventoryData;
	}
	
	
}


