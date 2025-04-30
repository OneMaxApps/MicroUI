package microUI.util;

import microUI.core.BaseImage;
import microUI.core.Component;
import microUI.effect.Ripples;
import microUI.effect.Shadow;
import microUI.event.Event;
import processing.core.PApplet;

public abstract class Rectangle extends Component {
    public final BaseImage image;
    public final Stroke stroke;
    
    public Rectangle(PApplet app,float x, float y, float w, float h) {
        super(app,x,y,w,h);
        visible();
        
        image = new BaseImage(app) {
        	@Override
            public final void update() {
          	  app.pushStyle();
        	        app.tint(tint.get());
                	app.image(image,x,y,w,h);
              app.popStyle();
            }
        };
        image.setTransforms(this);
  
        stroke = new Stroke(app);
        
      }
    
    
    public Rectangle(PApplet app) {
      this(app,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
    }
    
    @Override
    public void update() {
      event.listen(this);
      
      if(image.isLoaded()) { image.draw(); } else {
		 stroke.get();
	     fill.use(app);
	     app.rect(x,y,w,h);
    	}
 
    }
    
    public void setStyle(final Rectangle rectangle) {
    	super.setStyle(rectangle);
    	image.set(rectangle.image.get());
    	stroke.set(rectangle.stroke);
    }

	@Override
	public void setX(float x) {
		if(image != null) { image.setX(x); }
		super.setX(x);
	}


	@Override
	public void setY(float y) {
		if(image != null) { image.setY(y); }
		super.setY(y);
	}


	@Override
	public void setW(float w) {
		if(image != null) { image.setW(w); }
		super.setW(w);
	}


	@Override
	public void setH(float h) {
		if(image != null) { image.setH(h); }
		super.setH(h);
	}
    
}