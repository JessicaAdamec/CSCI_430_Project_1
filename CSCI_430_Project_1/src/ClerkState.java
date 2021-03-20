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
	System.out.println(EXIT + " to exit the program\n");
	System.out.println(ADD_CLIENT + " to add a client");
	System.out.println(SHOW_PRODUCTS + " to  show products");
	System.out.println(SHOW_CLIENTS + " to  show clients ");
	System.out.println(SHOW_CLIENTS_WITH_BALANCE + " to show clients with a balance");
	System.out.println(BECOME_CLIENT + " to  become a client");
	System.out.println(DISPLAY_WAITLIST + " to display the waitlist");
	System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
	System.out.println(LOGOUT + " to logout");
	System.out.println(HELP + " for help");
  }
  
  //Copied from Library example - need to update with our methods
//  -Add client - move existing method out of UI class
//	-Show Products - move existing method out of UI class. Show products with quantities and sale prices. The state invokes a method on Facade to get an iterator, and then extracts the needed information.
//	-Show list of clients - move existing method of UI class
//	-Show list of clients with outstanding balance - move existing method of UI class
//	-Become a client - (use example from ClerkState.java in LibraryFSM code). The actor will be asked to input a ClientID; if valid, this ID will be stored in Context, and the system transitions to the  ClientMenuState.
//	-Display the waitlist - move existing method of UI class 
//	-Receive a shipment - move existing method of UI class processShipment()
//	-Logout- (use example from UserState.java logout method in LibraryFSM). System transitions to the previous  state, which has to be remembered in the context. (If previous state was the OpeningState, it goes there; otherwise it goes to ManagerMenuState.)


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
//  public void logout()
//  {
//    (LibContext.instance()).changeState(0); // exit with a code 0
//  }
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
//  
  public void run() {
//    process();
  }
  

}
