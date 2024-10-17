package PrEis.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.data.IntDict;

/**
 * @implNote <code>nullish</code> is expected to be realized for specific object
 * types when/as needed; i.e. there is NO generalized (i.e. <code>object</code> 
 * parm version: just do the comparison to <code>null</code> inline...
 */
public class QueryUtils {
  
  public static <T> boolean arrayNullOrEmpty(T[] arr){
    return arr==null || arr.length==0;
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  |>>> TESTING NOTE: The following three `null<…>` functions WERE successfully
  |                  (QAD) unit tested on `08/28/24` via W3Schools Java Editor.
  +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  /** Returns true if <b>ALL</b> of the input objects are <code>null</code>. */
  public static boolean nullAll(Object ... objs){
    for(Object o : objs){if (o!=null){return false;}}
    return true;
  }

  /** Returns true if <b>NONE</b> of the input objects are <code>null</code>. */
  public static boolean nullNone(Object ... objs){
    for(Object o : objs){if (o==null){return false;}}
    return true;
  }

  /** Returns true if <b>AT LEAST ONE</b> input object is <code>null</code>. */
  public static boolean nullAny(Object ... objs){
    for(Object o : objs){if (o==null){return true;}}
    return false;
  }


  public static boolean strArrContainsStr(String[] arr, String str){
    if(arrayNullOrEmpty(arr)){return false;}
    for(String s : arr){if(str.equals(s)){return true;}}
    return false;
  }


  public static String epochSecondToString(){
    return ""+Instant.now().getEpochSecond();
  }

  public static String dateTimeToString(){
    return StringUtils.concatStrings(
      PApplet.nf(PApplet.month(),2)+"/",
      PApplet.nf(PApplet.day(),2)+"/",
      PApplet.year()+".",
      PApplet.nf(PApplet.hour(),2)+":",
      PApplet.nf(PApplet.minute())+":",
      PApplet.nf(PApplet.second())+""
    );
  }


public static String[] diffDirFilenames(String path1, String path2){
  return _diffDirFilenames(FileSysUtils.fileNamesOfDir(path1), FileSysUtils.fileNamesOfDir(path2));
}

public static String[] diffDirFilenames(String path1, String path2, ExtType fType){
  return _diffDirFilenames(FileSysUtils.fileNamesOfDir(path1, fType), FileSysUtils.fileNamesOfDir(path2, fType));
}

private static String[] _diffDirFilenames(String[] p1Files, String[] p2Files){
  ArrayList<String> sList = new ArrayList<String>();
  IntDict imgNameFreqsBothDirs = new IntDict();
    
  for(String fn : p1Files){imgNameFreqsBothDirs.increment(fn);}
  for(String fn : p2Files){imgNameFreqsBothDirs.increment(fn);}
  
  sList.add(EiStrings.EQUAL_CHAR_72X);
  sList.add("Filenames within BOTH directories...");
  sList.add(EiStrings.DASH_CHAR_72X);
  sList.addAll(Arrays.asList(DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 2)));
  sList.add(EiStrings.EQUAL_CHAR_72X);
  
  sList.add("\n"+EiStrings.EQUAL_CHAR_72X);
  sList.add("Filenames within ONE directory...");
  sList.add(EiStrings.DASH_CHAR_72X);  
  sList.addAll(Arrays.asList(DataStructUtils.filterIntDictByFreq(imgNameFreqsBothDirs, '=', 1)));
  sList.add(EiStrings.EQUAL_CHAR_72X);

  return FormatUtils.arrFromList(String.class, sList);
}



}