package microUI.component;

import java.util.ArrayList;

import microUI.event.Event;

public class Spinner extends Button {
	private boolean open,showSelectedItem,autoCloseable;
	private ArrayList<Button> itemList;
	private int select;
	private float listHeight;
	private Event localEvent;
	
	public Spinner(String title, float x, float y, float w, float h) {
		super(title,x,y,w,h);
		//shadow.invisible();
		itemList = new ArrayList<Button>();
		showSelectedItem = autoCloseable = true;
		localEvent = new Event();
	}
	
	public Spinner(String title) {
		this(title,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}
	
	public Spinner() {
		this("Spinner",app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}
	
	@Override
	public void update() {
			localEvent.listen(this);
			
			super.update();
			if(localEvent.clicked()) { open = !open; }
			
			if(open) {
				if(!itemList.isEmpty()) {
					for(int i = 0; i < itemList.size(); i++) {
						Button item = itemList.get(i);
						item.text.setTextSize(text.getTextSize());
						item.draw();
						if(item.event.pressed()) {
							select = i;
							if(autoCloseable) { close(); }
						}
						
						
						
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
	}
	
	public Spinner setAutoCloseable(boolean a) {
		autoCloseable = a;
		return this;
	}
	
	public boolean isOpen() {
		return open;
	}

	public Spinner open() {
		this.open = true;
		return this;
	}
	
	public Spinner close() {
		this.open = false;
		return this;
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
			itemList.add(new Button(title[i],getX(),getY()+getH()+listHeight,getW(),getH()));
			listHeight += getH();
			//itemList.get(i).shadow.invisible();
		}
		
		return this;
	}
	
	public Spinner add(int... nums) {
		for(int i = 0; i < nums.length; i++) {
			itemList.add(new Button(String.valueOf(nums[i]),getX(),getY()+getH()+listHeight,getW(),getH()));
			listHeight += getH();
			//itemList.get(i).shadow.invisible();
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