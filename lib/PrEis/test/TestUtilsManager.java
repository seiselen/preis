package PrEis.test;
import java.io.File;
import java.util.ArrayList;

import PrEis.utils.Cons;
import PrEis.utils.FileSysUtils;
import PrEis.utils.PImageUtils;
import PrEis.utils.Pair;
import PrEis.utils.SpritesheetPlan;
import PrEis.utils.SpritesheetUtils;
import processing.core.PImage;
import processing.data.JSONObject;

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

    String planFP = FileSysUtils.pathConcat(app.getRootDir(),"tests","inputs","example_spritesheet_plan.json");
    SpritesheetUtils.splitAndSaveSprites(app, planFP);
    
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
