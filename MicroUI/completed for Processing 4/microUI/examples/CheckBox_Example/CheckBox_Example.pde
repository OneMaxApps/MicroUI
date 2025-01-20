import microUI.CheckBox;

CheckBox checkbox;
int gray;

void setup() {
  size(400,400,P2D);
  checkbox = new CheckBox(this);
  gray = 255;
}

void draw() {
  background(gray);
  
  checkbox.draw();
  
  if(checkbox.isIncluded()) { gray = (int) random(255); }
}
