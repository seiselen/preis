package PrEis.gui;

import PrEis.gui.Enums.AppFont;
import PrEis.gui.Interfaces.ActionCallback;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * Click Button
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 */
class ClickButton extends UIObject {
  
  private ActionCallback action;
  
  public ClickButton(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, UIObject.Type.CB);
  }
  
  public ClickButton setLabel(String iLbl){
    label=iLbl; return this;
  }
  
  public ClickButton setTitle(String ittl){
    title = ittl;
    return this;
  }
  
  public ClickButton setFont(AppFont iFnt){
    objFont = iFnt;
    onSetFont();
    return this;
  }  
  
  public ClickButton bindCallback(ActionCallback act){
    action = act;
    return this;
  }
  
  public void onMousePressed(){
    if(mouseOver&&action!=null){action.action();}
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
    return isDisabledState() ? style.fill_disabled : isClickedState() ? style.fill_clicked : isHoveredState() ? style.fill_hovered : style.fill;
  } 
  
  private int getStrokeCol(){
    return isDisabledState() ? style.strk_disabled : style.strk_enabled;
  }
}