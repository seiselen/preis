package PrEis.test;
import PrEis.utils.StringUtils;

public class TestStringUtils {

  private static String STREX1 = "batman bin suparman";



  private static PrEisTest TC(){return new PrEisTest();}

  public static void runTests(){
    System.out.println("<=[ Testing 'charTimesN' ]=====================>");
    test_charTimesN();
    System.out.println("<=[ Testing 'padL' ]===========================>");
    test_padL();
    System.out.println("<=[ Testing 'padR' ]===========================>");
    test_padR();
    System.out.println("<=[ Testing 'padL' (arrays) ]==================>");
    test_padL_arrays();
    System.out.println("<=[ Testing 'padR' (arrays) ]==================>");
    test_padR_arrays();
    System.out.println("<=[ Testing 'wrapString' ]=====================>");
    test_wrapStringWith();
    System.out.println("<=[ Testing 'lastCharOf' ]=====================>");
    test_lastCharOf();
    System.out.println("<=[ Testing 'capFirstChar' ]===================>");
    test_capFirstChar();
    System.out.println("<=[ Testing 'valToSSVCapdWords']===============>");
    test_valToSSVCapdWords();
    System.out.println("<=[ Testing 'concatStrings' ]==================>");
    test_concatStrings();
    System.out.println("<=[ Testing 'concatAsCSV' ]====================>");
    test_concatAsCSV();
    System.out.println("<=[ Testing 'concatAsSCSV' ]===================>");
    test_concatAsSCSV();
  }
  
  public static void test_charTimesN(){
    PrEisTestFunc.testResultsToConsole(new boolean[]{
      PrEisTestFunc.doEval(StringUtils.charTimesN('0', 0), ""),
      PrEisTestFunc.doEval(StringUtils.charTimesN('A', 1), "A"),
      PrEisTestFunc.doEval(StringUtils.charTimesN('B', 2), "BB"),
      PrEisTestFunc.doEval(StringUtils.charTimesN('C', 3), "CCC"),   
      PrEisTestFunc.doEval(StringUtils.charTimesN('D', 4), "DDDD"),
      PrEisTestFunc.doEval(StringUtils.charTimesN('E', 5), "EEEEE"),
      PrEisTestFunc.doEval(StringUtils.charTimesN('F', 6), "FFFFFF"),
      PrEisTestFunc.doEval(StringUtils.charTimesN('G', 7), "GGGGGGG"),
      PrEisTestFunc.doEval(StringUtils.charTimesN('8', 8), "88888888")
    });
  }

  public static void test_padL(){
    String[][] testVals = new String[][]{
      {  "0", "-17",    "0"},
      {  "0",   "0",    "0"},
      {  "0",   "1",    "0"},
      {  "0",   "2",   " 0"},
      { "17", "-31",   "17"},
      { "17",   "0",   "17"},
      { "17",   "1",   "17"},
      { "17",   "2",   "17"},
      { "17",   "3",  " 17"},
      { "17",   "4", "  17"},
      {"-17", "-31",  "-17"},
      {"-17",   "0",  "-17"},
      {"-17",   "1",  "-17"},
      {"-17",   "2",  "-17"},
      {"-17",   "3",  "-17"},
      {"-17",   "4", " -17"}
    };

    int nTests = testVals.length;
    boolean[] results = new boolean[nTests];

    for(int i=0; i<nTests; i++){
      results[i] = TC()
      .output(StringUtils.padL(""+Integer.valueOf(testVals[i][0]),Integer.valueOf(testVals[i][1])))
      .expect(testVals[i][2])
      .evalBool();
    }
    PrEisTestFunc.testResultsToConsole(results);
  }

