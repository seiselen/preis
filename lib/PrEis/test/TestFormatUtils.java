package PrEis.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import PrEis.utils.ExtType;
import PrEis.utils.FormatUtils;
import processing.core.PApplet;
import processing.core.PVector;

/** Used for array-from-collection function tests. */
class TestObj {
  private String name;
  public TestObj(String n){name=n;}
  public String toString(){return "name="+name;}
}

public class TestFormatUtils {

  public static void runTests(PApplet p){
    String [] strArr = new String []{"idkfa", "idbehold", "iddqd", "idclip", "iddt", "idclev", "idmus", "idmypos"};
    int    [] intArr = new int    []{0, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987};
    float  [] fltArr = new float  []{1.23f, 3.56f, 7.89f, 0.12f, 3.45f, 6.78f, 9.01f, 2.34f};
    PVector[] vecArr = new PVector[]{new PVector(631,516,76), new PVector(212,718,78), new PVector(520,480,65)};
    TestObj[] tstArr = new TestObj[]{new TestObj("mixom"), new TestObj("moxim"), new TestObj("moxy")};

    ArrayList<String>  strArrList = new ArrayList<String>(Arrays.asList(strArr));
    ArrayList<PVector> vecArrList = new ArrayList<PVector>(Arrays.asList(vecArr));
    ArrayList<TestObj> tstArrList = new ArrayList<TestObj>(Arrays.asList(tstArr));

    test_colorRGBAToString(p);
    test_colorRGBAFromPColor(p);
    test_colorFromIntArr(p);
    test_toStrArrFuncs(strArr, intArr, fltArr);
    test_arr1FromObj();
    test_arrFromArrList(strArr, strArrList, vecArr, vecArrList, tstArr, tstArrList);
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

  public static void test_colorFromIntArr(PApplet p){
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{0,   255}), p.color(  0,  255));    
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{255,   0}), p.color(255,    0));    
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{120, 240}), p.color(120,  240));

    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{  0,   0,   0}), p.color(  0,   0,   0));
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{255, 255, 255}), p.color(255, 255, 255));    
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{ 12,  24,  36}), p.color( 12,  24,  36));
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{ 13,  71,  31}), p.color( 13,  71,  31));

    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{255,   0,   0,  80}), p.color(255,   0,   0,  80));
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{  0, 255,   0, 160}), p.color(  0, 255,   0, 160));
    TestFunc.doEval(FormatUtils.colorFromIntArr(new int[]{  0,   0, 255, 240}), p.color(  0,   0, 255, 240));
  }

  public static void test_toStrArrFuncs(String[] strArr, int[] intArr, float[] fltArr){
    
    ArrayList<String> strArrList = new ArrayList<String>();
    Collections.addAll(strArrList, strArr);
    TestFunc.doEval(FormatUtils.strArrListToStrArr(strArrList), strArr);
    
    strArr = new String[intArr.length];
    for(int i=0; i<intArr.length; i++){strArr[i] = ""+intArr[i];}
    TestFunc.doEval(FormatUtils.primArrToStrArr(intArr), strArr);
    
    strArr = new String[fltArr.length];    
    for(int i=0; i<fltArr.length; i++){strArr[i] = ""+fltArr[i];}
    TestFunc.doEval(FormatUtils.primArrToStrArr(fltArr), strArr);
  }

  public static void test_arr1FromObj(){
    Integer   IntTestVal = Integer.valueOf(5882300);
    Integer[] IntTestArr = FormatUtils.arr1FromObj(IntTestVal);
    TestFunc.doEval(""+IntTestVal, ""+IntTestArr[0]);

    Float   FloatTestVal = Float.valueOf(631.516f);
    Float[] FloatTestArr = FormatUtils.arr1FromObj(FloatTestVal);
    TestFunc.doEval(""+FloatTestVal, ""+FloatTestArr[0]);

    String   strTestVal = "Batman Bin Suparman";
    String[] strTestArr = FormatUtils.arr1FromObj(strTestVal);
    TestFunc.doEval(strTestVal, strTestArr[0]);

    PVector   vecTestVal = new PVector(212,718,516);
    PVector[] vecTestArr = FormatUtils.arr1FromObj(vecTestVal);
    TestFunc.doEval(vecTestVal.toString(), vecTestArr[0].toString());

    ExtType   enumTestVal = ExtType.WAD;
    ExtType[] enimTestArr = FormatUtils.arr1FromObj(enumTestVal);
    TestFunc.doEval(enumTestVal.val(), enimTestArr[0].val());
  }

  //arrFromArrList(ArrayList<T> aList)
  public static void test_arrFromArrList(
    String[]  strArr, ArrayList<String>  strList,
    PVector[] vecArr, ArrayList<PVector> vecList,
    TestObj[] tstArr, ArrayList<TestObj> tstList
  ){
    TestFunc.doEval(FormatUtils.arrFromList(String.class,  strList), strArr);
    TestFunc.doEval(FormatUtils.arrFromList(PVector.class, vecList), vecArr);
    TestFunc.doEval(FormatUtils.arrFromList(TestObj.class, tstList), tstArr);
  }


}