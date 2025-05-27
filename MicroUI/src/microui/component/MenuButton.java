package microui.component;

import java.util.ArrayList;
import java.util.Arrays;

import microui.core.style.Color;
import microui.event.Event;



public final class MenuButton extends Button {
	private boolean open,autoClose,isRoot;
	private final ArrayList<Button> itemList;
	private int selectedId;
	private float listHeight;
	private final Event localEvent;
	private MenuButton root;
	
	public MenuButton(String title, float x, float y, float w, float h) {
		super(title,x,y,w,h);
		
		autoClose = true;
		itemList = new ArrayList<Button>();
		selectedId = -1;
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
	
	// TODO: Check deeper inside
	@Override
	public final void update() {
			super.update();
			localEvent.listen(this);
			if(localEvent.clicked()) {
				open = !open;
				if(!open) { closeAllSubMenus(); }
			}
			
			if(open) {
				selectedId = -1;
				
				if(!itemList.isEmpty()) {
					
					for(int i = 0; i < itemList.size(); i++) {
						Button item = itemList.get(i);
						item.text.setTextSize(text.getTextSize());
						item.draw();
						
						if(item.event.clicked()) {
							if(i != -1) {
								selectedId = i;
								closeAllSubMenusWithoutSelected();
							}
						}
						
						if(!(item instanceof MenuButton)) {
							if(item.event.pressed()) {
								selectedId = i;
								if(autoClose) {
									if(isRoot) { close(); } else { root.close(); }								
									root.closeAllSubMenus();	
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
			
	}
	
	public final void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}
	
	public final boolean isOpen() { return open; }

	public final void open() { this.open = true; }
	
	public final void close() { this.open = false; }
	
	public final int getSelectedId() {
		return selectedId;
	}
	
	public final boolean isSelected(final String TITLE) {
		boolean selected = false;
		
		for(int i = 0; i < itemList.size(); i++) {
			final Button ITEM = itemList.get(i);
			
			if(ITEM.text.get().equals(TITLE) && i == selectedId) { selected = true; }
			
			if(selected) { return true; }
			
			if(ITEM instanceof MenuButton subMenu) {
				selected = subMenu.isSelected(TITLE);
			}
			
		}
		
		return selected;
	}
	
	public final void setSelectedId(final int SELECTED_ID) {
		if(SELECTED_ID < 0 || SELECTED_ID >= itemList.size()) { throw new IndexOutOfBoundsException("Index out of bounds of selecting"); }
		this.selectedId = SELECTED_ID;
		if(open) { open = !open; }
	}

	public final void add(final String... TITLE) {
		if(isRoot) {
			rootListState(TITLE);
		} else {
			childListState(TITLE);
		}
		
	}
	
	public final void add(final int... NUMBERS) {
		add(Arrays.stream(NUMBERS).mapToObj(String::valueOf).toArray(String[]::new));
	}
	
	public final void add(final MenuButton... SUB_MENU) {
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
		for(int i = 0; i < SUB_MENU.length; i++) {
			itemList.add(SUB_MENU[i]);
			SUB_MENU[i].setRoot(root);
			if(isRoot) {
				SUB_MENU[i].setTransforms(getX(),getY()+getH()+listHeight,getW(),getH());
			} else {
				SUB_MENU[i].setTransforms(CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),getY()+listHeight,getW(),getH());
			}
			listHeight += getH();
		}
	}
	
	@Override
	public final void inTransforms() {
		super.inTransforms();
		if(itemList == null) { return; }
		
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
		
		for(int i = 0; i < itemList.size(); i++) {
			final Button ITEM = itemList.get(i);
			ITEM.setSize(w, getH());
			
			if(isRoot) {
				ITEM.setPosition(x,y+getH()*(i+1));
			} else {
				ITEM.setPosition(CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),y+getH()*(1+i)-getH());
			}
		}
		
	}
	
	public final void remove(final int INDEX) {
		if(itemList == null || itemList.isEmpty() || INDEX < 0 || INDEX >= itemList.size()) { return; }
		
		itemList.remove(INDEX);
		listHeight -= getH();
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
		for(int i = 0; i < itemList.size(); i++) {
			final Button ITEM = itemList.get(i);
			if(isRoot) {
				ITEM.setPosition(getX(),getY()+getH()*(i+1));
			} else {
				ITEM.setPosition(CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),y+getH()*(1+i)-getH());
			}
		}
	}
	
	public final int getItemsCount() { return itemList.size(); }
	
	public final void setItemsColor(final Color COLOR) {
		for(Button item : itemList) {
			item.fill.set(COLOR);
			
			if(item instanceof MenuButton subMenu) {
				subMenu.setItemsColor(COLOR);
			}
		}
	}
	
	
	private final void closeAllSubMenusWithoutSelected() {
		if(itemList.isEmpty() || selectedId == -1) { return; }
		for(int i = 0; i < itemList.size(); i++) {
			if(i == selectedId) { continue; }
			if(itemList.get(i) instanceof MenuButton subMenu) {
				subMenu.closeAllSubMenus();
				subMenu.close();
			}
		}
	}
	
	private final void closeAllSubMenus() {
		if(itemList.isEmpty()) { return; }
		
		if(!open) {
			for(int i = 0; i < itemList.size(); i++) {
				if(itemList.get(i) instanceof MenuButton subMenu) {
					subMenu.close();
					subMenu.closeAllSubMenus();
				}
			}	
		}

	}
	
	private final void rootListState(final String... TITLE) {
		for(int i = 0; i < TITLE.length; i++) {
			itemList.add(new Button(TITLE[i],getX(),getY()+getH()+listHeight,getW(),getH()));
			listHeight += getH();
		}
	}
	
	private final void childListState(final String... TITLE) {
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width; 
		for(int i = 0; i < TITLE.length; i++) {
			itemList.add(new Button(TITLE[i],CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),getY()+listHeight,getW(),getH()));
			listHeight += getH();
		}
	}
	
	private final void setRoot(final MenuButton ROOT) {
		if(ROOT == null) { return; }
		this.root = ROOT;
		if(ROOT != this) { isRoot = false; }
	}
	
}