package PrEis.test;
import PrEis.utils.DataStructUtils;
import processing.core.PVector;

/**
 * @implNote Skipping testsing on the `IntDict` filtering stuff for now, as it's
 * a 'niche' util for the (recently rediscovered) image compare utils.
 */
public class TestDataStructUtils {
  public static void runTests(){
    System.out.println("<=[ Testing 'createVector' ]===================>");
    test_PVector_factories();
  }

  public static void test_PVector_factories(){
    PrEisTestFunc.testResultsToConsole(new boolean[]{
      PrEisTestFunc.doEval(DataStructUtils.createVector().equals(new PVector(0, 0, 0)), true),
      PrEisTestFunc.doEval(DataStructUtils.createVector(3, 4).equals(new PVector(3, 4)), true),
      PrEisTestFunc.doEval(DataStructUtils.createVector(3, 4, 5).equals(new PVector(3, 4, 5)), true),
      PrEisTestFunc.doEval(DataStructUtils.createVector(1.2f, 2.3f).equals(new PVector(1.2f, 2.3f)), true),
      PrEisTestFunc.doEval(DataStructUtils.createVector(3.4f, 4.5f, 5.6f).equals(new PVector(3.4f, 4.5f, 5.6f)), true),
      PrEisTestFunc.doEval(DataStructUtils.createVector(1, 2, 3, 4, 5, 6).equals(new PVector(1, 2, 3)), true),
      PrEisTestFunc.doEval(DataStructUtils.createVector(1.2f, 2.3f, 3.4f, 4.5f, 5.6f).equals(new PVector(1.2f, 2.3f, 3.4f)), true),
    });
  }
}