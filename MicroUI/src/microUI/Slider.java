package microUI;

import microUI.utils.Rectangle;
import microUI.utils.Scrolling;
import processing.core.PApplet;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

public class Slider extends Rectangle {
	  private float min,max,value;
	  private boolean isVerticalMode,showText;
	  public Button button;
	  public Rectangle level;
	  public Scrolling scrolling;
	  private float distOfMouseToButton;
	  
	  public Slider(PApplet app) { this(app,0,0,0,0); }
	  
	  public Slider(PApplet app, float min, float max, float value) {
		this(app);
		this.min = min;
		this.max = max;
		this.value = value;
	  }
	  
	  public Slider(PApplet app, float x, float y, float w, float h) {
	    super(app,x,y,w,h);

	    setMinMax(0,1);
	    
	    button = new Button(app,x,y,buttonsWeight(),h);
	    button.shadow.setVisible(false);
	    
	    level = new Rectangle(app,x,y,button.getX()-getX(),h);
	    level.fill.set(app.color(0,128,234,234));
	    level.shadowDestroy();
	    
	    scrolling = new Scrolling(event);
	    
	    showText(true);
	  }
	  
	  
	  public void draw() {
	    super.draw();
	    level.draw();
	    button.draw();
	    
	    if(button.event.moving()) {
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
	    
	    if(event.inside() || scrolling.isScrolling()) {
	      appendValue(scrolling.get());
	    }
	    
	    if(showText) {
	      if(event.inside() || scrolling.isScrolling() || button.event.moving()) { button.text.set((int) value); } else { button.text.clear(); }
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
	    buttonTransformsUpdate();
	  }
	  public float getMin() { return min; }
	  
	  public void setMax(float max) {
	    this.max = max;
	    updateDataOfValue();
	    buttonTransformsUpdate();
	  }
	  public float getMax() { return max; }
	  
	  public void setValue(float value) {
	    this.value = value;
	    buttonTransformsUpdate();
	    updateLevelTransforms();
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
	    updateLevelTransforms();

	  }
	  
	  @Override
	  public void setY(float y) {
	    super.setY(y);
	    update();
	    updateLevelTransforms();

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
	  
	  private void updateDataOfValue() {
	    if(button == null) { return; }
	    if(!isVerticalMode) {
	      value = constrain(map(app.mouseX-button.getW()/2,getX(),getX()+getW()-button.getW(),min,max),min,max);
	    } else {
	      value = constrain(map(app.mouseY-button.getH()/2,getY(),getY()+getH()-button.getH(),min,max),min,max);
	    }
	  }
	  
	  private void update() {
	    setSize(getH(),getW());
	    buttonTransformsUpdate();
	  }
	  
	}