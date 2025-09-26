package microui.component;

import static microui.core.style.theme.ThemeManager.getTheme;

import java.util.ArrayList;
import java.util.Arrays;

import microui.core.base.SpatialView;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Event;
import processing.event.MouseEvent;

// TODO make setting padding, color etc for items
public class MenuButton extends Button implements Scrollable {
	private boolean isOpen, isAutoCloseEnabled, isRoot, isMarkVisible;

	private int selectedId;

	private float listHeight;
	private float markX, markY, markW, markH;

	private final Color indicatorColor;

	private final ArrayList<Button> itemList;
	private final Scrolling scrolling;

	private MenuButton root;

	public MenuButton(String title, float x, float y, float w, float h) {
		super(title, x, y, w, h);
		setPriority(1);
		isAutoCloseEnabled = true;
		itemList = new ArrayList<Button>();
		selectedId = -1;
		isRoot = true;
		isMarkVisible = true;
		root = this;
		calculateMarkBounds();
		scrolling = new Scrolling();

		onClick(() -> {
			isOpen = !isOpen;
			if (!isOpen) {
				closeAllSubMenus();
			} else {
				selectedId = -1;
			}
		});

		indicatorColor = new Color(getTheme().getPrimaryColor());

	}

	public MenuButton(String title) {
		this(title,0,0,0,0);
		setSize(getMaxWidth(),getMaxHeight());
		setPosition(ctx.width/2-getMaxWidth()/2,ctx.height/2-getMaxHeight()/2);
	}

	public MenuButton(String title, String... items) {
		this(title);
		add(items);
	}

	public MenuButton() {
		this("Menu Button");
	}

	@Override
	protected final void render() {
		super.render();
		scrolling.update();

		itemsOnDraw();
		markOnDraw();
		checkClosingFromOut();
	}

	public final void setAutoClose(boolean autoClose) {
		this.isAutoCloseEnabled = autoClose;
	}

	public final boolean isOpen() {
		return isOpen;
	}

	public final void open() {
		this.isOpen = true;
	}

	public final void close() {
		this.isOpen = false;
	}

	public final int getSelectedId() {
		return selectedId;
	}

	public final boolean isSelected(final String title) {

		for (Button item : itemList) {
			if (item.getText().equals(title)) {
				return true;
			}
		}

		return false;
	}

	public final boolean isSelectedDeep(final String title) {
		if (selectedId == -1) {
			return false;
		}

		boolean selected = false;

		if (itemList.get(selectedId).getText().equals(title)) {
			selected = true;
		}

		if (selected) {
			return true;
		}

		if (itemList.get(selectedId) instanceof MenuButton subMenu) {
			selected = subMenu.isSelectedDeep(title);
		}

		return selected;
	}

	public final void setSelectedId(final int selectedId) {
		if (selectedId < 0 || selectedId >= itemList.size()) {
			throw new IndexOutOfBoundsException("Index out of bounds of selecting");
		}
		this.selectedId = selectedId;
		if (isOpen) {
			isOpen = !isOpen;
		}
	}

