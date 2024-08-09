package PrEis.gui;
import java.util.ArrayList;
import processing.core.PFont;
import processing.core.PApplet;
import processing.core.PVector;
import PrEis.gui.Enums.AppFont;
import PrEis.gui.Enums.LabelType;
import PrEis.gui.Interfaces.ActionCallback;
import PrEis.gui.Interfaces.ToggleCallback;
import PrEis.gui.Interfaces.UpdateCallback;
import PrEis.utils.Cons;
import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;

public class UIManager {

  PApplet p;
  PFont textFont;
  PFont glyphFont;

  ArrayList<UIObject> objects;

  public UIManager(PApplet parent){
    p = parent;
    objects = new ArrayList<UIObject>();
  }

  public UIManager injectFonts(PFont tf, PFont gf){
    if(tf==null||gf==null){Cons.err_act(Err.NULL_XOR_INVALID, Act.RETURN_NO_ACTION);}
    else{textFont=tf; glyphFont=gf;}
    return this;
  }

  private UIObject bindUiObject(UIObject obj){
    objects.add(obj);
    return obj;
  }


  /** Calls `update` ∀ objects bound hereto. */
  public void update(){
    for (UIObject obj : objects){obj.update();}
  }

  /** Calls `onMousePressed` ∀ objects bound hereto. */
  public void onMousePressed(){
    for (UIObject obj : objects){obj.onMousePressed();}
  }

  /** Calls `onMouseWheel` ∀ objects bound hereto. */
  public void onMouseWheel(int v){
    for (UIObject obj : objects){obj.onMouseWheel(v);}
  }

  /** Calls `onKeyPressed` ∀ objects bound hereto. */
  public void onKeyPressed(){
    for (UIObject obj : objects){obj.onKeyPressed();}
  }

  /** Calls `render` ∀ objects bound hereto. */
  public void render(){
    for (UIObject obj : objects){obj.render();}
  }

  public ClickButton createClickButton(String txt, AppFont fnt, ActionCallback cbk, PVector pos, PVector dim){
    return (ClickButton)bindUiObject(new ClickButton(p,pos,dim).setLabel(txt).bindCallback(cbk).setFont(fnt));
  }

  public ClickButton createClickButton(String txt, ActionCallback cbk, PVector pos, PVector dim){
    return (ClickButton)bindUiObject(new ClickButton(p,pos,dim).setLabel(txt).bindCallback(cbk));
  }

  public ToggleButton createToggleButton(String txt, AppFont fnt, ToggleCallback cbk, PVector pos, PVector dim){
    return (ToggleButton)bindUiObject(new ToggleButton(p,pos,dim).setLabel(txt).bindCallback(cbk).setFont(fnt));
  }

  public ToggleButton createToggleButton(String txt, ToggleCallback cbk, PVector pos, PVector dim){
    return (ToggleButton)bindUiObject(new ToggleButton(p,pos,dim).setLabel(txt).bindCallback(cbk));
  }

  public ToggleButton createToggleButton(ToggleCallback cbk, AppFont fnt, PVector pos, PVector dim){
    return (ToggleButton)bindUiObject(new ToggleButton(p,pos,dim).bindCallback(cbk).setFont(fnt));
  }

  public ToggleButton createToggleButton(ToggleCallback cbk, PVector pos, PVector dim){
    return (ToggleButton)bindUiObject(new ToggleButton(p,pos,dim).bindCallback(cbk));
  }

  public Label createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim, LabelType typ, AppFont fnt){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt).bindCallback(cbk).setType(typ).setFont(fnt));
  }

  public Label createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim, LabelType typ){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt).bindCallback(cbk).setType(typ));
  }
    
  public Label createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim, AppFont fnt){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt).bindCallback(cbk).setFont(fnt));
  }
  
  public Label createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt).bindCallback(cbk));
  }
  
  public Label createLabel (UpdateCallback cbk, PVector pos, PVector dim, LabelType typ, AppFont fnt){
    return (Label)bindUiObject(new Label(p,pos,dim).bindCallback(cbk).setType(typ).setFont(fnt));
  }
  
  public  Label createLabel (UpdateCallback cbk, PVector pos, PVector dim, LabelType typ){
    return (Label)bindUiObject(new Label(p,pos,dim).bindCallback(cbk).setType(typ));
  }
  
  public Label createLabel (UpdateCallback cbk, PVector pos, PVector dim, AppFont fnt){
    return (Label)bindUiObject(new Label(p,pos,dim).bindCallback(cbk).setFont(fnt));
  }
  
  public Label createLabel (UpdateCallback cbk, PVector pos, PVector dim){
    return (Label)bindUiObject(new Label(p,pos,dim).bindCallback(cbk));
  }
  
  public Label createLabel (String txt, PVector pos, PVector dim, LabelType typ, AppFont fnt){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt).setType(typ).setFont(fnt));
  }
  
  public Label createLabel (String txt, PVector pos, PVector dim, LabelType typ){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt).setType(typ));
  }
  
  public Label createLabel (String txt, PVector pos, PVector dim, AppFont fnt){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt).setFont(fnt));
  }

  public Label createLabel (String txt, PVector pos, PVector dim){
    return (Label)bindUiObject(new Label(p,pos,dim).setLabel(txt));
  }

}