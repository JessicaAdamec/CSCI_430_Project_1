import java.util.*;
import java.text.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.StringTokenizer;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class QueryClientState extends WarehouseState implements ActionListener {
	private static QueryClientState queryClientState;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private WarehouseContext context;

	 private static final int EXIT = 0;
	 private static final int DISPLAY_ALL = 1;
	 private static final int OUTSTANDING_BALANCE = 2;
	 private static final int NO_TRANSACTIONS = 3;
	 private static final int HELP = 4;
	 
	 private JFrame frame;
	 private AbstractButton showAllButton, showOutstandingBalanceButton, showNoTransactionButton, 
	 	exitButton;
	 private JTextArea textArea = new JTextArea(10, 30);
	 private JScrollPane scrollArea;
	 
	 private GetPrompts getPrompt;
	 private GUIMaker guiMaker = new GUIMaker();

	  
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
	      GUIprocess();
	  }
	  
	  public void GUIprocess() {
	  	  frame = WarehouseContext.instance().getFrame();
	  	  getPrompt = new GetPrompts(frame);
	  	  frame.getContentPane().removeAll();
	  	  frame.getContentPane().setLayout(new FlowLayout());
	  	    showAllButton = guiMaker.makeButton("Show All Clients", DISPLAY_ALL, this);
	  	    showOutstandingBalanceButton = guiMaker.makeButton("Show Clients with Outstanding Balance", OUTSTANDING_BALANCE, this); 
	  	    showNoTransactionButton = guiMaker.makeButton("Show Clients with No Tranactions", NO_TRANSACTIONS, this); 
		 	exitButton = guiMaker.makeButton("Exit", EXIT, this); 
	  	  frame.getContentPane().add(this.showAllButton);
	  	  frame.getContentPane().add(this.showOutstandingBalanceButton);
	  	  frame.getContentPane().add(this.showNoTransactionButton);
	  	  frame.getContentPane().add(this.exitButton);
	  	  textArea.setEditable(false);
	  	  scrollArea = new JScrollPane(textArea);
	  	  scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	  	  frame.getContentPane().add(this.scrollArea, BorderLayout.CENTER);
	  	  frame.setVisible(true);
	  	  frame.paint(frame.getGraphics()); 
	  	  //frame.repaint();
	  	  frame.toFront();
	  	  frame.requestFocus();
	  }
	  
	  public void actionPerformed(ActionEvent event) {
	    	int command = Integer.parseInt(event.getActionCommand());
	    	switch (command) {
	            case DISPLAY_ALL:     	showAllClients();
	                                    break;
	            case OUTSTANDING_BALANCE:showClientsWithBalance();
	                                    break;
	            case NO_TRANSACTIONS: 	showClientsWithNoTransactions();
	                                    break;                             
	            case EXIT:              logout();
	                                    break;
          }
                                
	   }
	  
	  private void showAllClients() {
		  	Iterator allClients = warehouse.getClients();
			String output = "List of Clients: \n";
			while (allClients.hasNext()){
				Client client = (Client)(allClients.next());
			    output += client.toString();
			}
			textArea.setText(output);
	  }
	  
	  private void showClientsWithBalance() { 
			Iterator allClients = warehouse.getClients();
			String output = "List of Clients with Outstanding Balance: \n";
			while (allClients.hasNext()){
				Client client = (Client)(allClients.next());
			  	if (client.getBalance() > 0)
			    output += client.toString();
			}  
			textArea.setText(output);
	  }
	  
	  private void showClientsWithNoTransactions() {
		  	Iterator allClients = warehouse.getClients();
			String output = "List of Clients with No Transactions: \n";
			while (allClients.hasNext()){
				Client client = (Client)(allClients.next());
			  	Iterator transactionList = client.getTransactionList();
			  	if(!transactionList.hasNext())
			  		output += client.toString();
			}  
			textArea.setText(output);
	  }
	  
	  public void logout() {//
	      if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk) {
	         WarehouseContext.instance().changeState(1); // Become a clerk
	      }
	      else 
	         (WarehouseContext.instance()).changeState(3); //Go to LoginState
	    }

}