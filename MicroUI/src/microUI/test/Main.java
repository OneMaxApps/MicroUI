package microUI.test;

import microUI.TextInput;
import processing.core.PApplet;

public class Main extends PApplet {
	
	
	TextInput textInput;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		textInput = new TextInput(this,"Введите текст:");
	}
		
	@Override
	public void draw() {
		background(128);
		textInput.draw();
		System.out.println(frameRate);
	}

	@Override
	public void mousePressed() {
		
	}

	@Override
	public void keyPressed() {
		textInput.keyPressed();
	}
	
}