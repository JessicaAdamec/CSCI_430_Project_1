//Class by Jessica Adamec
//Created 3/20/2021 for CSCI 430

import java.util.*;
import java.text.*;
import java.io.*;

public class ClerkState extends WarehouseState {
		
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private WarehouseContext context;
	private static ClerkState instance;
	private static final int EXIT = 0;
	private static final int ADD_CLIENT = 1;
	private static final int SHOW_PRODUCTS = 2;
	private static final int SHOW_CLIENTS = 3;
	private static final int SHOW_CLIENTS_WITH_BALANCE = 4;
	private static final int BECOME_CLIENT = 6;
	private static final int DISPLAY_WAITLIST = 7;
	private static final int RECEIVE_SHIPMENT = 8;
	private static final int LOGOUT = 9;
	private static final int HELP = 10;
	
	private ClerkState() {
	    super();
	    warehouse = Warehouse.instance();
	}
	public static ClerkState instance() {
	  if (instance == null) {
	    instance = new ClerkState();
	  }
	  return instance;
	}
	
	public String getToken(String prompt) {
	  do {
	    try {
	      System.out.println(prompt);
	      String line = reader.readLine();
	      StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
	      if (tokenizer.hasMoreTokens()) {
	        return tokenizer.nextToken();
	      }
	    } catch (IOException ioe) {
	      System.exit(0);
	    }
	  } while (true);
	}
	private boolean yesOrNo(String prompt) {
	  String more = getToken(prompt + " (Y|y)[es] or anything else for no");
	  if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
	    return false;
	  }
	  return true;
	}
	public int getNumber(String prompt) {
	  do {
	    try {
	      String item = getToken(prompt);
	      Integer num = Integer.valueOf(item);
	      return num.intValue();
	    } catch (NumberFormatException nfe) {
	      System.out.println("Please input a number ");
	    }
	  } while (true);
	}
	public int getCommand() {
	  do {
	    try {
	      int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
		  if (value >= EXIT && value <= HELP) {
		    return value;
		  }
	    } catch (NumberFormatException nfe) {
	    	System.out.println("Enter a number");
	    }
	  } while (true);
	}

	public void help() {
		System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
		System.out.println(EXIT + " to exit the program");
		System.out.println(ADD_CLIENT + " to add a client");
		System.out.println(SHOW_PRODUCTS + " to show products");
		System.out.println(SHOW_CLIENTS + " to show clients ");
		System.out.println(SHOW_CLIENTS_WITH_BALANCE + " to show clients with a balance");
		System.out.println(BECOME_CLIENT + " to become a client");
		System.out.println(DISPLAY_WAITLIST + " to display the waitlist");
		System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
		System.out.println(LOGOUT + " to logout");
		System.out.println(HELP + " for help");
	}
  
	public void addClient() {
	    String name = getToken("Enter client name");
	    String address = getToken("Enter address");
	    String phone = getToken("Enter phone");
	    Client result;
	    result = warehouse.addClient(name, address, phone);
	    if (result == null) {
	      System.out.println("Could not add client");
	    }
	    System.out.println(result);
  	}
	public void showProducts() {
		Iterator allProducts = warehouse.getProducts();
		System.out.println("List of Products: ");
		while (allProducts.hasNext()){
		Product product = (Product)(allProducts.next());
		    System.out.println(product.toString());
		}
	}  
	public void showClients() {
		Iterator allClients = warehouse.getClients();
		System.out.println("List of Clients: ");
		while (allClients.hasNext()){
		Client client = (Client)(allClients.next());
		    System.out.println(client.toString());
		}
	}  
	public void showClientsWithBalance() { 
		Iterator allClients = warehouse.getClients();
		System.out.println("List of Clients with Outstanding Balance: ");
		while (allClients.hasNext()){
			Client client = (Client)(allClients.next());
		  	if (client.getBalance() > 0)
		    System.out.println(client.toString());
		}  
	}
	public void becomeClient()
	{
	  String clientID = getToken("Please input the client id: ");
	  if (Warehouse.instance().validateClient(clientID) != null){
	    (WarehouseContext.instance()).setClient(clientID);      
	    (WarehouseContext.instance()).changeState(0);//check this number
	  }
	  else 
	    System.out.println("Invalid client id."); 
	}
	public void showProductWaitlist() {
	  String id = getToken("Enter product id");
	  Product product = warehouse.validateProduct(id); 
	  System.out.println("Quantity of Waitlist: " + warehouse.getProductWaitlistQty(product));
	}
	public void processShipment() {
	  Invoice invoice = new Invoice();
	  String productId = "";
	  while (!productId.equals("EXIT")) {
		  productId = getToken("Enter product id or EXIT");
		  Product product = warehouse.validateProduct(productId); 
		  if (product == null) {
			  if (!productId.equals("EXIT"))
				  System.out.println("Invalid ID");
		   }
		   else {
			   int qty = getNumber("Enter product qty");
			   Iterator allWaitListEntries = warehouse.getProductWaitlist(product);
			   while (allWaitListEntries.hasNext() & qty > 0){
				   WaitListEntry waitListEntry = (WaitListEntry)(allWaitListEntries.next());
				   System.out.println(waitListEntry.toString());
				   String entry = getToken("Process this Waitlisted Order Item? (Y/N)");
				   if (entry.toLowerCase().equals("y")) {
					   if (qty > waitListEntry.getQuantity()) {
						   invoice.addWaitListedItenSting(product, waitListEntry);
						   qty = waitListEntry.processEntry(qty);
					   } else {
						   System.out.println("Insufficient items to fill order");
					   }
				   }
		       }
			   if (qty > 0) { product.addInventory(qty); }
		   }
	  }
	  System.out.println(invoice.toString());
	}
	public void logout() {//
	  if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsManager) { //stem.out.println(" going to manager \n ");
	     WarehouseContext.instance().changeState(2); // check this number
	  }
	  else //go to LoginState
	     (WarehouseContext.instance()).changeState(3); // check this number
	}
  
	public void process() {
		int command;
	    help();
	    while ((command = getCommand()) != EXIT) {
	      switch (command) {
	        case ADD_CLIENT:        addClient();
	                                break;
	        case SHOW_PRODUCTS:     showProducts();
	                                break;
	        case SHOW_CLIENTS:      showClients();
	                                break;
	        case SHOW_CLIENTS_WITH_BALANCE: showClientsWithBalance();
            						break;
	        case BECOME_CLIENT:     becomeClient();
	                                break;
	        case DISPLAY_WAITLIST:  showProductWaitlist();
	                                break;
	        case RECEIVE_SHIPMENT:  processShipment();
	                                break;
	        case LOGOUT:          	logout();
	        						break;	                                
	        case HELP:              help();
	                                break;
	      }
	    }
	    logout();
	}
	
	public void run() {
	    process();
	}
}
