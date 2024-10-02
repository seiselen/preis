package PrEis.gui;
import java.util.ArrayList;
import java.util.Collections;

import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.Pgfx;
import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import processing.core.PApplet;
import processing.core.PVector;

/** Dropdown */
public class UIDropdown extends UIObject {

  /** 
   * Basically: 'speed' by which `mouseWheel` scrolls through list.
   */
  private float scrollFactor;
  /**
   * Basically: current vertical offset by which {@link UIDropdownItem} list is
   * translated.
   */
  private float scrollOff;
  /** 
   * Basically: how much space needs to be scrolled through to reach bottom item
   * of options list. Technically: total vertical space required to fully render 
   * entire options list; sans vertical space alread provided by dropdown.
   */
  private float maxScrollOff;

  private PVector curBaseOff;


  private PVector ddownItemDim;

  private ArrayList<UIDropdownItem> options;
  private UIDropdownItem curSelItem;

  private ISelectAction action;

  public UIDropdown(PApplet iApp, BBox iBBox){
    super(iApp, iBBox, WidgetType.DD);
    options = new ArrayList<UIDropdownItem>();
    //> Keeping it simple for now; this is a bitch ATM to realize.
    ddownItemDim = new PVector(bbox.wide(), 32);
    curBaseOff   = new PVector();
    scrollFactor=16;
    reset();
  }

  public UIDropdown(PApplet iApp, PVector iPos, PVector iDim){
    this(iApp, new BBox(iPos, iDim));
  }

  public static UIDropdown create(PApplet iApp, BBox iBBox){
    return new UIDropdown(iApp, iBBox);
  }

  public static UIDropdown create(PApplet iApp, PVector iPos, PVector iDim){
    return new UIDropdown(iApp, iPos, iDim);
  }

  public static UIDropdown create(UIManager iMgr, BBox iBBox){
    return create(iMgr.app, iBBox).bindManager(iMgr).castTo(UIDropdown.class);
  }

  public static UIDropdown create(UIManager iMgr, PVector iPos, PVector iDim){
    return create(iMgr.app, iPos, iDim).bindManager(iMgr).castTo(UIDropdown.class);
  }


  /*=[ STATE (RE)INIT METHODS ]=================================================
  +===========================================================================*/

  /** 
   * Clears options list, current option selected, and scroll state. This method
   * <b>MUST</b> be called to correctly replace the current set of options with
   * a new one (e.g. changing target of sprite animation viz in EiSpriteViewer).
   */
  public void reset(){
    curSelItem = null;
    options.clear();
    resetOffsets();
  }

  public UIDropdown bindAction(ISelectAction iAction){
    action = iAction;
    return this;
  }

  /*=[ OPTION CRUD METHODS ]====================================================
  +===========================================================================*/

  public void onSelectionMade(UIDropdownItem selItm){
    if(curSelItem!=null){curSelItem.setSelected(false);}
    curSelItem = selItm;
    curSelItem.setSelected(true);
    if(action!=null){action.OnSelection(selItm.value);}
  }

  public UIDropdown addOption(String val, String lbl){
    options.add(
      UIDropdownItem.create(this, app, curBaseOff.copy(), ddownItemDim.copy(), val, (lbl==null?val:lbl))
      .castTo(UIDropdownItem.class)
    );
    recomputeOffsets();
    return this;
  }

  public UIDropdown addOption(String val){
    return addOption(val, null);
  }

  public UIDropdown addOptions(String[] vals){
    for(String val : vals){addOption(val);} return this;
  }

  public UIDropdown addOptions(String[] vals, String[] lbls){
    if(vals.length != lbls.length){Cons.err_act(Err.ARR_LEN_MISMATCH, Act.RETURN_NO_ACTION);}
    else{for(int i=0; i<vals.length; i++){addOption(vals[i], lbls[i]);}}
    return this;
  }
  
  public UIDropdown addOptions(String[][] vals_and_lbls){
    for(String[] opt : vals_and_lbls){addOption(opt[0], opt[1]);}
    return this;
  }

  public UIDropdown sortByLabel(){
    if(options.size()>0){Collections.sort(options);}
    return this;
  }


  /*=[ SCROLL METHODS ]=========================================================
  +===========================================================================*/

  private void resetOffsets(){
    scrollOff    = 0;
    maxScrollOff = 0;
    curBaseOff.set(bbox.midX()-(ddownItemDim.x/2), bbox.minY());
  }

  private void recomputeOffsets(){
    curBaseOff.y+=ddownItemDim.y;
    maxScrollOff = (options.size()*ddownItemDim.y)-bbox.dimY();
  }

  private boolean recalcScrollOff(int v){
    float nScrollOff = scrollOff+(v*scrollFactor);        
    if(nScrollOff<0||nScrollOff>maxScrollOff){return false;}
    scrollOff=nScrollOff;
    return true;
  }


  /*=[ UPDATE AND I/O METHODS ]=================================================
  +===========================================================================*/

  public void update(){
    for(UIDropdownItem ddi : options){ddi.update();}
  }
 
  public void onMouseWheel(int v){
    if(!bbox.inBounds(app.mouseX, app.mouseY)){return;}   
    if(!recalcScrollOff(v)){return;}
    for(UIDropdownItem ddi : options){ddi.scrollTransform(v*-scrollFactor);}
  }
  
  public void onMousePressed(){
    if(!bbox.inBounds(app.mouseX, app.mouseY)){return;}
    for(UIDropdownItem ddi : options){
      if(ddi.bbox.minY() < bbox.maxY()){ddi.onMousePressed();}
    }
  }


  /*=[ RENDER FUNCTIONS ]=======================================================
  +===========================================================================*/

  /*----------------------------------------------------------------------------
  |# Future Implementation Notes:
  |   > MUST call `super.render()` if widget text realized (e.g. `curSel`); and
  |   > MUST call `lateRender` if tooltips realized for dropdown and/xor items.
  +---------------------------------------------------------------------------*/
  public void render(){
    renderBG();
    clipAndRenderOptions();
    renderFG();
  }

  private void renderBG(){
    app.fill(style.fill);
    app.noStroke(); //> redundant as clipping will hide it, but meh...
    renderRect();
  }

  private void clipAndRenderOptions(){
    app.imageMode(PApplet.CORNER);
    Pgfx.clip(app, bbox);
    for(UIDropdownItem ddi : options){ddi.render();}
    app.noClip();
  }

  private void renderFG(){
    app.noFill();
    app.stroke(style.strk_enabled);
    app.strokeWeight(style.swgt);
    renderRect();
  }


  /*=[ TO-STRING/CONSOLE FUNCTIONS ]============================================
  +===========================================================================*/

  public void optionsToConsole(){
    for(UIDropdownItem o : options){o.toConsole();}
  }

}