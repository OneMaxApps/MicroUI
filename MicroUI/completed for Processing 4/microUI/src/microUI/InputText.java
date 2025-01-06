package microUI;

import microUI.utils.BaseView;
import processing.core.PApplet;
import processing.core.PGraphics;


// all lines must be inside the one PGraphics object
public class InputText extends BaseView {
	private PGraphics g;
	
	public InputText(PApplet app, float x, float y, float w, float h) {
		super(app,x,y,w,h);
		g = app.createGraphics(200,200);
		
	}
	
	public InputText(PApplet app) {
		this(app,100,100,app.width-200,app.height-200);
	}

	@Override
	public void draw() {
		g.beginDraw();
			g.pushStyle();
			g.noStroke();
			g.fill(fill.getRed(),fill.getGreen(),fill.getBlue(),fill.getAlpha());
			g.rect(0,0,w,h);
			g.popStyle();
		g.endDraw();
		
		app.image(g,x, y, w, h);
	}
	
}