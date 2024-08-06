PrEisTester tester;

void setup() {
  size(640,640);
  PrEisTester.testStringUtils();

  tester = new PrEisTester(this);
}

void draw() {
  background(255);
  
  tester.testGFXUtils();
  
}
