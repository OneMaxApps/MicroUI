package microUI.component;

import microUI.core.AbstractButton;
import microUI.util.Text;

public class Button extends AbstractButton {
	  public final Text text;
	  
	  public Button(String text, float x, float y, float w, float h) {
	    super(x,y,w,h);
	    
	    this.text = new Text(text.toUpperCase(),x,y,w,h);
	    this.text.setUpperCaseStyle(true);
	    this.text.setAutoResize(true);
	  }
	  public Button(float x, float y, float w, float h) { this("",x,y,w,h); }
	  public Button(String titie) { this(titie,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f); } 
	  public Button() {
		  this("BUTTON");
	  }
	  
	  @Override
	  public void update() {
		 super.update();
		 text.draw();
		 
	  }
	  
	  public void setStyle(Button otherButton) {
	    	super.setStyle(otherButton);
	    	text.set(otherButton.text);
	  }
	  
	  @Override
	  public void inTransforms() {
		    super.inTransforms();
			if(text != null) { text.setTransforms(this); }
	  }
      
	}