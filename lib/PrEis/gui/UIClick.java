package PrEis.gui;

import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.Cons.Err;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * Click Button
 */
public class UIClick extends UIObject {
  
  private IActionCallback action;
  

  public UIClick(PApplet iApp, BBox iBox){
    super(iApp, iBox, WidgetType.CB);
  }

  public UIClick(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, WidgetType.CB);
  }

  public static UIClick create(PApplet app, BBox box, String txt, AppFont fnt, IActionCallback cbk){
    switch(fnt){
      case TEXT  : return new UIClick(app,box).withLabel(txt).bindCallback(cbk);
      case GLYPH : return new UIClick(app,box).withGlyph(txt).bindCallback(cbk);
      default    : Cons.err(Err.SWITCH_DROP_OUT); return null;
    }
  }

  public static UIClick create(PApplet app, PVector pos, PVector dim, String txt, AppFont fnt, IActionCallback cbk){
    return create(app, new BBox(pos, dim), txt, fnt, cbk);
  }


  public static UIClick create(UIManager mgr, BBox box, String txt, AppFont fnt, IActionCallback cbk){
    return create(mgr.app, box, txt, fnt, cbk).bindManager(mgr);
  }

  public static UIClick create(UIManager mgr, PVector pos, PVector dim, String txt, AppFont fnt, IActionCallback cbk){
    return create(mgr.app, pos, dim, txt, fnt, cbk).bindManager(mgr);
  }


  public UIClick bindCallback(IActionCallback act){
    action = act;
    return this;
  }


  public UIClick withLabel(String iLabel){return (UIClick)super.withLabel(iLabel);}

  public UIClick withGlyph(String iGlyph){return (UIClick)super.withGlyph(iGlyph);}  

  public UIClick bindManager(UIManager iMgr){return (UIClick)super.bindManager(iMgr);}


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