  public static void test_padR(){
    String[][] testVals = new String[][]{
      {  "0", "-17",    "0"},  
      {  "0",   "0",    "0"},
      {  "0",   "1",    "0"},  
      {  "0",   "2",   "0 "},
      { "17", "-31",   "17"}, 
      { "17",   "0",   "17"},
      { "17",   "1",   "17"}, 
      { "17",   "2",   "17"},
      { "17",   "3",  "17 "},
      { "17",   "4", "17  "},
      {"-17", "-31",  "-17"},
      {"-17",   "0",  "-17"},
      {"-17",   "1",  "-17"},
      {"-17",   "2",  "-17"},
      {"-17",   "3",  "-17"},
      {"-17",   "4", "-17 "},
    };

    int nTests = testVals.length;
    boolean[] results = new boolean[nTests];

    for(int i=0; i<nTests; i++){
      results[i] = TC()
      .output(StringUtils.padR(""+Integer.valueOf(testVals[i][0]),Integer.valueOf(testVals[i][1])))
      .expect(testVals[i][2])
      .evalBool();
    }
    PrEisTestFunc.testResultsToConsole(results);
  }

  public static void test_padL_arrays(){
    String[] pad_ArrIn = new String[]{"","b","bat","batman","bin","suparman","batman bin","batman bin suparman"};
    String[] padL_ArrExp = new String[]{"                   ", "                  b", "                bat", "             batman", "                bin", "           suparman", "         batman bin", "batman bin suparman"};
    boolean t1 = PrEisTestFunc.doEval(StringUtils.padL(pad_ArrIn, 0), pad_ArrIn);
    for(int i=0; i<padL_ArrExp.length; i++){padL_ArrExp[i] = "  "+padL_ArrExp[i];}
    boolean t2 = PrEisTestFunc.doEval(StringUtils.padL(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2), padL_ArrExp);
    PrEisTestFunc.testResultsToConsole(new boolean[]{t1,t2});
  }

  public static void test_padR_arrays(){
    String[] pad_ArrIn = new String[]{"","b","bat","batman","bin","suparman","batman bin","batman bin suparman"};
    String[] padR_ArrExp = new String[]{"                   ", "b                  ", "bat                ", "batman             ", "bin                ", "suparman           ", "batman bin         ", "batman bin suparman"};
    boolean t1 = PrEisTestFunc.doEval(StringUtils.padR(pad_ArrIn, 0), pad_ArrIn);
    for(int i=0; i<padR_ArrExp.length; i++){padR_ArrExp[i] = padR_ArrExp[i]+"  ";}
    boolean t2 = PrEisTestFunc.doEval(StringUtils.padR(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2), padR_ArrExp);
    PrEisTestFunc.testResultsToConsole(new boolean[]{t1,t2});
  }

  public static void test_wrapStringWith(){PrEisTestFunc.testResultsToConsole(new boolean[]{
    PrEisTestFunc.doEval(StringUtils.wrapWith('"', ""), "\"\""),
    PrEisTestFunc.doEval(StringUtils.wrapWith('"', ""), "\"\""),
    PrEisTestFunc.doEval(StringUtils.wrapWith('"', " "), "\" \""),
    PrEisTestFunc.doEval(StringUtils.wrapWith('"', STREX1), "\""+STREX1+"\""),
    PrEisTestFunc.doEval(StringUtils.wrapWith('#', " "+STREX1+" "), "# "+STREX1+" #"),
    PrEisTestFunc.doEval(StringUtils.wrapWith('"', "", '"'), "\"\""),
    PrEisTestFunc.doEval(StringUtils.wrapWith('"', " ", '"'), "\" \""),
    PrEisTestFunc.doEval(StringUtils.wrapWith(' ', "", ' '), "  "),
    PrEisTestFunc.doEval(StringUtils.wrapWith('<', STREX1, '>'), "<"+STREX1+">"),
    PrEisTestFunc.doEval(StringUtils.wrapWith('<', STREX1), "<"+STREX1+">"),
    PrEisTestFunc.doEval(StringUtils.wrapWith('{', " "+STREX1+" ", '}'), "{ "+STREX1+" }"),
    PrEisTestFunc.doEval(StringUtils.wrapWith('{', STREX1), "{"+STREX1+"}"),
    PrEisTestFunc.doEval(StringUtils.wrapParens(STREX1), "("+STREX1+")"),
    PrEisTestFunc.doEval(StringUtils.wrapParens(" "), "( )")
  });}

