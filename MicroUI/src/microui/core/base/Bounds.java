package microui.core.base;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;
import static processing.core.PApplet.constrain;

//Status: STABLE - Do not modify
//Last Reviewed: 05.09.2025
public abstract class Bounds extends View {
	private static final int DEFAULT_MIN_WIDTH = 1;
	private static final int DEFAULT_MAX_WIDTH = ctx.width;
	private static final int DEFAULT_MIN_HEIGHT = 1;
	private static final int DEFAULT_MAX_HEIGHT = ctx.height;
	private static final float EPSILON = .01f;

	private float x, y, width, height, minWidth, minHeight, maxWidth, maxHeight;
	private boolean isPosDirty, isDimDirty, isNegativeDimensionsEnabled, isConstrainDimensionsEnabled;

	public Bounds(float x, float y, float width, float height) {
		setBounds(x, y, width, height);
		setMaxWidth(DEFAULT_MAX_WIDTH);
		setMaxHeight(DEFAULT_MAX_HEIGHT);
		setMinHeight(DEFAULT_MIN_HEIGHT);
		setMinWidth(DEFAULT_MIN_WIDTH);

	}

	public Bounds(Bounds bounds) {
		this(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public Bounds() {
		this(0, 0, 0, 0);
	}

	@Override
	public void draw() {
		super.draw();
		updateHooks();
	}

	public float getX() {
		return x;
	}

	public final void setX(float x) {
		if (areEqual(this.x, x)) {
			return;
		}

		this.x = x;
		isPosDirty = true;
	}

	public final void setX(Bounds bounds) {
		setX(requireNonNull(bounds, "bounds cannot be null").getX());
	}

	public float getY() {
		return y;
	}

	public final void setY(float y) {
		if (areEqual(this.y, y)) {
			return;
		}
		this.y = y;
		isPosDirty = true;
	}

	public final void setY(Bounds bounds) {
		setY(requireNonNull(bounds, "bounds cannot be null").getY());
	}

	public float getWidth() {
		return width;
	}

	public final void setWidth(float width) {
		if (isConstrainDimensionsEnabled) {
			if (isNegativeDimensionsEnabled) {
				if (!areEqual(this.width, constrain(width, minWidth, maxWidth))) {
					this.width = constrain(width, minWidth, maxWidth);
					isDimDirty = true;
					return;
				}
			} else {
				if (!areEqual(this.width, max(0, constrain(width, minWidth, maxWidth)))) {
					this.width = max(0, constrain(width, minWidth, maxWidth));
					isDimDirty = true;
					return;
				}
			}
		} else {
			if (isNegativeDimensionsEnabled) {
				if (!areEqual(this.width, width)) {
					this.width = width;
					isDimDirty = true;
					return;
				}
			} else {
				if (!areEqual(this.width, max(0, width))) {
					this.width = max(0, width);
					isDimDirty = true;
					return;
				}
			}
		}

	}

	public final void setWidth(Bounds bounds) {
		setWidth(requireNonNull(bounds, "bounds cannot be null").getWidth());
	}

	public float getHeight() {
		return height;
	}

	public final void setHeight(float height) {

		if (isConstrainDimensionsEnabled) {
			if (isNegativeDimensionsEnabled) {
				if (!areEqual(this.height, constrain(height, minHeight, maxHeight))) {
					this.height = constrain(height, minHeight, maxHeight);
					isDimDirty = true;
					return;
				}
			} else {
				if (!areEqual(this.height, max(0, constrain(height, minHeight, maxHeight)))) {
					this.height = max(0, constrain(height, minHeight, maxHeight));
					isDimDirty = true;
					return;
				}
			}
		} else {
			if (isNegativeDimensionsEnabled) {
				if (!areEqual(this.height, height)) {
					this.height = height;
					isDimDirty = true;
					return;
				}
			} else {
				if (!areEqual(this.height, max(0, height))) {
					this.height = max(0, height);
					isDimDirty = true;
					return;
				}
			}
		}

	}

	public final void setHeight(Bounds bounds) {
		setHeight(requireNonNull(bounds, "bounds cannot be null").getHeight());
	}

	public final void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	public final void setPosition(Bounds bounds) {
		setPosition(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY());
	}

	public final void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

	public final void setSize(float size) {
		setWidth(size);
		setHeight(size);
	}

	public final void setSize(Bounds bounds) {
		setSize(requireNonNull(bounds, "bounds cannot be null").getWidth(), bounds.getHeight());
	}

	public final void setBounds(float x, float y, float width, float height) {
		setPosition(x, y);
		setSize(width, height);
	}

	public final void setBounds(Bounds bounds) {
		setBounds(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public final float getMinWidth() {
		return minWidth;
	}

	public final void setMinWidth(float minWidth) {
		if (isNegativeDimensionsEnabled) {
			this.minWidth = minWidth;
		} else {
			this.minWidth = max(0, minWidth);
		}

		setWidth(width);

		if (minWidth > maxWidth) {
			throw new IllegalArgumentException("min width cannot be greater than max width");
		}
	}

	public final void setMinWidth(Bounds bounds) {
		setMinWidth(requireNonNull(bounds, "bounds cannot be null").getMinWidth());
	}

	public final float getMinHeight() {
		return minHeight;
	}

	public final void setMinHeight(float minHeight) {
		if (isNegativeDimensionsEnabled) {
			this.minHeight = minHeight;
		} else {
			this.minHeight = max(0, minHeight);
		}

		setHeight(height);

		if (minHeight > maxHeight) {
			throw new IllegalArgumentException("min height cannot be greater than max height");
		}

	}

	public final void setMinHeight(Bounds bounds) {
		setMinHeight(requireNonNull(bounds, "bounds cannot be null").getMinHeight());
	}

	public final float getMaxWidth() {
		return maxWidth;
	}

	public final void setMaxWidth(float maxWidth) {
		if (isNegativeDimensionsEnabled) {
			this.maxWidth = maxWidth;
		} else {
			this.maxWidth = max(0, maxWidth);
		}

		setWidth(width);

		if (maxWidth < minWidth) {
			throw new IllegalArgumentException("max width cannot be lower than min width");
		}

	}

	public final void setMaxWidth(Bounds bounds) {
		setMaxWidth(requireNonNull(bounds, "bounds cannot be null").getMaxWidth());
	}

	public final float getMaxHeight() {
		return maxHeight;
	}

	public final void setMaxHeight(float maxHeight) {
		if (isNegativeDimensionsEnabled) {
			this.maxHeight = maxHeight;
		} else {
			this.maxHeight = max(0, maxHeight);
		}

		setHeight(height);

		if (maxHeight < minHeight) {
			throw new IllegalArgumentException("max height cannot be lower than min height");
		}
	}

	public final void setMaxHeight(Bounds bounds) {
		setMaxHeight(requireNonNull(bounds, "bounds cannot be null").getMaxHeight());
	}

	public final void setMinSize(float minWidth, float minHeight) {
		setMinWidth(minWidth);
		setMinHeight(minHeight);
	}

	public final void setMinSize(float minSize) {
		setMinWidth(minSize);
		setMinHeight(minSize);
	}

	public final void setMinSize(Bounds bounds) {
		setMinWidth(bounds);
		setMinHeight(bounds);
	}

	public final void setMaxSize(float maxWidth, float maxHeight) {
		setMaxWidth(maxWidth);
		setMaxHeight(maxHeight);
	}

	public final void setMaxSize(float maxSize) {
		setMaxWidth(maxSize);
		setMaxHeight(maxSize);
	}

	public final void setMaxSize(Bounds bounds) {
		setMaxWidth(bounds);
		setMaxHeight(bounds);
	}

	public final void setBoundsState(Bounds bounds) {
		setMinWidth(requireNonNull(bounds, "bounds cannot be null").getMinWidth());
		setMinHeight(bounds.getMinHeight());
		setMaxWidth(bounds.getMaxWidth());
		setMaxHeight(bounds.getMaxHeight());
		setConstrainDimensionsEnabled(bounds.isConstrainDimensionsEnabled());
		setNegativeDimensionsEnabled(bounds.isNegativeDimensionsEnabled());
		setBounds(bounds);
	}

	public final boolean isConstrainDimensionsEnabled() {
		return isConstrainDimensionsEnabled;
	}

	public final void setConstrainDimensionsEnabled(boolean isConstrainDimensionsEnabled) {
		if (this.isConstrainDimensionsEnabled != isConstrainDimensionsEnabled) {
			this.isConstrainDimensionsEnabled = isConstrainDimensionsEnabled;
			setSize(width, height);
		}

	}

	protected void onChangePositions() {
	}

	protected void onChangeDimensions() {
	}

	protected void onChangeBounds() {
	}

	protected boolean isNegativeDimensionsEnabled() {
		return isNegativeDimensionsEnabled;
	}

	protected void setNegativeDimensionsEnabled(boolean isNegativeDimensionsEnabled) {
		this.isNegativeDimensionsEnabled = isNegativeDimensionsEnabled;
	}

	private static boolean areEqual(float firstValue, float secondValue) {
		return abs(firstValue - secondValue) < EPSILON;
	}

	private void updateHooks() {
		if (isPosDirty && isDimDirty) {
			onChangePositions();
			onChangeDimensions();
			onChangeBounds();
			isPosDirty = isDimDirty = false;
			return;
		}

		if (isPosDirty || isDimDirty) {
			onChangeBounds();
		}

		if (isPosDirty) {
			onChangePositions();
			isPosDirty = false;
		}

		if (isDimDirty) {
			onChangeDimensions();
			isDimDirty = false;
		}
	}

}