package microui.core.base;

import microui.core.Texture;
import microui.core.effect.Shadow;
import microui.core.interfaces.Focusable;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;


public abstract class Container extends Bounds implements Scrollable, KeyPressable, Focusable {
	public final Color fill;
	public final Margin margin;
	public final Texture image;
	public final Shadow shadow;
	
	protected Container(float x, float y, float w, float h) {
		super(x, y, w, h);
		visible();
		fill = new Color(0,0,128,32);
		margin = new Margin();
		image = new Texture();
		image.setTransforms(this);
		shadow = new Shadow(this);
		shadow.invisible();
	}
	
	@Override
	public void update() {
			if(image.isLoaded()) {
					image.draw();
			} else {
				app.pushStyle();
					app.stroke(0);
					app.strokeWeight(1);
					app.fill(fill.get());
					app.rect(getX(), getY(), getW(), getH());
				app.popStyle();
			}
			
			app.pushStyle();
			shadow.draw();
			app.popStyle();
		
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
	
	@Override
	public void inTransforms() {
		if(image != null) { image.setTransforms(this); }
	}
	
	
	public final class Margin {
		private float left, up, right, down;

		private Margin() {
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
			this.right = left == 0 ? right : right+left;
		}

		public void setDown(float down) {
			this.down = up == 0 ? down : down+up;
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