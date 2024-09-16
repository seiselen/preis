package PrEis;
import PrEis.test.TestAssetKey;
import PrEis.test.TestGUIManager;
import PrEis.test.TestGridManager;
import PrEis.test.TestPgfxUtils;
import PrEis.test.TestUtilsManager;
import PrEis.utils.Cons;

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.data.JSONObject;

public class Testbed extends PApplet {

  public enum TestMode {UTILS, GRIDMGR, PGFXUTL, GUIOBJS};
  public static TestMode curTestMode = TestMode.GUIOBJS;

  private static final String TEMP_ROOT_DIR = "C:/Users/Phoenix/Documents/projectsWorkspace/PrEis";
  private static final String TEST_DIRS_SUBPATH = "assets/testDirs.json";

  public static TestGridManager  testGridMgr;
  public static TestUtilsManager   testUtils;
  public static TestGUIManager    testUIObjs;

  public static JSONObject TEST_DIRS;

  public static void main(String[] args) {
    PApplet.main("PrEis.Testbed"); 
  }

  public void settings(){
    switch(curTestMode){
      case GRIDMGR: size(640,320); return;
      case GUIOBJS: size(1280,720); return;
      default: return;
    }
  }

  public void setup(){
    loadTestDirsJSON();

    switch(curTestMode){
      case UTILS:
        testUtils = new TestUtilsManager(this).testUtils();
        exit();
        return;
      case GRIDMGR: 
        testGridMgr = new TestGridManager(this);
        return;
      case GUIOBJS:
        testUIObjs = new TestGUIManager(this).Dark();
        return;
      default: 
      return;
    }
  }

  
  public void update(){
    switch(curTestMode){
      case GUIOBJS: testUIObjs.update(); return;
      default: return;
    }
  }
  
  public void render(){
    switch(curTestMode){
      case GRIDMGR: testGridMgr.testRender(); break;
      case GUIOBJS: testUIObjs.testRender(); break;
      case PGFXUTL: TestPgfxUtils.testRender(this); break;
      default: break;
    }    
  }
  
  public void keyPressed(){
    switch(curTestMode){
      case GRIDMGR: testGridMgr.onKeyPressed(); return;
      case GUIOBJS: testUIObjs.onKeyPressed(); return;
      case PGFXUTL: TestPgfxUtils.testRender(this); return;
      default: return;
    }
  }
  
  public void mousePressed(){
    switch(curTestMode){
      case GUIOBJS: testUIObjs.onMousePressed(); return;
      default: return;
    }
  }
  
  public void mouseWheel(MouseEvent e){
    switch(curTestMode){
      case GUIOBJS: testUIObjs.onMouseWheel(e.getCount()); break;
      default: return;
    }
  }
  
  public void draw(){
    update();
    render();
  }

  public static String getPathOf(TestAssetKey k){
    return TEMP_ROOT_DIR+"/"+ TEST_DIRS.getString(k.s());
  }



  private void loadTestDirsJSON(){
    String testDirsFilePath = TEMP_ROOT_DIR+"/"+TEST_DIRS_SUBPATH;
    System.out.println("Fullpath Of 'testDirs' : ("+testDirsFilePath+")");
    try {
      TEST_DIRS = loadJSONObject(testDirsFilePath);
    }
    catch (Exception e) {
      Cons.err("Something went wrong reading file ("+testDirsFilePath+")");
      exit();
      return;
    }
  }

}
