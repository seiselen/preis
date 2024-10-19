package PrEis.utils;

import java.util.ArrayList;

import PrEis.utils.Cons.Act;
import PrEis.utils.Cons.Err;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

public class SpritesheetUtils {

  public static void splitAndSaveSprites(PApplet app, String planFP){
    JSONObject planJObj = FileSysUtils.loadJSONObjNullFail(app, planFP, true);
    if(planJObj==null){return;} //> `loadJSONObjNullFail` WILL conserr if returning null!
    SpritesheetPlan plan = SpritesheetPlan.withJSON(planJObj);
    if(plan==null){Cons.err_act(Err.NULL_INPUT, Act.RETURN_NO_ACTION); return;}
    ArrayList<Pair<String,PImage>> namedSprites = split(app, plan);
    if(namedSprites==null || namedSprites.size()<1){Cons.err_act(Err.NULL_INPUT, Act.RETURN_NO_ACTION); return;}
    if(plan.dstDP==null || plan.dstDP.isEmpty()){Cons.err_act("Nullish destination dirpath", Act.RETURN_NO_ACTION); return;}

    PImageUtils.savePImages(app, namedSprites, plan.dstDP);
  }
  
  public static ArrayList<Pair<String,PImage>> split(PApplet app, SpritesheetPlan plan){
    switch(plan.type){
      case GRID: switch(plan.order){
        case COL: return _splitGridColMaj(app, (SpritesheetGridPlan)plan);
        case ROW: return _splitGridRowMaj(app, (SpritesheetGridPlan)plan);
        default: conserrInvalidPlan(plan); return null;
      }
      case STRIP: switch(plan.order){
        case L2R: 
        case T2B:
        default:
      }
      default:
        Cons.err_act(Err.NOT_YET_REALIZED, Act.RETURN_NULL);
        return null;
    }    
  };
  
  private static ArrayList<Pair<String,PImage>> _splitGridColMaj(PApplet app, SpritesheetGridPlan plan){
    ArrayList<Pair<String,PImage>> list = new ArrayList<Pair<String,PImage>>();

    PImage sheetImg = app.loadImage(plan.getSrcFP());
    int[] off = DataStructUtils.int2WithVec2(plan.getOffset());
    int[] dim = DataStructUtils.int2WithVec2(plan.getDims());
    String[][] names = plan.getData();

    for(int c=0; c<names.length; c++){
      for(int r=0; r<names[c].length; r++){
        list.add(new Pair<String,PImage>(names[c][r], sheetImg.get(off[0]+(c*dim[0]),off[1]+(r*dim[1]),dim[0],dim[1])));
      }
    }
    return list;
  }

  private static ArrayList<Pair<String,PImage>> _splitGridRowMaj(PApplet app, SpritesheetGridPlan plan){
    ArrayList<Pair<String,PImage>> list = new ArrayList<Pair<String,PImage>>();

    PImage sheetImg = app.loadImage(plan.getSrcFP());
    int[] off = DataStructUtils.int2WithVec2(plan.getOffset());
    int[] dim = DataStructUtils.int2WithVec2(plan.getDims());
    String[][] names = plan.getData();

    for(int r=0; r<names.length; r++){
      for(int c=0; c<names[r].length; c++){
        list.add(new Pair<String,PImage>(names[r][c], sheetImg.get(off[0]+(c*dim[0]),off[1]+(r*dim[1]),dim[0],dim[1])));
      }
    }
    return list;
  }

  private static void conserrInvalidPlan(SpritesheetPlan p){
    Cons.err_act("Invalid spritesheet plan", Act.RETURN_NULL);
    Cons.err("Printing plan state sans its data...");
    for(String s : p.toStringArray()){System.err.println("  > "+s);}
  }
  
}
