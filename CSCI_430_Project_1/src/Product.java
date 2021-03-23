  
  import java.io.Serializable;
import java.util.Iterator;
  
public class Product implements Serializable {
	
	private String id;		//identifier for product
	private String name; 		//name of the product
	private ProductSupplierList productSupplierList;
	private double salePrice;
	private int inventory;
	private WaitList waitList;

	public Product (String id, String name, double salePrice, String supplierID, double purchasePrice, int inventory) {
	  this.id = id;
	  this.name = name;
	  this.salePrice = salePrice;
	  this.inventory = inventory;
	  productSupplierList = new ProductSupplierList();
	  addProductSupplier(supplierID, purchasePrice);
	  waitList = new WaitList();
	}

	public void setName(String name) {
	  this.name = name;
	}	  
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
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
	public Iterator getWaitList() {
		return waitList.getWaitList();
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void addProductSupplier(String supplierId, double price) {
		ProductSupplier productSupplier = new ProductSupplier(supplierId, price);
		productSupplierList.insertProductSupplier(productSupplier);
	}
	
	public void removeProductSupplier(String supplierId) {
		ProductSupplier productSupplier = getProductSupplier(supplierId);
		productSupplierList.removeProductSupplier(productSupplier);
	}
	
	public void addEntryToWaitlist(String orderId, int qty, String clientId) {
		WaitListEntry entry = new WaitListEntry(orderId, qty, clientId);
		waitList.insertWaitListEntry(entry);
	}
	public ProductSupplierList getSupplierList() {
		return productSupplierList;
	}
	public ProductSupplier getProductSupplier(String supplierId) {
		ProductSupplier retProductSupplier = null;
		Iterator allProductSuppliers = productSupplierList.getProductSuppliers();
		while (allProductSuppliers.hasNext()){
			ProductSupplier productSupplier = (ProductSupplier)(allProductSuppliers.next());
			if (supplierId.equals(productSupplier.getSupplierId())) {
				retProductSupplier = productSupplier;
				break;
			}
		}
		return retProductSupplier;
	}
	public void addInventory(int amount) {
		inventory += amount;
	}
	public int removeInventory(int amount) {
		inventory -= amount;
		int inventoryShort = 0;
		if (inventory < 0) {
			inventoryShort = Math.abs(inventory);
			inventory = 0;
		}		
		return inventoryShort;
	}
	public String toString() {
	  return "ID: " + id + " Name: " + name;
	}
}