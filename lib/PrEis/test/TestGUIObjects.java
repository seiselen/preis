package PrEis.test;

import PrEis.gui.UIManager;
import processing.core.PApplet;
import processing.core.PImage;

public class TestGUIObjects {

  private PApplet   app;
  private UIManager uim;
  private PImage    img;

  public TestGUIObjects(PApplet p){
    app = p;
    uim = new UIManager(app);
  }

  public TestGUIObjects bindBGImg(PImage i){img=i; return this;}

  public void update(){uim.update();}

  public void onKeyPressed(){uim.onKeyPressed();}
  
  public void onMousePressed(){uim.onMousePressed();}
  
  public void onMouseWheel(int v){uim.onMouseWheel(v);}

  public void testRender(){
    if(img!=null){app.imageMode(PApplet.CORNER); app.image(img, 0, 0);}
    uim.render();
  }

}
