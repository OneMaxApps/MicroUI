package microUI;

import microUI.component.Button;
import microUI.container.ContainerManager;
import microUI.container.layout.ColumnLayout;
import microUI.container.layout.GridLayout;
import microUI.container.layout.LinearLayout;
import microUI.container.window.Window;
import microUI.util.Text;
import processing.core.PApplet;

public class ContainersTest extends PApplet {

	private ContainerManager containerManager;
	private GridLayout layoutMenu,layoutGameplay;
	private Window window;
	
	public static void main(String[] args) {
		PApplet.main("microUI.ContainersTest");
	}
	
	@Override
	public void settings() {
		size(400,400);
		
	}
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		layoutMenu = new GridLayout(1) {{
			image.set("C:\\Users\\002\\Desktop\\bg.jpg");
			image.visible();
			final LinearLayout linearLayout = new LinearLayout();
			add(linearLayout,0,0);
			linearLayout.margin.set(20);
			linearLayout.setVerticalMode();
			final Text title = new Text("Resident Evil 1");
			linearLayout.add(title, .2f);
			title.setTextSize(title.getH()/2);
			title.shadow.visible();
			title.shadow.setSize(title.getTextHeight()/16);
			
			final GridLayout innerGridLayout = new GridLayout(3,1);
			linearLayout.add(innerGridLayout,.9f);
			
			final ColumnLayout menuItemsLayout = new ColumnLayout() {{
				add("",.6f);
				add(new Button("Start"),.1f);
				add(new Button("Settings"),.1f);
				add(new Button("Rules"),.1f);
				add(new Button("Quit"),.1f);
			}};
			
			innerGridLayout.add(menuItemsLayout, 1, 0);
			
			linearLayout.setVisibleTotal(false);
		}};
		
		layoutGameplay = new GridLayout(1) {{
			image.set("C:\\Users\\002\\Desktop\\bg.jpg");
			image.visible();
			final LinearLayout linearLayout = new LinearLayout();
			add(linearLayout,0,0);
			linearLayout.margin.set(20);
			linearLayout.setVerticalMode();
			final Text title = new Text("Gameplay");
			linearLayout.add(title, .2f);
			title.setTextSize(title.getH()/2);
			title.shadow.visible();
			title.shadow.setSize(title.getTextHeight()/16);
			
			final GridLayout innerGridLayout = new GridLayout(3,1);
			linearLayout.add(innerGridLayout,.9f);
			
			final ColumnLayout menuItemsLayout = new ColumnLayout() {{
				add("",.6f);
				add(new Button("Start"),.1f);
				add(new Button("Settings"),.1f);
				add(new Button("Rules"),.1f);
				add(new Button("Quit"),.1f);
			}};
			
			innerGridLayout.add(menuItemsLayout, 1, 0);
			
			linearLayout.setVisibleTotal(false);
		}};
		
		window = new Window();
		
		containerManager = new ContainerManager();
		containerManager.add(layoutMenu,layoutGameplay,window);
		
		containerManager.setFocusOn(layoutMenu);
		
	}
	
	@Override
	public void draw() {
		background(128);
		
		containerManager.draw();
		
	}
	
	@Override
	public void keyPressed() {
		switch((int) random(3)) {
		case 0 : containerManager.setFocusOn(layoutMenu); break;
		case 1 : containerManager.setFocusOn(layoutGameplay); break;
		case 2 : containerManager.setFocusOn(window); break;
		
		}
	}
	
}