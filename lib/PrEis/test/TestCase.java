package PrEis.test;
import java.util.ArrayList;

/** Encompasses a reasonably generic test case realization. */
public class TestCase {
  /** Test Argument[s]. */
  private ArrayList<Object> inputArgs;
  /** Expected Output Value. */
  private Object expected;

  /**
   * @param iArg Input Argument Value (primitives: pass resp. Wrapper instance)
   * @param iExp Input Expected Value 
   */
  public TestCase(){
    inputArgs = new ArrayList<Object>();
  }

  public TestCase arg(Object arg){
    inputArgs.add(arg);
    return this;
  }

  public TestCase args(Object ... args){
    for(Object o : args){arg(o);}
    return this;
  }

  public TestCase expect(Object exp){
    expected = exp;
    return this;
  }

  /** Syntax Sugar for {@link #expect} */
  public TestCase exp(Object exp){
    return expect(exp);
  }


  /** @param argNum Note: Uses <code>1</code> i.e. <b>ONE</b> indexing! */
  public int    aInt   (int argNum){return (Integer)inputArgs.get(argNum-1);}
  /** @param argNum Note: Uses <code>1</code> i.e. <b>ONE</b> indexing! */
  public float  aFloat (int argNum){return (Float)inputArgs.get(argNum-1);}
  /** @param argNum Note: Uses <code>1</code> i.e. <b>ONE</b> indexing! */
  public char   aChar  (int argNum){return (Character)inputArgs.get(argNum-1);}
  /** @param argNum Note: Uses <code>1</code> i.e. <b>ONE</b> indexing! */
  public String aString(int argNum){return (String)inputArgs.get(argNum-1);}

  public int    eInt()    {return (Integer)expected;  }
  public float  eFloat()  {return (Float)expected;    }
  public char   eChar()   {return (Character)expected;}
  public String eString() {return (String)expected;   }

  public String argsToString(){String ret="Arguments: {"; int len=inputArgs.size(); for(int i=0; i<len; i++){ret+=inputArgs.get(i).toString(); if(i<len-1){ret+=", ";}} return ret+='}';}
  public String expToString(){return expected==null ? "No Expected Value Provided." : ("Expected: {"+expected.toString()+"}");}
  public void argsToConsole(){System.out.println(argsToString());}
  public void expToConsole(){System.out.println(expToString());}
  public void toConsole(){argsToConsole(); expToConsole();}
}