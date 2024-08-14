package PrEis.gui;
import PrEis.utils.FormatUtils;
import processing.core.PApplet;
import processing.data.JSONObject;
import PrEis.utils.Cons;

public class UIStyle {
  int     swgt;
  int     txt_size;
  int     border_radius;
  TxtOri  txt_anchor;
  int     txt_offset_x;
  int     txt_offset_y;
  int     strk_enabled;
  int     strk_disabled;
  int     fill;
  int     fill_hovered;
  int     fill_clicked;
  int     fill_disabled;
  int     fill_on;
  int     fill_on_hovered;
  int     fill_on_clicked;
  int     fill_on_disabled;
  int     fill_off;
  int     fill_off_hovered;
  int     fill_off_clicked;
  int     fill_off_disabled;
  int     fill_opaque;
  int     strk_opaque;
  int     fill_transp;
  int     strk_transp;
  int     fill_txt; //> used for labels, especially transparent
  float   txt_off_pct;

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
    txt_anchor      = TxtOri.withString(curSubsheet.getString("txt_anchor"));
    txt_offset_x    = curSubsheet.getInt("txt_offset_x");
    txt_offset_y    = curSubsheet.getInt("txt_offset_y");    
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

  //> Loads Custom [Sub-]Styles. WARNING: You must maintain this manually for now!!!
  public void setPredefStyle(String style){
    if(style==null){return;}
    JSONObject curSubsheet = stylesheet.getJSONObject(style);
    if(curSubsheet==null){return;}

    try {txt_size = curSubsheet.getInt("txt_size");} catch (Exception e){}
  }

}