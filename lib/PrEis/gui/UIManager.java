package PrEis.gui;
import processing.core.PApplet;
import java.util.ArrayList;

public class UIManager {
  PApplet app;
  ArrayList<UIObject> objects;

  public UIManager(PApplet parent){
    app = parent;
    objects = new ArrayList<UIObject>();
  }

  public UIObject bindUiObject(UIObject obj){
    objects.add(obj);
    return obj;
  }

  /** Calls `update` ∀ objects bound hereto. */
  public void update(){for (UIObject obj : objects){obj.update();}}

  /** Calls `onKeyPressed` ∀ objects bound hereto. */
  public void onKeyPressed(){for (UIObject obj : objects){obj.onKeyPressed();}}

  /** Calls `onMousePressed` ∀ objects bound hereto. */
  public void onMousePressed(){for (UIObject obj : objects){obj.onMousePressed();}}

  /** Calls `onMouseWheel` ∀ objects bound hereto. */
  public void onMouseWheel(int v){for (UIObject obj : objects){obj.onMouseWheel(v);}}

  /** Calls `render` ∀ objects bound hereto. */
  public void render(){
    for (UIObject obj : objects){obj.render();}
    for (UIObject obj : objects){obj.lateRender();}
  }

}