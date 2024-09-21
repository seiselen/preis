package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.Cons.Err;
import PrEis.utils.Pgfx;
import processing.core.PApplet;
import processing.core.PVector;

/** 
 * Label
 */
public class UILabel extends UIObject {
  
  private IUpdateCallback updateCallback;
  
  private LabelType lblType;
  

  public UILabel(PApplet iApp, BBox iBox){
    super(iApp, iBox, WidgetType.LB);
  }

  public UILabel(PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, WidgetType.LB);
  }
    

  public static UILabel create(PApplet app, BBox box, String txt, AppFont fnt, LabelType typ, IUpdateCallback cbk){
    switch(fnt){
      case TEXT  : return new UILabel(app,box).withLabel(txt, typ).bindCallback(cbk);
      case GLYPH : return new UILabel(app,box).withGlyph(txt, typ).bindCallback(cbk);
      default    : Cons.err(Err.SWITCH_DROP_OUT); return null;
    }
  }

  public static UILabel create(PApplet app, PVector pos, PVector dim, String txt, AppFont fnt, LabelType typ, IUpdateCallback cbk){
    return create(app, new BBox(pos, dim), txt, fnt, typ, cbk);
  }

  public static UILabel create(UIManager mgr, BBox box, String txt, AppFont fnt, LabelType typ, IUpdateCallback cbk){
    return create(mgr.app, box, txt, fnt, typ, cbk).bindManager(mgr);
  }

  public static UILabel create(UIManager mgr, PVector pos, PVector dim, String txt, AppFont fnt, LabelType typ, IUpdateCallback cbk){
    return create(mgr.app, pos, dim, txt, fnt, typ, cbk).bindManager(mgr);
  }


  public UILabel setType(LabelType iTyp){
    lblType = iTyp;
    return this;
  }
  
  public UILabel bindCallback(IUpdateCallback cbk){
    if(cbk==null){return this;} //> no conserr, intended pass-thru if `null`
    updateCallback=cbk;
    label=updateCallback.getTxt();
    return this;
  }
  
  
  public UILabel withLabel(String iLabel, LabelType iType){setType(iType); return (UILabel)super.withLabel(iLabel);}

  public UILabel withGlyph(String iGlyph, LabelType iType){setType(iType); return (UILabel)super.withGlyph(iGlyph);}  

  public UILabel bindManager(UIManager iMgr){return (UILabel)super.bindManager(iMgr);}









  public void update(){
    if(updateCallback!=null){label = updateCallback.getTxt();}
  }
  
  public void render(){
    super.render();
    app.textSize(style.txt_size);
    app.strokeWeight(style.swgt);
    app.fill(getFillCol());
    app.stroke(getStrokeCol());
    Pgfx.rect(app, bbox);
    app.fill(getTxtFillCol());
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