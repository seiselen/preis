import PrEis.test.TestAppUtil;
TestAppUtil testUtil = new TestAppUtil(this);

//=[PROCESSING PRIMARY FUNCTIONS]===============================================
void settings(){testUtil.settings();}
void setup(){testUtil.setup();}
void draw(){testUtil.updateΘ().render();}
void keyPressed(){testUtil.onKeyPressed();}
void mousePressed(){testUtil.onMousePressed();}
void mouseWheel(MouseEvent e){testUtil.onMouseWheel(e);}