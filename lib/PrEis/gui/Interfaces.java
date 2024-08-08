package PrEis.gui;

/** 
 * Interfaces for UIObject Handlers.
 * @TODO Put this within `UIObject` or its manager should I realize one?
 */
public class Interfaces {

  /** 
   * Used With ClickButtons. Contains action call of which to make when clicked.
  */
  public interface ActionCallback {
    void action();
  }

  /**
   * Same as {@link ActionCallback} except realizes getter for a label. Intended
   * for Dropdown Items but can also be used for ClickButtons.
   */
  public interface LabelledActionCallback extends ActionCallback {
    public String getLabel();
  }

  /** 
   * Used With ToggleButtons. Contains getter call of target boolean state (via
   * which it renders accordingly), as well as action call to toggle such state.
  */
  public interface ToggleCallback {
    boolean getState();
    void toggleState();
  }

  /** 
   * Callback Used With Labels. Contains poller call of target string to render.
   */
  public interface UpdateCallback {
    String getTxt();
  }
    
}