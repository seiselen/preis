import PrEis.test.TestStringUtils;
import PrEis.test.TestDataStructUtils;
import PrEis.test.TestQueryUtils;
import PrEis.test.TestPGfxUtils;
import PrEis.test.TestEnums;
import PrEis.test.TestFormatUtils;
import PrEis.test.TestGridManager;
import PrEis.test.TestFileSysUtils;
import PrEis.test.TestFileWriteUtil;

boolean TEST_DOES_RENDER = false;

TestGridManager tGridManager;

void setup(){
  if(!TEST_DOES_RENDER){
    testUtils();
    exit();
  }
  else{
    size(640,320);
    tGridManager = new TestGridManager(this);
  }
}


void draw(){
  background(255);
  
  if(TEST_DOES_RENDER){
    //TestPGfxUtils.testRender(this);
    tGridManager.testRender();
  }
}

void keyPressed(){
  if(TEST_DOES_RENDER){
    tGridManager.onKeyPressed();
  }  

}
