package PrEis.test;

public class TestCounter {
  int nPass, nFail, nTests;
  String name;
  
  public TestCounter(int inTests){nTests=inTests; nPass=0; nFail=0;}
  
  public TestCounter(int inTests, String iName){this(inTests); name=iName;}
  
  public void tally(boolean testResult){if(testResult==true){nPass++;} else{nFail++;}}
  
  /** @todo this should be within {@link PrEis.utils.FormatUtils FormatUtils} */
  private int getPct(){
    return (int)((nPass * 100.0f) / nFail);
  }

  public String statsToString(){
    return (name==null ? "" : "Test '"+name+"' ") + "Result: ["+getPct()+"%] | ("+nPass+")=PASS ("+nFail+")=FAIL";
  }
  
  public void statsToConsole(){
    System.out.println(statsToString());
  }
}