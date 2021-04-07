import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GetPrompts {
	
	private int EXIT;
	private int HELP;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static JFrame frame;
	
	public GetPrompts(JFrame WareFrame) {
		this.frame = WareFrame;
	}
	
	public String getToken(String prompt) {
		do {
          System.out.println(prompt);
          String line = JOptionPane.showInputDialog(frame, prompt);
          StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
	      if (tokenizer.hasMoreTokens()) {
	        return tokenizer.nextToken();
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
	    	JOptionPane.showMessageDialog(frame,"Please input a number ");
	    }
	  } while (true);
	}
	
	public boolean yesOrNo(String prompt) {
		if (JOptionPane.showConfirmDialog(null, prompt, "Yes or No?",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    return true;
	  } else {
		    return false;
	  }
	}
	
	public double getDouble(String prompt) {
	    do {
	      try {
	        String item = getToken(prompt);
	        Double num = Double.valueOf(item);
	        return num.doubleValue();
	      } catch (NumberFormatException nfe) {
	    	  JOptionPane.showMessageDialog(frame,"Please input a number ");
	      }
	    } while (true);
	  }
}