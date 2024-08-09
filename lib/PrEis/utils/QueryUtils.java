package PrEis.utils;
public class QueryUtils {
  
  /** For String Args: Is it null, empty, or blank? */
  public static boolean nullOrEmpty(String qStr){
    return qStr==null || qStr.isEmpty() || qStr.isBlank();
  }

  public static <T> boolean arrayNullOrEmpty(T[] arr){
    return arr==null || arr.length==0;
  }

  public static boolean nullish(String s){
    return s==null||s.isEmpty();
  }

  public static boolean nullish(Object s){
    return s==null;
  }

}
