package microUI.layouts;

import microUI.utils.BaseForm;
import microUI.utils.Color;
import processing.core.PApplet;

public abstract class Layout extends BaseForm {
	private boolean isVisible, isElementsResizable;
	protected PApplet applet;
	public Color fill;
	public Margin margin;

	public Layout(PApplet app, float x, float y, float w, float h) {
		super(x, y, w, h);
		this.applet = app;
		fill = new Color(app);
		fill.set(app.color(0, 32));
		margin = new Margin();
		setVisible(true);
	}

	public void draw() {
		if (isVisible()) {
			applet.pushStyle();
			applet.fill(fill.get());
			applet.rect(getX(), getY(), getW(), getH());
			applet.popStyle();
		}
	}

	public boolean isElementsResizable() {
		return isElementsResizable;
	}

	public void setElementsResizable(boolean r) {
		isElementsResizable = r;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean v) {
		isVisible = v;
	}

	public class Margin {
		private float left, up, right, down;

		public Margin() {
		}

		public Margin(float margin) {
			set(margin);
		}

		public Margin(float left, float up, float right, float down) {
			set(left, up, right, down);
		}

		public void set(float left, float up, float right, float down) {
			setLeft(left);
			setUp(up);
			setRight(right);
			setDown(down);
			
		}

		public void set(float margin) {
			set(margin, margin, margin * 2, margin * 2);
			
		}

		public void setLeft(float left) {
			setX(getX() + left);
			this.left = left;
		}

		public void setUp(float up) {
			setY(getY() + up);
			this.up = up;
			
		}

		public void setRight(float right) {
			setW(getW() - right);
			this.right = right;
		}

		public void setDown(float down) {
			setH(getH() - down);
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
	}
}