package microUI;

import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.SQUARE;
import static processing.core.PConstants.TWO_PI;

import microUI.util.Color;
import microUI.util.Component;
import microUI.util.Scrollable;
import microUI.util.Scrolling;
import microUI.util.Stroke;
import microUI.util.Value;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

public final class CircleSeekBar extends Component implements Scrollable {
	public final Circle circle;
	public final Value value;
	public final Scrolling scrolling;
	public final Info info;
	public final Stroke stroke;
	private  PImage texture;

	public CircleSeekBar(PApplet app, float x, float y, float size) {
		super(app,x,y,size,size);
		fill.set(255,0);
		stroke = new Stroke(app);
		stroke.fill.set(255,0);
		circle = new Circle();
		value = new Value(0,100,0);
		scrolling = new Scrolling(event);
		info = new Info();
		scrolling.setReverse(true);
		visible();
	}
	
	public CircleSeekBar(PApplet app, String text, float x, float y, float size) {
		this(app,x,y,size);
	}
	
	public CircleSeekBar(PApplet app) {
		this(app,0,0,app.width*.1f+app.height*.1f);
		setPosition(app.width/2-getW()/2,app.height/2-getH()/2);
	}
	
	@Override
	public void update() {
		event.listen(this);
		app.pushStyle();
		circle.draw();
		if(event.inside() || event.moved()) {
			info.draw();
		}
		
			
		if(event.inside() || scrolling.isScrolling()) {
			value.append(-scrolling.get());
		}
		app.popStyle();
	}

	
	public PImage getTexture() {
		return texture;
	}

	public void setTexture(PImage texture) {
		this.texture = texture;
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x,y);
	}
	
	@Override
	public void setSize(float w, float h) {
		float size = min(w,h);
		super.setSize(size,size);
		if(info != null) {
			info.setSize((int) max(w,h)/3);
		}
	}
	
	
	
	@Override
	public void setTransforms(float x, float y, float w, float h) {
		float size = min(w,h);
		super.setTransforms(x, y, size,size);
	}

	public class Circle {
		public Color fill;
		public Arrow arrow;
		
		public Circle() {
			this.fill = new Color(34);
			arrow = new Arrow();
		}

		public void draw() {
			if(texture != null) {
				app.push();
				app.translate(x+w/2,y+h/2);
				app.rotate(map(value.get(), value.getMin(), value.getMax(), 0, TWO_PI-PI/4));
				app.image(texture, -w/2,-h/2,w,h);
				app.pop();
			}
			
			app.push();
			app.strokeCap(SQUARE);
			if(texture == null) {
				app.fill(fill.get());
				app.ellipse(x+w/2,y+h/2,w,h);
			}
			arrow.draw();
			app.pop();
			
			
			
			if(event.moved()) { value.append(app.pmouseY-app.mouseY); }
		}
		
		public class Arrow {
			private float weight;
			private boolean isVisible;

			public Color fill;
			
			public Arrow() {
				setWeight(8);
				fill = new Color(234);
				setVisible(true);
			}
			
			public void draw() {
				if(isVisible) {
					app.push();
					app.strokeCap(SQUARE);
					app.stroke(fill.get());
					app.strokeWeight(weight);
					app.translate(getX()+getW()/2,getY()+getH()/2);
					app.rotate(map(value.get(), value.getMin(), value.getMax(), PI/4, TWO_PI-PI/4));
					app.line(0,getH()/3,0,getH()/2);
					app.pop();
				}
			}
			
			public float getWeight() {
				return weight;
			}

			public void setWeight(float weight) {
				if(weight <= 0) { return; }
				this.weight = weight;
			}
			
			public boolean isVisible() {
				return isVisible;
			}

			public void setVisible(boolean isVisible) {
				this.isVisible = isVisible;
			}
		}
	}

	public final class Info {
		private int size;
		public final Color fill;
		
		public Info() {
			super();
			fill = new Color(255);
			setSize((int) max(w,h)/3);
		}
		
		private final void draw() {
			app.pushStyle();
			app.fill(fill.get());
			app.textSize(size <= 0 ? 1 : size);
			app.textAlign(CENTER,CENTER);
			app.text(String.valueOf((int) value.get()), getX(),getY()+getH()/2-getH()/4,getW(),getH()/2);
			app.popStyle();
		}
		
		public final void setSize(int size) { this.size = size; }
		public final int getSize() { return size; }
	}
	
	@Override
	public final void mouseWheel(MouseEvent e) {
	  scrolling.init(e);
	}
}