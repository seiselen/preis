package PrEis.test;

import PrEis.utils.ExtType;

/**
 * @implNote This could realize testing for all public/protected enums (unless I
 * have a good reason not to).
 */
public class TestEnums {

  public static void runTests(){ 
    System.out.println("<=[ Testing Enum: 'ExtType' ]==================>");
    test_ExtType();
  }

  public static void test_ExtType(){
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(ExtType.DEH.val().equals(".deh"),true),
      TestFunc.doEval(ExtType.JSON.val().equals(".json"),true),
      TestFunc.doEval(ExtType.MP3.val().equals(".mp3"),true),
      TestFunc.doEval(ExtType.OGG.val().equals(".ogg"),true),
      TestFunc.doEval(ExtType.PK3.val().equals(".pk3"),true),
      TestFunc.doEval(ExtType.PNG.val().equals(".png"),true),
      TestFunc.doEval(ExtType.TXT.val().equals(".txt"),true),
      TestFunc.doEval(ExtType.WAD.val().equals(".wad"),true),
      TestFunc.doEval(ExtType.WAV.val().equals(".wav"),true),
      TestFunc.doEval(ExtType.ZIP.val().equals(".zip"),true)
    });
  }
}
