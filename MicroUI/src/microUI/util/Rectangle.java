package microUI.util;

import processing.core.PApplet;

public class Rectangle extends Component {
    public final BaseImage image;
    public final Ripples ripples;
    public final Shadow shadow;
    public final Stroke stroke;
    
    private boolean basicFX;
    private final Event eventForRipples;
    
    
    public Rectangle(PApplet app,float x, float y, float w, float h) {
        super(app,x,y,w,h);
        
        image = new BaseImage(app) {
        	@Override
            public final void update() {
          	  app.pushStyle();
                if(isLoaded()) {
        	        if(basicFX && event != null) {
        	        	app.tint(event.inside() && !event.pressed() ? 94 : event.pressed() ? 0 : 128);
        	        } else {
        	        	app.tint(tint.get());
        	        }
                	app.image(image,x,y,w,h);
                }
               app.popStyle();
            }
        };
        image.setTransforms(this);
        
        setVisible(true);
        setBasicFX(true);
        shadow = new Shadow(app,this);
        stroke = new Stroke(app);
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
      
    	if(image.isLoaded()) {
    		image.draw();
    	} else {
    		stroke.get();
	        app.fill(fill.get());
	        app.rect(x,y,w,h);
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
    	shadow.setStyle(shadow);
    	basicFX = rectangle.getBasicFX();
    	stroke.set(rectangle.stroke);
    }
    
    public final void setBasicFX(final boolean basicFX) { this.basicFX = basicFX; }
    public final boolean getBasicFX() { return basicFX; }


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
	public float getH() {
		if(image != null) { image.setH(h); }
		return super.getH();
	}
    
}