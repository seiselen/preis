package PrEis.utils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import processing.core.PApplet;


/**
 * FOR NOW: SERVING ROLE OF BOTH FormatUtils AND ConversionUtils (a/a)
 * @todo the 'A' part of 'colorRGBAToString' is a misnomer; as neither overload
 * returns the alpha channel if zero?; nor handles calls which just want RGB.
 * Might want to realize it and retain old code for 'colorRGBToString' function.
 * @todo realize `colorRGB` functions alongside existing `colorRGBA`
 */

public class FormatUtils{

  public static String colorRGBAToString(int[] c){
    if(c.length==3){c = new int[]{c[0],c[1],c[2],255};} //> add alpha channel (a/a)
    return StringUtils.wrapParens(StringUtils.concatAsCSSV(StringUtils.padL(primArrToStrArr(c),3)));
  }
  
  public static String colorRGBAToString(int c){
    return StringUtils.wrapParens(StringUtils.concatAsCSSV(StringUtils.padL(primArrToStrArr(colorRGBAFromPColor(c)),3)));
  }

  public static int[] colorRGBAFromPColor(int c){
    return new int[]{(int)(c >> 16 & 0xFF),(int)(c >> 8 & 0xFF),(int)(c & 0xFF), (int)(c >> 24 & 0xFF)};
  }




  /**
   * @implNote This is used for the util (currently within `EiSpriteViewer` app)
   * that extracts sprite offsets from their `.png` headers. Verbatim therefrom
   * and known to work therein - so no immediate need to test. If I do move the
   * sprite offset extraction code to some 'EisDoomUtils' module at some time in
   * the future: do testing then.
   */
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


  public static String[] strArrListToStrArr (ArrayList<String> aList){
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

  public static String[] primArrToStrArr(double[] a){
    String[] r = new String[a.length];
    for(int i=0; i<a.length; i++){r[i]=""+a[i];}
    return r;
  }

  public static String[] primArrToStrArr(boolean[] a){
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

  /** 
   * @todo see if I can procedurally grab class as per {@link #arr1FromObj}.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] arrFromList(Class<T> c, List<T> list) {
    return list.toArray((T[])Array.newInstance(c, list.size()));
  }

}