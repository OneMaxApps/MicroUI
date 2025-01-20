import microUI.Button;

Button button;
int gray;

void setup() {
  size(400,400,P2D);
  button = new Button(this);
  gray = 255;
}

void draw() {
  background(gray);
  
  button.draw();
  
  if(button.event.clicked()) { gray = (int) random(255); }
}
