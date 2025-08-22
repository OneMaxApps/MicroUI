package microui.container.panel;

import static microui.event.EventType.CLICKED;

import microui.component.Button;
import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Container;
import microui.event.Event;
import processing.event.MouseEvent;

public class Panel extends Container {
	private final TitleBar titleBar;
	private Container container;
	private boolean open;
	
	public Panel(String title, float x, float y, float w, float h) {
		super(x, y, w, h);
		color.set(64);
		resizeHandle.setEnable(true);
		resizeHandle.colorDots.set(255,0);
		resizeHandle.getDot(0).invisible();
		resizeHandle.getDot(1).invisible();
		
		titleBar = new TitleBar();
		
		setTitle(title);
		
		open();
	}
	
	public Panel(String title) {
		this(title,app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}
	
	public Panel() {
		this("",app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}

	@Override
	public void update() {
		if(open) {
			super.update();
			titleBar.draw();
			if(container != null) {
				container.draw();
			}
		}
	}
	



	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		if(titleBar != null) { titleBar.onChangeBounds(); }
		if(container != null) { container.setBounds(this); }
	}
	

	public final Container getContainer() {
		return container;
	}
	

	public final void setContainer(Container container) {
		if(container == null) { return; }
		this.container = container;
		container.setBounds(this);
	}
	
	public final void setTitle(String title) {
		titleBar.title.set(title);
	}
	
	public final String getTitle() {
		return titleBar.title.get();
	}
	
	public void open() {
		open = true;
	}
	
	public void close() {
		open = false;
	}
	
	public final boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public void mouseWheel(MouseEvent e) {
		if(container != null) {
			container.mouseWheel(e);
		}
	}

	@Override
	public void keyPressed() {
		if(container != null) {
			container.keyPressed();
		}
	}

	
	private final class TitleBar extends Bounds {
		private final int HEIGHT = 20, BUTTON_WIDTH = 50, BUTTON_HEIGHT = HEIGHT;
		private final Button buttonClose;
		private final TextView title;
		private final Event event;
		
		public TitleBar() {
			super();
			visible();
			setSize(Panel.this.getWidth(),HEIGHT);
			
			buttonClose = new Button("");
			buttonClose.getColor().set(154,0,0,128);
			buttonClose.ripples.invisible();
			buttonClose.stroke.setWeight(1);
			buttonClose.setPosition(getX()+getWidth()-buttonClose.getWidth(),getY());
			buttonClose.getCallback().addListener(CLICKED, () -> close() );
			
			buttonClose.setPosition(Panel.this.getX()+Panel.this.getWidth()-BUTTON_WIDTH,Panel.this.getY()-getHeight());
			buttonClose.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
			
			title = new TextView("Title");
			title.setPosition(Panel.this.getX(),Panel.this.getY()-HEIGHT);
			title.setSize(Panel.this.getWidth()-BUTTON_WIDTH,HEIGHT);
			title.setTextSize(HEIGHT*.8f);
			
			event = new Event();
		}

		@Override
		public void update() {
			event.listen(this);
			
			app.pushStyle();
			app.fill(0,24);
			app.rect(x, y, w, h);
			app.popStyle();
			
			buttonClose.draw();
			
			title.draw();
			
			if(event.holding()) {
				Panel.this.setPosition(Panel.this.getX()+(app.mouseX-app.pmouseX),Panel.this.getY()+(app.mouseY-app.pmouseY));
			}
		}

		@Override
		public void onChangeBounds() {
			super.onChangeBounds();
			setPosition(Panel.this.getX(),Panel.this.getY()-getHeight());
			setSize(Panel.this.getWidth(),HEIGHT);
			
			if(buttonClose != null) { buttonClose.setPosition(getX()+getWidth()-buttonClose.getWidth(),getY()); }
		
			if(title != null) {
				title.setPosition(Panel.this.getX(),Panel.this.getY()-HEIGHT);
				title.setSize(Panel.this.getWidth()-BUTTON_WIDTH,HEIGHT);
			}
		}
		
	}
}