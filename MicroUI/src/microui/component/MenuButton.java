package microui.component;

import java.util.ArrayList;
import java.util.Arrays;

import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Event;
import processing.event.MouseEvent;

public class MenuButton extends Button implements Scrollable {
	private boolean isOpen,isAutoClose,isRoot,isMarkVisible;
	
	private int selectedId;
	
	private float listHeight;
	private float markX,markY,markW,markH;
	
	private final ArrayList<Button> itemList;
	private final Scrolling scrolling;
	
	private MenuButton root;
	
	public MenuButton(String title, float x, float y, float w, float h) {
		super(title,x,y,w,h);
		
		isAutoClose = true;
		itemList = new ArrayList<Button>();
		selectedId = -1;
		isRoot = true;
		isMarkVisible = true;
		root = this;
		calculateMarkBounds();
		scrolling = new Scrolling();
		
		onClick(() -> {
			isOpen = !isOpen;
			if(!isOpen) { closeAllSubMenus(); } else { selectedId = -1; }
		});
	}
	
	public MenuButton(String title) {
		this(title,cxt.width*.3f,cxt.height*.45f,cxt.width*.4f,cxt.height*.1f);
	}
	
	public MenuButton(String title, String... items) {
		this(title,cxt.width*.3f,cxt.height*.45f,cxt.width*.4f,cxt.height*.1f);
		add(items);
	}
	
	public MenuButton() {
		this("Menu Button",cxt.width*.3f,cxt.height*.45f,cxt.width*.4f,cxt.height*.1f);
	}

	@Override
	protected final void update() {
			super.update();
			scrolling.update();
			
			itemsOnDraw();
			markOnDraw();
			checkClosingFromOut();
	}
	
	public final void setAutoClose(boolean autoClose) {
		this.isAutoClose = autoClose;
	}
	
	public final boolean isOpen() { return isOpen; }

	public final void open() { this.isOpen = true; }
	
	public final void close() { this.isOpen = false; }
	
	public final int getSelectedId() {
		return selectedId;
	}
	
	public final boolean isSelected(final String title) {
		
		for(Button item : itemList) {
			if(item.getText().equals(title)) { return true; }
		}
		
		return false;
	}
	
	public final boolean isSelectedDeep(final String title) {
		if(selectedId == -1) { return false; }
		
		boolean selected = false;
		
			if(itemList.get(selectedId).getText().equals(title)) { selected = true; }
			
			if(selected) { return true; }
			
			if(itemList.get(selectedId) instanceof MenuButton subMenu) {
				selected = subMenu.isSelectedDeep(title);
			}
		
		return selected;
	}
	
