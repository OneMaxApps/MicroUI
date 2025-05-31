package microui.core.base;

public abstract class Bounds extends View {
	protected float x,y,w,h;
	
	public Bounds(float x, float y, float w, float h) {
		setBounds(x,y,w,h);
		
	}
	
	public Bounds(Bounds otherBounds) {
		this(otherBounds.getX(),otherBounds.getY(),otherBounds.getWidth(),otherBounds.getHeight());
	}
	
	public Bounds() {
		this(0,0,0,0);
	}

	
	public float getX() { return x; }

	public void setX(final float x) {
		if(this.x == x) { return; }
		this.x = x;
		onChangeBounds();
	}
	
	
	public float getY() { return y; }

	public void setY(final float y) {
		if(this.y == y) { return; }
		this.y = y;
		onChangeBounds();
	}
	
	
	public float getWidth() { return w; }
	
	public void setWidth(final float w) {
		if(this.w == w) { return; }
		if(w < 0) { this.w = 0; return; }
		this.w = w;
		onChangeBounds();
	}
	
	
	public float getHeight() { return h; }
	
	public void setHeight(final float h) {
		if(this.h == h) { return; }
		if(h < 0) { this.h = 0; return; }
		this.h = h;
		onChangeBounds();
	}
	
	
	public void setSize(float w, float h) {
		setWidth(w);
		setHeight(h);
	}
	
	public void setSize(float size) {
		setSize(size,size);
	}
	
	public void setSize(final Bounds otherBounds) {
		if(otherBounds == null) { return; }
		setWidth(otherBounds.getWidth());
		setHeight(otherBounds.getHeight());
	}
	
	
	public void setBounds(float x, float y, float w, float h) {
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
	}
	
	public void setBounds(Bounds otherBounds) {
		if(otherBounds == null) { return; }
		setBounds(otherBounds.getX(),otherBounds.getY(),otherBounds.getWidth(),otherBounds.getHeight());
	}
	
	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(Bounds otherBounds) {
		if(otherBounds == null) { return; }
		setPosition(otherBounds.getX(),otherBounds.getY());
	}
	
	
	public void onChangeBounds() {}

}