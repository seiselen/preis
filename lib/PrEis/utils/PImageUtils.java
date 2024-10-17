package PrEis.utils;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import PrEis.utils.Cons.Err;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.IntDict;
import processing.data.StringList;


public class PImageUtils {

  private static boolean CONSLOG_BAKE_STATS = true;
  private static boolean CONSLOG_MATCH_STATS = true;

  private static boolean LOG_PX_MATCHES = true;

  /** Possible results of image-2-image comparison. */
  enum ReturnCode {TEX_PIXS_MATCH, TEX_PIXS_MISMATCH, TEX_DIMS_MISMATCH}

  public static PApplet app;
  PImage buffImage;


  public static PImage withFilepath(PApplet iApp, String iPath) {
    try {
       return iApp.loadImage(iPath);
    } catch (Exception e) {
       System.err.println(e);
       return null;
    }
 }




  /** Renders input {@link PImage} at canvas center */
  public void displayAtCanvasCenter(PApplet app, PImage img){
    if(img==null){Cons.err(Err.NULL_VALUE); return;}
    app.imageMode(PApplet.CENTER);
    app.image(img, app.width/2, app.height/2);
  }


  /*============================================================================
  |> IMAGE BAKE UTILS
  +===========================================================================*/

  public void bakeDirOfTextures(String inDirpath, String outDirpath){
    String[] fNames = FileSysUtils.fileNamesOfDir(inDirpath, ExtType.PNG);
    int total = fNames.length;
    for(int i=0; i<fNames.length; i++){
      bakeTexture(fNames[i],inDirpath,outDirpath);
      if(CONSLOG_BAKE_STATS){Cons.log("Baked ("+(i+1)+"/"+total+") images");}
    }
  }
  
  public void bakeTexture(String fName, String inDirpath, String outDirpath){
    String fullNameI = FileSysUtils.pathConcat(inDirpath,fName);
    String fullNameO = FileSysUtils.pathConcat(outDirpath,fName);
    app.loadImage(fullNameI).save(fullNameO);
    if(CONSLOG_BAKE_STATS){Cons.log("successfully loaded ("+fullNameI+") and saved as ("+fullNameO+")");}
  }

  /*============================================================================
  |> SPRITESHEET SPLIT-&-CELL UTILS
  +===========================================================================*/

  
  /** #TODO */
  public PImage[] splitSpritesheet(PImage img, SpritesheetPlan plan){
    /*### STUB ###*/
    return null;
  };
  
  /** #TODO */
  public PImage[] saveSplitSprites(PImage[] splitSprites, SpritesheetPlan plan, Path outPath){
    /*### STUB ###*/
    return null;
  };



  /*============================================================================
  |> IMAGE PIXEL COMPARE UTILS
  +===========================================================================*/

  public Pair<ReturnCode,String[]> comparePixelColors(Path pFP, Path qFP){
    ArrayList<String> retList = new ArrayList<>();
    PImage pImg = app.loadImage(pFP.toString());
    PImage qImg = app.loadImage(qFP.toString());

    if(pImg.width!=qImg.width || pImg.height!=qImg.height){
      return Pair.create(ReturnCode.TEX_DIMS_MISMATCH, null);
    }
  
    boolean doDiff = false;
    int     pixLen = pImg.width * pImg.height;    
    int     curRow = 0;
    int     curCol = 0;
    int     curEvt = 1;
    int     pxCol1 = -1;
    int     pxCol2 = -1;

    for(int i=0; i<pixLen; i++){
      pxCol1 = pImg.pixels[i];
      pxCol2 = qImg.pixels[i];
      if(pxCol1 != pxCol2){
        if(!doDiff){
          retList.add("Differences between\n * <"+pFP.getFileName().toString()+"> and\n * <"+qFP.getFileName().toString()+">\nare as follows:");
          retList.add(StringUtils.concatStrings(EiStrings.PX_CMPR_TAB_BORD,"\n",EiStrings.PX_CMPR_TAB_HEAD,"\n",EiStrings.PX_CMPR_TAB_BORD));
        }
        doDiff=true;
        retList.add(tableEvtRow(curEvt, curRow, curCol, pxCol1, pxCol2));
        curEvt++;      
      }
      curCol++; 
      if(curCol%32==0){curRow++; curCol=0;}
    }

    if(doDiff){
      retList.add(EiStrings.PX_CMPR_TAB_BORD);
      return Pair.create(ReturnCode.TEX_PIXS_MISMATCH, FormatUtils.arrFromList(String.class, retList));
    }
    return Pair.create(ReturnCode.TEX_PIXS_MATCH, null);
  }


