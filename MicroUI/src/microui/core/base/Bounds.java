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

	public final void setX(final float x) {
		if (areEqual(this.x, x)) {
			return;
		}
		this.x = x;
		onChangePositions();
		onChangeBounds();
	}

	public float getY() {
		return y;
	}

	public final void setY(final float y) {
		if (areEqual(this.y, y)) {
			return;
		}
		this.y = y;
		onChangePositions();
		onChangeBounds();
	}

	public float getWidth() {
		return width;
	}

	public final void setWidth(final float width) {
		if (areEqual(this.width, width)) {
			return;
		}

		applyWidth(width);
		onChangeDimensions();
		onChangeBounds();
	}

	public float getHeight() {
		return height;
	}

	public final void setHeight(final float height) {
		if (areEqual(this.height, height)) {
			return;
		}

		applyHeight(height);

		onChangeDimensions();
		onChangeBounds();
	}

	public final void setSize(final float width, final float height) {
		if (areEqual(this.width, width) && areEqual(this.height, height)) {
			return;
		}

		applyDimensions(width, height);

		onChangeDimensions();
		onChangeBounds();
	}

	public final void setSize(float size) {
		setSize(size, size);
	}

	public final void setSize(final Bounds bounds) {
		setSize(requireNonNull(bounds, "bounds cannot be null").getWidth(), bounds.getHeight());
	}

	public final void setBounds(final float x, final float y, final float width, final float height) {
		final boolean hasChanges = !areEqual(this.x, x) || !areEqual(this.y, y) || !areEqual(this.width, width)
				|| !areEqual(this.height, height);

		if (!hasChanges) {
			return;
		}

		this.x = x;
		this.y = y;
		applyDimensions(width, height);

		onChangePositions();
		onChangeDimensions();
		onChangeBounds();

	}

	public final void setBounds(final Bounds bounds) {
		setBounds(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public final void setPosition(final float x, final float y) {
		if (areEqual(this.x, x) && areEqual(this.y, y)) {
			return;
		}

		this.x = x;
		this.y = y;

		onChangePositions();
		onChangeBounds();

	}

	public final void setPosition(final Bounds bounds) {
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

	protected void onChangeDimensions() {
	}

	protected void onChangePositions() {
	}

	private static final boolean areEqual(float firstValue, float secondValue) {
		return abs(firstValue - secondValue) < EPSILON;
	}

	private final void applyWidth(float width) {
		this.width = isNegativeDimensionsEnabled ? width : max(0, width);
	}

	private final void applyHeight(float height) {
		this.height = isNegativeDimensionsEnabled ? height : max(0, height);
	}

	private final void applyDimensions(float width,float height) {
		applyWidth(width);
		applyHeight(height);
	}
}