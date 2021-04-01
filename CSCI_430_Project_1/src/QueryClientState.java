import java.util.*;
import java.text.*;
import java.io.*;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class QueryClientState extends WarehouseState {
	private static QueryClientState queryClientState;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private WarehouseContext context;

	 private static final int EXIT = 0;
	 private static final int DISPLAY_ALL = 1;
	 private static final int OUTSTANDING_BALANCE = 2;
	 private static final int NO_TRANSACTIONS = 3;
	 private static final int HELP = 4;
	 
	 private GetPrompts getPrompt = new GetPrompts(EXIT, HELP);

	  
	  private QueryClientState() {
	    super();
	    warehouse = Warehouse.instance();
	  }

	  public static QueryClientState instance() {
	    if (queryClientState == null) {
	      return queryClientState = new QueryClientState();
	    } else {
	      return queryClientState;
	    }
	  }
	  
	  public void run() {
	      process();
	  }
	  
	  public void process() {
	      int command;
	        help();
	        while ((command = getPrompt.getCommand()) != EXIT) {
	          switch (command) {
	            case DISPLAY_ALL:     	showAllClients();
	                                    break;
	            case OUTSTANDING_BALANCE:showClientsWithBalance();
	                                    break;
	            case NO_TRANSACTIONS: 	showClientsWithNoTransactions();
	                                    break;                             
	            case HELP:              help();
	                                    break;
	          }
	        }
	        logout();
	   }
	  
	  private void showAllClients() {
		  	Iterator allClients = warehouse.getClients();
			System.out.println("List of Clients: ");
			while (allClients.hasNext()){
			Client client = (Client)(allClients.next());
			    System.out.println(client.toString());
			}
	  }
	  
	  private void showClientsWithBalance() { 
			Iterator allClients = warehouse.getClients();
			System.out.println("List of Clients with Outstanding Balance: ");
			while (allClients.hasNext()){
				Client client = (Client)(allClients.next());
			  	if (client.getBalance() > 0)
			    System.out.println(client.toString());
			}  
	  }
	  
	  private void showClientsWithNoTransactions() {
		  	Iterator allClients = warehouse.getClients();
			System.out.println("List of Clients with No Transactions: ");
			while (allClients.hasNext()){
				Client client = (Client)(allClients.next());
			  	Iterator transactionList = client.getTransactionList();
			  	if(!transactionList.hasNext())
			  		System.out.println(client.toString());
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
	      System.out.println("\nEnter a number between " + EXIT + " and " + HELP + " as explained below:\n");
	      System.out.println(EXIT + " to exit to clerk menu");
	      System.out.println(DISPLAY_ALL + " to see a list of all clients");
	      System.out.println(OUTSTANDING_BALANCE + " to show list of clients with an outstanding balance.");
	      System.out.println(NO_TRANSACTIONS + " to show a list of clients with no transactions ");
	      System.out.println(HELP + " for help");
	    }
}