/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */
  
import java.util.Currency;

public class ProductSupplier implements Serializable {
	private static DecimalFormat priceFormat = new DecimalFormat("##.##");
	
	private String supplierId;
	private double price;
	private int inventory;
	private WaitList waitList;
	
	public ProductSupplier(String supplierId, double price) {
		this.supplierId = supplierId;
		this.price = price;
		inventory = 0;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getInventory() {
		return inventory
	}
	
	public WaitList getWaitList() {
		return waitList;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setInventory(int inventory) {
		this.inventory = inventory
	}
	
	public void addInventory(int amount) {
		inventory += amount;
	}
	
	public int removeInventory(int amount) {
		inventory -= amount;
		int inventoryShort = 0;
		
		if (inventory < 0) {
			inventoryShort = Math.abs(inventory);
			inventory = 0
		}
		
		return inventoryShort;
	}
	
	public String toString() {
		String strPrice = String.format("%.2f", price);
		return "Supplier ID: " + supplierId + " Price: $" + strPrice + " Inventory: " + inventory; 
	}
}