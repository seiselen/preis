package PrEis.utils;
import processing.core.PApplet;
import processing.core.PVector;


public class Pgfx {

  /**
   * Used for <code>textWithShadow</code> functions. Not actually stroke weight,
   * but +=  */
  public static int shadowWeight = 1;




  /** Syntax sugar for calling `noFill` then setting `stroke`; s.t. stroke is <code>{r,g,b,a} ints</code>. */
  public static void strokenofill(PApplet p, int r, int g, int b){p.noFill(); p.stroke(r,g,b);}

  /** Syntax sugar for calling `noFill` then setting `stroke`; s.t. stroke is <code>{r,g,b,a} ints</code>. */
  public static void strokenofill(PApplet p, int r, int g, int b, int a){p.noFill(); p.stroke(r,g,b,a);}

  /** Syntax sugar for calling `noFill` then setting `stroke`; s.t. fill is a <code> color int</code>. */
  public static void strokenofill(PApplet p, int c){p.noFill(); p.stroke(c);}  

  /** Syntax sugar for calling `noStroke` then setting `fill`; s.t. fill is <code>{r,g,b} ints</code>. */
  public static void fillnostroke(PApplet p, int r, int g, int b){p.noStroke(); p.fill(r,g,b);}

  /** Syntax sugar for calling `noStroke` then setting `fill`; s.t. fill is <code>{r,g,b,a} ints</code>. */
  public static void fillnostroke(PApplet p, int r, int g, int b, int a){p.noStroke(); p.fill(r,g,b,a);}

  /** Syntax sugar for calling `noStroke` then setting `fill`; s.t. fill is a <code> color int</code>. */
  public static void fillnostroke(PApplet p, int c){p.noStroke(); p.fill(c);}


  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  |# Note On `rectMode`|`ellipseMode` Support: Both are (thankfully) exposed as
  |  read-only public props of the `PApplet` instance; s.t. I should be able to
  |  easily reference them within a switch handler.
  +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  /** Syntax Sugar for <code>p.rect</code> with {@link BBox} input. */
  public static void rect(PApplet p, BBox bbox){
    p.rect(bbox.minX(), bbox.minY(), bbox.dimX(), bbox.dimY());
  }

  /** Syntax Sugar for <code>p.rect</code> with {@link BBox} input and border radius. */
  public static void rect(PApplet p, BBox bbox, float bRad){
    p.rect(bbox.minX(), bbox.minY(), bbox.dimX(), bbox.dimY(), bRad);
  }

  /** Syntax Sugar for <code>p.rect</code> with {@link PVector} input. */
  public static void rect(PApplet p, PVector vecTL, PVector vecBR){
    p.rect(vecTL.x, vecTL.y, vecBR.x, vecBR.y);
  }

  /** Syntax Sugar for <code>p.rect</code> with {@link PVector} input and border radius. */
  public static void rect(PApplet p, PVector vecTL, PVector vecBR, float bRad){
    p.rect(vecTL.x, vecTL.y, vecBR.x, vecBR.y, bRad);
  }


  public static void clip(PApplet p, BBox bbox){
    p.clip(bbox.minX(), bbox.minY(), bbox.dimX(), bbox.dimY());
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