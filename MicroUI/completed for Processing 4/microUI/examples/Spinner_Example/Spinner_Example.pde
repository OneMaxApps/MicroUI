import microUI.Spinner;

Spinner spinner;

void setup() {
  size(400,400,P2D);
  spinner = new Spinner(this);
  spinner.add("Red","Green","Blue");
}

void draw() {
  switch(spinner.getSelect()) {
   case 0 : background(255,0,0); break;
   case 1 : background(0,255,0); break;
   case 2 : background(0,0,255); break;
  }
 
  spinner.draw();
  
}
