package PrEis.gui;

/** 
 * Defines Action State. See member docu for semantics. There <b>MUST<b> be an
 * instance for which to reflect changes (e.g. via field in implementing class
 * named <code>curState</code> WLOG) for consumer widgets to reflect via GUI;
 * which will be fetched during their <b>[OnUpdate]</b> routine.
 */
public enum ConfirmState{
  /** Not yet clicked else prior click was cancelled. */       ONINIT,
  /** Has been clicked once; s.t. second click does action. */ ONWARN,
  /** Has been clicked twice; s.t. action was performed. */    ONDONE,
}