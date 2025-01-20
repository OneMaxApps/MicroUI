import microUI.Window;

Window window;

void setup() {
  size(400,400,P2D);
  window = new Window(this,"Window");
  
}

void draw() {
  background(234);
  window.draw();
}
