package PrEis.gui;
import PrEis.utils.FormatUtils;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONObject;
import PrEis.utils.Cons;
import PrEis.utils.DataStructUtils;
import java.lang.reflect.*;

/**
 * @implNote ALL state is set to <code>public</code> for now until I figure out
 * a better means of encapsulation (e.g. reflection); and until I have time to
 * focus hereto (alongside the other <b>UIObject</b> 'open sore' design issues).
 */
public class UIStyle {
  public int     swgt;
  public int     txt_size;
  public int     border_radius;
  public PosOri  txt_anchor;
  public PVector txt_offset;
  public int     strk_enabled;
  public int     strk_disabled;
  public int     fill;
  public int     fill_hovered;
  public int     fill_clicked;
  public int     fill_disabled;
  public int     fill_on;
  public int     fill_on_hovered;
  public int     fill_on_clicked;
  public int     fill_on_disabled;
  public int     fill_off;
  public int     fill_off_hovered;
  public int     fill_off_clicked;
  public int     fill_off_disabled;
  public int     fill_opaque;
  public int     strk_opaque;
  public int     fill_transp;
  public int     strk_transp;
  public int     fill_txt; //> used for labels, especially transparent
  public float   txt_off_pct;

  public int txt_size_ttip;

  //> these are buffers; unused after constructor returns
  JSONObject stylesheet;
  JSONObject curSubsheet;
  PApplet p;

  /** This constructor is ONLY to be used with {@link DefaultStyles} factory. */
  public UIStyle(){}

  public void injectStylesheet(PApplet parent, WidgetType type){
    p = parent;
    stylesheet = p.loadJSONObject("/data/styles.json");
    loadCommonStyles();
    if(type==null){return;}
    switch (type) {
      case CB: loadClickButtonStyles(); return;
      case TB: loadToggleButtonStyles(); return;
      case LB: loadLabelStyles(); return;
      default: Cons.warn_UIStyleConstructor(); return;
    }

  }

  private void loadCommonStyles(){
    curSubsheet     = stylesheet.getJSONObject("COMMON");
    swgt            = curSubsheet.getInt("swgt");
    txt_size        = curSubsheet.getInt("txt_size");
    border_radius   = curSubsheet.getInt("border_radius");
    txt_anchor      = PosOri.withString(curSubsheet.getString("txt_anchor"));
    txt_offset      = DataStructUtils.createVector(curSubsheet.getJSONArray("txt_offset").toFloatArray()); 
    strk_enabled    = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("strk_enabled").toIntArray());
    strk_disabled   = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("strk_disabled").toIntArray());
  }

  private void loadClickButtonStyles(){
    curSubsheet       = stylesheet.getJSONObject("CLICK");
    fill              = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill").toIntArray());
    fill_hovered      = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_hovered").toIntArray());
    fill_clicked      = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_clicked").toIntArray());
    fill_disabled     = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_disabled").toIntArray());
    border_radius     = curSubsheet.getInt("border_radius");
  }

  private void loadToggleButtonStyles(){
    curSubsheet       = stylesheet.getJSONObject("TOGGLE");
    fill_on           = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_on").toIntArray());
    fill_on_hovered   = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_on_hovered").toIntArray());
    fill_on_clicked   = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_on_clicked").toIntArray());
    fill_on_disabled  = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_on_disabled").toIntArray());
    fill_off          = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_off").toIntArray());
    fill_off_hovered  = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_off_hovered").toIntArray());
    fill_off_clicked  = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_off_clicked").toIntArray());
    fill_off_disabled = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_off_disabled").toIntArray());
    border_radius     = curSubsheet.getInt("border_radius");
  }

  private void loadLabelStyles(){
    curSubsheet       = stylesheet.getJSONObject("LABEL");
    fill_opaque       = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_opaque").toIntArray());
    strk_opaque       = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("strk_opaque").toIntArray());
    fill_transp       = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_transp").toIntArray());
    strk_transp       = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("strk_transp").toIntArray());
    strk_transp       = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("strk_transp").toIntArray());      
    fill_txt          = FormatUtils.colorFromIntArr(curSubsheet.getJSONArray("fill_txt").toIntArray());
  }

  /** 
   * Given valid style prop name, class type, and desired value: sets the value
   * accordingly. For primitive types: you must specify the appropriate wrapper
   * class (e.g. <code>int→Integer</code>); though you may pass primitive values
   * as input arguments via unboxing/autoboxing magic (see example below).
   * @example <pre>{@code setStyleProp("txt_size", Integer.class, 32);}</pre>
   */
  public <T> void setStyleProp(String propName, Class<T> propType, Object propValue){
    try{
      Field propField = UIStyle.class.getDeclaredField(propName);
      if(propField==null){Cons.err("Prop Name '"+propName+"' cannot be found!");}
      else if(propType.getName()==Integer.class.getName()){propField.setInt(this, (Integer)propValue);}
      else if(propType.getName()==Float.class.getName()){propField.setFloat(this, (Float)propValue);}
      else if(propType.getName()==PVector.class.getName()){propField.set(this, (PVector)propValue);}
      else if(propType.getName()==PosOri.class.getName()){propField.set(this, (PosOri)propValue);}
    }
    catch(Exception e){
      Cons.err("Failed to set style of prop '"+propName+"'. Err string to follow...", e.toString());
    }
  }

}