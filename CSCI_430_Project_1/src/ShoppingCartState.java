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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ShoppingCartState extends WarehouseState implements ActionListener{
	private static ShoppingCartState shoppingCartState;
	private static Warehouse warehouse;
	private WarehouseContext context;

	 private static final int EXIT = 0;
	 private static final int VIEW_CART = 1;
	 private static final int ADD_TO_CART = 2;
	 private static final int REMOVE_FROM_CART = 3;
	 private static final int CHANGE_QUANTITY = 4;
	 private static final int HELP = 5;
	 
	 private JFrame frame;
	 private AbstractButton viewCartButton, addToCartButton, removeFromCartButton, changeQuantityButton, exitButton;
	 private JTextArea textArea = new JTextArea(10, 30);
	 private JScrollPane scrollArea;
		 
	 private GetPrompts getPrompt;
	 private GUIMaker guiMaker = new GUIMaker();

	  
	  private ShoppingCartState() {
	    super();
	    warehouse = Warehouse.instance();
	  }

	  public static ShoppingCartState instance() {
	    if (shoppingCartState == null) {
	      return shoppingCartState = new ShoppingCartState();
	    } else {
	      return shoppingCartState;
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
	  	  	viewCartButton = guiMaker.makeButton("View Cart", VIEW_CART, this);
	  	  	addToCartButton = guiMaker.makeButton("Add to Cart", ADD_TO_CART, this);
	  	  	removeFromCartButton = guiMaker.makeButton("Remove from Cart", REMOVE_FROM_CART, this);
	  	  	changeQuantityButton = guiMaker.makeButton("Change cart quantity", CHANGE_QUANTITY, this);
		 	exitButton = guiMaker.makeButton("Exit", EXIT, this); 
	  	  frame.getContentPane().add(this.viewCartButton);
	  	  frame.getContentPane().add(this.addToCartButton);
	  	  frame.getContentPane().add(this.removeFromCartButton);
	  	  frame.getContentPane().add(this.changeQuantityButton);
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
	            case VIEW_CART:     	viewCart();
	                                    break;
	            case ADD_TO_CART:		addProductToCart();
	            						break;
	            case REMOVE_FROM_CART: 	removeProductFromCart();
	                          			break;
	            case CHANGE_QUANTITY:  	editCart();
	                                    break;                     
	            case EXIT:              exitCart();
	                                    break;
	    	}
                              
	   }
	  
	  public void addProductToCart() {
	        String id = WarehouseContext.instance().getClient();
	        Client client = warehouse.validateClient(id); 
	        if (client == null) {
	            JOptionPane.showMessageDialog(frame, "Invalid ID");
	        }
	        else {
	            String productId = getPrompt.getToken("Enter product id");
	            Product product = warehouse.validateProduct(productId); 
	            if (product == null) {
	                JOptionPane.showMessageDialog(frame, "Invalid ID");
	            }
	            else {
	               String quantity = getPrompt.getToken("Enter quantity");
	               int qty = Integer.valueOf(quantity);
	               warehouse.addItemToCart(client, product, qty);
	               textArea.setText("The value of shopping cart after adding product: " 
	                                               +  warehouse.getShoppingCart(client));
	            }
	            
	        }
	   }
	  
	  public void removeProductFromCart() {
		  String id = WarehouseContext.instance().getClient();
	        Client client = warehouse.validateClient(id); 
	        if (client == null) {
	            JOptionPane.showMessageDialog(frame, "Invalid ID");
	        }
	        else {
	            String productId = getPrompt.getToken("Enter product id");
	            CartItem cartItem = warehouse.validateCartItem(productId, client); 
	            if (cartItem == null) {
	                JOptionPane.showMessageDialog(frame, "Invalid ID");
	            }
	            else {
	            	warehouse.removeItemFromCart(client, cartItem);
	            }
	        }
	  }
	  
	  public void viewCart() {
		  String id = WarehouseContext.instance().getClient();
	      Client client = warehouse.validateClient(id); 
	      String output = "";
		  output += "Contents of the shopping cart: \n";
	      output += warehouse.getShoppingCart(client);
	      textArea.setText(output);
	  }
	  
	  public void editCart() {
	      String id = WarehouseContext.instance().getClient();
	      Client client = warehouse.validateClient(id); 
	      String output = "";
	      if (client == null) {
	        JOptionPane.showMessageDialog(frame, "Invalid ID");
	      }
	      else {
	        String productId = "";
	        while (!productId.equals("EXIT")) { 
	          productId = getPrompt.getToken("Enter product id or EXIT to exit");
	          CartItem cartItem = warehouse.validateCartItem(productId, client);
	          if (cartItem == null) {
	            if(!productId.equals("EXIT"))
	              JOptionPane.showMessageDialog(frame, "Invalid ID");
	          } else {
	            int qty = getPrompt.getNumber("Enter new quantity");
	            cartItem.setQuantity(qty);
	            output += cartItem.toString() + " \n";
	          }
	        }
	        textArea.setText(output);
	      }
	   }
	  
	  public void exitCart() {
	      WarehouseContext.instance().changeState(0); //Go to ClientState
	    }
}