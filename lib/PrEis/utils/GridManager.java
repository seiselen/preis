package PrEis.utils;

import processing.core.PApplet;
import processing.core.PFont;

public class GridManager {
  private final boolean DEF_SHOW_GRID = true;
  private final int     DEF_SCALAR    = 1;
  private final int     DEF_SWGT      = 1;  
  private final int     DEF_CELL_SIZE = 16;
  private final int     DEF_TEXT_SIZE = 12;
  private final int     DEF_DELTA_TIC = 2;

  private PApplet app;
  private BBox box;
  private PFont font;
  private boolean showGrid;
  private int cellSize;
  private int cellSizh;
  private int cellsWide;
  private int cellsTall;
  private int strkWgt;
  private int strkCol;
  private int textCol;
  private int textSiz;  
  private float scalar;
  private int deltaTic;

  public GridManager(PApplet iApp, BBox iBox, PFont ifont){
    app      = iApp;
    box      = iBox;
    font     = ifont;
    setShowGrid(DEF_SHOW_GRID);
    setScalar(DEF_SCALAR);
    setStrkCol(app.color(32,128));
    setTextCol(app.color(32));
    setStrkWgt(DEF_SWGT);
    setDeltaTic(DEF_DELTA_TIC);
    setTextSiz(DEF_TEXT_SIZE);
    setCellSize(DEF_CELL_SIZE);
  }
  

  //=[ STATE GETTERS ]=========================================================#
  public float   getScalar(){return scalar;}
  public boolean getShowGrid(){return showGrid;}
  public int     getDeltaTic(){return deltaTic;}

  //=[ STATE SETTERS ]=========================================================#

  public GridManager setCellSize(int n_cellSize){
    cellSize  = PApplet.constrain(n_cellSize, 4, app.width);
    cellSizh  = cellSize/2;
    cellsWide = PApplet.floor(box.wide()/cellSize);
    cellsTall = PApplet.floor(box.tall()/cellSize);
    if(cellsWide*cellSize<=box.dimX()){cellsWide+=1;}
    if(cellsTall*cellSize<=box.dimY()){cellsTall+=1;}
    return this;
  }

  public GridManager setDeltaTic(int nDeltaTic){deltaTic = nDeltaTic; return this;}

  public GridManager setStrkWgt(int nStrkWgt){strkWgt = nStrkWgt; return this;}

  public GridManager setStrkCol(int nStrkCol){strkCol = nStrkCol; return this;}

  public GridManager setTextCol(int nTextCol){textCol = nTextCol; return this;}

  public GridManager setTextSiz(int nTextSiz){textSiz = nTextSiz; return this;}  

  public GridManager setScalar(float nScalar){if(nScalar>0){scalar = nScalar;} return this;}

  public GridManager setShowGrid(){showGrid = !showGrid; return this;}

  public GridManager setShowGrid(boolean v){showGrid = v; return this;}

  public GridManager setTransform(float x, float y){box.setPos(x,y); return this;}
  
  //=[ RENDER CALLS ]==========================================================# 
  public void render(){
    if(!showGrid){return;}
    //app.push();
    //app.scale(scalar);
    renderLabels();
    renderGrid();
    //app.pop();
  }

  private void renderGrid(){
    app.stroke(strkCol);
    app.strokeWeight(strkWgt);
    for(int i=0; i<cellsTall; i++){Pgfx.lineh(app, 
      box.minX()*scalar, 
      box.maxX()*scalar, 
      box.minY()*scalar+(cellSize*i*scalar)
    );}
    for(int i=0; i<cellsWide; i++){
      Pgfx.linev(app,
      box.minX()*scalar+(cellSize*i*scalar),
      box.minY()*scalar,
      box.maxY()*scalar);}
  }

  private void renderLabels(){
    app.textFont(font);
    app.fill(textCol);
    app.textSize(textSiz);
    renderLabelsXAxis();
    renderLabelsYAxis();
  }

  private void renderLabelsYAxis(){
    app.textAlign(PApplet.RIGHT);
    for(int i=0; i<cellsTall; i++){
      if(i%deltaTic==0){app.text(
        (cellSize*i)+" ",
        box.minX()*scalar,
        (box.minY()+(cellSize*i)+cellSizh)*scalar
      );}
    }
  }
  
  private void renderLabelsXAxis(){
    app.push();
    app.rotate(-90*PApplet.DEG_TO_RAD);
    app.translate(-box.minY()*scalar, box.minX()*scalar);
    app.textAlign(PApplet.LEFT);
    for(int i=0; i<cellsWide; i++){
      if(i%deltaTic==0){app.text(
        " "+(cellSize*i),
        0,
        (cellSize*i*scalar)+cellSizh
      );}
    }
    app.pop();
  }

}