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
  private static Warehouse warehouse;
  private WarehouseContext context;

 private static final int EXIT = 0;
 private static final int CLIENT_DETALS = 1;
 private static final int LIST_PRODUCTS = 2;
 private static final int CLIENT_TRANSACTIONS = 3;
 private static final int VIEW_CART = 4;
 private static final int WAIT_LIST = 5;
 private static final int LOGOUT = 6;
 private static final int HELP = 7;
 
 private GetPrompts getPrompt = new GetPrompts(EXIT, HELP);

  
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
      String id = WarehouseContext.instance().getClient();
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

   public void showClientDetails(){
     String id = WarehouseContext.instance().getClient();
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
      String id = WarehouseContext.instance().getClient();
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
    
    public void viewCart() {
    	(WarehouseContext.instance()).changeState(4); //Go to ShoppingCartState
    }

    public void help() {
      System.out.println("\nEnter a number between " + EXIT + " and " + HELP + " as explained below:\n");
      System.out.println(EXIT + " to exit the program\n");
      System.out.println(CLIENT_DETALS + " to see a client's details ");
      System.out.println(LIST_PRODUCTS + " to show products");
      System.out.println(CLIENT_TRANSACTIONS + " to show client transactions ");
      System.out.println(VIEW_CART + " to view the shopping cart");
      System.out.println(WAIT_LIST + " to see a client's waitlist");
      System.out.println(LOGOUT + " to logout");
      System.out.println(HELP + " for help");
    }

    public Calendar getDate(String prompt) {
      do {
        try {
          Calendar date = new GregorianCalendar();
          String item = getPrompt.getToken(prompt);
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
        while ((command = getPrompt.getCommand()) != EXIT) {
          switch (command) {
            case CLIENT_DETALS:     showClientDetails();
                                    break;
            case LIST_PRODUCTS:     showProducts();
                                    break;
            case CLIENT_TRANSACTIONS: showTransactions();
                                    break;
            case VIEW_CART: 		viewCart();
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
    
    public void run() {
      process();
    }
  }
