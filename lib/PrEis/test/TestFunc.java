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
   * <b>Do Evaluate</b> i.e. compares function output with its expected input.
   * @param out function output @param exp expected output
   * @return <code>true</code> if <code>(out==exp)</code>, else <code>false</code>
   */
  public static boolean doEval(String out, String exp){
    boolean result = out.equals(exp);
    if(result && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL); compareOutValExpVal(out,exp);}
    return result;
  }

  /**
   * <b>Do Evaluate</b> i.e. compares function output with its expected input.
   * @param out function output @param exp expected output
   * @return <code>true</code> if <code>(out==exp)</code>, else <code>false</code>
   */
  public static boolean doEval(String[] out, String[] exp){
    
    if (out.length!=exp.length){
      System.out.println("FAIL: Array Lengths Disagree.");
      return false;
    }
    
    boolean doesPass = true;
    for (int i=0; i<out.length; i++){
      if(!out[i].equals(exp[i])){
        doesPass=false;
        compareOutValExpVal(out[i], exp[i]);
      }
    }

    if(doesPass && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL);}
    return doesPass;
  }

  /**
   * <b>Do Evaluate</b> i.e. compares function output with its expected input.
   * This is for <code>int[]-2-int[]</code> comparisons; non-generalized (yet!)
   * because it's late and I'm over schedule+spec+scope and really don't want to
   * fuck with 'Generics Wild West' hijinks any more than I already have...
   * @param out function output @param exp expected output
   * @return <code>true</code> if <code>(out==exp)</code>, else <code>false</code>
   */
  public static boolean doEval(int[] out, int[] exp){
    if (out.length!=exp.length){System.out.println("FAIL: Array Lengths Disagree.");return false;}
    boolean doesPass = true;
    for (int i=0; i<out.length; i++){if(out[i]!=exp[i]){doesPass=false; compareOutValExpVal(""+out[i], ""+exp[i]);}}
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

}
