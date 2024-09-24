package PrEis.test;
import PrEis.utils.QueryUtils;
import processing.core.PVector;

public class TestQueryUtils {

  public static void runTests(){
    System.out.println("<=[ Testing 'arrayNullOrEmpty' ]===============>");
    test_ArrayNullOrEmpty();
  }

  public static void test_ArrayNullOrEmpty(){
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(QueryUtils.arrayNullOrEmpty(null),true),
      TestFunc.doEval(QueryUtils.arrayNullOrEmpty(new String[]{}),true),
      TestFunc.doEval(QueryUtils.arrayNullOrEmpty(new PVector[]{}),true),
      TestFunc.doEval(QueryUtils.arrayNullOrEmpty(new String[]{"a","b"}),false),
      TestFunc.doEval(QueryUtils.arrayNullOrEmpty(new Integer[]{1,2,3}),false)
    });
  }
}