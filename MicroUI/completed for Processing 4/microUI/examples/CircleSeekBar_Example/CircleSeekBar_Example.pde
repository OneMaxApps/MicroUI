import microUI.CircleSeekBar;

CircleSeekBar circleSeekBar;

void setup() {
  size(400,400,P2D);
  circleSeekBar = new CircleSeekBar(this);
  circleSeekBar.value.setMax(255);
}

void draw() {
  background(circleSeekBar.value.getValue());
  
  circleSeekBar.draw();

}

void mouseWheel(MouseEvent e) {
  circleSeekBar.scrolling.init(e);
}
