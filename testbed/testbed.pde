import PrEis.test.TestStringUtils;
import PrEis.test.TestDataStructUtils;
import PrEis.test.TestQueryUtils;
import PrEis.test.TestPGfxUtils;
import PrEis.test.TestEnums;
import PrEis.test.TestFormatUtils;
import PrEis.test.TestGridManager;
import PrEis.test.TestFileSysUtils;
import PrEis.test.TestFileWriteUtil;
import PrEis.test.TestGUIObjects;

int bg_col;
boolean TEST_DOES_RENDER = false;

TestGridManager tgrid;
TestGUIObjects  tGUI;

void setup(){
  if(!TEST_DOES_RENDER){
    testUtils();
    exit();
  }
  else{
    size(640,320);
    bg_col = color(255;)
    tgrid = new TestGridManager(this);
    tGUI  = new TestGUIObjects(this);
  }
}


void draw(){
  if(!TEST_DOES_RENDER){return;}
  background(bg_col);
  //TestPGfxUtils.testRender(this);
  tgrid.testRender();
  tGUI.testRender();
}

void keyPressed(){
  if(!TEST_DOES_RENDER){return;}
  tgrid.onKeyPressed();
  tGUI.onKeyPressed();
}

void mousePressed(){
  if(!TEST_DOES_RENDER){return;}
  tGUI.onMousePressed();
}

void mouseWheel(MouseEvent event) {
  if(!TEST_DOES_RENDER){return;}
  tGUI.onMouseWheel(event.getCount());
}