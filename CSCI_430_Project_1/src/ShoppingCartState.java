import java.util.*;
import java.text.*;
import java.io.*;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ShoppingCartState extends WarehouseState {
	private static ShoppingCartState shoppingCartState;
	private static Warehouse warehouse;
	private WarehouseContext context;

	 private static final int EXIT = 0;
	 private static final int VIEW_CART = 1;
	 private static final int ADD_TO_CART = 2;
	 private static final int REMOVE_FROM_CART = 3;
	 private static final int CHANGE_QUANTITY = 4;
	 private static final int HELP = 5;
	 
	 private GetPrompts getPrompt = new GetPrompts(EXIT, HELP);

	  
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
	      process();
	  }
	  
	  public void process() {
	      int command;
	        help();
	        while ((command = getPrompt.getCommand()) != EXIT) {
	          switch (command) {
	            case VIEW_CART:     	viewCart();
	                                    break;
	            case ADD_TO_CART:		addProductToCart();
	            						break;
	            case REMOVE_FROM_CART: 	removeProductFromCart();
	                          			break;
	            case CHANGE_QUANTITY:  	editCart();
	                                    break;                     
	            case HELP:              help();
	                                    break;
	          }
	        }
	        exitCart();
	   }
	  
	  public void addProductToCart() {
	        String id = WarehouseContext.instance().getClient();
	        Client client = warehouse.validateClient(id); 
	        if (client == null) {
	            System.out.println("Invalid ID");
	        }
	        else {
	            String productId = getPrompt.getToken("Enter product id");
	            Product product = warehouse.validateProduct(productId); 
	            if (product == null) {
	                System.out.println("Invalid ID");
	            }
	            else {
	               String quantity = getPrompt.getToken("Enter quantity");
	               int qty = Integer.valueOf(quantity);
	               warehouse.addItemToCart(client, product, qty);
	               System.out.println("The value of shopping cart after adding product: " 
	                                               +  warehouse.getShoppingCart(client));
	            }
	        }
	   }
	  
	  public void removeProductFromCart() {
		  String id = WarehouseContext.instance().getClient();
	        Client client = warehouse.validateClient(id); 
	        if (client == null) {
	            System.out.println("Invalid ID");
	        }
	        else {
	            String productId = getPrompt.getToken("Enter product id");
	            CartItem cartItem = warehouse.validateCartItem(productId, client); 
	            if (cartItem == null) {
	                System.out.println("Invalid ID");
	            }
	            else {
	            	warehouse.removeItemFromCart(client, cartItem);
	            }
	        }
	  }
	  
	  public void viewCart() {
		  String id = WarehouseContext.instance().getClient();
	      Client client = warehouse.validateClient(id); 
		  System.out.println("Contents of the shopping cart: ");
	      System.out.println(warehouse.getShoppingCart(client));
	  }
	  
	  public void editCart() {
	      String id = WarehouseContext.instance().getClient();
	      Client client = warehouse.validateClient(id); 
	      if (client == null) {
	        System.out.println("Invalid ID");
	      }
	      else {
	        String productId = "";
	        while (!productId.equals("EXIT")) { 
	          productId = getPrompt.getToken("Enter product id or EXIT to exit");
	          CartItem cartItem = warehouse.validateCartItem(productId, client);
	          if (cartItem == null) {
	            if(!productId.equals("EXIT"))
	              System.out.println("Invalid ID");
	          } else {
	            int qty = getPrompt.getNumber("Enter new quantity");
	            cartItem.setQuantity(qty);
	            System.out.println(cartItem.toString());
	          }
	        }
	      }
	   }
	  
	  public void exitCart() {
	      WarehouseContext.instance().changeState(0); //Go to ClientState
	    }

		public void help() {
		  System.out.println("\nEnter a number between " + EXIT + " and " + HELP + " as explained below:\n");
		  System.out.println(EXIT + " to exit the cart\n");
		  System.out.println(VIEW_CART + " to view the cart ");
		  System.out.println(ADD_TO_CART + " to add item to the cart");
		  System.out.println(REMOVE_FROM_CART + " to remove item from the cart");
		  System.out.println(CHANGE_QUANTITY + " to change a cart item quantity");
		  System.out.println(HELP + " for help");
		}
}