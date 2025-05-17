package microUI.core;

public abstract class Bounds extends View {
	protected float x,y,w,h;
	
	public Bounds(float x, float y, float w, float h) {
		setTransforms(x,y,w,h);
		
	}
	
	public Bounds(Bounds otherBounds) {
		this(otherBounds.getX(),otherBounds.getY(),otherBounds.getW(),otherBounds.getH());
	}
	
	public Bounds() {
		this(0,0,0,0);
	}

	
	public float getX() { return x; }

	public void setX(final float x) {
		if(this.x == x) { return; }
		this.x = x;
		inTransforms();
	}
	
	
	public float getY() { return y; }

	public void setY(final float y) {
		if(this.y == y) { return; }
		this.y = y;
		inTransforms();
	}
	
	
	public float getW() { return w; }
	
	public void setW(final float w) {
		if(this.w == w) { return; }
		if(w < 0) { this.w = 0; return; }
		this.w = w;
		inTransforms();
		
	}
	
	
	public float getH() { return h; }
	
	public void setH(final float h) {
		if(this.h == h) { return; }
		if(h < 0) { this.h = 0; return; }
		this.h = h;
		inTransforms();
	}
	
	
	public void setSize(float w, float h) {
		setW(w);
		setH(h);
	}
	
	public void setSize(float size) {
		setSize(size,size);
	}
	
	public void setSize(final Bounds otherBounds) {
		setW(otherBounds.getW());
		setH(otherBounds.getH());
	}
	
	
	public void setTransforms(float x, float y, float w, float h) {
		setX(x);
		setY(y);
		setW(w);
		setH(h);
	}
	
	public void setTransforms(Bounds otherBounds) {
		setTransforms(otherBounds.getX(),otherBounds.getY(),otherBounds.getW(),otherBounds.getH());
	}
	
	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(Bounds otherBounds) {
		setPosition(otherBounds.getX(),otherBounds.getY());
	}
	
	
	protected void inTransforms() {}

}