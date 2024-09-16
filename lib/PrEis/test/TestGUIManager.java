package PrEis.test;

import java.util.HashMap;
import java.util.Set;

import PrEis.Testbed;
import PrEis.gui.AppFont;
import PrEis.gui.ConfirmState;
import PrEis.gui.IActionCallback;
import PrEis.gui.IConfirmAction;
import PrEis.gui.ISelectAction;
import PrEis.gui.IToggleCallback;
import PrEis.gui.IUpdateCallback;
import PrEis.gui.LabelType;
import PrEis.gui.UIClick;
import PrEis.gui.UIContainer;
import PrEis.gui.UIDropdown;
import PrEis.gui.UILabel;
import PrEis.gui.UIManager;
import PrEis.gui.UIObject;
import PrEis.gui.UIToggle;
import PrEis.gui.UIConfirm;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.DataStructUtils;
import PrEis.utils.QueryUtils;
import PrEis.utils.StringUtils;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.IntDict;
import processing.data.JSONObject;

public class TestGUIManager {

  private enum BGThMode {LITE, DARK};
  private BGThMode bgTheme = BGThMode.LITE;

  private int COL_ERR, COL_LITE, COL_DARK;
  private int ALP_ERR, ALP_LITE, ALP_DARK;

  private Testbed   app;
  private UIManager uim;
  private PImage    guiBgImg;
  private boolean dispImg = true;
  private IntDict  glyphDict;

  PFont textFont;
  PFont glyphFont;
  JSONObject glyphCodes;

  ConslogAction  conslogAction;
  ToggAbleAction toggAbleAction;
  LitebulbAction litebulbAction;
  MousePosUpdate mousePosUpdate;
  ToggleBGGrid   toggleBGGrid;
  ExDDownAction  ayleidRuinsAction;
   
  /** @implNote Call Order <b>COUNTS</b>! */
  public TestGUIManager(Testbed p){
    app = p;
    uim = new UIManager(app);
    loadFonts();
    injectFonts();
    initCustomGlyphs();
    initGUI();
    initMiscGFX();
  }

  private void loadFonts(){
    try {
      textFont  = app.loadFont(Testbed.getPathOf(TestAssetKey.TEXT_FONT));
      glyphFont = app.loadFont(Testbed.getPathOf(TestAssetKey.GLYPH_FONT));
      glyphCodes = app.loadJSONObject(Testbed.getPathOf(TestAssetKey.GLYPH_CODES));
      guiBgImg  = app.loadImage(Testbed.getPathOf(TestAssetKey.GUI_BG_IMG));
    } catch (Exception e) {
      System.err.println("Issue fetching one or more GUI Testbed assets. Check that filepaths are correct.");
      e.printStackTrace();
      app.exit();
      return;
    }
  }

  private void injectFonts(){
    uim.injectFonts(textFont, glyphFont);
  }






  public TestGUIManager Dark(){bgTheme=BGThMode.DARK; return this;}
  public TestGUIManager Lite(){bgTheme=BGThMode.LITE; return this;}

  public void initMiscGFX(){
    COL_ERR  = app.color(255,0,255);
    COL_LITE = app.color(255);
    COL_DARK = app.color(0);
    ALP_LITE = (int)(255*0.5f);
    ALP_DARK = (int)(255*0.75f);
    ALP_ERR  = 255;
  }

  private void initCustomGlyphs(){
    glyphDict = new IntDict();
    String[] keys = DataStructUtils.keyArrayOfJSONObj(glyphCodes);
    for (String k : keys){glyphDict.add(k,glyphCodes.getInt(k));}
  }


  public void toggleDispBGImage(){dispImg = !dispImg;}

  public boolean getDispBGImage(){return dispImg;}

  private String glyphChar(String n){return ""+(char)glyphDict.get(n);}


