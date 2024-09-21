package PrEis.test;

public enum TestAssetKey {
  TEXT_FONT,
  GLYPH_FONT,
  GLYPH_CODES,
  TEST_OUT_DIR,
  GUI_BG_IMG_LITE,
  GUI_BG_IMG_DARK,

  //-[ DIAGNOSTIC IMAGES ]-----------------------------------------------------#
  DIAG_IMG_PFIX,
  DIMG_32X32("32x32.png"),
  DIMG_32X64("32x64.png"),
  DIMG_64X32("64x32.png"),
  DIMG_64X64("64x64.png"),
  DIMG_128X64("128x64.png"),
  DIMG_64X128("64x128.png"),
  DIMG_128X128("128x128.png"),


  STUB; //> Exists Only For Semicolon

  /** 
   * Syntax Sugar call of <code>this.toString()</code> upon caller instance.
   * @deprecated
   */
  public String s(){return this.toString();}

  private String sPath;
  TestAssetKey(){sPath=null;}
  TestAssetKey(String in_sPath){sPath = in_sPath;}
  public String get(){return sPath==null ? this.toString() : sPath;}


}