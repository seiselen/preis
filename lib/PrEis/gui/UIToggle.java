package PrEis.gui;
import PrEis.utils.BBox;
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

      if(labelT!=null && labelF!=null){
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
    p.textSize(style.txt_size);
    p.strokeWeight(style.swgt);
    p.fill(getFillCol());
    p.stroke(getStrokeCol());
    renderRect();
    p.fill(getStrokeCol());
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