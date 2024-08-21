import PrEis.test.TestStringUtils;
import PrEis.test.TestDataStructUtils;
import PrEis.test.TestQueryUtils;
import PrEis.test.TestPgfxUtils;
import PrEis.test.TestEnums;
import PrEis.test.TestFormatUtils;
import PrEis.test.TestGridManager;
import PrEis.test.TestFileSysUtils;
import PrEis.test.TestFileWriteUtil;
import PrEis.test.TestGUIObjects;

//=[CONST DEFS]=================================================================
String fname_bg_gui = "data/assets/PrEisGUI_diagnosticGrid.png";

//=[OBJECT DECLARATIONS]========================================================
TestGridManager tgrid;
TestGUIObjects tGUI;

//=[TEST MODE ENUM DEF AND CURRENT VAL]=========================================
enum TestMode {UTILS, GRIDMGR, PGFXUTL, GUIOBJS};
TestMode mode = TestMode.GUIOBJS;

//=[PROCESSING PRIMARY FUNCTIONS]===============================================
void settings(){switch(mode){
  case GRIDMGR: size(640,320); return;
  case GUIOBJS: size(1280,720); return;
  default: return;
}}

void setup(){switch(mode){
  case UTILS: testUtils(); exit(); return;
  case GRIDMGR: tgrid = new TestGridManager(this); return;
  case GUIOBJS: tGUI = new TestGUIObjects(this).bindBGImg(loadImage(fname_bg_gui)); return;
  default: return;
}}

void draw(){
  //=[PER-FRAME IO CALLS]=======================================
    
  //=[UPDATE CALLS]=============================================
  switch(mode){
    case GUIOBJS: tGUI.update(); break;
    default: break;
  }
  //=[RENDER CALLS]=============================================
  switch(mode){
    case GRIDMGR: tgrid.testRender(); break;
    case GUIOBJS: tGUI.testRender(); break;
    case PGFXUTL: TestPgfxUtils.testRender(this); break;
    default: break;
  }
}

void keyPressed(){switch(mode){
  case GRIDMGR: tgrid.onKeyPressed(); return;
  case GUIOBJS: tGUI.onKeyPressed(); return;
  case PGFXUTL: TestPgfxUtils.testRender(this); return;
  default: return;
}}

void mousePressed(){switch(mode){
  case GUIOBJS: tGUI.onMousePressed(); return;
  default: return;
}}

void mouseWheel(MouseEvent event){switch(mode){
  case GUIOBJS: tGUI.onMouseWheel(event.getCount()); return;
  default: return;
}}
