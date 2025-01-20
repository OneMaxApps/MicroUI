import microUI.EditText;

EditText editText;

void setup() {
  size(400,400,P2D);
  editText = new EditText(this);
  editText.setHint("Enter the digits:");
  editText.setEnterType(EditText.DIGITS);
}

void draw() {
  background(constrain(Integer.parseInt(0+editText.getText()),0,255));
  
  editText.draw();
  
}

void keyPressed() {
  editText.keyPressed();
}
