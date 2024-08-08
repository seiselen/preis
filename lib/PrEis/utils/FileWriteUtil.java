package PrEis.utils;
import java.io.PrintWriter;

import processing.core.PApplet;

public class FileWriteUtil {
  PApplet p;
  PrintWriter w;

  public FileWriteUtil(PApplet parent){
    p = parent;
  }

  /** This <strong>must</strong> be called to commence output to a text file. */
  public FileWriteUtil launchWrite(String filename){
    if(QueryUtils.nullOrEmpty(filename)){
      Cons.err(Cons.Err.NULL_XOR_INVALID);
    }
    if(!filename.endsWith(ExtType.TXT.val())){
      Cons.err_act(Cons.Err.MISSING_SUFFIX, Cons.Act.ADDING_REQ_SUFX, ExtType.TXT.val());
      filename+=ExtType.TXT.val();
    }
    w = p.createWriter(filename);
    return this;
  }

  /** This <strong>must</strong> be called to commence output to a text file. */
  public FileWriteUtil launchWrite(){
    w = p.createWriter(getDefaultFilename());
    return this;
  }

  public FileWriteUtil writeToFile(String str){
    w.println(str);
    return this;
  }


  public FileWriteUtil writeToFile(String ... strs){
    for(String str : strs){writeToFile(str);}
    return this;
  }

  private String getDefaultFilename(){
    return "output_file_"+System.currentTimeMillis()+".txt";
  }

  /** This <strong>must</strong> be called to complete output to a text file. */
  public FileWriteUtil finishWrite(){
    if(w!=null){
      w.flush();
      w.close();
      w=null;
    }
    return this;
  }
  
}
