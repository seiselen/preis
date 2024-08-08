package PrEis.gui;

import PrEis.gui.Interfaces.LabelledActionCallback;
import PrEis.utils.DataStructUtils;
import processing.core.PApplet;
import processing.core.PVector;

public class DropdownItem extends UIObject {
 
  private LabelledActionCallback cback;
  
  public DropdownItem(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp,iPos,iDim,UIObject.Type.DI);
    value = "";
  }

  public DropdownItem appendValue(String v){
    value = v;
    if(label.length()==0){label=value;}
    return this;
  }
  
  public DropdownItem appendValue(String v, String l){
    value = v;
    label = l;
    return this;
  }
  
  public DropdownItem appendAction(LabelledActionCallback cb){
    cback = cb;
    return this;
  }
    
  public void scrollTransform(float val){
    setTransform(PVector.add(pos,DataStructUtils.createVector(0,val)),dim);
  }
  
  private int getFillCol(){
    return isDisabledState() ? style.fill_disabled : isClickedState() ? style.fill_clicked : isHoveredState() ? style.fill_hovered : style.fill;
  } 
  
  private int getStrokeCol(){
    return isDisabledState() ? style.strk_disabled : style.strk_enabled;
  }

  public void onMousePressed(){
    if(mouseOver && cback!=null){cback.action();}
  }
  
  public void update(){
    super.update();
  }
  
  public void render(){
    p.textSize(style.txt_size);
    p.strokeWeight(style.swgt);
    p.fill(getFillCol());
    p.stroke(getStrokeCol());
    p.rect(pos.x,pos.y,dim.x,dim.y);
    p.fill(getStrokeCol());
    renderText();
  }
  
  public void renderText(){
    p.textAlign(PApplet.LEFT, PApplet.CENTER);
    p.text(label, PApplet.lerp(pos.x,mPt.x,style.txt_off_pct), mPt.y);
  }

}