public abstract class WarehouseState {
  protected static Warehouse context;
  protected WarehouseState() {
  }
  public abstract void run();
}
