/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

  public class TransactionList implements Serializable {
	private List transactions = new LinkedList();
	  
	public TransactionList() {
	}
	
	public boolean insertTransaction(Transaction transaction) {
		transactions.add(transaction);
		return true;
	}
	
	public Iterator getTransactions() {
		return transactions.iterator();
	}
	
	public String toString() {
		return transactions.toString();
	}
	
  }