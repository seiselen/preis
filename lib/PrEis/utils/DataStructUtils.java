package PrEis.utils;
import java.util.Set;

import processing.core.PVector;
import processing.data.IntDict;
import processing.data.JSONObject;
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

  /** 
   * Syntax Sugar for {@link DataStructUtils#createVector(float x, float y)}.
   */
  public static PVector vec2(float x, float y){
    return createVector(x, y);
  }

  public static PVector createVector(){
    return new PVector();
  }

  public static PVector createVector(int x, int y){
    return new PVector(x,y);
  }

  public static PVector createVector(float x, float y){
    return new PVector(x,y);
  }

  public static PVector createVector(int x, int y, int z){
    return new PVector(x,y,z);
  }

  public static PVector createVector(float x, float y, float z){
    return new PVector(x,y,z);
  }

  public static PVector createVector(int ... vs){
    if(vs==null || vs.length<2){return conserrAndRetZVec();}
    return (vs.length==2) ? createVector(vs[0],vs[1]) : createVector(vs[0],vs[1],vs[2]);     
  }

  public static PVector createVector(float ... vs){
    if(vs==null || vs.length<2){return conserrAndRetZVec();}
    return (vs.length==2) ? createVector(vs[0],vs[1]) : createVector(vs[0],vs[1],vs[2]);
  }

  public static int[]   int2WithVec2  (PVector v){return new int[]{(int)v.x, (int)v.y};}
  public static int[]   int3WithVec3  (PVector v){return new int[]{(int)v.x, (int)v.y, (int)v.z};}
  public static float[] float2WithVec2(PVector v){return new float[]{v.x, v.y};}
  public static float[] float3WithVec3(PVector v){return new float[]{v.x, v.y, v.z};}


  @SuppressWarnings("unchecked")
  public static String[] keyArrayOfJSONObj(JSONObject jsonObj){
    return ((Set<String>)jsonObj.keys()).toArray(new String[jsonObj.size()]);
  }


  private static PVector conserrAndRetZVec(){
    Cons.err_act(Cons.Err.NULL_XOR_INVALID, Cons.Act.RETURN_ZERO_VEC);
    return createVector();
  }

}
