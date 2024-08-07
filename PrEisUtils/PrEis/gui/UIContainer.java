package PrEis.gui;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * UIObject Container
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 * @TODO this is still the QAD one and needs refinement ASAP
 */
class UIContainer extends UIObject {
  
  private static final boolean SHOW_BOUNDS = false;
  
  private ArrayList<UIObject> objects;
  
  public UIContainer(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp,iPos,iDim,null);
    objects = new ArrayList<UIObject>();
    this.style.strk_enabled=p.color(0,255,0);
  }
  
  public UIContainer addChild(UIObject obj){
    obj.addTranslate(pos);
    objects.add(obj);
    return this;
  }
  
  public void update(){
    for(UIObject o : objects){o.update();}
  }
  
  public void onMousePressed(){
    for(UIObject o : objects){o.onMousePressed();}
  }
  
  public void render(){
    renderBounds();
    for(UIObject o : objects){o.render();}
  }
  
  private void renderBounds(){
    if(!UIContainer.SHOW_BOUNDS){return;}
    p.noFill();
    p.stroke(this.style.strk_enabled);
    p.rectMode(PApplet.CORNER);
    p.imageMode(PApplet.CORNER);
    p.rect(pos.x, pos.y, dim.x, dim.y);
  }

}
