package microui.component;

import java.util.ArrayList;
import java.util.Arrays;

import microui.core.style.Color;
import microui.event.Event;

public class MenuButton extends Button {
	private boolean open,autoClose,isRoot;
	private final ArrayList<Button> itemList;
	private int selectedId;
	private float listHeight;
	private final Event innerEvent;
	private MenuButton root;
	private float markX,markY,markW,markH;
	
	public MenuButton(String title, float x, float y, float w, float h) {
		super(title,x,y,w,h);
		
		autoClose = true;
		itemList = new ArrayList<Button>();
		selectedId = -1;
		innerEvent = new Event();
		isRoot = true;
		root = this;
		
		calculateMarkBounds();
	}
	
	public MenuButton(String title) {
		this(title,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}
	
	public MenuButton() {
		this("Menu Button",app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	}

	@Override
	public final void update() {
			super.update();
			innerEvent.listen(this);
			
			if(innerEvent.clicked()) {
				open = !open;
				if(!open) { closeAllSubMenus(); } else { selectedId = -1; }
			}
			
			itemsOnDraw();
			markOnDraw();
			
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
	
	public final boolean isSelected(final String title) {
		if(selectedId == -1) { return false; }
		
		boolean selected = false;
		
			if(itemList.get(selectedId).text.get().equals(title)) { selected = true; }
			
			if(selected) { return true; }
			
			if(itemList.get(selectedId) instanceof MenuButton subMenu) {
				selected = subMenu.isSelected(title);
			}
		
		return selected;
	}
	
	public final void setSelectedId(final int selectedId) {
		if(selectedId < 0 || selectedId >= itemList.size()) { throw new IndexOutOfBoundsException("Index out of bounds of selecting"); }
		this.selectedId = selectedId;
		if(open) { open = !open; }
	}

	public final MenuButton add(final String... title) {
		if(isRoot) {
			rootListState(title);
		} else {
			childListState(title);
		}
		
		return this;
	}
	
	public final MenuButton add(final int... numbers) {
		add(Arrays.stream(numbers).mapToObj(String::valueOf).toArray(String[]::new));
		return this;
	}
	
	public final void add(final MenuButton... subMenu) {
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
		for(int i = 0; i < subMenu.length; i++) {
			itemList.add(subMenu[i]);
			subMenu[i].setRoot(root);
			if(isRoot) {
				subMenu[i].setTransforms(getX(),getY()+getH()+listHeight,getW(),getH());
			} else {
				subMenu[i].setTransforms(CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),getY()+listHeight,getW(),getH());
			}
			listHeight += getH();
		}
		
		setRootForSubMenus();
	}
	
	
	public final MenuButton addSubMenu(final MenuButton subMenu, final String... items) {
		add(subMenu);
		subMenu.add(items);
		return this;
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
		
		calculateMarkBounds();
	}
	
	public final void remove(final int index) {
		if(itemList == null || itemList.isEmpty() || index < 0 || index >= itemList.size()) { return; }
		
		itemList.remove(index);
		listHeight -= getH();
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
		for(int i = 0; i < itemList.size(); i++) {
			final Button item = itemList.get(i);
			if(isRoot) {
				item.setPosition(getX(),getY()+getH()*(i+1));
			} else {
				item.setPosition(CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),y+getH()*(1+i)-getH());
			}
		}
	}
	
	public final int getItemsCount() { return itemList.size(); }
	
	public final void setItemsColor(final Color color) {
		for(Button item : itemList) {
			item.fill.set(color);
			
			if(item instanceof MenuButton subMenu) {
				subMenu.setItemsColor(color);
			}
		}
	}
	
	public final Button getItem(final String title) {

		for(Button item : itemList) {
			if(item.text.get().equals(title)) {
				return item;
			}
		}
		
		for(Button item : itemList) {
			if(item instanceof MenuButton subMenu) {
				return subMenu.getItem(title);
			}
		}
		
		throw new IllegalArgumentException(title+" item is not exists");
	}
	
	
	private final MenuButton setRootForSubMenus() {
		for(Button item : itemList) {
			if(item instanceof MenuButton subMenu) {
				subMenu.setRoot(root);
				for(Button innerItem : subMenu.itemList) {
					if(innerItem instanceof MenuButton innerSubMenu) {
						innerSubMenu.setRoot(root);
						innerSubMenu.setRootForSubMenus();
					}
				}
			}
		}
		return this;
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
	
	private final void rootListState(final String... title) {
		for(int i = 0; i < title.length; i++) {
			itemList.add(new Button(title[i],getX(),getY()+getH()+listHeight,getW(),getH()));
			listHeight += getH();
		}
	}
	
	private final void childListState(final String... title) {
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width; 
		for(int i = 0; i < title.length; i++) {
			itemList.add(new Button(title[i],CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),getY()+listHeight,getW(),getH()));
			listHeight += getH();
		}
	}
	
	private final void setRoot(final MenuButton root) {
		if(root == null) { return; }
		this.root = root;
		if(root != this) { isRoot = false; }
	}
	
	private final void markOnDraw() {
			app.pushStyle();
			app.noStroke();
			if(!isRoot) { app.fill(255,128); } else { app.fill(0,255,0,128); }
			app.rect(markX,markY,markW,markH);
			app.popStyle();
	}
	
	private final void itemsOnDraw() {
		if(open) {
			if(!itemList.isEmpty()) {
				
				for(int i = 0; i < itemList.size(); i++) {
					final Button item = itemList.get(i);
					item.draw();
					
					if(item.event.clicked()) {
						selectedId = i;
						closeAllSubMenusWithoutSelected();
						
						if(!(item instanceof MenuButton)) {
							if(autoClose) {
								root.close();					
								root.closeAllSubMenus();	
							}
						}
					}
					
				}
			}
			
			
		}
	}

	private final void calculateMarkBounds() {
		markX = getX()+getW()*.2f;
		markY = getY()+getH()*.8f;
		markW = getW()*.6f;
		markH = getH()*.05f;
		
	}
}