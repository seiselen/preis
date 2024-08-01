import processing.data.IntDict;
import processing.data.StringList;

public class DataStructUtils {
  
  public static String[] filterIntDictByFreq(IntDict intDict, char operator, int q_operand){
    String[] keys = intDict.keyArray();
    StringList filteredList = new StringList();
    for(String k : keys){if(intDictFilter(operator, q_operand, intDict.get(k))){filteredList.append(k);} }
    return filteredList.toArray();
  }

  // need to be creative with less-than and greater-than due to switch term constraints in Java
  public static boolean intDictFilter(char operator, int q_operand, int v_operand){
    switch(operator){
      case '<' : return v_operand <  q_operand;
      case 'l' : return v_operand <= q_operand;
      case '>' : return v_operand >  q_operand;
      case 'g' : return v_operand >= q_operand;    
      case '=' : return v_operand == q_operand;
      case '!' : return v_operand != q_operand;
      default  : System.err.println("Input Error: Invalid operator ["+operator+"]; returning [false]!"); return false; 
    }
  }

}
