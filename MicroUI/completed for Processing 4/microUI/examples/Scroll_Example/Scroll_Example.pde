import microUI.Scroll;

Scroll scroll;

void setup() {
  size(400,400,P2D);
  scroll = new Scroll(this);
  scroll.setMinMax(0,255);
  scroll.setValue(scroll.getMax()/2);
}

void draw() {
  background(scroll.getValue());
  
  scroll.draw();
  
}

void mouseWheel(MouseEvent e) {
  scroll.scrolling.init(e);
}
