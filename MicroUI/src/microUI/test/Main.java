package microUI.test;

import microUI.EditText;
import microUI.util.Event;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class Main extends PApplet {

	EditText editText;

	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		editText = new EditText(this);
		editText.items.setTextSize(12);
		editText.loadText("C:\\Users\\002\\Desktop\\EditText.txt");
		
	}
		
	@Override
	public void draw() {
		background(128);
		editText.draw();
		
		if(mouseButton == RIGHT) { editText.setPosition(mouseX,mouseY); }
	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void keyPressed() {
		Event.keyPressed(this);
		
		editText.keyPressed();
		
	}
	
	

	@Override
	public void keyReleased() {
		Event.keyReleased();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		editText.mouseWheel(event);
	}
	
	
}