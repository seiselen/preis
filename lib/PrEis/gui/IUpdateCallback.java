package PrEis.gui;

/** 
 * Callback Used With Labels. Contains poller call of target string to render.
 */
public interface IUpdateCallback extends IGUICallBack {
  String getTxt();
}