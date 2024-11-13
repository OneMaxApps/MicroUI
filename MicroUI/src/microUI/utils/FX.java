package microUI.utils;

import static processing.core.PApplet.map;

import java.util.ArrayList;

import microUI.Button;
import microUI.CheckBox;
import microUI.CircleSeekBar;
import microUI.Scroll;
import microUI.Slider;
import processing.core.PApplet;

public class FX {
	private PApplet app;
	private int duration;
	public Buttons buttons;
	public CheckBoxs checkBoxs;
	public CircleSeekBars circleSeekBars;
	public Scrolls scrolls;
	public Sliders sliders;
	private boolean basicFX;
	private ArrayList<Rectangle> elementList;
	private ArrayList<Integer> posList;
	
	public FX(PApplet app) {
		this.app = app;
		buttons = new Buttons(app);
		checkBoxs = new CheckBoxs(app);
		circleSeekBars = new CircleSeekBars(app);
		scrolls = new Scrolls(app);
		sliders = new Sliders(app);
		duration = 10;
		basicFX = true;
		elementList = new ArrayList<Rectangle>();
		posList = new ArrayList<Integer>();
	}
	
	public void init() {
		for(int i = 0; i < elementList.size(); i++) {
			
		if(elementList.get(i).event.moving()) {
			if(posList.get(i) != duration) { posList.set(i,posList.get(i)+1); }
		} else {
			if(posList.get(i) != 0) { posList.set(i,posList.get(i)-1); }
			}
		
		elementList.get(i).setBasicFX(basicFX);
			app.pushStyle();
			if(elementList.get(i) instanceof Button) {
				convertingButton(i,(Button) elementList.get(i),buttons.before,buttons.after);
			}
			app.popStyle();
			
			app.pushStyle();
			if(elementList.get(i) instanceof CheckBox) {
				convertingRectangle(i, elementList.get(i), checkBoxs.before, checkBoxs.after);
			}
			app.popStyle();
			
			app.pushStyle();
			if(elementList.get(i) instanceof CircleSeekBar) {
				convertingRectangle(i, elementList.get(i), circleSeekBars.before, circleSeekBars.after);
				((CircleSeekBar) (elementList.get(i))).circle.fill.set(convertingColor(i,circleSeekBars.before.circle.fill,circleSeekBars.after.circle.fill));
			}
			app.popStyle();
			
			app.pushStyle();
			if(elementList.get(i) instanceof Scroll) {
				convertingRectangle(i, elementList.get(i), scrolls.before, scrolls.after);

				convertingButton(i, ((Scroll) (elementList.get(i))).button, scrolls.before.button, scrolls.after.button);
				convertingButton(i, ((Scroll) (elementList.get(i))).buttonDown, scrolls.before.buttonDown, scrolls.after.buttonDown);
				convertingButton(i, ((Scroll) (elementList.get(i))).buttonUp, scrolls.before.buttonUp, scrolls.after.buttonUp);	
			}
			app.popStyle();
			
			app.pushStyle();
			if(elementList.get(i) instanceof Slider) {
				convertingRectangle(i, elementList.get(i), sliders.before, sliders.after);

				convertingButton(i, ((Slider) (elementList.get(i))).button, sliders.before.button, sliders.after.button);
			}
			app.popStyle();
	 }
	}
	
	public void add(Rectangle... form) {
		for(int i = 0; i < form.length; i++) {
			if(elementList.isEmpty()) {
			elementList.add(form[i]);
			posList.add(0);
			}
		  }
	}
	
	public void remove(int index) {
		if(index < 0 || index > elementList.size()-1) { return; }
		elementList.remove(index);
		posList.remove(index);
	}
	
	
	public boolean isBasicFX() {
		return basicFX;
	}

	public void setBasicFX(boolean basicFX) {
		this.basicFX = basicFX;
	}

	private int convertingColor(int index, Color before, Color after) {
		return app.color(map(posList.get(index),0,duration,before.getRed(),after.getRed()),
			   map(posList.get(index),0,duration,before.getGreen(),after.getGreen()),
		       map(posList.get(index),0,duration,before.getBlue(),after.getBlue()),
			   map(posList.get(index),0,duration,before.getAlpha(),after.getAlpha()));
		
	}
	
