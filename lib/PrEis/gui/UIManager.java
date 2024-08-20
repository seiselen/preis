package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import processing.core.PFont;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class UIManager {

  PApplet app;
  private static PFont labelFont;
  private static PFont glyphFont;

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

  public UIClick createUIClick(BBox box, String txt, AppFont fnt, IActionCallback cbk){
    switch(fnt){
      case TEXT  : return new UIClick(app,box).withLabel(txt).bindCallback(cbk).bindManager(this);
      case GLYPH : return new UIClick(app,box).withGlyph(txt).bindCallback(cbk).bindManager(this);
      default    : Cons.err(Err.SWITCH_DROP_OUT); return null;
    }
  }
  public UIClick createUIClick(PVector pos, PVector dim, String txt, AppFont fnt, IActionCallback cbk){
    return createUIClick(new BBox(pos, dim), txt, fnt, cbk);
  } 

  
  public UIToggle createUIToggle(BBox box, String txt, AppFont fnt, IToggleCallback cbk){
    switch(fnt){
      case TEXT  : return new UIToggle(app,box).withLabel(txt).bindCallback(cbk).bindManager(this);
      case GLYPH : return new UIToggle(app,box).withGlyph(txt).bindCallback(cbk).bindManager(this);
      default    : Cons.err(Err.SWITCH_DROP_OUT); return null;
    }
  }
  public UIToggle createUIToggle(PVector pos, PVector dim, String txt, AppFont fnt, IToggleCallback cbk){
    return createUIToggle(new BBox(pos, dim), txt, fnt, cbk);
  }


  public UILabel createUILabel(BBox box, String txt, AppFont fnt, LabelType typ, IUpdateCallback cbk){
    switch(fnt){
      case TEXT  : return new UILabel(app,box).withLabel(txt, typ).bindCallback(cbk).bindManager(this);
      case GLYPH : return new UILabel(app,box).withGlyph(txt, typ).bindCallback(cbk).bindManager(this);
      default    : Cons.err(Err.SWITCH_DROP_OUT); return null;
    }
  }
  public UILabel createUILabel(PVector pos, PVector dim, String txt, AppFont fnt, LabelType typ, IUpdateCallback cbk){
    return createUILabel(new BBox(pos, dim), txt, fnt, typ, cbk);
  }


}