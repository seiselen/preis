package PrEis.gui;

import PrEis.gui.UIImage.ImageSpecial;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.JAResourceUtil;
import PrEis.utils.PrEisRes;
import PrEis.utils.Cons.Act;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/** Encompasses Header <b>AND</b> Footer of standard <code>PrEis</code> app. */
public class UIAppBar extends UIObject {
  private final String XBTN_INIT = "EXIT APP";
  private final String XBTN_WARN = "EXIT ?!?";
  private final String XBTN_DONE = "EXITING!";

  UIContainer tBar, bBar;
  
  public UIAppBar(
    PApplet iPar,
    WidgetType iTyp
    ) {
    super(iPar, new BBox(iPar.width, iPar.height), iTyp);
    label = PrEisRes.EIS_CTAG.get();
    initComponents();
  }

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

  public void initComponents(){
    tBar = UIContainer.create(app, new BBox(0, 0, app.width, 64)).addChildren(
      UIConfirm.create(app, new BBox(app.width-160,0,160,64), new AppQuitAction(app))
      .setButtonLabelsΘ(XBTN_INIT, XBTN_WARN, XBTN_DONE)
      .setTitle("Click Twice To Exit App")
    )
    .setStyleProp("fill", Integer.class, app.color(32,96,224))
    .setStyleProp("strk_enabled", Integer.class, app.color(255))
    .castTo(UIContainer.class)
    ;

    bBar = UIContainer.create(app, new BBox(0, app.height-32, app.width, 32)).addChildren(
      UIImage.create(app, new BBox(app.width-256, 1, 32, 32), JAResourceUtil.getImageFromJAR(PrEisRes.EIS_LOGO))
      .asΘ(ImageSpecial.SCALE_TO_FIT),

      UILabel.create(
        app, new BBox(app.width-224, 0, 224, 32),
        label, AppFont.TEXT, LabelType.TP, null
      )
      .setStyleProp("strk_transp", Integer.class, app.color(255,0))
      .setStyleProp("fill_transp", Integer.class, app.color(255,0)),

      UILabel.create(
        app, new BBox(0, 0, 224, 32),
        null, AppFont.TEXT, LabelType.TP, new FPSAppBarUpdate(app)
      )
      .setStyleProp("txt_anchor", PosOri.class, PosOri.LFT)
      .setStyleProp("txt_offset", PVector.class, new PVector(8,0))      
      .setStyleProp("strk_transp", Integer.class, app.color(255,0))
      .setStyleProp("fill_transp", Integer.class, app.color(255,0))
    )
    .setStyleProp("border_radius", Integer.class, 0)
    .setStyleProp("fill", Integer.class, app.color(32,96,224))
    .setStyleProp("strk_enabled", Integer.class, app.color(255))
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