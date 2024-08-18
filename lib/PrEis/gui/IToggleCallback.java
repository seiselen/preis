package PrEis.gui;

/** 
 * Used With ToggleButtons. Contains getter call of target boolean state (via
 * which it renders accordingly), as well as action call to toggle such state.
*/
public interface IToggleCallback {
  boolean getState();
  void toggleState();
}