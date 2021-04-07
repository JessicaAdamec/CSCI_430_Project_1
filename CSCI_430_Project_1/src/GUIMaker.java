import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUIMaker {
	public GUIMaker() {
		
	}
	
	public JButton makeButton(String text, int actionCommand, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.setActionCommand(String.valueOf(actionCommand));
		button.addActionListener(actionListener);
		
		return button;
	}
	
	public JButton makeButton(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.addActionListener(actionListener);
		
		return button;
	}
}