import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final int PRODUCT_NOT_FOUND  = 1;
  public static final int PRODUCT_ON_WAITLIST  = 2;
  public static final int OPERATION_COMPLETED= 3;
  public static final int OPERATION_FAILED= 4;
  public static final int NO_SUCH_MEMBER = 5;
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
	    Product product = new Product(id, name, price, inventory, supplierID);
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
  
  public Client editClient(String id, String name, String address, String phone) {
	  Iterator allClients = warehouse.getClients();
	  Client client = null;
	  while (allClients.hasNext()){
		  client = (Client)(allClients.next());
		  if (client.getId() == id) {
			  client.setName(name);
			  client.setAddress(address);
			  client.setPhone(phone);
		  }
		  else System.out.println("Invalid client id");
		  return null;
	  }
	  return client;
  }
  
  public Product editProduct(String id, String name, String price, int inventory, String supplier) {
	  Iterator allProducts = warehouse.getProducts();
	  Product product = null;
	  while (allProducts.hasNext()){
		  product = (Product)(allProducts.next());
		  if (product.getID() == id) {
			  product.setName(name);
			  product.setPrice(price);
			  product.setInventory(inventory);
			  product.setSupplierID(supplier);
		  }
		  else System.out.println("Invalid product id");
		  return null;
	  }
	  return product;
  }

  
  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input);
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
