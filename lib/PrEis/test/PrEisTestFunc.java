package PrEis.test;
//> THERE SHOULD BE **NO** PrEis MODULE IMPORTS HERE! NATIVE JAVA ONLY.

/**
 * Test Functions.
 */
public class PrEisTestFunc {

  private static final String STR_HASH_LINE = "################################";
  private static final String STR_DASH_LINE = "--------------------------------";
  private static final String STR_TEST_PASS = "PASS";
  private static final String STR_TEST_FAIL = "FAIL";

  private static final String STR_NULL_EXPD = "NULL OUTPUT EXPECTED";

  /** Output default message for passing tests indicating thereof? */
  public static boolean LOG_PASS = false; 

  private static void compareOutValExpVal(String out, String exp){
    System.out.println("OUTPUT: "+out+'\n'+"ACTUAL: "+exp);
  }

  private static void compareOutValExpVal(boolean out, boolean exp){
    System.out.println("OUTPUT: "+out+'\n'+"ACTUAL: "+exp);
  }

  
  /**
   * Evaluates i.e. compares function output with expected input.
   * @param out function output 
   * @param exp expected output
   */
  public static boolean doEval(int out, int exp){
    boolean result = out==(exp);
    if(result){if(LOG_PASS){System.out.println(STR_TEST_PASS);}}
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
    if(result){if(LOG_PASS){System.out.println(STR_TEST_PASS);}}
    else{System.out.println(STR_TEST_FAIL); compareOutValExpVal(out,exp);}
    return result;
  }


  /** 
   * Special version of {@link #doEval} for tests in which <code>null</code> is
   * expected as output. 
   * @implNote No QAD way to handle false positives with the other versions of
   * this function, ergo this'll do for now!
   */
  public static boolean doEvalExpNull(Object out){
    if(out==null){return true;}
    System.out.println(STR_NULL_EXPD);
    return false;
  }


  /**
   * Evaluates i.e. compares function output with expected input.
   * @param out function output 
   * @param exp expected output
   */
  public static boolean doEval(boolean out, boolean exp){
    boolean result = out==exp;
    if(result){if(LOG_PASS){System.out.println(STR_TEST_PASS);}}
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
    if(doesPass){if(LOG_PASS){System.out.println(STR_TEST_PASS);}}
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
    if(doesPass){if(LOG_PASS){System.out.println(STR_TEST_PASS);}}
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
    if(doesPass){if(LOG_PASS){System.out.println(STR_TEST_PASS);}}
    else{System.out.println(STR_TEST_FAIL);}
    return doesPass;
  }





  public void print_launchingTesting(){
    System.out.println(STR_HASH_LINE);
    System.out.println("Commencing Testing Process - Standby");
    System.out.println(STR_DASH_LINE);
  }
  

  /** Prints elements of string array. */
  public static void strArrToConsole(String[] out){
    for(String s : out){System.out.println(s);}
  }


  /** Prints elements of string array. */
  public static void testResultsToConsole(boolean[] results){
    int nTests  = results.length;
    int nPassed = 0;
    String ret = "";
    for(int i=0; i<results.length; i++){
      if(results[i]){nPassed++;}
      else{ret += "T#"+(i+1)+" ";}
    }
    System.out.print("("+getPercentOf(nPassed,nTests)+"% "+STR_TEST_PASS+")");
    if(ret.length()>0){
      //> tempted to call `lastCharOf` here, but see comment atop this source :-) 
      if(ret.charAt(ret.length()-1)==' '){ret = ret.substring(0, ret.length()-1);}
      //> tempted to call `wrapWith...` here but also see comment atop this source :-)
      System.out.println(" | "+STR_TEST_FAIL+"={"+ret+"}");
    }
    else{System.out.println();}
  }

  private static boolean arrLensDiffToConsole(){
    System.out.println("FAIL: Array Lengths Disagree.");
    return false;
  }
  
  
  /**
   * Returns ratio in int range `[0:100]`.
   * @todo could be a utils function, maybe put in `FormatUtils` (in lieu of `MathUtils`)?
   * @param n <b>numerator</b>
   * @param d <b>denominator</b>
   */
  public static int getPercentOf(int n, int d){
    return (int)((n*100f)/d);
  }
  
  
}
