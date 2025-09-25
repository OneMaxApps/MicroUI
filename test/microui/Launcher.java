package microui;

import microui.component.Button;
import microui.component.CheckBox;
import microui.component.Knob;
import microui.component.LabeledCheckBox;
import microui.component.MenuButton;
import microui.component.Scroll;
import microui.component.Slider;
import microui.component.TextField;
import microui.component.TextView;
import microui.constants.ContainerMode;
import microui.core.base.Component;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.layout.ColumnLayout;
import microui.layout.ColumnLayoutParams;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import processing.core.PApplet;


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
//		fullScreen();
		size(720,480);
	}
 
	@Override
	public void setup() {
		MicroUI.setContext(this);
		//MicroUI.setDebugModeEnabled(true);

		cm = ContainerManager.getInstance();

		//cm.add(getContainerMain(),"main");
		//cm.add(getContainerAllComponents(),"all_components");
		cm.add(getContainerWith(new Button().onLeaveLong(() -> background(random(255)))),"Button");
//		cm.add(getContainerWith(new CheckBox()),"CheckBox");
//		//cm.add(getContainerWith(new EditText()),"EditText");
//		cm.add(getContainerWith(new Knob()),"Knob");
//		cm.add(getContainerWith(new LabeledCheckBox()),"LabeledCheckBox");
//		cm.add(getContainerWith(new MenuButton()),"MenuButton");
//		cm.add(getContainerWith(new Scroll()),"Scroll");
//		cm.add(getContainerWith(new Slider()),"Slider");
//		cm.add(getContainerWith(new TextField()),"TextField");
//		cm.add(getContainerWith(new TextView()),"TextView");
		
	}

	@Override
	public void draw() {
		background(32);
		// cm.getContainerByTextId("container_main").getComponentByTextId("edit_text").setSize(mouseX,mouseY);
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
		
		Container ContainerMenuItem = new Container(new ColumnLayout());
		ContainerMenuItem.setContainerMode(ContainerMode.IGNORE_CONSTRAINTS);
		
		ContainerMenuItem.addComponent(new Button("show all components"), new ColumnLayoutParams(.2f), () -> cm.switchOn("all_components"));
		
		MenuButton menuButtonOfComponents;
		ContainerMenuItem.addComponent(menuButtonOfComponents = new MenuButton("show component","Button","CheckBox","EditText","Knob","LabeledCheckBox","MenuButton","Scroll","Slider","TextField","TextView"), new ColumnLayoutParams(.2f));
		
		menuButtonOfComponents.getItem("Button").onClickOld(() -> cm.switchOn("Button"));
		menuButtonOfComponents.getItem("CheckBox").onClickOld(() -> cm.switchOn("CheckBox"));
		menuButtonOfComponents.getItem("EditText").onClickOld(() -> cm.switchOn("EditText"));
		menuButtonOfComponents.getItem("Knob").onClickOld(() -> cm.switchOn("Knob"));
		menuButtonOfComponents.getItem("LabeledCheckBox").onClickOld(() -> cm.switchOn("LabeledCheckBox"));
		menuButtonOfComponents.getItem("MenuButton").onClickOld(() -> cm.switchOn("MenuButton"));
		menuButtonOfComponents.getItem("Scroll").onClickOld(() -> cm.switchOn("Scroll"));
		menuButtonOfComponents.getItem("Slider").onClickOld(() -> cm.switchOn("Slider"));
		menuButtonOfComponents.getItem("TextField").onClickOld(() -> cm.switchOn("TextField"));
		menuButtonOfComponents.getItem("TextView").onClickOld(() -> cm.switchOn("TextView"));
		
		container.addComponent(ContainerMenuItem, new GridLayoutParams(0,0));
		
		return container;
	}
	
	private Container getContainerAllComponents() {
		Container container = new Container(new GridLayout(5,5));

		container.addComponent(new Button(), new GridLayoutParams(0,0));
		container.addComponent(new CheckBox(), new GridLayoutParams(1,0));
		//container.addComponent(new EditText(), new GridLayoutParams(2,0));
		container.addComponent(new LabeledCheckBox("confirm"), new GridLayoutParams(3,0));
		container.addComponent(new MenuButton().add("one","two","three","four","five"), new GridLayoutParams(4,0));
		container.addComponent(new Scroll(), new GridLayoutParams(0,1),"scroll");
		container.addComponent(new Slider(), new GridLayoutParams(1,1));
		container.addComponent(new TextField(), new GridLayoutParams(2,1));
		container.addComponent(new TextView("TextView"), new GridLayoutParams(3,1));
		container.addComponent(new Knob(), new GridLayoutParams(4,1));
		
		return container;
	}

	private Container getContainerWith(Component component) {
		Container container = new Container(new GridLayout(11,11));
		container.setContainerMode(ContainerMode.IGNORE_CONSTRAINTS);
		
		container.addComponent(component, new GridLayoutParams(5,5),"edit_text");

		return container;
	}
}