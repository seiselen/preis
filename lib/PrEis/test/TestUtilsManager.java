package PrEis.test;
import java.io.File;

import PrEis.utils.Cons;
import PrEis.utils.FileSysUtils;
import PrEis.utils.PImageUtils;
import PrEis.utils.Pair;
import PrEis.utils.SpritesheetPlan;
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

    String pathStr = FileSysUtils.pathConcat(app.getRootDir(),"tests","inputs","example_spritesheet_plan.json");
    File f = new File(pathStr);
    if(!f.exists()){System.out.println("Can't find it!"); return;}
    JSONObject o = app.loadJSONObject(pathStr);
    if(o==null){System.out.println("Can't load it!"); return;}

    SpritesheetPlan plan = SpritesheetPlan.withJSON(o);
    plan.toConsole();

    Pair<String,PImage> test = PImageUtils.splitSpritesheet(plan);

    test.b.save(FileSysUtils.pathConcat(app.getRootDir(),"tests","outputs",test.a+".png"));


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
