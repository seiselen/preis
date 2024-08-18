package PrEis.utils;

import processing.core.PApplet;

public class GridManager {
  private final boolean DEF_SHOW_GRID = true;
  private final int     DEF_CELL_SIZE = 16;

  private PApplet p;
  private boolean showGrid;
  private int cellSize;
  private int cellsWide;
  private int cellsTall;
  private int strk_wgt;
  private int strk_col;
    
  public GridManager(PApplet iApp){
    p        = iApp;
    showGrid = DEF_SHOW_GRID;
    strk_wgt = 1;
    strk_col = p.color(32,128);
    set_cellSize(DEF_CELL_SIZE);
  }
  
  public GridManager set_cellSize(int n_cellSize){
    cellSize  = PApplet.constrain(n_cellSize, 4, p.width);
    cellsWide = p.width/cellSize;
    cellsTall = p.height/cellSize;
    return this;
  }

  public GridManager set_strk_wgt(int n_strk_wgt){
    strk_wgt = n_strk_wgt;
    return this;
  }
  
  /** 
   * @implNote NO data validation, as Processing's `color` type is an int and I 
   * don't have the time right now to realize a clamping/constrain realization.
   */
  public GridManager set_strk_col(int n_strk_col){
    strk_col = n_strk_col;
    return this;
  }

  public GridManager setShowGrid(){
    showGrid = !showGrid;
    return this;
  }
  
  public GridManager setShowGrid(boolean v){
    showGrid = v;
    return this;    
  }
  
  public boolean getShowGrid(){
    return showGrid;
  }
 
  public void showGridToString(){
    System.out.println("Showing Grid? ["+(showGrid ? "yes" : "no")+"]");
  }

  public void render(){
    if(!showGrid){return;}
    p.stroke(strk_col);
    p.strokeWeight(strk_wgt);
    for(int i=0; i<cellsTall; i++){Pgfx.lineh(p, 0, p.width, cellSize*i);}
    for(int i=0; i<cellsWide; i++){Pgfx.linev(p, cellSize*i, 0, p.height);}
  }
  
} // Ends Class