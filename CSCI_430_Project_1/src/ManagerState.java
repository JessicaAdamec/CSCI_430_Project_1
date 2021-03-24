import java.util.*;
import java.text.*;
import java.io.*;
public class ManagerState extends WarehouseState {
	
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarehouseContext context;
  private static ManagerState instance;
  
  private static final int EXIT = 0;
  private static final int ADD_PRODUCT = 1;
  private static final int ADD_SUPPLIER = 2;
  private static final int SHOW_SUPPLIERS = 3;
  private static final int SHOW_PRODUCT_SUPPLIERS = 4;
  private static final int SHOW_SUPPLIER_PRODUCTS = 5;
  private static final int UPDATE_SUPPLIER_PRODUCTS = 6;
  private static final int LOGIN_AS_SALESCLERK = 7;
  private static final int LOGOUT = 8;
  private static final int HELP = 9;
  
  private ManagerState() {
      super();
      warehouse = Warehouse.instance();
      //context = LibContext.instance();
  }

  public static ManagerState instance() {
    if (instance == null) {
      instance = new ManagerState();
    }
    return instance;
  }
  
  public void process() {
		int command;
	    help();
	    while ((command = getCommand()) != EXIT) {
	      switch (command) {
	      	case ADD_PRODUCT:       
	      							addProduct();
	                                break;
	        case ADD_SUPPLIER:      
	        						addSupplier();
	                                break;
	        case SHOW_SUPPLIERS:    
	        						showAllSuppliers();
	                                break;
	        case SHOW_PRODUCT_SUPPLIERS:
	        						showProductSuppliers();
	        						break;
	        case SHOW_SUPPLIER_PRODUCTS:
									showSupplierProducts();
									break;
	        case UPDATE_SUPPLIER_PRODUCTS:
	        						updateSupplierProducts();
									break;
	        case LOGIN_AS_SALESCLERK:
									loginAsSalesClerk();
									break;
	        case LOGOUT:          	
	        						logout();
	        						break;	                                
	        case HELP:              
	        						help();
	                                break;
	      }
	    }
	    logout();
  }
  
  public void help() {
	System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
	System.out.println(EXIT + " to exit the program\n");
	System.out.println(ADD_PRODUCT + " to add a product");
	System.out.println(ADD_SUPPLIER + " to add a supplier");
	System.out.println(SHOW_SUPPLIERS + " to show a list of suppliers");
	System.out.println(SHOW_PRODUCT_SUPPLIERS + " to retrieve a list of suppliers for a particular product");
	System.out.println(SHOW_SUPPLIER_PRODUCTS + " to retrieve a list of all products a supplier provides");
	System.out.println(UPDATE_SUPPLIER_PRODUCTS + " to update products a supplier provides.");
	System.out.println(LOGIN_AS_SALESCLERK + " to login as a Sales Clerk");
	System.out.println(LOGOUT + " to logout");
	System.out.println(HELP + " for help");
  }
	  
  public void addProduct() {
	    String id = getToken("Enter product ID");
	    String name = getToken("Enter product name");
	    double salePrice = getDouble("Enter sale price");
	    int inventory = getNumber("Enter product inventory");
	    String supplierID = getToken("Enter product supplier ID");
	    double purchasePrice = getDouble("Enter purchase price");
	    Product result;
	    result = warehouse.addProduct(id, name, salePrice, purchasePrice, inventory, supplierID);
	    if (result == null) {
	      System.out.println("Could not add product");
	    }
	    String myString = result + " Sale Price: $" + salePrice + " Inventory: "
	    		+ inventory + " Supplier ID: " + supplierID;  
  } 
  
  public void addProduct(String supplierId) {
	  	String id = getToken("Enter product ID");
	    String name = getToken("Enter product name");
	    double salePrice = getDouble("Enter sale price");
	    int inventory = getNumber("Enter product inventory");
	    double purchasePrice = getDouble("Enter purchase price");
	    Product result;
	    result = warehouse.addProduct(id, name, salePrice, purchasePrice, inventory, supplierId);
	    if (result == null) {
	      System.out.println("Could not add product");
	    }
	    String myString = result + " Sale Price: $" + salePrice + " Inventory: "
	    		+ inventory + " Supplier ID: " + supplierId;  
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
  } 
  
  public void showAllSuppliers() {
      Iterator allSuppliers = warehouse.getSuppliers();
      System.out.println("List of Suppliers: ");
      while (allSuppliers.hasNext()){
	  Supplier supplier = (Supplier)(allSuppliers.next());
          System.out.println(supplier.toString());
      }
  }
  
