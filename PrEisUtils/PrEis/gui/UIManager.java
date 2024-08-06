package PrEis.gui;

import PrEis.gui.Enums.AppFont;
import PrEis.gui.Interfaces.ActionCallback;
import processing.core.PApplet;
import processing.core.PVector;

public class UIManager {

  PApplet p;

  public UIManager(PApplet parent){
    p = parent;
  }

  /*
  public UIClickButton createClickButton(
    String txt,
    AppFont fnt,
    ActionCallback cbk,
    PVector pos,
    PVector dim)
  {return new UIClickButton(pos,dim).setLabel(txt).bindCallback(cbk).setFont(fnt);}

public UIClickButton createClickButton(String txt, ActionCallback cbk, PVector pos, PVector dim){return new UIClickButton(pos,dim).setLabel(txt).bindCallback(cbk);}


public UIToggleButton createToggleButton(String txt, AppFont fnt, ToggleCallback cbk, PVector pos, PVector dim){return new UIToggleButton(pos, dim).setLabel(txt).bindCallback(cbk).setFont(fnt);}
public UIToggleButton createToggleButton(String txt, ToggleCallback cbk, PVector pos, PVector dim){return new UIToggleButton(pos, dim).setLabel(txt).bindCallback(cbk);}
public UIToggleButton createToggleButton(ToggleCallback cbk, AppFont fnt, PVector pos, PVector dim){return new UIToggleButton(pos, dim).bindCallback(cbk).setFont(fnt);}
public UIToggleButton createToggleButton(ToggleCallback cbk, PVector pos, PVector dim){return new UIToggleButton(pos, dim).bindCallback(cbk);}


public UILabel createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim, LabelType typ, AppFont fnt){return new UILabel(pos,dim).setLabel(txt).bindCallback(cbk).setType(typ).setFont(fnt);}
public UILabel createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim, LabelType typ)             {return new UILabel(pos,dim).setLabel(txt).bindCallback(cbk).setType(typ);}
public UILabel createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim, AppFont fnt)               {return new UILabel(pos,dim).setLabel(txt).bindCallback(cbk).setFont(fnt);}
public UILabel createLabel (String txt, UpdateCallback cbk, PVector pos, PVector dim)                            {return new UILabel(pos,dim).setLabel(txt).bindCallback(cbk);}
public UILabel createLabel (UpdateCallback cbk, PVector pos, PVector dim, LabelType typ, AppFont fnt)            {return new UILabel(pos,dim).bindCallback(cbk).setType(typ).setFont(fnt);}
public UILabel createLabel (UpdateCallback cbk, PVector pos, PVector dim, LabelType typ)                         {return new UILabel(pos,dim).bindCallback(cbk).setType(typ);}
public UILabel createLabel (UpdateCallback cbk, PVector pos, PVector dim, AppFont fnt)                           {return new UILabel(pos,dim).bindCallback(cbk).setFont(fnt);}
public UILabel createLabel (UpdateCallback cbk, PVector pos, PVector dim)                                        {return new UILabel(pos,dim).bindCallback(cbk);}
public UILabel createLabel (String txt, PVector pos, PVector dim, LabelType typ, AppFont fnt)                    {return new UILabel(pos,dim).setLabel(txt).setType(typ).setFont(fnt);}
public UILabel createLabel (String txt, PVector pos, PVector dim, LabelType typ)                                 {return new UILabel(pos,dim).setLabel(txt).setType(typ);}
public UILabel createLabel (String txt, PVector pos, PVector dim, AppFont fnt)                                   {return new UILabel(pos,dim).setLabel(txt).setFont(fnt);}
public UILabel createLabel (String txt, PVector pos, PVector dim)                                                {return new UILabel(pos,dim).setLabel(txt);}


  */
}
