package PrEis.test;
import PrEis.utils.Cons;
import PrEis.utils.JAResourceUtil;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.data.JSONObject;

public class Testbed extends PApplet {

  public enum TestMode {UTILS, GRIDMGR, PGFXUTL, GUIOBJS, HTMLPARSE};
  public static TestMode curTestMode = TestMode.UTILS;

  public static String APP_ROOT_DIR = "";
  private static final String TEST_DIRS_SUBPATH = "assets/testDirs.json";

  public static TestGridManager  testGridMgr;
  public static TestUtilsManager   testUtils;
  public static TestGUIManager    testUIObjs;
  public static TestHTMLParsing  testHTMLPrs;

  public static JSONObject TEST_DIRS;

  public static void main(String[] args) {
    PApplet.main("PrEis.test.Testbed");
    System.out.println("\n \n"); //> corrects debug launch blurb lack of newline
  }

  public void settings(){
    switch(curTestMode){
      case GRIDMGR: size(1280,768); return;
      case GUIOBJS: size(1280,768); return;
      default: return;
    }
  }
  
  public void setup(){
    JAResourceUtil.app = this;
    APP_ROOT_DIR = sketchPath();
    loadTestDirsJSON();

    JAResourceUtil.app = this;

    switch(curTestMode){
      case UTILS:
        testUtils = new TestUtilsManager(this).testUtils();
        noLoop(); exit(); return;
      case GRIDMGR: 
        testGridMgr = new TestGridManager(this);
        return;
      case GUIOBJS:
        testUIObjs = new TestGUIManager(this).Dark();
        return;
      case HTMLPARSE:
        testHTMLPrs = new TestHTMLParsing(this);
        testHTMLPrs.test1();
        noLoop(); exit(); return;
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
    if(key=='q'||key=='Q'||keyCode==PApplet.ESC){
      exit(); return;
    }
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

  public String getPathOf(TestAssetKey k){
    return APP_ROOT_DIR+"/"+ TEST_DIRS.getString(k.get());
  }

  public String getRootDir(){
    return APP_ROOT_DIR;
  }

  private void loadTestDirsJSON(){
    String testDirsFilePath = APP_ROOT_DIR+"/"+TEST_DIRS_SUBPATH;
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
