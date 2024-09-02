package PrEis.utils;

/**
 * @implNote <code>nullish</code> is expected to be realized for specific object
 * types when/as needed; i.e. there is NO generalized (i.e. <code>object</code> 
 * parm version: just do the comparison to <code>null</code> inline...
 */
public class QueryUtils {
  
  /** For String Args: Is it null, empty, or blank? */
  public static boolean nullish(String qStr){
    return qStr==null || qStr.isEmpty();
  }

  public static <T> boolean arrayNullOrEmpty(T[] arr){
    return arr==null || arr.length==0;
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  |>>> TESTING NOTE: The following three `null<…>` functions WERE successfully
  |                  (QAD) unit tested on `08/28/24` via W3Schools Java Editor.
  +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  /** Returns true if <b>ALL</b> of the input objects are <code>null</code>. */
  public static boolean nullAll(Object ... objs){
    for(Object o : objs){if (o!=null){return false;}}
    return true;
  }

  /** Returns true if <b>NONE</b> of the input objects are <code>null</code>. */
  public static boolean nullNone(Object ... objs){
    for(Object o : objs){if (o==null){return false;}}
    return true;
  }

  /** Returns true if <b>AT LEAST ONE</b> input object is <code>null</code>. */
  public static boolean nullSome(Object ... objs){
    for(Object o : objs){if (o==null){return true;}}
    return false;
  }  

  public static boolean strArrContainsStr(String[] arr, String str){
    if(arrayNullOrEmpty(arr)){return false;}
    for(String s : arr){if(str.equals(s)){return true;}}
    return false;
  }

}
