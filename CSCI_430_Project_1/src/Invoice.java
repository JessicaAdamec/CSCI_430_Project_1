
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
	public void addWaitListedItenSting(Product product, WaitListEntry entry) {
		String productStr = product.toString();
		String entryStr = entry.toString();
		invoiceString += productStr + entryStr;
	}
	public String toString() {
	    return invoiceString;
	}
}