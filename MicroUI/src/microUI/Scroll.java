package microUI;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

import microUI.utils.Rectangle;
import microUI.utils.Scrolling;
import processing.core.PApplet;

public class Scroll extends Rectangle {
	  private float min,max,value;
	  private boolean isVerticalMode;
	  public Button button,buttonUp,buttonDown;
	  public Scrolling scrolling;
	  private float distOfMouseToButton;
	  
	public Scroll(float w, float h) {
		this(0,0,w,h);
		setMinMax(0,1);
		setValue(0);
	}  
	  
	public Scroll() {
		this(0,100,0);
		setTransforms(MicroUI.app.width*.2f,MicroUI.app.height*.45f,MicroUI.app.width*.6f,MicroUI.app.height*.1f);
	}

	public Scroll(int min, int max, int value) {
		this(0,0,0,0);
		setMinMax(min,max);
		setValue(value);
	}

	public Scroll(float x, float y, float w, float h) {
	    super(x,y,w,h);
	    
	    button = new Button(x,y,buttonsWeight(),h);
	    button.shadow.setVisible(false);
	    button.fill.set(32);
	    
	    scrolling = new Scrolling(event);
	    
	    buttonUp = new Button("+",x+w-buttonsWeight(),y,buttonsWeight(),h);
	    buttonDown = new Button("-",x,y,buttonsWeight(),h);
	    
	    buttonUp.shadow.setVisible(false);
	    buttonDown.shadow.setVisible(false);
	    
	    setBasicFX(false);
	    button.setBasicFX(false);
	    buttonUp.setBasicFX(false);
	    buttonDown.setBasicFX(false);
	    
	    buttonsTransformsUpdate();
	  }
	  
	  
	  public void draw() {
		if(isVisible()) {
		    super.draw();
		    buttonUp.draw(); if(buttonUp.event.pressed()) { appendValue(-1); }
		    buttonDown.draw(); if(buttonDown.event.pressed()) { appendValue(1); }
		    button.draw();
		    
		    if(button.event.moved()) {
		      if(!isVerticalMode) {
		        button.setX(constrain(MicroUI.app.mouseX+distOfMouseToButton,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight()));
		        value = constrain(map(MicroUI.app.mouseX+distOfMouseToButton,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight(),min,max),min,max);
		      } else {
		        button.setY(constrain(MicroUI.app.mouseY+distOfMouseToButton,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight()));
		        value = constrain(map(MicroUI.app.mouseY+distOfMouseToButton,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight(),max,min),min,max);
		      }
		    }
		    
		    if(event != null) {
		    	if(event.inside() || scrolling.isScrolling()) {
		    		appendValue(scrolling.get());
		    	}
		    }
		    if(isVerticalMode) {
		      if(button.event.inside()) { distOfMouseToButton = button.getY()-MicroUI.app.mouseY; }
		    } else {
		      if(button.event.inside()) { distOfMouseToButton = button.getX()-MicroUI.app.mouseX; }
		    }
		}
	  }
	  
	  
	  public void setMin(float min) {
		if(min > max) { return; }
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
	      setValue(constrain(getValue() - a,min,max));
	    }
	  }
	  
	  public float getValue() { return value; }
	  
	  public void setMinMax(float min, float max) {
	    setMin(min);
	    setMax(max);
	    if(min > max) { System.out.println("min value not must be more than max value"); }
	  }
	  
	  public void setVerticalMode(boolean v) {
	    if(isVerticalMode == v) { return; }
	    isVerticalMode = v;
	    
	    if(isVerticalMode) {
	      setSize(getH(),getW());
	      button.setSize(getW(),buttonsWeight());
	      button.setPosition(getX(),map(value,min,max,getY()+getH()-button.getH()-buttonsWeight(),getY()+buttonsWeight()));
	      
	      buttonUp.setPosition(getX(),getY());
	      buttonUp.setSize(getW(),buttonsWeight());
	      
	      buttonDown.setPosition(getX(),getY()+getH()-buttonsWeight());
	      buttonDown.setSize(getW(),buttonsWeight());
	      
	    } else {
	      setSize(getH(),getW());
	      button.setSize(getW()/10,getH());
	      button.setPosition(map(value,min,max,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight()),getY());
	      
	      buttonUp.setPosition(getX()+getW()-buttonsWeight(),getY());
	      buttonUp.setSize(buttonsWeight(),getH());
	      
	      buttonDown.setPosition(getX(),getY());
	      buttonDown.setSize(buttonsWeight(),getH());
	    }
	  
	  }
	  
	  public boolean isVerticalMode() { return isVerticalMode; }
	  
	  
	  @Override
	  public void setX(float x) {
	    super.setX(x);
	    update();
	  }
	  
	  @Override
	  public void setY(float y) {
	    super.setY(y);
	    update();

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
	    return isVerticalMode() ? getH()/10 : getW()/10;
	  }
	  
	  private void buttonsTransformsUpdate() {
	      if(button == null || buttonUp == null || buttonDown == null) { return; }
	      
	      if(isVerticalMode) {
	        button.setSize(getW(),buttonsWeight());
	        button.setPosition(getX(),constrain( map(value,min,max,getY()+getH()-button.getH()-buttonsWeight(),getY()+buttonsWeight()) ,getY(),getY()+getH()-button.getH()));
	        
	        buttonUp.setPosition(getX(),getY());
	        buttonUp.setSize(getW(),buttonsWeight());
	        
	        buttonDown.setPosition(getX(),getY()+getH()-buttonsWeight());
	        buttonDown.setSize(getW(),buttonsWeight());
	      } else {
	        button.setSize(buttonsWeight(),getH());
	        button.setPosition(constrain( map(value,min,max,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight()) ,getX(),getX()+getW()-button.getW()),getY());
	        
	        buttonUp.setPosition(getX()+getW()-buttonsWeight(),getY());
	        buttonUp.setSize(buttonsWeight(),getH());
	        
	        buttonDown.setPosition(getX(),getY());
	        buttonDown.setSize(buttonsWeight(),getH());
	      }
	  }
	  
	  private void updateDataOfValue() {
	    if(button == null) { return; }
	    if(!isVerticalMode) {
	      value = constrain(map(MicroUI.app.mouseX-button.getW()/2,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight(),min,max),min,max);
	    } else {
	      value = constrain(map(MicroUI.app.mouseY-button.getH()/2,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight(),min,max),min,max);
	    }
	  }
	  
	  private void update() {
	    setSize(getW(),getH());
	    buttonsTransformsUpdate();
	  }
	  
	}