package warehouse;

import product.Product;

public class Invoice {

	String invoiceString;
	
	Invoice(){
		invoiceString = "";
	}
	public void addProductString(Product product, int qty, double totalPrice) {
		String productStr = product.toString();
		String strTotalPrice = String.format("%.2f", totalPrice);
		invoiceString += product.toString() + " Quantity: " + Integer.toString(qty) + " Price: $" + strTotalPrice + " \n";
	}
	public void addTotalString(double totalPrice) {
		String strTotalPrice = String.format("%.2f", totalPrice);
		invoiceString += " Total: $" + strTotalPrice;
	}
	
	public String toString() {
	    return invoiceString;
	}
}

