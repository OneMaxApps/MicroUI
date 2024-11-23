package microUI.layouts;

import microUI.utils.BaseForm;
import microUI.utils.Color;
import microUI.utils.Form;
import microUI.utils.Shadow;
import processing.core.PApplet;

public abstract class Layout extends BaseForm {
	private boolean isVisible, isElementsResizable;
	protected PApplet applet;
	public Color fill;
	public Margin margin;
	public Shadow shadow;

	public Layout(PApplet app, float x, float y, float w, float h) {
		super(x, y, w, h);
		this.applet = app;
		fill = new Color(app);
		fill.setHEX(app.color(0, 32));
		margin = new Margin();
		setVisible(true);
	}

	public void draw() {
		if (isVisible()) {
			applet.pushStyle();
			applet.stroke(0);
			applet.strokeWeight(1);
			applet.fill(fill.get());
			applet.rect(getX(), getY(), getW(), getH());
			applet.popStyle();
			
			applet.pushStyle();
			if(shadow != null) {
				shadow.draw();
			}
			applet.popStyle();
		}
	}
	
	

	@Override
	public float getX() {
		return super.getX()+margin.getLeft();
	}

	@Override
	public float getY() {
		return super.getY()+margin.getUp();
	}

	@Override
	public float getW() {
		return super.getW()-margin.getRight();
	}

	@Override
	public float getH() {
		return super.getH()-margin.getDown();
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
	
	public void initShadow() {
		if(shadow == null) {
			shadow = new Shadow(applet,this);
		}
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
	}
}