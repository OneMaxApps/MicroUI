package microUI.util;

import processing.core.PApplet;

public class BaseForm extends View {
	protected float x,y,w,h;
	
	public BaseForm(PApplet app, float x, float y, float w, float h) {
		super(app);
		setTransforms(x,y,w,h);
	}
	
	public BaseForm(PApplet app, float x, float y, float size) {
		this(app,x,y,size,size);
	}
	
	public BaseForm(PApplet app, float w, float h) {
		this(app,0,0,w,h);
	}
	
	public BaseForm(PApplet app) {
		this(app,0,0,0,0);
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
	
	@Override
	public void update() {}

}