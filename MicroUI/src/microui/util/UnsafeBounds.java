package microui.util;

import microui.core.base.Bounds;

public abstract class UnsafeBounds extends Bounds {
	private boolean allowNegativeDimensions;
	
	public UnsafeBounds() {
		super();
	}

	public UnsafeBounds(Bounds otherBounds) {
		super(otherBounds);
	}

	public UnsafeBounds(float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	@Override
	public void setWidth(final float w) {
		if(this.w == w) { return; }
		if(!isAllowedNegativeDimensions()) {
		if(w < 0) { this.w = 0; return; }
		}
		this.w = w;
		onChangeBounds();
		
	}
	
	@Override
	public void setHeight(final float h) {
		if(this.h == h) { return; }
		if(!isAllowedNegativeDimensions()) {
		if(h < 0) { this.h = 0; return; }
		}
		this.h = h;
		onChangeBounds();
	}
	
	public final boolean isAllowedNegativeDimensions() {
		return allowNegativeDimensions;
	}

	public final void allowNegativeDimensions(boolean allowNegativeDimensions) {
		this.allowNegativeDimensions = allowNegativeDimensions;
	}
}