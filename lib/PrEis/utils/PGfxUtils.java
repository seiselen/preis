package PrEis.utils;
import processing.core.PApplet;
import processing.core.PVector;

public class PGfxUtils {

  /**
   * Used for <code>textWithShadow</code> functions. Not actually stroke weight,
   * but +=  */
  public static int shadowWeight = 1;

  /**
   * Syntax sugar for calling `noFill` then setting `stroke`.
   * @param p - `PApplet` of caller
   * @param r - RED   channel value
   * @param g - GREEN channel value
   * @param b - BLUE  channel value
   */
  public static void strokenofill(PApplet p, int r, int g, int b){
    p.noFill();
    p.stroke(r,g,b);
  }

  /**
   * Syntax sugar for calling `noFill` then setting `stroke`.
   * @param r - RED   channel value
   * @param g - GREEN channel value
   * @param b - BLUE  channel value
   * @param a - ALPHA channel value
   */
  public static void strokenofill(PApplet p, int r, int g, int b, int a){
    p.noFill();
    p.stroke(r,g,b,a);
  }

  /**
   * Syntax sugar for calling `noStroke` then setting `fill`.
   * @param p - `PApplet` of caller
   * @param r - RED   channel value
   * @param g - GREEN channel value
   * @param b - BLUE  channel value
   */
  public static void fillnostroke(PApplet p, int r, int g, int b){
    p.noStroke();
    p.fill(r,g,b);
  }

  /**
   * Syntax sugar for calling `noStroke` then setting `fill`.
   * @param r - RED   channel value
   * @param g - GREEN channel value
   * @param b - BLUE  channel value
   * @param a - ALPHA channel value
   */  
  public static void fillnostroke(PApplet p, int r, int g, int b, int a){
    p.noStroke();
    p.fill(r,g,b,a);
  }

  /**
   * Renders Vertical Line.
   * @param p  - `PApplet` of caller
   * @param x  - `X` position of both endpoints
   * @param y1 - `Y` position of first segment endpoint
   * @param y2 - `Y` position of second segment endpoint
   */
  public static void linev(PApplet p, float x, float y1, float y2){
    p.line(x,y1,x,y2);
  }

  /**
   * Renders Horizontal Line.
   * @param p  - `PApplet` of caller
   * @param x1 - `X` position of first segment endpoint
   * @param x2 - `Y` position of second segment endpoint
   * @param y  - `Y` position of both endpoints
   */
  public static void lineh(PApplet p, float x1, float x2, float y){
    p.line(x1,y,x2,y);
  }

  /** 
   * Simple realization of Text Shadow, via set of `text(⋯)` calls.
   * @param p     - `PApplet` of caller
   * @param txt   - text to be rendered
   * @param colFG - non-shadow (i.e. foreground) color; should contrast with `colBG`
   * @param colBG - shadow (i.e. background) color; should contract with `colFG`
   * @param posX  - text `X` position; s.t. shadows render offset hereto
   * @param posY  - text `Y` position; s.t. shadows render offset hereto
   */
  public static void textWithShadow(PApplet p, String txt, int colFG, int colBG, float posX, float posY){
    p.fill(colBG);
    p.text(txt,posX-shadowWeight,posY);
    p.text(txt,posX+shadowWeight,posY);
    p.text(txt,posX,posY-shadowWeight);
    p.text(txt,posX,posY+shadowWeight);
    p.fill(colFG);
    p.text(txt,posX,posY);
  }

  /** 
   * Simple realization of Text Shadow, via set of `text(⋯)` calls.
   * @param p     - `PApplet` of caller
   * @param txt   - text to be rendered
   * @param colFG - non-shadow (i.e. foreground) color; should contrast with `colBG`
   * @param colBG - shadow (i.e. background) color; should contract with `colFG`
   * @param pos   - text position; s.t. shadows render offset hereto
   */
  public static void textWithShadow(PApplet p, String txt, int colFG, int colBG, PVector pos){
    textWithShadow(p, txt, colFG, colBG, pos.x, pos.y);
  }

}