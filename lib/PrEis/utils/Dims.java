package PrEis.utils;
public class Dims {
  private float _wide;
  private float _wideh;
  private float _wideq;
  private float _tall;
  private float _tallh;
  private float _tallq;
  
  public Dims(){setDims(0,0);}
  public Dims(float nWide, float nTall){setDims(nWide,nTall);}
  public Dims(int[] arr){setDims(arr[0],arr[1]);}
  
  public void setDims(float nWide, float nTall){setWide(nWide); setTall(nTall);}
  public void setWide(float nWide){_wide=nWide; _wideh=_wide/2f; _wideq=_wide/4f;}
  public void setTall(float nTall){_tall=nTall; _tallh=_tall/2f; _tallq=_tall/4f;}
  
  /** returns width var */
  public float wide(){return _wide;}
  public float tall(){return _tall;}
  public float wideh(){return _wideh;}
  public float tallh(){return _tallh;}
  public float wideq(){return _wideq;}
  public float tallq(){return _tallq;}  
  public float wideRat(float rat){return _wide/rat;}
  public float tallRat(float rat){return _tall/rat;}
  public float widePct(float pct){return _wide*pct;}
  public float tallPct(float pct){return _tall*pct;}

  public float[] toArray(){return new float[]{_wide,_tall};}
  public float[] toArrayh(){return new float[]{_wideh,_tallh};}
  public float[] toArrayq(){return new float[]{_wideq,_tallq};}
  public float[] toArrayRat(float rat){return new float[]{_wide/rat,_tall/rat};}
  public float[] toArrayPct(float pct){return new float[]{_wide*pct,_tall*pct};}
}