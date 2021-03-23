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


  //Copied from Library example, need to update for our project
//  private static final int EXIT = 0;
//  private static final int ISSUE_BOOKS = 3;
//  private static final int RENEW_BOOKS = 5;
//  private static final int PLACE_HOLD = 7;
//  private static final int REMOVE_HOLD = 8;
//  private static final int GET_TRANSACTIONS = 10;
//  private static final int HELP = 13;
  
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
  
//  public String getToken(String prompt) {
//    do {
//      try {
//        System.out.println(prompt);
//        String line = reader.readLine();
//        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
//        if (tokenizer.hasMoreTokens()) {
//          return tokenizer.nextToken();
//        }
//      } catch (IOException ioe) {
//        System.exit(0);
//      }
//    } while (true);
//  }
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
//    System.out.println(ISSUE_BOOKS + " to  issue books to a  member");
//    System.out.println(RENEW_BOOKS + " to  renew books ");
//    System.out.println(PLACE_HOLD + " to  place a hold on a book");
//    System.out.println(REMOVE_HOLD + " to  remove a hold on a book");
//    System.out.println(GET_TRANSACTIONS + " to  print transactions");
//    System.out.println(HELP + " for help");
//  }
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
//fix the method below -- too messy
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

    public int getProductWaitlistQty(Product product) {
      int qty = 0;	  
      Iterator productWaitlist = product.getWaitList();
      while (productWaitlist.hasNext()){
        WaitListEntry waitListEntry = (WaitListEntry)(productWaitlist.next());
        qty += waitListEntry.getQuantity();
      }	  
      return qty;
    }
    
    public void logout() {//
      if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsManager) { //stem.out.println(" going to manager \n ");
         WarehouseContext.instance().changeState(2); // check this number
      }
      else //go to LoginState
         (WarehouseContext.instance()).changeState(3); // check this number
    }
//
//  public void issueBooks() {
//    Book result;
//    String memberID = LibContext.instance().getUser();
//    do {
//      String bookID = getToken("Enter book id");
//      result = library.issueBook(memberID, bookID);
//      if (result != null){
//        System.out.println(result.getTitle()+ "   " +  result.getDueDate());
//      } else {
//          System.out.println("Book could not be issued");
//      }
//      if (!yesOrNo("Issue more books?")) {
//        break;
//      }
//    } while (true);
//  }
//
//  public void renewBooks() {
//    Book result;
//    String memberID = LibContext.instance().getUser();
//    Iterator issuedBooks = library.getBooks(memberID);
//    while (issuedBooks.hasNext()){
//      Book book = (Book)(issuedBooks.next());
//      if (yesOrNo(book.getTitle())) {
//        result = library.renewBook(book.getId(), memberID);
//        if (result != null){
//          System.out.println(result.getTitle()+ "   " + result.getDueDate());
//        } else {
//          System.out.println("Book is not renewable");
//        }
//      }
//    }
//  }
//
//
//  public void placeHold() {
//    String memberID = LibContext.instance().getUser();
//    String bookID = getToken("Enter book id");
//    int duration = getNumber("Enter duration of hold");
//    int result = library.placeHold(memberID, bookID, duration);
//    switch(result){
//      case Library.BOOK_NOT_FOUND:
//        System.out.println("No such Book in Library");
//        break;
//      case Library.BOOK_NOT_ISSUED:
//        System.out.println(" Book is not checked out");
//        break;
//      case Library.NO_SUCH_MEMBER:
//        System.out.println("Not a valid member ID");
//        break;
//      case Library.HOLD_PLACED:
//        System.out.println("A hold has been placed");
//        break;
//      default:
//        System.out.println("An error has occurred");
//    }
//  }
//
//  public void removeHold() {
//    String memberID = LibContext.instance().getUser();
//    String bookID = getToken("Enter book id");
//    int result = library.removeHold(memberID, bookID);
//    switch(result){
//      case Library.BOOK_NOT_FOUND:
//        System.out.println("No such Book in Library");
//        break;
//      case Library.NO_SUCH_MEMBER:
//        System.out.println("Not a valid member ID");
//        break;
//      case Library.OPERATION_COMPLETED:
//        System.out.println("The hold has been removed");
//        break;
//      default:
//        System.out.println("An error has occurred");
//    }
//  }
//
//  public void getTransactions() {
//    Iterator result;
//    String memberID = LibContext.instance().getUser();
//    Calendar date  = getDate("Please enter the date for which you want records as mm/dd/yy");
//    result = library.getTransactions(memberID,date);
//    if (result == null) {
//      System.out.println("Invalid Member ID");
//    } else {
//      while(result.hasNext()) {
//        Transaction transaction = (Transaction) result.next();
//        System.out.println(transaction.getType() + "   "   + transaction.getTitle() + "\n");
//      }
//      System.out.println("\n  There are no more transactions \n" );
//    }
//  }
//
//  public void process() {
//    int command;
//    help();
//    while ((command = getCommand()) != EXIT) {
//      switch (command) {
//
//        case ISSUE_BOOKS:       issueBooks();
//                                break;
//        case RENEW_BOOKS:       renewBooks();
//                                break;
//        case PLACE_HOLD:        placeHold();
//                                break;
//        case REMOVE_HOLD:       removeHold();
//                                break;
//        case GET_TRANSACTIONS:  getTransactions();
//                                break;
//        case HELP:              help();
//                                break;
//      }
//    }
//    logout();
//  }
//
  
  public void run() {
    //process();
  }
  
//
//  public void logout()
//  {
//    if ((LibContext.instance()).getLogin() == LibContext.IsClerk)
//       { //stem.out.println(" going to clerk \n ");
//         (LibContext.instance()).changeState(1); // exit with a code 1
//        }
//    else if (LibContext.instance().getLogin() == LibContext.IsUser)
//       {  //stem.out.println(" going to login \n");
//        (LibContext.instance()).changeState(0); // exit with a code 2
//       }
//    else 
//       (LibContext.instance()).changeState(2); // exit code 2, indicates error
//  }
// 


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
}