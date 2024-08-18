package PrEis.gui;

/**
 * Same as {@link IActionCallback} except realizes getter for a label. Intended
 * for Dropdown Items but can also be used for ClickButtons.
 */
public interface ILabelledActionCallback extends IActionCallback {
  public String getLabel();
}