package PrEis.test;
import PrEis.utils.PGfxUtils;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @implNote Should do tests for the other functions, but they're simple enough
 * implementations proven in-app that I'm not worried thereof; so KISS for now!
 */
public class TestPGfxUtils {

  public static void testRender(PApplet p){
    p.background(0,160,255);
    p.textAlign(PApplet.CENTER, PApplet.CENTER);
    p.textSize(32);

    PGfxUtils.shadowWeight = 1;
    PGfxUtils.textWithShadow(
      p, "# TEST (POS{X,Y} OVERLOAD, WGT=[1]) #", p.color(255), p.color(0), p.width*0.5f, p.height*0.25f
    );

    PGfxUtils.shadowWeight = 2;
    PGfxUtils.textWithShadow(
      p, "# TEST (PVECTOR OVERLOAD, WGT=[3]) #", p.color(255), p.color(0), new PVector(p.width*0.5f, p.height*0.75f)
    );
  }
  
}
