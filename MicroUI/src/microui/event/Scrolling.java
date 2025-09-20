package microui.event;

import microui.core.base.Component;
import processing.event.MouseEvent;
 
public final class Scrolling {
	private final Component component;
    private float speed,velocity;
    private boolean isScrolling,reverse;
    
    public Scrolling(Component component) {
    	this.component = component;
    	velocity = .01f;
    }
    
    public final void init(final MouseEvent e) {
      	if(reverse) {
	        if(e.getCount() < 0) {
	          if(speed > 0) { speed = 0; }
	            speed += e.getCount()*velocity*10;
	        } else {
	          if(speed < 0) { speed = 0; }
	            speed += e.getCount()*velocity*10;
	        }
      	} else {
      		if(e.getCount() > 0) {
  	          if(speed > 0) { speed = 0; }
  	            speed -= e.getCount()*velocity*10;
  	        } else {
  	          if(speed < 0) { speed = 0; }
  	            speed -= e.getCount()*velocity*10;
  	        }
      	}
    }
    
    public float get() {
      if(speed > .01f) { speed -= velocity; }
      if(speed < -.01f) { speed += velocity; }
      
      isScrolling = (speed < -.01f || speed > .01f);
      
      if(component.isHolding()) { speed = 0; }
      
      if(isScrolling) { return speed; }
      return 0f;
    }
    
    public boolean isScrolling() { return isScrolling; }

	public final float getVelocity() {
		return velocity;
	}

	public final void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public final boolean isReverse() {
		return reverse;
	}

	public final void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
    
    
    
  }