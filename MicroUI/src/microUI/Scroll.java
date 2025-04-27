package microUI;

import static processing.core.PApplet.abs;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

import microUI.util.Rectangle;
import microUI.util.Scrollable;
import microUI.util.Scrolling;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Scroll extends Rectangle implements Scrollable {
	  public Button button,buttonPlus,buttonMinus;
	  public Scrolling scrolling;
	  private float min,max,value;
	  private boolean isVerticalMode;
	  private float distOfMouseToButton;
	  
	public Scroll(PApplet app, float w, float h) {
		this(app,0,0,w,h);
		setMinMax(0,100);
		setValue(0);
	}  
	  
	public Scroll(PApplet app) {
		this(app,0,100,0);
		setTransforms(app.width*.2f,app.height*.45f,app.width*.6f,app.height*.1f);
	}

	public Scroll(PApplet app, int min, int max, int value) {
		this(app, 0,0,0,0);
		setMinMax(min,max);
		setValue(value);
	}

	public Scroll(PApplet app, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    ripples.setVisible(false);
	    
	    fill.set(28);
	    
	    button = new Button(app,x,y,buttonsWeight(),h) {{
	    	fill.set(12);
	    	shadow.setVisible(false);
	    	ripples.setVisible(false);
	    	shadow.invisible();
	    }};
	    
	    scrolling = new Scrolling(event);
	    
	    buttonPlus = new Button(app,"+",x+w-buttonsWeight(),y,buttonsWeight(),h) {{
	    	shadow.setVisible(false);
	    	ripples.setVisible(false);
	    	shadow.invisible();
	    }};
	    
	    buttonMinus = new Button(app,"-",x,y,buttonsWeight(),h){{
	    	shadow.setVisible(false);
	    	ripples.setVisible(false);
	    	shadow.invisible();
    	}};
	    
	    setBasicFX(false);
	    
	    buttonsTransformsUpdate();
	  }
	  
	  @Override
	  public void update() {
		    super.update();
		    buttonPlus.draw(); if(buttonPlus.event.pressed()) { appendValue(1); }
		    buttonMinus.draw(); if(buttonMinus.event.pressed()) { appendValue(-1); }
		    button.draw();
		    
		    if(button.event.moved()) {
		      if(!isVerticalMode) {
		        button.setX(constrain(app.mouseX+distOfMouseToButton,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight()));
		        value = constrain(map(app.mouseX+distOfMouseToButton,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight(),min,max),min,max);
		      } else {
		        button.setY(constrain(app.mouseY+distOfMouseToButton,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight()));
		        value = constrain(map(app.mouseY+distOfMouseToButton,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight(),max,min),min,max);
		      }
		    }
		    
		    if(event != null) {
		    	if(event.inside() || scrolling.isScrolling()) {
		    		appendValue(scrolling.get());
		    	}
		    }
		    if(isVerticalMode) {
		      if(button.event.inside()) { distOfMouseToButton = button.getY()-app.mouseY; }
		    } else {
		      if(button.event.inside()) { distOfMouseToButton = button.getX()-app.mouseX; }
		    }
	  }
	  
	  
	  public void setMin(float min) {
		if(min > value) { value = min; }
	    this.min = min;
	    updateDataOfValue();
	    buttonsTransformsUpdate();
	  }
	  public float getMin() { return min; }
	  
	  public void setMax(float max) {
	    if(value > max) { value = max; }
		this.max = max;
	    updateDataOfValue();
	    buttonsTransformsUpdate();
	  }
	  public float getMax() { return max; }
	  
	  public void setValue(float value) {
		if(value < min || value > max) { return; }
	    this.value = value;
	    buttonsTransformsUpdate();
	  }
	  
	  public void appendValue(float a) {
	    if(a < -.01f || a > .01f) {
	      setValue(constrain(getValue() + a,min,max));
	    }
	  }
	  
	  public final void autoScrollAppending() {
		  appendValue(scrolling.get());
	  }
	  
	  public float getValue() { return value; }
	  
	  public void setMinMax(float min, float max) {
	    setMin(min);
	    setMax(max);
	  }
	  
	  public Scroll setVerticalMode(boolean v) {
	    if(isVerticalMode == v) { return this; }
	    isVerticalMode = v;
	    
	    if(isVerticalMode) {
	    	 
	      setSize(getH(),getW());
	      button.setSize(getW(),buttonsWeight());
	      button.setPosition(getX(),map(value,min,max,getY()+getH()-button.getH()-buttonsWeight(),getY()+buttonsWeight()));
	      
	      buttonPlus.setPosition(getX(),getY());
	      buttonPlus.setSize(getW(),buttonsWeight());
	      
	      buttonMinus.setPosition(getX(),getY()+getH()-buttonsWeight());
	      buttonMinus.setSize(getW(),buttonsWeight());
	      
	    } else {
	      setSize(getH(),getW());
	      button.setSize(getW()/10,getH());
	      button.setPosition(map(value,min,max,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight()),getY());
	      
	      buttonPlus.setPosition(getX()+getW()-buttonsWeight(),getY());
	      buttonPlus.setSize(buttonsWeight(),getH());
	      
	      buttonMinus.setPosition(getX(),getY());
	      buttonMinus.setSize(buttonsWeight(),getH());
	    }
	    return this;
	  }
	  
	  public boolean isVerticalMode() { return isVerticalMode; }
	  
	  
	  @Override
	  public void setX(float x) {
	    super.setX(x);
	    updateForm();
	  }
	  
	  @Override
	  public void setY(float y) {
	    super.setY(y);
	    updateForm();

	  }
	  
	  @Override
	  public void setW(float w) {
	    super.setW(w);  
	    buttonsTransformsUpdate();
	  }
	  
	  @Override
	  public void setH(float h) {
	    super.setH(h);
	    buttonsTransformsUpdate();
	  }
	  
	  private float buttonsWeight() {
	    return isVerticalMode() ? min(getH()/constrain(abs(max(abs(min),abs(max))),2,20),getH()/5) : min(getW()/constrain(abs(max(abs(min),abs(max))),2,20),getW()/5);
	  }
	  
	  private void buttonsTransformsUpdate() {
	      if(button == null || buttonPlus == null || buttonMinus == null) { return; }
	      
	      if(isVerticalMode) {
	        button.setSize(getW(),buttonsWeight());
	        
	        if(min != max) {
	        	button.setPosition(getX(),constrain( map(value,min,max,getY()+getH()-button.getH()-buttonsWeight(),getY()+buttonsWeight()) ,getY(),getY()+getH()-button.getH()));
	        }
	        
	        buttonPlus.setPosition(getX(),getY());
	        buttonPlus.setSize(getW(),buttonsWeight());
	        
	        buttonMinus.setPosition(getX(),getY()+getH()-buttonsWeight());
	        buttonMinus.setSize(getW(),buttonsWeight());
	      } else {
	        button.setSize(buttonsWeight(),getH());
	        if(min != max) {
	        	button.setPosition(constrain( map(value,min,max,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight()),getX(),getX()+getW()-button.getW()),  getY());
	        }
	        buttonPlus.setPosition(getX()+getW()-buttonsWeight(),getY());
	        buttonPlus.setSize(buttonsWeight(),getH());
	        
	        buttonMinus.setPosition(getX(),getY());
	        buttonMinus.setSize(buttonsWeight(),getH());
	      }
	  }
	  
	  private void updateDataOfValue() {
	    if(button == null) { return; }
	    if(min == max || button.getW() == 0 || button.getH() == 0) { return; }
	    if(!isVerticalMode) {
	      value = constrain(map(app.mouseX-button.getW()/2,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight(),min,max),min,max);
	    } else {
	      value = constrain(map(app.mouseY-button.getH()/2,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight(),min,max),min,max);
	    }
	  }
	  
	  private void updateForm() {
	    setSize(getW(),getH());
	    buttonsTransformsUpdate();
	  }
	  
	  @Override
	  public final void mouseWheel(MouseEvent e) {
		  scrolling.init(e);
	  }
	  
	}