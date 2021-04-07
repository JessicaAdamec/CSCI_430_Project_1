//Class by Jessica Adamec
//Created 3/20/2021 for CSCI 430

import java.util.*;

import javax.swing.*;

import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class LoginState extends WarehouseState implements ActionListener{
  private static final int CLIENT_LOGIN = 0;
  private static final int CLERK_LOGIN = 1;
  private static final int MANAGER_LOGIN = 2;
  private static final int EXIT = 3;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
  private Warehouse context;
  private static LoginState instance;
  private JFrame frame;
  private AbstractButton clientButton, clerkButton, managerButton, exitButton;
  
  private GetPrompts getPrompt;
  private GUIMaker guiMaker = new GUIMaker();
  
  private LoginState() {
      super();
  }

  public static LoginState instance() {
    if (instance == null) {
      instance = new LoginState();
    }
    return instance;
  }

  
  private void client() {
    String clientID = getPrompt.getToken("Please input the client id: ");
    if (Warehouse.instance().validateClient(clientID) != null){
      (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
      (WarehouseContext.instance()).setClient(clientID);      
      (WarehouseContext.instance()).changeState(0);
    }
    else 
      JOptionPane.showMessageDialog(frame, "Invalid client id.");
  }

  private void clerk(){
	  (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);  
	  (WarehouseContext.instance()).changeState(1);
  }

  private void manager(){
	  (WarehouseContext.instance()).setLogin(WarehouseContext.IsManager);  
	  (WarehouseContext.instance()).changeState(2);
  } 

  public void run() {
	  GUIprocess();
  }
  
  public void GUIprocess() {
	  frame = WarehouseContext.instance().getFrame();
	  getPrompt = new GetPrompts(frame);
	  frame.getContentPane().removeAll();
	  frame.getContentPane().setLayout(new FlowLayout());
	      clientButton = guiMaker.makeButton("Client", this);
	      clerkButton = guiMaker.makeButton("Clerk", this);
	      managerButton = guiMaker.makeButton("Manager", this);
	      exitButton = guiMaker.makeButton("Exit", this);      
	  frame.getContentPane().add(this.clientButton);
	  frame.getContentPane().add(this.clerkButton);
	  frame.getContentPane().add(this.managerButton);
	  frame.getContentPane().add(this.exitButton);
	  frame.setVisible(true);
	  frame.paint(frame.getGraphics()); 
	  //frame.repaint();
	  frame.toFront();
	  frame.requestFocus();
  }
  
	 public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(this.clientButton)) 
			this.client();
		else if (event.getSource().equals(this.clerkButton)) 
			this.clerk();
		else if (event.getSource().equals(this.managerButton)) 
			this.manager();
		else if (event.getSource().equals(this.exitButton))
			WarehouseContext.instance().changeState(3);
	 } 
}
