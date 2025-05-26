package microui.component;

import java.util.ArrayList;

import microui.event.Event;



public class MenuButton extends Button {
	private boolean open,autoCloseable,isRoot;
	private ArrayList<Button> itemList;
	private int select,recentOpenedItemId;
	private float listHeight;
	private Event localEvent;
	private MenuButton root;
	
	public MenuButton(String title, float x, float y, float w, float h) {
		super(title,x,y,w,h);
		
		autoCloseable = true;
		itemList = new ArrayList<Button>();
		select = -1;
		localEvent = new Event();
		isRoot = true;
		
		root = this;
	}
	
	public MenuButton(String title) {
		this(title,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}
	
	public MenuButton() {
		this("Menu Button",app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}
	
	@Override
	public void update() {
			localEvent.listen(this);
			
			super.update();
			if(localEvent.clicked()) {
				open = !open;
				closeAllInner();
			}
			
			if(open) {
				select = -1;
				if(!itemList.isEmpty()) {
					
					for(int i = 0; i < itemList.size(); i++) {
						Button item = itemList.get(i);
						item.text.setTextSize(text.getTextSize());
						item.draw();
						
						if(item.event.clicked()) {
							if(i != -1) {
								recentOpenedItemId = i;
							}
						}
						
						if(!(item instanceof MenuButton)) {
							if(item.event.pressed()) {
								recentOpenedItemId = select = i;
								if(autoCloseable) {
									if(isRoot) { close(); } else { root.close(); }								
									root.closeAllInner();	
								}
							}
						}
					}
					
				}
				
				
			}
			
			if(!isRoot) {
				app.pushStyle();
				app.noStroke();
				app.fill(255,128);
				app.rect(getX()+getW()*.2f,getY()+getH()*.8f,getW()*.6f,getH()*.1f);
				app.popStyle();
			}
			
			if(recentOpenedItemId != -1) {
				closeAllSubMenu();
				//recentOpenedItemId = -1;
			}
	}
	
	public MenuButton setAutoCloseable(boolean a) {
		autoCloseable = a;
		return this;
	}
	
	public boolean isOpen() {
		return open;
	}

	public MenuButton open() {
		this.open = true;
		return this;
	}
	
	public MenuButton close() {
		this.open = false;
		return this;
	}
	
	public void closeAllSubMenu() {
		for(int i = 0; i < itemList.size(); i++) {
			if(i == recentOpenedItemId) { continue; }
			Button item = itemList.get(i);
			if(item instanceof MenuButton subMenu) {
				subMenu.close();
				subMenu.closeAllInner();
				
				if(item instanceof MenuButton innerMenu) {
					for(Button innerItem : innerMenu.itemList) {
						if(innerItem instanceof MenuButton innerItemSubMenu) {
						innerItemSubMenu.close();
						innerItemSubMenu.closeAllInner();
						}
					}
				}
			}
		}
	}
	
	public void closeAllInner() {
		if(!open) {
			if(itemList.isEmpty()) { return ; }
			
			for(int i = 0; i < itemList.size(); i++) {
				if(itemList.get(i) instanceof MenuButton subMenu) {
					
					for(int j = 0; j < subMenu.itemList.size(); j++) {
						if(subMenu.itemList.get(j) instanceof MenuButton innerSubMenu) {
							innerSubMenu.close();
						}
					}
					
					subMenu.close();
				}
			}
			
		}

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
	
	public MenuButton add(String... title) {
		if(isRoot) {
			for(int i = 0; i < title.length; i++) {
				itemList.add(new Button(title[i],getX(),getY()+getH()+listHeight,getW(),getH()));
				listHeight += getH();
			}
		} else {
			final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW() < app.width; 
			for(int i = 0; i < title.length; i++) {
				if(CAN_BE_IN_RIGHT_SIDE) {
					itemList.add(new Button(title[i],getX()+getW(),getY()+listHeight,getW(),getH()));
				} else {
					itemList.add(new Button(title[i],getX()-getW(),getY()+listHeight,getW(),getH()));
				}
				listHeight += getH();
			}
		}
		
		return this;
	}
	
	public MenuButton add(int... nums) {
		for(int i = 0; i < nums.length; i++) {
			add(String.valueOf(nums[i]));
		}
		return this;
	}
	
	public MenuButton add(MenuButton... subMenu) {
		
		if(isRoot) {
			for(int i = 0; i < subMenu.length; i++) {
				itemList.add(subMenu[i]);
				subMenu[i].setTransforms(getX(),getY()+getH()+listHeight,getW(),getH());
				subMenu[i].isRoot = false;
				subMenu[i].root = root;
				listHeight += getH();
			}
		} else {
			final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW() < app.width; 
			for(int i = 0; i < subMenu.length; i++) {
				itemList.add(subMenu[i]);
				if(CAN_BE_IN_RIGHT_SIDE) {
					subMenu[i].setTransforms(getX()+getW(),getY()+listHeight,getW(),getH());
				} else {
					subMenu[i].setTransforms(getX()-getW(),getY()+listHeight,getW(),getH());
				}
				subMenu[i].isRoot = false;
				subMenu[i].root = root;
				listHeight += getH();
			}
		}
		
		return this;
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if(itemList == null) { return; }
		
		if(isRoot) {
			for(int i = 0; i < itemList.size(); i++) {
				Button item = itemList.get(i);
				item.setPosition(x,y+getH()*(i+1));
			}
			
		} else {
			final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
			
			for(int i = 0; i < itemList.size(); i++) {
				Button item = itemList.get(i);
				
				if(CAN_BE_IN_RIGHT_SIDE) { item.setPosition(x+w,y+getH()*(1+i)-getH()); }
				else { item.setPosition(x-w,y+getH()*(1+i)-getH()); }
			}
			
		}

	}
	
	@Override
	public void setSize(float w, float h) {
		super.setSize(w, h);
		
		if(itemList == null) { return; }
		
		listHeight = itemList.size()*h;
		
		if(isRoot) {
			
			for(int i = 0; i < itemList.size(); i++) {
				Button item = itemList.get(i);
				item.setPosition(x,y+getH()*(i+1));
				item.setSize(w, getH());
			}
			
		} else {
			final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
			
			for(int i = 0; i < itemList.size(); i++) {
				Button item = itemList.get(i);
				item.setSize(w, getH());
				
				if(CAN_BE_IN_RIGHT_SIDE) { item.setPosition(x+w,y+getH()*(1+i)-getH()); }
				else { item.setPosition(x-w,y+getH()*(1+i)-getH()); }
			}
			
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