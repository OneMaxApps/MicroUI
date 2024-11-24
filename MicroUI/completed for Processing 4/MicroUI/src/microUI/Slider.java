package microUI;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

import microUI.utils.Rectangle;
import microUI.utils.Scrolling;
import processing.core.PApplet;

public class Slider extends Rectangle {
	  private float min,max,value;
	  private boolean isVerticalMode,showText;
	  public Button button;
	  public Rectangle level;
	  public Scrolling scrolling;
	  private float distOfMouseToButton;
	  
	  public Slider() { this(MicroUI.app.width*.3f,MicroUI.app.height*.45f,MicroUI.app.width*.4f,MicroUI.app.height*.1f); }
	  
	  public Slider(float w, float h) { this(0,0,w,h); }

	  
	  public Slider(float min, float max, float value) {
		this(MicroUI.app.width*.3f,MicroUI.app.height*.45f,MicroUI.app.width*.4f,MicroUI.app.height*.1f);
		this.min = min;
		this.max = max;
		this.value = value;
	  }
	  
	  public Slider(float x, float y, float w, float h) {
	    super(MicroUI.app,x,y,w,h);

	    setMinMax(0,1);
	    
	    button = new Button(x,y,buttonsWeight(),h);
	    button.shadow.setVisible(false);
	    button.setBasicFX(false);
	    button.fill.set(32);
	    setBasicFX(false);
	    
	    level = new Rectangle(app,x,y,button.getX()-getX(),h);
	    level.fill.setHEX(app.color(0,128,234,234));
	    level.shadowDestroy();
	    level.eventDestroy();
	    
	    scrolling = new Scrolling(event);
	    
	    showText(true);
	  }
	  
	  
	  public void draw() {
		if(isVisible()) {
		    super.draw();
		    level.draw();
		    button.draw();
		    
		    if(button.event.moved()) {
		      if(!isVerticalMode) {
		        button.setX(constrain(app.mouseX+distOfMouseToButton,getX(),getX()+getW()-button.getW()));
		        value = constrain(map(app.mouseX+distOfMouseToButton,getX(),getX()+getW()-button.getW(),min,max),min,max);
		        level.setPosition(getX(),button.getY());
		        level.setSize(button.getX()-getX(),getH());
		      } else {
		        button.setY(constrain(app.mouseY+distOfMouseToButton,getY(),getY()+getH()-button.getH()));
		        value = constrain(map(app.mouseY+distOfMouseToButton,getY(),getY()+getH()-button.getH(),max,min),min,max);
		        level.setPosition(getX(),button.getY()+button.getH());
		        level.setSize(getW(),getY()+getH()-button.getY()-button.getH());
		      }
		    }
		    
		    if(event != null) {
			    if(event.inside() || scrolling.isScrolling()) {
			      appendValue(scrolling.get());
			    }
		    
		    if(showText) {
		      if(event.inside() || scrolling.isScrolling() || button.event.moved()) { button.text.set((int) value); } else { button.text.clear(); }
		    }
		    
		    }
		    
		    if(isVerticalMode) {
		      if(button.event.inside()) { distOfMouseToButton = button.getY()-app.mouseY; }
		    } else {
		      if(button.event.inside()) { distOfMouseToButton = button.getX()-app.mouseX; }
		    }
		}
	  }
	  
	  
	  public void setMin(float min) {
		if(min > max) { return; }
		if(value < min) { value = min; }
	    this.min = min;
	    update();

	  }
	  public float getMin() { return min; }
	  
	  public void setMax(float max) {
	    this.max = max;
	    if(value > max) { value = max; }
	    update();
	  }
	  public float getMax() { return max; }
	  
	  public void setValue(float value) {
	    if(value < min || value > max) { return; }
		this.value = value;
	    update();

	  }
	  
	  public void appendValue(float a) {
	    if(a < -.01f || a > .01f) {
	      setValue(constrain(getValue() + a,min,max));
	    }
	  }
	  
	  public float getValue() { return value; }
	  
	  public void setMinMax(float min, float max) {
	    this.min = min;
	    this.max = max;
	    if(value < min) { value = min; }
	    if(value > max) { value = max; }
	    
	    update();
	    if(min > max) { System.out.println("min value not must be more than max value"); }
	  }
	  
	  public void setVerticalMode(boolean v) {
	    if(isVerticalMode == v) { return; }
	    isVerticalMode = v;
	    
	    if(isVerticalMode) {
	      setSize(getH(),getW());
	      button.setSize(getW(),getH()/10);
	      button.setPosition(getX(),map(value,min,max,getY()+getH()-button.getH(),getY()));
	    } else {
	      setSize(getH(),getW());
	      button.setSize(getW()/10,getH());
	      button.setPosition(map(value,min,max,getX(),getX()+getW()-button.getW()),getY());
	    }
	    
	    updateLevelTransforms();
	  
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
	    buttonTransformsUpdate();
	    updateLevelTransforms();
	  }
	  
	  @Override
	  public void setH(float h) {
	    super.setH(h);
	    buttonTransformsUpdate();
	    updateLevelTransforms();
	  }
	  
	  public void showText(boolean s) { showText = s; }
	  
	  private float buttonsWeight() {
	    return isVerticalMode() ? getH()/10 : getW()/10;
	  }
	  
	  private void buttonTransformsUpdate() {
	      if(button == null) { return; }
	      
	      if(isVerticalMode) {
	        button.setSize(getW(),getH()/10);
	        button.setPosition(getX(),constrain( map(value,min,max,getY()+getH()-button.getH(),getY()) ,getY(),getY()+getH()-button.getH()));
	      } else {
	        button.setSize(getW()/10,getH());
	        button.setPosition(constrain(map(value,min,max,getX(),getX()+getW()-button.getW()),getX(),getX()+getW()-button.getW()),getY());
	      }
	  }
	  
	  private void updateLevelTransforms() {
	    if(button == null) { return; }
	    
	    if(isVerticalMode) {
	      level.setPosition(getX(),button.getY()+button.getH());
	      level.setSize(getW(),getY()+getH()-button.getY()-button.getH());
	    } else {
	      level.setPosition(getX(),button.getY());
	      level.setSize(button.getX()-getX(),getH());
	    }
	  }
	  
	  private void update() {
	    setSize(getW(),getH());
	    buttonTransformsUpdate();
	  }
	  
	}