package PrEis.utils;
import java.io.PrintWriter;

import processing.core.PApplet;

public class FileWriteUtil {
  PApplet p;
  PrintWriter w;

  public FileWriteUtil(PApplet parent){
    p = parent;
  }

  /** 
   * This <strong>must</strong> be called to commence output to a text file.
   * @param filename Input filename; s.t. if nullish: {@link #launchWrite()} is
   * called; and if suffix is not <code>.txt</code>: such will be appended.
   */
  public FileWriteUtil launchWrite(String filename){
    if(QueryUtils.nullish(filename)){return launchWrite();}
    if(!filename.endsWith(ExtType.TXT.val())){filename+=ExtType.TXT.val();}
    w = p.createWriter(filename);
    return this;
  }

  /** This <strong>must</strong> be called to commence output to a text file. */
  public FileWriteUtil launchWrite(){
    w = p.createWriter(getDefaultFilename());
    return this;
  }

  /**
   * @implNote FOR TESTING PURPOSES ONLY! It mocks {@link #launchWrite()} s.t I
   * can specify a subroot dir of `/data` to create the file. Ergo this is NOT
   * intended to be called for any other reason sans testing as aforementioned.
   */
  public FileWriteUtil launchWriteTestAnnon(String pfix){
    w = p.createWriter(pfix+getDefaultFilename());
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

  public static void writeOneCall(PApplet p, String fname, String ... strs){
    new FileWriteUtil(p).launchWrite(fname).writeToFile(strs).finishWrite();
  }
}
