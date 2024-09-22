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
import PrEis.gui.PosOri;
import PrEis.gui.UIClick;
import PrEis.gui.UIContainer;
import PrEis.gui.UIDropdown;
import PrEis.gui.UIImage;
import PrEis.gui.UILabel;
import PrEis.gui.UIManager;
import PrEis.gui.UIObject;
import PrEis.gui.UIToggle;
import PrEis.gui.UIImage.ImageSpecial;
import PrEis.gui.UIConfirm;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.DataStructUtils;
import PrEis.utils.FileSysUtils;
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

  private enum Do {SKIP,RUN};

  private BGThMode bgTheme = BGThMode.LITE;
  private int BG_IMG_ALPHA = 160;

  private int COL_ERR, COL_LITE, COL_DARK;


  private Testbed   app;
  private UIManager uim;
  private PImage    bgImgLite;
  private PImage    bgImgDark;  
  private boolean dispImg = true;
  private IntDict  glyphDict;
  private HashMap<TestAssetKey,PImage> diagImg;

  PFont textFont;
  PFont glyphFont;
  JSONObject glyphCodes;

  ConslogAction  conslogAction;
  ToggAbleAction toggAbleAction;
  LitebulbAction litebulbAction;
  MousePosUpdate mousePosUpdate;
  ToggleBGGrid   toggleBGGrid;

   
  /** @implNote Call Order <b>COUNTS</b>! */
  public TestGUIManager(Testbed p){
    app = p;
    uim = new UIManager(app);
    diagImg = new HashMap<TestAssetKey,PImage>();
    loadFonts();
    loadImages();
    uim.injectFonts(textFont, glyphFont);
    initCustomGlyphs();
    initGUI();
    initMiscGFX();
  }

  private void loadFonts(){
    try {
      textFont  = app.loadFont(Testbed.getPathOf(TestAssetKey.TEXT_FONT));
      glyphFont = app.loadFont(Testbed.getPathOf(TestAssetKey.GLYPH_FONT));
      glyphCodes = app.loadJSONObject(Testbed.getPathOf(TestAssetKey.GLYPH_CODES));    
    } catch (Exception e) {
      System.err.println("Issue fetching one or more Font assets. Check that filepaths are correct.");
      e.printStackTrace();
      app.exit();
      return;
    }
  }


  private void loadDiagImg(TestAssetKey pkid, String path, TestAssetKey fn){
    String fpath = FileSysUtils.pathConcat(path,fn.get());
    //System.out.println(fpath);
    diagImg.put(pkid, app.loadImage(fpath));
  }

  private void loadImages(){
    try{
      bgImgLite  = app.loadImage(Testbed.getPathOf(TestAssetKey.GUI_BG_IMG_LITE));
      bgImgDark  = app.loadImage(Testbed.getPathOf(TestAssetKey.GUI_BG_IMG_DARK));

      String diagImgPath = Testbed.getPathOf(TestAssetKey.DIAG_IMG_PFIX);

      loadDiagImg(TestAssetKey.DIMG_32X32   , diagImgPath , TestAssetKey.DIMG_32X32  );
      loadDiagImg(TestAssetKey.DIMG_32X64   , diagImgPath , TestAssetKey.DIMG_32X64  );
      loadDiagImg(TestAssetKey.DIMG_64X32   , diagImgPath , TestAssetKey.DIMG_64X32  );
      loadDiagImg(TestAssetKey.DIMG_64X64   , diagImgPath , TestAssetKey.DIMG_64X64  );
      loadDiagImg(TestAssetKey.DIMG_128X64  , diagImgPath , TestAssetKey.DIMG_128X64 );
      loadDiagImg(TestAssetKey.DIMG_64X128  , diagImgPath , TestAssetKey.DIMG_64X128 );
      loadDiagImg(TestAssetKey.DIMG_128X128 , diagImgPath , TestAssetKey.DIMG_128X128);
    }
    catch (Exception e){
      System.err.println("Issue fetching one or more Image assets. Check that filepaths are correct.");
      e.printStackTrace();
      app.exit();
      return;
    }
  }

  public TestGUIManager Dark(){bgTheme=BGThMode.DARK; return this;}
  public TestGUIManager Lite(){bgTheme=BGThMode.LITE; return this;}

  public void initMiscGFX(){
    COL_ERR  = app.color(255,0,255);
    COL_LITE = app.color(255);
    COL_DARK = app.color(0);
  }

  private void initCustomGlyphs(){
    glyphDict = new IntDict();
    String[] keys = DataStructUtils.keyArrayOfJSONObj(glyphCodes);
    for (String k : keys){glyphDict.add(k,glyphCodes.getInt(k));}
  }


  public void toggleDispBGImage(){dispImg = !dispImg;}

  public boolean getDispBGImage(){return dispImg;}

  private String glyphChar(String n){return ""+(char)glyphDict.get(n);}

  private BBox box(float x, float y, float w, float t){return new BBox(x,y,w,t);}

  public void initGUI(){
    mousePosUpdate = new MousePosUpdate(app);

    test_Tooltips_01(Do.RUN);
    test_Image_01(Do.SKIP);
    test_misc_01(Do.SKIP);
    test_Container_01(Do.SKIP);
    test_Container_02(Do.SKIP);
    test_Dropdown_01(Do.SKIP);
    test_Confirm_01(Do.SKIP);
    /*=[ DON'T REMOVE THIS, NOR EVEN TOUCH IT ]===============================*/
    Cons.log("Function 'initGui' has completed.");
  }


  private void test_Tooltips_01(Do d){
    if(d==Do.SKIP){return;}

    int xd = 256;
    int yd = 64;
    int xo = 64;
    int yo = 64;
    int xs = xd+32;
    int ys = yd+128;


    UIClick.create(uim, new BBox(16, yo, xd, yd), "Test Click Button", AppFont.TEXT, new ConslogAction("HELLO CONSOLE!"))
    .setTitle("I am a UIClick!")
    ;

    UIClick.create(uim, new BBox(992, yo, xd, yd), "Test Click Button", AppFont.TEXT, new ConslogAction("HELLO CONSOLE!"))
    .setTitle("I am a UIClick!")
    ;

    UIClick.create(uim, new BBox(640, 688, xd, yd), "Test Click Button", AppFont.TEXT, new ConslogAction("HELLO CONSOLE!"))
    .setTitle("I am a UIClick!")
    ;

    UIClick.create(uim, new BBox(1024, 688, xd, yd), "Test Click Button", AppFont.TEXT, new ConslogAction("HELLO CONSOLE!"))
    .setTitle("I am a UIClick!")
    ;

    UIToggle.create(uim, new BBox(xo+=xs, yo, xd, yd), "Toggle Example", AppFont.TEXT, new StubToggleAct())
    .withOnOffLabels("Toggle [ON]", "Toggle [OFF]")
    .setTitle("I am a UIToggle!")
    ;

    UILabel.create(uim, new BBox(xo+=xs, yo, xd, yd), null, AppFont.TEXT, LabelType.OP, mousePosUpdate)
    .setTitle("I am a UILabel!")
    ;

    UIImage.create(uim, box(xo+=xs, ys, xd,yd), diagImg.get(TestAssetKey.DIMG_128X64))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ()
    .setTitle("I am a UIImage!")
    ;


  }



  private void test_Image_01(Do d){
    if(d==Do.SKIP){return;}

    int xd = 128;
    int yd = 128;
    int xo = 64;
    int yo = 32;
    int xs = xd+32;
    int ys = yd+32;

    UIImage.create(uim, box(xo,     yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X32))
    .doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X64))
    .doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X32))
    .doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X64))
    .doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X128))
    .doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X64))
    .doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X128))
    .doDebugVizΘ();

    yo += ys;
    xo = 64;

    UIImage.create(uim, box(xo,     yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X32))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X64))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X32))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X64))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X128))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X64))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X128))
    .asΘ(ImageSpecial.STRETCH_TO_FIT).doDebugVizΘ(); 

    yo += ys;
    xo = 64;
    yd = 64;

    UIImage.create(uim, box(xo,     yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X32))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X32))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X128))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X128))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ(); 

    yo += ys-64;
    xo = 64;
    xd = 64;
    yd = 128;

    UIImage.create(uim, box(xo,     yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X32))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X32))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X128))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X128))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ(); 

    yo += ys;
    xo = 64;
    xd = 128;
    yd = 128;

    UIImage.create(uim, box(xo,     yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X32))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_32X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X32))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_64X128))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X64))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ();
    UIImage.create(uim, box(xo+=xs, yo, xd,yd), diagImg.get(TestAssetKey.DIMG_128X128))
    .asΘ(ImageSpecial.SCALE_TO_FIT).doDebugVizΘ(); 



  }






  private void test_misc_01(Do d){
    if(d==Do.SKIP){return;}

    litebulbAction = new LitebulbAction(app, DataStructUtils.vec2(352, 192));
    
    int xOff = 64;
    int yOff = 64;

    UIClick.create(
      uim,
      new BBox(xOff, yOff, 256, 64),
      "Test Click Button",
      AppFont.TEXT,
      new ConslogAction("HELLO CONSOLE!")
    );

    UIToggle.create(uim, new BBox(xOff, yOff+=96, 256, 64), "Light Bulb", AppFont.TEXT, litebulbAction)
    .withOnOffLabels("Lightbulb [ON]", "Lightbulb [OFF]");

    UILabel.create(uim, new BBox(xOff, yOff+=96, 256, 64), null, AppFont.TEXT, LabelType.OP, mousePosUpdate);

    UIClick needsToBeVar = UIClick.create(uim, new BBox(xOff, yOff+=96, 256, 64), "I Am Enabled!", AppFont.TEXT, conslogAction)
    .setDisabledΘ(true).withDLabel("I Am Disabled!").castTo(UIClick.class);

    toggAbleAction = new ToggAbleAction(needsToBeVar);
    UIClick.create(uim, new BBox(xOff, yOff+=96, 320, 64), "(En/Dis)able Above Button", AppFont.TEXT, toggAbleAction);

  }


  private void test_Container_01(Do d){
    if(d==Do.SKIP){return;}

    UIContainer.create(uim, new BBox(416, 64, 160, 128)).addChildren(
      UILabel.create(app, new BBox(0, 0, 160, 32), "Toggle BG Image", AppFont.TEXT, LabelType.OP, null)
      .setStyleProp("txt_size", Integer.class, 18),
      UIToggle.create(app, new BBox(32, 48, 96, 64), glyphChar("gridish"), AppFont.GLYPH, new ToggleBGGrid(this))
    ).toggleShowBounds();
  }



  private void test_Container_02(Do d){
    if(d==Do.SKIP){return;}

    int xOff = 0;
    int yOff = 0; 
    int xDim = 64;
    int yDim = 32;

    UIContainer.create(uim, new BBox(64, 512+32, 512, 192))
    .addChildren(

    UILabel.create(
      app,
      new BBox(xOff, yOff, 176, 32),
      "This is a UI Container!", 
      AppFont.TEXT, LabelType.OP, null)
    .setStyleProp("txt_size", Integer.class, 16)
    .setStyleProp("strk_opaque", Integer.class, app.color(0))
    .setStyleProp("txt_anchor", PosOri.class, PosOri.LFT)
    .setStyleProp("txt_offset", PVector.class, new PVector(8,0))
    ,


      UIClick.create(app, new BBox(xOff+=32, yOff+=64, xDim, yDim           ), "MER", AppFont.TEXT, new ConslogAction("Mercury")),
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
  }


  private void test_Dropdown_01(Do d){
    if(d==Do.SKIP){return;}

    ExDDownAction ayleidRuinsAction = new ExDDownAction();
    AyledidRuinsCallback cb = new AyledidRuinsCallback(ayleidRuinsAction);

    UILabel.create(
      uim, new BBox(416, 352, 384, 32), null, AppFont.TEXT, LabelType.OP, cb)
    .setStyleProp("txt_size", Integer.class, 16);
    
    @SuppressWarnings("unused")
    UIDropdown ddown = UIDropdown.create(uim, new BBox(640, 416, 160, 320))
    .addOptions(ayleidRuinsAction.val_lbl_map)
    .bindAction(ayleidRuinsAction);
  }

  private void test_Confirm_01(Do d){
    if(d==Do.SKIP){return;}

    //> keeping these as vars for future 'cancel' and other testing
    ConslogConfirmAction cfirmAct = new ConslogConfirmAction();
    
    @SuppressWarnings("unused")
    UIConfirm cfirmWidget = UIConfirm.create(uim, new BBox(416, 224, 320, 32), cfirmAct)
    .setButtonLabelsΘ(
      "Click Here To Conslog Something.",
      "Sure Ya Wanna Conslog Something?",
      "You've Just Conslog'd Something!"
    )
    .setStyleProp("txt_size", Integer.class, 16)
    .castTo(UIConfirm.class) 
    ;

    class CancelConfirmAction implements IActionCallback {
      ConslogConfirmAction act;
      public CancelConfirmAction(ConslogConfirmAction iAct){act=iAct;}
      public void action(){act.cancel();}
    }

    UIClick.create(uim, new BBox(416, 288, 320, 32),
      "Click Here To Cancel Confirm Seq.", AppFont.TEXT,
      new CancelConfirmAction(cfirmAct)
    )
    .setStyleProp("txt_size", Integer.class, 16)    
    ;
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
    if(litebulbAction!=null){litebulbAction.renderLite();}
  }

  private void renderBGCol(){
    switch(bgTheme){
      case LITE: app.background(COL_LITE); break;
      case DARK: app.background(COL_DARK); break;
      default:   app.background(COL_ERR); break;
    }
  }

  private void renderBGImg(){
    app.imageMode(PApplet.CORNER);
    if(dispImg){
      if(BG_IMG_ALPHA<255){app.tint(BG_IMG_ALPHA);}
      switch(bgTheme){
        case LITE: app.image(bgImgLite, 0, 0); break;
        case DARK: app.image(bgImgDark, 0, 0); break;
        default: break;
      }
      if(BG_IMG_ALPHA<255){app.tint(255);}
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
    String pfix = "DDown Selection: ";
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

class StubToggleAct implements IToggleCallback {
  private boolean state;
  public StubToggleAct(){;}
  public boolean getState() {return state;}
  public void toggleState() {state = !state;}
}

class LitebulbAction implements IToggleCallback {
  boolean liteIsOn;
  private int nContours = 8;
  private int minDiam   = 4;
  private int maxDiam   = 52;
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