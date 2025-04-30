package microUI.component;

import microUI.core.AbstractButton;
import microUI.util.Text;
import processing.core.PApplet;

public class Button extends AbstractButton {
	  public Text text;
	  
	  public Button(PApplet app, String text, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    
	    this.text = new Text(app,text.toUpperCase(),x,y,w,h);
	    this.text.setUpperCaseStyle(true);
	    
	  }
	  public Button(PApplet app, float x, float y, float w, float h) { this(app,"",x,y,w,h); }
	  public Button(PApplet app, String titie) { this(app,titie,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f); } 
	  public Button(PApplet app) {
		  this(app,"Button");
	  }
	  
	  @Override
	  public void update() {
		 super.update();
		 text.draw();
		 
	  }
	  
	  public void setStyle(Button button) {
	    	super.setStyle(button);
	    	text = button.text;
	  }
	  
	  
	  @Override
	  public void setX(float x) {
		super.setX(x);
		transforming();
	  }
	  @Override
	  public void setY(float y) {
		super.setY(y);
		transforming();
	  }
	  @Override
      public void setW(float w) {
        super.setW(w);
        transforming();
      }
      @Override
      public void setH(float h) {
	    super.setH(h);
	    transforming();
	  }
		  
      public final void transforming() {
	  if(text == null) { return; }
	  text.setTransforms(this);
      }
		
	}