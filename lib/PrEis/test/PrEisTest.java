package PrEis.test;

/*
  new TestCase()
  .eval(FileSysUtils.pathConcat("A","B","C","123.txt"))
  .aseq("A/B/C/123.txt")
 */

/** Encompasses a reasonably generic test case realization. */
public class PrEisTest {
  Object   outVal;
  Object   expVal;
  boolean  result;

  enum TestResult {PASS,FAIL};

  public <T> PrEisTest(){
    result   = false;
  }

  public PrEisTest output(Object retVal){
    outVal = retVal;
    return this;
  }

  public PrEisTest expect(Object tstVal){
    expVal = tstVal;
    if(outVal.getClass()!=expVal.getClass()){result=false;}
    else{result = outVal.equals(expVal);}
    return this;
  }

  public boolean evalBool(){
    return result;
  }

  public TestResult evalResult(){
    return result ? TestResult.PASS : TestResult.FAIL;
  }


  public String outToString(){
    return outVal.toString();
  }

  public String expToString(){
    return expVal.toString();
  }

  public String valsToString(){
    return "Output Val: "+outToString()+"\n"+
           "Expect Val: "+expToString()+"\n"+
           "Result Val: "+result;
  }

  public String compareVals(){
    return "OUTPUT: "+outToString()+'\n'+"ACTUAL: "+expToString();
  }

  public void toConsole(){System.out.println(valsToString());}

  public static void TestSet(PrEisTest ... tests){
    int nTests = tests.length;
    boolean [] results = new boolean[nTests];
    for (int i=0; i<nTests; i++){
      if(tests[i].evalResult()==TestResult.FAIL){tests[i].compareVals();}
      results[i] = tests[i].evalBool();     
    }
    PrEisTestFunc.testResultsToConsole(results);
  }
}