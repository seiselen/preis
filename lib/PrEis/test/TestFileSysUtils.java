package PrEis.test;
import PrEis.utils.ExtType;
import PrEis.utils.FileSysUtils;
import processing.core.PApplet;

public class TestFileSysUtils {
  /**
   * @param p PApplet of Processing app sketch where this is called
   * @param xFName name of JSON with dir examples
   * @param xArrKey key of `String[]` in JSON of examples
   * @implNote JSON file of examples MUST be in <code>/data/*</code> dir
   */
  public static void runTests(PApplet p, String[] realDirTests){
    System.out.println("<=[ Testing 'pathConcat' ]=====================>");
    test_pathConcat();
    System.out.println("<=[ Testing 'fnameFromFpath' ]=================>");
    test_fnameFromFpath();
    System.out.println("<=[ Testing 'winPth(s)ToLinuxPth(s)' ]=========>");
    test_winPathToLinux();
    System.out.println("<=[ Testing 'fNameExtIs' ]=====================>");
    test_fNameExtIs();
    System.out.println("<=[ Testing 'fNameExtAnyOf' ]==================>");
    test_fNameExtAnyOf();
    System.out.println("<=[ Testing 'appendExtIfNeeded' ]==============>");
    test_appendExtIfNeeded();
    System.out.println("<=[ Testing 'fullPathFileName' ]===============>");
    test_fullPathFileName();
    System.out.println("<=[ Testing All 'Filenames Of Dir' ]===========>");
    test_fileNamesAndPathsOfDir(p,realDirTests);
  }