	public final void setSelectedId(final int selectedId) {
		if(selectedId < 0 || selectedId >= itemList.size()) { throw new IndexOutOfBoundsException("Index out of bounds of selecting"); }
		this.selectedId = selectedId;
		if(isOpen) { isOpen = !isOpen; }
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
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getWidth()*2 < cxt.width;
		for(int i = 0; i < subMenu.length; i++) {
			itemList.add(subMenu[i]);
			subMenu[i].setRoot(root);
			if(isRoot) {
				subMenu[i].setBounds(getX(),getY()+getHeight()+listHeight,getWidth(),getHeight());
			} else {
				subMenu[i].setBounds(CAN_BE_IN_RIGHT_SIDE ? getX()+getWidth() : getX()-getWidth(),getY()+listHeight,getWidth(),getHeight());
			}
			listHeight += getHeight();
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
	protected final void onChangeBounds() {
		super.onChangeBounds();
		if(itemList == null) { return; }
		
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getWidth()*2 < cxt.width;
		
		listHeight = 0;
		for(int i = 0; i < itemList.size(); i++) {
			final Button ITEM = itemList.get(i);
			ITEM.setSize(getWidth(), getHeight());
			
			if(isRoot) {
				ITEM.setPosition(getX(),getY()+getHeight()*(i+1));
			} else {
				ITEM.setPosition(CAN_BE_IN_RIGHT_SIDE ? getX()+getWidth() : getX()-getWidth(),getY()+getHeight()*(1+i)-getHeight());
			}
			
			listHeight += ITEM.getHeight();
		}
		
		calculateMarkBounds();
		
		
	}
	
	public final void remove(final int index) {
		if(itemList == null || itemList.isEmpty() || index < 0 || index >= itemList.size()) { return; }
		
		itemList.remove(index);
		listHeight -= getHeight();
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getWidth()*2 < cxt.width;
		for(int i = 0; i < itemList.size(); i++) {
			final Button item = itemList.get(i);
			if(isRoot) {
				item.setPosition(getX(),getY()+getHeight()*(i+1));
			} else {
				item.setPosition(CAN_BE_IN_RIGHT_SIDE ? getX()+getWidth() : getX()-getWidth(),getY()+getHeight()*(1+i)-getHeight());
			}
		}
	}
	
	public final int getItemsCount() { return itemList.size(); }
	
	public final void setItemsColor(final Color color) {
		for(Button item : itemList) {
			item.getColor().set(color);
			
			if(item instanceof MenuButton subMenu) {
				subMenu.setItemsColor(color);
			}
		}
	}

	public final Button getItemDeep(final String title) {
		Button ref = null;
		
		for(Button item : itemList) {
			if(item.getText().equals(title)) {
				return item;
			}
		}
		
		for(Button item : itemList) {
			if(item instanceof MenuButton subMenu) {
				if(ref == null) {
					ref =  subMenu.getItemDeep(title);
				}
			}
		}
		
		if(isRoot && ref == null) { throw new IllegalArgumentException(title + "  is not exists"); }
		
		return ref;
	}
	
	public final Button getItem(final String title) {
		
		for(Button item : itemList) {
			if(item.getText().equals(title)) {
				return item;
			}
		}
		
		throw new IllegalArgumentException(title + "  is not exists");
	}
	
	public final ArrayList<Button> getItems() {
		return itemList;
	}
	
	public final boolean isMarkVisible() {
		return isMarkVisible;
	}

	public final void setMarkVisible(boolean markVisible) {
		this.isMarkVisible = markVisible;
	}
	
	public final void setMarksVisible(boolean markVisible) {
		this.isMarkVisible = markVisible;
		
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
		if(!isOpen) { return; }
		boolean inside = false;
		
		inside = checkInsideToAnyIn();
		
		if(cxt.mousePressed && isMouseOutside() && !inside) {
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
		
		if(!isOpen) {
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
			itemList.add(new Button(title[i],getX(),getY()+getHeight()+listHeight,getWidth(),getHeight()));
			listHeight += getHeight();
		}
	}
	
	private final void childListState(final String... title) {
		final boolean CAN_BE_IN_RIGHT_SIDE = getX()+getWidth()*2 < cxt.width; 
		for(int i = 0; i < title.length; i++) {
			itemList.add(new Button(title[i],CAN_BE_IN_RIGHT_SIDE ? getX()+getWidth() : getX()-getWidth(),getY()+listHeight,getWidth(),getHeight()));
			listHeight += getHeight();
		}
	}
	
	private final void setRoot(final MenuButton root) {
		if(root == null) { return; }
		this.root = root;
		if(root != this) { isRoot = false; }
	}
	
	private final void markOnDraw() {
			if(!isMarkVisible) { return; }
			cxt.pushStyle();
			cxt.noStroke();
			if(isOpen) { cxt.fill(0,255,0,128); } else { cxt.fill(255,128); }
			cxt.rect(markX,markY,markW,markH);
			cxt.popStyle();
			
	}
	
	private final void itemsOnDraw() {
		if(isOpen) {
			if(!itemList.isEmpty()) {
				
				for(int i = 0; i < itemList.size(); i++) {
					final Button item = itemList.get(i);
					item.draw();
					
					if(item.isClicked()) {
						selectedId = i;
						closeAllSubMenusWithoutSelected();
						
						if(!(item instanceof MenuButton)) {
							if(isAutoClose) {
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
		markX = getX()+getWidth()*.2f;
		markY = getY()+getHeight()*.8f;
		markW = getWidth()*.6f;
		markH = getHeight()*.05f;
		
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
				event.listen(itemList.get(0).getX(),getY()+getHeight(),itemList.get(0).getWidth(),listHeight);
			} else {
				event.listen(itemList.get(0).getX(),getY(),itemList.get(0).getWidth(),listHeight);
			}
		}
		
		private final void init(final MouseEvent e) {
			if(!enable || !event.inside() || itemList.size() <= 1) { return; }
			
			for(int i = 0; i < itemList.size(); i++) {
				final Button item = itemList.get(i);
				
				if(e.getCount() > 0) {
					item.setY(item.getY()+item.getHeight());
				} else {
					item.setY(item.getY()-item.getHeight());
				}

			}
						
			if(e.getCount() > 0) {
				itemList.add(0,itemList.remove(itemList.size()-1));
				itemList.get(0).setY(itemList.get(1).getY()-itemList.get(0).getHeight());
			} else {
				itemList.add(itemList.size()-1,itemList.remove(0));
				itemList.get(itemList.size()-1).setY(itemList.get(itemList.size()-2).getY()+itemList.get(itemList.size()-1).getHeight());
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