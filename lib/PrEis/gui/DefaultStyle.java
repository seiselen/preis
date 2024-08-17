package PrEis.gui;
import processing.core.PApplet;

/** 
 * Defines default i.e. 'sufficient minimum' style values for all UIObject types
 * s.t. all can be used within an app without providing a stylesheet; analogous
 * (AFAIK) to how HTML docs without provided CSS work. 
 * <ul>
 * <li> 
 * Note that the values are hardcoded i.e. NOT fetched from a JSON. This allows
 * PrEis-GUI to initialize default styles independently within its code; s.t. a
 * a consuming applet is not obliged to add some template json 'or else'.
 * </li>
 * <li>
 * Note also that UIStyle will first populate these values (resp. to a widget's
 * type); and then handle any specified stylesheets thereafter. Ergo we get the 
 * best of both realizations: built-in initialization of 'bare default' values;
 * as well as continued support for fully procedural stylesheet JSONs.
 * </li>
 * <li>
 * Note also that UIStyle will first populate these values (resp. to a widget's
 * type); and then handle any specified stylesheets thereafter. Ergo we get the 
 * best of both realizations: built-in initialization of 'bare default' values;
 * as well as continued support for fully procedural stylesheet JSONs.
 * </ul>
 */
class DefaultStyle {

  static UIStyle Get(PApplet p){
    return _CommonVals(p);
  }

  static UIStyle Get(PApplet p, WidgetType style){
    if(style==null){return Get(p);}
    switch (style){
      case CB: return _ClickButton(p);
      case FB: return _ConfirmButton(p);
      case TB: return _ToggleButton(p);
      case LB: return _Label(p);
      case DI: return _DropdownItem(p);
      case DD:
      case CO:    
      default: return Get(p);
    }
  }

  private static UIStyle _CommonVals(PApplet p){
    UIStyle s = new UIStyle();
    s.swgt          = 2;
    s.txt_size      = 24;
    s.txt_anchor    = TxtOri.CC;
    s.txt_offset_x  = 0;
    s.txt_offset_y  = 0;
    s.strk_enabled  = p.color(255, 255, 255);
    s.strk_disabled = p.color(192, 192, 192);
    s.border_radius = 0;
    return s;
  }

  private static UIStyle _ClickButton(PApplet p){
    UIStyle s = _CommonVals(p);
    s.fill          = p.color(  0, 112, 255);
    s.fill_hovered  = p.color(  0, 144, 255);
    s.fill_clicked  = p.color(  0, 176, 255);
    s.fill_disabled = p.color(  0,  80, 228);
    s.border_radius = 8;
    return s;
  }

  private static UIStyle _ToggleButton(PApplet p){
    UIStyle s = _CommonVals(p);
    s.fill_on           = p.color(255, 112,   0);
    s.fill_on_hovered   = p.color(255, 144,   0);
    s.fill_on_clicked   = p.color(255, 176,   0);
    s.fill_on_disabled  = p.color(228,  80,   0);
    s.fill_off          = p.color(  0, 112, 255);
    s.fill_off_hovered  = p.color(  0, 144, 255);
    s.fill_off_clicked  = p.color(  0, 176, 255);
    s.fill_off_disabled = p.color(  0,  80, 228);
    s.border_radius     = 8;
    return s;
  }

  private static UIStyle _Label(PApplet p){
    UIStyle s = _CommonVals(p);
    s.fill_opaque       = p.color(255, 255, 255);
    s.strk_opaque       = p.color(  0, 112, 255);
    s.fill_transp       = p.color(  0,   0,  32,  32);
    s.strk_transp       = p.color(255, 255, 255, 128);
    s.fill_txt          = p.color(255, 255, 255);
    return s;
  }

  private static UIStyle _DropdownItem(PApplet p){
    UIStyle s = _CommonVals(p);
    s.fill         = p.color(  0, 112, 255);
    s.fill_hovered = p.color(  0, 144, 255);
    s.fill_clicked = p.color(  0, 176, 255);
    s.fill_txt     = p.color(255);
    s.txt_size     = 20;
    s.strk_enabled = p.color(255);
    s.swgt         = 2;
    s.txt_off_pct  = 0.1f;
    return s;
  }

  /** Gonna hack with ToggleButtons vars for now. */
  private static UIStyle _ConfirmButton(PApplet p){
    UIStyle s = _CommonVals(p);
    s.fill_on         = p.color(  0,   0,  80);
    s.fill_on_hovered = p.color(  0,   0, 114);
    s.fill_on_clicked = p.color(  0,   0, 144);
    s.strk_enabled    = p.color(255);
    s.txt_size        = 32;
    s.strk_enabled    = p.color(255);
    s.swgt            = 2;
    /*----------------------------------*/
    s.fill_on         = p.color( 80,   0,   0);
    s.fill_on_hovered = p.color(114,   0,   0);
    s.fill_on_clicked = p.color(144,   0,   0);
    s.strk_enabled    = p.color(255);
    s.txt_size        = 32;
    s.strk_enabled    = p.color(255);
    s.swgt            = 2;
    return s;
  }

}