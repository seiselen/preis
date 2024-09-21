package PrEis.gui;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.StringUtils;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class UIImage extends UIObject {

  public enum ImageSpecial {STRETCH_TO_FIT, SCALE_TO_FIT, CENTER_ONLY};
  private ImageSpecial mySpecial;

  private PImage img;
  private PVector imgDim;
  private boolean debugBoxViz;

  public UIImage(PApplet iApp, PVector iPos, PVector iDim){
    this(iApp, new BBox(iPos, iDim));
  }

  public UIImage(PApplet iApp, BBox iBox){
    super(iApp, iBox, WidgetType.IM);
    img = null;
    imgDim = new PVector();
    mySpecial = ImageSpecial.CENTER_ONLY;
    debugBoxViz = false;
  }

  public static UIImage create(PApplet app, BBox box, PImage img){
    return new UIImage(app, box).bindImageΘ(img);
  }

  public static UIImage create(PApplet app, PVector pos, PVector dim, PImage img){
    return create(app, new BBox(pos, dim),img);
  }

  public static UIImage create(UIManager mgr, BBox box, PImage img){
    return create(mgr.app, box, img).bindManager(mgr).castTo(UIImage.class);
  }

  public static UIImage create(UIManager mgr, PVector pos, PVector dim, PImage img){
    return create(mgr, new BBox(pos, dim), img);
  }

  public void bindImage(PImage in_img){
    if(in_img==null||in_img.width==0||in_img.height==0){return;}
    img = in_img;
    computeImgDim();
  }
  
  public UIImage bindImageΘ(PImage img){
    bindImage(img); return this;
  }

  public void as(ImageSpecial iSpecial){mySpecial = iSpecial;}
  public UIImage asΘ(ImageSpecial iSpecial){as(iSpecial); return this;}

  public void bindSpecial(ImageSpecial iSpecial){as(iSpecial);}  
  public UIImage bindSpecialΘ(ImageSpecial iSpecial){return asΘ(iSpecial);}

  
  public void doDebugViz(){debugBoxViz = true;}
  public UIImage doDebugVizΘ(){doDebugViz(); return this;}

  public void computeImgDim(){
    //> Scenario 'W' in which image scales WRT matching box width
    PVector imgDimViaWide = new PVector(bbox.wide(), (bbox.wide()/img.width)*img.height);
    //> Scenario 'H' in which image scales WRT matching box height
    PVector imgDimViaTall = new PVector((bbox.tall()/img.height)*img.width, bbox.tall());

    /*>>> JUSSSSSSSSSSSST IN CASE...
    Cons.log(
      StringUtils.charTimesN('#', 32),
      "img  dims --------------> "+StringUtils.wrapParens(StringUtils.concatAsCSV(img.width,img.height)),
      "bbox dims --------------> "+StringUtils.wrapParens(StringUtils.concatAsCSV(bbox.wide(),bbox.tall())),
      "img.width/bbox.wide() --> "+StringUtils.wrapParens(""+(img.width/bbox.wide())),
      "img.height/bbox.tall() -> "+StringUtils.wrapParens(""+(img.height/bbox.tall())),
      "imgDimViaWide ----------> "+StringUtils.wrapParens(StringUtils.concatAsCSV(imgDimViaWide.x,imgDimViaWide.y)),
      "imgDimViaTall ----------> "+StringUtils.wrapParens(StringUtils.concatAsCSV(imgDimViaTall.x,imgDimViaTall.y))
    );
    */

    //> Scenario 'W' yields invalid image height: Assign Scenario 'H' and return
    if(imgDimViaWide.y > bbox.tall()){imgDim = imgDimViaTall; return;}

    //> Scenario 'H' yields invalid image width: Assign Scenario 'W' and return
    if(imgDimViaTall.x > bbox.wide()){imgDim = imgDimViaWide; return;}

    //> Both scenarios valid (usually 'scale-up' case): assign one of max area
    double imgAreaViaWide = imgDimViaWide.x * imgDimViaWide.y;
    double imgAreaViaTall = imgDimViaTall.x * imgDimViaTall.y;
    if(imgAreaViaWide > imgAreaViaTall){imgDim = imgDimViaWide; return;}
    if(imgAreaViaTall < imgAreaViaWide){imgDim = imgDimViaTall; return;}

    //> Areas are equal (usually due to same wide/tall ratio as box): assign box
    imgDim = new PVector(bbox.dimX(),bbox.dimY());
  }

  public void render(){
    if(img==null){renderNoImg();}
    else{renderImg();}
    if(debugBoxViz){renderBox();}
  }

  private void renderBox() {
    app.noFill(); app.stroke(0,255,0); app.strokeWeight(2); app.rectMode(PApplet.CENTER);
    app.rect(bbox.midX(), bbox.midY(), bbox.wide(), bbox.tall());    
  }

  public void renderImg(){
    switch(mySpecial){
      case CENTER_ONLY:
        app.imageMode(PApplet.CENTER);
        app.image(img, bbox.midX(), bbox.midY());
        return;
      case STRETCH_TO_FIT:
        app.imageMode(PApplet.CORNER);
        app.image(img, bbox.minX(), bbox.minY(), bbox.wide(), bbox.tall());
        return;
      case SCALE_TO_FIT:
        app.imageMode(PApplet.CENTER);
        app.image(img, bbox.midX(), bbox.midY(), imgDim.x, imgDim.y);
        return;
      default: return;
    }
  }

  private void renderNoImg(){
    app.noFill(); app.stroke(255,0,255); app.strokeWeight(3); app.rectMode(PApplet.CENTER);
    app.rect(bbox.midX(), bbox.midY(), bbox.wide(), bbox.tall());
    app.line(bbox.minX(), bbox.minY(), bbox.maxX(), bbox.maxY());
    app.line(bbox.minX(), bbox.maxY(), bbox.maxX(), bbox.minY());    
  }

}