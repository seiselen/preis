package PrEis.test;

public enum TestAssetKey {
  TEXT_FONT,
  GLYPH_FONT,
  GLYPH_CODES,
  GUI_BG_IMG,
  TEST_OUT_DIR;

  /** Syntax Sugar call of <code>this.toString()</code> upon caller instance. */
  public String s(){return this.toString();}
}