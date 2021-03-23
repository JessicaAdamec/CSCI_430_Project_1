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
  }
  
  public void updateSupplierProducts() {
	  //TODO
  }
  
  public void loginAsSalesClerk() {
	  (WarehouseContext.instance()).changeState(1);
  }
  
  public void logout()
  {
    (WarehouseContext.instance()).changeState(0); // exit with a code 0
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
  
//  private boolean yesOrNo(String prompt) {
//    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
//    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
//      return false;
//    }
//    return true;
//  }
  
//  public int getNumber(String prompt) {
//    do {
//      try {
//        String item = getToken(prompt);
//        Integer num = Integer.valueOf(item);
//        return num.intValue();
//      } catch (NumberFormatException nfe) {
//        System.out.println("Please input a number ");
//      }
//    } while (true);
//  }
  
//  public Calendar getDate(String prompt) {
//    do {
//      try {
//        Calendar date = new GregorianCalendar();
//        String item = getToken(prompt);
//        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
//        date.setTime(df.parse(item));
//        return date;
//      } catch (Exception fe) {
//        System.out.println("Please input a date as mm/dd/yy");
//      }
//    } while (true);
//  }
  
//  public int getCommand() {
//    do {
//      try {
//        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
//        if (value >= EXIT && value <= HELP) {
//          return value;
//        }
//      } catch (NumberFormatException nfe) {
//        System.out.println("Enter a number");
//      }
//    } while (true);
//  }
//
//  public void help() {
//    System.out.println("Enter a number between 0 and 12 as explained below:");
//    System.out.println(EXIT + " to Exit\n");
//    System.out.println(ADD_MEMBER + " to add a member");
//    System.out.println(ADD_BOOKS + " to  add books");
//    System.out.println(RETURN_BOOKS + " to  return books ");
//    System.out.println(REMOVE_BOOKS + " to  remove books");
//    System.out.println(PROCESS_HOLD + " to  process holds");
//    System.out.println(USERMENU + " to  switch to the user menu");
//    System.out.println(HELP + " for help");
//  }
//
//  public void addMember() {
//    String name = getToken("Enter member name");
//    String address = getToken("Enter address");
//    String phone = getToken("Enter phone");
//    Member result;
//    result = library.addMember(name, address, phone);
//    if (result == null) {
//      System.out.println("Could not add member");
//    }
//    System.out.println(result);
//  }
//
//  public void addBooks() {
//    Book result;
//    do {
//      String title = getToken("Enter  title");
//      String bookID = getToken("Enter id");
//      String author = getToken("Enter author");
//      result = library.addBook(title, author, bookID);
//      if (result != null) {
//        System.out.println(result);
//      } else {
//        System.out.println("Book could not be added");
//      }
//      if (!yesOrNo("Add more books?")) {
//        break;
//      }
//    } while (true);
//  }
// 
//  public void returnBooks() {
//    int result;
//    do {
//      String bookID = getToken("Enter book id");
//      result = library.returnBook(bookID);
//      switch(result) {
//        case Library.BOOK_NOT_FOUND:
//          System.out.println("No such Book in Library");
//          break;
//        case Library.BOOK_NOT_ISSUED:
//          System.out.println(" Book  was not checked out");
//          break;
//        case Library.BOOK_HAS_HOLD:
//          System.out.println("Book has a hold");
//          break;
//        case Library.OPERATION_FAILED:
//          System.out.println("Book could not be returned");
//          break;
//        case Library.OPERATION_COMPLETED:
//          System.out.println(" Book has been returned");
//          break;
//        default:
//          System.out.println("An error has occurred");
//      }
//      if (!yesOrNo("Return more books?")) {
//        break;
//      }
//    } while (true);
//  }
//  public void removeBooks() {
//    int result;
//    do {
//      String bookID = getToken("Enter book id");
//      result = library.removeBook(bookID);
//      switch(result){
//        case Library.BOOK_NOT_FOUND:
//          System.out.println("No such Book in Library");
//          break;
//        case Library.BOOK_ISSUED:
//          System.out.println(" Book is currently checked out");
//          break;
//        case Library.BOOK_HAS_HOLD:
//          System.out.println("Book has a hold");
//          break;
//        case Library.OPERATION_FAILED:
//          System.out.println("Book could not be removed");
//          break;
//        case Library.OPERATION_COMPLETED:
//          System.out.println(" Book has been removed");
//          break;
//        default:
//          System.out.println("An error has occurred");
//      }
//      if (!yesOrNo("Remove more books?")) {
//        break;
//      }
//    } while (true);
//  }
//
//  public void processHolds() {
//    Member result;
//    do {
//      String bookID = getToken("Enter book id");
//      result = library.processHold(bookID);
//      if (result != null) {
//        System.out.println(result);
//      } else {
//        System.out.println("No valid holds left");
//      }
//      if (!yesOrNo("Process more books?")) {
//        break;
//      }
//    } while (true);
//  }
//
//  public void usermenu()
//  {
//    String userID = getToken("Please input the user id: ");
//    if (Library.instance().searchMembership(userID) != null){
//      (LibContext.instance()).setUser(userID);      
//      (LibContext.instance()).changeState(1);
//    }
//    else 
//      System.out.println("Invalid user id."); 
//  }
//
// 
//
//  public void process() {
//    int command;
//    help();
//    while ((command = getCommand()) != EXIT) {
//      switch (command) {
//        case ADD_MEMBER:        addMember();
//                                break;
//        case ADD_BOOKS:         addBooks();
//                                break;
//        case RETURN_BOOKS:      returnBooks();
//                                break;
//        case REMOVE_BOOKS:      removeBooks();
//                                break;
//        case PROCESS_HOLD:      processHolds();
//                                break;
//        case USERMENU:          usermenu();
//                                break;
//        case HELP:              help();
//                                break;
//      }
//    }
//    logout();
//  }
  
  public void run() {
//    process();
  }
  
}
