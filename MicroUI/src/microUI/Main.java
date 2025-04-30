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
import microUI.util.Physics;
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
		gridLayout		 = new GridLayout(this,3);
		
		button 	   		 = new Button(this);
		checkBox   		 = new CheckBox(this);
		circleSeekBar    = new CircleSeekBar(this);
		editText         = new EditText(this);
		
		scroll           = new Scroll(this);
		slider           = new Slider(this);
		spinner          = new Spinner(this);
		textInput        = new TextInput(this);
		
		window           = new Window(this);
		
		
		gridLayout.add(button, 0,0).add(checkBox, 1,0).add(circleSeekBar, 2,0).add(editText, 0,1)
				  .add(scroll, 1,1).add(slider, 2,1).add(spinner, 0,2).add(textInput, 1, 2)
				  .add(window, 2, 2);
		
		
		
		//Physics.constrain(width/2, height/2, button, checkBox);
		
		// System.out.println(Physics.collision(checkBox, button));
	}
		
	@Override
	public void draw() {
		background(128);
		gridLayout.draw();

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
