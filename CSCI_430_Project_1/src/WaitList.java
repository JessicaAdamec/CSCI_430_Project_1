/*
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
  
  public class WaitList implements Serializable {
	private List waitListEntries = new LinkedList();
	  
	public WaitList() {
	}
	
	public Iterator getWaitList() {
		return Iterator.waitListEntries();
	}
	
	public boolean insertWaitListEntry(WaitListEntry waitListEntry) {
		waitListEntries.add(waitListEntry);
		return true;
	}
	
	public int processWaitList(int amount) {
		Iterator waitList = getWaitList();
		
		while (waitList.hasNext() && amount > 0){
			WaitListEntry entry = (WaitListEntry)(waitList.next());
			System.out.println("Stock Remaining: " + amount);  //Why is this in the class and not in the UI?
			System.out.println("Process the following Wait Listed Item?");
			System.out.println(entry.toString());
			
			System.out.println(1 + " to process");
			System.out.println(2 + " to skip");
			String command = getToken("Enter selection");   
		   switch (Integer.valueOf(command)) {
			   case 1:	  	amount = entry.ProcessEntry(amount)
							System.out.println("Processed");        
									break;
			   case 2:      System.out.println("Skipped");        
									break;					
		   }
		}
		
		return amount;
	}
	
	public String toString() {
		return waitListEntries.toString();
	}
  }