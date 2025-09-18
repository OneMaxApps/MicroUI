package microui;

import microui.component.Knob;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.core.style.Color;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import processing.core.PApplet;

// TODO check full work life cycle of components
// [1] Button;
// [1] CheckBox;
// [1] LabeledCheckBox;
// [1] Knob;
// [0] EditText; (need full refactor)
// [1] MenuButton;
// [1] Scroll;
// [1] Slider;
// [0] TextField;
// [1] TextView;

// NOTE: ///////////////////////////////////////////////////
// getX,Y,Width,Height = content area;
// getPadX,Y,Width,Height = padded area;
// getAbsoluteX,Y,Width,Height = margin area;
////////////////////////////////////////////////////////////

public final class Launcher extends PApplet {

	ContainerManager cm;
	Knob knobRed,knobGreen,knobBlue;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
//		size(640, 480);
		fullScreen();
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		MicroUI.setDebugModeEnabled(true);

		cm = ContainerManager.getInstance();
		
		Container container = new Container(new GridLayout(10,10));
		
		
		knobRed = new Knob();
		knobGreen = new Knob();
		knobBlue = new Knob();
		
		knobRed.setIndicatorColor(new Color(255,0,0,128));
		knobGreen.setIndicatorColor(new Color(0,255,0,128));
		knobBlue.setIndicatorColor(new Color(0,0,255,128));
		
		knobRed.setMaxValue(255);
		knobGreen.setMaxValue(255);
		knobBlue.setMaxValue(255);
		
		container.addComponent(knobRed, new GridLayoutParams(1,5));
		container.addComponent(knobGreen, new GridLayoutParams(2,5));
		container.addComponent(knobBlue, new GridLayoutParams(3,5));
		
		
//		container.addComponent(new Button(), new GridLayoutParams(0,0));
//		container.addComponent(new CheckBox(), new GridLayoutParams(1,0));
//		container.addComponent(new EditText(), new GridLayoutParams(2,0));
//		container.addComponent(new LabeledCheckBox(), new GridLayoutParams(3,0));
//		container.addComponent(new MenuButton().add("one","two","three","four","five"), new GridLayoutParams(4,0));
//		container.addComponent(new Scroll(), new GridLayoutParams(0,1));
//		container.addComponent(new Slider(), new GridLayoutParams(1,1));
//		container.addComponent(new TextField(), new GridLayoutParams(2,1));
//		container.addComponent(new TextView("TextView"), new GridLayoutParams(3,1));

		cm.add(container,0);
		container.setColor(new Color(0,0));
	}

	@Override
	public void draw() {
		background(knobRed.getValue(),knobGreen.getValue(),knobBlue.getValue());
		
		if(mouseButton == RIGHT) {
			cm.getContainerById(0).setSize(mouseX,mouseY);
		}
	}
	
}