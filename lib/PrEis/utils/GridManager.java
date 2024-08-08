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
    setCellSize(DEF_CELL_SIZE);
  }
  
  public void setCellSize(int nSize){
    cellSize  = PApplet.constrain(nSize, 4, p.width);
    cellsWide = p.width/cellSize;
    cellsTall = p.height/cellSize;
  }
  
  public void setShowGrid(){
    showGrid = !showGrid;
  }
  
  public void setShowGrid(boolean v){
    showGrid = v;
  }
  
  public boolean getShowGrid(){
    return showGrid;
  }
 
  public void showGridToString(){
    System.out.println("Showing Grid? ["+(showGrid ? "yes" : "no")+"]");
  }

  public void drawGrid(){
    if(!showGrid){return;}
    p.stroke(strk_col);
    p.strokeWeight(strk_wgt);
    for(int i=0; i<cellsTall; i++){PGfxUtils.lineh(p, 0, p.width, cellSize*i);}
    for(int i=0; i<cellsWide; i++){PGfxUtils.linev(p, cellSize*i, 0, p.height);}
  }
  
} // Ends Class