package microUI.utils;

import processing.core.PApplet;
import processing.core.PImage;

public class Rectangle extends Form {
    public Corners corners;
    public Image image;
    private boolean basicFX;
    public Shadow shadow;
  
    public Rectangle(PApplet app) {
      this(app,0,0,10,10);
    }
    
    public Rectangle(PApplet app, float x, float y, float w, float h) {
      super(app,x,y,w,h);
      corners = new Corners();
      image = new Image();
      setVisible(true);
      setBasicFX(true);
      shadow = new Shadow(app,this);
    }
    
    @Override
    public void draw() {
      if(event != null) { event.listen(this); }
      if(shadow != null) { shadow.draw(); }
      
      if(isVisible()) {
    	if(!image.isLoaded()) {
	        stroke.get();
	        app.fill(fill.get());
	        app.rect(x,y,w,h,corners.corns[0],corners.corns[1],corners.corns[2],corners.corns[3]);
    	} else {
    		image.draw();
    	}
    	
        if(basicFX && event != null && !image.isLoaded()) {
        	app.fill(0,event.inside() && !event.pressed() ? 34 : event.pressed() ? 128 : 0);
        	app.rect(x,y,w,h,corners.get()[0],corners.get()[1],corners.get()[2],corners.get()[3]);
        }
        
        
      }
    }
    
    public void setBasicFX(boolean basicFX) { this.basicFX = basicFX; }
    
    public BaseForm shadowDestroy() { shadow = null; return this; }
    
    public BaseForm eventDestroy() { event = null; return this; }
    
    public class Corners {
      private float[] corns = new float[4];
      
      public void set(float corns) {
        for(int i = 0; i < this.corns.length; i++) { this.corns[i] = corns; }
      }
      
      public void set(float c, float c1, float c2, float c3) {
        corns[0] = c;
        corns[1] = c1;
        corns[2] = c2;
        corns[3] = c3;
      }
      
      public void set(int[] corns) {
        set(corns[0],corns[1],corns[2],corns[3]);
      }
      
      public float[] get() { return corns; }
      public float get(int index) { if(index < 0 || index > 3) { throw new IndexOutOfBoundsException("index out of corners count exception"); } return corns[index]; }
    }
    
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
        if(corners != null) { corners.set(0); }
        setVisible(true);
      }
      
      public PImage getImage() { return image; }
      
      public boolean isLoaded() {
    	  return image != null;
      }
    }
}