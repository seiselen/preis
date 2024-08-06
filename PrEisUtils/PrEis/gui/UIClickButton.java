package PrEis.gui;

/** 
 * Click Button
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 */
class UIClickButton {
  private ActionCallback action;
  public                 UIClickButton(PVector iPos, PVector iDim){super(iPos, iDim, UIObjType.CB);}
  public   UIClickButton setLabel(String iLbl){txt=iLbl; return this;}
  public   UIClickButton setTitle(String ittl){title=ittl; return this;}  
  public   UIClickButton setFont(AppFont iFnt){objFont=iFnt; onSetFont(); return this;}  
  public   UIClickButton bindCallback(ActionCallback act){action = act; return this;}
  public            void onMousePressed(){if(mouseOver&&action!=null){action.action();}}
  public            void render(){ super.render(); textSize(style.txt_size); strokeWeight(style.swgt); fill(getFillCol()); stroke(getStrokeCol()); renderRect(); fill(getStrokeCol()); renderTextViaOri(); if(mouseOver&&title!=null){drawBotBarLabel(title);}}
  private            int getFillCol(){return isDisabledState() ? style.fill_disabled : isClickedState() ? style.fill_clicked : isHoveredState() ? style.fill_hovered : style.fill;} 
  private            int getStrokeCol(){return isDisabledState() ? style.strk_disabled : style.strk_enabled;}
}