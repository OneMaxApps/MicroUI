package microui.component;

import java.util.ArrayList;
import java.util.Arrays;

import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Event;
import processing.event.MouseEvent;

public class MenuButton extends Button implements Scrollable {
	private boolean open,autoClose,isRoot,markVisible;
	private final ArrayList<Button> itemList;
	private int selectedId;
	private float listHeight;
	private final Event innerEvent;
	private MenuButton root;
	private float markX,markY,markW,markH;
	private final Scrolling scrolling;
	
	public MenuButton(String title, float x, float y, float w, float h) {
		super(title,x,y,w,h);
		
		autoClose = true;
		itemList = new ArrayList<Button>();
		selectedId = -1;
		innerEvent = new Event();
		isRoot = true;
		markVisible = true;
		root = this;
		calculateMarkBounds();
		scrolling = new Scrolling();
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
			scrolling.update();
			innerEvent.listen(this);
			
			
			if(innerEvent.clicked()) {
				open = !open;
				if(!open) { closeAllSubMenus(); } else { selectedId = -1; }
			}
			
			itemsOnDraw();
			markOnDraw();
			checkClosingFromOut();
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
	
	public final MenuButton addSubMenu(final String... items) {
		final MenuButton subMenu = new MenuButton(items[0]);
		add(subMenu);
		String[] itemsCopy = Arrays.copyOfRange(items, 1, items.length);
		subMenu.add(itemsCopy);
		
		return this;
	}
	
	@Override
	public final void inTransforms() {
		super.inTransforms();
		if(itemList == null) { return; }
		
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getW()*2 < app.width;
		
		listHeight = 0;
		for(int i = 0; i < itemList.size(); i++) {
			final Button ITEM = itemList.get(i);
			ITEM.setSize(w, getH());
			
			if(isRoot) {
				ITEM.setPosition(x,y+getH()*(i+1));
			} else {
				ITEM.setPosition(CAN_BE_IN_RIGHT_SIDE ? getX()+getW() : getX()-getW(),y+getH()*(1+i)-getH());
			}
			
			listHeight += ITEM.getH();
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
	
	// FIXME: can't call from setup()
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
	
	public final ArrayList<Button> getItems() {
		return itemList;
	}
	
	public final boolean isMarkVisible() {
		return markVisible;
	}

	public final void setMarkVisible(boolean markVisible) {
		this.markVisible = markVisible;
	}
	
	public final void setMarksVisible(boolean markVisible) {
		this.markVisible = markVisible;
		
		itemList.forEach(i -> {
			if(i instanceof MenuButton subMenu) {
				subMenu.setMarksVisible(markVisible);
			}
		});
	}

	@Override
	public final void mouseWheel(MouseEvent e) {
		scrolling.init(e);
		
		for(Button item : itemList) {
			if(item instanceof MenuButton subMenu) {
				subMenu.mouseWheel(e);
			}
		}
	}
	
	private final void checkClosingFromOut() {
		if(!open) { return; }
		boolean inside = false;
		
		inside = checkInsideToAnyIn();
		
		if(app.mousePressed && !event.inside() && !inside) {
			close();
		}
		
	}
	
	private final boolean checkInsideToAnyIn() {
		boolean insideState = false;
		if(scrolling.event.inside()) { insideState = true; }
		
		for(Button item : itemList) {
			if(item instanceof MenuButton subMenu) {
				if(subMenu.checkInsideToAnyIn() && subMenu.isOpen()) {
					insideState = true;
				}
			}
		}
		
		return insideState;
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
			if(!markVisible) { return; }
			app.pushStyle();
			app.noStroke();
			if(open) { app.fill(0,255,0,128); } else { app.fill(255,128); }
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
	
	public final class Scrolling {
		private final Event event;
		private boolean enable;
		
		private Scrolling() {
			event = new Event();
			enable = true;
		}
		
		private final void update() {
			if(itemList.isEmpty()) { return; }
			
			if(isRoot) {
				event.listen(itemList.get(0).getX(),y+h,itemList.get(0).getW(),listHeight);
			} else {
				event.listen(itemList.get(0).getX(),y,itemList.get(0).getW(),listHeight);
			}
		}
		
		private final void init(final MouseEvent e) {
			if(!enable) { return; }
			
			if(!event.inside()) { return; }
			
			for(int i = 0; i < itemList.size(); i++) {
				final Button item = itemList.get(i);
				
				if(e.getCount() > 0) {
					item.setY(item.getY()+item.getH());
				} else {
					item.setY(item.getY()-item.getH());
				}

			}
			
			if(e.getCount() > 0) {
				itemList.add(0,itemList.remove(itemList.size()-1));
				itemList.get(0).setY(itemList.get(1).getY()-itemList.get(0).getH());
			} else {
				itemList.add(itemList.size()-1,itemList.remove(0));
				itemList.get(itemList.size()-1).setY(itemList.get(itemList.size()-2).getY()+itemList.get(itemList.size()-1).getH());
			}
			
		}

		public final boolean isEnable() {
			return enable;
		}

		public final void setEnable(boolean enable) {
			this.enable = enable;
		}
		
	}
}