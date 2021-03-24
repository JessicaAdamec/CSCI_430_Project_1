//Class by William Doyle
//3/22/2021 CSCI 430 

import java.util.*;
import java.text.*;
import java.io.*;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ClientState extends WarehouseState {
	
  private static ClientState clientState;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarehouseContext context;
  private static ClerkState instance; 

 private static final int EXIT = 0;
 private static final int CLIENT_DETALS = 1;
 private static final int LIST_PRODUCTS = 2;
 private static final int CLIENT_TRANSACTIONS = 3;
 private static final int ADD_TO_CART = 4;
 private static final int EDIT_CART = 5;
 private static final int WAIT_LIST = 6;
 private static final int HELP = 7;
 private static final int LOGOUT = 8;

  
  private ClientState() {
    super();
    warehouse = Warehouse.instance();
  }

  public static ClientState instance() {
    if (clientState == null) {
      return clientState = new ClientState();
    } else {
      return clientState;
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

   public void showClientDetails(){
     String id = getToken("Enter Client Id");
     Client selectedClient = warehouse.validateClient(id);
     if (selectedClient == null){
       System.out.println("Invalid ID");
     }
     else {
       System.out.println("Client Name: " + warehouse.getClientName(selectedClient));
       System.out.println("Client Phone: " + warehouse.getClientPhone(selectedClient));
       System.out.println("Client Address: " + warehouse.getClientAddress(selectedClient));
       System.out.println("Client Balance: " + warehouse.getClientBalance(selectedClient).toString());
     }
   }

   public void showProducts() {
      Iterator allProducts = warehouse.getProducts();
      System.out.println("List of Products: ");
      while (allProducts.hasNext()){
        Product product = (Product)(allProducts.next());
        System.out.println(product.toString());
        System.out.println("Product Name: " + warehouse.getProductName(product));
        System.out.println("Product Sale Price: " + warehouse.getProductSalePrice(product));      
      }
    }

    public void showClientWaitlist(){
      String id = getToken("Enter Client Id");
      Client selectedClient = warehouse.validateClient(id);
      if (selectedClient == null){
        System.out.println("Invalid ID");
      }
      else{
        Iterator allProducts = warehouse.getProducts();
        while (allProducts.hasNext()){
          Product product = (Product)(allProducts.next());
          if(warehouse.getProductWaitlistQty(product) > 0){
            Iterator productWaitlist = product.getWaitList();
            if (!productWaitlist.hasNext()){
              System.out.println("Wait List empty!");
            }
            while (productWaitlist.hasNext()){
              WaitListEntry waitListEntry = (WaitListEntry)(productWaitlist.next());
              if (waitListEntry.getClientid() == id){
                System.out.println("Product Name: " + warehouse.getProductName(product));
                System.out.println("Product Sale Price: " + warehouse.getProductSalePrice(product));      
              }
            }	  
          }
        }
      }
    }
    
    public void logout() {//
      if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk) {
         WarehouseContext.instance().changeState(1); // Become a clerk
      }
      else 
         (WarehouseContext.instance()).changeState(3); //Go to LoginState
    }

    public void help() {
      System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
      System.out.println(EXIT + " to exit the program\n");
      System.out.println(CLIENT_DETALS + " to see a client's details ");
      System.out.println(LIST_PRODUCTS + " to show products");
      System.out.println(CLIENT_TRANSACTIONS + " to show client transactions ");
      System.out.println(ADD_TO_CART + " to add an item to the cart");
      System.out.println(EDIT_CART + " to edit shopping cart");
      System.out.println(WAIT_LIST + " to see a client's waitlist");
      System.out.println(HELP + " for help");
      System.out.println(LOGOUT + " to logout");
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
    private boolean yesOrNo(String prompt) {
      String more = getToken(prompt + " (Y|y)[es] or anything else for no");
      if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
        return false;
      }
      return true;
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
   
    public void process() {
      int command;
        help();
        while ((command = getCommand()) != EXIT) {
          switch (command) {
            case CLIENT_DETALS:     showClientDetails();
                                    break;
            case LIST_PRODUCTS:     showProducts();
                                    break;
            case CLIENT_TRANSACTIONS: showTransactions();
                                    break;
            case ADD_TO_CART:		addProductToCart();
            						break;
            case EDIT_CART: 		editCart();
                          			break;
            case WAIT_LIST:  		showClientWaitlist();
                                    break;
            case LOGOUT:          	logout();
                        			break;	                                
            case HELP:              help();
                                    break;
          }
        }
        logout();
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
    
    public void run() {
      process();
    }
  }
