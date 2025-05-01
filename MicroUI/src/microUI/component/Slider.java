package microUI.component;

import static processing.core.PApplet.abs;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PApplet.max;

import microUI.core.Component;
import microUI.event.Scrollable;
import microUI.event.Scrolling;
import processing.event.MouseEvent;

/*
public class Slider extends Component implements Scrollable {
	  public Button button;
	  public Rectangle level;
	  public Scrolling scrolling;
	  private float distOfMouseToButton;
	  private float min,max,value;
	  private boolean isVerticalMode,showText;
	  
	  public Slider(float x, float y, float w, float h) {
		    super(x,y,w,h);

		    setMinMax(0,100);
		    
		    button = new Button(x,y,buttonsWeight(),h) {{
		     ripples.setVisible(false);
		     fill.set(32);
		    }};
		    
		    
		    level = new Rectangle(x,y,button.getX()-getX(),h) {{
		     fill.set(0,128,234,234);
		     event.setEnable(false);
		    }};
		    
		    scrolling = new Scrolling(event);
		    
		    showText(true);
		    
	  }
	  
	  public Slider() { this(app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f); }
	  
	  public Slider(float w, float h) { this(0,0,w,h); }

	  
	  public Slider(float min, float max, float value) {
		this(app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
		this.min = min;
		this.max = max;
		this.value = value;
	  }
	  
	  
	  
	  @Override
	  public void update() {
		    // super.update();
		    // level.draw();
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
		    
			if(event.inside() || scrolling.isScrolling()) {
			  appendValue(scrolling.get());
			
		    
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
	  
	  
	  public void setMin(float min) {
		if(min > max) { return; }
		if(value < min) { value = min; }
	    this.min = min;
	    updateForm();

	  }
	  public float getMin() { return min; }
	  
	  public void setMax(float max) {
	    this.max = max;
	    if(value > max) { value = max; }
	    updateForm();
	  }
	  public float getMax() { return max; }
	  
	  public void setValue(float value) {
	    if(value < min || value > max) { return; }
		this.value = value;
		updateForm();

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
	    
	    updateForm();
	  }
	  
	  public Slider setVerticalMode(boolean v) {
	    if(isVerticalMode == v) { return this; }
	    isVerticalMode = v;
	    
	    if(isVerticalMode) {
	      setSize(getH(),getW());
	      button.setSize(getW(),getH()/constrain(abs(max),2,20));
	      button.setPosition(getX(),map(value,min,max,getY()+getH()-button.getH(),getY()));
	    } else {
	      setSize(getH(),getW());
	      button.setSize(getW()/constrain(abs(max),2,20),getH());
	      button.setPosition(map(value,min,max,getX(),getX()+getW()-button.getW()),getY());
	    }
	    
	    updateLevelTransforms();
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
		    return isVerticalMode() ? getH()/constrain(abs(max(abs(min),abs(max))),2,20) : getW()/constrain(abs(max(abs(min),abs(max))),2,20);
	  }
	  
	  private void buttonTransformsUpdate() {
	      if(button == null) { return; }
	      
	      if(isVerticalMode) {
	        button.setSize(getW(),getH()/constrain(abs(max),2,20));
	        button.setPosition(getX(),constrain( map(value,min,max,getY()+getH()-button.getH(),getY()) ,getY(),getY()+getH()-button.getH()));
	      } else {
	        button.setSize(getW()/constrain(abs(max),2,20),getH());
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
	  
	  private void updateForm() {
	    setSize(getW(),getH());
	    buttonTransformsUpdate();
	  }
	  
	  
	  @Override
	  public final void mouseWheel(MouseEvent e) {
		  scrolling.init(e);
	  }
	}*/