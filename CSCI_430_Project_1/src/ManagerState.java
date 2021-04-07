import java.util.*;

import javax.swing.*;

import java.text.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.*;
public class ManagerState extends WarehouseState {
	
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarehouseContext context;
  private static ManagerState instance;
  
  private static final int EXIT = 0;
  private static final int ADD_PRODUCT = 1;
  private static final int ADD_SUPPLIER = 2;
  private static final int SHOW_SUPPLIERS = 3;
  private static final int SHOW_PRODUCT_SUPPLIERS = 4;
  private static final int SHOW_SUPPLIER_PRODUCTS = 5;
  private static final int UPDATE_SUPPLIER_PRODUCTS = 6;
  private static final int LOGIN_AS_SALESCLERK = 7;
  private static final int LOGOUT = 8;
  private static final int HELP = 9;
  
  private JFrame frame;
  private AbstractButton addProductButton, addSupplierButton, shopSuppliersButton, 
	 	showProductSuppliersButton, showSupplierProductsButton, updateSupplierProductsButton,
	 	loginAsSalesClerkButton, logoutButton;
  private JTextArea textArea = new JTextArea(10, 30);
  private JScrollPane scrollArea;
	 
  private GetPrompts getPrompt;
  private GUIMaker guiMaker = new GUIMaker();
  
  private ManagerState() {
      super();
      warehouse = Warehouse.instance();
      //context = LibContext.instance();
  }

  public static ManagerState instance() {
    if (instance == null) {
      instance = new ManagerState();
    }
    return instance;
  }
  
	  
  public void addProduct() {
	    String id = getPrompt.getToken("Enter product ID");
	    String name = getPrompt.getToken("Enter product name");
	    double salePrice = getPrompt.getDouble("Enter sale price");
	    int inventory = getPrompt.getNumber("Enter product inventory");
	    String supplierID = getPrompt.getToken("Enter product supplier ID");
	    double purchasePrice = getPrompt.getDouble("Enter purchase price");
	    Product result;
	    result = warehouse.addProduct(id, name, salePrice, purchasePrice, inventory, supplierID);
	    if (result == null) {
	    	JOptionPane.showMessageDialog(frame, "Could not add product");
	    }
	    String myString = result + " Sale Price: $" + salePrice + " Inventory: "
	    		+ inventory + " Supplier ID: " + supplierID;  
	    textArea.setText(myString);
  } 
  
  public void addProduct(String supplierId) {
	  	String id = getPrompt.getToken("Enter product ID");
	    String name = getPrompt.getToken("Enter product name");
	    double salePrice = getPrompt.getDouble("Enter sale price");
	    int inventory = getPrompt.getNumber("Enter product inventory");
	    double purchasePrice = getPrompt.getDouble("Enter purchase price");
	    Product result;
	    result = warehouse.addProduct(id, name, salePrice, purchasePrice, inventory, supplierId);
	    if (result == null) {
	    	JOptionPane.showMessageDialog(frame, "Could not add product");
	    }
	    String myString = result + " Sale Price: $" + salePrice + " Inventory: "
	    		+ inventory + " Supplier ID: " + supplierId;  
	    
	    textArea.setText(myString);
  }
  
  public void addSupplier() {
	    String name = getPrompt.getToken("Enter supplier name");
	    String address = getPrompt.getToken("Enter supplier address");
	    String phone = getPrompt.getToken("Enter supplier phone number");
	    Supplier result;
	    result = warehouse.addSupplier(name, address, phone);
	    if (result == null) {
	    	JOptionPane.showMessageDialog(frame, "Could not add supplier");
	    }
  } 
  
  public void showAllSuppliers() {
      Iterator allSuppliers = warehouse.getSuppliers();
      String output = "List of Suppliers: \n";
      while (allSuppliers.hasNext()){
	  Supplier supplier = (Supplier)(allSuppliers.next());
          output += supplier.toString() + " \n";
      }
      textArea.setText(output);
  }
  
