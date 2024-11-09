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
	  
	public Scroll(PApplet app) {
		this(app,0,100,0);
	}

	public Scroll(PApplet app, int min, int max, int value) {
		this(app,0,0,0,0);
		setMinMax(min,max);
		setValue(value);
	  }

	public Scroll(PApplet app, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    
	    button = new Button(app,x,y,buttonsWeight(),h);
	    button.shadow.setVisible(false);
	    
	    scrolling = new Scrolling(event);
	    
	    buttonUp = new Button(app,"+",x+w-buttonsWeight(),y,buttonsWeight(),h);
	    buttonDown = new Button(app,"-",x,y,buttonsWeight(),h);
	    
	    buttonUp.shadow.setVisible(false);
	    buttonDown.shadow.setVisible(false);
	    
	    buttonsTransformsUpdate();
	  }
	  
	  
	  public void draw() {
	    super.draw();
	    buttonUp.draw(); if(buttonUp.event.pressed()) { appendValue(-1); }
	    buttonDown.draw(); if(buttonDown.event.pressed()) { appendValue(1); }
	    button.draw();
	    
	    if(button.event.moving()) {
	      if(!isVerticalMode) {
	        button.setX(constrain(app.mouseX+distOfMouseToButton,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight()));
	        value = constrain(map(app.mouseX+distOfMouseToButton,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight(),min,max),min,max);
	      } else {
	        button.setY(constrain(app.mouseY+distOfMouseToButton,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight()));
	        value = constrain(map(app.mouseY+distOfMouseToButton,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight(),max,min),min,max);
	      }
	    }
	    
	    if(event.inside() || scrolling.isScrolling()) {
	      appendValue(scrolling.get());
	    }
	    
	    if(isVerticalMode) {
	      if(button.event.inside()) { distOfMouseToButton = button.getY()-app.mouseY; }
	    } else {
	      if(button.event.inside()) { distOfMouseToButton = button.getX()-app.mouseX; }
	    }
	  }
	  
	  
	  public void setMin(float min) {
	    this.min = min;
	    updateDataOfValue();
	    buttonsTransformsUpdate();
	  }
	  public float getMin() { return min; }
	  
	  public void setMax(float max) {
	    this.max = max;
	    updateDataOfValue();
	    buttonsTransformsUpdate();
	  }
	  public float getMax() { return max; }
	  
	  public void setValue(float value) {
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
	      value = constrain(map(app.mouseX-button.getW()/2,getX()+buttonsWeight(),getX()+getW()-button.getW()-buttonsWeight(),min,max),min,max);
	    } else {
	      value = constrain(map(app.mouseY-button.getH()/2,getY()+buttonsWeight(),getY()+getH()-button.getH()-buttonsWeight(),min,max),min,max);
	    }
	  }
	  
	  private void update() {
	    setSize(getH(),getW());
	    buttonsTransformsUpdate();
	  }
	  
	}