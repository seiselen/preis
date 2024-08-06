package PrEisUtils;
import processing.core.PVector;
import processing.data.IntDict;
import processing.data.StringList;

/** Right Now: This contains factories and utils for Processing Data Structs. */
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

  
  public static PVector createVector(){
    return new PVector();
  }

  public static PVector createVector(float x, float y){
    return new PVector(x,y);
  }

  public static PVector createVector(float x, float y, float z){
    return new PVector(x,y,z);
  }

  public static PVector vec2WithArray(float[] a){
    if(a==null || a.length<2){
      Cons.err_act(Cons.Err.NULL_XOR_INVALID, Cons.Act.RETURN_ZERO_VEC);
      return createVector();
    }
    return (a.length==2) ? createVector(a[0],a[1]) : createVector(a[0],a[1],a[2]);
  }

}
