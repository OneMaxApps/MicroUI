package microUI.test.layout;

import microUI.Button;
import microUI.CheckBox;
import microUI.CircleSeekBar;
import microUI.EditText;
import microUI.Scroll;
import microUI.Slider;
import microUI.Spinner;
import microUI.TextInput;
import microUI.Window;
import microUI.layout.GridLayout;
import processing.core.PApplet;

public final class GridLayoutTest extends PApplet {
	private  GridLayout gridLayout;
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
		PApplet.main("microUI.test.layout.GridLayoutTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		gridLayout		 = new GridLayout(this);
		
		button 	   		 = new Button(this);
		checkBox   		 = new CheckBox(this);
		circleSeekBar    = new CircleSeekBar(this);
		editText         = new EditText(this);
		
		scroll           = new Scroll(this);
		slider           = new Slider(this);
		spinner          = new Spinner(this);
		textInput        = new TextInput(this);
		
		window           = new Window(this);
		
		/*
		gridLayout.add(button, 0,0).add(checkBox, 1,0).add(circleSeekBar, 2,0).add(editText, 0,1)
				  .add(scroll, 1,1).add(slider, 2,1).add(spinner, 0,2).add(textInput, 1, 2)
				  .add(window, 2, 2);
		*/
	}
		
	@Override
	public void draw() {
		background(128);
		gridLayout.draw();
	}
	
}