	public final MenuButton add(final String... title) {
		if (isRoot) {
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
		final boolean CAN_BE_IN_RIGHT_SIDE = getX() + getWidth() * 2 < ctx.width;
		for (int i = 0; i < subMenu.length; i++) {
			itemList.add(subMenu[i]);
			subMenu[i].setRoot(root);
			if (isRoot) {
				subMenu[i].setBounds(getX(), getY() + getHeight() + listHeight, getWidth(), getHeight());
			} else {
				subMenu[i].setBounds(CAN_BE_IN_RIGHT_SIDE ? getX() + getWidth() : getX() - getWidth(),
						getY() + listHeight, getWidth(), getHeight());
			}
			listHeight += getHeight();
		}

		setRootForSubMenus();

		requestUpdate();
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

	public final void remove(final int index) {
		if (itemList == null || itemList.isEmpty() || index < 0 || index >= itemList.size()) {
			return;
		}

		itemList.remove(index);
		listHeight -= getHeight();
		final boolean CAN_BE_IN_RIGHT_SIDE = getX() + getWidth() * 2 < ctx.width;
		for (int i = 0; i < itemList.size(); i++) {
			final Button item = itemList.get(i);
			if (isRoot) {
				item.setPosition(getX(), getY() + getHeight() * (i + 1));
			} else {
				item.setPosition(CAN_BE_IN_RIGHT_SIDE ? getX() + getWidth() : getX() - getWidth(),
						getY() + getHeight() * (1 + i) - getHeight());
			}
		}
	}

	public final int getItemsCount() {
		return itemList.size();
	}

	public final void setItemsColor(final Color color) {
		for (Button item : itemList) {
			item.setBackgroundColor(color);

			if (item instanceof MenuButton subMenu) {
				subMenu.setItemsColor(color);
			}
		}
	}

	public final Button getItemDeep(final String title) {
		Button ref = null;

		for (Button item : itemList) {
			if (item.getText().equals(title)) {
				return item;
			}
		}

		for (Button item : itemList) {
			if (item instanceof MenuButton subMenu) {
				if (ref == null) {
					ref = subMenu.getItemDeep(title);
				}
			}
		}

		if (isRoot && ref == null) {
			throw new IllegalArgumentException(title + "  is not exists");
		}

		return ref;
	}

	public final Button getItem(final String title) {

		for (Button item : itemList) {
			if (item.getText().equals(title)) {
				return item;
			}
		}

		throw new IllegalArgumentException(title + "  is not exists");
	}

	public final MenuButton getSubMenu(final String subMenuItem) {
		return (MenuButton) getItem(subMenuItem);
	}

	public final MenuButton getSubMenuDeep(final String subMenuItem) {
		return (MenuButton) getItemDeep(subMenuItem);
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
			if (i instanceof MenuButton subMenu) {
				subMenu.setMarksVisible(markVisible);
			}
		});
	}

	@Override
	public final void mouseWheel(MouseEvent e) {
		scrolling.init(e);

		for (Button item : itemList) {
			if (item instanceof MenuButton subMenu) {
				subMenu.mouseWheel(e);
			}
		}
	}

	@Override
	protected final void onChangeBounds() {
		super.onChangeBounds();
		if (itemList == null) {
			return;
		}

		final boolean canBeInRightSide = getX() + getWidth() * 2 < ctx.width;

		listHeight = 0;
		for (int i = 0; i < itemList.size(); i++) {
			final Button item = itemList.get(i);
			item.setConstrainDimensionsEnabled(false);
			item.setSize(getWidth(), getHeight());
			if (isRoot) {
				item.setPosition(getX(), getY() + getHeight() * (i + 1));
			} else {
				item.setPosition(canBeInRightSide ? getX() + getWidth() : getX() - getWidth(),
						getY() + getHeight() * (1 + i) - getHeight());
			}

			listHeight += item.getHeight();
		}

		calculateMarkBounds();

	}

	private final void checkClosingFromOut() {
		if (!isOpen) {
			return;
		}
		boolean inside = false;

		inside = checkInsideToAnyIn();

		if (ctx.mousePressed && isLeave() && !inside) {
			close();
		}

	}

	private final boolean checkInsideToAnyIn() {
		boolean insideState = false;
		if (scrolling.event.isEnter()) {
			insideState = true;
		}

		for (Button item : itemList) {
			if (item instanceof MenuButton subMenu) {
				if (subMenu.checkInsideToAnyIn() && subMenu.isOpen()) {
					insideState = true;
				}
			}
		}

		return insideState;
	}

	private final MenuButton setRootForSubMenus() {
		for (Button item : itemList) {
			if (item instanceof MenuButton subMenu) {
				subMenu.setRoot(root);
				for (Button innerItem : subMenu.itemList) {
					if (innerItem instanceof MenuButton innerSubMenu) {
						innerSubMenu.setRoot(root);
						innerSubMenu.setRootForSubMenus();
					}
				}
			}
		}
		return this;
	}

