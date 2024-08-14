package PrEis.test;
//> THERE SHOULD BE **NO** PrEis MODULE IMPORTS HERE! NATIVE JAVA ONLY.

/**
 * Test Functions.
 */
public class TestFunc {

  private static final String STR_HASH_LINE = "################################";
  private static final String STR_DASH_LINE = "--------------------------------";
  private static final String STR_TEST_PASS = "PASS";
  private static final String STR_TEST_FAIL = "FAIL";

  /** Output default message for passing tests indicating thereof? */
  public static boolean LOG_PASS = true; 

  private static void compareOutValExpVal(String out, String exp){
    System.out.println("OUTPUT: "+out+'\n'+"ACTUAL: "+exp);
  }
  
  /**
   * Evaluates i.e. compares function output with expected input.
   * @param out function output 
   * @param exp expected output
   */
  public static boolean doEval(int out, int exp){
    boolean result = out==(exp);
    if(result && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL); compareOutValExpVal(""+out,""+exp);}
    return result;
  }

  /**
   * Evaluates i.e. compares function output with expected input.
   * @param out function output 
   * @param exp expected output
   */
  public static boolean doEval(String out, String exp){
    boolean result = out.equals(exp);
    if(result && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL); compareOutValExpVal(out,exp);}
    return result;
  }

  /**
   * Evaluates i.e. compares function output with expected input.
   * @param out function output 
   * @param exp expected output
   */
  public static boolean doEval(String[] out, String[] exp){
    if (out.length!=exp.length){return arrLensDiffToConsole();}
    boolean doesPass = true;
    for (int i=0; i<out.length; i++){if(!out[i].equals(exp[i])){doesPass=false; compareOutValExpVal(out[i], exp[i]);}}
    if(doesPass && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL);}
    return doesPass;
  }

  /**
   * Evaluates i.e. compares function output with expected input.
   * @param out function output 
   * @param exp expected output
   */
  public static boolean doEval(int[] out, int[] exp){
    if (out.length!=exp.length){return arrLensDiffToConsole();}
    boolean doesPass = true;
    for (int i=0; i<out.length; i++){if(out[i]!=exp[i]){doesPass=false; compareOutValExpVal(""+out[i], ""+exp[i]);}}
    if(doesPass && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL);}
    return doesPass;
  }



  /**
   * Evaluates i.e. compares function output with expected input. This realizes
   * comparisons for arbitrary object types via comparing calls of `toString`.
   * @param out function output 
   * @param exp expected output
   */
  public static boolean doEval(Object[] out, Object[] exp){
    if (out.length!=exp.length){return arrLensDiffToConsole();}
    boolean doesPass = true;
    for (int i=0; i<out.length; i++){if(out[i]!=exp[i]){doesPass=false; compareOutValExpVal(out[i].toString(), exp[i].toString());}}
    if(doesPass && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL);}
    return doesPass;
  }




  /** Syntax Sugar for {@link #doEval} */
  public static boolean e(String o, String e){return doEval(o, e);}
  

  public void print_launchingTesting(){
    System.out.println(STR_HASH_LINE);
    System.out.println("Commencing Testing Process - Standby");
    System.out.println(STR_DASH_LINE);
  }
  

  /** Prints elements of string array. */
  public static void strArrToConsole(String[] out){
    for(String s : out){System.out.println(s);}
  }

  private static boolean arrLensDiffToConsole(){
    System.out.println("FAIL: Array Lengths Disagree.");
    return false;
  }

}
