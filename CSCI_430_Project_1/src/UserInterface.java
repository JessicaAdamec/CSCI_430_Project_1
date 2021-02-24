import java.util.*;
import java.text.*;
import java.io.*;

public class UserInterface {
	
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_PRODUCT = 2;
  private static final int ADD_SUPPLIER = 3;
  private static final int EDIT_CLIENT = 4;
  private static final int EDIT_PRODUCT = 5;
  private static final int EDIT_SUPPLIER = 6;
  private static final int SHOW_CLIENTS = 7;
  private static final int SHOW_PRODUCTS = 8;
  private static final int SHOW_SUPPLIERS = 9;
  private static final int SHOW_SHOPPING_CART = 10;
  private static final int ADD_PRODUCT_TO_CART = 11;
  private static final int GET_TRANSACTIONS = 12;
  private static final int SAVE = 13;
  private static final int RETRIEVE = 14;
  private static final int HELP = 15;
  
  private UserInterface() {
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
  }
  
  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
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
  
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
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
    System.out.println("Enter a number between 0 and 15 as explained below:");
    System.out.println(EXIT + " to Exit");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(ADD_PRODUCT + " to add a product");
    System.out.println(ADD_SUPPLIER + " to add a supplier");
    System.out.println(ADD_PRODUCT + " to add a product");
    System.out.println(EDIT_CLIENT + " to edit client information");
    System.out.println(EDIT_PRODUCT + " to edit product information");
    System.out.println(EDIT_SUPPLIER + " to edit supplier information");
    System.out.println(SHOW_CLIENTS + " to print clients");
    System.out.println(SHOW_PRODUCTS + " to print products");
    System.out.println(SHOW_SUPPLIERS + " to print suppliers");  
    System.out.println(SHOW_SHOPPING_CART + " to show shopping cart");
    System.out.println(ADD_PRODUCT_TO_CART + " to add a product to the shopping cart");
    System.out.println(GET_TRANSACTIONS + " to print transactions");
    System.out.println(SAVE + " to save data");
    System.out.println(RETRIEVE + " to retrieve");
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
	   System.out.println("Press " + SAVE + " to save the data: " 
			   + result); 
  }   
  public void addProduct() {
	    String id = getToken("Enter product ID");
	    String name = getToken("Enter product name");
	    String price = getToken("Enter price");
	    int inventory = getNumber("Enter product inventory");
	    String supplierID = getToken("Enter product supplier ID");
	    Product result;
	    result = warehouse.addProduct(id, name, price, inventory, supplierID);
	    if (result == null) {
	      System.out.println("Could not add product");
	    }
		   System.out.println("Press " + SAVE + " to save the data: " 
				   + result); 
	  } 
  public void addSupplier() {
	    String name = getToken("Enter supplier name");
	    String address = getToken("Enter supplier address");
	    String phone = getToken("Enter supplier phone number");
	    Supplier result;
	    result = warehouse.addSupplier(name, address, phone);
	    if (result == null) {
	      System.out.println("Could not add supplier");
	    }
		   System.out.println("Press " + SAVE + " to save the data: " 
				   + result); 
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
		   else {
		   System.out.println("Press " + SAVE + " to save the data: " 
				   + updatedClient); 
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
		   System.out.println(4 + " to to edit supplier ID");
		   String command = getToken("Enter selection");   
		   switch (Integer.valueOf(command)) {
	       case 1:	  	String name = getToken("Enter new product name");        
	       				warehouse.editProductName(updatedProduct, name);
	       						break;
	       case 2:      String price = getToken("Enter new product price");        
						//warehouse.editProductPrice(updatedProduct, price);
	      						break;
	       case 3:      int inventory = getNumber("Enter new product inventory");        
						//warehouse.editProductInventory(updatedProduct, inventory);
	       						break;
	       case 4:      String supplierID = getToken("Enter new product supplier ID");        
						//warehouse.editProductSupplierID(updatedProduct, supplierID);
								break;       						
		   }
		   System.out.println("Press " + SAVE + " to save the data: " 
				   + updatedProduct); 
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
		   System.out.println("Press " + SAVE + " to save the data: " 
				   + updatedSupplier); 
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
  public void showProducts() {
      Iterator allProducts = warehouse.getProducts();
      System.out.println("List of Products: ");
      while (allProducts.hasNext()){
	  Product product = (Product)(allProducts.next());
          System.out.println(product.toString());
      }
  }  
  public void showSuppliers() {
      Iterator allSuppliers = warehouse.getSuppliers();
      System.out.println("List of Suppliers: ");
      while (allSuppliers.hasNext()){
	  Supplier supplier = (Supplier)(allSuppliers.next());
          System.out.println(supplier.toString());
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
		      System.out.println("The value of shopping cart after adding product: " +  warehouse.getShoppingCart(client));   
		   }
	   }
  }
	   
  public void getTransactions() {
      System.out.println("Dummy Action");   
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
  
  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:        addClient();
                                break;
        case ADD_PRODUCT:       addProduct();
        						break;
        case ADD_SUPPLIER:      addSupplier();
        						break;
        case EDIT_CLIENT:     	editClient();
								break;	       						
        case EDIT_PRODUCT:     	editProduct();
								break;							
        case EDIT_SUPPLIER:     editSupplier();
								break;		
        case SHOW_CLIENTS:		showClients();
        						break;
        case SHOW_PRODUCTS:		showProducts();
        						break;
        case SHOW_SUPPLIERS:	showSuppliers();
        						break;
        case SHOW_SHOPPING_CART: 	showShoppingCart();
        						break;
        case ADD_PRODUCT_TO_CART:	addProductToCart();
        						break;
        case GET_TRANSACTIONS:  getTransactions();
                                break;
        case SAVE:              save();
                                break;
        case RETRIEVE:          retrieve();
                                break;
        case HELP:              help();
                                break;
      }
    }
  }
  
  public static void main(String[] s) {
    UserInterface.instance().process();
  }
}