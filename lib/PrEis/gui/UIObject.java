package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Pgfx;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Abstract (i.e. 'Root') class ∀ `UIObject` subtypes.
 */
public abstract class UIObject {

  /** PApplet of the Sketch/Applet. */
  protected PApplet app;

  /** Bounding Box (providing pos, dim, ept, mpt, and lerping thereof) */
  protected BBox bbox;

  /** Is the mouse currently over this UIObject? */
  protected boolean mouseOver;

  /** Is this UIObject currently disabled? */
  protected boolean disabled;

  /** Is this UIObject currently selected? <i>(currently used for Dropdown Items)</i> */
  protected boolean selected;

  /** Display text of this UIObject. */  
  protected String label;

  /** <b>(OPTIONAL)</b> Special display text if disabled. */  
  protected String label_disabled;  

  /** Value string of this UIObject (A/A). */  
  protected String value; 

  /** Tooltip text of this UIObject (A/A). */
  protected String title;

  /** UIObject Type of this UIObject. */
  protected WidgetType type; 

  /** 
   * Style defining this UIObject.
   * @implNote currently set to <code>public</code> to make `EiSpriteViewer`
   * integration a bit easier until I figure out how to better handle setters.
   */
  public UIStyle style;

  /** Current font used by this UIObject. */
  protected AppFont objFont;

  /** 
   * @implNote Used for setting fonts. Not a good design pattern but it's either
   * this else injecting the {@link PApplet} into every {@link UIObject}. Plus:
   * instances already <i><b>'know'</b></i> {@link UIManager} via the method
   * {@link #bindManager} <i>(yeah, I know, also not a good design pattern)</i>.
   */
  protected UIManager manager;


  protected IGUICallBack genericCallBack;


  public UIObject(PApplet iPar, BBox iBox, WidgetType iTyp){
    app         = iPar;
    objFont   = AppFont.TEXT;
    label     = "";
    type      = iTyp;
    style     = DefaultStyle.Get(iPar, iTyp);
    mouseOver = false;
    disabled  = false;
    bbox      = iBox;

  }  

  public UIObject(PApplet iPar, PVector iPos, PVector iDim, WidgetType iTyp){
    this(iPar, new BBox(iPos, iDim), iTyp);
  }



  public void setTranslate(PVector iPos, PVector iDim){
    bbox = new BBox(iPos, iDim);
  }

  public void setTranslate(BBox iBox){
    bbox = iBox;
  }


  public UIManager getManager(){return manager;}

  /** This is the QAD version for UIObjects created as children of UIContainers. */
  public void setManager(UIManager iMgr){
    manager = iMgr;    
  }

  /** This is the QAD version for UIObjects created as children of UIContainers. */
  public UIObject withManager(UIManager iMgr){
    manager = iMgr; return this;
  }

  public UIObject bindManager(UIManager iMgr){
    manager = iMgr;
    iMgr.bindUiObject(this);
    return this;
  }




  public void addTranslate(PVector trs){
    bbox.translatePos(trs);
  }







  public UIObject withBBox(BBox iBox){setTranslate(iBox); return this;}

  public UIObject withVectors(PVector iPos, PVector iDim){setTranslate(iPos, iDim); return this;}

  public UIObject withLabel(String iLabel){
    if(iLabel != null){setLabel(iLabel);}
    setFont(AppFont.TEXT);
    return this;
  }
  
  public UIObject withValue(String iValue){
    if(iValue != null){setValue(iValue);}
    setFont(AppFont.TEXT);
    return this;
  }



  public UIObject withValueAndLabel(String v){
    return withValue(v).withLabel(v);
  }

  public UIObject withValueAndLabel(String v, String l){
    return withValue(v).withLabel(l);
  }




  /** This assumes {@link #withLabel(String)} was or will be called, as to call `setFont` within. */
  public UIObject withDLabel(String iLabel){setDLabel(iLabel); return this;}

  public UIObject withGlyph(String iGlyph){
    setLabel(iGlyph);
    setFont(AppFont.GLYPH);
    return this;
  }




  public UIObject withDisabledState(boolean iState){setDisabled(iState); return this;}


  
  public boolean isMouseOver(){
    return bbox.inBounds(app.mouseX, app.mouseY);
  }
  
