package microUI.core;

public abstract class AbstractRectangle extends View {
	protected float x,y,w,h;
	
	public AbstractRectangle() {
		super();
	}

	public AbstractRectangle(float x, float y, float w, float h) {
		setTransforms(x,y,w,h);
	}

	public AbstractRectangle(AbstractRectangle rect) {
		this(rect.getX(),rect.getY(),rect.getW(),rect.getH());
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
	
	public void setSize(float w, float h) {
		setW(w);
		setH(h);
	}
	
	public void setSize(AbstractRectangle rect) {
		setW(rect.getW());
		setH(rect.getH());
	}
	
	public void setTransforms(float x, float y, float w, float h) {
		setX(x);
		setY(y);
		setW(w);
		setH(h);
	}
	
	public void setTransforms(AbstractRectangle rect) {
		setTransforms(rect.getX(),rect.getY(),rect.getW(),rect.getH());
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

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(AbstractRectangle baseForm) {
		setX(baseForm.getX());
		setY(baseForm.getY());
	}
	
	public void inTransforms() {
		
	}
}