package order;


import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class Order implements Serializable {
	
	private String id;
	private String clientId;
	private List orderItems = new LinkedList();
	private Date date;
	
	private static final String ORDER_STRING = "O";

	public Order(String clientId) {
		id = ORDER_STRING + (OrderIdServer.instance()).getId();
		this.clientId = clientId;
		date = Calendar.getInstance().getTime();
	}

	public String getId() {
		return id;
	}
	public String getClientId() {
		return clientId;
	}
	
	public boolean insertOrderItem(OrderItem orderItem) {
		orderItems.add(orderItems);
		return true;
	}
	
	public Iterator getOrderItems(){
		return orderItems.iterator();
	}
	
	public Date getDate() {
		return date;
	}

	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
		String strDate = dateFormat.format(date);
		
		String orderItemString = null;
		Iterator allItems = getOrderItems();
		while (allItems.hasNext()){
			OrderItem orderItem = (OrderItem)(allItems.next());
			orderItemString += orderItem.toString() + "\n";
		}

		String string = "Order ID: " + id + " Client ID: " + clientId + " Date: " + strDate + "\n" + orderItemString;
		return string;
	}
}