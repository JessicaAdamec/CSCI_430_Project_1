/**
  * NAME: Jessica Adamec
  * DATE: 2/09/2021
  */

import java.util.*;
import java.io.*;

public class Client implements Serializable {
	
	  private static final long serialVersionUID = 1L;
	  private String name;
	  private String address;
	  private String phone;
	  private String id;
	  private static final String CLIENT_STRING = "C";
	  private List transactions = new LinkedList();
	  private ShoppingCart shoppingCart;
	  
	  public  Client (String name, String address, String phone) {
	    this.name = name;
	    this.address = address;
	    this.phone = phone;
	    id = CLIENT_STRING + (ClientIdServer.instance()).getId();
	    shoppingCart = ShoppingCart.instance(id);	      
	  }

	  public String getName() {
	    return name;
	  }
	  public String getPhone() {
	    return phone;
	  }
	  public String getAddress() {
	    return address;
	  }
	  public String getId() {
	    return id;
	  }
	  public ShoppingCart getShoppingCart() {
		 return shoppingCart;
	  }	  
	  public void getShoppingCartList() {
		 shoppingCart.getProducts();
	  }
	  public void setName(String newName) {
	    name = newName;
	  }
	  public void setAddress(String newAddress) {
	    address = newAddress;
	  }
	  public void setPhone(String newPhone) {
	    phone = newPhone;
	  }
	  public boolean equals(String id) {
	    return this.id.equals(id);
	  }
	  public void addItemToCart(CartItem cartItem) {
		  shoppingCart.insertProduct(cartItem);
	  }
	  public String toString() {
	    String string = "Client name: " + name + " Address: " + address + " ID: " + id + " Phone: " + phone;
	    return string;
	  }
}