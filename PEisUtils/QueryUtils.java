public class QueryUtils {
  
  /** For String Args: Is it null, empty, or blank? */
  public static boolean nullOrEmpty(String qStr){
    return qStr==null || qStr.isEmpty() || qStr.isBlank();
  }

  public static <T> boolean arrayNullOrEmpty(T[] arr){
    return arr==null || arr.length==0;
  }

  public static boolean nullish(String s){return s==null||s.isEmpty();}

  public static boolean nullish(Object s){return s==null;}



  public static char lastCharOf(String str){
    return (QueryUtils.nullOrEmpty(str)) ? '\0' : str.charAt(str.length()-1);
  }

  public static String getLastElement(String[] a){
    return a[a.length-1];
  }







}