	private final void closeAllSubMenusWithoutSelected() {
		if (itemList.isEmpty() || selectedId == -1) {
			return;
		}
		for (int i = 0; i < itemList.size(); i++) {
			if (i == selectedId) {
				continue;
			}
			if (itemList.get(i) instanceof MenuButton subMenu) {
				subMenu.closeAllSubMenus();
				subMenu.close();
			}
		}
	}

	private final void closeAllSubMenus() {
		if (itemList.isEmpty()) {
			return;
		}
		
		if (!isOpen) {
			for (int i = 0; i < itemList.size(); i++) {
				if (itemList.get(i) instanceof MenuButton subMenu) {
					subMenu.close();
					subMenu.closeAllSubMenus();
				}
			}
		}

	}

	private void rootListState(String... title) {
		for (int i = 0; i < title.length; i++) {
			itemList.add(new Button(title[i], getX(), getY() + getHeight() + listHeight, getWidth(), getHeight()));
			listHeight += getHeight();
		}
	}

	private void childListState(String... title) {
		final boolean isCanToBeInRightSide = getX() + getWidth() * 2 < ctx.width;
		final float correctX = isCanToBeInRightSide ? getX() + getWidth() : getX() - getWidth();
		float correctY = 0;
		
		for (int i = 0; i < title.length; i++) {
			correctY = getY() + listHeight;
			itemList.add(new Button(title[i], correctX, correctY, getWidth(), getHeight()));
			listHeight += getHeight();
		}
		
	}

	private void setRoot(MenuButton root) {
		if (root == null) {
			return;
		}
		this.root = root;
		if (root != this) {
			isRoot = false;
		}
	}

	private void markOnDraw() {
		if (!isMarkVisible) {
			return;
		}
		ctx.pushStyle();
		ctx.noStroke();

		if (isOpen) {
			indicatorColor.apply();
		} else {
			ctx.fill(255, 128);
		}

		ctx.rect(markX, markY, markW, markH);
		ctx.popStyle();

	}

	private void calculateMarkBounds() {
		markX = getX() + getWidth() * .2f;
		markY = getY() + getHeight() * .8f;
		markW = getWidth() * .6f;
		markH = getHeight() * .05f;

	}

	private void itemsOnDraw() {
		if (!isOpen || itemList.isEmpty()) {
			return;
		}

		for (int i = 0; i < itemList.size(); i++) {
			Button item = itemList.get(i);
			item.draw();

			if (item.isClicked()) {
				selectedId = i;
				closeAllSubMenusWithoutSelected();

				if (!(item instanceof MenuButton)) {
					if (isAutoCloseEnabled) {
						root.close();
						root.closeAllSubMenus();
					}
				}
			}
		}
	}

	private final class Scrolling {
		private final Event event;
		private final SpatialView spatial;
		boolean enable;

		Scrolling() {
			spatial = new SpatialView() {
				@Override
				protected void render() {
				}
			};
			
			event = new Event(spatial);
			enable = true;
		}

		final void update() {
			event.listen();
			if (itemList.isEmpty()) {
				return;
			}

			if (isRoot) {
				spatial.setBounds(itemList.get(0).getX(), getY() + getHeight(), itemList.get(0).getWidth(), listHeight);
			} else {
				spatial.setBounds(itemList.get(0).getX(), getY(), itemList.get(0).getWidth(), listHeight);
			}
		}

		final void init(final MouseEvent e) {
			if (!enable || !event.isEnter() || itemList.size() <= 1) {
				return;
			}

			for (int i = 0; i < itemList.size(); i++) {
				final Button item = itemList.get(i);

				if (e.getCount() > 0) {
					item.setY(item.getY() + item.getHeight());
				} else {
					item.setY(item.getY() - item.getHeight());
				}

			}

			if (e.getCount() > 0) {
				itemList.add(0, itemList.remove(itemList.size() - 1));
				itemList.get(0).setY(itemList.get(1).getY() - itemList.get(0).getHeight());
			} else {
				itemList.add(itemList.size() - 1, itemList.remove(0));
				itemList.get(itemList.size() - 1)
						.setY(itemList.get(itemList.size() - 2).getY() + itemList.get(itemList.size() - 1).getHeight());
			}

		}
	}
}