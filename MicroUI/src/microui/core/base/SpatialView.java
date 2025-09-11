package microui.core.base;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;

//Status: STABLE - Do not modify
//Last Reviewed: 11.09.2025
public abstract class SpatialView extends View {
	private static final int DEFAULT_MIN_SIZE = 1;
	private static final int DEFAULT_MAX_SIZE = 200;
	private static final float EPSILON = .01f;

	private float x, y, width, height, minWidth, minHeight, maxWidth, maxHeight;
	private boolean isPosDirty, isDimDirty, isNegativeDimensionsEnabled, isConstrainDimensionsEnabled;

	public SpatialView(float x, float y, float width, float height) {
		initDefaultMinMaxSize();
		setBounds(x, y, width, height);
	}

	public SpatialView(SpatialView spatialView) {
		this(requireNonNull(spatialView, "spatialView cannot be null").getX(), spatialView.getY(),
				spatialView.getWidth(), spatialView.getHeight());
	}

	public SpatialView() {
		this(0, 0, 0, 0);
	}

	public final float getX() {
		return x;
	}

	public final void setX(float x) {
		if (areEqual(this.x, x)) {
			return;
		}

		this.x = x;
		isPosDirty = true;
		updateHooks();
	}

	public final void setX(SpatialView spatialView) {
		setX(requireNonNull(spatialView, "spatialView cannot be null").getX());
	}

	public final float getY() {
		return y;
	}

	public final void setY(float y) {
		if (areEqual(this.y, y)) {
			return;
		}
		this.y = y;
		isPosDirty = true;
		updateHooks();
	}

	public final void setY(SpatialView spatialView) {
		setY(requireNonNull(spatialView, "spatialView cannot be null").getY());
	}

	public final float getWidth() {
		return width;
	}

	public final void setWidth(float width) {
		this.width = getCorrectDimension(this.width, width, minWidth, maxWidth);

	}

	public final void setWidth(SpatialView spatialView) {
		setWidth(requireNonNull(spatialView, "spatialView cannot be null").getWidth());
	}

	public final float getHeight() {
		return height;
	}

	public final void setHeight(float height) {
		this.height = getCorrectDimension(this.height, height, minHeight, maxHeight);
	}

	public final void setHeight(SpatialView spatialView) {
		setHeight(requireNonNull(spatialView, "spatialView cannot be null").getHeight());
	}

	public final void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	public final void setPosition(SpatialView spatialView) {
		setPosition(requireNonNull(spatialView, "spatialView cannot be null").getX(), spatialView.getY());
	}

	public final void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

	public final void setSize(float size) {
		setSize(size, size);
	}

	public final void setSize(SpatialView spatialView) {
		setSize(requireNonNull(spatialView, "spatialView cannot be null").getWidth(), spatialView.getHeight());
	}

	public final void setBounds(float x, float y, float width, float height) {
		setPosition(x, y);
		setSize(width, height);
	}

	public final void setBounds(SpatialView spatialView) {
		setBounds(requireNonNull(spatialView, "spatialView cannot be null").getX(), spatialView.getY(),
				spatialView.getWidth(), spatialView.getHeight());
	}

	public final float getMinWidth() {
		return minWidth;
	}

	public final void setMinWidth(float minWidth) {
		if(areEqual(this.minWidth,minWidth)) { return; }
		
		this.minWidth = isNegativeDimensionsEnabled ? minWidth : max(0, minWidth);

		if (this.minWidth > maxWidth) {
			throw new IllegalArgumentException("min width cannot be greater than max width");
		}

		setWidth(max(this.minWidth,width));

	}

	public final void setMinWidth(SpatialView spatialView) {
		setMinWidth(requireNonNull(spatialView, "spatialView cannot be null").getMinWidth());
	}

	public final float getMinHeight() {
		return minHeight;
	}

	public final void setMinHeight(float minHeight) {
		if(areEqual(this.minHeight,minHeight)) { return; }
		
		this.minHeight = isNegativeDimensionsEnabled ? minHeight : max(0, minHeight);

		if (this.minHeight > maxHeight) {
			throw new IllegalArgumentException("min height cannot be greater than max height");
		}

		setHeight(max(this.minHeight,height));
	}

	public final void setMinHeight(SpatialView spatialView) {
		setMinHeight(requireNonNull(spatialView, "spatialView cannot be null").getMinHeight());
	}

	public final float getMaxWidth() {
		return maxWidth;
	}

	public final void setMaxWidth(float maxWidth) {
		if(areEqual(this.maxWidth,maxWidth)) { return; }
		
		this.maxWidth = isNegativeDimensionsEnabled ? maxWidth : max(0, maxWidth);
		
		if (this.maxWidth < minWidth) {
			throw new IllegalArgumentException("max width cannot be lower than min width");
		}

		setWidth(min(this.maxWidth,width));

	}

	public final void setMaxWidth(SpatialView spatialView) {
		setMaxWidth(requireNonNull(spatialView, "spatialView cannot be null").getMaxWidth());
	}

	public final float getMaxHeight() {
		return maxHeight;
	}

	public final void setMaxHeight(float maxHeight) {
		if(areEqual(this.maxHeight,maxHeight)) { return; }
		
		this.maxHeight = isNegativeDimensionsEnabled ? maxHeight : max(0, maxHeight);

		if (this.maxHeight < minHeight) {
			throw new IllegalArgumentException("max height cannot be lower than min height");
		}

		setHeight(min(this.maxHeight,height));
	}

	public final void setMaxHeight(SpatialView spatialView) {
		setMaxHeight(requireNonNull(spatialView, "spatialView cannot be null").getMaxHeight());
	}

	public final void setMinSize(float minWidth, float minHeight) {
		setMinWidth(minWidth);
		setMinHeight(minHeight);
	}

