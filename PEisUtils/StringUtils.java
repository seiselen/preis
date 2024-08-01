import java.util.ArrayList;
import processing.core.PApplet;

public class StringUtils {
  
  public static String charTimesN (char c, int n){
    String ret="";
    for(int i=0; i<n; i++){ret+=c;}
    return ret;
  }

  public static String padStringR (String s, int n){
    return String.format("%-"+n+"s",s);
  }

  public static String padStringL (String s, int n){
    return String.format("%"+n+"s",s);
  }

  public static String wrapStringWith (char bfix, String str){
    return ""+bfix+str+bfix;
  }

  public static String wrapStringWith (char pfix, String str, char sfix){
    return ""+pfix+str+sfix;
  }

  public static String byteArrSubStrToHex (byte[] a, int i, int n){
    String ret = "";
    for(int s=i; s<i+n; s++){ret+= PApplet.hex(a[s]);}
    return ret;
  }

  public static String[] strArrListToArr (ArrayList<String> aList){
    return aList.toArray(new String[0]);
  }



  public static String capFirstChar(String str) {
    if(str== null || str.isEmpty()){return str;}
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
  
  public static String valToSpaceSepCapdWords(String val){
    String[] words = val.split("_");
    for(int i=0; i<words.length; i++){words[i]=capFirstChar(words[i]);}
    return String.join(" ", words);
  }

  public static String concatStrings(String ... strs){
    String ret = "";
    for(String s:strs){ret+=s;}
    return ret;
  }

}
