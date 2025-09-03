package microui.core.base;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;
import static processing.core.PApplet.constrain;

// Status: STABLE - Do not modify
// Last Reviewed: 04.09.2025
public abstract class Bounds extends View {
	private static final float EPSILON = .01f;
	private float x, y, width, height, minWidth, maxWidth, minHeight, maxHeight;
	private boolean isNegativeDimensionsEnabled,isConstrainDimensionsEnabled;

	public Bounds(float x, float y, float width, float height) {
		setMinWidth(100);
		setMaxWidth(400);
		setMinHeight(50);
		setMaxHeight(200);
		
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
		if (areEqual(this.width, getTargetWidth(width))) {
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
		if (areEqual(this.height, getTargetHeight(height))) {
			return;
		}

		applyHeight(height);

		onChangeDimensions();
		onChangeBounds();
	}

	public final void setSize(final float width, final float height) {
		if (areEqual(this.width, getTargetWidth(width)) && areEqual(this.height, getTargetHeight(height))) {
			return;
		}

		applyDimensions(getTargetWidth(width), getTargetHeight(height));

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
		final boolean hasChanges = !areEqual(this.x, x) || !areEqual(this.y, y)
				|| !areEqual(this.width, getTargetWidth(width)) || !areEqual(this.height, getTargetHeight(height));

		if (!hasChanges) {
			return;
		}

		this.x = x;
		this.y = y;
		applyDimensions(getTargetWidth(width), getTargetHeight(height));

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

	public final float getMinWidth() {
		return minWidth;
	}

	public final void setMinWidth(float minWidth) {
		this.minWidth = isNegativeDimensionsEnabled ? minWidth : min(max(0, minWidth), minWidth);

		onChangeDimensions();
		onChangeBounds();
	}

	public final float getMaxWidth() {
		return maxWidth;
	}

	public final void setMaxWidth(float maxWidth) {
		this.maxWidth = max(minWidth, maxWidth);

		onChangeDimensions();
		onChangeBounds();
	}
	
	public final float getMinHeight() {
		return minHeight;
	}

	public final void setMinHeight(float minHeight) {
		this.minHeight = isNegativeDimensionsEnabled ? minHeight : min(max(0, minHeight), minHeight);

		onChangeDimensions();
		onChangeBounds();
	}

	public final float getMaxHeight() {
		return maxHeight;
	}

	public final void setMaxHeight(float maxHeight) {
		this.maxHeight = max(minHeight, maxHeight);

		onChangeDimensions();
		onChangeBounds();
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
	
	protected final boolean isConstrainDimensionsEnabled() {
		return isConstrainDimensionsEnabled;
	}

	protected final void setConstrainDimensionsEnabled(boolean isConstrainDimensionsEnabled) {
		this.isConstrainDimensionsEnabled = isConstrainDimensionsEnabled;
	}

	private static final boolean areEqual(float firstValue, float secondValue) {
		return abs(firstValue - secondValue) < EPSILON;
	}

	private void applyWidth(float width) {
		this.width = isNegativeDimensionsEnabled ? width : max(0, width);
	}

	private void applyHeight(float height) {
		this.height = isNegativeDimensionsEnabled ? height : max(0, height);
	}

	private void applyDimensions(float width, float height) {
		applyWidth(width);
		applyHeight(height);
	}

	private float getTargetWidth(float rawWidth) {
		if(!isConstrainDimensionsEnabled) { return rawWidth; }
		
		if (isNegativeDimensionsEnabled) {
			return constrain(rawWidth, minWidth, maxWidth);
		}
		return constrain(rawWidth, max(0, minWidth), maxWidth);
	}
	
	private float getTargetHeight(float rawHeight) {
		if(!isConstrainDimensionsEnabled) { return rawHeight; }
		
		if (isNegativeDimensionsEnabled) {
			return constrain(rawHeight, minHeight, maxHeight);
		}
		return constrain(rawHeight, max(0, minHeight), maxHeight);
	}
}