	public final void setMinSize(float minSize) {
		setMinWidth(minSize);
		setMinHeight(minSize);
	}

	public final void setMinSize(SpatialView spatialView) {
		setMinWidth(requireNonNull(spatialView, "spatialView cannot be null"));
		setMinHeight(spatialView);
	}

	public final void setMaxSize(float maxWidth, float maxHeight) {
		setMaxWidth(maxWidth);
		setMaxHeight(maxHeight);
	}

	public final void setMaxSize(float maxSize) {
		setMaxWidth(maxSize);
		setMaxHeight(maxSize);
	}

	public final void setMaxSize(SpatialView spatialView) {
		setMaxWidth(requireNonNull(spatialView, "spatialView cannot be null"));
		setMaxHeight(spatialView);
	}

	public final void setMinMaxSize(float minSize, float maxSize) {
		setMinSize(minSize);
		setMaxSize(maxSize);
	}

	public final void setMinMaxSize(float size) {
		setMinMaxSize(size, size);
	}

	public final void setMinMaxSize(SpatialView spatialView) {
		setMinSize(requireNonNull(spatialView, "spatialView cannot be null").getMinWidth(), spatialView.getMinHeight());
		setMaxSize(spatialView.getMaxWidth(), spatialView.getMaxHeight());
	}

	public final void setBoundsProperty(SpatialView spatialView) {
		setConstrainProperty(spatialView);
		setBounds(spatialView);
	}

	public final void setConstrainProperty(SpatialView spatialView) {
		setMinWidth(requireNonNull(spatialView, "spatialView cannot be null").getMinWidth());
		setMinHeight(spatialView.getMinHeight());
		setMaxWidth(spatialView.getMaxWidth());
		setMaxHeight(spatialView.getMaxHeight());
		setConstrainDimensionsEnabled(spatialView.isConstrainDimensionsEnabled());
		setNegativeDimensionsEnabled(spatialView.isNegativeDimensionsEnabled());
	}

	public final boolean isConstrainDimensionsEnabled() {
		return isConstrainDimensionsEnabled;
	}

	public final void setConstrainDimensionsEnabled(boolean isConstrainDimensionsEnabled) {
		if (this.isConstrainDimensionsEnabled == isConstrainDimensionsEnabled) {
			return;
		}

		this.isConstrainDimensionsEnabled = isConstrainDimensionsEnabled;
		setSize(width, height);

	}

	public final void appendX(float delta) {
		setX(getX() + delta);
	}

	public final void appendX(float delta, float min, float max) {
		setX(constrain(getX() + delta, min, max));
	}

	public final void appendY(float delta) {
		setY(getY() + delta);
	}

	public final void appendY(float delta, float min, float max) {
		setY(constrain(getY() + delta, min, max));
	}

	public final void appendWidth(float delta) {
		setWidth(getWidth() + delta);
	}

	public final void appendWidth(float delta, float min, float max) {
		setWidth(constrain(getWidth() + delta, min, max));
	}

	public final void appendHeight(float delta) {
		setHeight(getHeight() + delta);
	}

	public final void appendHeight(float delta, float min, float max) {
		setHeight(constrain(getHeight() + delta, min, max));
	}

	public final void appendSize(float delta) {
		setSize(getWidth() + delta, getHeight() + delta);
	}

	public final void appendSize(float delta, float min, float max) {
		setSize(constrain(getWidth() + delta, min, max), constrain(getHeight() + delta, min, max));
	}

	protected void onChangePositions() {
	}

	protected void onChangeDimensions() {
	}

	protected void onChangeBounds() {
	}

	protected final boolean isNegativeDimensionsEnabled() {
		return isNegativeDimensionsEnabled;
	}

	protected final void setNegativeDimensionsEnabled(boolean isNegativeDimensionsEnabled) {
		if (this.isNegativeDimensionsEnabled == isNegativeDimensionsEnabled) {
			return;
		}

		this.isNegativeDimensionsEnabled = isNegativeDimensionsEnabled;

		if (isNegativeDimensionsEnabled) {
			setSize(width, height);
		}
	}

	/**
	 * request for update state
	 * <p>
	 * this method calling hooks force, even if not was really changes in position
	 * or dimensions
	 * </p>
	 */
	protected final void requestUpdate() {
		onChangePositions();
		onChangeDimensions();
		onChangeBounds();
	}

	private static boolean areEqual(float firstValue, float secondValue) {
		return abs(firstValue - secondValue) < EPSILON;
	}

	private void updateHooks() {
		
		if (isPosDirty || isDimDirty) {
			onChangeBounds();
			if (isPosDirty) {
				onChangePositions();
			}
			if (isDimDirty) {
				onChangeDimensions();
			}
			System.out.println("hooks is updating");
			isPosDirty = isDimDirty = false;
		}
	}

	private float getCorrectDimension(float currentValue, float newValue, float min, float max) { 
		if(areEqual(currentValue, newValue)) { return currentValue; }
		
		float correctValue = currentValue;

		float constrainedValue = constrain(newValue, min, max);

		if (isConstrainDimensionsEnabled) {
			correctValue = constrainedValue;
		} else {
			if (isNegativeDimensionsEnabled) {
				correctValue = newValue;
			} else {
				correctValue = max(0, newValue);
			}
		}
		
		if (isDimDirty = !areEqual(currentValue, correctValue)) {
			updateHooks();
		}
		
		return correctValue;
	}

	private static float constrain(float value, float min, float max) {
		if (value < min) {
			return min;
		}
		if (value > max) {
			return max;
		}
		return value;
	}

	private void initDefaultMinMaxSize() {
		minWidth = minHeight = DEFAULT_MIN_SIZE;
		maxWidth = maxHeight = DEFAULT_MAX_SIZE;
	}
}