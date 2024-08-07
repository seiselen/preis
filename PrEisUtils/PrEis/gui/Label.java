package PrEis.gui;

import PrEis.gui.Enums.AppFont;
import PrEis.gui.Enums.LabelType;
import PrEis.gui.Interfaces.UpdateCallback;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * Label
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 */
public class Label extends UIObject {
  
  private UpdateCallback updateCallback;
  
  private LabelType lblType;
  
  public Label(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, UIObject.Type.LB);
  }
  
  public Label setLabel(String iLbl){
    label = iLbl;
    return this;
  }
  
  public Label setFont(AppFont iFnt){
    objFont = iFnt;
    onSetFont();
    return this;
  }
  
  public Label setType(LabelType iTyp){
    lblType = iTyp;
    return this;
  }
  
  public Label bindCallback(UpdateCallback cbk){
    updateCallback=cbk;
    label=updateCallback.getTxt();
    return this;
  }
  
  public void update(){
    if(updateCallback!=null){label = updateCallback.getTxt();}
  }
  
  public void render(){
    super.render();
    p.textSize(style.txt_size);
    p.strokeWeight(style.swgt);
    p.fill(getFillCol());
    p.stroke(getStrokeCol());
    p.rect(pos.x,pos.y,dim.x,dim.y);
    p.fill(getTxtFillCol());
    renderTextViaOri();
  }
  
  private int getFillCol(){
    switch(lblType){case OP: return style.fill_opaque; case TP: default: return style.fill_transp;}
  }
  
  private int getStrokeCol(){
    switch(lblType){case OP: return style.strk_opaque; case TP: default: return style.strk_transp;}
  }
  
  private int getTxtFillCol(){
    switch(lblType){case OP: return style.strk_opaque; case TP: default: return style.fill_txt;}
  }
}