package PrEis.gui;


public interface IConfirmAction {
  /** 
   * Called for initial click and confirmation click events. should set state to
   * {@link ConfirmState#ONWARN}, then {@link ConfirmState#ONDONE}
   */
  public void action();

  /** 
   * Called to cancel event sequence back to default; i.e. should reset state to
   * {@link ConfirmState#ONINIT}.
   */
  public void cancel();

  /** Gets current state. */
  public ConfirmState getState();
  
}
