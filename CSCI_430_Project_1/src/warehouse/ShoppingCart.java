package warehouse;
/**
  * NAME: Eric Young
  * DATE: 2/11/2021
  */
  
  import java.util.*;
  import java.io.*;
  
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;
	private List cartItems = new LinkedList();
//	private static ShoppingCart shoppingCart; //Reevaluate this. This being a static object may be making there only be one shoppingCart
//	private String clientID; //Redundant now that shopping cart is an object in client. Remove references

//	public static ShoppingCart instance(String clientID) { //Make a non-instance constructor. This could be affecting things negatively as well.
//		if (shoppingCart == null) { 
//			return (shoppingCart = new ShoppingCart(clientID));	
//		} else {
//			return shoppingCart;
//		}		
//	}
	
	public ShoppingCart() {
//		this.clientID = clientID;
	}
	
	public boolean insertProduct(CartItem cartItem) {
		cartItems.add(cartItem);
		return true;
	}
	public Iterator getProducts() {
		return cartItems.iterator();
	}
	
//	private void writeObject(java.io.ObjectOutputStream output) {
//		try {
//		  output.defaultWriteObject();
//		  output.writeObject(shoppingCart);
//		} catch(IOException ioe) {
//		  System.out.println(ioe);
//		}
//	}
//	
//	private void readObject(java.io.ObjectInputStream input) {
//		try {
//		  if (shoppingCart != null) {
//			return;
//		  } else {
//			input.defaultReadObject();
//			if (shoppingCart == null) {
//			  shoppingCart = (ShoppingCart) input.readObject();
//			} else {
//			  input.readObject();
//			}
//		  }
//		} catch(IOException ioe) {
//		  System.out.println("in ShoppingCart readObject \n" + ioe);
//		} catch(ClassNotFoundException cnfe) {
//		  cnfe.printStackTrace();
//		}
//	 }
//	 
	public String toString() {
		return cartItems.toString();
	}
}