package PrEis.gui;
import PrEis.utils.Cons;
import PrEis.utils.QueryUtils;
import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import processing.core.PFont;
import processing.core.PApplet;
import java.util.ArrayList;

public class UIManager {

  PApplet app;
  public static PFont labelFont;
  public static PFont glyphFont;

  ArrayList<UIObject> objects;

  public UIManager(PApplet parent){
    app = parent;
    objects = new ArrayList<UIObject>();
  }

  public UIManager injectFonts(PFont tf, PFont gf){
    if(tf==null||gf==null){Cons.err_act(Err.NULL_XOR_INVALID, Act.RETURN_NO_ACTION);}
    else{labelFont=tf; glyphFont=gf;}
    return this;
  }

  public UIObject bindUiObject(UIObject obj){
    objects.add(obj);
    return obj;
  }

  public static PFont getFont(AppFont f){
    switch(f){
      case TEXT  : return labelFont;
      case GLYPH : return glyphFont;
      default    : Cons.err_act(Err.SWITCH_DROP_OUT, Act.RETURN_NULL); return null;
    }
  }

  public void setFont(AppFont f){
    if(QueryUtils.nullAll(labelFont,glyphFont==null)){
      Cons.err_act(Err.NULL_VALUE, Act.RETURN_NO_ACTION, "one ore more fonts");
      return;
    }
    switch(f){
      case TEXT  : app.textFont(labelFont); return;
      case GLYPH : app.textFont(glyphFont); return;
      default    : Cons.err_act(Err.SWITCH_DROP_OUT, Act.RETURN_NULL);
    }
  }

  /** Calls `update` ∀ objects bound hereto. */
  public void update(){
    for (UIObject obj : objects){obj.update();}
  }

  /** Calls `onKeyPressed` ∀ objects bound hereto. */
  public void onKeyPressed(){
    for (UIObject obj : objects){obj.onKeyPressed();}
  }

  /** Calls `onMousePressed` ∀ objects bound hereto. */
  public void onMousePressed(){
    for (UIObject obj : objects){obj.onMousePressed();}
  }

  /** Calls `onMouseWheel` ∀ objects bound hereto. */
  public void onMouseWheel(int v){
    for (UIObject obj : objects){obj.onMouseWheel(v);}
  }

  /** Calls `render` ∀ objects bound hereto. */
  public void render(){
    for (UIObject obj : objects){obj.render();}
  }


}