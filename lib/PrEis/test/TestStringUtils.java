package PrEis.test;
import PrEis.utils.StringUtils;

public class TestStringUtils {
  private static final String P = "PASS";
  private static final String F = "FAIL";

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
    System.out.println("<=[ Testing 'wrapStringWith' ]=================>");
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
    TestFunc.doEval(StringUtils.charTimesN('0', 0), "");
    TestFunc.doEval(StringUtils.charTimesN('A', 1), "A");
    TestFunc.doEval(StringUtils.charTimesN('B', 2), "BB");
    TestFunc.doEval(StringUtils.charTimesN('C', 3), "CCC");    
    TestFunc.doEval(StringUtils.charTimesN('D', 4), "DDDD");
    TestFunc.doEval(StringUtils.charTimesN('E', 5), "EEEEE");
    TestFunc.doEval(StringUtils.charTimesN('F', 6), "FFFFFF");
    TestFunc.doEval(StringUtils.charTimesN('G', 7), "GGGGGGG");
    TestFunc.doEval(StringUtils.charTimesN('8', 8), "88888888");
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
    for (TestCase t : ts){TestFunc.doEval(StringUtils.padL(""+t.aInt(1),t.aInt(2)),t.eString());}
  }

  public static void test_padR(){
    TestCase[] tests = new TestCase[]{
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

    TestCounter tc = new TestCounter(tests.length,"test_padR_int");
    for (TestCase t : tests){
      try {tc.tally(TestFunc.doEval(StringUtils.padR(""+t.aInt(1),t.aInt(2)),t.eString()));} 
      catch (Exception e) {tc.tally(false); System.err.println("An Exception Occured");} //> QAD sysout for now
    }
    //tc.statsToConsole();
  }

  public static void test_padL_arrays(){
    String[] pad_ArrIn = new String[]{"","b","bat","batman","bin","suparman","batman bin","batman bin suparman"};
    String[] padL_ArrExp = new String[]{"                   ", "                  b", "                bat", "             batman", "                bin", "           suparman", "         batman bin", "batman bin suparman"};
    TestFunc.doEval(StringUtils.padL(pad_ArrIn, 0), pad_ArrIn);
    for(int i=0; i<padL_ArrExp.length; i++){padL_ArrExp[i] = "  "+padL_ArrExp[i];}
    TestFunc.doEval(StringUtils.padL(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2), padL_ArrExp);
  }

  public static void test_padR_arrays(){
    String[] pad_ArrIn = new String[]{"","b","bat","batman","bin","suparman","batman bin","batman bin suparman"};
    String[] padR_ArrExp = new String[]{"                   ", "b                  ", "bat                ", "batman             ", "bin                ", "suparman           ", "batman bin         ", "batman bin suparman"};
    TestFunc.doEval(StringUtils.padR(pad_ArrIn, 0), pad_ArrIn);
    for(int i=0; i<padR_ArrExp.length; i++){padR_ArrExp[i] = padR_ArrExp[i]+"  ";}
    TestFunc.doEval(StringUtils.padR(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2), padR_ArrExp);
  }

  public static void test_wrapStringWith(){
    String xStr = "batman bin suparman";
    TestFunc.doEval(StringUtils.wrapStringWith('"', ""), "\"\"");
    TestFunc.doEval(StringUtils.wrapStringWith('"', " "), "\" \"");
    TestFunc.doEval(StringUtils.wrapStringWith('"', " "), " ");
    TestFunc.doEval(StringUtils.wrapStringWith('"', xStr), "\""+xStr+"\"");
    TestFunc.doEval(StringUtils.wrapStringWith('#', " "+xStr+" "), "# "+xStr+" #");
    TestFunc.doEval(StringUtils.wrapStringWith('"', "", '"'), "\"\"");
    TestFunc.doEval(StringUtils.wrapStringWith('"', " ", '"'), "\" \"");
    TestFunc.doEval(StringUtils.wrapStringWith(' ', "", ' '), "  ");
    TestFunc.doEval(StringUtils.wrapStringWith('<', xStr, '>'), "<"+xStr+">");
    TestFunc.doEval(StringUtils.wrapStringWith('{', " "+xStr+" ", '}'), "{ "+xStr+" }");
  }

  public static void test_lastCharOf(){
    TestFunc.strArrToConsole(new String[]{
      (StringUtils.lastCharOf("")=='\0'?P:F),   //> Empty String
      (StringUtils.lastCharOf(null)=='\0'?P:F), //> Null Input
      (StringUtils.lastCharOf("b")=='b'?P:F),
      (StringUtils.lastCharOf("bat")=='t'?P:F),
    });    
  } 

  public static void test_capFirstChar(){
    TestFunc.strArrToConsole(new String[]{
      (StringUtils.capFirstChar("")==""?P:F),                  //> Empty String
      (StringUtils.capFirstChar(null)==null?P:F),              //> Null Input
      (StringUtils.capFirstChar(" ").equals(" ")?P:F),         //> non {aA:zZ} test #1
      (StringUtils.capFirstChar("#yolo").equals("#yolo")?P:F), //> non {aA:zZ} test #2
      (StringUtils.capFirstChar("b").equals("B")?P:F),
      (StringUtils.capFirstChar("bat").equals("Bat")?P:F),
      (StringUtils.capFirstChar("B").equals("B")?P:F),
      (StringUtils.capFirstChar("BAT").equals("BAT")?P:F),
    });    
  } 

  public static void test_valToSSVCapdWords(){
    TestFunc.e(StringUtils.valToSSVCapdWords("batman_bin_suparman"), "Batman Bin Suparman");
    TestFunc.e(StringUtils.valToSSVCapdWords("mixom_moxim"), "Mixom Moxim");
    TestFunc.e(StringUtils.valToSSVCapdWords("i_o"), "I O");
    TestFunc.e(StringUtils.valToSSVCapdWords("m_a_g_a"), "M A G A");
    TestFunc.e(StringUtils.valToSSVCapdWords("_yolo"), "Yolo");
    TestFunc.e(StringUtils.valToSSVCapdWords("yolo_"), "Yolo");
    TestFunc.strArrToConsole(new String[]{       
      (StringUtils.valToSSVCapdWords("_").equals("")?"PASS":"FAIL"),
      (StringUtils.valToSSVCapdWords("")==null?"PASS":"FAIL"),
      (StringUtils.valToSSVCapdWords(null)==null?"PASS":"FAIL"),
    });
  }

  public static void test_concatStrings(){
    TestFunc.e(StringUtils.concatStrings(""), "");
    TestFunc.e(StringUtils.concatStrings(" "," "," "," "," "," "), "      ");    
    TestFunc.e(StringUtils.concatStrings("<=1=>"), "<=1=>");
    TestFunc.e(StringUtils.concatStrings("<=1=>","<=2=>","<=3=>"), "<=1=><=2=><=3=>");
  }

  public static void test_concatAsCSV(){
    TestFunc.e(StringUtils.concatAsCSV(""), "");
    TestFunc.e(StringUtils.concatAsCSV(" "," "," "," "," "," "), " , , , , , ");    
    TestFunc.e(StringUtils.concatAsCSV("<=1=>"), "<=1=>");
    TestFunc.e(StringUtils.concatAsCSV("<=1=>","<=2=>","<=3=>"), "<=1=>,<=2=>,<=3=>");
  }

  public static void test_concatAsSCSV(){
    TestFunc.e(StringUtils.concatAsSCSV(""), "");
    TestFunc.e(StringUtils.concatAsSCSV(" "," "," "," "," "," "), " ,  ,  ,  ,  ,  ");    
    TestFunc.e(StringUtils.concatAsSCSV("<=1=>"), "<=1=>");
    TestFunc.e(StringUtils.concatAsSCSV("<=1=>","<=2=>","<=3=>"), "<=1=>, <=2=>, <=3=>");
  }

}