  public void initGUI(){
    conslogAction  = new ConslogAction("HELLO CONSOLE!");
    litebulbAction = new LitebulbAction(app, DataStructUtils.vec2(app.width/2f, app.height/2f));
    mousePosUpdate = new MousePosUpdate(app);
    int xOff, yOff, xDim, yDim;

    xOff = 64;
    yOff = 32;

    UIClick.create(uim, new BBox(xOff, yOff, 256, 64), "Test Click Button", AppFont.TEXT, conslogAction);

    UIToggle.create(uim, new BBox(xOff, yOff+=96, 256, 64), "Light Bulb", AppFont.TEXT, litebulbAction)
    .withOnOffLabels("Lightbulb [ON]", "Lightbulb [OFF]");

    UILabel.create(uim, new BBox(xOff, yOff+=96, 256, 64), null, AppFont.TEXT, LabelType.OP, mousePosUpdate);

    UIClick needsToBeVar = UIClick.create(uim, new BBox(xOff, yOff+=96, 256, 64), "I Am Enabled!", AppFont.TEXT, conslogAction)
    .withDisabledState(true).withDLabel("I Am Disabled!").castTo(UIClick.class);

    toggAbleAction = new ToggAbleAction(needsToBeVar);
    UIClick.create(uim, new BBox(xOff-32, yOff+=96, 320, 64), "(En/Dis)able Above Button", AppFont.TEXT, toggAbleAction);


    UIContainer.create(uim, new BBox(xOff+304, 32, 192, 144)).addChildren(
      UILabel.create(app, new BBox(16, 16, 160, 32), "TOGGLE BG GRID", AppFont.TEXT, LabelType.OP, null)
      .setStyleProp("txt_size", Integer.class, 18),
      UIToggle.create(app, new BBox(48, 64, 96, 64), glyphChar("gridish"), AppFont.GLYPH, new ToggleBGGrid(this))
    ).toggleShowBounds();

    xOff = 32; yOff = 16; xDim = 64; yDim = 32;

    UIContainer.create(uim, new BBox(64, 512+32, 512, 128))
    .addChildren(
      UIClick.create(app, new BBox(xOff, yOff, xDim, yDim           ), "MER", AppFont.TEXT, new ConslogAction("Mercury")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "VEN", AppFont.TEXT, new ConslogAction("Venus")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "EAR", AppFont.TEXT, new ConslogAction("Earth")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "MAR", AppFont.TEXT, new ConslogAction("Mars")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "CER", AppFont.TEXT, new ConslogAction("Ceres")),      
      UIClick.create(app, new BBox(xOff=32,yOff+=yDim+yDim,xDim,yDim), "JUP", AppFont.TEXT, new ConslogAction("Jupiter")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "SAT", AppFont.TEXT, new ConslogAction("Saturn")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "NEP", AppFont.TEXT, new ConslogAction("Neptune")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "URA", AppFont.TEXT, new ConslogAction("Uranus")),
      UIClick.create(app, new BBox(xOff+=xDim+yDim, yOff, xDim, yDim), "PLT", AppFont.TEXT, new ConslogAction("Pluto"))      
    )
    .toggleShowBounds();
    

    ayleidRuinsAction = new ExDDownAction();
    AyledidRuinsCallback cb = new AyledidRuinsCallback(ayleidRuinsAction);

    UILabel.create(
      uim, new BBox((app.width/2)+256, 32, 320, 32), null, AppFont.TEXT, LabelType.OP, cb)
    .setStyleProp("txt_size", Integer.class, 16);
    
    /*UIDropdown ddown =*/ UIDropdown.create(uim, new BBox((app.width/2)+64, 32, 160, 320))
    .addOptions(ayleidRuinsAction.val_lbl_map)
    .bindAction(ayleidRuinsAction);


    xOff = (app.width/2)+64;
    yOff = (app.height/2)+64;
    xDim = 256;
    yDim = 32;

    //> keeping these as vars for future 'cancel' and other testing
    ConslogConfirmAction cfirmAct = new ConslogConfirmAction();
    UIConfirm cfirmWidget = UIConfirm.create(uim, new BBox(xOff, yOff, xDim, yDim), cfirmAct);
    
    cfirmWidget.setButtonLabelsΘ(
      "Click Here To Conslog Something.",
      "Sure Ya Wanna Conslog Something?",
      "You've Just Conslog'd Something!"
    )
    .setStyleProp("txt_size", Integer.class, 16)    
    ;


    yDim +=32;
    yOff +=48;

    class CancelConfirmAction implements IActionCallback {
      ConslogConfirmAction act;
      public CancelConfirmAction(ConslogConfirmAction iAct){act=iAct;}
      public void action(){act.cancel();}
    }

    UIClick.create(
      uim, new BBox(xOff, yOff, xDim, yDim),
      "Cancel Confirm Sequence", AppFont.TEXT,
      new CancelConfirmAction(cfirmAct)
    );

  
    /*=[ DON'T REMOVE THIS, NOR EVEN TOUCH IT ]===============================*/
    Cons.log("Function 'initGui' has completed.");
  }

