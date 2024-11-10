package microUI.utils;

import static processing.core.PApplet.map;

import java.util.ArrayList;

import microUI.Button;
import microUI.CheckBox;
import processing.core.PApplet;

public class FX {
	private PApplet app;
	private int duration;
	public Buttons button;
	public CheckBoxs checkBox;
	private boolean basicFX;
	private ArrayList<Rectangle> elementList;
	private ArrayList<Integer> posList;
	
	public FX(PApplet app) {
		this.app = app;
		button = new Buttons(app);
		checkBox = new CheckBoxs(app);
		duration = 10;
		basicFX = true;
		elementList = new ArrayList<Rectangle>();
		posList = new ArrayList<Integer>();
	}
	
	public void init() {
		for(int i = 0; i < elementList.size(); i++) {
			
		if(elementList.get(i).event.pressed()) {
			if(posList.get(i) != duration) { posList.set(i,posList.get(i)+1); }
		} else {
			if(posList.get(i) != 0) { posList.set(i,posList.get(i)-1); }
			}
		
		elementList.get(i).setBasicFX(basicFX);
			
			if(elementList.get(i) instanceof Button) {
				elementList.get(i).fill.set(convertingColors(i,button.before.fill,button.after.fill));
				((Button) (elementList.get(i))).text.fill.set(convertingColors(i,button.before.text.fill,button.after.text.fill));
				((Button) (elementList.get(i))).text.setTextSize(convert(i,button.before.text.getTextSize(),button.after.text.getTextSize()));
				elementList.get(i).stroke.fill.set(convertingColors(i,button.before.stroke.fill,button.after.stroke.fill));
				elementList.get(i).stroke.setWeight(convert(i,button.before.stroke.getWeight(),button.after.stroke.getWeight()));		
				elementList.get(i).corners.set(convert(i,button.before.corners.get()[0],button.after.corners.get()[0]),
							 	 convert(i,button.before.corners.get()[1],button.after.corners.get()[1]),
								 convert(i,button.before.corners.get()[2],button.after.corners.get()[2]),
								 convert(i,button.before.corners.get()[3],button.after.corners.get()[3]));
			}
			
			if(elementList.get(i) instanceof CheckBox) {
				elementList.get(i).fill.set(convertingColors(i,checkBox.before.fill,checkBox.after.fill));
				elementList.get(i).stroke.fill.set(convertingColors(i,checkBox.before.stroke.fill,checkBox.after.stroke.fill));
				elementList.get(i).stroke.setWeight(convert(i,checkBox.before.stroke.getWeight(),checkBox.after.stroke.getWeight()));		
				elementList.get(i).corners.set(convert(i,checkBox.before.corners.get()[0],checkBox.after.corners.get()[0]),
							 	 convert(i,checkBox.before.corners.get()[1],checkBox.after.corners.get()[1]),
								 convert(i,checkBox.before.corners.get()[2],checkBox.after.corners.get()[2]),
								 convert(i,checkBox.before.corners.get()[3],checkBox.after.corners.get()[3]));
			}
		
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

	private int convertingColors(int index, Color before, Color after) {
		return app.color(map(posList.get(index),0,duration,before.getRed(),after.getRed()),
			   map(posList.get(index),0,duration,before.getGreen(),after.getGreen()),
		       map(posList.get(index),0,duration,before.getBlue(),after.getBlue()),
			   map(posList.get(index),0,duration,before.getAlpha(),after.getAlpha()));
		
	}
	
	private int convert(int index, float valueStart, float valueEnd) {
		return (int) (map(posList.get(index),0,duration,valueStart,valueEnd));
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
	
	private class Buttons {
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
	
	private class CheckBoxs {
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
}