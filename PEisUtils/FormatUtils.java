import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//> FOR NOW: SERVING ROLE OF BOTH FormatUtils AND ConversionUtils (a/a)
public class FormatUtils{

  //> @TODO CAN THIS BE REPLACED WITH PROCESSING'S `nf/nfc/etc.`?
  public static String colStr(int inVal){
    return ((inVal<10)?"  ":(inVal<100)?" ":"")+inVal;
  }

  //> @TODO CAN THIS BE REPLACED WITH PROCESSING'S `nf/nfc/etc.`?
  public static String digit4Str(int inVal){
    boolean isNeg=false;
    if(inVal<0){isNeg=true;inVal*=-1;}
    return(isNeg?"-":"")+((inVal<10)?"000":(inVal<100)?"00":(inVal<1000)?"0":"")+inVal;
  }

  public static String pixelAndItsColorToString(int pRow, int pCol, int rawColor){
    return "Pixel: ("+pRow+","+pCol+"), Color: "+colorRGBAToString(colorRGBAFromPColor(rawColor));
  }
  
  public static String colorRGBAToString(int[] c){
    return "("+colStr(c[0])+","+colStr(c[1])+","+colStr(+c[2])+","+colStr(c[3])+")";
  }
  
  public static int[] colorRGBAFromPColor(int c){
    return new int[]{(int)(c >> 16 & 0xFF),(int)(c >> 8 & 0xFF),(int)(c & 0xFF), (int)(c >> 24 & 0xFF)};
  }

  public static String appendExtIfNeeded(String s, ExtType e){
    return s.endsWith(e.val()) ? s : s.concat(e.val());
  }


  public static String strValElseNone(String s){return QueryUtils.nullish(s) ? "NONE" : s;}


  public static String pathToLinux(String winPath){
    return winPath.toString().replace("\\","/");
  }

  public static String[] pathsToLinux(String[] winPaths){
    List<String> pths = Arrays.asList(winPaths);
    pths.forEach((p)->p=pathToLinux(p));
    return arrFromList(pths);
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  |> 
  +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

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

  //> KEEPING THESE IN CASE THE ABOVE GENERIC FUNCTIONS DON'T WORK
  // LoadConfig[] arr1FromObj(LoadConfig c){return new LoadConfig[]{c};}
  // String[] arr1FromObj(String s){return new String[]{s};}
  // LoadConfig[] arrFromArrList(ArrayList<LoadConfig> aList){return aList.toArray(new LoadConfig[0]);}

}