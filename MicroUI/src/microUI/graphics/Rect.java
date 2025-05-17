package microUI.graphics;

import microUI.core.UnsafeBounds;

public final class Rect extends UnsafeBounds {
    public final Stroke stroke;
    public final Color fill;
    
    public Rect(float x, float y, float w, float h) {
        super(x,y,w,h);
        visible();
  
        stroke = new Stroke();
        fill = new Color(44);
      }
    
    
    public Rect() {
      this(app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
    }
    
    @Override
    public void update() {
      app.pushStyle();
      stroke.get();
	  fill.use();
	  app.rect(x,y,w,h);
	  app.popStyle();
 
    }
    
    public void setStyle(final Rect otherRect) {
    	stroke.set(otherRect.stroke);
    	fill.setHEX(otherRect.fill.get());
    }
	
}