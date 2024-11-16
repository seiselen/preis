package PrEis.utils;
import processing.core.PApplet;

/** 
 * <b>Virtual</i> FPS Util</b> i.e. realizes animation and other operations via
 * uniform delta frame intervals against the current frame count.
 * @implNote Utilizes <b>delta frame</b> basis; i.e. no longer directly affects
 * processing's `frameRate`, and now does modulo testing against `frameCount`.
 */
public class VFPSUtil {
  public static final int DEF_DF = 1;
  public static final int MIN_DF = 1;
  public static final int MAX_DF = 6;
  
  private int tarFPS;
  private PApplet app;

  public VFPSUtil(PApplet iApp, int iDF){app=iApp; setTarDF(iDF);}
  public VFPSUtil(PApplet iApp)         {app=iApp; setTarDF(DEF_DF);}

  /*-[ SETTERS/MODIFIERS ]----------------------------------------------------*/
  public void setTarDF(int nDF){tarFPS=PApplet.constrain(nDF, MIN_DF, MAX_DF);}
  public void incTarDF(int nDF){setTarDF(tarFPS+nDF);}
  public void decTarDF(int nDF){setTarDF(tarFPS-nDF);}

  /*-[ GETTERS/QUERIERS ]-----------------------------------------------------*/
  public int getTarFPS(){return tarFPS;}
  public boolean isActiveFrame(){return app.frameCount%tarFPS==0;}
  
  /*-[ TOSTRING ]-------------------------------------------------------------*/
  public String tarFPSToString(){return PApplet.nf(tarFPS,2);}  
}