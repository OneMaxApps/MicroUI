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
	private float defaultElementWidth,defaultElementHeight;
	
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
	}
	
	public EdgeContainer set(Bounds bounds) {
		this.bounds = bounds;
		defaultElementWidth = bounds.getWidth();
		defaultElementHeight = bounds.getHeight();
		updateState();
		return this;
	}
	
	public EdgeContainer set(String text) {
		this.bounds = new TextView(text);
		defaultElementWidth = bounds.getWidth();
		defaultElementHeight = bounds.getHeight();
		updateState();
		return this;
	}

	public boolean isLeft() {
		return left;
	}

	public EdgeContainer setLeft(boolean left) {
		if(this.left == left) { return this; }
		
		this.left = left;
		
		if(left) { right = center = centerHorizontal = false; }
		
		return this;
	}

	public boolean isUp() { return up; }

	public EdgeContainer setUp(boolean up) {
		if(this.up == up) { return this; }
		
		this.up = up;
		
		if(up) { down = center = centerVertical = false; }
		
		return this;
	}

	public boolean isRight() { return right; }

	public EdgeContainer setRight(boolean right) {
		if(this.right == right) { return this; }
		
		this.right = right;
		if(right) { left = center = centerHorizontal = false; }
		
		return this;
	}

	public boolean isDown() { return down; }

	public EdgeContainer setDown(boolean down) {
		if(this.down == down) { return this; }
		
		this.down = down;
		if(down) { up = center = centerVertical = false; }
		
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
	
	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		updateState();
	}
	
	private final void updateState() {
		if(bounds == null) { return; }
		
		bounds.setSize(min(getWidth(),defaultElementWidth), min(getHeight(),defaultElementHeight));
		
		if(left)   { bounds.setX(getX()); }
		if(up)	   { bounds.setY(getY()); }
		if(right)  { bounds.setX(getX()+getWidth()-bounds.getWidth());   }
		if(down)   { bounds.setY(getY()+getHeight()-bounds.getHeight()); }
		if(center) { bounds.setPosition(getX()+getWidth()/2-bounds.getWidth()/2,getY()+getHeight()/2-bounds.getHeight()/2); }
		if(centerHorizontal) { bounds.setX(getX()+getWidth()/2-bounds.getWidth()/2); }
		if(centerVertical)   { bounds.setY(getY()+getHeight()/2-bounds.getHeight()/2); }
		
	}
}