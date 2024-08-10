package PrEis.test;
import PrEis.utils.StringUtils;

public class TestStringUtils {

  public static void runTests(){
    test_charTimesN();
  }
  
  public static void test_charTimesN(){
    TestFunc.testEquals(StringUtils.charTimesN('0', 0), "");
    TestFunc.testEquals(StringUtils.charTimesN('A', 1), "A");
    TestFunc.testEquals(StringUtils.charTimesN('B', 2), "BB");
    TestFunc.testEquals(StringUtils.charTimesN('C', 3), "CCC");    
    TestFunc.testEquals(StringUtils.charTimesN('D', 4), "DDDD");
    TestFunc.testEquals(StringUtils.charTimesN('E', 5), "EEEEE");
    TestFunc.testEquals(StringUtils.charTimesN('F', 6), "FFFFFF");
    TestFunc.testEquals(StringUtils.charTimesN('G', 7), "GGGGGGG");
    TestFunc.testEquals(StringUtils.charTimesN('8', 8), "88888888");
  }

  public static void test_pad_ints(){

  }

  public static void test_pad_strs(){

  }

  public static void test_pad_intArrays(){

  }

  public static void test_pad_strArrays(){

  }

  /*> REMAINING FUNCTIONS TO WRITE TESTS FOR...
    String  wrapStringWith      (char bfix, String str)
    String  wrapStringWith      (char pfix, String str, char sfix)
    String  wrapWithParenChars  (String str)
    char    lastCharOf          (String str)
    String  capFirstChar        (String str)
    String  valToSSVCapdWords   (String val)
    String  concatStrings       (String ... strs)
    String  concatAsCSV         (String ... strs)
    String  concatAsSCSV        (String ... strs)
 */


}

