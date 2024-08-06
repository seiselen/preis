import PrEisUtils.PGfxUtils;
import PrEisUtils.StringUtils;
import processing.core.PApplet;


public class PrEisTester{

  PApplet p;
  PGfxUtils g;

  public PrEisTester(PApplet in_p){
    p = in_p;
    g = new PGfxUtils(in_p);
  }

  public void testGFXUtils(){
    p.stroke(0);
    p.strokeWeight(2);
    g.lineh(0, p.width, p.height/2);
    g.linev(p.width/2, 0, p.height);
  }


  public static void testStringUtils(){
    System.out.println(StringUtils.concatAsSCSV("hi","there"));
  }

}

