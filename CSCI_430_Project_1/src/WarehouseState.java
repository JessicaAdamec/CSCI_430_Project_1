public abstract class WarehouseState {
  protected static Warehouse context;
  protected WarehouseState() {
    //context = LibContext.instance(); //maybe we can delete this commented part
  }
  public abstract void run();
}
