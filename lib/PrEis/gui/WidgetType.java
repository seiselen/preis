package PrEis.gui;
import PrEis.utils.StringUtils;

/** 
 * Enumeration of UIObject Types.
 * {@link #CB} {@link #FB} {@link #TB} {@link #LB} {@link #CO} {@link #DD} {@link #DI} 
 */
public enum WidgetType {
  /** ClickButton   */ CB,
  /** ConfirmButton */ FB,
  /** ToggleButton  */ TB,
  /** Label         */ LB,
  /** Container     */ CO,
  /** Dropdown      */ DD,
  /** DropdownItem  */ DI;

  public static String typeToName(WidgetType t){
    switch(t){
      case CB: return "Click Button";
      case CO: return "Container";
      case DD: return "Dropdown";
      case DI: return "Dropdown Item";
      case FB: return "Confirm Button";
      case LB: return "Label";
      case TB: return "Toggle Button";
      default: return "Unknown Type";
    }
  }

  public static String typeToNameAndID(WidgetType t){
    return typeToName(t)+" "+StringUtils.wrapParens(t.toString());
  }

  public String toName(){return WidgetType.typeToName(this);}
  
  public String toNameAndID(){return typeToNameAndID(this);}  
}