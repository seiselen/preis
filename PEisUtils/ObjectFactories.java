import java.util.ArrayList;
import processing.core.PVector;

public class ObjectFactories {

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


  String[] strArrListToArr(ArrayList<String> aList){
    return aList.toArray(new String[0]);
  }



}
