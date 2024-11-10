package microUI.layouts;

import microUI.utils.BaseForm;
import processing.core.PApplet;
import static processing.core.PApplet.min;

public class EdgeLayout extends Layout {
	private BaseForm form;
	private boolean left,up,right,down,center,centerHorizontal,centerVertical;
	private float defaultWidthOfElement,defaultHeightOfElement;
	

	public EdgeLayout(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		setCenter(true);
		setElementsResizable(true);
	}
	
	public EdgeLayout(PApplet app) {
		this(app, 0,0,app.width,app.height);
	}
	
	public void draw() {
		super.draw();
		
		if(form != null) {
			updateSize();
			updatePosition();	
			form.draw();
		}
	}
	
	public void updatePosition() {
		if(left) { form.setX(x); }
		if(up) { form.setY(y); }
		if(right) { form.setX(x+w-form.getW()); }
		if(down) { form.setY(y+h-form.getH()); }
		
		if(center) {
			form.setPosition(x+w/2-form.getW()/2,y+h/2-form.getH()/2);
		}
		
		if(centerHorizontal) {
			form.setX(x+w/2-form.getW()/2);
		}
		
		if(centerVertical) {
			form.setY(y+h/2-form.getH()/2);
		}
	}
	
	public void updateSize() {
		if(isElementsResizable()) {
			form.setSize(min(w,defaultWidthOfElement), min(h,defaultHeightOfElement));
			
		}
	}
	
	public EdgeLayout set(BaseForm form) {
		this.form = form;
		defaultWidthOfElement = form.getW();
		defaultHeightOfElement = form.getH();
		return this;
	}

	public boolean isLeft() {
		return left;
	}

	public EdgeLayout setLeft(boolean left) {
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

	public EdgeLayout setUp(boolean up) {
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

	public EdgeLayout setRight(boolean right) {
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

	public EdgeLayout setDown(boolean down) {
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

	public EdgeLayout setCenter(boolean center) {
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
	
}