	private int convertingValue(int index, float valueStart, float valueEnd) {
		return (int) (map(posList.get(index),0,duration,valueStart,valueEnd));
	}
	
	private void convertingRectangle(int index, Rectangle main, Rectangle before, Rectangle after) {
		main.fill.set(convertingColor(index,before.fill,after.fill));
		main.stroke.fill.set(convertingColor(index,before.stroke.fill,after.stroke.fill));
		main.stroke.setWeight(convertingValue(index,before.stroke.getWeight(),buttons.after.stroke.getWeight()));		
		main.corners.set(convertingValue(index,before.corners.get()[0],after.corners.get()[0]),
					 	 convertingValue(index,before.corners.get()[1],after.corners.get()[1]),
						 convertingValue(index,before.corners.get()[2],after.corners.get()[2]),
						 convertingValue(index,before.corners.get()[3],after.corners.get()[3]));
	
	}
	
	private void convertingButton(int index, Button main, Button before, Button after) {
		convertingRectangle(index,main,before,after);
		main.text.fill.set(convertingColor(index,buttons.before.text.fill,buttons.after.text.fill));
		main.text.setTextSize(convertingValue(index,buttons.before.text.getTextSize(),buttons.after.text.getTextSize()));
		
	}
	

	public int getPos(int index) {
		return posList.get(index);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public class Buttons {
		public Button before,after;

		public Buttons(PApplet app) {
			super();
			before = new Button(app);
			after = new Button(app);
			
			after.fill.set(app.color(0,164,0));
			after.text.setTextSize(20);
			after.stroke.fill.set(app.color(0,64,0));
			after.stroke.setWeight(4);
			
			before.corners.set(10);
			after.corners.set(100,0,100,0);
		}
		
	}
	
	public class CheckBoxs {
		public CheckBox before,after;

		public CheckBoxs(PApplet app) {
			super();
			before = new CheckBox(app);
			after = new CheckBox(app);
			
			after.fill.set(app.color(0,164,0));
			after.stroke.fill.set(app.color(0,64,0));
			after.stroke.setWeight(4);
			
			before.corners.set(10);
			after.corners.set(100,0,100,0);
		}
		
	}
	
	public class CircleSeekBars {
		public CircleSeekBar before,after;

		public CircleSeekBars(PApplet app) {
			super();
			before = new CircleSeekBar(app);
			after = new CircleSeekBar(app);
			
			after.fill.set(app.color(0,164,0));
			after.stroke.fill.set(app.color(0,64,0));
			after.stroke.setWeight(4);
			
			before.corners.set(10);
			after.corners.set(100,0,100,0);
			
			after.circle.fill.set(app.color(0,164,0));
		}
		
	}
	
	public class Scrolls {
		public Scroll before,after;

		public Scrolls(PApplet app) {
			super();
			before = new Scroll(app);
			after = new Scroll(app);
			
			after.fill.set(app.color(0,164,0));
			after.button.fill.set(app.color(0,164,0));
			after.stroke.fill.set(app.color(0,64,0));
			//after.stroke.setWeight(4);
			
			before.corners.set(10);
			after.corners.set(100,0,100,0);
			after.button.corners.set(100,0,100,0);
			after.buttonDown.corners.set(100,0,100,0);
			after.buttonUp.corners.set(100,0,100,0);
		}
		
	}
	
	public class Sliders {
		public Slider before,after;

		public Sliders(PApplet app) {
			super();
			before = new Slider(app);
			after = new Slider(app);
			
			after.fill.set(app.color(0,164,0));
			after.button.fill.set(app.color(0,164,0));
			after.stroke.fill.set(app.color(0,64,0));
			after.stroke.setWeight(4);
			
			before.corners.set(10);
			after.corners.set(100,0,100,0);
			after.button.corners.set(100,0,100,0);
			
			after.level.corners.set(100,0,100,0);
			after.level.corners.set(100,0,100,0);
			
		}
		
	}
}