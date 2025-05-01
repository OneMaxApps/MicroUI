package microUI;

import microUI.component.Button;
import microUI.component.CheckBox;
import microUI.component.CircleSeekBar;
import microUI.component.EditText;
import microUI.component.Scroll;
import microUI.component.Slider;
import microUI.component.Spinner;
import microUI.component.TextInput;
import microUI.container.layout.GridLayout;
import microUI.container.window.Window;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class Main extends PApplet {
	private GridLayout gridLayout;
	private Button button;
	private CheckBox checkBox;
	private CircleSeekBar circleSeekBar;
	private EditText editText;
	private Scroll scroll;
	private Slider slider;
	private Spinner spinner;
	private TextInput textInput;
	private Window window;
	
	public static void main(String[] args) {
		PApplet.main("microUI.Main");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		gridLayout		 = new GridLayout(3);
		
		button 	   		 = new Button();
		checkBox   		 = new CheckBox();
		circleSeekBar    = new CircleSeekBar();
		editText         = new EditText();
		
		scroll           = new Scroll();
		slider           = new Slider();
		spinner          = new Spinner();
		textInput        = new TextInput();
		
		window           = new Window();
		
		gridLayout.setFillTheGrid(false);
		gridLayout.add(button, 0,0).add(checkBox, 1,0).add(circleSeekBar, 2,0).add(editText, 0,1)
				  .add(scroll, 1,1).add(slider, 2,1).add(spinner, 0,2).add(textInput, 1, 2)
				  .add(window, 2, 2);
		
	}
		
	@Override
	public void draw() {
		background(128);
		gridLayout.draw();
		
		// if(mouseButton == RIGHT) { button.setPosition(mouseX,mouseY); }
		if(mouseButton == RIGHT) { button.setSize(mouseX,mouseY); }
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		gridLayout.mouseWheel(event);
	}
	
	@Override
	public void keyPressed() {
		gridLayout.keyPressed();
	}
}
