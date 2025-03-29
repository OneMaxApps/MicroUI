package microUI;

import static processing.core.PApplet.dist;
import static processing.core.PApplet.max;

import microUI.layout.GridLayout;
import microUI.layout.Layout;
import microUI.layout.RowLayout;
import microUI.util.BaseForm;
import microUI.util.Event;
import microUI.util.Rectangle;
import microUI.util.Text;
import processing.core.PApplet;

public class Window extends Rectangle {
	public Bar bar;
	private boolean canResize,resizable;
	private Layout layout;
	
	public Window(PApplet app, String title, float x, float y, float w, float h) {
		super(app,x,y,w,h);
		setBasicFX(false);
		fill.set(32);
		resizable = true;
		bar = new Bar(app, title);
		ripples.setVisible(false);
	}
	
	public Window(PApplet app, String title) {
		this(app,title,app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}
	
	public Window(PApplet app) {
		this(app,"Window",app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}

	@Override
	public void update() {
		super.update();
		bar.draw();
		if(layout != null) { layout.draw(); }
		
		if(resizable) {
			if(event.pressed()) {
			 if(dist(x+w, y+h, app.mouseX, app.mouseY) < ((w+h)/40)) {
				app.cursor(5);
				canResize = true;
			 }
			}
			if(!app.mousePressed) {
				if(canResize) {
					app.cursor(0);
					canResize = false;
				}
			}
			
			if(canResize) {
				setSize(max(100,getW()+app.mouseX-app.pmouseX),max(100,getH()+app.mouseY-app.pmouseY));
			}
		}
			
	}
	
	public final void setLayout(Layout layout) {
		this.layout = layout;
		if(bar.isVisible) {
			layout.setPosition(x, y+bar.HEIGHT);
			layout.setSize(w, h-bar.HEIGHT);
		} else {
			layout.setPosition(x, y);
			layout.setSize(w, h);
		}
	}
	
	public final Layout getLayout() { return layout; }
	
	public final void setForm(BaseForm form) {
		layout = new GridLayout(app,x,y+bar.HEIGHT,w,h-bar.HEIGHT,1,1);
		((GridLayout) (layout)).setFillTheGrid(true);
		((GridLayout) (layout)).add(form, 0, 0);
	}
	
	public final void open() { visible = true; }
	public final void close() { visible = false; }
	public final boolean isOpen() { return visible; }
	
	public final void fullScreen() {
		setTransforms(0,0,app.width,app.height);
	}
	
	public final void smallScreen() {
		setTransforms(app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}
	
	public boolean isFullScreen() {
		return (int) w == app.width && (int) h == app.height;
	}
	
	public final boolean isResizable() {
		return resizable;
	}

	public final void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public final class Bar {
		public final int HEIGHT = 25;
		public final Event event;
		public RowLayout layout;
		private final Button buttonClose;
		private final Text title;
		private boolean isVisible;
		
		public Bar(PApplet app, String title) {
			layout = new RowLayout(app,x,y,w,HEIGHT);
			layout.add("", .1f);
			layout.add(this.title = new Text(app,title),.8f);
			layout.add(buttonClose = new Button(app,"Close"),.1f);
			this.title.setInCenter(false);
			this.title.setTextSize(HEIGHT/2);
			buttonClose.shadowDestroy();
			isVisible = true;
			layout.setElementsResizable(false);
			event = new Event(app);
		}
		
		public final void draw() {
			if(isVisible) {
				event.listen(layout);
				layout.draw();
				
				if(app.mousePressed)
				if(event.moved()) { setPosition(getX()+app.mouseX-app.pmouseX, getY()+app.mouseY-app.pmouseY); }
				
				if(event.clicked(2)) {
					if(!isFullScreen()) {
						fullScreen();
					} else {
						smallScreen();
					}
				}
				
				if(buttonClose.event.clicked()) { close(); }
			}
		}

		public final boolean isVisible() {
			return isVisible;
		}

		public final void setVisible(boolean isVisible) {
			this.isVisible = isVisible;
			if(isVisible) {
				Window.this.layout.setPosition(x, y+bar.HEIGHT);
				Window.this.layout.setSize(w, h-bar.HEIGHT);
			} else {
				Window.this.layout.setPosition(x, y);
				Window.this.layout.setSize(w, h);
			}
		}
		
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		if(bar != null && bar.layout != null) {
			bar.layout.setX(x);
		}
		if(layout != null) {
			layout.setX(x);
		}
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		if(bar != null && bar.layout != null) {
		bar.layout.setY(y);
		
		 if(layout != null) {
			if(bar.isVisible) {
				layout.setY(y+bar.HEIGHT);
			} else {
				layout.setY(y);
			}
		 }
			
		}
		
	}

	@Override
	public void setW(float w) {
		super.setW(w);
		if(bar != null && bar.layout != null) {
		bar.layout.setW(w);
		 if(layout != null) {
			layout.setW(w);
		 }
		}
	}

	@Override
	public void setH(float h) {
		super.setH(h);
		if(bar != null && bar.layout != null) {
			 if(layout != null) {
				if(bar.isVisible) {
					layout.setH(h-bar.HEIGHT);
				} else {
					layout.setH(h);
				}
			 }
		}
	}
	
	public final void keyPressed() {
		for(int i = 0; i < layout.getElements().size(); i++) {
			if(layout.getElements().get(i) instanceof TextInput) {
				((TextInput) (layout.getElements().get(i))).keyPressed();
			}
		}
	}
}