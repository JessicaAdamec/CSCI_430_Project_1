import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {

  private static final long serialVersionUID = 1L;
  private ClientList clientList;
  private ProductList productList;
  private SupplierList supplierList;
    
  private static Warehouse warehouse;
  
  private Warehouse() {
    clientList = ClientList.instance();
    productList = ProductList.instance();
    supplierList = SupplierList.instance();
  }
  
  public static Warehouse instance() {
    if (warehouse == null) {
      ClientIdServer.instance(); // instantiate all singletons
      SupplierIdServer.instance();
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }
  
  public Client addClient(String name, String address, String phone) {
    Client client = new Client(name, address, phone);
    if (clientList.insertClient(client)) {
      return (client);
    }
    return null;
  }    
  public Product addProduct(String id, String name, String price, 
		int inventory, String supplierID) {
	    Product product = new Product(id, name);
	    if (productList.insertProduct(product)) {
	      return (product);
	    }
	    return null;
  } 
  public Supplier addSupplier(String name, String address, String phone) {
	  	Supplier supplier = new Supplier(name, address, phone);
	    if (supplierList.insertSupplier(supplier)) {
	      return (supplier);
	    }
	    return null;
	  }
  
  public Iterator getClients() {
      return clientList.getClients();
  }
  public Iterator getProducts() {
      return productList.getProducts();
  }
  public Iterator getSuppliers() {
      return supplierList.getSuppliers();
  }
  public ShoppingCart getShoppingCart(Client client) {
	  return client.getShoppingCart();
  }
  
  public Client validateClient(String id) {
	  Iterator allClients = warehouse.getClients();
	  while (allClients.hasNext()){
		  Client client = (Client)(allClients.next());
		  if ((client.getId()).equals(id)) {
			  return client;
		  }
	  }
	  return null;
  }
  public Product validateProduct(String id) {
	  Iterator allProducts = warehouse.getProducts();
	  while (allProducts.hasNext()){
		  Product product = (Product)(allProducts.next());
		  if ((product.getID()).equals(id)) {
			  return product;
		  }
	  }
	  return null;
  }
  public Supplier validateSupplier(String id) {
	  Iterator allSuppliers = warehouse.getSuppliers();
	  while (allSuppliers.hasNext()){
		  Supplier supplier = (Supplier)(allSuppliers.next());
		  if ((supplier.getId()).equals(id)) {
			  return supplier;
		  }
	  }
	  return null;
  }

  public void editClientName(Client client, String name) {
	  client.setName(name);
  }
  public void editClientAddress(Client client, String address) {
	  client.setAddress(address);
  }
  public void editClientPhone(Client client, String phone) {
	  client.setPhone(phone);
  }
  
  public void editProductName(Product product, String name) {
	  product.setName(name);
  }
//  public void editProductPrice(Product product, ProductSupplier productSupplier, String price) {
//	  product.productSupplier.setPrice(price);
//  }
//  public void editProductInventory(Product product, int inventory) {
//	  product.setInventory(inventory);
//  }
//  public void editProductSupplierID(Product product, String supplierID) {
//	  product.setSupplierID(supplierID);
//  }
  
  public void editSupplierName(Supplier supplier, String name) {
	  supplier.setName(name);
  }
  public void editSupplierAddress(Supplier supplier, String address) {
	  supplier.setAddress(address);
  }
  public void editSupplierPhone(Supplier supplier, String phone) {
	  supplier.setPhone(phone);
  }
  
  public void addItemToCart(Client client, Product product, int qty) {
	  CartItem cartItem = new CartItem(product.getID(), qty);
      client.addItemToCart(cartItem);
  }

  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input);
      SupplierIdServer.retrieve(input);
      return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }
  
  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(ClientIdServer.instance());
      output.writeObject(SupplierIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public String toString() {
    return "Warehouse System\n";
  }
}