  public void update(){uim.update();}

  public void onKeyPressed(){
    if(app.key=='q'||app.key=='Q'||app.keyCode==PApplet.ESC){app.exit(); return;}
    uim.onKeyPressed();
  }
  
  public void onMousePressed(){uim.onMousePressed();}
  
  public void onMouseWheel(int v){uim.onMouseWheel(v);}

  public void testRender(){
    renderBGCol();
    renderBGImg();
    uim.render();
    litebulbAction.renderLite();
  }

  private void renderBGCol(){
    switch(bgTheme){
      case LITE: app.background(COL_LITE); break;
      case DARK: app.background(COL_DARK); break;
      default:   app.background(COL_ERR); break;
    }
  }

  private void renderBGImg(){
    if(guiBgImg!=null && dispImg){
      switch(bgTheme){
        case LITE: app.tint(255, ALP_LITE); break;
        case DARK: app.tint(255, ALP_DARK); break;
        default:   app.tint(255, ALP_ERR); break;
      }
      app.imageMode(PApplet.CORNER);
      app.image(guiBgImg, 0, 0);
      app.tint(ALP_ERR); //> reset to full opaque J.I.C.
    }
  }


}

/*==============================================================================
|>>> TEST CALLBACK DEFINITIONS (COULD MOVE TO `PDE` CODE S.T. GITIGNORED)
+=============================================================================*/

class ConslogConfirmAction implements IConfirmAction {
  private static final String DEF_LOG_MSG = "You Confirmed An Action To Conslog This!";
  private ConfirmState curState;
  private String logMsg;

  public ConslogConfirmAction(){
    logMsg = DEF_LOG_MSG;
    curState = ConfirmState.ONINIT;
  }

  public ConslogConfirmAction(String inLogMsg){
    this();
    logMsg = inLogMsg;
  }

  public void action() {switch (curState) {
    case ONINIT: curState = ConfirmState.ONWARN; return;
    case ONWARN: curState = ConfirmState.ONDONE; doAction(); return;
    default: return; //> ONDONE action is nothing for now.
  }}

  private void doAction(){System.out.println(logMsg);}

  public void cancel(){curState = ConfirmState.ONINIT;}

  public ConfirmState getState(){return curState;}
}


class ExDDownAction implements ISelectAction {
  private String curSel;

  public String[][] val_lbl_map;
  public HashMap<String, String[]> loc_val_map;
  
