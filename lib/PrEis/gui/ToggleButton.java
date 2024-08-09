package PrEis.gui;
import PrEis.gui.Interfaces.ToggleCallback;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * Toggle Button
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 */
public class ToggleButton extends UIObject {
  
  private ToggleCallback toggleCallback;
  
  public ToggleButton(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, WidgetType.TB);
  }
  
  public ToggleButton setLabel(String iLbl){
    label = iLbl;
    return this;
  }
  
  public ToggleButton setTitle(String ittl){
    title = ittl;
    return this;
  }
  
  public ToggleButton setFont(AppFont iFnt){
    objFont = iFnt;
    onSetFont();
    return this;
  }
  
  public ToggleButton bindCallback(ToggleCallback cbk){
    toggleCallback=cbk;
    return this;
  }
  
  public void onMousePressed(){
    if(mouseOver){
      toggleCallback.toggleState();
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