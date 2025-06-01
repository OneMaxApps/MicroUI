package microui.core.base;

// Status: STABLE - Do not modify
// Last Reviewed: 01.06.2025
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
		this.w = Math.max(0, w);
		onChangeBounds();
	}
	
	
	public float getHeight() { return h; }
	
	public void setHeight(final float h) {
		if(this.h == h) { return; }	
		this.h = Math.max(0, h);
		onChangeBounds();
	}
	
	
	public void setSize(float w, float h) {
		if(this.w == w && this.h == h) { return; }
		
		this.w = Math.max(0, w);
		this.h = Math.max(0, h);
		
		onChangeBounds();	
	}
	
	public void setSize(float size) {
		if(w == size && h == size) { return; }
		
		setSize(size,size);
	}
	
	public void setSize(final Bounds otherBounds) {
		if(otherBounds == null) { return; }
		setSize(otherBounds.getWidth(),otherBounds.getHeight());
	}
	
	public void setBounds(float x, float y, float w, float h) {
		boolean hasChanges = this.x != x || this.y != y || this.w != w || this.h != h;
		
		if(hasChanges) {
			this.x = x;
			this.y = y;
			this.w = Math.max(0, w);
			this.h = Math.max(0, h);
			onChangeBounds();
		}
		
	}
	
	public void setBounds(final Bounds otherBounds) {
		if(otherBounds == null) { return; }
		setBounds(otherBounds.getX(),otherBounds.getY(),otherBounds.getWidth(),otherBounds.getHeight());
	}
	
	
	public void setPosition(float x, float y) {
		if(this.x == x && this.y == y) { return; }
		
		this.x = x;
		this.y = y;
		
		onChangeBounds();
		
	}
	
	public void setPosition(Bounds otherBounds) {
		if(otherBounds == null) { return; }
		
		setPosition(otherBounds.getX(),otherBounds.getY());
	}
	
	
	public void onChangeBounds() {}

}