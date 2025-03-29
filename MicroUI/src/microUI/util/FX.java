package microUI.util;

import static processing.core.PApplet.map;

import java.util.ArrayList;

import microUI.Button;
import microUI.CheckBox;
import microUI.CircleSeekBar;
import microUI.Scroll;
import microUI.Slider;
import microUI.Spinner;
import processing.core.PApplet;

public final class FX {
	public Buttons buttons;
	public CheckBoxs checkBoxs;
	public CircleSeekBars circleSeekBars;
	public Scrolls scrolls;
	public Sliders sliders;
	public Spinners spinners;
	
	private PApplet app;
	private final int EVENTS_COUNT = 5;
	private int duration, eventType;
	private boolean basicFX,changedTypeOfEvent,includedPosition,includedSize;
	private ArrayList<Rectangle> elementList;
	private ArrayList<Integer> posList;
	
	
	public FX(PApplet app) {
		this.app = app;
		buttons = new Buttons(app);
		checkBoxs = new CheckBoxs(app);
		circleSeekBars = new CircleSeekBars(app);
		scrolls = new Scrolls(app);
		sliders = new Sliders(app);
		spinners = new Spinners(app);
		duration = 10;
		basicFX = true;
		elementList = new ArrayList<Rectangle>();
		posList = new ArrayList<Integer>();
	}
	
	public void init() {
		for(int i = 0; i < elementList.size(); i++) {
			
		if(!changedTypeOfEvent) {
			initEvent(i,elementList.get(i).event.moved());
		} else {
			switch(eventType) {
			case Event.PRESSED : initEvent(i,elementList.get(i).event.pressed()); break;
			case Event.CLICKED : initEvent(i,elementList.get(i).event.clicked()); break;
			case Event.MOVED : initEvent(i,elementList.get(i).event.moved()); break;
			case Event.INSIDE : initEvent(i,elementList.get(i).event.inside()); break;
			case Event.OUTSIDE : initEvent(i,elementList.get(i).event.outside()); break;
			}
			
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
				((CircleSeekBar) (elementList.get(i))).circle.fill.setHEX(convertingColor(i,circleSeekBars.before.circle.fill,circleSeekBars.after.circle.fill));
			}
			app.popStyle();
			
			app.pushStyle();
			if(elementList.get(i) instanceof Scroll) {
				convertingRectangle(i, elementList.get(i), scrolls.before, scrolls.after);

				convertingButton(i, ((Scroll) (elementList.get(i))).button, scrolls.before.button, scrolls.after.button);
				convertingButton(i, ((Scroll) (elementList.get(i))).buttonMinus, scrolls.before.buttonMinus, scrolls.after.buttonMinus);
				convertingButton(i, ((Scroll) (elementList.get(i))).buttonPlus, scrolls.before.buttonPlus, scrolls.after.buttonPlus);	
			}
			app.popStyle();
			
			app.pushStyle();
			if(elementList.get(i) instanceof Slider) {
				convertingRectangle(i, elementList.get(i), sliders.before, sliders.after);
				
				convertingRectangle(i, ((Slider) (elementList.get(i))).level, sliders.before.level, sliders.after.level);

				convertingButton(i, ((Slider) (elementList.get(i))).button, sliders.before.button, sliders.after.button);
			}
			app.popStyle();
			
			app.pushStyle();
			if(elementList.get(i) instanceof Spinner) {
				convertingRectangle(i,(Spinner) elementList.get(i),spinners.before,spinners.after);
				((Spinner) (elementList.get(i))).text.fill.setHEX(convertingColor(i,spinners.before.text.fill,spinners.after.text.fill));
				((Spinner) (elementList.get(i))).text.setTextSize(convertingValue(i,spinners.before.text.getTextSize(),spinners.after.text.getTextSize()));
				
			}
			app.popStyle();
	 }
	}
	
	public void setEventType(int eventType) {
		if(eventType < 0 || eventType > EVENTS_COUNT) { return; }
		this.eventType = eventType;
		changedTypeOfEvent = true;
	}
	
	private void initEvent(int index, boolean typeOfEvent) {
		if(typeOfEvent) {
			if(posList.get(index) != duration) { posList.set(index,posList.get(index)+1); }
		} else {
			if(posList.get(index) != 0) { posList.set(index,posList.get(index)-1); }
			}
	}
	
