package microui.container;

import static processing.core.PApplet.min;

import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Container;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import processing.event.MouseEvent;

public class EdgeContainer extends Container {
	
	private Bounds bounds;
	private boolean left,up,right,down,center,centerHorizontal,centerVertical;
	private float defaultWidthOfElement,defaultHeightOfElement;
	

	public EdgeContainer(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		setCenter(true);
	}
	
	public EdgeContainer() {
		this(0,0,app.width,app.height);
	}
	
	@Override
	public void draw() {
		super.draw();
		update();
	}
	
	@Override
	public void update() {
		if(visible) {
			super.update();
		}
		
		if(bounds != null) {
			updateSize(bounds);
			updatePosition(bounds);
			bounds.draw();
		}
	}
	
	public void updatePosition(Bounds form) {
		
		if(left) { form.setX(getX()); }
		if(up) { form.setY(getY()); }
		if(right) { form.setX(getX()+getW()-form.getW()); }
		if(down) { form.setY(getY()+getH()-form.getH()); }
		
		if(center) {
			form.setPosition(getX()+getW()/2-form.getW()/2,getY()+getH()/2-form.getH()/2);
		}
		
		if(centerHorizontal) {
			form.setX(getX()+getW()/2-form.getW()/2);
		}
		
		if(centerVertical) {
			form.setY(getY()+getH()/2-form.getH()/2);
		}
		
	}
	
	public void updateSize(Bounds f) {
		f.setSize(min(getW(),defaultWidthOfElement), min(getH(),defaultHeightOfElement));
	}
	
	
	
	public EdgeContainer set(Bounds form) {
		this.bounds = form;
		defaultWidthOfElement = form.getW();
		defaultHeightOfElement = form.getH();
		return this;
	}
	
	public EdgeContainer set(String text) {
		this.bounds = new TextView(text);
		defaultWidthOfElement = bounds.getW();
		defaultHeightOfElement = bounds.getH();
		return this;
	}

	public boolean isLeft() {
		return left;
	}

	public EdgeContainer setLeft(boolean left) {
		this.left = left;
		if(left) {
			right = false;
			center = false;
			centerHorizontal = false;
		}
		return this;
	}

	public boolean isUp() {
		return up;
	}

	public EdgeContainer setUp(boolean up) {
		this.up = up;
		
		if(up) {
			down = false;
			center = false;
			centerVertical = false;
		}
		return this;
	}

	public boolean isRight() {
		return right;
	}

	public EdgeContainer setRight(boolean right) {
		this.right = right;
		if(right) {
		left = false;
		center = false;
		centerHorizontal = false;
		}
		return this;
	}

	public boolean isDown() {
		return down;
	}

	public EdgeContainer setDown(boolean down) {
		this.down = down;
		if(down) {
		up = false;
		center = false;
		centerVertical = false;
		}
		return this;
	}
	
	public boolean isCenter() {
		return center;
	}

	public EdgeContainer setCenter(boolean center) {
		this.center = center;
		if(center) {
		  left = false;
		  up = false;
		  right = false;
		  down = false;
		  centerHorizontal = true;
		  centerVertical = true;
		}
		return this;
	}
	
	public Bounds getElement() { return bounds; }
	
	@Override
	public void mouseWheel(MouseEvent e) {
		if(bounds instanceof Scrollable b) {
			b.mouseWheel(e);
		}
	}

	@Override
	public void keyPressed() {
		if(bounds instanceof KeyPressable b) {
			b.keyPressed();
		}
	}
}