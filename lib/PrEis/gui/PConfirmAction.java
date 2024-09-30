package PrEis.gui;

public abstract class PConfirmAction implements IConfirmAction {
  private ConfirmState curState;

  /**
   * Increments through {@link ConfirmState}s; s.t. child class instances will
   * first call this method (i.e. as <b>super</b>) within their local methods,
   * and then execute further given the resultant incremented state.
   */
  public void action() {
    switch (curState) {
      case ONINIT: curState = ConfirmState.ONWARN; return;
      case ONWARN: curState = ConfirmState.ONDONE; return;
      case ONDONE: curState = ConfirmState.ONINIT; return;      
      default: return;
    }
  }

  public void cancel(){curState = ConfirmState.ONINIT;}

  public ConfirmState getState(){return curState;}

}