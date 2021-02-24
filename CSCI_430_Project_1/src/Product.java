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

	public void setPrice(String price) {
	  this.price = price;
	}

	public String getID() {
	  return id;
	}

	public String getName() {
	  return name;
	}
	
	public void addProductSupplier(String supplierId, double price) {
		ProductSupplier productSupplier = new ProductSupplier(supplierId, price);
		ProductSupplierList.insertProductSupplier(productSupplier);
	}
	
	public ProductSupplierList getSupplierList() {
		return productSupplierList;
	}
	
	public ProductSupplier getProductSuppler(String supplierId) {
		ProductSupplier retProductSupplier;
		Iterator allProductSuppliers = ProductSupplierList.getProductSupplers();
		while (allProductSuppliers.hasNext()){
			ProductSupplier productSupplier = (productSupplier)(allProductSuppliers.next());
			if (supplierId.equals(productSupplier.getSupplierId()) {
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