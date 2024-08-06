package PrEisUtils;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.IntDict;
import processing.data.StringList;

public class PImageUtils {

  private static boolean LOG_BAKE_DONE  = true;
  private static boolean LOG_BAKE_TOTAL = true;
  private static boolean LOG_PX_MATCHES = true;


  enum ReturnCode {TEXS_PIXS_MATCH, TEXS_PIXS_MISMATCH, TEXS_DIMS_MISMATCH}

  private PApplet p;
  PImage buffImage;

  public PImageUtils(PApplet parent){
    p = parent;
  }

  void bakeDirOfTextures(String inDirpath, String outDirpath){
    String[] fNames = FileSysUtils.fileNamesOfDir(inDirpath, ExtType.PNG);
    int total = fNames.length;
    for(int i=0; i<fNames.length; i++){
      bakeTexture(fNames[i],inDirpath,outDirpath);
      if(LOG_BAKE_TOTAL){Cons.log("Baked ("+(i+1)+"/"+total+") images");}
    }
  }
  
  //> @TODO: Do I need the `appendExtIfNeeded`calls?
  void bakeTexture(String fName, String inDirpath, String outDirpath){
    String fullNameI = FileSysUtils.appendExtIfNeeded(FileSysUtils.pathConcat(inDirpath,fName),ExtType.PNG);
    String fullNameO = FileSysUtils.appendExtIfNeeded(FileSysUtils.pathConcat(outDirpath,fName),ExtType.PNG);
    Cons.log(fullNameO);
    buffImage = p.loadImage(fullNameI);
    buffImage.save(fullNameO);
    if(LOG_BAKE_DONE){Cons.log("successfully loaded ("+fullNameI+") and saved as ("+fullNameO+")");}
  }




  //> @TODO: Again...Do I need the `appendExtIfNeeded`calls?
ReturnCode imageColorValueCompare(String path1, String name1, String path2, String name2){

  String fullName1 = FileSysUtils.appendExtIfNeeded(FileSysUtils.pathConcat(path1, name1),ExtType.PNG);
  String fullName2 = FileSysUtils.appendExtIfNeeded(FileSysUtils.pathConcat(path2, name2),ExtType.PNG);
  PImage img1      = p.loadImage(fullName1);
  PImage img2      = p.loadImage(fullName2);

  if(img1.width!=img2.width || img1.height!=img2.height){return ReturnCode.TEXS_DIMS_MISMATCH;}
  
  boolean doDiff = false;
  int     pixLen = img1.width * img1.height;    
  int     curRow = 0;
  int     curCol = 0;
  int     curEvt = 1;
  int     pxCol1 = -1;
  int     pxCol2 = -1;

  FileWriteUtil fw = new FileWriteUtil(p);
  
  for(int i=0; i<pixLen; i++){
    pxCol1 = img1.pixels[i];
    pxCol2 = img2.pixels[i];
    if(pxCol1 != pxCol2){
      if(!doDiff){
        fw.launchWrite("/mismatch_log/"+name1+"-vs-"+name2);
        fw.writeToFile(new String[]{diffTextHeader(fullName1,fullName2), tableBorder(), tableHeader(), tableBorder()});
        doDiff=true;
      }
      fw.writeToFile(tableEvtRow(curEvt, curRow, curCol, pxCol1, pxCol2));
      curEvt++;      
    }    
    curCol++; if(curCol%32==0){curRow++; curCol=0;}
  }
  
  if(doDiff){fw.finishWrite(); return ReturnCode.TEXS_PIXS_MISMATCH;}
  
  return ReturnCode.TEXS_PIXS_MATCH;
}


void imageColorValueCompareDirTextures(String path1, String path2){
  ReturnCode curRetCode;
  int numSamePixs = 0;
  int numDiffDims = 0;
  int numDiffPixs = 0;
  int numOnly1Dir = 0;
  
  StringList mismatchDimsList = new StringList();
  StringList matchingPixsList = new StringList();
  
  String [] p1Files = FileSysUtils.fileNamesOfDir(path1, ExtType.PNG);
  String [] p2Files = FileSysUtils.fileNamesOfDir(path2, ExtType.PNG);
  
  IntDict imgNameFreqsBothDirs = new IntDict();
  for(String fn : p1Files){imgNameFreqsBothDirs.increment(fn);}
  for(String fn : p2Files){imgNameFreqsBothDirs.increment(fn);}
  String [] namesInBothDirs = DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 2);
  Cons.log("...completed 'in-both-dirs' dict enumerations");
  
