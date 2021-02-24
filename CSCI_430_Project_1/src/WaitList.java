/*
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
  
  public class WaitList implements Serializable {
	private List waitListEntries = new LinkedList();
	  
	public WaitList() {
	}
	
	public Iterator getWaitList() {
		return waitListEntries.iterator();
	}
	
	public boolean insertWaitListEntry(WaitListEntry waitListEntry) {
		waitListEntries.add(waitListEntry);
		return true;
	}
	
	public int processWaitList(int amount) {
		Iterator waitList = getWaitList();
		
		//Move to UI eventually
		while (waitList.hasNext() && amount > 0){
			WaitListEntry entry = (WaitListEntry)(waitList.next());
			System.out.println("Stock Remaining: " + amount);  
			System.out.println("Process the following Wait Listed Item?");
			System.out.println(entry.toString());
			
			System.out.println(1 + " to process");
			System.out.println(2 + " to skip");
			
			
			String command = getToken("Enter selection");   
		   switch (Integer.valueOf(command)) {
			   case 1:	  	amount = entry.ProcessEntry(amount);
							System.out.println("Processed");        
									break;
			   case 2:      System.out.println("Skipped");        
									break;					
		   }
		}
		
		return amount;
	}
	
	//Remove getToken once above code has been moved to the UI
	  public String getToken(String prompt) {
		  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    do {
		      try {
		        System.out.println(prompt);
		        String line = reader.readLine();
		        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
		        if (tokenizer.hasMoreTokens()) {
		          return tokenizer.nextToken();
		        }
		      } catch (IOException ioe) {
		        System.exit(0);
		      }
		    } while (true);
		  }
	
	
	
	public String toString(){
		return waitListEntries.toString();
	}
  }