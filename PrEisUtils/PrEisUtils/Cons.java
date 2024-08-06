package PrEisUtils;

public class Cons {
  private static String errPfix = "ERROR: ";
  private static String actPfix = "ACTION: ";

  public enum Err {
    NULL_XOR_INVALID {public String toString(){return "Nullish Xor Invalid Input";}},
    MISSING_SUFFIX   {public String toString(){return "Input Lacks Required Suffix";}}
  }

  public enum Act {
    RETURN_ZERO_VEC {public String toString(){return "Returning Zeroed PVector";}},
    ADDING_REQ_SUFX {public String toString(){return "Adding Required Suffix";}}
  }

  public static void err(Err e){System.err.println(errPfix+e+"!");}
  public static void err(Err e, String s){System.err.println(errPfix+e+" ["+s+"]!");}

  public static void act(Act a){System.err.println(actPfix+a+".");}
  public static void act(Act a, String s){System.err.println(actPfix+a+" ["+s+"].");}

  public static void err_act(Err e, Act a){Cons.err(e); Cons.act(a);}
  public static void err_act(Err e, Act a, String s){Cons.err(e,s); Cons.act(a,s);}

  public static void log(String s){System.out.println(s);}
  public static void log(String ... strs){for(String s: strs){log(s);}}

}