  private static void test_pathConcat(){
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(FileSysUtils.pathConcat(), ""),
      TestFunc.doEval(FileSysUtils.pathConcat("test"), "test"),
      TestFunc.doEval(FileSysUtils.pathConcat("users","Batman"), "users/Batman"),
      TestFunc.doEval(FileSysUtils.pathConcat("users","Batman","bin"), "users/Batman/bin"),
      TestFunc.doEval(FileSysUtils.pathConcat("users","Batman","bin","suparman.txt"), "users/Batman/bin/suparman.txt"),
      TestFunc.doEval(FileSysUtils.pathConcat("a","B","c","D","e","F","g","H"), "a/B/c/D/e/F/g/H"),
    });
  }

  private static void test_fnameFromFpath(){
    String xfnm = "afile";
    String xfnx = xfnm+".txt";
    String dir1 = "aDir/";
    String dir2 = dir1+"bDir/";
    String dir3 = dir2+"cDir/dDir/eDir/fDir/";
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(FileSysUtils.fnameFromFpath(     xfnm), xfnm),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir1+xfnm), xfnm),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir2+xfnm), xfnm),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir3+xfnm), xfnm),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(     xfnx), xfnx),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir1+xfnx), xfnx),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir2+xfnx), xfnx),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir3+xfnx), xfnx),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(     xfnx, false), xfnm),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir1+xfnx, false), xfnm),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir2+xfnx, false), xfnm),
      TestFunc.doEval(FileSysUtils.fnameFromFpath(dir3+xfnx, false), xfnm),
    });
  }

  private static void test_winPathToLinux(){
    String[] winDirs = new String[]{
      "test1",
      "test1.json",
      "sprites\\test1",
      "sprites\\test1.json",
      "C:\\Users\\Yolo\\GameAssets\\sprites\\test1",
      "C:\\Users\\Yolo\\GameAssets\\sprites\\test1.json"};
    String[] linDirs = new String[]{
      "test1",
      "test1.json",
      "sprites/test1",
      "sprites/test1.json",
      "C:/Users/Yolo/GameAssets/sprites/test1",
      "C:/Users/Yolo/GameAssets/sprites/test1.json"
    };
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(FileSysUtils.winPthToLinuxPth(winDirs[0]), linDirs[0]),
      TestFunc.doEval(FileSysUtils.winPthToLinuxPth(winDirs[1]), linDirs[1]),
      TestFunc.doEval(FileSysUtils.winPthToLinuxPth(winDirs[2]), linDirs[2]),
      TestFunc.doEval(FileSysUtils.winPthToLinuxPth(winDirs[3]), linDirs[3]),
      TestFunc.doEval(FileSysUtils.winPthToLinuxPth(winDirs[4]), linDirs[4]),
      TestFunc.doEval(FileSysUtils.winPthToLinuxPth(winDirs[5]), linDirs[5]),
      TestFunc.doEval(FileSysUtils.winPthsToLinuxPths(winDirs), linDirs),
    });
  }

  private static void test_fNameExtIs(){
    String xPNG = "C:/Users/SomeUser/Documents/Images/someImg.png";
    String xOGG = "C:\\Users\\SomeUser\\Documents\\Music\\someSong.ogg";
    String xTXT = "data/someText.txt";
    String xJSN = "someJson.json";
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(FileSysUtils.fNameExtIs(xPNG, ExtType.PNG), true),
      TestFunc.doEval(FileSysUtils.fNameExtIs(xPNG, ExtType.PK3), false),
      TestFunc.doEval(FileSysUtils.fNameExtIs(xOGG, ExtType.OGG), true),
      TestFunc.doEval(FileSysUtils.fNameExtIs(xOGG, ExtType.WAD), false),
      TestFunc.doEval(FileSysUtils.fNameExtIs(xTXT, ExtType.TXT), true),
      TestFunc.doEval(FileSysUtils.fNameExtIs(xTXT, ExtType.DEH), false),
      TestFunc.doEval(FileSysUtils.fNameExtIs(xJSN, ExtType.JSON), true),
      TestFunc.doEval(FileSysUtils.fNameExtIs(xJSN, ExtType.WAV), false),
    });
  }

  private static void test_fNameExtAnyOf(){
    String xPNG = "C:/Users/SomeUser/Documents/Images/someImg.png";
    String xOGG = "C:\\Users\\SomeUser\\Documents\\Music\\someSong.ogg";
    String xTXT = "data/someText.txt";
    String xJSN = "someJson.json";
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xPNG, ExtType.PNG), true),
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xPNG, ExtType.PK3), false),
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xOGG, ExtType.DEH, ExtType.OGG), true),
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xOGG, ExtType.WAD, ExtType.WAV), false),
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xTXT, ExtType.WAV, ExtType.DEH, ExtType.TXT), true),
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xTXT, ExtType.DEH, ExtType.PK3, ExtType.WAD), false),
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xJSN, ExtType.JSON, ExtType.DEH, ExtType.TXT, ExtType.WAD), true),
      TestFunc.doEval(FileSysUtils.fNameExtAnyOf(xJSN, ExtType.WAV,  ExtType.WAD, ExtType.DEH, ExtType.OGG), false),
    });
  }

  public static void test_appendExtIfNeeded(){
    String dirFile = "someFile";
    String dirPFix = "A:/B/C/D/E/"+dirFile;
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirPFix,        ExtType.PNG), dirPFix+ExtType.PNG.val()),
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirFile,        ExtType.OGG), dirFile+ExtType.OGG.val()),
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirPFix,        ExtType.TXT), dirPFix+ExtType.TXT.val()),
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirFile,        ExtType.WAD), dirFile+ExtType.WAD.val()),
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirPFix+".png", ExtType.PNG), dirPFix+ExtType.PNG.val()),
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirFile+".ogg", ExtType.OGG), dirFile+ExtType.OGG.val()),
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirPFix+".txt", ExtType.TXT), dirPFix+ExtType.TXT.val()),
      TestFunc.doEval(FileSysUtils.appendExtIfNeeded(dirFile+".wad", ExtType.WAD), dirFile+ExtType.WAD.val()),
    });
  }

  public static void test_fullPathFileName(){
    String dPath = "A:/B/C/D/E";
    String fName = "someFile";
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName, ExtType.PNG), dPath+"/"+fName+ExtType.PNG.val()),
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName, ExtType.OGG), dPath+"/"+fName+ExtType.OGG.val()),
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName, ExtType.TXT), dPath+"/"+fName+ExtType.TXT.val()),
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName, ExtType.WAD), dPath+"/"+fName+ExtType.WAD.val()),
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName+ExtType.PNG.val(), ExtType.PNG), dPath+"/"+fName+ExtType.PNG.val()),
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName+ExtType.OGG.val(), ExtType.OGG), dPath+"/"+fName+ExtType.OGG.val()),
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName+ExtType.TXT.val(), ExtType.TXT), dPath+"/"+fName+ExtType.TXT.val()),
      TestFunc.doEval(FileSysUtils.fullPathFileName(dPath, fName+ExtType.WAD.val(), ExtType.WAD), dPath+"/"+fName+ExtType.WAD.val()),
    });
  }
  
  //> public static String[] fileNamesOfDir (String dir, ExtType ... exts)
  //> public static String[] filePathsOfDir (String dir, ExtType ... exts)
  public static void test_fileNamesAndPathsOfDir(PApplet p, String[] exs){
    if(exs==null || exs.length==0){
      System.err.println("ERROR: Input dir `String[]` nullish! (null xor length==0)"); 
    }
    try {
      TestFunc.testResultsToConsole(new boolean[]{        
        TestFunc.doEval(FileSysUtils.listDirFileNames(exs[0]), FileSysUtils.fileNamesOfDir(exs[0])),
        TestFunc.doEval(FileSysUtils.listDirFileNames(exs[1]), FileSysUtils.fileNamesOfDir(exs[1])),
        TestFunc.doEval(FileSysUtils.listDirFileNames(exs[2]), FileSysUtils.fileNamesOfDir(exs[2])),
        TestFunc.doEval(FileSysUtils.listDirFileNames(exs[3]), FileSysUtils.fileNamesOfDir(exs[3])),
        TestFunc.doEval(FileSysUtils.listDirFileNames(exs[4]), FileSysUtils.fileNamesOfDir(exs[4])),
        TestFunc.doEval(FileSysUtils.listDirFileNames(exs[5]), FileSysUtils.fileNamesOfDir(exs[5])),

        TestFunc.doEval(FileSysUtils.listDirFilePaths(exs[0]), FileSysUtils.filePathsOfDir(exs[0])),
        TestFunc.doEval(FileSysUtils.listDirFilePaths(exs[1]), FileSysUtils.filePathsOfDir(exs[1])),
        TestFunc.doEval(FileSysUtils.listDirFilePaths(exs[2]), FileSysUtils.filePathsOfDir(exs[2])),
        TestFunc.doEval(FileSysUtils.listDirFilePaths(exs[3]), FileSysUtils.filePathsOfDir(exs[3])),
        TestFunc.doEval(FileSysUtils.listDirFilePaths(exs[4]), FileSysUtils.filePathsOfDir(exs[4])),
        TestFunc.doEval(FileSysUtils.listDirFilePaths(exs[5]), FileSysUtils.filePathsOfDir(exs[5])),

        TestFunc.doEval(FileSysUtils.fileNamesOfDir(exs[3], ExtType.WAD, ExtType.DEH), new String[]{"180mpv.wad", "180mpv_alt_map30.wad", "180mpv_deh.deh"}),
        TestFunc.doEval(FileSysUtils.fileNamesOfDir(exs[3], ExtType.TXT), new String[]{"180mpv.txt", "180mpv_b.txt", "180mpv_info.txt"}),
        TestFunc.doEval(FileSysUtils.fileNamesOfDir(exs[3], ExtType.JSON), new String[]{"loadinfo.json"}),
        TestFunc.doEval(FileSysUtils.fileNamesOfDir(exs[4], ExtType.WAD, ExtType.DEH), new String[]{"PL2.deh","PL2.wad"}),
        TestFunc.doEval(FileSysUtils.fileNamesOfDir(exs[4], ExtType.TXT), new String[]{"PL2.txt", "PL2INFO.txt"}),
        TestFunc.doEval(FileSysUtils.fileNamesOfDir(exs[5], ExtType.JSON, ExtType.ZIP), new String[]{"loadinfo.json", "stuff.zip"}),
      });
    } 
    catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Something went wrong with file[dir] retrieval!");
    }
  }
}