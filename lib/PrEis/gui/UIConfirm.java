package PrEis.gui;

import PrEis.utils.BBox;
import PrEis.utils.QueryUtils;
import processing.core.PApplet;
import processing.core.PVector;

public class UIConfirm extends UIObject {

  private static final String DEF_ON_INIT_STR = "Click To Proceed";
  private static final String DEF_ON_WARN_STR = "Click To Confirm";  
  private static final String DEF_ON_DONE_STR = "Action Performed";
  private String lblOnInit;
  private String lblOnWarn;
  private String lblOnDone;

  private IConfirmAction cfirm;
  
  public UIConfirm(PApplet iApp, BBox iBox){
    super(iApp, iBox, WidgetType.FB);
    lblOnInit = DEF_ON_INIT_STR;
    lblOnWarn = DEF_ON_WARN_STR;
    lblOnDone = DEF_ON_DONE_STR;
  }

  public UIConfirm(PApplet iApp, PVector iPos, PVector iDim){
    this(iApp, new BBox(iPos, iDim));
  }

  /** @implNote calls {@link #withLabel} with null arg to init font state */
  public static UIConfirm create(PApplet app, BBox box, IConfirmAction cbk){
    return new UIConfirm(app,box).withLabel(null).castTo(UIConfirm.class).bindCallbackΘ(cbk);
  }

  public static UIConfirm create(PApplet app, PVector pos, PVector dim, IConfirmAction cbk){
    return create(app, new BBox(pos, dim), cbk);
  }

  public static UIConfirm create(UIManager mgr, BBox box, IConfirmAction cbk){
    return create(mgr.app, box, cbk).bindManager(mgr).castTo(UIConfirm.class);
  }

  public static UIConfirm create(UIManager mgr, PVector pos, PVector dim, IConfirmAction cbk){
    return create(mgr.app, pos, dim, cbk).bindManager(mgr).castTo(UIConfirm.class);
  }

  public void setButtonLabels(String inOnInit, String inOnWarn, String inOnDone){
    if(inOnInit!=null){lblOnInit=inOnInit;}
    if(inOnWarn!=null){lblOnWarn=inOnWarn;}
    if(inOnDone!=null){lblOnDone=inOnDone;}
  }

  public UIConfirm setButtonLabelsΘ(String inOnInit, String inOnWarn, String inOnDone){
    setButtonLabels(inOnInit, inOnWarn, inOnDone);
    return this;
  }

  public void bindCallback(IConfirmAction inCfirmAct){
    if(inCfirmAct!=null){cfirm=inCfirmAct;}
  }

  public UIConfirm bindCallbackΘ(IConfirmAction inCfirmAct){
    bindCallback(inCfirmAct);
    return this;
  }


  public void onMousePressed(){
    if(mouseOver && cfirm!=null){cfirm.action();}
  }

  public void update(){
    super.update();
    getAppropriateLabel();
  }



  private void getAppropriateLabel(){
    if(QueryUtils.nullAny(lblOnInit,lblOnWarn,cfirm)){return;}
    switch (cfirm.getState()) {
      case ONINIT: label=lblOnInit; return;
      case ONWARN: label=lblOnWarn; return;
      case ONDONE: label=lblOnDone; return;
    }
  }

  /** @implNote <code>{ONWARN,ONDONE}</code> share the same styles for now */
  private int getFillCol(){
    switch (cfirm.getState()) {
      case ONWARN:
      case ONDONE: return 
        isClickedState() ? style.fill_off_clicked :
        isHoveredState() ? style.fill_off_hovered : style.fill_off;
      case ONINIT: return
        isClickedState() ? style.fill_on_clicked :
        isHoveredState() ? style.fill_on_hovered : style.fill_on;
      default:
        return DefaultStyle.ERR_COL;
    }
  }
  
  private int getStrokeCol(){
    return style.strk_enabled;
  }


  public void render(){
    super.render();
    app.textSize(style.txt_size);
    app.strokeWeight(style.swgt);
    app.fill(getFillCol());
    app.stroke(getStrokeCol());
    renderRect();
    app.fill(getStrokeCol());
    renderTextViaOri();
    if(mouseOver&&title!=null){/* STUB FOR FUTURE TOOLTIP HANDLING */}
  }


}