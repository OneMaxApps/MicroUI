package microui.core.base;

import static java.util.Objects.requireNonNull;
import static processing.core.PApplet.max;

import java.util.Objects;

import microui.core.Texture;
import microui.core.effect.Shadow;
import microui.core.interfaces.Focusable;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Event;
import processing.core.PImage;

public abstract class Container extends Bounds implements Scrollable, KeyPressable, Focusable {
	protected final Color color;
	protected ResizeHandle resizeHandle;
	private final Margin margin;
	private final Texture image;
	private final Shadow shadow;
	private final Container context;

	protected Container(float x, float y, float w, float h) {
		super(x, y, w, h);
		setVisible(true);
		color = new Color(0, 0, 128, 32);
		margin = new Margin();
		image = new Texture();
		image.setBounds(this);
		shadow = new Shadow(this);
		shadow.setVisible(false);

		context = this;
	}

	@Override
	public void draw() {
		super.draw();

		if (resizeHandle != null) {
			resizeHandle.draw();
		}
	}

	@Override
	public void update() {
		if (image.isLoaded()) {
			image.draw();
		} else {
			app.pushStyle();
			app.stroke(0);
			app.strokeWeight(1);
			app.fill(color.get());
			app.rect(getX(), getY(), getWidth(), getHeight());
			app.popStyle();
		}

		app.pushStyle();
		shadow.draw();
		app.popStyle();

	}

	@Override
	public float getX() {
		return super.getX() + margin.getLeft();
	}

	@Override
	public float getY() {
		return super.getY() + margin.getUp();
	}

	@Override
	public float getWidth() {
		if (margin.isAutoCenterModeEnabled()) {
			return super.getWidth() - (margin.getRight() + margin.getLeft());
		} else {
			return super.getWidth() - margin.getRight();
		}
	}

	@Override
	public float getHeight() {
		if (margin.isAutoCenterModeEnabled()) {
			return super.getHeight() - (margin.getDown() + margin.getUp());
		} else {
			return super.getHeight() - margin.getDown();
		}
	}

	public final float getRealX() {
		return x;
	}

	public final float getRealY() {
		return y;
	}

	public final float getRealWidth() {
		return w;
	}

	public final float getRealHeight() {
		return h;
	}

	public final Color getColor() {
		return new Color(color);
	}

	public final void setColor(Color color) {
		this.color.set(Objects.requireNonNull(color, "color cannot be null"));
	}

	public final boolean isUserResizeEnabled() {
		return resizeHandle != null;
	}

	public final void setUserResizeEnabled(boolean enabled) {
		if(enabled) {
			if(resizeHandle == null) {
			resizeHandle = new ResizeHandle();
			}
		} else {
			resizeHandle = null;
		}
	}

	public final void setMargin(float left, float up, float right, float down) {
		margin.setLeft(left);
		margin.setUp(up);
		margin.setRight(right);
		margin.setDown(down);
	}

	public final void setMargin(float margin) {
		setMargin(margin,margin,margin,margin);
	}

	public final void setMarginLeft(float left) {
		setMargin(left,0,0,0);
	}

	public final void setMarginUp(float up) {
		setMargin(0,up,0,0);
	}

	public final void setMarginRight(float right) {
		setMargin(0,0,right,0);
	}

	public final void setMarginDown(float down) {
		setMargin(0,0,0,down);
	}

	public final float getMarginLeft() {
		return margin.getLeft();
	}

	public final float getMarginUp() {
		return margin.getUp();
	}

	public final float getMarginRight() {
		return margin.getRight();
	}

	public final float getMarginDown() {
		return margin.getDown();
	}

	public final boolean isMarginAutoCenterModeEnabled() {
		return margin.isAutoCenterModeEnabled();
	}

	public final void setMarginAutoCenterModeEnabled(boolean setAutoCenterModeEnabled) {
		margin.setAutoCenterModeEnabled(setAutoCenterModeEnabled);
	}

	public final PImage getImage() {
		return image.get();
	}

	public final void setImage(PImage image) {
		this.image.set(requireNonNull(image, "image cannot be null"));
	}

	public final boolean isShadowVisible() {
		return shadow.isVisible();
	}

	public final void setShadowVisible(boolean visible) {
		shadow.setVisible(visible);
	}

	public final void setShadowRadius(float radius) {
		shadow.set(radius);
	}

	@Override
	protected void onChangeBounds() {
		if (image != null) {
			image.setBounds(this);
		}
		if (resizeHandle != null) {
			resizeHandle.dots.inTransforms();
		}
	}

	public final class Margin {
		private float left, up, right, down;
		private boolean isAutoCenterModeEnabled;

		private Margin() {
			isAutoCenterModeEnabled = true;

		}

		public void set(float left, float up, float right, float down) {
			setLeft(left);
			setUp(up);
			setRight(right);
			setDown(down);

		}

