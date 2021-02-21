/**
  * NAME: Eric Young
  * DATE: 2/21/2021
  */

import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class Order implements Serializable {
	
	private String id;
	private String clientId;
	private String productId;
	//may need supplierId as there is a requirement for listing all suppliers of a product.
	private int quantity;
	private Date date;

	public Order(String clientId, String productId, int quantity) {
		id = ORDER_STRING + (OrderIdServer.instance()).getId();
		this.clientId = clientId;
		this.productId = productId;
		this.quantity = quantity;
		date = Calendar.getInstance().getTime();
	}

	public String getClient() {
		return clientId;
	}
	
	public String getProduct() {
		return productId;
	}
	
	public int getQuantity() {
		return quantity
	}
	
	public Date getDate() {
		return date;
	}

	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
		String strDate = dateFormat.format(date);

		String string = "Order ID: " + id + " Client ID: " + clientId + " Product ID: " + productID + 
			" Quantity: " + Integer.ToString(quantity) + " Date: " + strDate;
		return string;
	}
}