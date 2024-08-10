package PrEis.utils;

public class StringUtils {

  private enum PadDir {L,R};
  
  public static String charTimesN (char c, int n){
    String ret="";
    for(int i=0; i<n; i++){ret+=c;}
    return ret;
  }

  
  private static String _pad(String v, int n, PadDir p){
    return String.format((p==PadDir.L ? "%" : "%-")+n+"s",""+v);
  }

  private static String[] _pad(String[] vs, int n, PadDir p){
    int len = vs.length;
    String[] ret = new String[len];
    for(int i=0; i<len; i++){ret[i]=_pad(vs[1],n,p);}
    return ret;
  }

  private static String[] _pad (int[] vs, int n, PadDir p){
    return _pad(FormatUtils.primArrToStrArr(vs),n,p);
  }

  public static String   padL (int v, int n)      {return _pad(""+v,n,PadDir.L);}
  public static String   padR (int v, int n)      {return _pad(""+v,n,PadDir.R);}
  public static String   padL (String v, int n)   {return _pad(v,n,PadDir.L);}
  public static String   padR (String v, int n)   {return _pad(v,n,PadDir.R);}
  public static String[] padL (String[] vs, int n){return _pad(vs,n,PadDir.L);}
  public static String[] padR (String[] vs, int n){return _pad(vs,n,PadDir.R);}
  public static String[] padL (int[] vs, int n)   {return _pad(vs,n,PadDir.L);}
  public static String[] padR (int[] vs, int n)   {return _pad(vs,n,PadDir.R);}



  public static String wrapStringWith (char bfix, String str){
    return ""+bfix+str+bfix;
  }

  public static String wrapStringWith (char pfix, String str, char sfix){
    return ""+pfix+str+sfix;
  }

  public static String wrapWithParenChars(String str){
    return '('+str+')';
  }

  public static char lastCharOf(String str){
    return (QueryUtils.nullOrEmpty(str)) ? '\0' : str.charAt(str.length()-1);
  }

  public static String capFirstChar(String str) {
    if(str== null || str.isEmpty()){return str;}
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
  
  /** 
   * TLDR: Turns "batman_bin_suparman" into "Batman Bin Suparman". Specifically:
   * returns Space-Separated First-Char Capitalized Words of input string; i.e.
   * per the following algorithm...
   * <ol>
   * <li> Split into string array via delimiter of `_` char
   * <li> first char of each array member is capitalized
   * <li> array members joined with space (i.e. ` ` chars)
   * </ol>
   */
  public static String valToSSVCapdWords(String val){
    String[] words = val.split("_");
    for(int i=0; i<words.length; i++){words[i]=capFirstChar(words[i]);}
    return String.join(" ", words);
  }

  public static String concatStrings(String ... strs){
    String ret = "";
    for(String s:strs){ret+=s;}
    return ret;
  }

  /** Returns input strings concatenated with comma chars. */
  public static String concatAsCSV(String ... strs){
    return _concatAsCSV(strs,false);
  }

  /** Returns input strings concatenated with comma and space chars. */
  public static String concatAsSCSV(String ... strs){
    return _concatAsCSV(strs,true);    
  }
  
  private static String _concatAsCSV(String[] strs, boolean inclSpaces){
    String ret = "";
    int len = strs.length;
    for(int i=0; i<len; i++){
      ret+=strs[i];
      if(i<len-1){ret+=(inclSpaces?", ":",");}
    }
    return ret;    
  }

}
