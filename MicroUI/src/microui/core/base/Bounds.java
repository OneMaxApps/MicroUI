package microui.core.base;

import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;

// Status: STABLE - Do not modify
// Last Reviewed: 04.09.2025
public abstract class Bounds extends View {
	private float x, y, width, height;
	private boolean isNegativeDimensionsEnabled;

	public Bounds(float x, float y, float width, float height) {
		setBounds(x, y, width, height);
	}

	public Bounds(Bounds bounds) {
		this(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public Bounds() {
		this(0, 0, 0, 0);
	}

	public float getX() {
		return x;
	}

	public void setX(final float x) {
		if (this.x == x) {
			return;
		}
		this.x = x;
		onChangeBounds();
	}

	public float getY() {
		return y;
	}

	public void setY(final float y) {
		if (this.y == y) {
			return;
		}
		this.y = y;
		onChangeBounds();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(final float width) {
		if (this.width == width) {
			return;
		}

		this.width = isNegativeDimensionsEnabled ? width : max(0, width);

		onChangeBounds();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(final float height) {
		if (this.height == height) {
			return;
		}

		this.height = isNegativeDimensionsEnabled ? height : max(0, height);

		onChangeBounds();
	}

	public void setSize(final float width, final float height) {
		if (this.width == width && this.height == height) {
			return;
		}

		this.width = isNegativeDimensionsEnabled ? width : max(0, width);
		this.height = isNegativeDimensionsEnabled ? height : max(0, height);

		onChangeBounds();
	}

	public void setSize(final float size) {
		setSize(size, size);
	}

	public void setSize(final Bounds bounds) {
		setSize(requireNonNull(bounds, "bounds cannot be null").getWidth(), bounds.getHeight());
	}

	public void setBounds(final float x, final float y, final float width, final float height) {
		final boolean hasChanges = this.x != x || this.y != y || this.width != width || this.height != height;

		if (hasChanges) {
			this.x = x;
			this.y = y;
			this.width = isNegativeDimensionsEnabled ? width : max(0, width);
			this.height = isNegativeDimensionsEnabled ? height : max(0, height);
			onChangeBounds();
		}

	}

	public void setBounds(final Bounds bounds) {
		setBounds(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public void setPosition(final float x, final float y) {
		if (this.x == x && this.y == y) {
			return;
		}

		this.x = x;
		this.y = y;

		onChangeBounds();

	}

	public void setPosition(final Bounds bounds) {
		setPosition(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY());
	}

	protected final boolean isNegativeDimensionsEnabled() {
		return isNegativeDimensionsEnabled;
	}

	protected final void setNegativeDimensionsEnabled(boolean isNegativeDimensionsEnabled) {
		this.isNegativeDimensionsEnabled = isNegativeDimensionsEnabled;
	}

	protected void onChangeBounds() {
	}

}