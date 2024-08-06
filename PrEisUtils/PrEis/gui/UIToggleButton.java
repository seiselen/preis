package PrEis.gui;

/** 
 * Toggle Button
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 */
public class UIToggleButton {
  private ToggleCallback toggleCallback;
  public                 UIToggleButton(PVector iPos, PVector iDim){super(iPos, iDim, UIObjType.TB);}
  public  UIToggleButton setLabel(String iLbl){txt=iLbl; return this;}
  public  UIToggleButton setTitle(String ittl){title=ittl; return this;} 
  public  UIToggleButton setFont(AppFont iFnt){objFont=iFnt; onSetFont(); return this;}
  public  UIToggleButton bindCallback(ToggleCallback cbk){toggleCallback=cbk; return this;}
  public            void onMousePressed(){if(mouseOver){toggleCallback.toggleState(); if(title!=null){drawBotBarLabel(title);}}}
  public            void update(){super.update();}
  public            void render(){super.render(); textSize(style.txt_size); strokeWeight(style.swgt); fill(getFillCol()); stroke(getStrokeCol()); renderRect(); fill(getStrokeCol()); renderTextViaOri(); if(mouseOver&&title!=null){drawBotBarLabel(title);}}
  private            int getFillCol(){if(toggleCallback.getState()){return isDisabledState() ? style.fill_on_disabled : isClickedState() ? style.fill_on_clicked : isHoveredState() ? style.fill_on_hovered : style.fill_on;} else{return isDisabledState() ? style.fill_off_disabled : isClickedState() ? style.fill_off_clicked : isHoveredState() ? style.fill_off_hovered : style.fill_off;}}
  private            int getStrokeCol(){return isDisabledState() ? style.strk_disabled : style.strk_enabled;}
}