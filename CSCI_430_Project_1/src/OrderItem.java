
/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.io.Serializable;

public class OrderItem implements Serializable {
	private String productId;
	private int quantity;
	
	public OrderItem(String productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public String getProductId() {
		return productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public String toString() {
		String string = "Product ID: " + productId + " Quantity: " + quantity;
		return string;
	}
}