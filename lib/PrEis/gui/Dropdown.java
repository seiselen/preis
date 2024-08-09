package PrEis.gui;

import PrEis.gui.Interfaces.LabelledActionCallback;
import PrEis.utils.DataStructUtils;
import processing.core.PApplet;
import processing.core.PVector;

public class Dropdown extends UIObject {

  //> @TODO: these should be spec'd by UIStyles
  private float scrollFactor;
  private float scrollOff;
  private float maxScrollOff;
  private float nScrollOff;

  private float ddownItemWide;
  private float ddownItemTall; 

  private DropdownItem[] children;
 
  public Dropdown(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, WidgetType.DD);
    scrollOff=0;
    maxScrollOff=0;
    scrollFactor=16;
    //> TEMPORARY INIT VALS, REFINE LATER
    setItemDims(iDim.x, 32);
  }

  public Dropdown setItemDims(float wide, float tall){
    ddownItemWide = wide;
    ddownItemTall = tall;
    return this;
  }
  
  //> keeping this version for future generalized version within UIObjects
  public Dropdown appendChildren(String[] childValues){
    children = new DropdownItem[childValues.length];
    
    float xOff=pos.x+(dim.x-ddownItemWide)/2;
    float yOff=pos.y;
    
    maxScrollOff = (children.length*ddownItemTall)-dim.y;
    
    for (int i=0; i<childValues.length; i++){
      children[i] = new DropdownItem(p,DataStructUtils.createVector(xOff,yOff), DataStructUtils.createVector(ddownItemWide,ddownItemTall))
      .appendValue(childValues[i])
      .appendAction(null);
      yOff+=ddownItemTall;
    }

    return this;
  }

  public Dropdown appendChildren(LabelledActionCallback[] actions){
    children = new DropdownItem[actions.length];
    
    float xOff=pos.x+(dim.x-ddownItemWide)/2;
    float yOff=pos.y;
    
    maxScrollOff = (children.length*ddownItemTall)-dim.y;
    
    for (int i=0; i<actions.length; i++){
      children[i] = new DropdownItem(p,DataStructUtils.createVector(xOff,yOff), DataStructUtils.createVector(ddownItemWide,ddownItemTall))
      .appendValue(actions[i].getLabel())
      .appendAction(actions[i]);
      yOff+=ddownItemTall;
    }
    
    return this;
  }


  
  public void update(){
    for(DropdownItem ddi : children){ddi.update();}
  }
 
  public void onMouseWheel(int v){
    nScrollOff = scrollOff+(v*scrollFactor);         
    if(nScrollOff<0||nScrollOff>maxScrollOff){return;}
    scrollOff=nScrollOff;
    for(DropdownItem ddi : children){ddi.scrollTransform(v*-scrollFactor);}
  }
  
  public void onMousePressed(){
    for(DropdownItem ddi : children){if(ddi.pos.y < ePt.y){ddi.onMousePressed();}}
  }

  public void render(){
    renderBG();
    p.clip(pos.x,pos.y,dim.x,dim.y);
    for(DropdownItem ddi : children){ddi.render();}
    p.noClip();
    renderFG();
  }
  
  private void renderBG(){
    p.fill(32);
    p.noStroke();
    p.rect(pos.x,pos.y,dim.x,dim.y);
  }

  private void renderFG(){
    p.noFill();
    p.stroke(0,0,64);
    p.strokeWeight(2);
    p.rect(pos.x,pos.y,dim.x,dim.y);
  }
  
}
