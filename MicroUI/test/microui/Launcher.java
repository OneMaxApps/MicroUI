package microui;

import microui.component.Button;
import microui.component.CheckBox;
import microui.component.EditText;
import microui.component.LabeledCheckBox;
import microui.component.MenuButton;
import microui.component.Scroll;
import microui.component.Slider;
import microui.component.TextField;
import microui.component.TextView;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import processing.core.PApplet;

// TODO check full work life cycle of components
// [1] Button;
// [1] CheckBox;
// [0] Dial; (need full refactor)
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
	Button button;
	
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
		MicroUI.setDebugModeEnabled(true);
		MicroUI.setContext(this);

		cm = ContainerManager.getInstance();
		
		Container container = new Container(new GridLayout(5,10));
		//container.setContainerMode(ContainerMode.RESPECT_CONSTRAINTS);
		
		container.addComponent(new Button(), new GridLayoutParams(0,0));
		container.addComponent(new CheckBox(), new GridLayoutParams(1,0));
		container.addComponent(new EditText(), new GridLayoutParams(2,0));
		container.addComponent(new LabeledCheckBox(), new GridLayoutParams(3,0));
		container.addComponent(new MenuButton(), new GridLayoutParams(4,0));
		container.addComponent(new Scroll(), new GridLayoutParams(0,1));
		container.addComponent(new Slider(), new GridLayoutParams(1,1));
		container.addComponent(new TextField(), new GridLayoutParams(2,1));
		container.addComponent(new TextView("TextView"), new GridLayoutParams(3,1));
		
		cm.add(container);
		
	}

	@Override
	public void draw() {
	}
	
}