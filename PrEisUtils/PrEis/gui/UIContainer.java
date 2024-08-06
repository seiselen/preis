package PrEis.gui;

/** 
 * UIObject Container
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 */
class UIContainer {
  private static final boolean SHOW_BOUNDS = false;
  private ArrayList<UIObject> objects;
  public UIContainer(PVector iPos, PVector iDim){super(iPos,iDim,null); objects = new ArrayList<UIObject>(); this.style.strk_enabled=color(0,255,0);}
  public UIContainer addChild(UIObject obj){obj.addTranslate(pos); objects.add(obj); return this;}
  public void update(){for(UIObject o : objects){o.update();}  }
  public void onMousePressed(){for(UIObject o : objects){o.onMousePressed();}}
  public void render(){renderBounds(); for(UIObject o : objects){o.render();}}
  private void renderBounds(){if(!UIContainer.SHOW_BOUNDS){return;} noFill(); stroke(this.style.strk_enabled); rectMode(CORNER); imageMode(CORNER); rect(pos.x, pos.y, dim.x, dim.y);}
}
