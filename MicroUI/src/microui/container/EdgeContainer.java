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
	private boolean left,up,right,down,center,centerHorizontal,centerVertical,dirtyState;
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
		if(bounds != null) {
			bounds.draw();
		}
		
		if(dirtyState) {
			updateState();
			dirtyState = false;
		}
	}
	
	public void updateState() {
		if(bounds == null) { return; }
		
		if(left) { bounds.setX(getX()); }
		if(up) { bounds.setY(getY()); }
		if(right) { bounds.setX(getX()+getW()-bounds.getW()); }
		if(down) { bounds.setY(getY()+getH()-bounds.getH()); }
		
		if(center) {
			bounds.setPosition(getX()+getW()/2-bounds.getW()/2,getY()+getH()/2-bounds.getH()/2);
		}
		
		if(centerHorizontal) {
			bounds.setX(getX()+getW()/2-bounds.getW()/2);
		}
		
		if(centerVertical) {
			bounds.setY(getY()+getH()/2-bounds.getH()/2);
		}
		
		bounds.setSize(min(getW(),defaultWidthOfElement), min(getH(),defaultHeightOfElement));

	}
	
	
	public EdgeContainer set(Bounds bounds) {
		this.bounds = bounds;
		defaultWidthOfElement = bounds.getW();
		defaultHeightOfElement = bounds.getH();
		dirtyState = true;
		return this;
	}
	
	public EdgeContainer set(String text) {
		this.bounds = new TextView(text);
		defaultWidthOfElement = bounds.getW();
		defaultHeightOfElement = bounds.getH();
		dirtyState = true;
		return this;
	}

	public boolean isLeft() {
		return left;
	}

	public EdgeContainer setLeft(boolean left) {
		if(this.left == left) { return this; }
		
		this.left = left;
		
		if(left) {
			right = center = centerHorizontal = false;
		}
		
		dirtyState = true;
		return this;
	}

	public boolean isUp() {
		return up;
	}

	public EdgeContainer setUp(boolean up) {
		if(this.up == up) { return this; }
		
		this.up = up;
		
		if(up) {
			down = center = centerVertical = false;
		}
		dirtyState = true;
		return this;
	}

	public boolean isRight() {
		return right;
	}

	public EdgeContainer setRight(boolean right) {
		if(this.right == right) { return this; }
		
		this.right = right;
		if(right) {
			left = center = centerHorizontal = false;
		}
		dirtyState = true;
		return this;
	}

	public boolean isDown() {
		return down;
	}

	public EdgeContainer setDown(boolean down) {
		if(this.down == down) { return this; }
		
		this.down = down;
		if(down) {
			up = center = centerVertical = false;
		}
		dirtyState = true;
		return this;
	}
	
	public boolean isCenter() {
		return center;
	}

	public EdgeContainer setCenter(boolean center) {
		if(this.center == center) { return this; }
		
		this.center = center;
		if(center) {
		  left = up = right = down = false;
		  centerHorizontal = centerVertical = true;
		}
		dirtyState = true;
		return this;
	}
	
	public Bounds getElement() { return bounds; }
	
	
	
	@Override
	public void inTransforms() {
		super.inTransforms();
		updateState();
	}

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