  /*> Poor Man's Text Shadow. */
  public void textWithShadow(String txt, int colBase, int colShad, int baseX, int baseY){
    p.fill(colShad);
    p.text(txt,baseX-1,baseY);
    p.text(txt,baseX+1,baseY);
    p.text(txt,baseX,baseY-1);
    p.text(txt,baseX,baseY+1);
    p.fill(colBase);
    p.text(txt,baseX,baseY);
  }