package PrEis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Image;
import javax.imageio.ImageIO;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONObject;


public class JAResourceUtil {

  public static PApplet app;

  public static InputStream getInStreamOfJARAsset(String assetLibPath){
    InputStream is = null;
    try {is = Thread.currentThread().getContextClassLoader().getResourceAsStream(assetLibPath);}   
    catch (Exception e) {e.printStackTrace();}
    return is;
  }

  public static PFont getFontFromJAR(String assetLibPath){
    PFont pfnt = null;
    try {InputStream is = getInStreamOfJARAsset(assetLibPath); pfnt = new PFont(is); is.close();}
    catch (IOException e) {e.printStackTrace();}
    return pfnt;
  }

  @SuppressWarnings("deprecation")
  public static PImage getImageFromJAR(String assetLibPath){
    PImage pimg = null;
    try {InputStream is = getInStreamOfJARAsset(assetLibPath); pimg = new PImage((Image)ImageIO.read(is)); is.close();}
    catch (IOException e) {e.printStackTrace();}
    return pimg;
  }

  public static JSONObject getJSONObjectFromJAR(String assetLibPath){
    try {
      InputStream is = getInStreamOfJARAsset(assetLibPath);
      BufferedReader buffRead = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      StringBuilder respBldr = new StringBuilder();
      String l = "";
      while ((l=buffRead.readLine())!=null){respBldr.append(l);}
      is.close();
      return app.parseJSONObject(respBldr.toString());
    }
    catch (IOException e) {e.printStackTrace(); return null;}
  }

}