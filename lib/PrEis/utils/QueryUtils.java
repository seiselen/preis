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

}
