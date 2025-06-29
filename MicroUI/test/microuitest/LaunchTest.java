package microuitest;

import microui.MicroUI;
import microui.component.Button;
import microui.component.MenuButton;
import microui.container.layout.ColumnLayout;
import microui.container.layout.LinearLayout;
import microui.container.layout.RowLayout;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	ColumnLayout columnLayout;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		columnLayout = new ColumnLayout();
		RowLayout rowLayout = new RowLayout().add(new MenuButton().add("NEW","OPEN","SAVE","SAVE AS"),.1f);
		LinearLayout linearLayout = new LinearLayout().add(new Button(), DEFAULT_HEIGHT);
		columnLayout.add(rowLayout,.1f);
		columnLayout.add(linearLayout, .9f);
		columnLayout.setPriority(rowLayout, 1);
	}
	
	@Override
	public void draw() {
		background(128);
		columnLayout.draw();
		GlobalTooltip.draw();
	}
	
}