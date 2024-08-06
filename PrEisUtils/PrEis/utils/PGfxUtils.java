package PrEis.utils;
import processing.core.PApplet;

public class PGfxUtils {
  
  private PApplet p;

  public PGfxUtils(PApplet parent){p = parent;}

  public void strokenofill(int r, int g, int b){p.noFill(); p.stroke(r,g,b);}

  public void strokenofill(int r, int g, int b, int a){p.noFill(); p.stroke(r,g,b,a);}

  public void fillnostroke(int r, int g, int b){p.noStroke(); p.fill(r,g,b);}

  public void fillnostroke(int r, int g, int b, int a){p.noStroke(); p.fill(r,g,b,a);}

  /*> Renders Vertical Line. */
  public void linev(float x, float y1, float y2){p.line(x,y1,x,y2);}

  /*> Renders Horizontal Line. */
  public void lineh(float x1, float x2, float y){p.line(x1,y,x2,y);}

}