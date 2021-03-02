package warehouse;
/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.io.Serializable;
  
  public class WaitListEntry implements Serializable {
	private String orderId;
	private int quantity;
	  
	public WaitListEntry(String orderId, int quantity) {
		this.orderId = orderId;
		this.quantity = quantity;
	}
	
	public int ProcessEntry(int amount){
		if (amount >= quantity) {
			amount -= quantity;
			quantity = 0;
		} else {
			quantity -= amount;
			amount = 0;
		}
		
		return amount;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int processEntry(int qty) {
		quantity = quantity - qty;
		if (quantity < 0) {
			qty = -quantity; //return the remaining quantity.
			quantity = 0;
		}
		
		return qty;
	}
	
	public String toString() {
		return "Order ID: " + orderId + " Quantity: " + quantity;
	}
  }