package PrEis.gui;

import PrEis.utils.Cons;



/**
 * Positional Orientation ᴀᴋᴀ 'Anchoring'. Domain as follows:
 * <ul>
 * <li> {@link #TL} ▪ <b>TOP LEFT</b>
 * <li> {@link #TR} ▪ <b>TOP RIGHT</b>
 * <li> {@link #BL} ▪ <b>BOTTOM LEFT</b>
 * <li> {@link #BR} ▪ <b>BOTTOM RIGHT</b>
 * <li> {@link #CTR} ▪ <b>CENTER</b>
 * <li> {@link #TOP} ▪ <b>TOP</b>
 * <li> {@link #BOT} ▪ <b>BOTTOM</b>
 * <li> {@link #LFT} ▪ <b>LEFT</b>
 * <li> {@link #RGT} ▪ <b>RIGHT</b>
 * </ul>
 */
public enum PosOri {
  /*=[ENUM VALS]============================================================*/
  /**     Top Left */ TL,
  /**    Top Right */ TR,
  /**  Bottom Left */ BL,
  /** Bottom Right */ BR,
  /**       Center */ CTR,
  /**          Top */ TOP,
  /**       Bottom */ BOT, 
  /**         Left */ LFT,
  /**        Right */ RGT;

  /*=[UTIL CONSTS]==========================================================*/
  private final static PosOri TxtOri_DEFAULT = PosOri.CTR;
  private final static String TxtOri_NAME    = TxtOri_DEFAULT.getClass().getSimpleName();

  public static PosOri withString(String inStr){
    try {
      return PosOri.valueOf(inStr);
    } catch (IllegalArgumentException e) {
      Cons.warn_invalidEnumStrID(TxtOri_NAME, inStr, TxtOri_DEFAULT.toString());
      return TxtOri_DEFAULT;
    }
  }
}