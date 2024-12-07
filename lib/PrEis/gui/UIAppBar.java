package PrEis.gui;

import PrEis.gui.UIImage.ImageSpecial;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.EiStrings;
import PrEis.utils.JAResourceUtil;
import PrEis.utils.PrEisRes;
import PrEis.utils.Cons.Act;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/** Encompasses Header <b>AND</b> Footer of standard <code>PrEis</code> app. */
public class UIAppBar extends UIObject {

  UIContainer tBar, bBar;

  private int     appBar_fill;
  private int     appBar_strk;
  private int     appBar_bord;
  private int     bbar_lbl_strk;
  private int     bbar_lbl_fill;
  private PosOri  bbar_fps_anch;
  private PVector bbar_fps_xOff;

  public UIAppBar(PApplet iPar, WidgetType iTyp) {
    super(iPar, new BBox(iPar.width, iPar.height), iTyp);
    label = EiStrings.CTAG_EISTECHS;
    initStyles();
    initComponents();
  }

  public static UIAppBar create(PApplet app){return new UIAppBar(app, WidgetType.NA);}
  public static UIAppBar create(UIManager mgr){return create(mgr.app).bindManager(mgr).castTo(UIAppBar.class);}

  public UIAppBar bindAppLogoΘ(PImage logoImg, PVector logoDim){bindAppLogo(logoImg, logoDim); return this;}
  public void bindAppLogo(PImage logoImg, PVector logoDim){
    if(tBar==null){Cons.err("Attempted to bind app logo before initializing top bar"); Cons.act(Act.RETURN_NO_ACTION);}
    if(logoDim==null){logoDim = new PVector(logoImg.width, logoImg.height);}
    tBar.addChild(UIImage.create(app, new BBox(0, 0, logoDim.x, logoDim.y), logoImg)
    .asΘ(ImageSpecial.SCALE_TO_FIT)
    );
  }

  public UIAppBar setCTAGΘ(String newCTag){setCTAG(newCTag); return this;}
  public void setCTAG(String newCTag){if(newCTag!=null && !newCTag.isEmpty()){label=newCTag;}}


  private void initStyles(){
    appBar_fill   = app.color(32,96,224);
    appBar_strk   = app.color(255);
    appBar_bord   = 0;
    bbar_lbl_strk = app.color(255,0);
    bbar_lbl_fill = app.color(255,0);
    bbar_fps_anch = PosOri.LFT;
    bbar_fps_xOff = new PVector(8,0);
  }


  public void initComponents(){
    tBar = UIContainer.create(app, new BBox(0, 0, app.width, 64)).addChildren(
      UIConfirm.create(app, new BBox(app.width-160,0,160,64), new AppQuitAction(app))
      .setButtonLabelsΘ(EiStrings.EXIT_BTN_INIT, EiStrings.EXIT_BTN_WARN, EiStrings.EXIT_BTN_DONE)
      .setTitle(EiStrings.EXIT_BTN_LABL)
    )
    .setStyleProp("border_radius", Integer.class, appBar_bord)
    .setStyleProp("fill", Integer.class, appBar_fill)
    .setStyleProp("strk_enabled", Integer.class, appBar_strk)
    .castTo(UIContainer.class)
    ;

    bBar = UIContainer.create(app, new BBox(0, app.height-32, app.width, 32)).addChildren(
      UIImage.create(app, new BBox(app.width-256, 1, 32, 32), JAResourceUtil.getImageFromJAR(PrEisRes.EIS_LOGO))
      .asΘ(ImageSpecial.SCALE_TO_FIT),

      UILabel.create(app, new BBox(app.width-224, 0, 224, 32), label, AppFont.TEXT, LabelType.TP, null)
      .setStyleProp("strk_transp", Integer.class, bbar_lbl_strk)
      .setStyleProp("fill_transp", Integer.class, bbar_lbl_fill),

      UILabel.create(app, new BBox(0, 0, 224, 32), null, AppFont.TEXT, LabelType.TP, new FPSAppBarUpdate(app))
      .setStyleProp("strk_transp", Integer.class, bbar_lbl_strk)
      .setStyleProp("fill_transp", Integer.class, bbar_lbl_fill)
      .setStyleProp("txt_anchor", PosOri.class, bbar_fps_anch)
      .setStyleProp("txt_offset", PVector.class, bbar_fps_xOff)      
    )
    .setStyleProp("border_radius", Integer.class, appBar_bord)
    .setStyleProp("fill", Integer.class, appBar_fill)
    .setStyleProp("strk_enabled", Integer.class, appBar_strk)
    .castTo(UIContainer.class)
    ;
  }

  public void update()         {tBar.update(); bBar.update();}
  public void onMousePressed() {tBar.onMousePressed(); bBar.onMousePressed();}
  public void render()         {tBar.render(); bBar.render();}
  public void lateRender()     {tBar.lateRender(); bBar.lateRender();}
}

class AppQuitAction implements IConfirmAction {
  private PApplet app;
  private ConfirmState cs;
  public AppQuitAction(PApplet iApp){app = iApp; cs = ConfirmState.ONINIT;}
  public void cancel(){cs = ConfirmState.ONINIT;}
  public ConfirmState getState(){return cs;}
  public void doAction(){app.exit();}
  public void action(){switch (cs){
    case ONINIT: cs = ConfirmState.ONWARN; return;
    case ONWARN: cs = ConfirmState.ONDONE; doAction(); return;
    default: return;
  }}
}

class FPSAppBarUpdate implements IUpdateCallback {
  private PApplet app;
  public FPSAppBarUpdate(PApplet iApp){app = iApp;}  
  public String getTxt() {return "FPS: ("+PApplet.nfc(app.frameRate,2)+")";}
}