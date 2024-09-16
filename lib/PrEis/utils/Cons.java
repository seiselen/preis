package PrEis.utils;


public class Cons {
  private static String errPfix = "ERROR: ";
  private static String actPfix = "ACTION: ";
  private static String wrnPfix = "WARNING: ";

  public enum Err {
    NULL_XOR_INVALID {public String toString(){return "Nullish Xor Invalid Input";}},
    NULL_INPUT       {public String toString(){return "Nullish Input";}},
    NULL_VALUE       {public String toString(){return "Nullish Value";}},    
    MISSING_SUFFIX   {public String toString(){return "Input Lacks Required Suffix";}},
    SWITCH_DROP_OUT  {public String toString(){return "Unexpected Drop From Switch Statement";}},
    ARR_LEN_MISMATCH {public String toString(){return "Length of two or more arrays mismatch";}},
  }

  public enum Act {
    RETURN_NO_ACTION {public String toString(){return "Returning With No Further Action(s)";}},
    RETURN_ZERO_VEC  {public String toString(){return "Returning Zeroed PVector";}},
    RETURN_DEF_VALUE {public String toString(){return "Returning Default Value";}},
    ADDING_REQ_SUFX  {public String toString(){return "Adding Required Suffix";}},
    RETURN_NULL      {public String toString(){return "Returning `null`";}},    
  }
  
  public static void log(String l){System.out.println(l);}
  public static void log(String ... ls){for(String l: ls){log(l);}}
  
  public static void err(String e){System.err.println(errPfix+e+"!");}
  public static void err(String ... es){for(String e: es){err(e);}}

  public static void warn(String w){System.err.println(errPfix+w+"!");}
  public static void warn(String ... ws){for(String w: ws){err(w);}}

  public static void err(Err e){System.err.println(errPfix+e+"!");}
  public static void err(Err e, String s){System.err.println(errPfix+e+" ["+s+"]!");}
  public static void err_unx(Err e){System.err.println(errPfix+"Unexpected "+e+"!");}

  public static void act(Act a){System.err.println(actPfix+a+".");}
  public static void act(Act a, String s){System.err.println(actPfix+a+" ["+s+"].");}

  public static void err_act(Err e, Act a){Cons.err(e); Cons.act(a);}
  public static void err_act(Err e, Act a, String s){Cons.err(e,s); Cons.act(a,s);}

  public static void err_act(Err e, String es, Act a, String as){Cons.err(e,es); Cons.act(a,as);}






  public static void warn_invalidEnumStrID(String enumID, String inStr, String dfStr){
    String msg = wrnPfix + "Enum '" + enumID + "' has NO member named '" + inStr + ".";
    if(dfStr!=null){msg += "Returning member '"+ dfStr + "' instead as default";}
    System.out.println(msg);
  }  

  public static void warn_invalidEnumStrID(String enumID, String inStr){
    warn_invalidEnumStrID(enumID, inStr, null);
  }



  public static void warn_UIStyleConstructor(){
    log("UIStyle Constuctor Warning: entered catch-all for unsupported object types - might want to check your input?");
  }


}

