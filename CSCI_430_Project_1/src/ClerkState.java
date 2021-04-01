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
	private static final int LOOKUP_CLIENTS = 3;
	private static final int BECOME_CLIENT = 4;
	private static final int DISPLAY_WAITLIST = 5;
	private static final int RECEIVE_SHIPMENT = 6;
	private static final int LOGOUT = 9;
	private static final int HELP = 10;
	
	private GetPrompts getPrompt = new GetPrompts(EXIT, HELP);
	
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
	
	public void help() {
		System.out.println("\nEnter a number between " + EXIT + " and " + HELP + " as explained below:\n");
		System.out.println(EXIT + " to exit the program");
		System.out.println(ADD_CLIENT + " to add a client");
		System.out.println(SHOW_PRODUCTS + " to show products");
		System.out.println(LOOKUP_CLIENTS + " to lookup clients ");
		System.out.println(BECOME_CLIENT + " to become a client");
		System.out.println(DISPLAY_WAITLIST + " to display the waitlist");
		System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
		System.out.println(LOGOUT + " to logout");
		System.out.println(HELP + " for help");
	}
  
	public void addClient() {
	    String name = getPrompt.getToken("Enter client name");
	    String address = getPrompt.getToken("Enter address");
	    String phone = getPrompt.getToken("Enter phone");
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
	public void lookupClients() {
		(WarehouseContext.instance()).changeState(5); //Go to QueryClientState
	}  
	
	public void becomeClient()
	{
	  String clientID = getPrompt.getToken("Please input the client id: ");
	  if (Warehouse.instance().validateClient(clientID) != null){
	    (WarehouseContext.instance()).setClient(clientID);      
	    (WarehouseContext.instance()).changeState(0);//check this number
	  }
	  else 
	    System.out.println("Invalid client id."); 
	}
	public void showProductWaitlist() {
	  String id = getPrompt.getToken("Enter product id");
	  Product product = warehouse.validateProduct(id); 
	  if (product == null) {
		  System.out.println("Invalid ID");
	   } else {
		   System.out.println("Quantity of Waitlist: " + warehouse.getProductWaitlistQty(product));
	   }
	}
	public void processShipment() {
	  Invoice invoice = new Invoice();
	  String productId = "";
	  while (!productId.equals("EXIT")) {
		  productId = getPrompt.getToken("Enter product id or EXIT");
		  Product product = warehouse.validateProduct(productId); 
		  if (product == null) {
			  if (!productId.equals("EXIT"))
				  System.out.println("Invalid ID");
		   }
		   else {
			   int qty = getPrompt.getNumber("Enter product qty");
			   Iterator allWaitListEntries = warehouse.getProductWaitlist(product);
			   while (allWaitListEntries.hasNext() & qty > 0){
				   WaitListEntry waitListEntry = (WaitListEntry)(allWaitListEntries.next());
				   System.out.println(waitListEntry.toString());
				   String entry = getPrompt.getToken("Process this Waitlisted Order Item? (Y/N)");
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
	    while ((command = getPrompt.getCommand()) != EXIT) {
	      switch (command) {
	        case ADD_CLIENT:        addClient();
	                                break;
	        case SHOW_PRODUCTS:     showProducts();
	                                break;
	        case LOOKUP_CLIENTS:    lookupClients();
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
