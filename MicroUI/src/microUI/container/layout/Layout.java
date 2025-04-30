package microUI.container.layout;

import java.util.ArrayList;

import microUI.core.BaseForm;
import microUI.core.BaseImage;
import microUI.effect.Shadow;
import microUI.event.KeyPressable;
import microUI.event.Scrollable;
import microUI.util.Color;
import processing.core.PApplet;
import processing.event.MouseEvent;

public abstract class Layout extends BaseForm implements Scrollable, KeyPressable {
	public final Color fill;
	public final Margin margin;
	public final BaseImage image;
	public final Shadow shadow;
	protected boolean isElementsResizable;
	protected final ArrayList<BaseForm> elementList;
	
	
	public Layout(PApplet app, float x, float y, float w, float h) {
		super(app,x, y, w, h);
		elementList = new ArrayList<BaseForm>();
		fill = new Color();
		fill.set(0,0,128,32);
		margin = new Margin();
		setVisible(true);
		image = new BaseImage(app) {
			@Override
			public void update() {
				if(isLoaded()) {
					  app.pushStyle();
					  app.tint(tint.get());
					  app.image(image,x,y,w,h);
					  app.popStyle();
				  }
			}
		};
		image.setTransforms(this);
		
		shadow = new Shadow(app,this);
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
				if(shadow != null) { shadow.draw(); }
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
	public void setX(float x) {
		if(image != null) { image.setX(x); }
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		if(image != null) { image.setY(y); }
		super.setY(y);
	}

	@Override
	public void setW(float w) {
		if(image != null) { image.setW(w); }
		super.setW(w);
	}

	@Override
	public void setH(float h) {
		if(image != null) { image.setH(h); }
		super.setH(h);
	}

	public boolean isElementsResizable() {
		return isElementsResizable;
	}

	public void setElementsResizable(boolean r) {
		isElementsResizable = r;
	}
	
	public void setVisibleTotal(boolean v) {
		
		setVisible(v);
		for(BaseForm form : elementList) {
			if(form instanceof EdgeLayout) {
				EdgeLayout e = (EdgeLayout) form;
				if(e.getElement() instanceof Layout) {
					((Layout) (e.getElement())).setVisibleTotal(v);
				}
			} else {
				if(form instanceof Layout) {
					((Layout) (form)).setVisibleTotal(v);
				} 	
			}
			
		}
		
	}
	
	public ArrayList<BaseForm> getElements() {
		return elementList;
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {
		elementList.forEach(element -> {
			if(element instanceof Scrollable) {
				((Scrollable) element).mouseWheel(e);
			}
		});
	}

	@Override
	public void keyPressed() {
		elementList.forEach(element -> {
			if(element instanceof KeyPressable) {
				((KeyPressable) element).keyPressed();
			}
		});
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

	protected abstract class Transforming {
		private float layX,layY,layW,layH;
		  
		  protected final void autoUpdate() {
			  if(layX != x) {
				  layX = x;
				  updateForce();
			  }
			  
			  if(layY != y) {
				  layY = y;
				  updateForce();
			  }
			  
			  if(layW != w) {
				  layW = w;
				  updateForce();
			  }
			  
			  if(layH != h) {
				  layH = h;
				  updateForce();
			  }
		  }
		  
		  
		  protected abstract void updateForce();

	}
}