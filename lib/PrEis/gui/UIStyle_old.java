/*==============================================================================
|> WHAT IS THIS?   The 'old' definition of `UIStyle`. Swiped from EiSpriteViewer
|                  before I delete it and replace it with PrEis.gui.
|> WHY IS IT HERE? Keeping it temporarily for archive purposes as to review how
|                  I originally realized this (new version works differently) in
|                  case there are patterns/code that I find [new] utility for.
+=============================================================================*/

/*
public class UIStyle_old {
  int        swgt;
  int        txt_size;
  int        border_radius;
  TxtOri     txt_anchor;
  PVector    txt_offset;
  Dims       text_wrap;
  int        strk_enabled;
  int        strk_disabled;
  int        fill;
  int        fill_hovered;
  int        fill_clicked;
  int        fill_disabled;
  int        fill_on;
  int        fill_on_hovered;
  int        fill_on_clicked;
  int        fill_on_disabled;
  int        fill_off;
  int        fill_off_hovered;
  int        fill_off_clicked;
  int        fill_off_disabled;
  int        fill_opaque;
  int        strk_opaque;
  int        fill_transp;
  int        strk_transp;
  int        fill_txt;
  JSONObject stylesheet;
  JSONObject curSubsheet;
  
  public UIStyle(UIObjType t){
    stylesheet      = loadJSONObject("/data/styles.json");
//--[ LOAD STUFF COMMON TO ALL UIOBJECT TYPES ]--------------------------------#
    curSubsheet     = stylesheet.getJSONObject("COMMON");
    swgt            = curSubsheet.getInt("swgt");
    txt_size        = curSubsheet.getInt("txt_size");
    border_radius   = curSubsheet.getInt("border_radius");
    txt_anchor      = txtOriFromString(curSubsheet.getString("txt_anchor"));
    txt_offset      = vector2WithArray(curSubsheet.getJSONArray("txt_offset").toIntArray());
    strk_enabled    = colorFromIntArr(curSubsheet.getJSONArray("strk_enabled").toIntArray());
    strk_disabled   = colorFromIntArr(curSubsheet.getJSONArray("strk_disabled").toIntArray());
//--[ LOAD STUFF COMMON TO ALL UICLICKBUTTON TYPES ]---------------------------#
    if(t==UIObjType.CB){
      curSubsheet       = stylesheet.getJSONObject("CLICK");
      fill              = colorFromIntArr(curSubsheet.getJSONArray("fill").toIntArray());
      fill_hovered      = colorFromIntArr(curSubsheet.getJSONArray("fill_hovered").toIntArray());
      fill_clicked      = colorFromIntArr(curSubsheet.getJSONArray("fill_clicked").toIntArray());
      fill_disabled     = colorFromIntArr(curSubsheet.getJSONArray("fill_disabled").toIntArray());
      border_radius     = curSubsheet.getInt("border_radius");
    }
//--[ LOAD STUFF COMMON TO ALL UITOGGLEBUTTON TYPES ]--------------------------#
    else if(t==UIObjType.TB){
      curSubsheet       = stylesheet.getJSONObject("TOGGLE");
      fill_on           = colorFromIntArr(curSubsheet.getJSONArray("fill_on").toIntArray());
      fill_on_hovered   = colorFromIntArr(curSubsheet.getJSONArray("fill_on_hovered").toIntArray());
      fill_on_clicked   = colorFromIntArr(curSubsheet.getJSONArray("fill_on_clicked").toIntArray());
      fill_on_disabled  = colorFromIntArr(curSubsheet.getJSONArray("fill_on_disabled").toIntArray());
      fill_off          = colorFromIntArr(curSubsheet.getJSONArray("fill_off").toIntArray());
      fill_off_hovered  = colorFromIntArr(curSubsheet.getJSONArray("fill_off_hovered").toIntArray());
      fill_off_clicked  = colorFromIntArr(curSubsheet.getJSONArray("fill_off_clicked").toIntArray());
      fill_off_disabled = colorFromIntArr(curSubsheet.getJSONArray("fill_off_disabled").toIntArray());
      border_radius     = curSubsheet.getInt("border_radius");
    }
//--[ LOAD STUFF COMMON TO ALL UILABEL TYPES ]---------------------------------#
    else if(t==UIObjType.LB){
      curSubsheet       = stylesheet.getJSONObject("LABEL");
      fill_opaque       = colorFromIntArr(curSubsheet.getJSONArray("fill_opaque").toIntArray());
      strk_opaque       = colorFromIntArr(curSubsheet.getJSONArray("strk_opaque").toIntArray());
      fill_transp       = colorFromIntArr(curSubsheet.getJSONArray("fill_transp").toIntArray());
      strk_transp       = colorFromIntArr(curSubsheet.getJSONArray("strk_transp").toIntArray());
      strk_transp       = colorFromIntArr(curSubsheet.getJSONArray("strk_transp").toIntArray());      
      fill_txt          = colorFromIntArr(curSubsheet.getJSONArray("fill_txt").toIntArray());
    }
    else if (t==null){return;}
  }

  public void setPredefStyle(String style){
    if(style==null){return;}
    JSONObject curSubsheet = stylesheet.getJSONObject(style);
    if(curSubsheet==null){return;}
    try {txt_size = curSubsheet.getInt("txt_size");}
    catch (Exception e){System.out.println(e);}
  }
} // Ends Class UIStyle
*/