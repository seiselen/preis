package PrEis.gui;

public interface ISelectAction extends IGUICallBack {

  /**
   * Used with {@link UIDropdown} widget as an event handler similar to the <code>
   * HTML/JS</code> <code>onchange</code> event & handler. Specifically: this is
   * injected thereinto s.t. whenever a user selects of its {@link UIDropdownItem}
   * options: it calls this with <code>String</code> value associated therewith.
   * @param selOpt option selected by user via dropdown[element].
   */
  public void OnSelection(String selOpt);
  
}