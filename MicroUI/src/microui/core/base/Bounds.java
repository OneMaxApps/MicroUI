package microui.core.base;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;

// Status: STABLE - Do not modify
// Last Reviewed: 04.09.2025
public abstract class Bounds extends View {
	private static final float EPSILON = .01f;
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
		if (areEqual(this.x, x)) {
			return;
		}
		this.x = x;
		onChangeBounds();
	}

	public float getY() {
		return y;
	}

	public void setY(final float y) {
		if (areEqual(this.y, y)) {
			return;
		}
		this.y = y;
		onChangeBounds();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(final float width) {
		if (areEqual(this.width, width)) {
			return;
		}

		applyWidth(width);

		onChangeBounds();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(final float height) {
		if (areEqual(this.height, height)) {
			return;
		}

		applyHeight(height);
		
		onChangeBounds();
	}

	public void setSize(final float width, final float height) {
		if (areEqual(this.width, width) && areEqual(this.height, height)) {
			return;
		}

		applyDimensions(width,height);

		onChangeBounds();
	}

	public void setSize(final float size) {
		setSize(size, size);
	}

	public void setSize(final Bounds bounds) {
		setSize(requireNonNull(bounds, "bounds cannot be null").getWidth(), bounds.getHeight());
	}

	public void setBounds(final float x, final float y, final float width, final float height) {
		final boolean hasChanges = !areEqual(this.x, x) || !areEqual(this.y, y) || !areEqual(this.width, width)
				|| !areEqual(this.height, height);

		if (!hasChanges) {
			return;
		}

		this.x = x;
		this.y = y;
		applyDimensions(width,height);
		onChangeBounds();

	}

	public void setBounds(final Bounds bounds) {
		setBounds(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public void setPosition(final float x, final float y) {
		if (areEqual(this.x, x) && areEqual(this.y, y)) {
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

	protected final void setNegativeDimensionsEnabled(final boolean isNegativeDimensionsEnabled) {
		this.isNegativeDimensionsEnabled = isNegativeDimensionsEnabled;
	}

	protected void onChangeBounds() {
	}

	private static final boolean areEqual(final float a, final float b) {
		return abs(a - b) < EPSILON;
	}
	
	private final void applyWidth(final float width) {
		this.width = isNegativeDimensionsEnabled ? width : max(0, width);
	}
	
	private final void applyHeight(final float height) {
		this.height = isNegativeDimensionsEnabled ? height : max(0, height);
	}
	
	private final void applyDimensions(final float width, final float height) {
		applyWidth(width);
		applyHeight(height);
	}
}