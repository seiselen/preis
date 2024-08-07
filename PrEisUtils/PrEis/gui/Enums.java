package PrEis.gui;

import PrEis.utils.Cons;

/** Enums used within the UIObjects space. Might move these to `UIObjects` class
 *  as done with now-named `UIObject.Type`; but keeping here for now to KISS.
 */
public class Enums {

  private final static TxtOri TxtOri_DEFAULT = TxtOri.CC;
  private final static String TxtOri_NAME    = TxtOri_DEFAULT.getClass().getSimpleName();

  /**
   * Orientation ᴀᴋᴀ 'Anchoring' of text within UIObjects, of semantics:
   * <pre><code>{TxtOri.{V}{H} | V⮕vertical ∧ H⮕horizontal}</code></pre>
   * {@link #TL} {@link #TR} {@link #CC} {@link #TC} {@link #CL} {@link #CR} 
   */
  public enum TxtOri {
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

    public static TxtOri withString(String inStr){
      try {
        return TxtOri.valueOf(inStr);
      } catch (IllegalArgumentException e) {
        Cons.warn_invalidEnumStrID(TxtOri_NAME, inStr, TxtOri_DEFAULT.toString());
        return TxtOri_DEFAULT;
      }
    }
  };

  /**
   * Enumeration of Label Style Types. {@link #TP} {@link #OP}
   */  
  public enum LabelType    {
    /** Transparent BG */
    TP,
    /** Opaque BG */
    OP
  };

  /**
   * Enumeration of Font Types. {@link #TEXT} {@link #GLYPH}
   * @TODO SHOULD rename these to {TEXT, GLYPH} for generalization purposes.
   */
  public enum AppFont {
    /** Stantard = Titillium-Web */    
    TEXT,
    /** Standard = FontAwesome */
    GLYPH
  };
}