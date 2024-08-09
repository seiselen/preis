package PrEis.gui;

import PrEis.utils.Cons;



/**
 * Orientation ᴀᴋᴀ 'Anchoring' of text within UIObjects, of semantics:
 * <pre><code>{TxtOri.{V}{H} | V⮕vertical ∧ H⮕horizontal}</code></pre>
 * {@link #TL} {@link #TR} {@link #CC} {@link #TC} {@link #CL} {@link #CR} 
 */
public enum TxtOri {
  /*=[ENUM VALS]============================================================*/
  /** Top-Left */
  TL,
  /** Top-Right */
  TR,
  /** Center-Center */
  CC,
  /** Top-Center */
  TC,
  /** Center-Left */
  CL,
  /** Center-Right */
  CR;

  /*=[UTIL CONSTS]==========================================================*/
  private final static TxtOri TxtOri_DEFAULT = TxtOri.CC;
  private final static String TxtOri_NAME    = TxtOri_DEFAULT.getClass().getSimpleName();

  public static TxtOri withString(String inStr){
    try {
      return TxtOri.valueOf(inStr);
    } catch (IllegalArgumentException e) {
      Cons.warn_invalidEnumStrID(TxtOri_NAME, inStr, TxtOri_DEFAULT.toString());
      return TxtOri_DEFAULT;
    }
  }
}