/**
  * NAME: Eric Young
  * DATE: 2/09/2021
  */
  
  import java.util.*;
  import java.lang.*;
  import java.io.*;
  
  public class Product implements Serializable {
	  private String id;		//identifier for product
	  private String name; 		//name of the product
	  private String price;		//Changing to type 'String' to work with getToken method
	  private int inventory;	//how much of the product is available.
	  private String supplierID;
	  
	  public Product (String id, String name, String price, int inventory, String supplierID) {
		  this.id = id;
		  this.name = name;
		  this.price = price;
		  this.inventory = inventory;		  
		  this.supplierID = supplierID;
	  }
	 
	  
	  public void setName(String name) {
		  this.name = name;
	  }
	  
	  public void setInventory(int inventory) {
		this.inventory = inventory;
	  }
	  
	  public void addInventory(int num) {
		this.inventory = this.inventory + num;
	  }
	  
	  public void removeInventory(int num) {
		this.inventory = this.inventory - num;
	  }
	  
	  public void setPrice(String price) {
		  this.price = price;
	  }
	  
	  public void setSupplierID(String supplierID) {
		  this.supplierID = supplierID;
	  }
	  
	  public String getID() {
		  return id;
	  }
	  
	  public String getName() {
		  return name;
	  }
	  
	  public int getInventory() {
		  return inventory;
	  }
	  
	  public String getPrice() {
		  return price;
	  }
	  
	  public String getSupplierID() {
		  return supplierID;
	  }
	  
	  /*
	  
	  public Supplier getSupplier() {
		return Supplier;
	  }
	  
	  */
	  
	  public String toString() {
		  return "id: " + id + " name: " + name + " price: $" + price + " inventory: " + Integer.toString(inventory);
	  }
  }