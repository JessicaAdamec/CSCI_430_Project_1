/**
  * NAME: Eric Young
  * DATE: 2/09/2021
  */
  
  import java.util.*;
  import java.lang.*;
  import java.io.*;
  
public class Product implements Serializable {
	private String id;		//identifier for product
	private String name; 		//name of the product
	private ProductSupplierList productSupplierList;

	public Product (String id, String name) {
	  this.id = id;
	  this.name = name;
	}

	public void setName(String name) {
	  this.name = name;
	}	  

	public String getID() {
	  return id;
	}

	public String getName() {
	  return name;
	}
	
	public void addProductSupplier(String supplierId, double price) {
		ProductSupplier productSupplier = new ProductSupplier(supplierId, price);
		productSupplierList.insertProductSupplier(productSupplier);
	}
	
	public ProductSupplierList getSupplierList() {
		return productSupplierList;
	}
	
	public ProductSupplier getProductSuppler(String supplierId) {
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

	public String toString() {
	  return "id: " + id + " name: " + name;
	}
}