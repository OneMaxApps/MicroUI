package microui;

import microui.component.Button;
import microui.component.CheckBox;
import microui.component.EditText;
import microui.component.Knob;
import microui.component.LabeledCheckBox;
import microui.component.MenuButton;
import microui.component.Scroll;
import microui.component.Slider;
import microui.component.TextField;
import microui.component.TextView;
import microui.constants.ContainerMode;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.core.style.theme.ThemeGray;
import microui.core.style.theme.ThemeManager;
import microui.layout.ColumnLayout;
import microui.layout.ColumnLayoutParams;
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

	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
		//fullScreen();
		size(480,360);
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		ThemeManager.setTheme(new ThemeGray());
		//MicroUI.setDebugModeEnabled(true);

		cm = ContainerManager.getInstance();
		
		
		cm.add(getContainerMain(),"main");
		cm.add(getContainerAllComponents(),"all_components");
		//cm.add(getContainerEditText(),"edit_text");
		
	}

	@Override
	public void draw() {
		background(164);

		System.out.println(frameRate);
	}

	@Override
	public void keyPressed() {
		super.keyPressed();
		
		if(keyPressed) {
			if(keyCode == 0x70) {
				cm.switchOn("main");
			}
			
			if(keyCode == TAB) {
				cm.switchOnNextContainer();
			}
		}
		
		
	}
	
	
	private Container getContainerMain() {
		Container container = new Container(new GridLayout(3,3));
		container.setBackgroundImage(loadImage("C:\\Users\\002\\Desktop\\background.jpg"));
		
		Container ContainerMenuItem = new Container(new ColumnLayout());
		ContainerMenuItem.setContainerMode(ContainerMode.IGNORE_CONSTRAINTS);
		ContainerMenuItem.addComponent(new Button("show all components"), new ColumnLayoutParams(.2f), () -> cm.switchOn("all_components"));
		ContainerMenuItem.addComponent(new Button("show EditText"), new ColumnLayoutParams(.2f), () -> cm.switchOn("edit_text"));
		
		container.addComponent(ContainerMenuItem, new GridLayoutParams(1,1));
		
		return container;
	}
	
	private Container getContainerAllComponents() {
		Container container = new Container(new GridLayout(5,5));
		container.setBackgroundImage(loadImage("C:\\Users\\002\\Desktop\\background1.jpg"));
		
		container.addComponent(new Button(), new GridLayoutParams(0,0));
		container.addComponent(new CheckBox(), new GridLayoutParams(1,0));
		container.addComponent(new EditText(), new GridLayoutParams(2,0));
		container.addComponent(new LabeledCheckBox("confirm"), new GridLayoutParams(3,0));
		container.addComponent(new MenuButton().add("one","two","three","four","five"), new GridLayoutParams(4,0));
		container.addComponent(new Scroll(), new GridLayoutParams(0,1),"scroll");
		container.addComponent(new Slider(), new GridLayoutParams(1,1));
		container.addComponent(new TextField(), new GridLayoutParams(2,1));
		container.addComponent(new TextView("TextView"), new GridLayoutParams(3,1));
		container.addComponent(new Knob(), new GridLayoutParams(4,1));
		
		return container;
	}
	
//	private Container getContainerEditText() {
//		Container container = new Container(new GridLayout(5,5));
//		container.setContainerMode(ContainerMode.IGNORE_CONSTRAINTS);
//		
//		container.addComponent(new EditText(), new GridLayoutParams(1,1,3,3));
//		
//		return container;
//	}
}