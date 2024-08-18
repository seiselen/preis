package PrEis.gui;

import PrEis.utils.DataStructUtils;
import PrEis.utils.Pgfx;
import processing.core.PApplet;
import processing.core.PVector;

public class DropdownItem extends UIObject {
 
  private ILabelledActionCallback cback;
  
  public DropdownItem(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, WidgetType.DI);
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
  
  public DropdownItem appendAction(ILabelledActionCallback cb){
    cback = cb;
    return this;
  }
    
  public void scrollTransform(float val){
    addTranslate(DataStructUtils.createVector(0,val));
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
    Pgfx.rect(p, bbox);
    p.fill(getStrokeCol());
    renderText();
  }
  
  public void renderText(){
    p.textAlign(PApplet.LEFT, PApplet.CENTER);
    p.text(label, PApplet.lerp(bbox.minX(), bbox.midX(), style.txt_off_pct), bbox.midY());
  }

}