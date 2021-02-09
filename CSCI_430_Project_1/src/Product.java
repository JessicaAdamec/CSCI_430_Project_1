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
	  private double price;
	  private int inventory;	//how much of the product is available.
	  //private Supplier supplier;
	  
	  //need to add supplier class as products work off of a product/supplier pairing
	  public Product (String id, String name, double price, int inventory) {
		  this.id = id;
		  this.name = name;
		  this.price = price;
		  this.inventory = inventory;
		  
		  //this.supplier = supplier
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
	  
	  public void setPrice(double price) {
		  this.price = price;
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
	  
	  public double getPrice() {
		  return price;
	  }
	  
	  /*
	  
	  public Supplier getSupplier() {
		return Supplier;
	  }
	  
	  */
	  
	  public String toString() {
		  return "id: " + id + " name: " + name + " price: $" + Double.toString(price) + " inventory: " + Integer.toString(inventory);
	  }
  }