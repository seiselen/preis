package PrEis.test;

import PrEis.utils.FormatUtils;
import processing.core.PApplet;

public class TestFormatUtils {
  private static final String P = "PASS";
  private static final String F = "FAIL";

  public static void runTests(PApplet p){
    test_colorRGBAToString(p);
    test_colorRGBAFromPColor(p);
  }

  public static void test_colorRGBAToString(PApplet p){
    TestFunc.doEval(FormatUtils.colorRGBAToString(new int[]{12,24,36}),    "( 12,  24,  36, 255)");
    TestFunc.doEval(FormatUtils.colorRGBAToString(new int[]{108,120,144}), "(108, 120, 144, 255)");
    TestFunc.doEval(FormatUtils.colorRGBAToString(new int[]{0,255,0,128}), "(  0, 255,   0, 128)");
    TestFunc.doEval(FormatUtils.colorRGBAToString(p.color(255)),           "(255, 255, 255, 255)");
    TestFunc.doEval(FormatUtils.colorRGBAToString(p.color(224,192,0)),     "(224, 192,   0, 255)");
    TestFunc.doEval(FormatUtils.colorRGBAToString(p.color(224,0,224,160)), "(224,   0, 224, 160)");
  }

  public static void test_colorRGBAFromPColor(PApplet p){
    TestFunc.doEval(FormatUtils.colorRGBAFromPColor(p.color(12,24,36)),      new int[]{ 12,  24,  36, 255});
    TestFunc.doEval(FormatUtils.colorRGBAFromPColor(p.color(108,120,144)),   new int[]{108, 120, 144, 255});
    TestFunc.doEval(FormatUtils.colorRGBAFromPColor(p.color(0,255,0,128)),   new int[]{  0, 255,   0, 128});
    TestFunc.doEval(FormatUtils.colorRGBAFromPColor(p.color(255)),           new int[]{255, 255, 255, 255});
    TestFunc.doEval(FormatUtils.colorRGBAFromPColor(p.color(224,192,0)),     new int[]{224, 192,   0, 255});
    TestFunc.doEval(FormatUtils.colorRGBAFromPColor(p.color(224,0,224,160)), new int[]{224,   0, 224, 160});
  }



  /* 
  public static String   byteArrSubStrToHex (byte[] a, int i, int n)
  public static int      colorFromIntArr (int[] intArr)  
  public static String[] strArrListToArr (ArrayList<String> aList)
  public static String[] primArrToStrArr(int[] a)
  public static String[] primArrToStrArr(float[] a)
  public static <T> T[]  arr1FromObj(T obj)
  public static <T> T[]  arrFromArrList(ArrayList<T> aList)
   */
}
