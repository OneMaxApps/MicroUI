import microUI.Slider;

Slider slider;

void setup() {
  size(400,400,P2D);
  slider = new Slider(this);
  slider.setMinMax(0,255);
  slider.setValue(slider.getMax()/2);
}

void draw() {
  background(slider.getValue());
  
  slider.draw();
  
}

void mouseWheel(MouseEvent e) {
  slider.scrolling.init(e);
}
