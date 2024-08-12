package PrEis.test;
//> THERE SHOULD BE **NO** PrEis MODULE IMPORTS HERE! NATIVE JAVA ONLY.

/**
 * Test Functions.
 */
public class TestFunc {

  private static final String STR_HASH_LINE = "################################";
  private static final String STR_DASH_LINE = "--------------------------------";
  private static final String STR_TEST_PASS = "TEST PASSED!";
  private static final String STR_TEST_FAIL = "TEST FAILED!";

  /** Output default message for passing tests indicating thereof? */
  public static boolean LOG_PASS = false; 

  private static void testValCompare(String out, String exp){
    System.out.println("OUTPUT: "+out+'\n'+"ACTUAL: "+exp);
  }
  

  /**
   * <b>Do Evaluate</b> i.e. compares the output of a function with its expected
   * input; s.t. message may be printed and bool returned depending thereby.
   * @param out function call s.t. output is argument
   * @param exp expected output of the function
   * @return <code>true</code> if <code>(out==exp)</code>, else <code>false</code>
   */
  public static boolean doEval(String out, String exp){
    boolean result = out.equals(exp);
    if(result && LOG_PASS){System.out.println(STR_TEST_PASS);}
    else{System.out.println(STR_TEST_FAIL); testValCompare(out,exp);}
    return result;
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

}
