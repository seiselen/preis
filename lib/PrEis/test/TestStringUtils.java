package PrEis.test;
import PrEis.utils.StringUtils;

public class TestStringUtils {

  private static String STREX1 = "batman bin suparman";



  private static TestCase TC(){return new TestCase();}

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
    TestFunc.testResultsToConsole(new boolean[]{
      TestFunc.doEval(StringUtils.charTimesN('0', 0), ""),
      TestFunc.doEval(StringUtils.charTimesN('A', 1), "A"),
      TestFunc.doEval(StringUtils.charTimesN('B', 2), "BB"),
      TestFunc.doEval(StringUtils.charTimesN('C', 3), "CCC"),   
      TestFunc.doEval(StringUtils.charTimesN('D', 4), "DDDD"),
      TestFunc.doEval(StringUtils.charTimesN('E', 5), "EEEEE"),
      TestFunc.doEval(StringUtils.charTimesN('F', 6), "FFFFFF"),
      TestFunc.doEval(StringUtils.charTimesN('G', 7), "GGGGGGG"),
      TestFunc.doEval(StringUtils.charTimesN('8', 8), "88888888")
    });
  }

  public static void test_padL(){
    TestCase[] ts = new TestCase[]{
      TC().args(  0, -17).exp(   "0"),
      TC().args(  0,   0).exp(   "0"),
      TC().args(  0,   1).exp(   "0"),
      TC().args(  0,   2).exp(  " 0"),
      TC().args( 17, -31).exp(  "17"),
      TC().args( 17,   0).exp(  "17"),
      TC().args( 17,   1).exp(  "17"),
      TC().args( 17,   2).exp(  "17"),
      TC().args( 17,   3).exp( " 17"),
      TC().args( 17,   4).exp("  17"),
      TC().args(-17, -31).exp( "-17"),
      TC().args(-17,   0).exp( "-17"),
      TC().args(-17,   1).exp( "-17"),
      TC().args(-17,   2).exp( "-17"),
      TC().args(-17,   3).exp( "-17"),
      TC().args(-17,   4).exp(" -17"),
    };
    boolean[] results = new boolean[ts.length];
    for(int i=0; i<ts.length; i++){results[i]=TestFunc.doEval(StringUtils.padL(""+ts[i].aInt(1),ts[i].aInt(2)),ts[i].eString());}
    TestFunc.testResultsToConsole(results);
  }

  public static void test_padR(){
    TestCase[] ts = new TestCase[]{
      TC().args(  0, -17).exp(   "0"),  
      TC().args(  0,   0).exp(   "0"),
      TC().args(  0,   1).exp(   "0"),  
      TC().args(  0,   2).exp(  "0 "),
      TC().args( 17, -31).exp(  "17"), 
      TC().args( 17,   0).exp(  "17"),
      TC().args( 17,   1).exp(  "17"), 
      TC().args( 17,   2).exp(  "17"),
      TC().args(17,    3).exp( "17 "),
      TC().args( 17,   4).exp("17  "),
      TC().args(-17, -31).exp( "-17"),
      TC().args(-17,   0).exp( "-17"),
      TC().args(-17,   1).exp( "-17"),
      TC().args(-17,   2).exp( "-17"),
      TC().args(-17,   3).exp( "-17"),
      TC().args(-17,   4).exp("-17 "),
    };
    boolean[] results = new boolean[ts.length];
    for(int i=0; i<ts.length; i++){results[i]=TestFunc.doEval(StringUtils.padR(""+ts[i].aInt(1),ts[i].aInt(2)),ts[i].eString());}
    TestFunc.testResultsToConsole(results);
  }

  public static void test_padL_arrays(){
    String[] pad_ArrIn = new String[]{"","b","bat","batman","bin","suparman","batman bin","batman bin suparman"};
    String[] padL_ArrExp = new String[]{"                   ", "                  b", "                bat", "             batman", "                bin", "           suparman", "         batman bin", "batman bin suparman"};
    boolean t1 = TestFunc.doEval(StringUtils.padL(pad_ArrIn, 0), pad_ArrIn);
    for(int i=0; i<padL_ArrExp.length; i++){padL_ArrExp[i] = "  "+padL_ArrExp[i];}
    boolean t2 = TestFunc.doEval(StringUtils.padL(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2), padL_ArrExp);
    TestFunc.testResultsToConsole(new boolean[]{t1,t2});
  }

  public static void test_padR_arrays(){
    String[] pad_ArrIn = new String[]{"","b","bat","batman","bin","suparman","batman bin","batman bin suparman"};
    String[] padR_ArrExp = new String[]{"                   ", "b                  ", "bat                ", "batman             ", "bin                ", "suparman           ", "batman bin         ", "batman bin suparman"};
    boolean t1 = TestFunc.doEval(StringUtils.padR(pad_ArrIn, 0), pad_ArrIn);
    for(int i=0; i<padR_ArrExp.length; i++){padR_ArrExp[i] = padR_ArrExp[i]+"  ";}
    boolean t2 = TestFunc.doEval(StringUtils.padR(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2), padR_ArrExp);
    TestFunc.testResultsToConsole(new boolean[]{t1,t2});
  }

  public static void test_wrapStringWith(){TestFunc.testResultsToConsole(new boolean[]{
    TestFunc.doEval(StringUtils.wrapWith('"', ""), "\"\""),
    TestFunc.doEval(StringUtils.wrapWith('"', ""), "\"\""),
    TestFunc.doEval(StringUtils.wrapWith('"', " "), "\" \""),
    TestFunc.doEval(StringUtils.wrapWith('"', STREX1), "\""+STREX1+"\""),
    TestFunc.doEval(StringUtils.wrapWith('#', " "+STREX1+" "), "# "+STREX1+" #"),
    TestFunc.doEval(StringUtils.wrapWith('"', "", '"'), "\"\""),
    TestFunc.doEval(StringUtils.wrapWith('"', " ", '"'), "\" \""),
    TestFunc.doEval(StringUtils.wrapWith(' ', "", ' '), "  "),
    TestFunc.doEval(StringUtils.wrapWith('<', STREX1, '>'), "<"+STREX1+">"),
    TestFunc.doEval(StringUtils.wrapWith('<', STREX1), "<"+STREX1+">"),
    TestFunc.doEval(StringUtils.wrapWith('{', " "+STREX1+" ", '}'), "{ "+STREX1+" }"),
    TestFunc.doEval(StringUtils.wrapWith('{', STREX1), "{"+STREX1+"}"),
    TestFunc.doEval(StringUtils.wrapParens(STREX1), "("+STREX1+")"),
    TestFunc.doEval(StringUtils.wrapParens(" "), "( )")
  });}

  public static void test_lastCharOf(){TestFunc.testResultsToConsole(new boolean[]{
    TestFunc.doEval(StringUtils.lastCharOf(""),'\0'),   //> Empty String
    TestFunc.doEval(StringUtils.lastCharOf(null),'\0'), //> Null Input
    TestFunc.doEval(StringUtils.lastCharOf("b"),'b'),
    TestFunc.doEval(StringUtils.lastCharOf("bat"),'t')
  });}

  public static void test_capFirstChar(){TestFunc.testResultsToConsole(new boolean[]{
    TestFunc.doEval(StringUtils.capFirstChar(""), ""),      //> Empty String
    TestFunc.doEvalExpNull(StringUtils.capFirstChar(null)),      //> Null Input
    TestFunc.doEval(StringUtils.capFirstChar(" "), " "),     //> non {aA:zZ} test #1
    TestFunc.doEval(StringUtils.capFirstChar("#yolo"), "#yolo"), //> non {aA:zZ} test #2
    TestFunc.doEval(StringUtils.capFirstChar("b"), "B"),
    TestFunc.doEval(StringUtils.capFirstChar("bat"), "Bat"),
    TestFunc.doEval(StringUtils.capFirstChar("B"), "B"),
    TestFunc.doEval(StringUtils.capFirstChar("BAT"), "BAT")
  });}

  public static void test_valToSSVCapdWords(){TestFunc.testResultsToConsole(new boolean[]{
    TestFunc.doEval(StringUtils.valToSSVCapdWords("batman_bin_suparman"), "Batman Bin Suparman"),
    TestFunc.doEval(StringUtils.valToSSVCapdWords("mixom_moxim"), "Mixom Moxim"),
    TestFunc.doEval(StringUtils.valToSSVCapdWords("i_o"), "I O"),
    TestFunc.doEval(StringUtils.valToSSVCapdWords("m_a_g_a"), "M A G A"),
    TestFunc.doEval(StringUtils.valToSSVCapdWords("_yolo"), "Yolo"),
    TestFunc.doEval(StringUtils.valToSSVCapdWords("yolo_"), "Yolo"),
    (StringUtils.valToSSVCapdWords("_").equals("")),
    (StringUtils.valToSSVCapdWords("")==null),
    (StringUtils.valToSSVCapdWords(null)==null),
  });}

  public static void test_concatStrings(){TestFunc.testResultsToConsole(new boolean[]{
    TestFunc.doEval(StringUtils.concatStrings(""), ""),
    TestFunc.doEval(StringUtils.concatStrings(" "," "," "," "," "," "), "      "),
    TestFunc.doEval(StringUtils.concatStrings("<=1=>"), "<=1=>"),
    TestFunc.doEval(StringUtils.concatStrings("<=1=>","<=2=>","<=3=>"), "<=1=><=2=><=3=>"),
  });}

  public static void test_concatAsCSV(){TestFunc.testResultsToConsole(new boolean[]{
    TestFunc.doEval(StringUtils.concatAsCSV(""), ""),
    TestFunc.doEval(StringUtils.concatAsCSV(" "," "," "," "," "," "), " , , , , , "),
    TestFunc.doEval(StringUtils.concatAsCSV("<=1=>"), "<=1=>"),
    TestFunc.doEval(StringUtils.concatAsCSV("<=1=>","<=2=>","<=3=>"), "<=1=>,<=2=>,<=3=>"),
  });}

  public static void test_concatAsSCSV(){TestFunc.testResultsToConsole(new boolean[]{
    TestFunc.doEval(StringUtils.concatAsCSSV(""), ""),
    TestFunc.doEval(StringUtils.concatAsCSSV(" "," "," "," "," "," "), " ,  ,  ,  ,  ,  "),
    TestFunc.doEval(StringUtils.concatAsCSSV("<=1=>"), "<=1=>"),
    TestFunc.doEval(StringUtils.concatAsCSSV("<=1=>","<=2=>","<=3=>"), "<=1=>, <=2=>, <=3=>"),
  });}

}