/**
  * NAME: Eric Young
  * DATE: 2/11/2021
  */
  
  import java.util.*;
  import java.lang.*;
  import java.io.*;
  
public class CartItem implements Serializable {
	
	private String productID; 		//id of the product
	private int quantity;			//amount of product
	
	public CartItem(String productID, int quantity) {
	  this.productID = productID;
	  this.quantity = quantity;
	}


	public String getProductID() {
	  return productID;
	}

	public int getQuantity() {
	  return quantity;
	}
	
	public String toString() {
	  return "Product ID: " + productID + " Quantity: " + Integer.toString(quantity);
	}
}