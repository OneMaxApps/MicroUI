package microui.core.base;

import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;

// Status: STABLE - Do not modify
// Last Reviewed: 27.08.2025
public abstract class Bounds extends View {
	private float x, y, width, height;

	public Bounds(float x, float y, float w, float h) {
		setBounds(x, y, w, h);
	}

	public Bounds(Bounds bounds) {
		this(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),bounds.getHeight());
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

	public void setWidth(final float w) {
		if (this.width == w) {
			return;
		}
		this.width = max(0, w);
		onChangeBounds();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(final float h) {
		if (this.height == h) {
			return;
		}
		this.height = max(0, h);
		onChangeBounds();
	}

	public void setSize(float w, float h) {
		if (this.width == w && this.height == h) {
			return;
		}

		this.width = max(0, w);
		this.height = max(0, h);

		onChangeBounds();
	}

	public void setSize(float size) {
		if (width == size && height == size) {
			return;
		}

		setSize(size, size);
	}

	public void setSize(final Bounds bounds) {
		setSize(requireNonNull(bounds, "bounds cannot be null").getWidth(), bounds.getHeight());
	}

	public void setBounds(float x, float y, float w, float h) {
		boolean hasChanges = this.x != x || this.y != y || this.width != w || this.height != h;

		if (hasChanges) {
			this.x = x;
			this.y = y;
			this.width = max(0, w);
			this.height = max(0, h);
			onChangeBounds();
		}

	}

	public void setBounds(final Bounds bounds) {
		setBounds(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public void setPosition(float x, float y) {
		if (this.x == x && this.y == y) {
			return;
		}

		this.x = x;
		this.y = y;

		onChangeBounds();

	}

	public void setPosition(Bounds bounds) {
		setPosition(requireNonNull(bounds, "bounds cannot be null").getX(), bounds.getY());
	}

	protected void onChangeBounds() {
	}

}