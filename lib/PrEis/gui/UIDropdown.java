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

  //> @TODO: these should be spec'd by UIStyles
  private float scrollFactor;
  private float scrollOff;
  private float maxScrollOff;
  private float nScrollOff;

  private PVector ddownItemDim;
  private PVector curBaseOff;

  private ArrayList<UIDropdownItem> options;
  private UIDropdownItem curSelItem;

  private ISelectAction action;

  public UIDropdown(PApplet iApp, BBox iBBox){
    super(iApp, iBBox, WidgetType.DD);
    options = new ArrayList<UIDropdownItem>();
    setItemDims(bbox.wide()-32, 32);
    scrollFactor=16;
    reset();
  }

  /** 
   * Clears options list, current option selected, and scroll state. This method
   * <b>MUST</b> be called to correctly replace the current set of options with
   * a new one (e.g. changing target of sprite animation viz in EiSpriteViewer).
   */
  public void reset(){
    curSelItem = null;
    options.clear();
    scrollOff = 0;
    maxScrollOff = 0;
    curBaseOff.y=bbox.minY();
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

  public UIDropdown bindAction(ISelectAction iAction){
    action = iAction;
    return this;
  }

  private void setItemDims(float wide, float tall){
    ddownItemDim = new PVector(wide,tall);
    curBaseOff = new PVector(bbox.midX()-(ddownItemDim.x/2), bbox.minY());
  }

  public UIDropdown addOption(String val, String lbl){
    options.add(
      UIDropdownItem.create(this, app, curBaseOff.copy(), ddownItemDim.copy(), val, (lbl==null?val:lbl))
      .setManagerΘ(manager).castTo(UIDropdownItem.class)
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

  private void recomputeOffsets(){
    curBaseOff.y+=ddownItemDim.y;
    maxScrollOff = (options.size()*ddownItemDim.y)-bbox.dimY();
  }


  public UIDropdown sortByLabel(){
    if(options.size()>0){Collections.sort(options);}
    return this;
  }

  public void update(){
    for(UIDropdownItem ddi : options){ddi.update();}
  }
 
  public void onMouseWheel(int v){
    nScrollOff = scrollOff+(v*scrollFactor);        
    if(nScrollOff<0||nScrollOff>maxScrollOff){return;}
    scrollOff=nScrollOff;
    for(UIDropdownItem ddi : options){ddi.scrollTransform(v*-scrollFactor);}
  }
  
  public void onMousePressed(){
    if(!bbox.inBounds(app.mouseX, app.mouseY)){return;}
    for(UIDropdownItem ddi : options){
      if(ddi.bbox.minY() < bbox.maxY()){ddi.onMousePressed();}
    }
  }


  public void onSelectionMade(UIDropdownItem selItm){
    if(curSelItem!=null){curSelItem.setSelected(false);}
    curSelItem = selItm;
    curSelItem.setSelected(true);
    if(action!=null){action.OnSelection(selItm.value);}
  }


  public void optionsToConsole(){
    for(UIDropdownItem o : options){o.toConsole();}
  }


  /** 
   * @implSpec will need `super.render()` call if I introduce ANY non-item text i.e. current selection, etc.
   * @implSpec if you ever realize tooltips for dropdown xor items therein: you MUST call `lateRender`!
   */
  public void render(){
    renderBG();
    clipAndRenderOptions();
    renderFG();
  }

  private void renderBG(){
    Pgfx.fillnostroke(app,style.fill);
    renderRect();
  }

  private void clipAndRenderOptions(){
    app.imageMode(PApplet.CORNER);
    Pgfx.clip(app, bbox);
    for(UIDropdownItem ddi : options){
      ddi.render();
    }
    app.noClip();
  }

  private void renderFG(){
    Pgfx.strokenofill(app, style.strk_enabled);
    app.strokeWeight(style.swgt);
    renderRect();
  }
}