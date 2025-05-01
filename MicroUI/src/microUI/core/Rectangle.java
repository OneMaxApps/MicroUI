package microUI.core;

import microUI.event.Event;
import microUI.util.Color;
import microUI.util.Stroke;

public abstract class Rectangle extends AbstractRectangle {
    public final Stroke stroke;
    public final Color fill;
    public final Event event;
    
    public Rectangle(float x, float y, float w, float h) {
        super(x,y,w,h);
        visible();
  
        stroke = new Stroke();
        fill = new Color(44);
        event = new Event();
      }
    
    
    public Rectangle() {
      this(app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
    }
    
    @Override
    public void update() {
      event.listen(this);
      
      stroke.get();
	  fill.use(app);
	  app.rect(x,y,w,h);

 
    }
    
    public void setStyle(final Rectangle rectangle) {
    	stroke.set(rectangle.stroke);
    	fill.setHEX(rectangle.fill.get());
    }
	
}