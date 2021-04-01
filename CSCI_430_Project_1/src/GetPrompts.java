import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class GetPrompts {
	
	private int EXIT;
	private int HELP;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public GetPrompts(int EXIT, int HELP) {
		this.EXIT = EXIT;
		this.HELP = HELP;
	}
	
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}
	
	public String getToken(String prompt) {
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
	
	public int getNumber(String prompt) {
	  do {
	    try {
	      String item = getToken(prompt);
	      Integer num = Integer.valueOf(item);
	      return num.intValue();
	    } catch (NumberFormatException nfe) {
	      System.out.println("Please input a number ");
	    }
	  } while (true);
	}
	
	private boolean yesOrNo(String prompt) {
		  String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		  if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
		    return false;
		  }
		  return true;
	}
	
	public double getDouble(String prompt) {
	    do {
	      try {
	        String item = getToken(prompt);
	        Double num = Double.valueOf(item);
	        return num.doubleValue();
	      } catch (NumberFormatException nfe) {
	        System.out.println("Please input a number ");
	      }
	    } while (true);
	  }
}