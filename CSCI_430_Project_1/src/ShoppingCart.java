/**
  * NAME: Eric Young
  * DATE: 2/11/2021
  */
  
  import java.util.*;
  import java.io.*;
  
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;
	private static List cartItems = new LinkedList(); //Made this static
	private static ShoppingCart shoppingCart;
	private String clientID;

	public static ShoppingCart instance(String clientID) {
		if (cartItems == null) { //was productList which cannot be referenced here
		  return (shoppingCart = new ShoppingCart(clientID));
		} else {
		  return shoppingCart;
		}
	}
	
	public ShoppingCart(String clientID) {
		this.clientID = clientID;
	}
	
	public boolean insertProduct(CartItem cartItem) {
		cartItems.add(cartItem);
		return true;
	}
	public Iterator getProducts() {
		return cartItems.iterator();
	}
	
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
		  output.defaultWriteObject();
		  output.writeObject(cartItems);//was productList which cannot be referenced here
		} catch(IOException ioe) {
		  System.out.println(ioe);
		}
	}
	
	private void readObject(java.io.ObjectInputStream input) {
		try {
		  if (shoppingCart != null) {
			return;
		  } else {
			input.defaultReadObject();
			if (shoppingCart == null) {
			  shoppingCart = (ShoppingCart) input.readObject();
			} else {
			  input.readObject();
			}
		  }
		} catch(IOException ioe) {
		  System.out.println("in Catalog readObject \n" + ioe);
		} catch(ClassNotFoundException cnfe) {
		  cnfe.printStackTrace();
		}
	 }
	 
	public String toString() {
		return cartItems.toString();
	}
}