package microUI.layouts;

import static processing.core.PApplet.min;

import microUI.MicroUI;
import microUI.utils.BaseForm;

public class EdgeLayout extends Layout {
	private BaseForm form;
	private boolean left,up,right,down,center,centerHorizontal,centerVertical;
	private float defaultWidthOfElement,defaultHeightOfElement;
	

	public EdgeLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		setCenter(true);
		setElementsResizable(true);
	}
	
	public EdgeLayout() {
		this(0,0,MicroUI.app.width,MicroUI.app.height);
	}
	
	public void draw() {
	super.draw();
		
		if(form != null) {
			updateSize(form);
			updatePosition(form);
			form.draw();
		}
	}
	
	public void updatePosition(BaseForm form) {
		
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
	
	public void updateSize(BaseForm f) {
		if(isElementsResizable()) {
			f.setSize(min(getW(),defaultWidthOfElement), min(getH(),defaultHeightOfElement));
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
	
	public BaseForm getElement() { return form; }
}