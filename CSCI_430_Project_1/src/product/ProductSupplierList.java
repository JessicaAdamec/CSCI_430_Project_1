package product;
/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

  public class ProductSupplierList implements Serializable {
	private List productSuppliers = new LinkedList();
	  
	public ProductSupplierList() {
	}
	
	public boolean insertProductSupplier(ProductSupplier productSupplier) {
		productSuppliers.add(productSupplier);
		return true;
	}
	
	public Iterator getProductSuppliers() {
		return productSuppliers.iterator();
	}
	
	public String toString() {
		return productSuppliers.toString();
	}
	
  }