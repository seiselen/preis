package PrEis.test;
import PrEis.utils.GridManager;
import processing.core.PApplet;

public class TestGridManager {
  GridManager g;
  PApplet p;
  int[][] testVals;

  public TestGridManager(PApplet app){
    p = app;
    g = new GridManager(p);
    initTestVals();
    System.out.println("Construction Complete. Please refer to `switch` in `onKeyPressed` for testing options");
  }

  private void initTestVals(){
    //> SYNTAX: {cellSize, strk_wgt, strk_col}
    testVals = new int[][]{
      { 4, 1, p.color(213,  62,  79)},
      { 6, 2, p.color(244, 109,  67)},
      { 8, 3, p.color(253, 174,  97)},
      {12, 4, p.color( 50, 136, 189)},
      {16, 6, p.color(102, 194, 165)},
      {32, 8, p.color(171, 221, 164)}
    };
  }

  private void effectTestVal(int[] tv){
    g.setShowGrid(true).set_cellSize(tv[0]).set_strk_wgt(tv[1]).set_strk_col(tv[2]);
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
    switch (p.key) {
      case 'g' : g.setShowGrid(); return;
      case 't' : g.setShowGrid(true); return;
      case 'f' : g.setShowGrid(false); return;
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
    p.background(255);
    g.render();
  }
}
