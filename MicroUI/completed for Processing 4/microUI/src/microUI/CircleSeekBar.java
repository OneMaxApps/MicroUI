package microUI;

import static processing.core.PApplet.map;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.SQUARE;
import static processing.core.PConstants.TWO_PI;

import microUI.utils.Color;
import microUI.utils.Rectangle;
import microUI.utils.Scrolling;
import microUI.utils.Text;
import microUI.utils.Value;
import processing.core.PApplet;
import processing.core.PImage;

public class CircleSeekBar extends Rectangle {
	public Circle circle;
	public Value value;
	public Text title;
	public Scrolling scrolling;
	private PImage texture;
	
	public CircleSeekBar(float x, float y, float size) {
		super(x,y,size,size);
		fill.set(255,0);
		stroke.fill.set(255,0);
		shadowDestroy();
		setBasicFX(false);
		circle = new Circle();
		value = new Value(0,100,0);
		this.title = new Text("",x,y+h/2-h/4,w,h/2);
		scrolling = new Scrolling(event);
	}
	
	public CircleSeekBar(String text, float x, float y, float size) {
		this(x,y,size);
		this.title.setVisible(true);
		this.title.set(text);
	}
	
	public CircleSeekBar() {
		this(0,0,MicroUI.app.width*.1f+MicroUI.app.height*.1f);
		setPosition(MicroUI.app.width/2-getW()/2,MicroUI.app.height/2-getH()/2);
	}
	
	@Override
	public void draw() {
		if(isVisible()) {
			MicroUI.app.pushStyle();
			super.draw();
			circle.draw();
			if(title.isVisible()) {
				if(event.inside() || event.moved()) {
					MicroUI.app.fill(title.fill.get());
					MicroUI.app.textSize(title.getTextSize());
					MicroUI.app.textAlign(CENTER,CENTER);
					MicroUI.app.text(String.valueOf((int) value.getValue()), getX(),getY()+getH()/2-getH()/4,getW(),getH()/2);
				} else {
					title.setPosition(x, y+h/2-h/4);
					title.setSize(w, h/2);
					title.draw();
				}
			}
			
			if(event.inside() || scrolling.isScrolling()) {
				value.append(-scrolling.get());
			}
			MicroUI.app.popStyle();
		}
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
		if(title != null) { title.setPosition(x, y+h/2-h/4); }
	}
	
	@Override
	public void setSize(float w, float h) {
		float size = min(w,h);
		super.setSize(size,size);
		if(title != null) {
			title.setPosition(x, y+size/2-size/4);
			title.setSize(size,size/2);
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
			this.fill = new Color(MicroUI.app.color(34));
			arrow = new Arrow();
		}

		public void draw() {
			if(texture != null) {
				MicroUI.app.push();
				MicroUI.app.translate(x+w/2,y+h/2);
				MicroUI.app.rotate(map(value.getValue(), value.getMin(), value.getMax(), 0, TWO_PI-PI/4));
				MicroUI.app.image(texture, -w/2,-h/2,w,h);
				MicroUI.app.pop();
			}
			
			MicroUI.app.push();
			MicroUI.app.strokeCap(SQUARE);
			if(texture == null) {
				MicroUI.app.fill(fill.get());
				MicroUI.app.ellipse(x+w/2,y+h/2,w,h);
			}
			arrow.draw();
			MicroUI.app.pop();
			
			
			
			if(event.moved()) { value.append(MicroUI.app.pmouseY-MicroUI.app.mouseY); }
		}
		
		public class Arrow {
			private float weight;
			private boolean isVisible;

			public Color fill;
			
			public Arrow() {
				setWeight(8);
				fill = new Color(MicroUI.app.color(234));
				setVisible(true);
			}
			
			public void draw() {
				if(isVisible) {
					MicroUI.app.push();
					MicroUI.app.strokeCap(SQUARE);
					MicroUI.app.stroke(fill.get());
					MicroUI.app.strokeWeight(weight);
					MicroUI.app.translate(getX()+getW()/2,getY()+getH()/2);
					MicroUI.app.rotate(map(value.getValue(), value.getMin(), value.getMax(), PI/4, TWO_PI-PI/4));
					MicroUI.app.line(0,getH()/3,0,getH()/2);
					MicroUI.app.pop();
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
}
