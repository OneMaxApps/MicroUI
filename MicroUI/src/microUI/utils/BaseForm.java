package microUI.utils;

import processing.core.PApplet;

public class BaseForm {
	protected PApplet app;
	protected float x,y,w,h;
	protected  boolean isVisible;
	
	public BaseForm(PApplet app, float x, float y, float w, float h) {
		super();
		this.app = app;
		setTransforms(x,y,w,h);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setSize(float w, float h) {
		setW(w);
		setH(h);
	}
	
	public void setTransforms(float x, float y, float w, float h) {
		setX(x);
		setY(y);
		setW(w);
		setH(h);
	}
	
	public void setTransforms(BaseForm baseForm) {
		setTransforms(baseForm.getX(),baseForm.getY(),baseForm.getW(),baseForm.getH());
	}
	
	public void draw() {}
	
	public void setVisible(boolean v) { isVisible = v; }
    
    public boolean isVisible() { return isVisible; }
}