package PrEis.gui;
import PrEis.utils.BBox;
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
    p.textSize(style.txt_size);
    p.strokeWeight(style.swgt);
    p.fill(getFillCol());
    p.stroke(getStrokeCol());
    Pgfx.rect(p, bbox);
    p.fill(getTxtFillCol());
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