  /** @implNote <b>WARNING! STILL UNDER REDEVELOPMENT!</b> This remains a monster of a bitch... */
  public void imageColorValueCompareDirTextures(String path1, String path2){
    Pair<ReturnCode, String[]> curRet;
    int numSamePixs = 0;
    int numDiffDims = 0;
    int numDiffPixs = 0;
    int numOnly1Dir = 0;
    
    StringList mismatchDimsList = new StringList();
    StringList matchingPixsList = new StringList();
    
    Path [] p1Files = FileSysUtils.pathsOfType(path1, ExtType.PNG);
    Path [] p2Files = FileSysUtils.pathsOfType(path2, ExtType.PNG);
    
    HashMap<String,Path> p1Map = new HashMap<String,Path>();
    HashMap<String,Path> p2Map = new HashMap<String,Path>();
    IntDict imgNameFreqsBothDirs = new IntDict();

    for(Path fn : p1Files){
      imgNameFreqsBothDirs.increment(fn.getFileName().toString());
      p1Map.put(fn.getFileName().toString(), fn);
    }
    for(Path fn : p2Files){
      imgNameFreqsBothDirs.increment(fn.getFileName().toString());
      p2Map.put(fn.getFileName().toString(), fn);
    }

    String [] namesInBothDirs = DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 2);
    if(CONSLOG_MATCH_STATS){Cons.log("...completed 'in-both-dirs' dict enumerations");}
    for(String n : namesInBothDirs){
      curRet = comparePixelColors(p1Map.get(n), p2Map.get(n));
      switch(curRet.a){
        case TEX_DIMS_MISMATCH: numDiffDims++; mismatchDimsList.append(n); break;
        case TEX_PIXS_MISMATCH: numDiffPixs++; break;
        case TEX_PIXS_MATCH:    numSamePixs++; matchingPixsList.append(n); break;      
      } 
    }

    if(mismatchDimsList.size()>0){FileWriteUtil.writeOneCall(app, "files_of_different_dims", mismatchDimsList.toArray());}
    if(CONSLOG_MATCH_STATS){Cons.log("...completed 'in-both-dirs' return case total sums and logging files of mismatched dims");}
    
    //> Need to handle pixel col matching list in this way
    if(LOG_PX_MATCHES){
      if(matchingPixsList.size()>0){ 
        FileWriteUtil.writeOneCall(app, "files_that_match", matchingPixsList.toArray());     
        if(CONSLOG_MATCH_STATS){Cons.log("...completed logging files of matching pixel colors");} 
      }
    }
 
    String[] oneDirFiles = DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 1);
    
    if(oneDirFiles.length>0){
      numOnly1Dir = oneDirFiles.length;
      FileWriteUtil.writeOneCall(app, "files_unique_to_one_dir", oneDirFiles);

      new FileWriteUtil(app)
      .launchWrite("files_unique_to_one_dir")
      .writeToFile(new String[]{("\n"+EiStrings.EQUAL_CHAR_72X),("Filenames within ONE directory..."),(EiStrings.DASH_CHAR_72X)})
      .writeToFile(oneDirFiles)
      .writeToFile(EiStrings.EQUAL_CHAR_72X)
      .finishWrite();
    }
    Cons.log("...completed logging files unique to one directory");
    
    Cons.log("================================\n######### SUMMARY INFO #########\n--------------------------------");
    Cons.log("> Dims Differ: "+numDiffDims);
    Cons.log("> Pixs Differ: "+numDiffPixs);
    Cons.log("> Pixs Match:  "+numSamePixs);
    Cons.log("> Olnly 1 Dir:  "+numOnly1Dir);
  }

  public static String pixAndColToString(int pRow, int pCol, int rawColor){
    return "Pixel: ("+pRow+","+pCol+"), Color: "+FormatUtils.colorRGBAToString(rawColor);
  }

  private static String tableEvtRow(int evt, int row, int col, int rc1, int rc2){
    return "("+PApplet.nf(evt,4)+") | ("+PApplet.nf(row,4)+","+PApplet.nf(col,4)+")  | "+
    FormatUtils.colorRGBAToString(rc1)+" | "+FormatUtils.colorRGBAToString(rc2);
  }
}