  public void showProductSuppliers() {  
	  String id = getPrompt.getToken("Enter product id");
	   Product product = warehouse.validateProduct(id); 
	   if (product == null) {
		   JOptionPane.showMessageDialog(frame, "Invalid ID");
	   }
	   else {
		  Iterator productSuppliers = warehouse.getSupplierList(product);
	      String output = "List of Suppliers: \n";
	      while (productSuppliers.hasNext()){
		  ProductSupplier pSupplier = (ProductSupplier)productSuppliers.next();
	          output += pSupplier.toString() + " \n";
	      }
	      
	      textArea.setText(output);
	   }
  }
  
  public void showSupplierProducts() {
	  String id = getPrompt.getToken("Enter supplier id");
	  Supplier supplier = warehouse.validateSupplier(id);
	  
	  if (supplier == null) {
		  JOptionPane.showMessageDialog(frame, "Invalid ID");
	  } else {
		  printSupplierProductList(id);
	  }
  }
  
  //UPDATE SUPPLIER PRODUCTS START
  public void updateSupplierProducts() {
	  String supplierId = getPrompt.getToken("Enter supplier id");
	  Supplier supplier = warehouse.validateSupplier(supplierId);
	  
	  if (supplier == null) {
		  JOptionPane.showMessageDialog(frame, "Invalid ID");
	  } else {
		  processSupplierProductsMenu(supplierId);
	  }
  }
  
  public void processSupplierProductsMenu(String supplierId)
  {
	  Object[] options = {"Add a product to the supplier", 
			  			  "Remove a product from the supplier",
			  			  "Edit purchase price of a product"};
	  
	  String s = (String)JOptionPane.showInputDialog(frame, "Select an option or press cancel", 
			  "Supplier Products Menu", JOptionPane.PLAIN_MESSAGE, null, options, "Add a product to the supplier");
			  
	  switch (s) {
		  	case "Add a product to the supplier":
		  		addProductSupplier(supplierId);
		  		break;
		  	case "Remove a product from the supplier":
		  		removeProductSuppler(supplierId);
		  		break;
		  	case "Edit purchase price of a product":
		  		editPurchasePrice(supplierId);
		  		break;
		  	default:
		  		break;
	  }
  }
  
  public void addProductSupplier(String supplierId) {
	  //Add an existing Supplier for a specific Product - STAGE 3
	   Supplier supplier = warehouse.validateSupplier(supplierId); 
	   if (supplier == null) {
		   JOptionPane.showMessageDialog(frame, "Could not validate the id");
	   }
	   else {
		   String productId = getPrompt.getToken("Enter product id");
		   Product updatedProduct = warehouse.validateProduct(productId); 
		   if (updatedProduct == null) {
			   addProduct(supplierId);
		   }
		   else {
			   double price = getPrompt.getDouble("Enter purchase price");
			   int inventory = getPrompt.getNumber("Enter inventory");
			   warehouse.addProductSupplier(supplierId, updatedProduct, price, inventory);   
		   }
	   }
  } 
  
  public void removeProductSuppler(String supplierId) {
	  String productId = getPrompt.getToken("Enter product id");
	  Product updatedProduct = warehouse.validateProduct(productId); 
	   if (updatedProduct == null) {
		   JOptionPane.showMessageDialog(frame, "Invalid ID");
	   } else {
		   warehouse.removeProductSupplier(supplierId, updatedProduct);
	   }
  }
  
  public void editPurchasePrice(String supplierId) {
	  String productId = getPrompt.getToken("Enter product id");
	  Product updatedProduct = warehouse.validateProduct(productId); 
	   if (updatedProduct == null) {
		   JOptionPane.showMessageDialog(frame, "Invalid ID");
	   } else {
		   ProductSupplier productSupplier = updatedProduct.getProductSupplier(supplierId);
		   if (productSupplier == null) {
			   JOptionPane.showMessageDialog(frame, "Invalid ID");
		   } else {
			   double price = getPrompt.getDouble("Enter purchase price");
			   warehouse.editProductSupplierPrice(productSupplier, price);
		   }
	   }
  }
  
