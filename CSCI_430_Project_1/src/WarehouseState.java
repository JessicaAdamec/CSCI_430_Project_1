import java.awt.event.ActionListener;

public abstract class WarehouseState implements ActionListener {
  protected static Warehouse context;
  protected WarehouseState() {
  }
  public abstract void run();
}
