package PrEis.test;
import PrEis.utils.Cons;

public class TestUtilsManager {

  Testbed app;

  private final boolean snippetTest = false;

  public TestUtilsManager(Testbed iApp){
    app = iApp;
  }

  public TestUtilsManager testUtils(){
    switch(snippetTest?1:0){case 0 : onUtilsTesting(); break; default: onSnippetTesting();}
    return this;
  }

  private void onSnippetTesting(){

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
