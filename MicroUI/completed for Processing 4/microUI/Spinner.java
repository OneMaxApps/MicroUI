package microUI;

import java.util.ArrayList;

import processing.core.PApplet;

public class Spinner extends Button {
	private PApplet app;
	private boolean open,showSelectedItem;
	private ArrayList<Button> itemList;
	private int select;
	private float listHeight;

	public Spinner(PApplet app, String title, float x, float y, float w, float h) {
		super(app,title,x,y,w,h);
		this.app = app;
		corners.set(0);
		shadowDestroy();
		itemList = new ArrayList<Button>();
	}
	
	public Spinner(PApplet app, String title) {
		this(app,title,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}
	
	public Spinner(PApplet app) {
		this(app,"Spinner",app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}
	
	public void draw() {
		super.draw();
		if(event.clicked()) { open = !open; }
		
		if(open) {
			if(!itemList.isEmpty()) {
				for(int i = 0; i < itemList.size(); i++) {
					Button item = itemList.get(i);
					item.text.setTextSize(text.getTextSize());
					item.draw();
					if(item.event.clicked()) { select = i; }
					
					
					
				}
				if(select >= 0 && select < itemList.size()) {
					if(showSelectedItem) {
						app.pushStyle();
						app.fill(0,234,0,32);
						app.rect(itemList.get(select).getX(),itemList.get(select).getY(),itemList.get(select).getW(),itemList.get(select).getH());
						app.popStyle();
					}
				}
			}
			
			
		}
		;
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public int getSelect() {
		return select;
	}
	
	public boolean selected(String title) {
		return itemList.get(select).text.get().equals(title);
	}

	public void setSelect(int select) {
		if(select < 0 || select > itemList.size()-1) { throw new IndexOutOfBoundsException("Index out of bounds of selecting"); }
		this.select = select;
	}
	
	public Spinner add(String... title) {
		for(int i = 0; i < title.length; i++) {
			itemList.add(new Button(app,title[i],getX(),getY()+getH()+listHeight,getW(),getH()));
			listHeight += getH();
			itemList.get(i).shadowDestroy();
		}
		
		return this;
	}
	
	public boolean isShowSelectedItem() {
		return showSelectedItem;
	}

	public void setShowSelectedItem(boolean showSelectedItem) {
		this.showSelectedItem = showSelectedItem;
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if(itemList == null) { return; }
		
		for(int i = 0; i < itemList.size(); i++) {
			Button item = itemList.get(i);
			item.setPosition(x,y+getH()*(i+1));
		}
	}
	
	@Override
	public void setSize(float w, float h) {
		super.setSize(w, h);
		
		if(itemList == null) { return; }
		
		listHeight = itemList.size()*h;
		
		for(int i = 0; i < itemList.size(); i++) {
			Button item = itemList.get(i);
			item.setPosition(getX(),getY()+getH()*(i+1));
			item.setSize(w, getH());
		}
	}
	
	@Override
	public void setTransforms(float x, float y, float w, float h) {
		setPosition(x,y);
		setSize(w,h);
	}
	
	public void remove(int index) {
		itemList.remove(index);
		listHeight -= getH();
		for(int i = 0; i < itemList.size(); i++) {
			Button item = itemList.get(i);
			item.setPosition(getX(),getY()+getH()*(i+1));
		}
	}
}