  public void showProductSuppliers() {  
	  String id = getToken("Enter product id");
	   Product product = warehouse.validateProduct(id); 
	   if (product == null) {
		   System.out.println("Invalid ID");
	   }
	   else {
		  Iterator productSuppliers = warehouse.getSupplierList(product);
	      System.out.println("List of Suppliers: ");
	      while (productSuppliers.hasNext()){
		  ProductSupplier pSupplier = (ProductSupplier)productSuppliers.next();
	          System.out.println(pSupplier.toString());
	      }
	   }
  }
  
  public void showSupplierProducts() {
	  String id = getToken("Enter supplier id");
	  Supplier supplier = warehouse.validateSupplier(id);
	  
	  if (supplier == null) {
		  System.out.println("Invalid ID");
	  } else {
		  printSupplierProductList(id);
	  }
  }
  
  //UPDATE SUPPLIER PRODUCTS START
  public void updateSupplierProducts() {
	  String supplierId = getToken("Enter supplier id");
	  Supplier supplier = warehouse.validateSupplier(supplierId);
	  
	  if (supplier == null) {
		  System.out.println("Invalid ID");
	  } else {
		  printSupplierProductList(supplierId);
		  printSupplierProductsMenu(supplierId);
	  }
  }
  
  public void printSupplierProductsMenu(String supplierId) {
	  System.out.println(EXIT + " to exit this menu");
	  System.out.println(1 + " to add a product to this supplier");
	  System.out.println(2 + " to remove a product from this supplier");
	  System.out.println(3 + " to edit the purchase price of a product from this supplier.");
	  System.out.println(HELP + " to display this menu and products list again.");
  }
  
  public void processSupplierProductsMenu(String supplierId)
  {
	  int command;
	  while ((command = getCommand()) != EXIT) {
		  switch (command) {
		  	case 1:
		  			addProductSupplier(supplierId);
		  	case HELP: 
		  			printSupplierProductList(supplierId);
		  			printSupplierProductsMenu(supplierId);
		  			break;
		  }
	  }
  }
  
  public void addProductSupplier(String supplierId) {
	  //Add an existing Supplier for a specific Product - STAGE 3
	   Supplier supplier = warehouse.validateSupplier(supplierId); 
	   if (supplier == null) {
		   System.out.println("Could not validate the id");
	   }
	   else {
		   String productId = getToken("Enter product id");
		   Product updatedProduct = warehouse.validateProduct(productId); 
		   if (updatedProduct == null) {
			   addProduct(supplierId);
		   }
		   else {
			   double price = getDouble("Enter purchase price");
			   int inventory = getNumber("Enter inventory");
			   warehouse.addProductSupplier(supplierId, updatedProduct, price, inventory);   
		   }
	   }
  } 
  
  public void removeProductSuppler(String supplierId) {
	  String productId = getToken("Enter product id");
	  Product updatedProduct = warehouse.validateProduct(productId); 
	   if (updatedProduct == null) {
		   System.out.println("Invalid ID");
	   } else {
		   warehouse.removeProductSupplier(supplierId, updatedProduct);
	   }
  }
  
  public void editPurchasePrice(String supplierId) {
	  String productId = getToken("Enter product id");
	  Product updatedProduct = warehouse.validateProduct(productId); 
	   if (updatedProduct == null) {
		   System.out.println("Invalid ID");
	   } else {
		   ProductSupplier productSupplier = updatedProduct.getProductSupplier(supplierId);
		   if (productSupplier == null) {
			   System.out.println("Invalid ID");
		   } else {
			   double price = getDouble("Enter purchase price");
			   warehouse.editProductSupplierPrice(productSupplier, price);
		   }
	   }
  }
  
  //END OF UPDATE SUPPLIER PRODUCTS
  
  //SHARED METHODS
  public void printSupplierProductList(String id) {
	  Iterator allProducts = warehouse.getProducts();
      System.out.println("List of Products: ");
      while (allProducts.hasNext()){
    	  Product product = (Product)(allProducts.next());
    	  ProductSupplier productSupplier = product.getProductSupplier(id);
    	  
    	  if (productSupplier != null) {
    		  System.out.println(product.toString());
    		  System.out.println(productSupplier.toString());
    	  }
      }
  }
  //END SHARED
  
  public void loginAsSalesClerk() {
	  (WarehouseContext.instance()).changeState(1);
  }
  
  public void logout()
  {
    (WarehouseContext.instance()).changeState(3); 
  }

  //these should all move to their own class (Tokens)
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
  //end move to own class (Tokens)
  
  public void run() {
	  process();
  }
  
}
