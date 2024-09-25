package PrEis.test;
import PrEis.utils.FileSysUtils;
import PrEis.utils.StringUtils;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTMLEditorKit;

public class TestHTMLParsing {

  private Testbed tbed;

  public TestHTMLParsing(Testbed iTbed){
    tbed = iTbed;
  }

  public void test1(){
    String fileStr = "";
    String pathStr = FileSysUtils.pathConcat(tbed.getRootDir(),"tests","inputs","html_test_01.html");

    try {fileStr = Files.readString(Paths.get(pathStr));}
    catch (Exception e) {System.err.println("Error: Problem Reading HTML File!"); e.printStackTrace();}

    StringReader r = new StringReader(fileStr);
    EditorKit k = new HTMLEditorKit();
    Document d = k.createDefaultDocument();
    d.putProperty("IgnoreCharsetDirective", Boolean.TRUE);

    try {
      Iterator<?> itr;
      Object attr;
      String key;
      String val;

      k.read(r, d, 0);
      ElementIterator it = new ElementIterator(d);
      Element elem = null;

      while ((elem = it.next()) != null){
        if(elem.getName().contains("uiclick")){
          System.out.println("Element Type:  UIClick");
          AttributeSet as = elem.getAttributes();
          System.out.println("Element Attrs: ["+as.getAttributeCount()+"] As Follows: {");
          
          itr = as.getAttributeNames().asIterator();
          while(itr.hasNext()){
            attr = itr.next();
            key = attr.toString();
            val = (String)as.getAttribute(key);
            System.out.println("  "+StringUtils.wrapWith('{', "key:"+key+", val:"+val));
          }
          System.out.println("}");
          as.getAttributeNames().toString();
        }
      }
    }
    catch (Exception e){
      System.err.println("Error: Problem Parsing HTML File!"); e.printStackTrace();
    }
  }
}