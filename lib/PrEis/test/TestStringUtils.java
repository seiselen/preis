package PrEis.test;
import PrEis.utils.StringUtils;

public class TestStringUtils {

  private static TestCase TC(){return new TestCase();}

  public static void runTests(){
    //test_charTimesN();
    //test_padL();
    //test_padR();
    //test_pad_arrays();
    //test_wrapStringWith();
    test_lastCharOf();
    test_capFirstChar();
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
    tc.statsToConsole();
  }

  public static void test_pad_arrays(){
    String[] pad_ArrIn = new String[]{"","b","bat","batman","bin","suparman","batman bin","batman bin suparman"};
    System.out.println("Test...");
    TestFunc.strArrToConsole(StringUtils.padL(pad_ArrIn, 0));
    System.out.println("Test...");
    TestFunc.strArrToConsole(StringUtils.padR(pad_ArrIn, 0));
    System.out.println("Test...");
    TestFunc.strArrToConsole(StringUtils.padL(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2));
    System.out.println("Test...");
    TestFunc.strArrToConsole(StringUtils.padR(pad_ArrIn, StringUtils.maxCharLengthOf(pad_ArrIn)+2));
  }

  public static void test_wrapStringWith(){
    String xStr = "batman bin suparman";

    TestFunc.strArrToConsole(new String[]{
      StringUtils.wrapStringWith('"', ""),
      StringUtils.wrapStringWith('"', " "),
      StringUtils.wrapStringWith(' ', ""),           
      StringUtils.wrapStringWith('"', xStr),
      StringUtils.wrapStringWith('#', " "+xStr+" "),
      StringUtils.wrapStringWith('"', "", '"'),
      StringUtils.wrapStringWith('"', " ", '"'),
      StringUtils.wrapStringWith(' ', "", ' '),     
      StringUtils.wrapStringWith('<', xStr, '>'),
      StringUtils.wrapStringWith('{', " "+xStr+" ", '}'),      
    });
  }

  public static void test_lastCharOf(){
    TestFunc.strArrToConsole(new String[]{
      "Empty String ->"+(StringUtils.lastCharOf("")=='\0'?"PASS":"FAIL"),
      "Null Input --->"+(StringUtils.lastCharOf(null)=='\0'?"PASS":"FAIL"),
      ""+StringUtils.lastCharOf("b"),
      ""+StringUtils.lastCharOf("bat")
    });    
  } 

  public static void test_capFirstChar(){
    TestFunc.strArrToConsole(new String[]{
      "Empty String ->"+(StringUtils.capFirstChar("")==""?"PASS":"FAIL"),
      "Null Input --->"+(StringUtils.capFirstChar(null)==null?"PASS":"FAIL"),
      "Non {a-Z} T1 ->"+(StringUtils.capFirstChar(" ").equals(" ")?"PASS":"FAIL"),
      "Non {a-Z} T2 ->"+(StringUtils.capFirstChar("#yolo").equals("#yolo")?"PASS":"FAIL"),      
      StringUtils.capFirstChar("b"),
      StringUtils.capFirstChar("bat"),
      StringUtils.capFirstChar("B"),
      StringUtils.capFirstChar("BAT")
    });    
  } 

  public static void test_valToSSVCapdWords(){

  }

  public static void test_concatStrings(){

  }

  public static void test_concatAsCSV(){

  }

  public static void test_concatAsSCSV(){

  }

}


