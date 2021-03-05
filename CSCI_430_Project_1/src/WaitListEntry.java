
import java.io.Serializable;
  
public class WaitListEntry implements Serializable {
	
	private String orderId;
	private int quantity;
	  
	public WaitListEntry(String orderId, int quantity) {
		this.orderId = orderId;
		this.quantity = quantity;
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