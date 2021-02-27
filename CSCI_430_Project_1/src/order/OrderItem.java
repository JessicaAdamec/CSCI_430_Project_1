package order;

/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.io.Serializable;

public class OrderItem implements Serializable {
	private String productId;
	//private String supplierId;
	private int quantity;
	
	public OrderItem(String productId, String supplierId, int quantity) {
		this.productId = productId;
		//this.supplierId = supplierId;
		this.quantity = quantity;
	}
	
	public String getProductId() {
		return productId;
	}
//	
//	public String getSupplierId() {
//		return supplierId;
//	}
//	
	public int getQuantity() {
		return quantity;
	}
	
	public String toString() {
		String string = "Product ID: " + productId + //" Supplier ID: " + supplierId + 
				" Quantity: " + quantity;
		return string;
	}
}