  /** @todo use then CRUD a <code>json</code> file you fucking degenerate! :-) */
  public ExDDownAction(){
    val_lbl_map = new String[][]{
      {"ARP","Arpenia"},   {"ATA","Atatar"},       {"TEL","Telepe"},
      {"VEY","Veyond"},    {"WEL","Welke"},        {"NON","Nonungalo"},
      {"TAL","Talwinque"}, {"TRU","Trumbe"},       {"VAR","Varondo"},
      {"BEL","Beldaburo"}, {"NIR","Niryastare"},   {"ANG","Anga"},
      {"CEY","Ceyatatar"}, {"ELE","Elenglynn"},    {"LIN","Lindai"},
      {"NAR","Narfinsel"}, {"PIU","Piukanda"},     {"VIN","Vindasel"},
      {"WEN","Wendir"},    {"CUL","Culotte"},      {"NAG","Nagastani"},
      {"SER","Sercen"},    {"VIL","Vilverin"},     {"FAN","Fanacasecul"},
      {"NIN","Ninendava"}, {"RIE","Rielle"},       {"SED","Sedor"},
      {"HAM","Hame"},      {"MAC","Mackamentain"}, {"MAL","Malada"},
      {"OND","Ondo"},      {"VAH","Vahtacen"},     {"ANU","Anutwyll"},
      {"BAW","Bawn"},      {"MOR","Morahame"},     {"NEN","Nenalata"},
      {"MIS","Miscarcand"},{"NOR","Nornalhorst"},  {"SIL","Silorn"}
    };

    loc_val_map = new HashMap<String, String[]>();
      loc_val_map.put("Blackwood",          new String[]{"ARP","ATA","TEL","VEY","WEL"});
      loc_val_map.put("Colovian Highlands", new String[]{"NON","TAL","TRU","VAR"});
      loc_val_map.put("Gold Coast",         new String[]{"BEL","NIR"});
      loc_val_map.put("Great Forest",       new String[]{"ANG","CEY","ELE","LIN","NAR","PIU","VIN","WEN"});
      loc_val_map.put("Heartlands",         new String[]{"CUL","NAG","SER","VIL","FAN"});
      loc_val_map.put("Jerall Mountains",   new String[]{"NIN","RIE","SED"});
      loc_val_map.put("Nibenay Basin",      new String[]{"HAM","MAC","MAL","OND","VAH"});
      loc_val_map.put("Nibenay Valley",     new String[]{"ANU","BAW","MOR","NEN"});
      loc_val_map.put("West Weald",         new String[]{"MIS","NOR","SIL"});
  }

  public void OnSelection(String selOpt){
    if (selOpt!=null){curSel=selOpt;}
    Cons.log("Option "+StringUtils.wrapWith('"',selOpt)+" was just selected!");
  }

  public void curSelectionBlurbToConsole(){
    Cons.log(curSelectionBlurbToString());
  }

  public String curSelectionBlurbToString(){
    String pfix = "Selected: ";
    if(curSel==null){return pfix+"Nothing Yet?!?";}
    String ret = pfix + StringUtils.concatAsCSSV(curSel, siteNameWithID(curSel), regionNameWithID(curSel));
    return ret;
  }

  public String siteNameWithID(String siteID){
    for(String[] s : val_lbl_map){if(s[0].equals(siteID)){return s[1];}}
    return "N/A";
  }

  public String regionNameWithID(String siteID){
    Set<String> kList = loc_val_map.keySet();
    String [] buffSites;
    for(String r : kList){
      buffSites = loc_val_map.get(r);
      if(QueryUtils.strArrContainsStr(buffSites, siteID)){return r;}
    }
    return "N/A";
  }
}



class SimpleDropdownAction implements ISelectAction {
  private String curSelection = "N/A";
  
  public void OnSelection(String selOpt){
    if (selOpt!=null){curSelection=selOpt;}
    toConsole();
  }

  public void toConsole(){
    Cons.log(toString());
  }

  public String toString(){
    return "Current Selection = "+StringUtils.wrapWith('[',curSelection,']');
  }
}

class ToggleBGGrid implements IToggleCallback{
  TestGUIManager targetObj;
  public ToggleBGGrid(TestGUIManager tar){targetObj = tar;}
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

class AyledidRuinsCallback implements IUpdateCallback {
  ExDDownAction act;
  public AyledidRuinsCallback(ExDDownAction iAct){act=iAct;}
  public String getTxt(){return act.curSelectionBlurbToString();}
}

class ConslogAction implements IActionCallback {
  private String message;
  public ConslogAction(String iMsg){message=(iMsg==null?"":iMsg);}
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