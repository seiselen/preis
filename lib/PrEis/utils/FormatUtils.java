package PrEis.utils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import processing.core.PApplet;

//> FOR NOW: SERVING ROLE OF BOTH FormatUtils AND ConversionUtils (a/a)
public class FormatUtils{


  public static String pixAndColToString(int pRow, int pCol, int rawColor){
    return "Pixel: ("+pRow+","+pCol+"), Color: "+colorRGBAToString(colorRGBAFromPColor(rawColor));
  }
  
  public static String colorRGBAToString(int[] c){
    return StringUtils.wrapWithParenChars(StringUtils.concatAsSCSV(StringUtils.padL(c,3)));
  }
  
  public static String colorRGBAToString(int c){
    return StringUtils.wrapWithParenChars(StringUtils.concatAsSCSV(StringUtils.padL(colorRGBAFromPColor(c),3)));
  }

  public static int[] colorRGBAFromPColor(int c){
    return new int[]{(int)(c >> 16 & 0xFF),(int)(c >> 8 & 0xFF),(int)(c & 0xFF), (int)(c >> 24 & 0xFF)};
  }

  public static String strValElseNone(String s){
    return QueryUtils.nullish(s) ? "NONE" : s;
  }

  public static String byteArrSubStrToHex (byte[] a, int i, int n){
    String ret = "";
    for(int s=i; s<i+n; s++){ret+= PApplet.hex(a[s]);}
    return ret;
  }

  public static int colorFromIntArr (int[] intArr){
    switch(intArr.length){
      case 2: return _colFromInt2(intArr);
      case 3: return _colFromInt3(intArr);
      case 4: return _colFromInt4(intArr);
    }
    Cons.err_act(Err.SWITCH_DROP_OUT, ""+intArr.length, Act.RETURN_DEF_VALUE, "color(255,0,255)");
    return _colFromInts(255,0,255);
  }
  
  private static int _colFromInt2 (int[] intArr){
    String alp=PApplet.hex(intArr[1],2);
    String gry=PApplet.hex(intArr[0],2);
    return PApplet.unhex(alp+gry+gry+gry);
  }
  
  private static int _colFromInt3 (int[] intArr){
    String hexStr="FF";
    for(int i : intArr){hexStr+=PApplet.hex(i,2);}
    return PApplet.unhex(hexStr);
  }
  
  private static int _colFromInt4 (int[] intArr){
    String hexStr=PApplet.hex(intArr[3],2);
    for(int i=0; i<3; i++){hexStr+=PApplet.hex(intArr[i],2);}
    return PApplet.unhex(hexStr);
  }

  private static int _colFromInts (int ... intArr){
    String hexStr=PApplet.hex(intArr[3],2);
    for(int i=0; i<3; i++){hexStr+=PApplet.hex(intArr[i],2);}
    return PApplet.unhex(hexStr);
  }

  public static String[] strArrListToArr (ArrayList<String> aList){
    return aList.toArray(new String[0]);
  }

  public static String[] primArrToStrArr(int[] a){
    String[] r = new String[a.length];
    for(int i=0; i<a.length; i++){r[i]=""+a[i];}
    return r;
  }

  public static String[] primArrToStrArr(float[] a){
    String[] r = new String[a.length];
    for(int i=0; i<a.length; i++){r[i]=""+a[i];}
    return r;
  }


  @SuppressWarnings("unchecked")
  public static <T> T[] arr1FromObj(T obj){
    final T[] ret = (T[]) Array.newInstance(obj.getClass(), 1);
    ret[0] = obj;
    return ret;
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] arrFromArrList(ArrayList<T> aList){
    final T[] ret = (T[]) Array.newInstance(aList.getClass(), 1);
    for (int i=0; i<aList.size(); i++) {ret[i] = aList.get(i);}
    return ret;
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] arrFromList(List<T> list){
    final T[] ret = (T[]) Array.newInstance(list.getClass(), 1);
    for (int i=0; i<list.size(); i++) {ret[i] = list.get(i);}
    return ret;
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  # KEEPING THESE IN CASE THE ABOVE GENERIC FUNCTIONS DON'T WORK
  LoadConfig[] arr1FromObj(LoadConfig c){return new LoadConfig[]{c};}
  String[] arr1FromObj(String s){return new String[]{s};}
  LoadConfig[] arrFromArrList(ArrayList<LoadConfig> aList){return aList.toArray(new LoadConfig[0]);}
  +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

}