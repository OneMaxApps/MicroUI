import microUI.ListView;

ListView listView;
void setup() {
  size(400,400,P2D);
  
  listView = new ListView(this,0,0,width,height);
}

void draw() {
  background(128);
  
  listView.draw();
}

void mousePressed() {
  if(mouseButton == RIGHT) {
    listView.items.add(String.valueOf(listView.items.getItemsCount()),20);
  }
}

void mouseWheel(MouseEvent e) {
  listView.mouseWheel(e);
}
