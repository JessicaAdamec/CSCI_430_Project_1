
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JFrame;

public class WarehouseContext {
	
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  
  //Project 2 Code
  private static WarehouseContext context;
  private int currentState;
  private int currentUser;
  private WarehouseState[] states;
  private int[][] nextState;
  public static final int IsClient = 0;
  public static final int IsClerk = 1;
  public static final int IsManager = 2;
  private String clientID;
  private static JFrame WareFrame; 
  
  private GetPrompts getPrompt = new GetPrompts(WareFrame);
  
  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println(" The Warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new warehouse" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }  
  private void terminate()
  {
   if (getPrompt.yesOrNo("Save data?")) {
      if (warehouse.save()) {
         System.out.println(" The Warehouse has been successfully saved in the file WarehouseData \n" );
       } else {
         System.out.println(" There has been an error in saving \n" );
       }
     }
   System.out.println(" Goodbye \n "); System.exit(0);
  }
  
  public static void main(String[] s) {
    WarehouseContext.instance().process();
  }

  //Project 2 Code
  public void setLogin(int code) {
	currentUser = code;
  }
  public void setClient(String cID) {
	clientID = cID;	
  }
  public int getLogin() {
	return currentUser;
  }
  public String getClient() { 
	return clientID;
  }

  public void changeState(int transition) {
    //System.out.println("current state " + currentState + " \n \n ");
    currentState = nextState[currentState][transition];
    if (currentState == -2) 
      {System.out.println("Error has occurred"); terminate();}
    if (currentState == -1) 
      terminate();
    //System.out.println("current state " + currentState + " \n \n ");
    states[currentState].run();
  }
  
  private WarehouseContext() { //constructor
    if (getPrompt.yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
    // set up the FSM and transition table;
    states = new WarehouseState[6];  
    states[0] = ClientState.instance();
    states[1] = ClerkState.instance();
    states[2] = ManagerState.instance();
    states[3] = LoginState.instance();
    states[4] = ShoppingCartState.instance();
    states[5] = QueryClientState.instance();
    nextState = new int[6][6];
    nextState[0][0] = 3;nextState[0][1] = 1;nextState[0][2] = -2;nextState[0][3] = 3;nextState[0][4] = 4;nextState[0][5] = -1;
    nextState[1][0] = 0;nextState[1][1] = 0;nextState[1][2] = 2;nextState[1][3] = 3;nextState[1][4] = -1;nextState[1][5] = 5;
    nextState[2][0] = 0;nextState[2][1] = 1;nextState[2][2] = 3;nextState[2][3] = 3;nextState[2][4] = -1;nextState[2][5] = -1;
    nextState[3][0] = 0;nextState[3][1] = 1;nextState[3][2] = 2;nextState[3][3] = -1;nextState[3][4] = -1;nextState[3][5] = -1;
    nextState[4][0] = 0;nextState[4][1] = -1;nextState[4][2] = -1;nextState[4][3] = -1;nextState[4][4] = -1;nextState[4][5] = -1;
    nextState[5][0] = -1;nextState[5][1] = 1;nextState[5][2] = -1;nextState[5][3] = -1;nextState[5][4] = -1;nextState[5][5] = -1;
    currentState = 3;
    
    WareFrame = new JFrame("Warehouse GUI");
	WareFrame.addWindowListener(new WindowAdapter()
       {public void windowClosing(WindowEvent e){System.exit(0);}});
    WareFrame.setSize(400,400);
    WareFrame.setLocation(400, 400);
  }
   
  public static WarehouseContext instance() {
	if (context == null) {
	   context = new WarehouseContext();
	}
	return context;
  }
  
  public void process(){
	states[currentState].run();
  }
  
  public JFrame getFrame()
  { return WareFrame;}

}