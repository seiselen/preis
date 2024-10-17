package PrEis.test;
import PrEis.utils.BBox;
import PrEis.utils.Cons;
import PrEis.utils.GridManager;
import PrEis.utils.JAResourceUtil;
import PrEis.utils.PrEisRes;
import processing.core.PApplet;
import processing.core.PImage;

public class TestGridManager {
  GridManager grid;
  Testbed app;
  int[][] testVals;

  int BG_COL;
  private boolean DISP_BG_IMG = false;
  private int BG_IMG_ALPHA = 160;
  private PImage    bgImgDark; 

  public TestGridManager(Testbed inApp){
    app = inApp;
  
    grid = new GridManager(
      (PApplet)app,
      new BBox(160,112,320,256),
      JAResourceUtil.getFontFromJAR(PrEisRes.MON_FONT)
    );
    loadImages();
    BG_COL = app.color(0);
    initTestVals();
    effectTestVal(testVals[6]);
    System.out.println("Construction Complete. Please refer to `switch` in `onKeyPressed` for testing options");
  }


  private void loadImages(){
    try{bgImgDark  = app.loadImage(app.getPathOf(TestAssetKey.GUI_BG_IMG_DARK));}
    catch (Exception e){Cons.err("Issue fetching one or more assets"); e.printStackTrace(); app.exit(); return;}
  }





  private void initTestVals(){
    //> SYNTAX: {cellSize, strk_wgt, strk_col}
    testVals = new int[][]{
      { 4, 1, app.color(213,  62,  79)},
      { 6, 2, app.color(244, 109,  67)},
      { 8, 3, app.color(253, 174,  97)},
      {12, 4, app.color( 50, 136, 189)},
      {16, 6, app.color(102, 194, 165)},
      {32, 8, app.color(171, 221, 164)},
      {16, 2, app.color(255,255,0,128), app.color(255,255,0)}
    };
  }

  private void effectTestVal(int[] tv){
    grid.setShowGrid(true).setCellSize(tv[0]).setStrkWgt(tv[1]).setStrkCol(tv[2]);
    if(tv.length==4){grid.setTextCol(tv[3]);}
  }

  /**
   * @param kIdx key index i.e. which keyboard number is bound to this test
   * @param tv test info, as `int[3]` of {cellSize, strk_wgt, strk_col}
   */
  private String testValToString(int kIdx, int[] tv){
    return "Test ID ["+kIdx+"]: {cellSize:["+tv[0]+"], strk_wgt:["+tv[1]+"], strk_col:["+tv[2]+"]}";
  }

  /** 
   * Syntax sugar for calling {@link #effectTestVal} and then calling sysout on {@link #testValToString}.
   */
  private void effectAndConslogTest(int kIdx, int[] tv){
    effectTestVal(tv);
    System.out.println(testValToString(kIdx, tv));
  }

  public void onKeyPressed(){
    switch (app.key) {
      case 'b' : DISP_BG_IMG = !DISP_BG_IMG; return;
      case 'g' : grid.setShowGrid(); return;
      case 't' : grid.setDeltaTic(grid.getDeltaTic()==1 ? 2 : 1); return;
      case 's' : grid.setScalar(grid.getScalar()==1 ? 2 : 1); return;

      case '1' : effectAndConslogTest(1, testVals[0]); return;
      case '2' : effectAndConslogTest(2, testVals[1]); return;
      case '3' : effectAndConslogTest(3, testVals[2]); return;
      case '4' : effectAndConslogTest(4, testVals[3]); return;
      case '5' : effectAndConslogTest(5, testVals[4]); return;
      case '6' : effectAndConslogTest(6, testVals[5]); return;    
      default  : return;
    }
  }

  public void testRender(){
    renderBG();
    grid.render();
  }


  private void renderBG(){
    app.background(BG_COL);
    if(DISP_BG_IMG){
      app.imageMode(PApplet.CORNER);
      if(BG_IMG_ALPHA<255){app.tint(BG_IMG_ALPHA);}
      app.image(bgImgDark, 0, 0);
      if(BG_IMG_ALPHA<255){app.tint(255);}
    }
  }

}