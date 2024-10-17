package PrEis.utils;

/** 
 * <code>PrEis</code> Resource (incl. <code>JAR</code> filenames and primitives
 * i.e. <code>String</code>s). */
public enum PrEisRes {
  RES_ROOT("PrEis/assets"),
  SYM_FONT("font_awe_48.vlw"),
  SYM_CMAP("font_awe_char_codes.json"),
  EIS_LOGO("eis_logo.png"),
  TXT_FONT("tit_web_bold_32.vlw"),
  MON_FONT("ibm_plx_mono_32.vlw"),
  
  STUB;
  
  private String fname;
  PrEisRes(){fname=null;}
  PrEisRes(String in_fname){fname = in_fname;}
  public String get(){return fname==null ? this.toString() : fname;}
}