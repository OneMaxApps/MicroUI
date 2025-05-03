package microUI.core;

import static processing.core.PApplet.constrain;

public abstract class Bounds extends View {
	protected float x,y,w,h;
	private boolean allowNegativeDimensions,constrainInsideScreen;
	
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
		if(!isAllowedNegativeDimensions()) {
		if(w < 0) { this.w = 0; return; }
		}
		
		inTransforms();
		this.w = w;
	}
	
	
	public float getH() { return h; }
	
	public void setH(final float h) {
		if(this.h == h) { return; }
		if(!isAllowedNegativeDimensions()) {
		if(h < 0) { this.h = 0; return; }
		}
		inTransforms();
		this.h = h;
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
	
	
	protected void inTransforms() {
		if(constrainInsideScreen) {
			
			if(allowNegativeDimensions) {
				
				if(w < 0) { x = constrain(x, -w, app.width);
				} else {
					x = constrain(x, 0, app.width-w);
				}
				
				if(h < 0) { y = constrain(y, -h, app.height);
				} else {
					y = constrain(y, 0, app.height-h);
				}
				
			} else {
				x = constrain(x, 0, app.width-w);
				y = constrain(y, 0, app.height-h);
			}
			
		}
	}
	
	
	public final boolean isAllowedNegativeDimensions() {
		return allowNegativeDimensions;
	}

	public final void allowNegativeDimensions(boolean allowNegativeDimensions) {
		this.allowNegativeDimensions = allowNegativeDimensions;
	}

	
	public final boolean isConstrainInsideScreen() {
		return constrainInsideScreen;
	}

	public final void setConstrainInsideScreen(boolean constrainInsideScreen) {
		this.constrainInsideScreen = constrainInsideScreen;
	}
	
}