
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class WarehouseContext {
	
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  
  
  //Project 2 Code
  private static WarehouseContext context;
  private int currentState;
  private int currentUser;
  private WarehouseState[] states;
  private int[][] nextState;
  public static final int IsClient = 0;
  public static final int IsClerk = 1;
  public static final int IsManager = 2;
  private String clientID;
  

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
  

  
//Remove most of this code once new states are complete
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
  
  public double getDouble(String prompt) {
	    do {
	      try {
	        String item = getToken(prompt);
	        Double num = Double.valueOf(item);
	        return num.doubleValue();
	      } catch (NumberFormatException nfe) {
	        System.out.println("Please input a number ");
	      }
	    } while (true);
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
  }   

  public void addProductSupplier() {
	  //Add an existing Supplier for a specific Product - STAGE 3
	   String supplierId = getToken("Enter supplier id");
	   Supplier supplier = warehouse.validateSupplier(supplierId); 
	   if (supplier == null) {
		   System.out.println("Could not validate the id");
	   }
	   else {
		   String productId = getToken("Enter product id");
		   Product updatedProduct = warehouse.validateProduct(productId); 
		   if (updatedProduct == null) {
			   System.out.println("Could not validate the id");
		   }
		   else {
			   double price = getDouble("Enter purchase price");
			   int inventory = getNumber("Enter inventory");
			warehouse.addProductSupplier(supplierId, updatedProduct, price, inventory);   
		   }
	   }
  } 
  public void editClient() {
	   String id = getToken("Enter client id");
	   Client updatedClient = warehouse.validateClient(id); 
	   if (updatedClient == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		   System.out.println(1 + " to edit name");
		   System.out.println(2 + " to edit address");
		   System.out.println(3 + " to edit phone");
		   String command = getToken("Enter selection");   
		   switch (Integer.valueOf(command)) {
	       case 1:	  	String name = getToken("Enter new client name");        
	      				warehouse.editClientName(updatedClient, name);
	      						break;
	       case 2:      String address = getToken("Enter new client address");        
						warehouse.editClientAddress(updatedClient, address);
	     						break;
	       case 3:      String phone = getToken("Enter new client phone");        
						warehouse.editClientPhone(updatedClient, phone);
	      						break;      						
		   }
		   if (updatedClient == null) {
			   System.out.println("Could not update the data");
		   }
	   }
  }  
  public void editProduct() {
	   String id = getToken("Enter product id");
	   Product updatedProduct = warehouse.validateProduct(id); 
	   if (updatedProduct == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		   System.out.println(1 + " to edit name");
		   System.out.println(2 + " to edit price");
		   System.out.println(3 + " to edit inventory");
		   String command = getToken("Enter selection");   
		   switch (Integer.valueOf(command)) {
	       case 1:	  	String name = getToken("Enter new product name");        
	       				warehouse.editProductName(updatedProduct, name);
	       						break;
	       case 2:      double price = getDouble("Enter new product price");        
						warehouse.editProductPrice(updatedProduct, price);
	      						break;
	       case 3:      int inventory = getNumber("Enter new product inventory");        
						warehouse.editProductInventory(updatedProduct, inventory);
	       						break;     						
		   }
	   }
  } 
  public void editSupplier() {
	   String id = getToken("Enter supplier id");
	   Supplier updatedSupplier = warehouse.validateSupplier(id); 
	   if (updatedSupplier == null) {
		   System.out.println("Could not update the data");
	   }
	   else {
		   System.out.println(1 + " to edit name");
		   System.out.println(2 + " to edit address");
		   System.out.println(3 + " to edit phone");
		   String command = getToken("Enter selection");   
		   switch (Integer.valueOf(command)) {
	       case 1:	  	String name = getToken("Enter new supplier name");        
	     				warehouse.editSupplierName(updatedSupplier, name);
	     						break;
	       case 2:      String address = getToken("Enter new supplier address");        
						warehouse.editSupplierAddress(updatedSupplier, address);
	    						break;
	       case 3:      String phone = getToken("Enter new supplier phone");        
						warehouse.editSupplierPhone(updatedSupplier, phone);
	     						break;      						
		   }
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
  public void showProducts() {
      Iterator allProducts = warehouse.getProducts();
      System.out.println("List of Products: ");
      while (allProducts.hasNext()){
	  Product product = (Product)(allProducts.next());
          System.out.println(product.toString());
      }
  }  
  public void showProductWaitlist() {
	  //Show all waitlisted products with their qty
	  String id = getToken("Enter product id");
	  Product product = warehouse.validateProduct(id); 
	  System.out.println("Quantity of Waitlist: " + warehouse.getProductWaitlistQty(product));
  }
  
  public void addProductToCart() {
	   String id = getToken("Enter client id");
	   Client client = warehouse.validateClient(id); 
	   if (client == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		   String productId = getToken("Enter product id");
		   Product product = warehouse.validateProduct(productId); 
		   if (product == null) {
			   System.out.println("Invalid ID");
		   }
		   else {
			  String quantity = getToken("Enter quantity");
		      int qty = Integer.valueOf(quantity); 	
		      warehouse.addItemToCart(client, product, qty);
		      System.out.println("The value of shopping cart after adding product: " 
		    		  						+  warehouse.getShoppingCart(client));   
		   }
	   }
  }
  public void editCart() {
	   String id = getToken("Enter client id");
	   Client client = warehouse.validateClient(id); 
	   if (client == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		   System.out.println("Contents of the shopping cart: ");
		   System.out.println(warehouse.getShoppingCart(client));
		   String productId = "";
		   while (!productId.equals("EXIT")) { 
			   productId = getToken("Enter product id or EXIT to exit");
			   CartItem cartItem = warehouse.validateCartItem(productId, client);
			   if (cartItem == null) {
				   if(!productId.equals("EXIT"))
					   System.out.println("Invalid ID");
			   } else {
				   int qty = getNumber("Enter new quantity");
				   cartItem.setQuantity(qty);
				   System.out.println(cartItem.toString());
			   }
		   }
	   }
  }
  public void showShoppingCart() {
	   String id = getToken("Enter client id");
	   Client client = warehouse.validateClient(id); 
	   if (client == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		   System.out.println("Contents of the shopping cart: ");
		   System.out.println(warehouse.getShoppingCart(client));
	   }
  }
  public void processOrder() { 
	   String id = getToken("Enter client id");
	   Client client = warehouse.validateClient(id); 
	   if (client == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		   Invoice invoice = warehouse.processOrder(client);
		   System.out.println("Invoice: " + invoice.toString());
	   } 
  }
  public void processPayment() {
	  //Process a payment - STAGE 3
	   String id = getToken("Enter client id");
	   Client client = warehouse.validateClient(id); 
	   if (client == null) {
		   System.out.println("Invalid ID");
	   } else {
		   String description = "";
		   description += getToken("Enter card number");
		   double payment = getDouble("Enter payment amount");
		   warehouse.processPayment(client, description, payment);
	   }
  }  
  public void processShipment() {
	  //Process a shipment - STAGE 3
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
			   if (qty > 0) {
				   product.addInventory(qty);
			   }
		   }
	  }
	  System.out.println(invoice.toString());
  }
  public void showOrders() {
    Iterator allOrders = warehouse.getOrders();
    System.out.println("List of Orders: ");
    while (allOrders.hasNext()){
    Order order = (Order)(allOrders.next());
        System.out.println(order.toString());
    }
  }
  public void showTransactions() {
	  String id = getToken("Enter client id");
	  Client client = warehouse.validateClient(id);  
	  if (client == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		   System.out.println("Client transactions: ");
		   Iterator allTransactions = warehouse.getTransactions(client);
		   while (allTransactions.hasNext()){
			  Transaction transaction = (Transaction)(allTransactions.next());
		          System.out.println(transaction.toString());
	       }
	   }
  }  
  public void printInvoice() {
	    String id = getToken("Enter client id");
	    Client client = warehouse.validateClient(id); 
	    if (client == null) {
	      System.out.println("Invalid ID");
	    }
	    else {
	      System.out.println("Transactions: ");
	      System.out.println(warehouse.getTransactions(client));
	    }
  }  
  private void save() {
    if (warehouse.save()) {
      System.out.println("The warehouse has been successfully saved in the file WarehouseData \n" );
    } else {
      System.out.println("There has been an error in saving \n" );
    }
  }  
  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println(" The Warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new warehouse" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }  
  private void terminate()
  {
   if (yesOrNo("Save data?")) {
      if (warehouse.save()) {
         System.out.println(" The Warehouse has been successfully saved in the file WarehouseData \n" );
       } else {
         System.out.println(" There has been an error in saving \n" );
       }
     }
   System.out.println(" Goodbye \n "); System.exit(0);
  }
  
  public static void main(String[] s) {
    WarehouseContext.instance().process();
  }

  //Project 2 Code
  public void setLogin(int code) {
	currentUser = code;
  }
  public void setClient(String cID) {
	clientID = cID;	
  }
  public int getLogin() {
	return currentUser;
  }
  public String getClient() { 
	return clientID;
  }

  public void changeState(int transition) {
    System.out.println("current state " + currentState + " \n \n ");
    currentState = nextState[currentState][transition];
    if (currentState == -2) 
      {System.out.println("Error has occurred"); terminate();}
    if (currentState == -1) 
      terminate();
    System.out.println("current state " + currentState + " \n \n ");
    states[currentState].run();
  }
  
  private WarehouseContext() { //constructor
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
    // set up the FSM and transition table;
    states = new WarehouseState[4];  //magic numbers, not sure why Library example has 3 here, maybe it should be 4
    states[0] = ClientState.instance();
    states[1] = ClerkState.instance();
    states[2]=  ManagerState.instance();
    states[3]=  LoginState.instance();
    nextState = new int[4][4];//magic numbers, not sure why Library example has 3 here, maybe it should be 4
    //need to verify this matrix, copied from Library example
    nextState[0][0] = 3;nextState[0][1] = 1;nextState[0][2] = -2;nextState[0][3] = -2;
    nextState[1][0] = 0;nextState[1][1] = 0;nextState[1][2] = 2;nextState[1][3] = -2;
    nextState[2][0] = 0;nextState[2][1] = 1;nextState[2][2] = 3;nextState[2][3] = -2;
    nextState[3][0] = 0;nextState[3][1] = 1;nextState[3][2] = 2;nextState[3][3] = -1;
    currentState = 3;
  }
   
  public static WarehouseContext instance() {
	if (context == null) {
	   context = new WarehouseContext();
	}
	return context;
  }
  
  public void process(){
	states[currentState].run();
  }
  
}