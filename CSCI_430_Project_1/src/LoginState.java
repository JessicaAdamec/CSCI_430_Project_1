//Class by Jessica Adamec
//Created 3/20/2021 for CSCI 430

import java.util.*;
import java.text.*;
import java.io.*;
public class LoginState extends WarehouseState{
  private static final int CLIENT_LOGIN = 0;
  private static final int CLERK_LOGIN = 1;
  private static final int MANAGER_LOGIN = 2;
  private static final int EXIT = 3;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
  private Warehouse context;
  private static LoginState instance;
  
  private LoginState() {
      super();
     // context = LibContext.instance(); //maybe we can delete this commented part
  }

  public static LoginState instance() {
    if (instance == null) {
      instance = new LoginState();
    }
    return instance;
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" ));
        if (value <= EXIT && value >= CLIENT_LOGIN) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
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
  
  private void client() {
    String clientID = getToken("Please input the client id: ");
    if (Warehouse.instance().validateClient(clientID) != null){
      (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
      (WarehouseContext.instance()).setClient(clientID);      
      (WarehouseContext.instance()).changeState(0);
    }
    else 
      System.out.println("Invalid client id.");
  }

  private void clerk(){
	  (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);  
	  (WarehouseContext.instance()).changeState(1);
  }

  private void user(){
	  (WarehouseContext.instance()).setLogin(WarehouseContext.IsManager);  
	  (WarehouseContext.instance()).changeState(2);
  } 

  public void process() {
    int command;
    System.out.println("Please input 0 to login as Client\n"+ 
                        "input 1 to login as Clerk\n" +
                        "input 2 to login as Manager\n" +
                        "input 3 to exit the system\n");     
    while ((command = getCommand()) != EXIT) {

      switch (command) {
      	case CLIENT_LOGIN:		client();
      							break;
      
      	case CLERK_LOGIN:       clerk();
                                break;
        case MANAGER_LOGIN:        user();
                                break;
        default:                System.out.println("Invalid choice");
                                
      }
      System.out.println("Please input 0 to login as Client\n"+ 
              "input 1 to login as Clerk\n" +
              "input 2 to login as Manager\n" +
              "input 3 to exit the system\n");  
    }
    (WarehouseContext.instance()).changeState(3);
  }

  public void run() {
    process();
  }
}
