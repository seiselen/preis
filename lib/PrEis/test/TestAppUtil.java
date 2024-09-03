package PrEis.test;

import PrEis.utils.Cons;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.event.MouseEvent;

// GUI_TESTBED_BG_IMG


public class TestAppUtil {

  enum TestMode {UTILS, GRIDMGR, PGFXUTL, GUIOBJS};

  PApplet                  app;
  PImage            guiTbedImg;
  TestGridManager  testGridMgr;
  TestUtilsManager   testUtils;
  TestGUIManager    testUIObjs;

  public static TestMode curTestMode = TestMode.GUIOBJS;


  public TestAppUtil(PApplet iApp){
    app = iApp;
  }


  public void settings(){
    switch(curTestMode){
      case GRIDMGR: app.size(640,320); return;
      case GUIOBJS: app.size(1280,720); return;
      default: return;
    }
  }


  public void setup(){
    switch(curTestMode){
      case UTILS:
        testUtils = new TestUtilsManager(app).testUtils();
        app.exit();
        return;
      case GRIDMGR: 
        testGridMgr = new TestGridManager(app);
        return;
      case GUIOBJS:
        loadUIObjectTestAssets();
        testUIObjs = new TestGUIManager(app)
        .Dark()
        .bindBGImg(guiTbedImg);
        return;
      default: 
      return;
    }
  }

  private void loadUIObjectTestAssets(){
    try{
      JSONObject jsonObj = app.loadJSONObject("/data/testDirs.json");
      guiTbedImg = app.loadImage(jsonObj.getString("GUI_TESTBED_BG_IMG"));
    }
    catch (Exception e){
      Cons.err("Failed to load one or more resources. Stack trace follows...");
      e.printStackTrace();
    }
  }

  public void update(){
    switch(curTestMode){
      case GUIOBJS: testUIObjs.update(); return;
      default: return;
    }
  }

  public TestAppUtil updateΘ(){
    update(); return this;
  }

  public void onKeyPressed(){
    switch(curTestMode){
      case GRIDMGR: testGridMgr.onKeyPressed(); return;
      case GUIOBJS: testUIObjs.onKeyPressed(); return;
      case PGFXUTL: TestPgfxUtils.testRender(app); return;
      default: return;
    }
  }
  
  public void onMousePressed(){
    switch(curTestMode){
      case GUIOBJS: testUIObjs.onMousePressed(); return;
      default: return;
    }
  }
  
  public void onMouseWheel(MouseEvent event){
    switch(curTestMode){
      case GUIOBJS: testUIObjs.onMouseWheel(event.getCount()); break;
      default: return;
    }
  }

  public void render(){
    switch(curTestMode){
      case GRIDMGR: testGridMgr.testRender(); break;
      case GUIOBJS: testUIObjs.testRender(); break;
      case PGFXUTL: TestPgfxUtils.testRender(app); break;
      default: break;
    }
  }

  
}
