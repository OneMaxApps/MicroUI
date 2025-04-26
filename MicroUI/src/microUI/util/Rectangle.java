package microUI.util;

import processing.core.PApplet;
import processing.core.PImage;

public class Rectangle extends Component {
    public final Image image;
    public final Ripples ripples;
    public final Shadow shadow;
    
    private boolean basicFX;
    private final Event eventForRipples;
    
    
    public Rectangle(PApplet app,float x, float y, float w, float h) {
        super(app,x,y,w,h);
        image = new Image();
        setVisible(true);
        setBasicFX(true);
        shadow = new Shadow(app,this);
        ripples = new Ripples(this);
        eventForRipples = new Event(app);
      }
    
    
    public Rectangle(PApplet app) {
      this(app,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
    }
    
    @Override
    public void update() {
      eventForRipples.listen(this);
      if(event != null) { event.listen(this); }
      
      if(shadow != null) { shadow.draw(); }
      
    	if(!image.isLoaded()) {
	        stroke.get();
	        app.fill(fill.get());
	        app.rect(x,y,w,h);
    	} else {
    		image.draw();
    	}
    	
        if(basicFX && event != null && !image.isLoaded()) {
        	app.noStroke();
        	app.fill(0,event.inside() && !event.pressed() ? 34 : event.pressed() ? 128 : 0);
        	app.rect(x,y,w,h);
        }
        
        if(eventForRipples.clicked()) { ripples.initAnim(); }
        ripples.draw();
        
    }
    
    public void setStyle(final Rectangle rectangle) {
    	super.setStyle(rectangle);
    	image.set(rectangle.image.get());
    	if(shadow != null) { shadow.setStyle(shadow); }
    	basicFX = rectangle.getBasicFX();
    }
    
    public final void setBasicFX(final boolean basicFX) { this.basicFX = basicFX; }
    public final boolean getBasicFX() { return basicFX; }
    
    public final BaseForm eventDestroy() { event = null; return this; }
    
    public final class Image extends View {
      private PImage image;
      public final Color tint;
      private boolean tintUsed;
      
      public Image() {
    	  super(Rectangle.this.app);
    	  tint = new Color(app) {
    		@Override
    		public void set(Color c) {
    			super.set(c);
    			tintUsed = true;
    		}
    		
    		@Override
    		public void setHEX(int hex) {
    			super.setHEX(hex);
    			tintUsed = true;
    		}
    		
    		@Override
    		public void set(float gray) {
    			super.set(gray);
    			tintUsed = true;
    		}
    		
    		@Override
    		public void set(float gray, float alpha) {
    			super.set(gray,alpha);
    			tintUsed = true;
    		}
    		
    		@Override
    		public void set(float red, float green, float blue) {
    			super.set(red,green,blue);
    			tintUsed = true;
    		}
    		
    		@Override
    		public void set(float red, float green, float blue, float alpha) {
    			super.set(red,green,blue,alpha);
    			tintUsed = true;
    		}
    	  };
    	  invisible();
      }
      
      @Override
      public final void update() {
    	  app.pushStyle();
          if(image != null) {
          	if(tintUsed) {
          		app.tint(tint.get());
          	} else {
  	        	if(basicFX && event != null) {
  	        		app.tint(event.inside() && !event.pressed() ? 94 : event.pressed() ? 0 : 128);
  	        	}
          	}
          	app.image(image,x,y,w,h);
          }
         app.popStyle();
      }
      
      public final void set(final PImage image) {
        this.image = image;
        visible();
      }
      public final PImage get() { return image; }
      public final boolean isLoaded() { return image != null; }
    }
}