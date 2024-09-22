package PrEis.utils;
import processing.core.PApplet;
import processing.core.PVector;


public class Pgfx {

  /**
   * Used for <code>textWithShadow</code> functions. Not actually stroke weight,
   * but +=  */
  public static int shadowWeight = 1;

  /*=[@TODO: DEPRECATE THESE?]================================================*/
  public static void strokenofill(PApplet p, int r, int g, int b){p.noFill(); p.stroke(r,g,b);}
  public static void strokenofill(PApplet p, int r, int g, int b, int a){p.noFill(); p.stroke(r,g,b,a);}
  public static void fillnostroke(PApplet p, int r, int g, int b){p.noStroke(); p.fill(r,g,b);}
  public static void fillnostroke(PApplet p, int r, int g, int b, int a){p.noStroke(); p.fill(r,g,b,a);}


  /** Syntax Sugar: Calls `noFill` then sets `stroke` WRT input <code>{color:int}</code>. */
  public static void strokenofill(PApplet p, int c){p.noFill(); p.stroke(c);}  

  /** Syntax Sugar: Calls `strokeWeight` then calls {@link #strokenofill} WRT input . */
  public static void wstrokenofill(PApplet p, int strk, int swgt){p.strokeWeight(swgt); strokenofill(p, strk);}  

  /** Syntax Sugar: Calls `noStroke` then sets `fill` WRT input <code>{color:int}</code>. */
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






  /** 
   * Simple Text Shadow realization via set of offset `text(⋯)` calls. This is a
   * QAD more customizable version to suit 'in-the-field' demand via integrating
   * <b>PrEis</b> into the two <b>EisDooM</b> apps.
   * @param app - `PApplet` of caller
   * @param txt - text to be rendered
   * @param pos - `PVector` text position (will render via `CENTER` orientation)
   * @param siz - text size
   * @param wgt - offset of the 4 bg 'shadow' text renders
   * @param col - `Int[2]` of semantics `{SHAD, TEXT}`. If null: will use default colors
   * @implNote default colors: `{SHAD:BLACK, TEXT:WHITE}`, both fully opaque i.e. no alpha
   * @implNote no input validation... this is a distraction of a distraction A.T.M.
   */
  public static void textWithShadow(PApplet app, String txt, PVector pos, int siz, int wgt, int[] col){
    app.fill(col==null ? app.color(0) : col[0]);
    app.text(txt, pos.x-wgt, pos.y);
    app.text(txt, pos.x+wgt, pos.y);
    app.text(txt, pos.x, pos.y-wgt);
    app.text(txt, pos.x, pos.y+wgt);
    app.fill(col==null ? app.color(255) : col[1]);
    app.text(txt, pos.x, pos.y);
  }


  public static PVector mousePtToVec(PApplet app){
    return new PVector(app.mouseX, app.mouseY);
  }

  public static boolean mouseInCanvas(PApplet app){
    return app.mouseX >= 0 && app.mouseX <= app.width && app.mouseY >= 0 && app.mouseY <= app.height;
  }

  /** @implNote Note: is WRT current text settings WRT `PApplet`. */
  public static int textMaxHeight(PApplet app){
    return Math.round(app.textAscent() + app.textDescent());
  }

}