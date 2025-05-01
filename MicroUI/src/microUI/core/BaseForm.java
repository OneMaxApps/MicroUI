package microUI.core;

public abstract class BaseForm extends View {
	protected float x,y,w,h;
	
	public BaseForm(float x, float y, float w, float h) {
		setTransforms(x,y,w,h);
	}
	
	public BaseForm(float x, float y, float size) {
		this(x,y,size,size);
	}
	
	public BaseForm(float w, float h) {
		this(0,0,w,h);
	}
	
	public BaseForm(BaseForm baseForm) {
		this(baseForm.getX(),baseForm.getY(),baseForm.getW(),baseForm.getH());
	}
	
	public BaseForm() {
		this(0,0,0,0);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		inTransforms();
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		inTransforms();
	}

	public float getW() {
		return w;
	}
	
	public void setW(float w) {
		if(w <= 0) { this.w = 0; return; }
		inTransforms();
		this.w = w;
	}
	
	public float getH() {
		
		return h;
	}
	
	public void setH(float h) {
		if(h <= 0) { this.h = 0; return; }
		inTransforms();
		this.h = h;
	}

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(BaseForm baseForm) {
		setX(baseForm.getX());
		setY(baseForm.getY());
	}
	
	public void setSize(float w, float h) {
		setW(w);
		setH(h);
	}
	
	public void setSize(BaseForm baseForm) {
		setW(baseForm.getW());
		setH(baseForm.getH());
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
	
	public void inTransforms() {
		
	}
	
}