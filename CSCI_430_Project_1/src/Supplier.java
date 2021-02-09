import java.util.*;
import java.io.*;
public class Supplier implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private static final String SUPPLIER_STRING = "S";
  private List booksBorrowed = new LinkedList();
  private List booksOnHold = new LinkedList();
  private List transactions = new LinkedList();
  public  Supplier (String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    id = SUPPLIER_STRING + (SupplierIdServer.instance()).getId();
  }

  public String getSupplierName() {
    return name;
  }
  public String getSupplierPhone() {
    return phone;
  }
  public String getSupplierAddress() {
    return address;
  }
  public String getSupplierId() {
    return id;
  }
  public void setSupplierName(String newName) {
    name = newName;
  }
  public void setSupplierAddress(String newAddress) {
    address = newAddress;
  }
  public void setSupplierPhone(String newPhone) {
    phone = newPhone;
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Supplier name: " + name + " address: " + address + " id: " + id + " phone: " + phone;
    return string;
  }
}
