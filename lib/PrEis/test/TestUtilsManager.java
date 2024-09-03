package PrEis.test;

import PrEis.utils.Cons;
import processing.core.PApplet;

public class TestUtilsManager {

  PApplet app;

  public TestUtilsManager(PApplet iApp){
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
    try{ret = app.loadJSONObject("/data/testDirs.json").getJSONArray("EXAMPLE_DIRS").toStringArray();}
    catch (Exception e){e.printStackTrace(); System.err.println("ERROR: Something went wrong loading JSON! Returning null.");}
    return ret;
  }
  


}