  for(String n : namesInBothDirs){
    curRetCode = imageColorValueCompare(path1,n, path2,n);
    
    switch(curRetCode){
      case TEXS_DIMS_MISMATCH: numDiffDims++; mismatchDimsList.append(n); break;
      case TEXS_PIXS_MISMATCH: numDiffPixs++; break;
      case TEXS_PIXS_MATCH:    numSamePixs++; matchingPixsList.append(n); break;      
    } 
  }

  
  //> Need to handle mismatch list in this way
  if(mismatchDimsList.size()>0){
    new FileWriteUtil(p)
    .launchWrite("files_of_different_dims")
    .writeToFile(mismatchDimsList.toArray())
    .finishWrite();
  }
  Cons.log("...completed 'in-both-dirs' return case total sums and logging files of mismatched dims");
  
  //> Need to handle pixel col matching list in this way
  if(LOG_PX_MATCHES){
    if(matchingPixsList.size()>0){ 
      new FileWriteUtil(p)
      .launchWrite("files_that_match")
      .writeToFile(matchingPixsList.toArray())
      .finishWrite();      
    Cons.log("...completed logging files of matching pixel colors");  
  }
 
  String[] oneDirFiles = DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 1);
  
  if(oneDirFiles.length>0){
    numOnly1Dir = oneDirFiles.length;
    new FileWriteUtil(p)
    .launchWrite("files_unique_to_one_dir")
    .writeToFile(new String[]{("\n"+separator72_equalChar()),("Filenames within ONE directory..."),(separator72_dashChar())})
    .writeToFile(oneDirFiles)
    .writeToFile(separator72_equalChar())
    .finishWrite();
  }
  Cons.log("...completed logging files unique to one directory");
  
  Cons.log("================================\n######### SUMMARY INFO #########\n--------------------------------");
  Cons.log("> Dims Differ: "+numDiffDims);
  Cons.log("> Pixs Differ: "+numDiffPixs);
  Cons.log("> Pixs Match:  "+numSamePixs);
  Cons.log("> Only 1 Dir:  "+numOnly1Dir);
  }
}

void diffDirFilenames(String path1, String path2){
  String [] p1Files = FileSysUtils.fileNamesOfDir(path1, ExtType.PNG);
  String [] p2Files = FileSysUtils.fileNamesOfDir(path2, ExtType.PNG);

  IntDict imgNameFreqsBothDirs = new IntDict();
    
  for(String fn : p1Files){imgNameFreqsBothDirs.increment(fn);}
  for(String fn : p2Files){imgNameFreqsBothDirs.increment(fn);}
  
  Cons.log(separator72_equalChar());
  Cons.log("Filenames within BOTH directories...");
  Cons.log(separator72_dashChar());
  Cons.log(DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 2));
  Cons.log(separator72_equalChar());
  
  Cons.log("\n"+separator72_equalChar());
  Cons.log("Filenames within ONE directory...");
  Cons.log(separator72_dashChar());  
  Cons.log(DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 1));  
  Cons.log(separator72_equalChar());  
}



String separator72_equalChar(){return "========================================================================";}
String separator72_dashChar(){return "------------------------------------------------------------------------";}
String tableBorder(){return "=======+==============+===================+==================";}
String tableHeader(){return "Event  | Pixel Coords | (R-1,G-1,B-1,A-1) | (R-2,G-2,B-2,A-2)";}


  private static String tableEvtRow(int evt, int row, int col, int rc1, int rc2){
    return StringUtils.concatStrings(
      "(",
      PApplet.nf(evt,4),
      ") | (",
      PApplet.nf(row,4),
      ",",
      PApplet.nf(col,4),
      ")  | ",
      FormatUtils.colorRGBAToString(FormatUtils.colorRGBAFromPColor(rc1)),
      " | ",
      FormatUtils.colorRGBAToString(FormatUtils.colorRGBAFromPColor(rc2))
    );
    
    
  }

  @SuppressWarnings("unused") //> keeping in case I need it
  private static String diffTextHeader(String fp1, String fn1, String fp2, String fn2){
    return diffTextHeader(FileSysUtils.fullPathFileName(fp1,fn1), FileSysUtils.fullPathFileName(fp2,fn2));
  }

  private static String diffTextHeader(String f1, String f2){
    return "Differences between\n * <"+f1+"> and\n * <"+f2+">\nare as follows:";
  }

}
