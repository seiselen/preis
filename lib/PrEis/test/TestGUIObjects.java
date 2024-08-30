package PrEis.test;

import PrEis.gui.AppFont;
import PrEis.gui.IActionCallback;
import PrEis.gui.IToggleCallback;
import PrEis.gui.IUpdateCallback;
import PrEis.gui.LabelType;
import PrEis.gui.UIClick;
import PrEis.gui.UIManager;
import PrEis.gui.UIObject;
import PrEis.utils.BBox;
import PrEis.utils.DataStructUtils;
import PrEis.utils.QueryUtils;
import PrEis.utils.StringUtils;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.IntDict;
import processing.data.JSONObject;


public class TestGUIObjects {

  private PApplet   app;
  private UIManager uim;
  private PImage    img;
  private boolean dispImg;
  private IntDict  glyphDict;


  ConslogAction  conslogAction;
  ToggAbleAction toggAbleAction;
  LitebulbAction litebulbAction;
  MousePosUpdate mousePosUpdate;
  ToggleBGGrid   toggleBGGrid;

  public TestGUIObjects(PApplet p){
    app = p;
    uim = new UIManager(app);
    uim.injectFonts(
      app.loadFont("TitilliumWebBold32.vlw"),
      app.loadFont("font-awesome-48.vlw")
    );
    dispImg = true;
    initCustomGlyphs();
    initGUI();
  }


  public void toggleDispBGImage(){dispImg = !dispImg;}
  public boolean getDispBGImage(){return dispImg;}  


  private void initCustomGlyphs(){
    JSONObject jo = app.loadJSONObject("/data/fontAwesomeCharCodes.json");
    glyphDict = new IntDict();
    String[] keys = DataStructUtils.keyArrayOfJSONObj(jo);
    for (String k : keys){glyphDict.add(k,jo.getInt(k));}
  }

  private String glyphChar(String n){return ""+(char)glyphDict.get(n);}


  public void initGUI(){
    conslogAction  = new ConslogAction("HELLO CONSOLE!");
    litebulbAction = new LitebulbAction(app, DataStructUtils.vec2(app.width/2f, app.height/2f));
    mousePosUpdate = new MousePosUpdate(app);

    int xOff = 128;
    int yOff = 64;

    uim.createUIClick(new BBox(xOff, yOff, 256, 64), "Test Click Button", AppFont.TEXT, conslogAction);

    uim.createUIToggle(new BBox(xOff, yOff+=96, 256, 64), "Light Bulb", AppFont.TEXT, litebulbAction)
    .withOnOffLabels("Lightbulb [ON]", "Lightbulb [OFF]")
    ;

    uim.createUILabel(new BBox(xOff, yOff+=96, 256, 64), null, AppFont.TEXT, LabelType.OP, mousePosUpdate);

    UIClick needsToBeVar = uim.createUIClick(new BBox(xOff, yOff+=96, 256, 64), "I Am Enabled!", AppFont.TEXT, conslogAction)
    .withDisabledState(true).withDLabel("I Am Disabled!").castToClick();

    toggAbleAction = new ToggAbleAction(needsToBeVar);
    uim.createUIClick(new BBox(xOff-32, yOff+=96, 320, 64), "(En/Dis)able Above Button", AppFont.TEXT, toggAbleAction);

    
    toggleBGGrid = new ToggleBGGrid(this);
    uim.createUIToggle(
      new BBox(app.width-128, 128, 96, 96),
      glyphChar("gridish"),
      AppFont.GLYPH, toggleBGGrid
    );

  }

  public TestGUIObjects bindBGImg(PImage i){img=i; return this;}

  public void update(){uim.update();}

  public void onKeyPressed(){uim.onKeyPressed();}
  
  public void onMousePressed(){uim.onMousePressed();}
  
  public void onMouseWheel(int v){uim.onMouseWheel(v);}

  public void testRender(){
    app.background(255);
    if(img!=null && dispImg){app.imageMode(PApplet.CORNER); app.image(img, 0, 0);}
    uim.render();
    litebulbAction.renderLite();
  }



}

/*==============================================================================
|>>> TEST CALLBACK DEFINITIONS (COULD MOVE TO `PDE` CODE S.T. GITIGNORED)
+=============================================================================*/

class ToggleBGGrid implements IToggleCallback{
  TestGUIObjects targetObj;
  public ToggleBGGrid(TestGUIObjects tar){targetObj = tar;}
  public boolean getState(){return targetObj.getDispBGImage();}
  public void toggleState(){targetObj.toggleDispBGImage();}
}


class ToggAbleAction implements IActionCallback{
  UIObject targetObj;
  public ToggAbleAction(UIObject obj){targetObj = obj;}
  public void action(){targetObj.toggleDisabled();}
}

class MousePosUpdate implements IUpdateCallback {
  PApplet app;
  public MousePosUpdate(PApplet p){app = p;}
  public String getTxt(){return StringUtils.concatStrings("{ X:",PApplet.nf(app.mouseX,4),", Y:",PApplet.nf(app.mouseY,4)," }");}
}

class ConslogAction implements IActionCallback {
  private String message;
  public ConslogAction(String iMsg){message=QueryUtils.nullish(iMsg)?"":iMsg;}
  public void action(){System.out.println(message);}
}

class LitebulbAction implements IToggleCallback {
  boolean liteIsOn;
  private int nContours = 10;
  private int minDiam   = 32;
  private int maxDiam   = 128;
  private int[] col     = new int[4];
  private float buffPct;
  PVector pos;
  PApplet app;

  public LitebulbAction(PApplet iApp, PVector iPos){
    pos       = iPos;
    app       = iApp;
    liteIsOn  = false;
    col       = new int[]{
      app.color(224,224,0),   //[0]-> MIN  'ON' COLOR
      app.color(96,64,0),     //[1]-> MIN 'OFF' COLOR
      app.color(255,255,216), //[2]-> MAX  'ON' COLOR
      app.color(144,128,32),  //[3]-> MAX 'OFF' COLOR
    };
  }

  public boolean getState(){return liteIsOn;}
  public void toggleState(){liteIsOn = !liteIsOn;}

  /** @see Source: https://processing.org/examples/functions.html */
  void renderLite() {
    app.ellipseMode(PApplet.CENTER);
    for (int i=0; i<nContours; i++){
      buffPct = (i+1)*0.1f;
      if(i==0){app.stroke(64,32,0); app.strokeWeight(2);} else{app.noStroke();}
      app.fill(app.lerpColor(liteIsOn?col[0]:col[1], liteIsOn?col[2]:col[3], buffPct));
      app.circle(pos.x, pos.y, PApplet.lerp(maxDiam, minDiam, buffPct));
    }
  }
  
}