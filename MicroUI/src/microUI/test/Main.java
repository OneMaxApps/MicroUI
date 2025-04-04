package microUI.test;

import microUI.EditText;
import microUI.layout.GridLayout;
import microUI.util.Event;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Main extends PApplet {
	GridLayout gridLayout;
	EditText[] editText;

	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		editText = new EditText[2];
		
		editText[0] = new EditText(this);
		editText[1] = new EditText(this);
		
		editText[0].createFont("C:\\Windows\\Fonts\\consola.ttf");
		editText[1].createFont("C:\\Windows\\Fonts\\consola.ttf");
		
		gridLayout = new GridLayout(this,2);
		gridLayout.add(editText[0], 0, 0);
		gridLayout.add(editText[1], 1, 1);
	}
		
	@Override
	public void draw() {
		background(128);
		gridLayout.draw();
		// if(mousePressed) { editText.setSize(mouseX,mouseY); }
		System.out.println("fps:"+(int) frameRate);
	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void keyPressed() {
		Event.keyPressed(this);
		
		editText[0].keyPressed();
		editText[1].keyPressed();
		
	}
	
	

	@Override
	public void keyReleased() {
		Event.keyReleased();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		editText[0].mouseWheel(event);
		editText[1].mouseWheel(event);
	}
	
	
}