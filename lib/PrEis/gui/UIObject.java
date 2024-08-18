package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Pgfx;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Abstract (i.e. 'Root') class ∀ `UIObject` subtypes.
 * @note PROTECTED as nothing should see this sans fellow `PrEis.gui` members.
 */
abstract class UIObject {

  /** PApplet of the Sketch/Applet. */
  protected PApplet p;

  /** Bounding Box (providing pos, dim, ept, mpt, and lerping thereof) */
  protected BBox bbox;

  /** Is the mouse currently over this UIObject? */
  protected boolean mouseOver;

  /** Is this UIObject currently disabled? */
  protected boolean disabled;

  /** Display text of this UIObject. */  
  protected String label; 

  /** Value string of this UIObject (A/A). */  
  protected String value; 

  /** Tooltip text of this UIObject (A/A). */
  protected String title;

  /** UIObject Type of this UIObject. */
  protected WidgetType type; 

  /** Style defining this UIObject. */
  protected UIStyle style;

  /** Current font used by this UIObject. */
  protected AppFont objFont;

  public UIObject(PApplet iPar, PVector iPos, PVector iDim, WidgetType iTyp){
    p         = iPar;
    objFont   = AppFont.TEXT;
    label     = "";
    type      = iTyp;
    style     = DefaultStyle.Get(iPar, iTyp);
    mouseOver = false;
    disabled  = false;
    bbox      = new BBox(iPos, iDim);

  }
    
  public void addTranslate(PVector trs){
    bbox.translatePos(trs);
  }
  
  public boolean isMouseOver(){
    return bbox.inBounds(p.mouseX, p.mouseY);
  }
  
  public boolean isHoveredState(){
    return isMouseOver() && !p.mousePressed;
  }
   
  public boolean isClickedState(){
    return isMouseOver() && p.mousePressed;
  }
  
  public boolean isDisabledState(){
    return disabled;
  }
  
  public void onSetFont(){
    if (objFont!=null && objFont==AppFont.GLYPH){setPredefStyle("GLYPH");}
  } 
  
  public UIObject setPredefStyle(String s){
    style.setPredefStyle(s);
    return this;
  }

  /** Abstract assigns `mouseOver`. See child types for resp. addl. handling. */  
  public void update(){
    mouseOver = isMouseOver();
  }  
  
  /** Abstract for `mousePressed` events. See child types for resp. handling. */
  public void onMousePressed(){
    /*> ABSTRACT STUB <*/
  }

  /** Abstract for `keyPressed` events. See child types for resp. handling. */
  public void onKeyPressed(){
    /*> ABSTRACT STUB <*/
  }

  /** Abstract for `mouseWheel` events. See child types for resp. handling. */
  public void onMouseWheel(int v){
    /*> ABSTRACT STUB <*/
  }

  /** Abstract `render` used for common pre-pro; as children define how they render. */
  public void render(){
    //> not the prettiest realization but QAD works for now. unsure about who/how WRT responsibility
    UIManager.getFont(objFont);
  }
  
  public void renderRect(){
    if(style.border_radius>0){Pgfx.rect(p,bbox,style.border_radius);}
    else{Pgfx.rect(p,bbox);}
  }
  
  /** 
   * @TODO Realize this at some point? Perhaps as specd-else-default normalized
   * percent of widget width per its `BBox`.
   */
  public void renderText(float x1, float y1){
    //if(style.text_wrap){p.text(label,x1,y1,style.text_wrap.wide(),style.text_wrap.tall());}
    //else{p.text(label,x1,y1);}
    p.text(label,x1,y1);
  }

  public void renderTextViaOri(){
    switch(style.txt_anchor){
      case TL:rTL();return; case TR:rTR();return; case CC:rCC();return;
      case TC:rTC();return; case CL:rCL();return; case CR:rCR();return;
    }
  }

  private void rTL(){p.textAlign(PApplet.LEFT, PApplet.TOP); renderText(bbox.minX()+style.txt_offset.x,bbox.minY()+style.txt_offset.y);}
  private void rTR(){p.textAlign(PApplet.RIGHT, PApplet.TOP); renderText(bbox.maxX()-style.txt_offset.x,bbox.minY()+style.txt_offset.y);} 
  private void rCC(){p.textAlign(PApplet.CENTER, PApplet.CENTER); renderText(bbox.midX()+style.txt_offset.x,bbox.midY()+style.txt_offset.y);}
  private void rTC(){p.textAlign(PApplet.CENTER, PApplet.TOP); renderText(bbox.midX()+style.txt_offset.x,bbox.minY()+style.txt_offset.y);}
  private void rCR(){p.textAlign(PApplet.RIGHT, PApplet.CENTER); renderText(bbox.minX()-style.txt_offset.x,bbox.midY()+style.txt_offset.y);}
  private void rCL(){p.textAlign(PApplet.LEFT, PApplet.CENTER); renderText(bbox.minX()+style.txt_offset.x,bbox.midY()+style.txt_offset.y);}

} //> Ends Class UIObject