package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import PrEis.utils.Pgfx;
import PrEis.utils.QueryUtils;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

/**
 * Abstract (i.e. 'Root') class ∀ `UIObject` subtypes.
 */
public abstract class UIObject {

  public static PFont textFont;
  public static PFont symbFont;
  public static PFont monoFont;

  /** UIObject Type of this UIObject. */
  protected WidgetType type; 

  /** PApplet of the Sketch/Applet. */
  protected PApplet app;

  /** Bounding Box (providing pos, dim, ept, mpt, and lerping thereof) */
  protected BBox bbox;

  /** 
   * Style defining this UIObject.
   * @implNote currently set to <code>public</code> to make `EiSpriteViewer`
   * integration a bit easier until I figure out how to better handle setters.
   */
  public UIStyle style;

  /** Current font used by this UIObject. */
  protected AppFont objFont;

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


  /*-[ CONSTRUCTORS AND SPECIAL INITIALIZERS ]----------------------------------
  +===========================================================================*/

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
  

  public static void injectFonts(PFont tf, PFont gf){
    if(tf==null||gf==null){Cons.err_act(Err.NULL_XOR_INVALID, Act.RETURN_NO_ACTION);}
    else{
      UIObject.textFont=tf; 
      UIObject.symbFont=gf;
    }
  }



  /*-[ STATE GETTERS (INCL. QUERIERS & FORMATTERS ]-----------------------------
  +===========================================================================*/

  public boolean isMouseOver(){return bbox.inBounds(app.mouseX, app.mouseY);}
  
  public boolean isHoveredState(){return isMouseOver() && !app.mousePressed;}
   
  public boolean isClickedState(){return isMouseOver() && app.mousePressed;}

  public boolean isSelectedState(){return selected;}
  
  public boolean isDisabledState(){return disabled;}

  public <T> T castTo(Class<T> type){try {return type.cast(this);} catch(Exception e){return null;}}

  public PVector getPos (){return bbox.pos().copy();}


  /*-[ STATE SETTERS ]----------------------------------------------------------
  +===========================================================================*/

  public void toggleSelected(){
    selected = !selected;
  }

  public void setSelected(boolean v){
    selected = v;
  }

  public UIObject setSelectedΘ(boolean v){
    setSelected(v);
    return this;
  }
  
  public void toggleDisabled(){
    disabled = !disabled;
  }

  public void setDisabled(boolean v){
    disabled = v;
  }

  public UIObject setDisabledΘ(boolean iState){
    setDisabled(iState);
    return this;
  }

  public void setTranslate(PVector iPos, PVector iDim){
    bbox = new BBox(iPos, iDim);}

  public void setTranslate(BBox iBox){
    bbox = iBox;
  }

  public UIObject bindManager(UIManager iMgr){
    iMgr.bindUiObject(this);
    return this;
  }

  public void addTranslate(PVector trs){
    bbox.translatePos(trs);
  }

  public UIObject withBBox(BBox iBox){
    setTranslate(iBox);
    return this;
  }

  public UIObject withVectors(PVector iPos, PVector iDim){
    setTranslate(iPos, iDim);
    return this;
  }

  public UIObject withGlyph(String iGlyph){
    setLabel(iGlyph);
    setFontType(AppFont.GLYPH);
    return this;
  }

  public UIObject withLabel(String iLabel){
    if(iLabel != null){setLabel(iLabel);}
    setFontType(AppFont.TEXT);
    return this;
  }

  public void setLabel(String iLabel){
    label = iLabel;
  }

  public void setDLabel(String iLabel){
    label_disabled = iLabel;
  }
  
  /** This assumes {@link #withLabel(String)} was or will be called, as to call `setFont` within. */
  public UIObject withDLabel(String iLabel){
    setDLabel(iLabel);
    return this;
  }

  public void setValue(String iValue){
    value = iValue;
  }
  
  public UIObject withValue(String iValue){
    if(iValue != null){setValue(iValue);}
    setFontType(AppFont.TEXT);
    return this;
  }

  public UIObject setTitle(String iTitle){
    title = iTitle;
    return this;
  }

  public UIObject withValueAndLabel(String v){
    return withValue(v).withLabel(v);
  }

  public UIObject withValueAndLabel(String v, String l){
    return withValue(v).withLabel(l);
  }
  
  /** 
   * Calls {@link UIStyle#setStyleProp} on {@link #style}.
   * @see UIStyle#setStyleProp
   */
  public <T> UIObject setStyleProp(String propName, Class<T> propType, Object propValue){
    style.setStyleProp(propName, propType, propValue);
    return this;
  }


  /*-[ GAME LOOP AND I/O (STUB) HANDLERS ]--------------------------------------
  +===========================================================================*/

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


  /*-[ RENDER CALLS ]-----------------------------------------------------------
  +===========================================================================*/


  /** Abstract `render` used for common pre-pro; as children define how they render. */
  public void render(){
    //> here because `setFont` RESETS all text style settings, ergo needs to be called first
    changeFont(objFont);
  }


