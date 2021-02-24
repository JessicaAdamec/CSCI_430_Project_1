/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */
  
  public class TransactionList implements Serializable {
	private List transactions = new LinkedList();
	  
	public TransactionList() {
	}
	
	public boolean insertTransaction(Transaction transaction) {
		transactions.add(transaction);
		return true;
	}
	
	public iterator getTransactions() {
		return iterator.transactions();
	}
	
	public String toString() {
		return transactions.toString();
	}
	
  }