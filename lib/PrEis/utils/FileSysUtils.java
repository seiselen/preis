package PrEis.utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import processing.data.StringList;

public class FileSysUtils {

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

  /**
   * @todo realize option whereby bool opcode specifies to trim extension (a/a)
   */
  public static String fnameFromFpath(String fn){
    if(!fn.contains("/")){return fn;} //> no slash means we assume it's already a fn
    int idx = fn.lastIndexOf('/')+1;
    return fn.substring(idx);
  }


  public static String fullPathFileName(String p, String n, ExtType t){
    return pathConcat(p,appendExtIfNeeded(n,t));
  }
  

  public static String appendExtIfNeeded(String s, ExtType e){
    return s.endsWith(e.val()) ? s : s.concat(e.val());
  }


  public static String[] listDirFileNames(String dirPath){
    return _listDirFiles(dirPath, true);
  }

  public static String[] listDirFilePaths(String dirPath){
    return _listDirFiles(dirPath, false);
  }

  private static String[] _listDirFiles(String dirPath, boolean onlyNames){
    File dirAsFile = new File(dirPath);
  
    if (!dirAsFile.isDirectory()){return null;}
    
    File[] dirFiles = dirAsFile.listFiles();
      
    String curPath = "";
    StringList dirList = new StringList();
      
    for(File f : dirFiles){
      curPath = f.getAbsolutePath().replace("\\", "/");
      dirList.append(onlyNames ? fnameFromFpath(curPath) : curPath);
    }
    
    return dirList.toArray();
  }


public static boolean fNameExtIs(String fn, ExtType et){
    return fn.endsWith(et.val());
  }

  public static boolean fNameExtAnyOf(String fn, ExtType ... exts){
    for(ExtType e : exts){if(fNameExtIs(fn, e)){return true;}}
    return false;
  }  

  private static Path[] _filePathsOfDir(String dir){
    try {return Files.list(Paths.get(dir)).toArray(Path[]::new);}
    catch(IOException ie) {ie.printStackTrace(); return null;}
  }


  private static Path[] _filePathsOfDir(String dir, ExtType ... exts){
    try {
      return Files.find(
        Paths.get(dir), DEF_FIND_LEV,
        (p,a)->(fNameExtAnyOf(p.getFileName().toString(), exts))
      ).toArray(Path[]::new);
    }
    catch(IOException ie) {ie.printStackTrace(); return null;}
  }

  private static String[] _filePathStrs(Path[] paths, PathInfo reqInfo){
    if(QueryUtils.arrayNullOrEmpty(paths)){return null;}
    
    int n = paths.length;
    String[] ret = new String[n];
    for(int i=0; i<n; i++){
      ret[i] = (reqInfo==PathInfo.AS_FPATH) ? paths[i].toString() : paths[i].getFileName().toString();}
    return ret;
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


  
  public static String winPthToLinuxPth(String winPath){
    return winPath.toString().replace("\\","/");
  }

  public static String[] winPthsToLinuxPths(String[] winPaths){
    String[] ret = new String[winPaths.length];
    for(int i=0; i<winPaths.length; i++){ret[i]=winPthToLinuxPth(winPaths[i]);}
    return ret;
  }

}
