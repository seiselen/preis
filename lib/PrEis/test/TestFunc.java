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

  public static void testValCompare(String out, String exp){
    System.out.println("OUTPUT: "+out+'\n'+"ACTUAL: "+exp);
  }
  
  public static boolean testEquals(String out, String exp){
    boolean result = out.equals(exp);
    if(result==true){
      System.out.println(STR_TEST_PASS);
      System.out.println("Output String Matches Expected String");}
    else{
      System.out.println(STR_TEST_FAIL);
      testValCompare(out,exp);
    }
    return result;
  }
  
  
  public void print_launchingTesting(){
    System.out.println(STR_HASH_LINE);
    System.out.println("Commencing Testing Process - Standby");
    System.out.println(STR_DASH_LINE);
  }
  
}