  public UIObject setFontTypeΘ(AppFont f){setFontType(f); return this;}

  public void setFontType(AppFont f){
    objFont = f;
    if (objFont!=null && objFont==AppFont.GLYPH){
      setStyleProp("txt_size", Integer.class, DefaultStyle.GLYPH_TEXT_SIZE);
    }
  }
  

  private void changeFont(AppFont f){
    if(QueryUtils.nullAll(textFont,symbFont)){
      Cons.err_act(Err.NULL_VALUE, Act.RETURN_NO_ACTION, "one or more fonts null");
      return;
    }
    switch(f){
      case TEXT  : app.textFont(textFont); return;
      case GLYPH : app.textFont(symbFont); return;
      case MONO  : app.textFont(monoFont); return;
      default    : Cons.err_act(Err.SWITCH_DROP_OUT, Act.RETURN_NULL);
    }
  }

  public void lateRender(){
    if(mouseOver && title!=null){renderTooltip();}
  }
  
  public void renderRect(){
    app.rectMode(PApplet.CORNER);
    if(style.border_radius>0){Pgfx.rect(app,bbox,style.border_radius);}
    else{Pgfx.rect(app,bbox);}
  }


  public void renderText(float x1, float y1){
    if(style.text_wrap){renderTextWrapped(x1, y1);}
    else{app.text(label,x1,y1);}
  }

  //> not happy with computation expense, but #KISS for now.
  private void renderTextWrapped(float x1, float y1){
    app.text(label, x1, y1, bbox.dimX()-(style.txt_offset.x*2), bbox.dimY()-(style.txt_offset.y*2));
  }


  public void renderTextViaOri(){
    app.rectMode(PApplet.CORNER);
    switch(style.txt_anchor){
      case TL:  rTL(); return;
      case TR:  rTR(); return;
      case TOP: rTC(); return;
      case LFT: rCL(); return;
      case RGT: rCR(); return;
      case CTR: default: rCC(); return;
    }
  }

  private void rTL(){
    app.textAlign(PApplet.LEFT, PApplet.TOP);
    renderText(bbox.minX()+style.txt_offset.x,bbox.minY()+style.txt_offset.y);
  }
  
  private void rTR(){
    app.textAlign(PApplet.RIGHT, PApplet.TOP);
    renderText(bbox.maxX()-style.txt_offset.x,bbox.minY()+style.txt_offset.y);
  }
  
  private void rCC(){
    app.textAlign(PApplet.CENTER, PApplet.CENTER);
    renderText(bbox.midX()+style.txt_offset.x,bbox.midY()+style.txt_offset.y);
  }
  
  private void rTC(){
    app.textAlign(PApplet.CENTER, PApplet.TOP);
    renderText(bbox.midX()+style.txt_offset.x,bbox.minY()+style.txt_offset.y);
  }
  
  private void rCR(){
    app.textAlign(PApplet.RIGHT, PApplet.CENTER);
    renderText(bbox.minX()-style.txt_offset.x,bbox.midY()+style.txt_offset.y);
  }
  
  private void rCL(){
    app.textAlign(PApplet.LEFT, PApplet.CENTER);
    renderText(bbox.minX()+style.txt_offset.x,bbox.midY()+style.txt_offset.y);
  }

  public void renderTooltip(){
    if(!mouseOver||title==null){return;}

    //> ORDER COUNTS! `setFont` CALL WIPES FONT STATE!
    changeFont(AppFont.TEXT);
    app.textSize(style.txt_size_ttip);

    int off = style.txt_size_ttip/2;

    //> this is the approx size of the text string, plus a small offset
    PVector ttDim = new PVector(app.textWidth(title)+off,Pgfx.textMaxHeight(app)+off);

    float x1 = (app.mouseX+ttDim.x+style.txt_size_ttip>app.width) ? app.mouseX-ttDim.x : app.mouseX;
    float y1 = (app.mouseY+ttDim.y+style.txt_size_ttip>app.height) ? app.mouseX-ttDim.y : app.mouseY;
    float xo = app.mouseX;
    float yo = app.mouseY;

    xo = (x1==app.mouseX) ? (xo+(ttDim.x/2)+style.txt_size_ttip) : (xo-(ttDim.x/2)-off);
    yo = (y1==app.mouseY) ? (yo+(ttDim.y/2)+style.txt_size_ttip) : (yo-(ttDim.y/2)-off);

    app.stroke(app.color(0));
    app.strokeWeight(1);
    app.fill(app.color(255));
    app.rectMode(PApplet.CENTER);
    app.rect(xo,yo,ttDim.x,ttDim.y,4);
    app.noStroke();
    app.fill(0);
    app.textAlign(PApplet.CENTER, PApplet.CENTER);
    app.text(title,xo,yo);

    //> Diagnostic (TBH: realized for demo screengrabs to place mouse cursor imgs at lol)
    //app.stroke(255);
    //app.noFill();
    //app.circle(app.mouseX, app.mouseY, 8);
  }


}