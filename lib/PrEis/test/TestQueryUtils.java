package PrEis.test;
import PrEis.utils.QueryUtils;
import processing.core.PVector;

public class TestQueryUtils {
  private static final String P = "PASS";
  private static final String F = "FAIL";

  public static void runTests(){
    test_nullish_string();
    test_ArrayNullOrEmpty();
  }

  public static void test_nullish_string(){
    System.out.println("<=[ Testing 'nullish' (String) ]===============>");    
    TestFunc.strArrToConsole(new String[]{
      (QueryUtils.nullish("")==true?P:F),
      (QueryUtils.nullish(null)==true?P:F),
      (QueryUtils.nullish(" ")==false?P:F),
    });      
  }


  public static void test_ArrayNullOrEmpty(){
    System.out.println("<=[ Testing 'arrayNullOrEmpty' ]===============>");    
    TestFunc.strArrToConsole(new String[]{
      (QueryUtils.arrayNullOrEmpty(null)==true?P:F),
      (QueryUtils.arrayNullOrEmpty(new String[]{})==true?P:F),
      (QueryUtils.arrayNullOrEmpty(new PVector[]{})==true?P:F),
      (QueryUtils.arrayNullOrEmpty(new String[]{"a","b"})==false?P:F),
      (QueryUtils.arrayNullOrEmpty(new Integer[]{1,2,3})==false?P:F),
    });    
  }


  
}
