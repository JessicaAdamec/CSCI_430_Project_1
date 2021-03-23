
import java.io.Serializable;
  
public class WaitListEntry implements Serializable {
	
	private String orderId;
	private int quantity;
	private String clientId;
	  
	public WaitListEntry(String orderId, int quantity, String clientId) 
	{
		this.orderId = orderId;
		this.quantity = quantity;
		this.clientId =  clientId;
	}
 
	public String getOrderId() {
		return orderId;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getClientid(){
		return clientId;
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