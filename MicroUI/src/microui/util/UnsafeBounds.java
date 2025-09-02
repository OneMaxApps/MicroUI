package microui.util;

import microui.core.base.Bounds;

//FIXME there can be infinity recursion
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
		if (this.getWidth() == w) {
			return;
		}
		if (!isAllowedNegativeDimensions()) {
			if (w < 0) {
				setWidth(0);
				return;
			}
		}
		setWidth(w);
		onChangeBounds();

	}

	@Override
	public void setHeight(final float h) {
		if (this.getHeight() == h) {
			return;
		}
		if (!isAllowedNegativeDimensions()) {
			if (h < 0) {
				setHeight(0);
				return;
			}
		}
		setHeight(h);
		onChangeBounds();
	}

	public final boolean isAllowedNegativeDimensions() {
		return allowNegativeDimensions;
	}

	public final void allowNegativeDimensions(boolean allowNegativeDimensions) {
		this.allowNegativeDimensions = allowNegativeDimensions;
	}
}