package PrEis.gui;
import PrEis.utils.StringUtils;

/** Enumeration of UIObject Types. */
public enum WidgetType {
  /** <b>ClickButton</b>   */ CB,
  /** <b>ConfirmButton</b> */ FB,
  /** <b>ToggleButton</b>  */ TB,
  /** <b>Label</b>         */ LB,
  /** <b>Container</b>     */ CO,
  /** <b>Dropdown</b>      */ DD,
  /** <b>DropdownItem</b>  */ DI,
  /** <b>Image</b>         */ IM,
  /** GENERIC WIDGET       */ NA;

  public static String typeToName(WidgetType t){
    switch(t){
      case CB: return "Click Button";
      case CO: return "Container";
      case DD: return "Dropdown";
      case DI: return "Dropdown Item";
      case FB: return "Confirm Button";
      case LB: return "Label";
      case TB: return "Toggle Button";
      case IM: return "Image";
      default: return "Unknown Type";
    }
  }

  public static String typeToNameAndID(WidgetType t){
    return typeToName(t)+" "+StringUtils.wrapParens(t.toString());
  }

  public String toName(){return WidgetType.typeToName(this);}
  
  public String toNameAndID(){return typeToNameAndID(this);}  
}