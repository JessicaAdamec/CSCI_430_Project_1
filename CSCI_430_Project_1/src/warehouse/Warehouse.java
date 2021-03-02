package warehouse;
import java.util.*;

import client.Client;
import client.ClientIdServer;
import client.ClientList;
import order.Order;
import order.OrderItem;
import order.OrderList;
import product.Product;
import product.ProductList;
import product.ProductSupplier;
import product.ProductSupplierList;
import supplier.Supplier;
import supplier.SupplierIdServer;
import supplier.SupplierList;
import transaction.Transaction;

import java.io.*;

public class Warehouse implements Serializable {

  private static final long serialVersionUID = 1L;
  private ClientList clientList;
  private ProductList productList;
  private SupplierList supplierList;   
  private OrderList orderList;
  private static Warehouse warehouse;
  
  private Warehouse() {
    clientList = ClientList.instance();
    productList = ProductList.instance();
    supplierList = SupplierList.instance();
    orderList = OrderList.instance();
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
  public Product addProduct(String id, String name, double salePrice, double purchasePrice, 
		int inventory, String supplierId) {
	    Product product = new Product(id, name, salePrice, supplierId, purchasePrice, inventory);
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

  public void addProductSupplier(String supplierId, Product productId, double price, int inventory) {
	  //Add an existing Supplier for a specific Product - STAGE 3
	  productId.addProductSupplier(supplierId, price);
  }
  
  public Iterator getClients() {
      return clientList.getClients();
  }
  public Iterator getProducts() {
      return productList.getProducts();
  }
  public int getProductWaitlistQty(Product product)  {
	  //return qty waitlisted for a specific product
	  int qty = 0;	  
	  Iterator productWaitlist = product.getWaitList();
	  while (productWaitlist.hasNext()){
		  WaitListEntry waitListEntry = (WaitListEntry)(productWaitlist.next());
		  qty += waitListEntry.getQuantity();
	  }	  
	  return qty;
  }
  
  public Iterator getProductWaitlist(Product product)  {
	  Iterator productWaitList = product.getWaitList();
	  return productWaitList;
  }
  
 public Iterator getSupplierList(Product productId) {
	  ProductSupplierList supplierList = productId.getSupplierList();
	  Iterator supplierIterator= supplierList.getProductSuppliers(); 
	  return supplierIterator;
  }
  
  public Iterator getSuppliers() {
      return supplierList.getSuppliers();
  }
  public ShoppingCart getShoppingCart(Client client) {
	  return client.getShoppingCart();
  }
  public Iterator getShoppingCartList(Client client) {
	 return client.getShoppingCartList();
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
  
  public CartItem validateCartItem(String productId, Client client) {
	  Iterator allCartItems = warehouse.getShoppingCartList(client);
	  while (allCartItems.hasNext()){
		  CartItem cartItem = (CartItem)(allCartItems.next());
		  if ((cartItem.getProductID()).equals(productId)) {
			  return cartItem;
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
  public void editProductPrice(Product product, double price) {
	  product.setSalePrice(price);
  }
  public void editProductInventory(Product product, int inventory) {
	  product.setInventory(inventory);
  }
//  public void editProductSupplierList(Product product, String supplierID){
//  add/delete from the list - Stage 3
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
  
  public Order newOrder(String clientID){
    Order order = new Order(clientID);
      if (orderList.insertOrder(order)) {
        return (order);
      }
      return null;
  } 

  public Invoice processOrder(Client client) {
	   Iterator cartList = getShoppingCartList(client);	
	   Order order = warehouse.newOrder(client.getId()); 
	   String orderId = order.getId();
	   double totalPurchasePrice = 0.00;
	   Invoice invoice = new Invoice();
	      while (cartList.hasNext()){
			  CartItem cartItem =  (CartItem) cartList.next();
			  int itemQty = cartItem.getQuantity();
			  String productId = cartItem.getProductID();  	  
			  OrderItem orderItem = new OrderItem(productId, itemQty); 
			  if (order.insertOrderItem(orderItem)) {
				 //success - added orderItem
			  }
			  else {
				 System.out.println("Failed to add orderItem");
			  }
			  Product product = validateProduct(productId);
			  int inventory = product.getInventory();
			  double price = product.getSalePrice();
			  double purchasePrice = price * itemQty;
			  invoice.addProductString(product, itemQty, purchasePrice);
			  totalPurchasePrice += purchasePrice;
			  int inventoryShortage = product.removeInventory(itemQty);
		    	  if (inventoryShortage > 0) {
		   	    	   //add negative qty to waitlist  		    		  
		    		  product.addEntryToWaitlist(orderId, inventoryShortage);
		            System.out.println("Amount added to waitlist: "+ inventoryShortage);
	    	  }
		      }//end while
	      client.updateBalance(totalPurchasePrice);
	      String description = "Order Id: " + orderId;
	      Transaction transaction = new Transaction(description, totalPurchasePrice);
	      client.addTransactions(transaction);
	      invoice.addTotalString(totalPurchasePrice);
	  return invoice;
	  
  }
  
  public void processPayment(Client client, String description, double payment) {
	  client.updateBalance((payment * -1));
	  
	  Transaction transaction = new Transaction(description, payment);
      client.addTransactions(transaction);
  }
  
  public void processWaitList() {
	  
  }
  
  public Iterator getOrders(){
    return orderList.getOrders();  
  }
  
  public Iterator getTransactions(Client client) { 
	  return client.getTransactionList();
  }
  
  //getInvoice() //return a specific Transaction for a specific Client
  
  
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
