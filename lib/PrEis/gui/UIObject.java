package PrEis.gui;
import PrEis.gui.Enums.AppFont;
import PrEis.utils.Cons;
import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Abstract (i.e. 'Root') class ∀ `UIObject` subtypes.
 * @note PROTECTED as nothing should see this sans fellow `PrEis.gui` members.
 */
abstract class UIObject {

  /** 
   * Enumeration of UIObject Types.
   * {@link #CB} {@link #FB} {@link #TB} {@link #LB} {@link #CO} {@link #DD} {@link #DI} 
   */
  public enum Type {
    /** ClickButton */   CB,
    /** ConfirmButton */ FB,
    /** ToggleButton */  TB,
    /** Label */         LB,
    /** Container */     CO,
    /** Dropdown */      DD,
    /** DropdownItem */  DI,
  };

  /** PApplet of the Sketch/Applet. */
  protected PApplet p;

  /** Position of this UIObject. */
  protected PVector pos;

  /** Size of this UIObject. */  
  protected PVector dim;

  /** Endpoint position of this UIObject. */
  protected PVector ePt;

  /** Midpoint Position of this UIObject. */
  protected PVector mPt;

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
  protected UIObject.Type type; 

  /** Style defining this UIObject. */
  protected UIStyle style;

  /** Current font used by this UIObject. */
  protected AppFont objFont;

  public UIObject(PApplet iPar, PVector iPos, PVector iDim, UIObject.Type iTyp){
    p         = iPar;
    objFont   = AppFont.TEXT;
    label     = "";
    type      = iTyp;
    style     = DefaultStyle.Get(iPar, iTyp);
    mouseOver = false;
    disabled  = false;
    setTransform(iPos,iDim);
  }
  
  public void setTransform(PVector in_pos, PVector in_dim){
    pos=in_pos;
    dim=in_dim;
    ePt=new PVector(pos.x+dim.x,pos.y+dim.y);
    mPt=PVector.mult(dim,0.5f).add(pos);
  }
  
  public void addTranslate(PVector trs){
    setTransform(PVector.add(pos,trs),dim);
  }
  
  public boolean isMouseOver(){
    return p.mouseX >= pos.x && p.mouseX <= ePt.x && p.mouseY >= pos.y && p.mouseY <= ePt.y;
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

  /** @TODO RESOLVE COMMENTED OUT CODE! */
  public void changeFont(){
    switch(objFont){
      case TEXT: /*pApplet.textFont(titWeb);*/ return;
      case GLYPH: /*pApplet.textFont(fontAw);*/ return;
      default: Cons.err_act(Err.SWITCH_DROP_OUT, Act.RETURN_NO_ACTION);
    }
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
    changeFont();
  }
  
  public void renderRect(){
    if(style.border_radius>0){p.rect(pos.x,pos.y,dim.x,dim.y,style.border_radius);}
    else{p.rect(pos.x,pos.y,dim.x,dim.y);}
  }
  
  /** @TODO: Revisit this if/when I define UIObjects with a `Dims` struct -vs- current `PVectors`. */
  public void renderText(float x1, float y1){
    //if(style.text_wrap!=null){p.text(label,x1,y1,style.text_wrap.wide(),style.text_wrap.tall());}
    //else{p.text(label,x1,y1);}
    p.text(label,x1,y1);
  }

  public void renderTextViaOri(){
    switch(style.txt_anchor){
      case TL:rTL();return; case TR:rTR();return; case CC:rCC();return;
      case TC:rTC();return; case CL:rCL();return; case CR:rCR();return;
    }
  }

  private void rTL(){p.textAlign(PApplet.LEFT, PApplet.TOP); renderText(pos.x+style.txt_offset_x,pos.y+style.txt_offset_y);}
  private void rTR(){p.textAlign(PApplet.RIGHT, PApplet.TOP); renderText(ePt.x-style.txt_offset_x,pos.y+style.txt_offset_y);} 
  private void rCC(){p.textAlign(PApplet.CENTER, PApplet.CENTER); renderText(mPt.x+style.txt_offset_x,mPt.y+style.txt_offset_y);}
  private void rTC(){p.textAlign(PApplet.CENTER, PApplet.TOP); renderText(mPt.x+style.txt_offset_x,pos.y+style.txt_offset_y);}
  private void rCR(){p.textAlign(PApplet.RIGHT, PApplet.CENTER); renderText(pos.x-style.txt_offset_x,mPt.y+style.txt_offset_y);}
  private void rCL(){p.textAlign(PApplet.LEFT, PApplet.CENTER); renderText(pos.x+style.txt_offset_x,mPt.y+style.txt_offset_y);}

} //> Ends Class UIObject