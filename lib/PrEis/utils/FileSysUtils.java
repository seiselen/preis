package PrEis.utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import processing.data.StringList;

/**
 * @obsolete functions {@link #listDirFileNames} and {@link #listDirFilePaths} 
 * are mostly functionally equivalent to counterparts {@link #fileNamesOfDir}
 * and {@link #filePathsOfDir}, sans the 'filter-by-{@link ExtType}' feature.
 * <b>(KEEP FOR NOW)</b>
 * @obsolete function {@link #fnameFromFpath} is currently unused, as its prior
 * utility within `listDirFile[Name/Path]s` was replaced by a call of `getName`
 * foreach `File`. <b>(KEEP FOR NOW)</b>
 */
public class FileSysUtils {

  /** Opcode for if/how to format a full filepath. */
  private enum PathInfo {AS_FPATH, AS_FNAME};

  private final static int DEF_FIND_LEV = 1;

  public static String pathConcat(String ... sps){
    String ret = "";
    for(int i=0; i<sps.length; i++){
      ret += sps[i];
      if(i<sps.length-1){ret += "/";}
    }
    return ret;
  }  


  public static String fnameFromFpath(String fileName){
    return _fnameFromFpath(fileName,true);
  }

  public static String fnameFromFpath(String fileName, boolean keepExt){
    return _fnameFromFpath(fileName,keepExt);
  }

  private static String _fnameFromFpath(String fn, boolean keepExt){
    fn = (!fn.contains("/")) ? fn : fn.substring(fn.lastIndexOf('/')+1);
    return (keepExt || !fn.contains(".")) ? fn : fn.substring(0,fn.lastIndexOf('.'));
  }

  public static boolean fNameExtIs(String fn, ExtType et){
    return fn.endsWith(et.val());
  }

  public static boolean fNameExtAnyOf(String fn, ExtType ... exts){
    for(ExtType e : exts){if(fNameExtIs(fn, e)){return true;}}
    return false;
  }  
  
  public static String appendExtIfNeeded(String s, ExtType e){
    return s.endsWith(e.val()) ? s : s.concat(e.val());
  }
  
  public static String fullPathFileName(String p, String n, ExtType t){
    return pathConcat(p,appendExtIfNeeded(n,t));
  }

  public static String winPthToLinuxPth(String winPath){
    return winPath.replace("\\","/");
  }

  public static String[] winPthsToLinuxPths(String[] winPaths){
    String[] ret = new String[winPaths.length];
    for(int i=0; i<winPaths.length; i++){ret[i]=winPthToLinuxPth(winPaths[i]);}
    return ret;
  }

  public static String[] listDirFileNames(String dirPath){
    return _listDirFiles(dirPath, PathInfo.AS_FNAME);
  }

  public static String[] listDirFilePaths(String dirPath){
    return _listDirFiles(dirPath, PathInfo.AS_FPATH);
  }

  private static String[] _listDirFiles(String dirPath, PathInfo reqInfo){
    File dirAsFile = new File(dirPath);
    if (!dirAsFile.isDirectory()){return null;}
    File[] dirFiles = dirAsFile.listFiles();
    StringList dirList = new StringList();
    for(File f : dirFiles){
      dirList.append(reqInfo==PathInfo.AS_FNAME ? f.getName() : winPthToLinuxPth(f.getAbsolutePath()));
    }
    return dirList.toArray();
  }

  public static String[] filePathsOfDir(String dir){
    return _filePathStrs(_filePathsOfDir(dir), PathInfo.AS_FPATH);
  }

  public static String[] fileNamesOfDir(String dir){
    return _filePathStrs(_filePathsOfDir(dir), PathInfo.AS_FNAME);
  }

  public static String[] filePathsOfDir(String dir, ExtType ... exts){
    return _filePathStrs(_filePathsOfDir(dir, exts), PathInfo.AS_FPATH);
  }

  public static String[] fileNamesOfDir(String dir, ExtType ... exts){
    return _filePathStrs(_filePathsOfDir(dir, exts), PathInfo.AS_FNAME);
  }

  private static Path[] _filePathsOfDir(String dir){
    try {return Files.list(Paths.get(dir)).toArray(Path[]::new);}
    catch(IOException ie) {ie.printStackTrace(); return null;}
  }

  private static Path[] _filePathsOfDir(String dir, ExtType ... exts){
    try {
      return Files.find(Paths.get(dir),DEF_FIND_LEV,(p,a)->(fNameExtAnyOf(p.getFileName().toString(),exts)))
      .toArray(Path[]::new);
    }
    catch(IOException ie) {ie.printStackTrace(); return null;}
  }

  private static String[] _filePathStrs(Path[] paths, PathInfo reqInfo){
    if(QueryUtils.arrayNullOrEmpty(paths)){return null;}
    int n = paths.length;
    String[] ret = new String[n];
    for(int i=0; i<n; i++){
      ret[i] = (reqInfo==PathInfo.AS_FPATH) 
        ? winPthToLinuxPth(paths[i].toString())
        : paths[i].getFileName().toString();
    }
    return ret;
  }

}