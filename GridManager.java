//################################################################################
//=[MANAGER INSTANCE]=============================================================
GridManager gridManager;
//=[MANAGER DEFINITION]===========================================================
class GridManager {
  private boolean showGrid = DEF_SHOW_GRID;
  private int cellSize  = 16;
  private int cellsWide = -1;
  private int cellsTall = -1;
  private int   strk_wgt;
  private color strk_col;
  
  private static final boolean DEF_SHOW_GRID = false;
  
  public GridManager(){
    strk_col = color(32,128);
    strk_wgt = 1;
    setCellSize(cellSize);
    showGrid = GridManager.DEF_SHOW_GRID;
  }
  
  public void setCellSize(int nSize){
    cellSize  = int(constrain(nSize, 4, CANVAS_WIDH));
    cellsWide = int(CANVAS_WIDE/cellSize);
    cellsTall = int(CANVAS_TALL/cellSize);
  }
  
  public void setShowGrid(){showGrid = !showGrid;}
  
  public void setShowGrid(boolean v){showGrid = v;}
  
  public boolean getShowGrid(){return showGrid;}
 
  public void showGridToString(){println("Showing Grid? ["+(showGrid ? "yes" : "no")+"]");}  

  public void drawGrid(){
    if(!showGrid){return;}
    stroke(strk_col);
    strokeWeight(strk_wgt);
    for(int i=0; i<cellsTall; i++){line(0,cellSize*i,CANVAS_WIDE,cellSize*i);}
    for(int i=0; i<cellsWide; i++){line(cellSize*i,0,cellSize*i,CANVAS_TALL);}  
  }
  
} // Ends Class GridManager