package microUI.layout;

import java.util.ArrayList;

import microUI.util.BaseForm;
import microUI.util.Color;
import microUI.util.Scrollable;
import microUI.util.Shadow;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

public abstract class Layout extends BaseForm implements Scrollable {
	public final Color fill;
	public final Margin margin;
	public final Background background;
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
		background = new Background();
		shadow = new Shadow(app,this);
		shadow.invisible();
	}

	@Override
	public void update() {
			if(background.isLoaded()) {
					background.draw();
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
	
	public class Background {
		  public Color tint;
		  private PImage background;
		  
		  public Background() {
			  tint = new Color(255);
		  }
		  
		  private void draw() {
			  if(background != null) {
				  app.pushStyle();
				  app.tint(tint.get());
				  app.image(background,x,y,w,h);
				  app.popStyle();
			  }
		  }
		  
		  public void set(PImage background) {
			  this.background = background;
		  }
		  
		  public void load(String path) {
			  background = app.loadImage(path);
		  }
		  
		  public PImage get() { return background; }
		  
		  public boolean isLoaded() { return background != null; }
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