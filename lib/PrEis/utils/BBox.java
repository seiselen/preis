package PrEis.utils;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Bounding Box
 * @implNote Not (yet?) tested standalone; but most-else-all functionality will
 * <i>effectively</i> be tested via <b>PrEis.GUI</b>, as <code>UIObject</code>
 * is pending replacing {@link Dims} and {@link PVector} instances herewith.
 */
public class BBox {
  PVector _pos;
  PVector _dim;
  PVector _mpt;
  PVector _ept;

  private BBox(){
    _pos = new PVector();
    _dim = new PVector();
    _mpt = new PVector();
    _ept = new PVector();
  }

  public BBox(float iPosX, float iPosY, float iWide, float iTall){
    this();
    setTransform(iPosX, iPosY, iWide, iTall);
  }

  public BBox(float iWide, float iTall){
    this();
    setDim(iWide, iTall);
  }

  public BBox(PVector iPos, PVector iDim){
    this();
    setTransform(iPos, iDim);
  }

  public BBox(PVector iDim){
    this();
    set_dim(iDim);
  }

  public BBox setPos(float iPosX, float iPosY){
    _pos.set(new PVector(iPosX,iPosY));
    setTransform();
    return this;
  }

  public BBox setDim(float iWide, float iTall){
    _dim.set(new PVector(iWide,iTall));
    setTransform();
    return this;
  }

  public BBox set_pos(PVector iPos){
    _pos.set(iPos);
    setTransform();
    return this;
  }


  public BBox set_dim(PVector iDim){
    _dim.set(iDim);
    setTransform();
    return this;
  }

  
  public void translatePos(PVector dPos){
    _pos.add(dPos);
    setTransform();
  }


  /** This version sets transform WRT current {@link #_pos} and {@link #_dim} vals. */
  private void setTransform(){
    _mpt.set(PVector.mult(_dim,0.5f).add(_pos));
    _ept.set(PVector.add(_pos, _dim));    
  }

  /** 
   * This version sets transform WRT input <b>coordinates</b>.
   */
  private void setTransform(float iPosX, float iPosY, float iWide, float iTall){
    _pos.set(new PVector(iPosX,iPosY));
    _dim.set(new PVector(iWide,iTall));
    setTransform();
  }
 
  /** 
   * This version sets transform WRT input <b>vectors</b>.
   */
  private void setTransform(PVector iPos, PVector iDim){
    _pos.set(iPos);
    _dim.set(iDim);
    setTransform();
  }





  /*----------------------------------------------------------------------------
  |> UTIL GETTERS
  +---------------------------------------------------------------------------*/

  /** 
   * Returns lerp from {@link #_pos} to {@link #ept} via input <b>normalized</b>
   * percent value. 
   * @param pct input percent of range <code>[0.0f,1.0f]</code>
   */
  public PVector lerpPct(float pct){
    return PVector.lerp(_pos, _ept, PApplet.constrain(pct, 0.0f, 1.0f));
  }

  /** 
   * Returns lerp from {@link #_pos} to {@link #ept} via input <b>integer</b>
   * percent value. 
   * @param pct input percent of range <code>[0,100]</code>
   */
  public PVector lerpPct(int pct){
    return PVector.lerp(_pos, _ept, PApplet.constrain(pct, 0, 100));
  }

  /** 
   * Returns lerp from {@link #_pos} to {@link #ept} via input ratio as divisor;
   * i.e. for input <code>r</code> a lerp of <code>1f/ratio</code>. 
   * @param pct input percent of range <code>[0.0f,1.0f]</code>
   */
  public PVector lerpRat(int rat){
    return PVector.lerp(_pos, _ept, 1f/rat);
  }

  /**
   * Is the query PVector <code>q</code> within this bounding box region?
   * @param q query {@link PVector}
   */
  public boolean inBounds(PVector q){
    return q.x >= _pos.x && q.x <= _ept.x && q.y >= _pos.y && q.y <= _ept.y;
  }

  /**
   * Is the query PVector <code>q</code> within this bounding box region?
   * @param qx query <code>x</code> coordinate
   * @param qy query <code>y</code> coordinate
   */
  public boolean inBounds(float qx, float qy){
    return qx >= _pos.x && qx <= _ept.x && qy >= _pos.y && qy <= _ept.y;
  }

  //> Misc. Syntax Sugar Getters (Not Nec. Optimal, KISS)
  public float   minX  (){return _pos.x;}
  public float   minY  (){return _pos.y;}
  public float   midX  (){return _mpt.x;}
  public float   midY  (){return _mpt.y;}
  public float   maxX  (){return _ept.x;}
  public float   maxY  (){return _ept.y;}
  public float   dimX  (){return _dim.x;}
  public float   dimY  (){return _dim.y;}
  public PVector pos   (){return _pos.copy();}
  public PVector mpt   (){return _mpt.copy();}
  public PVector ept   (){return _ept.copy();}
  public PVector min   (){return pos();}
  public PVector mid   (){return mpt();}
  public PVector max   (){return mpt();}
  public PVector posTL (){return pos();}
  public PVector posBR (){return ept();}
  public PVector posCC (){return mpt();}  
  public PVector posTR (){return new PVector(maxX(), minY());}
  public PVector posBL (){return new PVector(minX(), maxY());}
  public PVector posLC (){return new PVector(minX(), midY());}
  public PVector posRC (){return new PVector(maxX(), midY());}
  public PVector posTC (){return new PVector(midX(), minY());}
  public PVector posBC (){return new PVector(midX(), maxY());}
  public float[] extentsToArray(){return new float[]{_pos.x,_pos.y,_ept.x,_ept.y};}
}