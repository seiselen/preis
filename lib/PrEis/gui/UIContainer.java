package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.Pgfx;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * UIObject Container
 * @implNote Note: I am using simple iteration for mousepress and other spatial
 * events (whereas IIRC ancient versions of `UIObjects` used implicit R-Tree).
 * The reason is: there's no need at the moment; simple as that.
 */
public class UIContainer extends UIObject {

  /** 
   * Defines the valid types of widget that a container can hold.
   * @implNote I am being rather strict right now (e.g. IIRC the 2015 version of
   * `UIObjects` supported containers with containers)
   */
  private static final WidgetType[] VALID_CHILD_WIDGETS = new WidgetType[]{
    WidgetType.CB, WidgetType.TB, WidgetType.FB, WidgetType.LB
  };
  
  private boolean SHOW_BOUNDS = false;
  
  private ArrayList<UIObject> objects;
  
  public UIContainer(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, WidgetType.CO);
    objects = new ArrayList<UIObject>();
    this.style.strk_enabled=p.color(0,255,0);
  }
  
  public UIContainer(PApplet iApp, BBox iBBox){
    super(iApp, iBBox, WidgetType.CO);
    objects = new ArrayList<UIObject>();
    this.style.strk_enabled=p.color(0,255,0);
  }

  public static UIContainer create(PApplet app, BBox bbox){
    return new UIContainer(app, bbox);
  }

  public static UIContainer create(PApplet app, PVector pos, PVector dim){
    return new UIContainer(app, pos, dim);
  }

  public static UIContainer create(UIManager mgr, BBox bbox){
    return create(mgr.app, bbox).bindManager(mgr).castToContainer();
  }

  public static UIContainer create(UIManager mgr, PVector pos, PVector dim){
    return create(mgr.app, pos, dim).bindManager(mgr).castToContainer();
  }




  public UIContainer addChild(UIObject c){
    if(isValidChildType(c)){
      c.addTranslate(bbox.pos());
      c.setManager(manager);
      objects.add(c);
    }
    return this;
  }

  public UIContainer addChildren(UIObject ... cs){
    for(UIObject c : cs){addChild(c);}
    return this;
  }



  public UIContainer toggleShowBounds(){
    SHOW_BOUNDS = !SHOW_BOUNDS;
    return this;
  }

  public void addTranslate(PVector trs){
    super.addTranslate(trs);
    for(UIObject o : objects){o.addTranslate(trs);}
  }

  private boolean isValidChildType(UIObject c){
    for(WidgetType t : VALID_CHILD_WIDGETS){if(c.type==t){return true;}}
    Cons.err("Invalid child widget type: "+c.type.toNameAndID());
    return false;
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
    if(!SHOW_BOUNDS){return;}
    p.noFill();
    p.stroke(this.style.strk_enabled);
    p.rectMode(PApplet.CORNER);
    p.imageMode(PApplet.CORNER);
    Pgfx.rect(p,bbox);
  }

}
