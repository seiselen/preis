package PrEis.test;
import PrEis.utils.Cons;
import PrEis.utils.PImageUtils;

public class TestUtilsManager {
  Testbed app;

  private final boolean snippetTest = true;

  public TestUtilsManager(Testbed iApp){
    PImageUtils.app = iApp;
    app = iApp;
  }

  public TestUtilsManager testUtils(){
    switch(snippetTest?1:0){case 0 : onUtilsTesting(); break; default: onSnippetTesting();}
    return this;
  }

  private void onSnippetTesting(){
    /* THIS IS USED FOR 'ON-THE-GO' I.E. QAD I.E. EXPRESS TESTING OR UTIL RUNS. */
  }

  private void onUtilsTesting(){
    TestStringUtils.runTests();
    TestDataStructUtils.runTests();
    TestQueryUtils.runTests();
    TestEnums.runTests();
    TestFormatUtils.runTests(app);
    TestFileSysUtils.runTests(app,getFileSysDirTests());
    TestFileWriteUtil.runTests(app);
    System.out.println("PrEis Utils Testing Complete!");
  }
  
  String[] getFileSysDirTests(){
    String [] ret = null;
    try{ret = Testbed.TEST_DIRS.getJSONArray("EXAMPLE_DIRS").toStringArray();}
    catch (Exception e){Cons.err("Issue fetching one or more Utils Test assets"); e.printStackTrace();}
    return ret;
  }
  
}
