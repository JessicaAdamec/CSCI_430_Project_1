  
import java.io.Serializable;
import java.text.DecimalFormat;

public class ProductSupplier implements Serializable {
	
	private static DecimalFormat priceFormat = new DecimalFormat("##.##");
	private String supplierId;
	private double price;
	
	public ProductSupplier(String supplierId, double price) {
		this.supplierId = supplierId;
		this.price = price;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String toString() {
		String strPrice = String.format("%.2f", price);
		return "Supplier ID: " + supplierId + " Price: $" + strPrice; 
	}
}