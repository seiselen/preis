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
    PrEisTestFunc.testResultsToConsole(new boolean[]{
      PrEisTestFunc.doEval(ExtType.DEH.val().equals(".deh"),true),
      PrEisTestFunc.doEval(ExtType.JSON.val().equals(".json"),true),
      PrEisTestFunc.doEval(ExtType.MP3.val().equals(".mp3"),true),
      PrEisTestFunc.doEval(ExtType.OGG.val().equals(".ogg"),true),
      PrEisTestFunc.doEval(ExtType.PK3.val().equals(".pk3"),true),
      PrEisTestFunc.doEval(ExtType.PNG.val().equals(".png"),true),
      PrEisTestFunc.doEval(ExtType.TXT.val().equals(".txt"),true),
      PrEisTestFunc.doEval(ExtType.WAD.val().equals(".wad"),true),
      PrEisTestFunc.doEval(ExtType.WAV.val().equals(".wav"),true),
      PrEisTestFunc.doEval(ExtType.ZIP.val().equals(".zip"),true)
    });
  }
}
