package application;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable{

	private static final long serialVersionUID = -7583716971996428404L;


	int id;
	int quantity;
	Date scheduledDate;
	//Date estimatedDate;
	String Color;
	String Size;
	//create 16 vars
	public Inventory(String col, String size, int quantity, Date placedDate) {
		this.Color = col;
		this.Size = size;
		this.scheduledDate =addDays(placedDate, 1);
		this.quantity = quantity;
		this.id = generateID(this.Color, this.Size);
	}
	
	@Override
	public String toString() {
		return "Inventory [id=" + id + ", quantity=" + quantity + ", scheduledDate=" + scheduledDate + ", Color="
				+ Color + ", Size=" + Size + "]";
	}

	// method for getting projected fill date
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
//	public int generateID(String col, String size) {
//		int id =0;
//		switch (col) {
//			case "Green":
//				switch (size) {
//					case "S": id = 1;break;
//					case "M": id = 5;break;
//					case "L": id = 9;break;
//					case "XL": id = 13;break;
//					//default: id = 0;break;
//				}
//				
//			case "Blue":
//				switch (size) {
//					case "S": id = 2;break;
//					case "M": id = 6;break;
//					case "L": id = 10;break;
//					case "XL": id = 14;break;
//				//default: id = 0;break;
//			}
//				
//			case "Red":
//				switch (size) {
//					case "S": id = 3;break;
//					case "M": id = 7;break;
//					case "L": id = 11;break;
//					case "XL": id = 15;break;
//				//default: id = 0;break;
//			}
//				
//			case "Yellow":
//				switch (size) {
//					case "S": id = 1;break;
//					case "M": id = 5;break;
//					case "L": id = 9;break;
//					case "XL": id = 13;break;
//				//default: id = 0;break;
//			}
//					
//				
//		}
//		return id;
//	}
	
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
	
/*	int gs;
	int gm;
	int gl;
	int gx;
	int bs;
	int bm;
	int bl;
	int bx;
	int rs;
	int rm;
	int rl;
	int rx;
	int ys;
	int ym;
	int yl;
	int yx;*/

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

//	public Date getEstimatedDate() {
//		return estimatedDate;
//	}
//
//	public void setEstimatedDate(Date estimatedDate) {
//		this.estimatedDate = estimatedDate;
//	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public String getSize() {
		return Size;
	}

	public void setSize(String size) {
		Size = size;
	}

/*	public int getGs() {
		return gs;
	}

	public void setGs(int gs) {
		this.gs = (int) (Math.random()*5 + 1);
	}

	public int getGm() {
		return gm;
	}

	public void setGm(int gm) {
		this.gm = (int) (Math.random()*5 + 1);
	}

	public int getGl() {
		return gl;
	}

	public void setGl(int gl) {
		this.gl = (int) (Math.random()*5 + 1);
	}

	public int getGx() {
		return gx;
	}

	public void setGx(int gx) {
		this.gx = (int) (Math.random()*5 + 1);
	}

	public int getBs() {
		return bs;
	}

	public void setBs(int bs) {
		this.bs = (int) (Math.random()*5 + 1);
	}

	public int getBm() {
		return bm;
	}

	public void setBm(int bm) {
		this.bm = (int) (Math.random()*5 + 1);
	}

	public int getBl() {
		return bl;
	}

	public void setBl(int bl) {
		this.bl = (int) (Math.random()*5 + 1);
	}

	public int getBx() {
		return bx;
	}

	public void setBx(int bx) {
		this.bx = (int) (Math.random()*5 + 1);
	}

	public int getRs() {
		return rs;
	}

	public void setRs(int rs) {
		this.rs = (int) (Math.random()*5 + 1);
	}

	public int getRm() {
		return rm;
	}

	public void setRm(int rm) {
		this.rm = (int) (Math.random()*5 + 1);
	}

	public int getRl() {
		return rl;
	}

	public void setRl(int rl) {
		this.rl = (int) (Math.random()*5 + 1);
	}

	public int getRx() {
		return rx;
	}

	public void setRx(int rx) {
		this.rx = (int) (Math.random()*5 + 1);
	}

	public int getYs() {
		return ys;
	}

	public void setYs(int ys) {
		this.ys = (int) (Math.random()*5 + 1);
	}

	public int getYm() {
		return ym;
	}

	public void setYm(int ym) {
		this.ym = (int) (Math.random()*5 + 1);
	}

	public int getYl() {
		return yl;
	}

	public void setYl(int yl) {
		this.yl = (int) (Math.random()*5 + 1);
	}

	public int getYx() {
		return yx;
	}

	public void setYx(int yx) {
		this.yx = (int) (Math.random()*5 + 1);
	}*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
