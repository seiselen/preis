package PrEis.utils;

public enum ExtType {
  DEH(".deh"),
  JSON(".json"),
  MP3(".mp3"),
  OGG(".ogg"),
  PNG(".png"),
  PK3(".pk3"),
  TXT(".txt"),
  WAD(".wad"),
  WAV(".wav"),
  ZIP(".zip")
  ;

  private String val;

  ExtType(String v){
    this.val = v;
  }

  public String val(){
    return this.val;
  }
}