  public boolean isHoveredState(){
    return isMouseOver() && !app.mousePressed;
  }
   
  public boolean isClickedState(){
    return isMouseOver() && app.mousePressed;
  }

  public boolean isSelectedState(){
    return selected;
  }
  
  public boolean isDisabledState(){
    return disabled;
  }
  

  public void setSelected(boolean v){selected = v;}
  public void toggleSelected(){selected = !selected;}

  public void setDisabled(boolean v){disabled = v;}
  public void toggleDisabled(){disabled = !disabled;}

  public void setLabel(String iLabel){
    label = iLabel;
  }

  public void setDLabel(String iLabel){
    label_disabled = iLabel;
  }


  public void setValue(String iValue){
    value = iValue;
  }


  public UIObject setTitle(String iTitle){
    title = iTitle;
    return this;
  }

  public void setFont(AppFont iFont){
    objFont = iFont;
    if (objFont!=null && objFont==AppFont.GLYPH){setPredefStyle("GLYPH");}
  } 
  
  public UIObject setPredefStyle(String s){
    style.setStyleProp("txt_size", Integer.class, 32);
    return this;
  }


  /** 
   * Calls {@link UIStyle#setStyleProp} on {@link #style}.
   * @see UIStyle#setStyleProp
   */
  public <T> UIObject setStyleProp(String propName, Class<T> propType, Object propValue){
    style.setStyleProp(propName, propType, propValue);
    return this;
  }
  

  /** @deprecated */
  public UIClick castToClick()  {return (UIClick)this;}
  /** @deprecated */  
  public UIToggle castToToggle(){return (UIToggle)this;}
  /** @deprecated */ 
  public UILabel castToLabel()  {return (UILabel)this;}
  /** @deprecated */
  public UIContainer castToContainer()  {return (UIContainer)this;}  

  /** WICKED and NAUGHTY! LOL */
  public <T> T castTo(Class<T> type){
    try {return type.cast(this);}
    catch(ClassCastException e){return null;}    
  }
  
  /** Stub for next hour 'till I realize custom callback subtype. */
  public void bindCallback(IGUICallBack iCallBack){
    genericCallBack = iCallBack;
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
    manager.setFont(objFont);
  }
  
  public void renderRect(){
    if(style.border_radius>0){Pgfx.rect(app,bbox,style.border_radius);}
    else{Pgfx.rect(app,bbox);}
  }



  
  /** 
   * @TODO Realize this at some point? Perhaps as specd-else-default normalized
   * percent of widget width per its `BBox`.
   */
  public void renderText(float x1, float y1){
    //if(style.text_wrap){p.text(label,x1,y1,style.text_wrap.wide(),style.text_wrap.tall());}
    //else{p.text(label,x1,y1);}
    app.text(label,x1,y1);
  }

  public void renderTextViaOri(){
    switch(style.txt_anchor){
      case TL:rTL();return;
      case TR:rTR();return;
      case TOP:rTC();return;
      case LFT:rCL();return;
      case RGT:rCR();return;
      case CTR:
      default: rCC(); return;
    }
  }

  private void rTL(){app.textAlign(PApplet.LEFT, PApplet.TOP); renderText(bbox.minX()+style.txt_offset.x,bbox.minY()+style.txt_offset.y);}
  private void rTR(){app.textAlign(PApplet.RIGHT, PApplet.TOP); renderText(bbox.maxX()-style.txt_offset.x,bbox.minY()+style.txt_offset.y);} 
  private void rCC(){app.textAlign(PApplet.CENTER, PApplet.CENTER); renderText(bbox.midX()+style.txt_offset.x,bbox.midY()+style.txt_offset.y);}
  private void rTC(){app.textAlign(PApplet.CENTER, PApplet.TOP); renderText(bbox.midX()+style.txt_offset.x,bbox.minY()+style.txt_offset.y);}
  private void rCR(){app.textAlign(PApplet.RIGHT, PApplet.CENTER); renderText(bbox.minX()-style.txt_offset.x,bbox.midY()+style.txt_offset.y);}
  private void rCL(){app.textAlign(PApplet.LEFT, PApplet.CENTER); renderText(bbox.minX()+style.txt_offset.x,bbox.midY()+style.txt_offset.y);}

} //> Ends Class UIObject