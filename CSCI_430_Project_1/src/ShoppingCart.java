  
import java.util.*;
import java.io.*;
  
public class ShoppingCart implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List cartItems = new LinkedList();
 
	public ShoppingCart() {
	}
	
	public boolean insertProduct(CartItem cartItem) {
		cartItems.add(cartItem);
		return true;
	}
	public boolean removeProduct(CartItem cartItem) {
		cartItems.remove(cartItem);
		return true;
	}
	public Iterator getProducts() {
		return cartItems.iterator();
	}
	public String toString() {
		return cartItems.toString();
	}
}