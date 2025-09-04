package microui.container.panel;

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
		setUserResizeEnabled(true);
		resizeHandle.colorDots.set(255,0);
		resizeHandle.getDot(0).setVisible(false);
		resizeHandle.getDot(1).setVisible(false);
		
		titleBar = new TitleBar();
		
		setTitle(title);
		
		open();
	}
	
	public Panel(String title) {
		this(title,ctx.width*.4f,ctx.height*.4f,ctx.width*.2f,ctx.height*.2f);
	}
	
	public Panel() {
		this("",ctx.width*.4f,ctx.height*.4f,ctx.width*.2f,ctx.height*.2f);
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
	protected void onChangeBounds() {
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
			setVisible(true);
			
			setSize(Panel.this.getWidth(),HEIGHT);
			
			buttonClose = new Button("");
			buttonClose.getColor().set(154,0,0,128);
			buttonClose.setRipplesEnabled(false);
			buttonClose.setStrokeWeight(1);
			buttonClose.setPosition(getX()+getWidth()-buttonClose.getWidth(),getY());
			buttonClose.onClick(() -> close() );
			
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
			
			ctx.pushStyle();
			ctx.fill(0,24);
			ctx.rect(getX(), getY(), getWidth(), getHeight());
			ctx.popStyle();
			
			buttonClose.draw();
			
			title.draw();
			
			if(event.holding()) {
				Panel.this.setPosition(Panel.this.getX()+(ctx.mouseX-ctx.pmouseX),Panel.this.getY()+(ctx.mouseY-ctx.pmouseY));
			}
		}

		@Override
		protected void onChangeBounds() {
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