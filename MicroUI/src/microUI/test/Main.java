package microUI.test;

import microUI.EditText;
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
		editText = new EditText(this,0,0,width,height);
		editText.loadText("C:\\Users\\002\\Desktop\\EditText.txt");
	}
		
	@Override
	public void draw() {
		background(128);
		editText.draw();
		
		//System.out.println(frameRate);
		if(mouseButton == RIGHT) { editText.items.setTextSize(map(mouseX,0,width,4,64)); }
		
	}

	@Override
	public void mousePressed() {
		// if(mouseButton == RIGHT) { editText.items.setTextSize(mouseX); }
		
	}

	@Override
	public void keyPressed() {
		editText.keyPressed();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		editText.mouseWheel(event);
	}
	
	
}