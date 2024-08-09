package PrEis.utils;

public enum ExtType {
  TXT(".txt"),
  PNG(".png"),
  WAD(".wad"),
  PK3(".pk3"),
  DEH(".deh")
  ;

  private String val;

  ExtType(String v){
    this.val = v;
  }

  public String val(){
    return this.val;
  }
}