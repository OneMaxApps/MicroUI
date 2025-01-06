package microUI.layouts;

import java.util.ArrayList;

import microUI.CircleSeekBar;
import microUI.Scroll;
import microUI.Slider;
import microUI.utils.BaseForm;
import microUI.utils.Color;
import microUI.utils.Shadow;
import processing.core.PApplet;
import processing.event.MouseEvent;

public abstract class Layout extends BaseForm {
	private boolean isVisible, isElementsResizable;
	protected ArrayList<BaseForm> elementList;
	public Color fill;
	public Margin margin;
	public Shadow shadow;

	public Layout(PApplet app, float x, float y, float w, float h) {
		super(app,x, y, w, h);
		elementList = new ArrayList<BaseForm>();
		fill = new Color(app);
		fill.setHEX(app.color(0, 32));
		margin = new Margin();
		setVisible(true);
	}

	public void draw() {
		if (isVisible()) {
			app.pushStyle();
				app.stroke(0);
				app.strokeWeight(1);
				app.fill(fill.get());
				app.rect(getX(), getY(), getW(), getH());
			app.popStyle();
			
			app.pushStyle();
				if(shadow != null) {
					shadow.draw();
				}
			app.popStyle();
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
	
	public void setVisibleTotal(boolean v) {
		setVisible(v);
		for(BaseForm form : elementList) {
			if(form instanceof EdgeLayout) {
				form.setVisible(v);
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
	
	public void mouseWheelInit(MouseEvent e) {
		  for(int i = 0; i < elementList.size(); i++) {
			  if(elementList.get(i) instanceof Slider) {
				  ((Slider) elementList.get(i)).scrolling.init(e);
			  }
			  
			  if(elementList.get(i) instanceof Scroll) {
				  ((Scroll) elementList.get(i)).scrolling.init(e);
			  }

			  if(elementList.get(i) instanceof CircleSeekBar) {
				  ((CircleSeekBar) elementList.get(i)).scrolling.init(e);
			  }
			  
			  if(elementList.get(i) instanceof Layout) {
				  ((Layout) elementList.get(i)).mouseWheelInit(e);
			  }
		  }
	  }
	
	public void initShadow() {
		if(shadow == null) {
			shadow = new Shadow(app,this);
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