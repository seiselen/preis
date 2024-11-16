package PrEis.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import PrEis.utils.Cons.Act;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class ZScriptUtils {

  /** Include BRIGHTMAPS files in search results? */
  public static boolean INCL_BMAPS = false;

  /** EXPECTED dir of which contains brightmaps sprites.  */
  private static final String BMAP_DIR = "BRIGHTMAPS";

  /** Distinguish states of frames of `{-1,0}` tic length via special preceding `"#LOOP"` handle? */
  public static boolean HANDLE_NON_POS_TIC_LENS = false;


  /** EXPECTED filename of sprite name map JSON. */
  public static final String FN_SPR_NAM_MAP = "sprite_name_map.json";
  /** EXPECTED filename of sprite offset map JSON. */
  public static final String FN_SPR_OFF_MAP = "sprite_offset_map.json";
  /** EXPECTED filename of sprite sheet (plan) map JSON. */
  public static final String FN_SPR_SHT_MAP = "sprite_sheet_map.json"; 


  /** Filepath is a match IFF it includes the <code>String[5]</code> sprite name. */
  public static boolean evalFName(Path q, String n5){
    return q.getFileName().toString().startsWith(n5);
  }

  /** If Filepath in brightmap dir: it is a match IFF brightmaps are not filtered. */
  public static boolean evalBMap(Path q){
    return INCL_BMAPS || !q.toString().contains(BMAP_DIR);
  }

  /** 
   * Default <code>maxDepth</code> for <code>Files.find(...)</code> operations.
   * @implNote currently <code>[8]</code> to account for reasonable nested dirs
   */
  private static final int FIND_MAX_DEPTH = 8;

  /**
   * Returns filepath of input sprite name if such exists within input dirpath.
   * @param fPath query filepath
   * @param name5 sprite name <code>char[5]</code>; e.g. <code>"PLSAB"</code>
   */
  public static String[] findSprite(Path fPath, String name5){
    ArrayList<String> buff = new ArrayList<String>();
    try {
      Files.find(fPath, FIND_MAX_DEPTH,(p,a)->(evalFName(p,name5) && evalBMap(p)))
      .forEach(f -> buff.add(f.toString()));
    }
    catch(IOException ie) {ie.printStackTrace();}
    return FormatUtils.arrFromList(String.class, buff);
  }

  public static void extractAllSpriteOffsetsIn(PApplet app, String srcPath, String outPath){
    Path sp = Paths.get(srcPath);
    FileSysUtils.DEF_FIND_LEV = 8;
    String [] spFNs = FileSysUtils.fileNamesOfDir(srcPath,ExtType.PNG);
    FileSysUtils.DEF_FIND_LEV = 1;
    for(int i=0; i<spFNs.length; i++){spFNs[i] = FileSysUtils.fnameFromFpath(spFNs[i],false);}
    PVector pv;
    FileWriteUtil writer = new FileWriteUtil(app);
    writer.launchWrite(outPath);
    for(String fn : spFNs){
      pv = extractSpriteOffset(app, sp, fn);
      if(pv!=null){writer.writeToFile(fn+": "+StringUtils.wrapParens(pv.x+", "+pv.y));}
    }
    writer.finishWrite();
  }


  public static PVector extractSpriteOffset(PApplet app, Path spriteDP, String sName){
    String sprPath = findSprite(spriteDP, sName)[0];
    byte[] byteArr = app.loadBytes(sprPath);
    int offStartIdx = -1;
    for(int i=0; i<byteArr.length; i++){if(findOffsetIdxStart(byteArr,i)){offStartIdx=i+4; break;}}
    if(offStartIdx==-1){
      Cons.err("Sprite Offset nullish xor zero vector, returning zero vector.");
      return new PVector();
    }
    return new PVector(
      PApplet.unhex(FormatUtils.byteArrSubStrToHex(byteArr,offStartIdx,4)),
      PApplet.unhex(FormatUtils.byteArrSubStrToHex(byteArr,offStartIdx+4,4))
    );
  }

  public static boolean findOffsetIdxStart(byte[] bArr, int idx){
    if (!PApplet.hex(bArr[  idx]).equals("67")){return false;}
    if (!PApplet.hex(bArr[idx+1]).equals("72")){return false;}
    if (!PApplet.hex(bArr[idx+2]).equals("41")){return false;}
    if (!PApplet.hex(bArr[idx+3]).equals("62")){return false;}  
    return true;
  }

  /** @todo TEST THIS */
  public static HashMap<String,PVector> offsetJSONToOffsetMap(JSONObject offsets){
    if(offsets==null){return null;}
    String[] keys = DataStructUtils.keyArrayOfJSONObj(offsets);
    HashMap<String,PVector> ret = new HashMap<String,PVector>();
    JSONArray arr;
    for(String k: keys){arr=offsets.getJSONArray(k); ret.put(k,new PVector(arr.getInt(0),arr.getInt(1)));}
    return ret;
  }


  /**
   * @param wrapSeq wrap set of sprites with curly brackets? e.g. "{PLSAA, PLSAB}" -vs- "PLSAA, PLSAB"
   * @param wrapFrm wrap sprites between parenthesis chars? e.g. "(PLSAA)" -vs- "PLSAA"  
   * @param sepSCSV separate sprites with comma as well as space? e.g. "{PLSAA, PLSAB}" -vs- "{PLSAA PLSAB}"
   * @todo test this (and its overload) at some point
   */
  public static void printSpriteSeqStrArr(String[] arr, boolean wrapSeq, boolean wrapFrm, boolean sepSCSV){
    String str = "";
    for(int i=0; i<arr.length; i++){
      str += wrapFrm ? StringUtils.wrapParens(arr[i]) : arr[i];
      if(i<arr.length-1){str += sepSCSV ? ", " : " ";}
    }
    if(wrapSeq){str = StringUtils.wrapWith('{',str);}
    System.out.println(str);
  }

  public static void printSpriteSeqStrArr(String[] arr){
    printSpriteSeqStrArr(arr,true,true,true);
  }

  /** @todo TEST THIS */
  public static String[] frameLineToSpriteArray(String line){  
    String[] components = line.trim().split("\\s+");
    if(components.length != 3){
      System.err.println("Error: Expecting [3] (three) components! Erroneous line String = '"+line+"'");
      return null;
    }
    String prefixStr = components[0];
    char[] suffixArr = components[1].toCharArray();
    int    ticLength = Integer.valueOf(components[2]);
    ArrayList<String> BUFF_STR_ARRLIST = new ArrayList<String>();
    for (char f : suffixArr){
      if(ticLength<=0){
        //> @TODO can clean these two lines into one with ternary (low priority)
        if(HANDLE_NON_POS_TIC_LENS){BUFF_STR_ARRLIST.add("#LOOP");}
        BUFF_STR_ARRLIST.add(""+prefixStr+f);
      } 
      else {
        for (int i=0; i<ticLength; i++){BUFF_STR_ARRLIST.add(""+prefixStr+f);}
      }
    }
    return FormatUtils.strArrListToStrArr(BUFF_STR_ARRLIST);
  }

  /** @todo TEST THIS */
  public static void stateSeqRawSideBySideToConsole(String[] stateNames, JSONObject allStates){
    Cons.log(StringUtils.charTimesN('#',80));
    String[] stateSeqArr;
    for (int i=0; i<stateNames.length; i++){
      Cons.log(StringUtils.charTimesN('=',80), "State: '"+stateNames[i]+"'", StringUtils.charTimesN('-',80));
      stateSeqArr= allStates.getJSONArray(stateNames[i]).toStringArray();
      for (int j=0; j<stateSeqArr.length; j++){
        Cons.log(StringUtils.padR(stateSeqArr[j],12)+" : ");
        printSpriteSeqStrArr(frameLineToSpriteArray(stateSeqArr[j]));
      }
      if(i<stateNames.length-1){Cons.log();}
    }
    Cons.log(StringUtils.charTimesN('#',80)+'\n');
  }

  /** @todo TEST THIS */
  public static void stateSeqRawFlatSequenceToConsole(String[] stateNames, JSONObject allStates){
    Cons.log(StringUtils.charTimesN('#',80));
    int paddingLen = StringUtils.maxCharLengthOf(DataStructUtils.keyArrayOfJSONObj(allStates))+2;
    ArrayList<String> allFrames = new ArrayList<String>();
    String[] stateSeqArr;
    for (String n : stateNames){
      Cons.log(StringUtils.padR("'"+n+"'", paddingLen)+" : ");
      stateSeqArr = allStates.getJSONArray(n).toStringArray();
      allFrames.clear();
      for (int i=0; i<stateSeqArr.length; i++){
        java.util.Collections.addAll(allFrames, frameLineToSpriteArray(stateSeqArr[i]));
      }
      printSpriteSeqStrArr(FormatUtils.strArrListToStrArr(allFrames),false,true,false);
    }
    Cons.log(StringUtils.charTimesN('#',80));
  }



  public static String[] generateVSpriteTEXTURES(PApplet app, String spriteInfoFP){

    JSONObject namesMapJSON  = FileSysUtils.loadJSONObjNullFail(app, FileSysUtils.pathConcat(spriteInfoFP,FN_SPR_NAM_MAP), true);
    JSONObject offsetMapJSON = FileSysUtils.loadJSONObjNullFail(app, FileSysUtils.pathConcat(spriteInfoFP,FN_SPR_OFF_MAP), true);
    JSONObject sheetMapJSON  = FileSysUtils.loadJSONObjNullFail(app, FileSysUtils.pathConcat(spriteInfoFP,FN_SPR_SHT_MAP), true);
    if(namesMapJSON==null||offsetMapJSON==null||sheetMapJSON==null){return null;}

    //> IOU HANDLING FOR {STRIP/UNIQUE SPRITESHEET [PLAN] TYPES}
    int[] comDim = null;
    try {comDim = sheetMapJSON.getJSONArray(SpritesheetPlan.KW_DIMS).toIntArray();} catch (Exception e){}
    if(comDim==null){Cons.err_act("Unable to find or assign sheet map sprite dims", Act.RETURN_NULL); return null;}
    //System.out.println(StringUtils.wrapParens(StringUtils.concatAsCSSV(commonDims)));

    String[][] namesMap = null;
    try {
      JSONArray dataArr = namesMapJSON.getJSONArray("SPRITE_DATA");
      String[][] dataArr2 = new String[dataArr.size()][];
      for(int i=0; i<dataArr.size(); i++){dataArr2[i] = dataArr.getJSONArray(i).toStringArray();}
      namesMap = dataArr2;
    } catch (Exception e){}
    if(namesMap==null){
      Cons.err_act("Unable to find or assign sprite name map String[][] data", Act.RETURN_NULL);
      return null;
    }


    HashMap<String,int[]> offsMap = new HashMap<String,int[]>();
    String [] offKeys = DataStructUtils.keyArrayOfJSONObj(offsetMapJSON);
    for(String k : offKeys){
      try {offsMap.put(k, offsetMapJSON.getJSONArray(k).toIntArray());} 
      catch (Exception e) {Cons.err("Error assigning offset vals for sprite '"+k+"'");}
    }

    ArrayList<String> retList = new ArrayList<String>();
    int BFN = 0;
    int VFN = 1;
    int SFN = 2;
    int[] spOff;
    int[] spOffDef = new int[]{0,0};
    for(String[] s : namesMap){
      spOff = offsMap.get(s[SFN]+"0");
      if(spOff==null){Cons.err("Cannot find offset for sprite '"+s[SFN]+"' - using [0,0]"); spOff=spOffDef;}
      retList.add("Sprite \""+s[VFN]+"0\", "+comDim[0]+", "+comDim[1]+" {");
      retList.add("  Offset "+spOff[0]+", "+spOff[1]+"");
      retList.add("  Patch \""+s[BFN]+"\", 0, 0");
      retList.add("}");
      retList.add(" ");
    }

    return FormatUtils.arrFromList(String.class, retList);
  }


}