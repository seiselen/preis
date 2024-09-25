package PrEis.test;
import PrEis.utils.QueryUtils;
import processing.core.PVector;

public class TestQueryUtils {

  public static void runTests(){
    System.out.println("<=[ Testing 'arrayNullOrEmpty' ]===============>");
    test_ArrayNullOrEmpty();
  }

  public static void test_ArrayNullOrEmpty(){
    PrEisTestFunc.testResultsToConsole(new boolean[]{
      PrEisTestFunc.doEval(QueryUtils.arrayNullOrEmpty(null),true),
      PrEisTestFunc.doEval(QueryUtils.arrayNullOrEmpty(new String[]{}),true),
      PrEisTestFunc.doEval(QueryUtils.arrayNullOrEmpty(new PVector[]{}),true),
      PrEisTestFunc.doEval(QueryUtils.arrayNullOrEmpty(new String[]{"a","b"}),false),
      PrEisTestFunc.doEval(QueryUtils.arrayNullOrEmpty(new Integer[]{1,2,3}),false)
    });
  }
}