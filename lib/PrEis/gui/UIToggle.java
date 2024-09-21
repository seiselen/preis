package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.Cons.Err;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * Toggle Button
 */
public class UIToggle extends UIObject {
  
  private IToggleCallback toggleCallback;
  private String labelT, labelF;
  
  public UIToggle(PApplet iApp, BBox iBox){
    super(iApp, iBox, WidgetType.TB);
  }

  public UIToggle(PApplet iApp, PVector iPos, PVector iDim){
    this(iApp,new BBox(iPos));
  }

  public static UIToggle create(PApplet app, BBox box, String txt, AppFont fnt, IToggleCallback cbk){
    switch(fnt){
      case TEXT  : return new UIToggle(app,box).withLabel(txt).bindCallback(cbk);
      case GLYPH : return new UIToggle(app,box).withGlyph(txt).bindCallback(cbk);
      default    : Cons.err(Err.SWITCH_DROP_OUT); return null;
    }
  }

  public static UIToggle create(PApplet app, PVector pos, PVector dim, String txt, AppFont fnt, IToggleCallback cbk){
    return create(app, new BBox(pos, dim), txt, fnt, cbk);
  }

  public static UIToggle create(UIManager mgr, BBox box, String txt, AppFont fnt, IToggleCallback cbk){
    return create(mgr.app, box, txt, fnt, cbk).bindManager(mgr);
  }

  public static UIToggle create(UIManager mgr, PVector pos, PVector dim, String txt, AppFont fnt, IToggleCallback cbk){
    return create(mgr.app, pos, dim, txt, fnt, cbk).bindManager(mgr);
  }
  
  public UIToggle bindCallback(IToggleCallback cbk){
    toggleCallback=cbk;
    return this;
  }
  
  public UIToggle withLabel(String iLabel){return (UIToggle)super.withLabel(iLabel);}

  public UIToggle withOnOffLabels(String lblT, String lblF){labelT=lblT; labelF=lblF; return this;}

  public UIToggle withGlyph(String iGlyph){return (UIToggle)super.withGlyph(iGlyph);}  

  public UIToggle bindManager(UIManager iMgr){return (UIToggle)super.bindManager(iMgr);}

  public void onMousePressed(){
    if(mouseOver){
      toggleCallback.toggleState();

      if(objFont==AppFont.TEXT && labelT!=null && labelF!=null){
        label = toggleCallback.getState() ? labelT : labelF;
      }

      if(title!=null){/* STUB FOR FUTURE TOOLTIP HANDLING */}
    }
  }
  
  public void update(){
    super.update();
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
  
  private int getFillCol(){
    if(toggleCallback.getState()){
      return isDisabledState() ? style.fill_on_disabled : isClickedState() ? style.fill_on_clicked : isHoveredState() ? style.fill_on_hovered : style.fill_on;} else{return isDisabledState() ? style.fill_off_disabled : isClickedState() ? style.fill_off_clicked : isHoveredState() ? style.fill_off_hovered : style.fill_off;
    }
  }
  
  private int getStrokeCol(){
    return isDisabledState() ? style.strk_disabled : style.strk_enabled;
  }
}