  public static void test_lastCharOf(){PrEisTestFunc.testResultsToConsole(new boolean[]{
    PrEisTestFunc.doEval(StringUtils.lastCharOf(""),'\0'),   //> Empty String
    PrEisTestFunc.doEval(StringUtils.lastCharOf(null),'\0'), //> Null Input
    PrEisTestFunc.doEval(StringUtils.lastCharOf("b"),'b'),
    PrEisTestFunc.doEval(StringUtils.lastCharOf("bat"),'t')
  });}

  public static void test_capFirstChar(){PrEisTestFunc.testResultsToConsole(new boolean[]{
    PrEisTestFunc.doEval(StringUtils.capFirstChar(""), ""),      //> Empty String
    PrEisTestFunc.doEvalExpNull(StringUtils.capFirstChar(null)),      //> Null Input
    PrEisTestFunc.doEval(StringUtils.capFirstChar(" "), " "),     //> non {aA:zZ} test #1
    PrEisTestFunc.doEval(StringUtils.capFirstChar("#yolo"), "#yolo"), //> non {aA:zZ} test #2
    PrEisTestFunc.doEval(StringUtils.capFirstChar("b"), "B"),
    PrEisTestFunc.doEval(StringUtils.capFirstChar("bat"), "Bat"),
    PrEisTestFunc.doEval(StringUtils.capFirstChar("B"), "B"),
    PrEisTestFunc.doEval(StringUtils.capFirstChar("BAT"), "BAT")
  });}

  public static void test_valToSSVCapdWords(){PrEisTestFunc.testResultsToConsole(new boolean[]{
    PrEisTestFunc.doEval(StringUtils.valToSSVCapdWords("batman_bin_suparman"), "Batman Bin Suparman"),
    PrEisTestFunc.doEval(StringUtils.valToSSVCapdWords("mixom_moxim"), "Mixom Moxim"),
    PrEisTestFunc.doEval(StringUtils.valToSSVCapdWords("i_o"), "I O"),
    PrEisTestFunc.doEval(StringUtils.valToSSVCapdWords("m_a_g_a"), "M A G A"),
    PrEisTestFunc.doEval(StringUtils.valToSSVCapdWords("_yolo"), "Yolo"),
    PrEisTestFunc.doEval(StringUtils.valToSSVCapdWords("yolo_"), "Yolo"),
    (StringUtils.valToSSVCapdWords("_").equals("")),
    (StringUtils.valToSSVCapdWords("")==null),
    (StringUtils.valToSSVCapdWords(null)==null),
  });}

  public static void test_concatStrings(){PrEisTestFunc.testResultsToConsole(new boolean[]{
    PrEisTestFunc.doEval(StringUtils.concatStrings(""), ""),
    PrEisTestFunc.doEval(StringUtils.concatStrings(" "," "," "," "," "," "), "      "),
    PrEisTestFunc.doEval(StringUtils.concatStrings("<=1=>"), "<=1=>"),
    PrEisTestFunc.doEval(StringUtils.concatStrings("<=1=>","<=2=>","<=3=>"), "<=1=><=2=><=3=>"),
  });}

  public static void test_concatAsCSV(){PrEisTestFunc.testResultsToConsole(new boolean[]{
    PrEisTestFunc.doEval(StringUtils.concatAsCSV(""), ""),
    PrEisTestFunc.doEval(StringUtils.concatAsCSV(" "," "," "," "," "," "), " , , , , , "),
    PrEisTestFunc.doEval(StringUtils.concatAsCSV("<=1=>"), "<=1=>"),
    PrEisTestFunc.doEval(StringUtils.concatAsCSV("<=1=>","<=2=>","<=3=>"), "<=1=>,<=2=>,<=3=>"),
  });}

  public static void test_concatAsSCSV(){PrEisTestFunc.testResultsToConsole(new boolean[]{
    PrEisTestFunc.doEval(StringUtils.concatAsCSSV(""), ""),
    PrEisTestFunc.doEval(StringUtils.concatAsCSSV(" "," "," "," "," "," "), " ,  ,  ,  ,  ,  "),
    PrEisTestFunc.doEval(StringUtils.concatAsCSSV("<=1=>"), "<=1=>"),
    PrEisTestFunc.doEval(StringUtils.concatAsCSSV("<=1=>","<=2=>","<=3=>"), "<=1=>, <=2=>, <=3=>"),
  });}

}