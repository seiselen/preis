package PrEis.utils;


import PrEis.utils.Cons.Act;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.data.StringList;





public abstract class SpritesheetPlan {

  /** Specifies type of [sub]sheet to extract. */
  enum SheetType {
    /** Sprite set arranged via row-major xor col-major grid.  */ GRID,
    /** Sprite set arranged via single left-to-right strip.    */ STRIP,
    /** Sprite set encompasses unique sizes|positions|offsets. */ UNIQUE,
    /** Catch-All to invalidate unknown/unspec'd types         */ ERROR;
    public static SheetType withString(String s){
      try {return SheetType.valueOf(s);}
      catch (IllegalArgumentException e) {Cons.err("Invalid input string: '"+s+"', returning catch-all member!"); return SheetType.ERROR;}
    }
  }

  /** Specifies subtype of [sub]sheet to extract. */
  enum SheetOrder {
    /** <b>ROW-MAJOR</b> <i>(i.e. left-to-right row-by-row grid)</i>.    */ ROW,
    /** <b>COL-MAJOR</b> <i>(i.e. up-to-down column-by-column grid)</i>. */ COL,
    /** <b>LEFT-RIGHT</b> <i>(i.e. left-to-right horizontal strip)</i>.  */ L2R,
    /** <b>TOP-BOTTOM</b> <i>(i.e. top-to-bottom vertical strip)</i>.    */ T2B,
    /** <b>NO ORDERING</b> <i>(i.e. positions individually spec'd)</i>.  */ NONE,
    /** Catch-All to invalidate unknown/unspec'd types                   */ ERROR;
    public static SheetOrder withString(String s){
      try {return SheetOrder.valueOf(s);}
      catch (IllegalArgumentException e) {Cons.err("Invalid input string: '"+s+"', returning catch-all member!"); return SheetOrder.NONE;}
    }
  }



  //> @TODO HOIST THESE SOMEPLACE MORE APPROPRIATE? <= YES! Do it as enum a-la `PrEisRes` soon.
  public static String KW_SRC  = "SRC_FP";
  public static String KW_DST  = "DST_DP";  
  public static String KW_OFF  = "OFFSET";
  public static String KW_ORDR = "ORDER"; 
  public static String KW_DIMS = "DIMS";
  public static String KW_DATA = "DATA";  
  public static String KW_TYPE = "TYPE";    

  protected SheetType  type;
  protected SheetOrder order;

  /** <b>Source Filepath</b> i.e. of spritesheet image. */
  protected String srcFP;
  /** <b>Destination Directory Path</b> i.e. where sprites are saved. */
  protected String dstDP;

  public SpritesheetPlan(SheetType inType, SheetOrder inOrder, String inSrcFP, String inDstDP){
    type  = inType;
    order = inOrder;
    srcFP  = inSrcFP;
    dstDP = inDstDP;
  }


  public String getSrcFP(){return srcFP;}
  public SheetType getType(){return type;}
  public SheetOrder getOrder(){return order;}

  public static SpritesheetPlan withJSON(JSONObject jObj){

    SheetType t = null;
    try {t = SheetType.withString(jObj.getString(KW_TYPE));} catch (Exception e){}
    if(t==null){Cons.err_act("Unable to find or assign sheet type!", Act.RETURN_NULL); return null;}

    switch (t) {
      case GRID: return SpritesheetPlan.grid_withJSON(jObj);           
      default: return null;
    }

  }

  private static SpritesheetPlan grid_withJSON(JSONObject jObj){
    
    String srcFP = null;
    try {srcFP = jObj.getString(KW_SRC);} catch (Exception e){}
    if(srcFP==null){Cons.err_act("Unable to find or assign sheet image filepath!", Act.RETURN_NULL); return null;}

    String dstDP = null;
    try {dstDP = jObj.getString(KW_DST);} catch (Exception e){}
    if(dstDP==null){Cons.err_act("Unable to find or assign destination dirpath!", Act.RETURN_NULL); return null;}

    SheetOrder ordr = null;
    try {
      ordr = SheetOrder.withString(jObj.getString(KW_ORDR));
    } catch (Exception e){}
    if(ordr==null){
      Cons.err_act("Unable to find or assign sheet ordering!", Act.RETURN_NULL);
      return null;
    }

    PVector off = null;
    try {
      int[] offRaw = jObj.getJSONArray(KW_OFF).toIntArray();
      off = new PVector(offRaw[0], offRaw[1]);
    } catch (Exception e){}
    if(off==null){
      Cons.err_act("Unable to find or assign base offset!", Act.RETURN_NULL);
      return null;
    }    

    PVector dims = null;
    try {
      int[] dimsOff = jObj.getJSONArray(KW_DIMS).toIntArray();
      dims = new PVector(dimsOff[0], dimsOff[1]);
    } catch (Exception e){}
    if(dims==null){
      Cons.err_act("Unable to find or assign common dimensions!", Act.RETURN_NULL);
      return null;
    }

    String[][] data = null;
    try {
      JSONArray dataArr = jObj.getJSONArray(KW_DATA);
      String[][] dataArr2 = new String[dataArr.size()][];
      for(int i=0; i<dataArr.size(); i++){dataArr2[i] = dataArr.getJSONArray(i).toStringArray();}
      data = dataArr2;
    } catch (Exception e){}
    if(data==null){
      Cons.err_act("Unable to find or assign data rows/cols!", Act.RETURN_NULL);
      return null;
    }
    return new SpritesheetGridPlan(srcFP, dstDP, ordr, off, dims, data);
  }


  public String[] toStringArray(){
    /*### STUB ###*/
    return null; 
  }

  public void toConsole(){
    /*### STUB ###*/
  }

}


class SpritesheetGridPlan extends SpritesheetPlan {
  private PVector    offset;
  private PVector    dims;
  private String[][] data;

  public SpritesheetGridPlan(String inSrcFP, String inDstDP, SheetOrder inOrdr, PVector inOff, PVector inDims, String[][] inData){
    super(SheetType.GRID, inOrdr, inSrcFP, inDstDP);
    offset = inOff;
    dims   = inDims;
    data   = inData;
  }

  public PVector getOffset(){return offset;}
  public PVector getDims(){return dims;}
  public String[][] getData(){return data;}


  public void toConsole(){
    String[] toStr = toStringArray();
    for(String s : toStr){System.out.println(s);}
  }

  public String[] toStringArray(){return toStringArray(true);}

  public String[] toStringArray(boolean inclData){
    //> Thx Processing for realizing this struct!
    StringList retList = new StringList();

    retList.append("Type --------> "+type.toString());
    retList.append("Order -------> "+order.toString());
    retList.append("Base Offset -> "+offset.toString());
    retList.append("Common Dims -> "+dims.toString());
    retList.append("Sheet FP ----> "+srcFP.toString());
    retList.append("Data A/F ----> ");
    if(inclData){retList.append(dataToString());}

    return retList.toArray();
  }

  private String[] dataToString(){
    String[] ret = new String[data.length];
    for(int i=0; i<ret.length; i++){
      ret[i] = StringUtils.wrapParens(StringUtils.concatAsCSSV(data[i]));
    }
    return ret;
  }

}