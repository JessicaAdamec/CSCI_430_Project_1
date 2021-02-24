/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */
  
  public class ProductSupplierList implements Serializable {
	private List productSuppliers = new LinkedList();
	  
	public ProductSupplierList() {
	}
	
	public boolean insertProductSupplier(ProductSupplier productSupplier) {
		productSuppliers.add(productSupplier);
		return true;
	}
	
	public iterator getProductSuppliers() {
		return iterator.productSuppliers();
	}
	
	public String toString() {
		return productSuppliers.toString();
	}
	
  }