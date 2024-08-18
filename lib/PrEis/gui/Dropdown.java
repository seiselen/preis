package PrEis.gui;

import PrEis.utils.DataStructUtils;
import PrEis.utils.Pgfx;
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
    PVector baseOff = getChildBaseOffset();
    maxScrollOff = (children.length*ddownItemTall)-bbox.maxY();
    
    for (int i=0; i<childValues.length; i++){
      children[i] = new DropdownItem(
        p,DataStructUtils.createVector(baseOff.x,baseOff.y),
        DataStructUtils.createVector(ddownItemWide,ddownItemTall)
      )
      .appendValue(childValues[i]).appendAction(null);
      baseOff.y+=ddownItemTall;
    }
    return this;
  }

  public Dropdown appendChildren(ILabelledActionCallback[] actions){
    children = new DropdownItem[actions.length];
    PVector baseOff = getChildBaseOffset();
    maxScrollOff = (children.length*ddownItemTall)-bbox.maxY();
    
    for (int i=0; i<actions.length; i++){
      children[i] = new DropdownItem(
        p,DataStructUtils.createVector(baseOff.x,baseOff.y),
        DataStructUtils.createVector(ddownItemWide,ddownItemTall)
      )
      .appendValue(actions[i].getLabel()).appendAction(actions[i]);
      baseOff.y+=ddownItemTall;
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
    for(DropdownItem ddi : children){
      if(ddi.bbox.minY() < bbox.maxY()){ddi.onMousePressed();}
    }
  }

  private PVector getChildBaseOffset(){
    return DataStructUtils.createVector(
      bbox.minX()+(bbox.maxX()-ddownItemWide)/2,
      bbox.minY()
    );
  }

  public void render(){
    renderBG();
    Pgfx.clip(p, bbox);
    for(DropdownItem ddi : children){ddi.render();}
    p.noClip();
    renderFG();
  }
  
  private void renderBG(){
    p.fill(32);
    p.noStroke();
    Pgfx.rect(p, bbox);
  }

  private void renderFG(){
    p.noFill();
    p.stroke(0,0,64);
    p.strokeWeight(2);
    Pgfx.rect(p, bbox);
  }
  
}
