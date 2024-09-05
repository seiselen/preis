package PrEis.utils;

public class StringUtils {

  /** Padding Direction */
  private enum PadDir {L,R};
  
  public static String charTimesN (char c, int n){
    String ret="";
    for(int i=0; i<n; i++){ret+=c;}
    return ret;
  }

  private static String _pad(String v, int n, PadDir p){
    return v.length()>=n ? v : String.format((p==PadDir.L ? "%" : "%-")+n+"s",""+v);
  }

  private static String[] _pad(String[] vs, int n, PadDir p){
    int len = vs.length;
    String[] ret = new String[len];
    for(int i=0; i<len; i++){ret[i]=_pad(vs[i],n,p);}
    return ret;
  }

  public static String   padL (String v, int n)   {return _pad(v,n,PadDir.L);}
  public static String   padR (String v, int n)   {return _pad(v,n,PadDir.R);}
  public static String[] padL (String[] vs, int n){return _pad(vs,n,PadDir.L);}
  public static String[] padR (String[] vs, int n){return _pad(vs,n,PadDir.R);}


  public static int maxCharLengthOf(String[] arr){
    int curMax = -1;
    int curLen;
    for (String s : arr){curLen=s.length(); if(curLen>curMax){curMax=curLen;}}
    return curMax;
  }


  /** 
   * Returns input string wrapped (i.e. placed between) input char. If char has
   * a horizontally flipped counterpart (e.g. either of the parenthesis chars):
   * the string will be wrapped between both counterparts in their appropriate
   * orientation; and it does not matter which of the two you input.
   * @implNote Currently supported chars for auto-counterpart feature: <code>( )
   * { } < > ' "</code>
   * @examples 
   * <pre>{@code //> Syntax: (input) ⮕ (output)
   * ('(', "expl") ⮕ "(expl)"
   * ('>', "expl") ⮕ "<expl>"
   * ('\'', "expl") ⮕ "'expl'"
   * ('_', "expl") ⮕ "_expl_"
   * }</pre>
   */
  public static String wrapWith(char bfix, String str){
    switch(bfix){
      case '(' : case ')'  : return '('+str+')';
      case '{' : case '}'  : return '{'+str+'}';
      case '<' : case '>'  : return '<'+str+'>';
      case '\'' : case '"' : default: return bfix+str+bfix;
    }
  }

  public static String wrapWith(char pfix, String str, char sfix){
    return ""+pfix+str+sfix;
  }

  /**
   * Returns input string wrapped in parenthesis chars.
   * @implNote <b>Syntax Sugar</b> call of {@link #wrapWith(char, String)}.
   */
  public static String wrapParens(String str){return wrapWith('(', str);}

  public static char lastCharOf(String str){
    return (QueryUtils.nullish(str)) ? '\0' : str.charAt(str.length()-1);
  }

  public static String capFirstChar(String str) {
    if(str==null || str.isEmpty()){return str;}
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
    if(QueryUtils.nullish(val)){return null;}

    if(val.charAt(0)=='_' || lastCharOf(val)=='_'){
      return capFirstChar(val.replaceAll("[_]", ""));
    }
    String[] words = val.split("_");
    for(int i=0; i<words.length; i++){words[i]=capFirstChar(words[i]);}
    return String.join(" ", words);
  }

  public static String concatStrings(String ... strs){
    if(strs.length==0){return null;} //> just in case
    String ret = "";
    for(String s:strs){ret+=s;}
    return ret;
  }


  private enum ConcatOption {
    CSV, SSV, CSSV;
    private static final String SUFX_CSV  = ",";
    private static final String SUFX_SSV  = " ";
    private static final String SUFX_CSSV = ", ";
    /** Returns separator string WRT instance {@link ConcatOption} type. */
    public String sep(){
      switch (this){case CSSV: return SUFX_CSSV; case CSV: return SUFX_CSV; default: return SUFX_SSV;}
    }
  }

  /** <b>(Comma Separated)</b> Returns input strings concatenated with comma chars. */
  public static String concatAsCSV(String ... strs){
    return _concatAsCSV(strs, ConcatOption.CSV);
  }

  /** <b>(Space Separated)</b> Returns input strings concatenated with space chars. */
  public static String concatAsSCV(String ... strs){
    return _concatAsCSV(strs, ConcatOption.SSV);    
  }

  /** <b>(Comma+Space Separated)</b> Returns input strings concatenated with comma and space chars. */
  public static String concatAsCSSV(String ... strs){
    return _concatAsCSV(strs, ConcatOption.CSSV);    
  }



  
  private static String _concatAsCSV(String[] strs, ConcatOption copt){
    if(strs.length==0){return null;} //> just in case
    String ret = "";
    int len = strs.length;
    for(int i=0; i<len; i++){
      ret+=strs[i];
      if(i<len-1){ret+=copt.sep();}
    }
    return ret;
  }



  /** 
   * Prints input string if input object is <code>null</code>. Note that <code>
   * print</code> is called, NOT <code>println</code>; i.e. <b>no newline!</b>.
   */
  public static void printIfNull(Object o, String s){
    if(o==null){System.out.print(s);}
  }

  /** 
   * Returns input string if input object is <code>null</code>; else returns the
   * empty string i.e. <code>""</code>.
   */
  public static String stringIfNull(Object o, String s){
    return (o==null ? "" : s);
  }

}