		public void set(float margin) {
			set(margin, margin, margin, margin);

		}

		public void setLeft(float left) {
			this.left = left;
		}

		public void setUp(float up) {
			this.up = up;

		}

		public void setRight(float right) {
			this.right = right;
		}

		public void setDown(float down) {
			this.down = down;
		}

		public float getLeft() {
			return left;
		}

		public float getUp() {
			return up;
		}

		public float getRight() {
			return right;
		}

		public float getDown() {
			return down;
		}

		public final boolean isAutoCenterModeEnabled() {
			return isAutoCenterModeEnabled;
		}

		public final void setAutoCenterModeEnabled(boolean setAutoCenterModeEnabled) {
			this.isAutoCenterModeEnabled = setAutoCenterModeEnabled;
		}

	}

	public final class ResizeHandle extends View {
		public final Color colorDots;
		private final Dots dots;
		private int minWidth, minHeight;

		private ResizeHandle() {
			setVisible(true);
			colorDots = new Color(255);
			dots = new Dots();
			minWidth = minHeight = 100;
		}

		@Override
		public void draw() {
			super.draw();
		}

		@Override
		public void update() {
			colorDots.apply();
			dots.draw();
		}

		public final int getMinWidth() {
			return minWidth;
		}

		public final void setMinWidth(int minWidth) {
			if (minWidth <= 0) {
				throw new IllegalArgumentException("minWidth cannot be less than 1");
			}
			this.minWidth = minWidth;
		}

		public final int getMinHeight() {
			return minHeight;
		}

		public final void setMinHeight(int minHeight) {
			if (minHeight <= 0) {
				throw new IllegalArgumentException("minHeight cannot be less than 1");
			}
			this.minHeight = minHeight;
		}

		public final Dots.Dot[] getDots() {
			return dots.dots;
		}

		public final Dots.Dot getDot(int index) {
			if (index < 0 || index > Dots.DOTS_COUNT) {
				throw new IllegalArgumentException("index cannot be less than 0 and greater than " + Dots.DOTS_COUNT);
			}
			return dots.dots[index];
		}

		private final class Dots {
			private static final int LEFT = 0, RIGHT = 1, DOWN_LEFT = 2, DOWN_RIGHT = 3,

					DOTS_COUNT = 4;

			private final Dot[] dots;

			private Dots() {
				dots = new Dot[DOTS_COUNT];
				for (int i = 0; i < DOTS_COUNT; i++) {
					dots[i] = new Dot(i);
				}

				inTransforms();
			}

			private void draw() {
				app.pushStyle();
				app.noStroke();
				for (Dot dot : dots) {
					dot.draw();
				}
				app.popStyle();
			}

			private void inTransforms() {
				for (Dot dot : dots) {
					dot.onChangeBounds();
				}
			}

			public final class Dot extends Bounds {
				private final int mode;
				private final Event event;
				private float tmpX, tmpY, difX, difY;

				public Dot(final int mode) {
					this.mode = mode;
					setVisible(true);
					setSize(10, 10);
					event = new Event();
				}

				@Override
				public final void update() {
					event.listen(this);
					app.rect(x, y, w, h);
					if (event.holding()) {

						switch (mode) {
						case LEFT:
							tmpX = getRealX();
							tmpY = getRealY();

							context.setPosition(app.mouseX, app.mouseY);

							difX = tmpX - getRealX();
							difY = tmpY - getRealY();

							context.setSize(getRealWidth() + difX, getRealHeight() + difY);
							break;

						case RIGHT:
							tmpY = getRealY();

							context.setWidth(app.mouseX - getRealX());
							context.setY(app.mouseY);

							difY = tmpY - getRealY();

							context.setHeight(getRealHeight() + difY);
							break;

						case DOWN_LEFT:
							tmpX = getRealX();

							context.setX(app.mouseX);
							context.setHeight(app.mouseY - getRealY());

							difX = tmpX - getRealX();

							context.setWidth(getRealWidth() + difX);
							break;

						case DOWN_RIGHT:
							context.setSize(app.mouseX - getRealX(), app.mouseY - getRealY());
							break;
						}

						context.setSize(max(minWidth, context.w), max(minHeight, context.h));
					}
				}

				@Override
				protected void onChangeBounds() {
					super.onChangeBounds();
					calcTransforms();
				}

				private final void calcTransforms() {
					switch (mode) {
					case LEFT:
						setPosition(getRealX(), getRealY());
						break;
					case RIGHT:
						setPosition(getRealX() + getRealWidth() - w, getRealY());
						break;
					case DOWN_LEFT:
						setPosition(getRealX(), getRealY() + getRealHeight() - h);
						break;
					case DOWN_RIGHT:
						setPosition(getRealX() + getRealWidth() - w, getRealY() + getRealHeight() - h);
						break;
					}
				}

			}
		}
	}
}