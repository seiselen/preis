package PrEis.gui;

/** 
 * Label
 * @note PROTECTED to restrict instantiation via ONLY `UIManager` factory calls.
 */
public class UILabel {
  private UpdateCallback updateCallback;
  private      LabelType lblType;
  public                 UILabel(PVector iPos, PVector iDim){super(iPos, iDim, UIObjType.LB);}
  public         UILabel setLabel(String iLbl){txt=iLbl; return this;}
  public         UILabel setFont(AppFont iFnt){objFont=iFnt; onSetFont(); return this;}
  public         UILabel setType(LabelType iTyp){lblType=iTyp; return this;}
  public         UILabel bindCallback(UpdateCallback cbk){updateCallback=cbk; txt=updateCallback.getTxt(); return this;}
  public            void update(){if(updateCallback!=null){txt = updateCallback.getTxt();}}
  public            void render(){super.render(); textSize(style.txt_size); strokeWeight(style.swgt); fill(getFillCol()); stroke(getStrokeCol()); rect(pos.x,pos.y,dim.x,dim.y); fill(getTxtFillCol()); renderTextViaOri(); }
  private            int getFillCol(){switch(lblType){case OP: return style.fill_opaque; case TP: default: return style.fill_transp;}}
  private            int getStrokeCol(){switch(lblType){case OP: return style.strk_opaque; case TP: default: return style.strk_transp;}}
  private            int getTxtFillCol(){switch(lblType){case OP: return style.strk_opaque; case TP: default: return style.fill_txt;}}
}