import PrEis.test.TestStringUtils;
import PrEis.test.TestDataStructUtils;
import PrEis.test.TestQueryUtils;
import PrEis.test.TestPGfxUtils;
import PrEis.test.TestEnums;
import PrEis.test.TestFormatUtils;

/*>>> 'BEFORE-I-FORGET' NOTE >>>
  The one thing that will need exclusive `pde-side' support for (i.e. of which 
  can't {necessarily} be realized per the `PrEis.test` module) is specification
  of example dirpaths and filepaths; which I'm opting to do with a `JSON`. This
  will be required for at least `FileSysUtils` and maybe `FileWriteUtil`.

  Later on: I will need to further support such-else-similar for `PImageUtils`,
  `Sprite`, `AnimClip`, and the fonts for `UIObject`s; depending on what I port
  thereto from the two apps, when I port them, when I decide to test them, etc.

  I should code the `jar-side` support s.t. finding no such `examples.JSON` file
  xor entires therein which have no values results in the app trying the `/data`
  folder for example file[s] (i.e. in caseuser shoved examples directly therein)
  before giving up and bitching it can't find req'd filepaths from which to work
  with for testing. And duh this all needs to be implemented/realized of course.
*/

void setup(){
  size(640,320);
  //TestStringUtils.runTests();
  //TestDataStructUtils.runTests();
  //TestQueryUtils.runTests();
  //TestEnums.runTests();
  TestFormatUtils.runTests(this);
  
  
  
  exit();
}


void draw(){
  background(255);
  TestPGfxUtils.testRender(this);
}
