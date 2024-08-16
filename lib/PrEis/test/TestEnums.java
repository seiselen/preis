package PrEis.test;

import PrEis.utils.ExtType;

/**
 * @implNote This could realize testing for all public/protected enums (unless I
 * have a good reason not to).
 */
public class TestEnums {
  private static final String P = "PASS";
  private static final String F = "FAIL";

  public static void runTests(){
    test_ExtType();
  }

  public static void test_ExtType(){
    TestFunc.strArrToConsole(new String[]{
      (ExtType.DEH.val().equals(".deh")?P:F),
      (ExtType.JSON.val().equals(".json")?P:F),
      (ExtType.MP3.val().equals(".mp3")?P:F),
      (ExtType.OGG.val().equals(".ogg")?P:F),
      (ExtType.PK3.val().equals(".pk3")?P:F),
      (ExtType.PNG.val().equals(".png")?P:F),
      (ExtType.TXT.val().equals(".txt")?P:F),
      (ExtType.WAD.val().equals(".wad")?P:F),
      (ExtType.WAV.val().equals(".wav")?P:F),
      (ExtType.ZIP.val().equals(".zip")?P:F),      
    });

  }
}