  //END OF UPDATE SUPPLIER PRODUCTS
  
  //SHARED METHODS
  public void printSupplierProductList(String id) {
	  Iterator allProducts = warehouse.getProducts();
      String output = "List of Products: \n";
      while (allProducts.hasNext()){
    	  Product product = (Product)(allProducts.next());
    	  ProductSupplier productSupplier = product.getProductSupplier(id);
    	  
    	  if (productSupplier != null) {
    		  output += product.toString() + " \n";
    		  output += productSupplier.toString() + " \n\n";
    	  }
      }
      textArea.setText(output);
  }
  //END SHARED
  
  public void loginAsSalesClerk() {
	  (WarehouseContext.instance()).changeState(1);
  }
  
  public void logout()
  {
    (WarehouseContext.instance()).changeState(3); 
  }
  
  public void run() {
	  GUIprocess();
  }
  
  public void GUIprocess() {
  	  frame = WarehouseContext.instance().getFrame();
  	  getPrompt = new GetPrompts(frame);
  	  frame.getContentPane().removeAll();
  	  frame.getContentPane().setLayout(new FlowLayout());
 		addProductButton = guiMaker.makeButton("Add Product", ADD_PRODUCT, this);
  		addSupplierButton = guiMaker.makeButton("Add a Supplier", ADD_SUPPLIER, this); 
  		shopSuppliersButton = guiMaker.makeButton("Show Suppliers", SHOW_SUPPLIERS, this); 
  		showProductSuppliersButton = guiMaker.makeButton("Show suppliers of a product", SHOW_PRODUCT_SUPPLIERS, this); 
  		showSupplierProductsButton = guiMaker.makeButton("Show products of a supplier", SHOW_SUPPLIER_PRODUCTS, this); 
	 	updateSupplierProductsButton = guiMaker.makeButton("Update Supplier Products", UPDATE_SUPPLIER_PRODUCTS, this);
	 	loginAsSalesClerkButton = guiMaker.makeButton("Log In As Sales Clerk", LOGIN_AS_SALESCLERK, this);
	 	logoutButton = guiMaker.makeButton("Logout", LOGOUT, this);
  	  frame.getContentPane().add(this.addProductButton);
  	  frame.getContentPane().add(this.addSupplierButton);
  	  frame.getContentPane().add(this.shopSuppliersButton);
  	  frame.getContentPane().add(this.showProductSuppliersButton);
  	  frame.getContentPane().add(this.showSupplierProductsButton);
  	  frame.getContentPane().add(this.updateSupplierProductsButton);
  	  frame.getContentPane().add(this.loginAsSalesClerkButton);
  	  frame.getContentPane().add(this.logoutButton);
  	  textArea.setEditable(false);
  	  scrollArea = new JScrollPane(textArea);
  	  scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  	  frame.getContentPane().add(this.scrollArea, BorderLayout.CENTER);
  	  frame.setVisible(true);
  	  frame.paint(frame.getGraphics()); 
  	  //frame.repaint();
  	  frame.toFront();
  	  frame.requestFocus();
  }

  public void actionPerformed(ActionEvent event) {
	int command = Integer.parseInt(event.getActionCommand());
	switch (command) {
	  	case ADD_PRODUCT:       
	  							addProduct();
	                            break;
	    case ADD_SUPPLIER:      
	    						addSupplier();
	                            break;
	    case SHOW_SUPPLIERS:    
	    						showAllSuppliers();
	                            break;
	    case SHOW_PRODUCT_SUPPLIERS:
	    						showProductSuppliers();
	    						break;
	    case SHOW_SUPPLIER_PRODUCTS:
								showSupplierProducts();
								break;
	    case UPDATE_SUPPLIER_PRODUCTS:
	    						updateSupplierProducts();
								break;
	    case LOGIN_AS_SALESCLERK:
								loginAsSalesClerk();
								break;
	    case LOGOUT:          	
	    						logout();
	    						break;	                                
	}
}
  
}
