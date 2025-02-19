package microUI.utils;

import processing.core.PApplet;
import processing.core.PImage;

public class Rectangle extends Component {
    public Image image;
    public Shadow shadow;
    public Ripples ripples;
    
    private boolean basicFX;
    private Event localEvent;
    
    public Rectangle(PApplet app,float x, float y, float w, float h) {
        super(app,x,y,w,h);
        image = new Image();
        setVisible(true);
        setBasicFX(true);
        shadow = new Shadow(app,this);
        ripples = new Ripples(this);
        localEvent = new Event(app);
      }
    
    
    public Rectangle(PApplet app) {
      this(app,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
    }
    
    @Override
    public void update() {
      localEvent.listen(this);
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
        
        if(localEvent.clicked()) { ripples.initAnim(); }
        ripples.draw();
        
    }
    
    public void setStyle(Rectangle rectangle) {
    	super.setStyle(rectangle);
    	image = rectangle.image;
    	shadow.setStyle(shadow);
    	basicFX = rectangle.basicFX;
    }
    
    public void setBasicFX(boolean basicFX) { this.basicFX = basicFX; }
    
    public BaseForm shadowDestroy() { shadow = null; return this; }
    
    public BaseForm eventDestroy() { event = null; return this; }
    
    public class Image {
      private PImage image;
      private boolean isVisible;
      public Color tint;
      private boolean tintUsed;
      
      public Image() {
    	  
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
    	  
      }
      
      public void draw() {
    	app.pushStyle();
        if(image != null && isVisible()) {
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
      
      public void setVisible(boolean isVisible) { this.isVisible = isVisible; }
      
      public boolean isVisible() { return isVisible; }
      
      public void setImage(PImage image) {
        this.image = image;
        setVisible(true);
      }
      
      public PImage getImage() { return image; }
      
      public boolean isLoaded() {
    	  return image != null;
      }
    }
}