package PrEis.test;
import PrEis.Testbed;
import PrEis.utils.Cons;

public class TestUtilsManager {

  Testbed app;

  public TestUtilsManager(Testbed iApp){
    app = iApp;
  }

  public TestUtilsManager testUtils(){
    TestStringUtils.runTests();
    TestDataStructUtils.runTests();
    TestQueryUtils.runTests();
    TestEnums.runTests();
    TestFormatUtils.runTests(app);
    TestFileSysUtils.runTests(app,getFileSysDirTests());
    TestFileWriteUtil.runTests(app);
    Cons.log("PrEis Utils Testing Complete!");
    return this;
  }
  
  String[] getFileSysDirTests(){
    String [] ret = null;
    try{
      ret = Testbed.TEST_DIRS.getJSONArray("EXAMPLE_DIRS").toStringArray();
    }
    catch (Exception e){
      Cons.err("Issue fetching one or more Utils Test assets");
      e.printStackTrace();
    }
    return ret;
  }
  


}