	public void add(Rectangle... form) {
		for(int i = 0; i < form.length; i++) {
			elementList.add(form[i]);
			posList.add(0);
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
		if(includedPosition) {
			main.setPosition(convertingValue(index,before.getX(),after.getX()),convertingValue(index,before.getY(),after.getY()));
		}
		if(includedSize) {
			main.setSize(convertingValue(index,before.getW(),after.getW()),convertingValue(index,before.getH(),after.getH()));
		}
		
		main.fill.setHEX(convertingColor(index,before.fill,after.fill));
		main.stroke.fill.setHEX(convertingColor(index,before.stroke.fill,after.stroke.fill));
		main.stroke.setWeight(convertingValue(index,before.stroke.getWeight(),buttons.after.stroke.getWeight()));		
		/*main.corners.set(convertingValue(index,before.corners.get(0),after.corners.get(0)),
					 	 convertingValue(index,before.corners.get(1),after.corners.get(1)),
						 convertingValue(index,before.corners.get(2),after.corners.get(2)),
						 convertingValue(index,before.corners.get(3),after.corners.get(3)));
		*/
	}
	
	private void convertingButton(int index, Button main, Button before, Button after) {
		convertingRectangle(index,main,before,after);
		main.text.fill.setHEX(convertingColor(index,buttons.before.text.fill,buttons.after.text.fill));
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
			
			after.fill.set(34);
			after.text.setTextSize(20);
			after.stroke.fill.set(0,64,0);
			after.stroke.setWeight(4);
			
			//before.corners.set(10);
			//after.corners.set(0);
			
		}
		
	}
	
	public class CheckBoxs {
		public CheckBox before,after;

		public CheckBoxs(PApplet app) {
			super();
			before = new CheckBox(app);
			after = new CheckBox(app);
			
			after.fill.setHEX(app.color(0,164,0));
			after.stroke.fill.setHEX(app.color(0,64,0));
			after.stroke.setWeight(4);
			
			//before.corners.set(10);
			//after.corners.set(100,0,100,0);
		}
		
	}
	
	public class CircleSeekBars {
		public CircleSeekBar before,after;

		public CircleSeekBars(PApplet app) {
			super();
			before = new CircleSeekBar(app);
			after = new CircleSeekBar(app);
			
			after.fill.setHEX(app.color(0,164,0));
			after.stroke.fill.setHEX(app.color(0,64,0));
			after.stroke.setWeight(4);
			
			//before.corners.set(10);
			//after.corners.set(100,0,100,0);
			
			after.circle.fill.setHEX(app.color(0,164,0));
		}
		
	}
	
	public class Scrolls {
		public Scroll before,after;

		public Scrolls(PApplet app) {
			super();
			before = new Scroll(app);
			after = new Scroll(app);
			
			after.fill.setHEX(app.color(0,164,0));
			after.button.fill.setHEX(app.color(0,164,0));
			after.stroke.fill.setHEX(app.color(0,64,0));

			//before.corners.set(10);
			//after.corners.set(100,0,100,0);
			//after.button.corners.set(100,0,100,0);
			//after.buttonMinus.corners.set(100,0,100,0);
			//after.buttonPlus.corners.set(100,0,100,0);
		}
		
	}
	
	public class Sliders {
		public Slider before,after;

		public Sliders(PApplet app) {
			super();
			before = new Slider(app);
			after = new Slider(app);
			
			before.fill.set(44);

			after.fill.set(32);
			before.level.fill.set(0,128,0);
			after.level.fill.set(0,234,0);
			after.button.fill.set(32);
			after.stroke.setWeight(4);
			
		}
		
	}

	public class Spinners {
		public Spinner before,after;

		public Spinners(PApplet app) {
			super();
			before = new Spinner(app);
			after = new Spinner(app);
			
			after.fill.set(34);
			after.text.setTextSize(20);
			after.stroke.fill.setHEX(app.color(0,64,0));
			after.stroke.setWeight(4);
			
			//before.corners.set(0);
			//after.corners.set(100,0,100,0);
		}
		
	}
	
	public boolean isIncludedPosition() {
		return includedPosition;
	}

	public void setIncludedPosition(boolean includedPosition) {
		this.includedPosition = includedPosition;
	}

	public boolean isIncludedSize() {
		return includedSize;
	}

	public void setIncludedSize(boolean includedSize) {
		this.includedSize = includedSize;
	}
	
	public void setIncludedTransforms(boolean i) {
		includedPosition = includedSize = i;
	}
}