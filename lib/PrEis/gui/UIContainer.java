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
  }
  
  public UIContainer(PApplet iApp, BBox iBBox){
    super(iApp, iBBox, WidgetType.CO);
    objects = new ArrayList<UIObject>();
  }

  public static UIContainer create(PApplet app, BBox bbox){
    return new UIContainer(app, bbox);
  }

  public static UIContainer create(PApplet app, PVector pos, PVector dim){
    return new UIContainer(app, pos, dim);
  }

  public static UIContainer create(UIManager mgr, BBox bbox){
    return create(mgr.app, bbox).bindManager(mgr).castTo(UIContainer.class);
  }

  public static UIContainer create(UIManager mgr, PVector pos, PVector dim){
    return create(mgr.app, pos, dim).bindManager(mgr).castTo(UIContainer.class);
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
  

  public void onMouseWheel(int v){
    if(!bbox.inBounds(app.mouseX, app.mouseY)){return;}
    for(UIObject o : objects){o.onMouseWheel(v);}
  }

  
  public void onMousePressed(){
    if(!bbox.inBounds(app.mouseX, app.mouseY)){return;}
    for(UIObject o : objects){o.onMousePressed();}
  }


  public void render(){
    app.fill(style.fill);
    app.stroke(style.strk_enabled);
    renderRect();
    for(UIObject o : objects){o.render();}
    if(SHOW_BOUNDS){renderBounds();}
  }
  
  public void lateRender(){
    for(UIObject o : objects){o.lateRender();}
  }
  
  private void renderBounds(){
    app.noFill();
    app.stroke(DefaultStyle.DBG_COL);
    app.rectMode(PApplet.CORNER);
    app.imageMode(PApplet.CORNER);
    Pgfx.rect(app,bbox);
  }

}
