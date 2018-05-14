package application;

import java.io.Serializable;

public class Thneed implements Serializable{
	String size;
	String color;
	int quantity;
	public Thneed() {
		}
	public Thneed(String size, String color, int quantity) {
		this.size = size;
		this.color = color;
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Thneed:"+size + "," + color + "," + quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int generateID(String col, String size) {
		int id = 0;
		if(col.equals("Green")) {
			if(size.equals("S")) {
				id = 1;
			}else if(size.equals("M")) {
				id = 5;
			}else if(size.equals("L")) {
				id = 9;
			}else {
				id = 13;
			}
		}
		else if(col.equals("Blue")) {
			if(size.equals("S")) {
				id = 2;
			}else if(size.equals("M")) {
				id = 6;
			}else if(size.equals("L")) {
				id = 10;
			}else {
				id = 14;
			}
		}
		else if(col.equals("Red")) {
			if(size.equals("S")) {
				id = 3;
			}else if(size.equals("M")) {
				id = 7;
			}else if(size.equals("L")) {
				id = 11;
			}else {
				id = 15;
			}
		}
		else {
			if(size.equals("S")) {
				id = 4;			
			}else if(size.equals("M")) {
				id = 8;
			}else if(size.equals("L")) {
				id = 12;
			}else {
				id = 16;
			}
		}
		
		return id;
	}
	}