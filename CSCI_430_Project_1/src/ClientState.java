//Class by William Doyle
//3/22/2021 CSCI 430 

import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ClientState extends WarehouseState implements ActionListener{
	
  private static ClientState clientState;
  private static Warehouse warehouse;
  private WarehouseContext context;

 private static final int EXIT = 0;
 private static final int CLIENT_DETAILS = 1;
 private static final int LIST_PRODUCTS = 2;
 private static final int CLIENT_TRANSACTIONS = 3;
 private static final int VIEW_CART = 4;
 private static final int WAIT_LIST = 5;
 private static final int LOGOUT = 6;
 private static final int HELP = 7;
 
 private JFrame frame;
 private AbstractButton detailsButton, productListButton, clientTransactionsButton, 
 	viewCartButton, showWaitListButton, logoutButton;
 private JTextArea textArea = new JTextArea(10, 30);
 private JScrollPane scrollArea;
 
 private GetPrompts getPrompt;
 private GUIMaker guiMaker = new GUIMaker();
  
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
        JOptionPane.showMessageDialog(frame, "Invalid ID");
      }
      else {
        String output = "Client transactions:" + " \n";
        Iterator allTransactions = warehouse.getTransactions(client);
        while (allTransactions.hasNext()){
          Transaction transaction = (Transaction)(allTransactions.next());
                output += transaction.toString() + " \n";
        }
        textArea.setText(output);
      }
    }  

   public void showClientDetails(){
     String id = WarehouseContext.instance().getClient();
     Client selectedClient = warehouse.validateClient(id);
     if (selectedClient == null){
       JOptionPane.showMessageDialog(frame, "Invalid ID");
     }
     else {
       String output = "Client Name: " + warehouse.getClientName(selectedClient)+ " \n";
       output += "Client Phone: " + warehouse.getClientPhone(selectedClient) + " \n";
       output += "Client Address: " + warehouse.getClientAddress(selectedClient) + " \n";
       output += "Client Balance: " + warehouse.getClientBalance(selectedClient).toString() + " \n";
       
       textArea.setText(output);
     }
   }

   public void showProducts() {
      Iterator allProducts = warehouse.getProducts();
      String output = "List of Products: \n";
      while (allProducts.hasNext()){
        Product product = (Product)(allProducts.next());
        output += product.toString();
        output += "Product Sale Price: " + warehouse.getProductSalePrice(product) + " \n \n";      
      }
      
      textArea.setText(output);
    }

    public void showClientWaitlist(){
      String id = WarehouseContext.instance().getClient();
      Client selectedClient = warehouse.validateClient(id);
      if (selectedClient == null){
        JOptionPane.showMessageDialog(frame, "Invalid ID");
      }
      else{
        Iterator allProducts = warehouse.getProducts();
        String output = "";
        while (allProducts.hasNext()){
          Product product = (Product)(allProducts.next());
          if(warehouse.getProductWaitlistQty(product) > 0){
            Iterator productWaitlist = product.getWaitList();
            if (!productWaitlist.hasNext()){
              JOptionPane.showMessageDialog(frame, "Wait List empty!");
            }
            while (productWaitlist.hasNext()){
              WaitListEntry waitListEntry = (WaitListEntry)(productWaitlist.next());
              if (waitListEntry.getClientid() == id){
                output += "Product Name: " + warehouse.getProductName(product) + " \n";
                output += "Product Sale Price: " + warehouse.getProductSalePrice(product) + " \n\n";      
              }
            }	  
          }
        }
        textArea.setText(output);
      }
    }
    
    public void logout() {//
      if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk || 
    		  WarehouseContext.instance().getLogin() == WarehouseContext.IsManager) {
         WarehouseContext.instance().changeState(1); // Become a clerk
      }
      else 
         (WarehouseContext.instance()).changeState(3); //Go to LoginState
    }
    
    public void viewCart() {
    	(WarehouseContext.instance()).changeState(4); //Go to ShoppingCartState
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
          JOptionPane.showMessageDialog(frame, "Please input a date as mm/dd/yy");
        }
      } while (true);
    }
   
    
    public void run() {
      GUIprocess();
    }
    
    public void GUIprocess() {
  	  frame = WarehouseContext.instance().getFrame();
  	  getPrompt = new GetPrompts(frame);
  	  frame.getContentPane().removeAll();
  	  frame.getContentPane().setLayout(new FlowLayout());
  	      detailsButton = guiMaker.makeButton("Client Details", CLIENT_DETAILS, this);
  	      productListButton =  guiMaker.makeButton("List Products", LIST_PRODUCTS, this);
  	      clientTransactionsButton = guiMaker.makeButton("Client Transactions", CLIENT_TRANSACTIONS, this);
  	      viewCartButton = guiMaker.makeButton("View Cart", VIEW_CART, this);
  	      showWaitListButton = guiMaker.makeButton("Show Waitlist", WAIT_LIST, this);
  	      logoutButton = guiMaker.makeButton("Logout", LOGOUT, this);  
  	  frame.getContentPane().add(this.detailsButton);
  	  frame.getContentPane().add(this.productListButton);
  	  frame.getContentPane().add(this.clientTransactionsButton);
  	  frame.getContentPane().add(this.viewCartButton);
  	  frame.getContentPane().add(this.showWaitListButton);
  	  frame.getContentPane().add(this.logoutButton);
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
	        case CLIENT_DETAILS:     showClientDetails();
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
    	}
    }
}
