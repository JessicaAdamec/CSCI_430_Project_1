
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
	public String toString(){
		return waitListEntries.toString();
	}
  }