package microUI.util;

import microUI.core.BaseImage;
import microUI.core.Component;

public abstract class Rectangle extends Component {
    public final BaseImage image;
    public final Stroke stroke;
    
    public Rectangle(float x, float y, float w, float h) {
        super(x,y,w,h);
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
    
    
    public Rectangle() {
      this(app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
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
	public void inTransforms() {
		if(image != null) { image.setTransforms(this); }
	}
    
	
}