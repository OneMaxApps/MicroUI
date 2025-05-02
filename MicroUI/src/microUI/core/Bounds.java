package microUI.core;

public abstract class Bounds extends View {
	protected float x,y,w,h;
	private boolean allowNegativeDimensions;
	
	public Bounds(float x, float y, float w, float h) {
		setTransforms(x,y,w,h);
	}
	
	public Bounds(Bounds otherBounds) {
		this(otherBounds.getX(),otherBounds.getY(),otherBounds.getW(),otherBounds.getH());
	}
	
	public Bounds() {
		super();
	}

	public float getW() { return w; }
	
	public void setW(final float w) {
		if(!isAllowedNegativeDimensions()) {
		if(w <= 0) { this.w = 0; return; }
		}
		
		inTransforms();
		this.w = w;
	}
	
	public float getH() { return h; }
	
	public void setH(final float h) {
		if(!isAllowedNegativeDimensions()) {
		if(h <= 0) { this.h = 0; return; }
		}
		inTransforms();
		this.h = h;
	}
	
	public void setSize(final float w, final float h) {
		setW(w);
		setH(h);
	}
	
	public void setSize(final Bounds otherBounds) {
		setW(otherBounds.getW());
		setH(otherBounds.getH());
	}
	
	public void setTransforms(final float x, final float y, final float w, final float h) {
		setX(x);
		setY(y);
		setW(w);
		setH(h);
	}
	
	public void setTransforms(Bounds otherBounds) {
		setTransforms(otherBounds.getX(),otherBounds.getY(),otherBounds.getW(),otherBounds.getH());
	}
	
	public float getX() { return x; }

	public void setX(final float x) {
		this.x = x;
		inTransforms();
	}

	public float getY() { return y; }

	public void setY(final float y) {
		this.y = y;
		inTransforms();
	}

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(Bounds otherBounds) {
		setX(otherBounds.getX());
		setY(otherBounds.getY());
	}
	
	public void inTransforms() {}

	public final boolean isAllowedNegativeDimensions() {
		return allowNegativeDimensions;
	}

	public final void allowNegativeDimensions(boolean allowNegativeDimensions) {
		this.allowNegativeDimensions = allowNegativeDimensions;
	}

}