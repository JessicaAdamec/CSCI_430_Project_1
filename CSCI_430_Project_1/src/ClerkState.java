//Class by Jessica Adamec
//Created 3/20/2021 for CSCI 430

import java.util.*;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.text.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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
	
	private JFrame frame;
	private AbstractButton addClientButton, showProductsButton, lookupClientsButton, 
	 	becomeClientButton, displayWaitListButton, receiveShipmentButton, logoutButton;
	private JTextArea textArea = new JTextArea(10, 30);
	private JScrollPane scrollArea;
	 
	private GetPrompts getPrompt;
	private GUIMaker guiMaker = new GUIMaker();
	
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
  
	public void addClient() {
	    String name = getPrompt.getToken("Enter client name");
	    String address = getPrompt.getToken("Enter address");
	    String phone = getPrompt.getToken("Enter phone");
	    Client result;
	    result = warehouse.addClient(name, address, phone);
	    if (result == null) {
	    	JOptionPane.showMessageDialog(frame,"Could not add client");
	    }
	    String output = result.toString();
	    textArea.setText(output);
  	}
	public void showProducts() {
		Iterator allProducts = warehouse.getProducts();
		String output = "List of Products: \n";
		while (allProducts.hasNext()){
		Product product = (Product)(allProducts.next());
		    output += product.toString() + " \n";
		    output += "Quantity: " + product.getInventory() + "\n \n";
		}
		textArea.setText(output);
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
	    JOptionPane.showMessageDialog(frame, "Invalid client id."); 
	}
	public void showProductWaitlist() {
	  String id = getPrompt.getToken("Enter product id");
	  Product product = warehouse.validateProduct(id); 
	  if (product == null) {
		  JOptionPane.showMessageDialog(frame, "Invalid ID");
	   } else {
		   textArea.setText("Quantity of Waitlist: " + warehouse.getProductWaitlistQty(product));
	   }
	}
	public void processShipment() {
	  Invoice invoice = new Invoice();
	  String productId = "";
	  
	  boolean cont = getPrompt.yesOrNo("Do you have shipment items to process?");
	  
	  while (cont) {
		  productId = getPrompt.getToken("Enter product id");
		  Product product = warehouse.validateProduct(productId); 
		  if (product == null) {
			  if (!productId.equals("EXIT"))
				  JOptionPane.showMessageDialog(frame, "Invalid ID");
		   }
		   else {
			   int qty = getPrompt.getNumber("Enter product qty");
			   Iterator allWaitListEntries = warehouse.getProductWaitlist(product);
			   while (allWaitListEntries.hasNext() & qty > 0){
				   WaitListEntry waitListEntry = (WaitListEntry)(allWaitListEntries.next());
				   String prompt = waitListEntry.toString();
				   boolean entry = getPrompt.yesOrNo(prompt + "\n Process this Waitlisted Order Item?");
				   if (entry) {
					   if (qty > waitListEntry.getQuantity()) {
						   invoice.addWaitListedItenSting(product, waitListEntry);
						   qty = waitListEntry.processEntry(qty);
					   } else {
						   JOptionPane.showMessageDialog(frame, "Insufficient items to fill order");
					   }
				   }
		       }
			   if (qty > 0) { product.addInventory(qty); }
		   }
		  cont = getPrompt.yesOrNo("Process another item?");
	  }
	  String output = "";
	  output += invoice.toString();
	  textArea.setText(output);
	}
	public void logout() {//
	  if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsManager) { //stem.out.println(" going to manager \n ");
	     WarehouseContext.instance().changeState(2); // check this number
	  }
	  else //go to LoginState
	     (WarehouseContext.instance()).changeState(3); // check this number
	}
	
	public void run() {
	    GUIprocess();
	}
	
	public void GUIprocess() {
	  	  frame = WarehouseContext.instance().getFrame();
	  	  getPrompt = new GetPrompts(frame);
	  	  frame.getContentPane().removeAll();
	  	  frame.getContentPane().setLayout(new FlowLayout());
	  	    addClientButton = guiMaker.makeButton("Add Client", ADD_CLIENT, this);
	  	    showProductsButton = guiMaker.makeButton("Show Products", SHOW_PRODUCTS, this); 
	  	    lookupClientsButton = guiMaker.makeButton("Lookup Clients", LOOKUP_CLIENTS, this); 
		 	becomeClientButton = guiMaker.makeButton("Become Client", BECOME_CLIENT, this); 
		 	displayWaitListButton = guiMaker.makeButton("Display Waitlists", DISPLAY_WAITLIST, this); 
		 	receiveShipmentButton = guiMaker.makeButton("Receive Shipment", RECEIVE_SHIPMENT, this); 
		 	logoutButton = guiMaker.makeButton("Logout", LOGOUT, this);
	  	  frame.getContentPane().add(this.addClientButton);
	  	  frame.getContentPane().add(this.showProductsButton);
	  	  frame.getContentPane().add(this.lookupClientsButton);
	  	  frame.getContentPane().add(this.becomeClientButton);
	  	  frame.getContentPane().add(this.displayWaitListButton);
	  	  frame.getContentPane().add(this.receiveShipmentButton);
	  	  frame.getContentPane().add(this.logoutButton);
	  	  textArea.setEditable(false);
	  	  scrollArea = new JScrollPane(textArea);
	  	  scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	  	  frame.getContentPane().add(this.scrollArea, BorderLayout.CENTER);
	  	  frame.setVisible(true);
	  	  frame.paint(frame.getGraphics()); 
	  	  //frame.repaint();
	  	  frame.toFront();
	  	  frame.requestFocus();
	}
	
	public void actionPerformed(ActionEvent event) {
    	int command = Integer.parseInt(event.getActionCommand());
    	switch (command) {
        	case ADD_CLIENT:    	addClient();
                                	break;
        	case SHOW_PRODUCTS:		showProducts();
        							break;
        	case LOOKUP_CLIENTS:	lookupClients();
        							break;
        	case BECOME_CLIENT:     becomeClient();
        							break;
        	case DISPLAY_WAITLIST:  showProductWaitlist();
                                	break;
        	case RECEIVE_SHIPMENT:  processShipment();
        							break;
        	case LOGOUT:          	logout();
        							break;	                                
    	}
    }
}
