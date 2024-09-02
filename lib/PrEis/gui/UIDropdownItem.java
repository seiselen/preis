package PrEis.gui;
import PrEis.utils.Cons;
import PrEis.utils.DataStructUtils;
import PrEis.utils.Pgfx;
import PrEis.utils.QueryUtils;
import PrEis.utils.StringUtils;
import processing.core.PApplet;
import processing.core.PVector;

public class UIDropdownItem extends UIObject implements Comparable<UIDropdownItem> {

  private UIDropdown parent;
   
  public UIDropdownItem(UIDropdown iPar, PApplet iApp, PVector iPos, PVector iDim){
    super(iApp, iPos, iDim, WidgetType.DI);
    parent = iPar;
    value  = null;
    label  = null;
  }

  public static UIDropdownItem create(UIDropdown iPar, PApplet iApp, PVector iPos, PVector iDim){
    return new UIDropdownItem(iPar, iApp, iPos, iDim);
  }

  public static UIDropdownItem create(UIDropdown iPar, PApplet iApp, PVector iPos, PVector iDim, String val){
    return create(iPar, iApp, iPos, iDim).withValue(val);
  }

  public static UIDropdownItem create(UIDropdown iPar, PApplet iApp, PVector iPos, PVector iDim, String val, String lbl){
    return create(iPar, iApp, iPos, iDim, val).withLabel(lbl);
  }

  public UIDropdownItem withValue(String v){
    if(!QueryUtils.nullish(v)){value = v;} return this;
  }

  public UIDropdownItem withLabel(String l){
    if(!QueryUtils.nullish(l)){label = l;} return this;
  }

  public UIDropdownItem withValueAndLabel(String v, String l){
    return withValue(v).withLabel(l);
  }
    
  public void scrollTransform(float val){
    addTranslate(DataStructUtils.createVector(0,val));
  }
  
  private int getFillCol(){
    return 
      isDisabledState() ? style.fill_disabled :
      isClickedState()  ? style.fill_clicked  :
      isSelectedState() ? style.fill_clicked  :
      isHoveredState()  ? style.fill_hovered  : style.fill;
  } 
  
  private int getStrokeCol(){
    return isDisabledState() ? style.strk_disabled : style.strk_enabled;
  }

  public void onMousePressed(){
    if(mouseOver){parent.onSelectionMade(this);}
  }
  
  public void update(){
    super.update();
  }
  
  public void render(){
    p.textSize(style.txt_size);
    p.strokeWeight(style.swgt);
    p.fill(getFillCol());
    p.stroke(getStrokeCol());
    Pgfx.rect(p, bbox);
    renderText();
  }
  
  public void renderText(){
    p.fill(getStrokeCol());
    p.textAlign(PApplet.LEFT, PApplet.CENTER);
    p.text(getID(), PApplet.lerp(bbox.minX(), bbox.midX(), style.txt_off_pct),bbox.midY());
  }


  public String toString(){
    return StringUtils.wrapStringWith('{',StringUtils.concatAsSCSV(
      "value:"+StringUtils.wrapStringWith('"', value),
      "label:"+StringUtils.wrapStringWith('"', label)
    ),'}');
  }

  public void toConsole(){
    Cons.log(toString());
  }


  /** Returns {@link #label} if it exists, else {@link #value} <i>(which <b>MUST</b> exist!)</i>. */
  public String getID(){
    return (QueryUtils.nullish(label) ? value : label);
  }


  /** Compares via call of {@link #getID} <i>(see description thereof for more info)</i>. */
  @Override
  public int compareTo(UIDropdownItem oth) {
    return getID().compareToIgnoreCase(oth.getID());
  }

}