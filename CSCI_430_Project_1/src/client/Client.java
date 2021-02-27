package client;
/**
  * NAME: Jessica Adamec
  * DATE: 2/09/2021
  */

import java.io.Serializable;
import java.util.Iterator;

import transaction.Transaction;
import transaction.TransactionList;
import warehouse.CartItem;
import warehouse.ShoppingCart;

public class Client implements Serializable {
	
	  private static final long serialVersionUID = 1L;
	  private String name;
	  private String address;
	  private String phone;
	  private String id;
	  private double balance;
	  private static final String CLIENT_STRING = "C";
	  private TransactionList transactionList;
	  private ShoppingCart shoppingCart;

	  public  Client (String name, String address, String phone) {
	    this.name = name;
	    this.address = address;
	    this.phone = phone;
	    id = CLIENT_STRING + (ClientIdServer.instance()).getId();
	    shoppingCart = new ShoppingCart();   
	    this.balance = 0.00;
	    transactionList = new TransactionList();
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
	  public Double getBalance(){
		  return balance;
	  }
	  public ShoppingCart getShoppingCart() {
		 return shoppingCart;
	  }	  
	  public Iterator getShoppingCartList() {
		 return shoppingCart.getProducts();
	  }
	  public Iterator getTransactionList() {
		return transactionList.getTransactions();
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
	  public void addTransactions(Transaction transaction) {
		transactionList.insertTransaction(transaction);
	}
	  public void updateBalance(double amount) {
		  balance += amount;
	  }
	  public String toString() {
	    String string = "Client name: " + name + " Address: " + address + " ID: " + id + " Phone: " + phone;
	    return string;
	  }
}