package microui.event;

import java.util.ArrayList;
import java.util.List;

import microui.MicroUI;
import microui.core.base.Bounds;

public final class EventCallback {
	private final Bounds bounds;

	private List<OnClickListener> onClickList;
	private List<OnInsideListener> onInsideList;
	private List<OnOutsideListener> onOutsideList;
	private List<OnPressedListener> onPressedList;
	private List<OnLongPressedListener> onLongPressedList;
	private List<OnHoldingListener> onHoldingList;
	private List<OnDoubleClickListener> onDoubleClickList;
	private List<OnDraggedListener> onDraggedList;
	private List<OnDraggingListener> onDraggingList;
	private List<OnShakeListener> onShakeList;
	
	private boolean hasClickedStateTriggered, isHolding, isEnable, isClicked, isDragged;

	private int clickCount,distForShake;
	
	private long clickedTimePrev,clickedTimeNow,delta, pressStartTime,currentPressDuration,longPressThreshold,doubleClickThreshold;

	public EventCallback(Bounds otherBounds) {
		bounds = otherBounds;
		isEnable = true;
		final int ONE_SECOND = 1000, THREE_SECONDS = 3000, THREE_PIXELS = 3;
		longPressThreshold = THREE_SECONDS;
		doubleClickThreshold = ONE_SECOND;
		
		distForShake = THREE_PIXELS;
	}

	public final void listen() {
		if (!isEnable) { return; }

		if(isInside(bounds)) { isClicked = isClicked(); } else { hasClickedStateTriggered = false; }

		if (isPressed(bounds)) {
			isHolding = true;
			hasClickedStateTriggered = true;
			if(pressStartTime == 0) { pressStartTime = System.currentTimeMillis(); }
			currentPressDuration = System.currentTimeMillis()-pressStartTime;
		} else {
			pressStartTime = currentPressDuration = 0;
		}
		
		if(!MicroUI.getContext().mousePressed) {
			isHolding = false;
			isDragged = false;
		}
		
		listeners();
	}

	public final boolean isEnable() {
		return isEnable;
	}

	public final void setEnable(boolean enable) {
		this.isEnable = enable;
	}

	public final long getLongPressThreshold() {
		return longPressThreshold;
	}

	public final void setLongPressThreshold(long longPressThreshold) {
		if(longPressThreshold <= 0) { return; }
		this.longPressThreshold = longPressThreshold;
	}

	public final long getDoubleClickThreshold() {
		return doubleClickThreshold;
	}

	public final void setDoubleClickThreshold(long doubleClickThreshold) {
		if(doubleClickThreshold <= 0) { return; }
		this.doubleClickThreshold = doubleClickThreshold;
	}
	
	public final int getDistForShake() {
		return distForShake;
	}

	public final void setDistForShake(int distForShake) {
		if(distForShake <= 0) { return; }
		this.distForShake = distForShake;
	}

	public final void clearAll() {
		clearList(onClickList);
		clearList(onInsideList);
		clearList(onOutsideList);
		clearList(onPressedList);
		clearList(onLongPressedList);
		clearList(onHoldingList);
		clearList(onDoubleClickList);
		clearList(onDraggedList);
		clearList(onDraggingList);
		clearList(onShakeList);
	}
	
	public final void clear(EventType type) {
		switch(type) {
			case CLICKED : onClickList.clear(); break;
			case DOUBLE_CLICKED : onDoubleClickList.clear(); break; 
			case DRAGGED : onDraggedList.clear(); break; 
			case DRAGGING : onDraggingList.clear(); break; 
			case HOLDING : onHoldingList.clear(); break; 
			case INSIDE : onInsideList.clear(); break; 
			case OUTSIDE : onOutsideList.clear(); break; 
			case LONG_PRESSED : onLongPressedList.clear(); break; 
			case PRESSED : onPressedList.clear(); break;
			case SHAKE : onShakeList.clear(); break;
		}
	}
	
	public final void addOnClickListener(OnClickListener onClick) {
		if (onClickList == null) {
			onClickList = new ArrayList<OnClickListener>();
		}
		onClickList.add(onClick);
		
	}

	public final void addOnInsideListener(OnInsideListener onInside) {
		if (onInsideList == null) {
			onInsideList = new ArrayList<OnInsideListener>();
		}
		onInsideList.add(onInside);
	}

	public final void addOnOutsideListener(OnOutsideListener onOutside) {
		if (onOutsideList == null) {
			onOutsideList = new ArrayList<OnOutsideListener>();
		}
		onOutsideList.add(onOutside);
	}

	public final void addOnPressedListener(OnPressedListener onPressed) {
		if (onPressedList == null) {
			onPressedList = new ArrayList<OnPressedListener>();
		}
		onPressedList.add(onPressed);
	}

	public final void addOnLongPressedListener(OnLongPressedListener onLongPressed) {
		if (onLongPressedList == null) {
			onLongPressedList = new ArrayList<OnLongPressedListener>();
		}
		onLongPressedList.add(onLongPressed);
	}

	public final void addOnHoldingListener(OnHoldingListener onHolding) {
		if (onHoldingList == null) {
			onHoldingList = new ArrayList<OnHoldingListener>();
		}
		onHoldingList.add(onHolding);
	}

	public final void addOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
		if (onDoubleClickList == null) {
			onDoubleClickList = new ArrayList<OnDoubleClickListener>();
		}
		onDoubleClickList.add(onDoubleClickListener);
	}
	
	public final void addOnDraggedListener(OnDraggedListener onDraggedListener) {
		if(onDraggedList == null) {
			onDraggedList = new ArrayList<OnDraggedListener>();
		}
		onDraggedList.add(onDraggedListener);
	}
	
	public final void addOnDraggingListener(OnDraggingListener onDraggingListener) {
		if(onDraggingList == null) {
			onDraggingList = new ArrayList<OnDraggingListener>();
		}
		onDraggingList.add(onDraggingListener);
	}
	
	public final void addOnShakeListener(OnShakeListener onShakeListener) {
		if(onShakeList == null) {
			onShakeList = new ArrayList<OnShakeListener>();
		}
		onShakeList.add(onShakeListener);
	}

	
	private final boolean isClicked() {
		if (!MicroUI.getContext().mousePressed && isInside(bounds) && hasClickedStateTriggered) {
			hasClickedStateTriggered = false;
			updateDeltaTimeBetweenClicks();
			if(delta < doubleClickThreshold) { clickCount++; } else { clickCount = 0; }
			return true;
		}

		return false;
	}
	
	private final void listeners() {
		if (onClickList != null && isClicked) {
			onClickList.forEach(listener -> listener.onClick());
		}

		if (onInsideList != null && isInside(bounds)) {
			onInsideList.forEach(listener -> listener.onInside());
		}

		if (onOutsideList != null && isOutside(bounds)) {
			onOutsideList.forEach(listener -> listener.onOutside());
		}
		
		if (onPressedList != null && isPressed(bounds)) {
			onPressedList.forEach(listener -> listener.onPressed());
		}
		
		if (onLongPressedList != null && isLongPressed()) {
			onLongPressedList.forEach(listener -> listener.onLongPressed());
		}
		
		if (onHoldingList != null && isHolding) {
			onHoldingList.forEach(listener -> listener.onHolding());
		}
		
		if (onDoubleClickList != null && isDoubleClicked()) {
			onDoubleClickList.forEach(listener -> listener.onDoubleClick());
		}
		
		if(onDraggedList != null && isDragged()) {
			onDraggedList.forEach(listener -> listener.onDragged());
		}
		
		if(onDraggingList != null && isDragging()) {
			onDraggingList.forEach(listener -> listener.onDragging());
		}
		
		if(onShakeList != null && isShaking()) {
			onShakeList.forEach(listener -> listener.onShake());
		}
	}

	private static final boolean isInside(Bounds bounds) {
		return MicroUI.getContext().mouseX > bounds.getX()
			&& MicroUI.getContext().mouseX < bounds.getX() + bounds.getWidth()
			&& MicroUI.getContext().mouseY > bounds.getY()
			&& MicroUI.getContext().mouseY < bounds.getY() + bounds.getHeight();
	}

	private static final boolean isOutside(Bounds bounds) {
		return !isInside(bounds);
	}

	private static final boolean isPressed(Bounds bounds) {
		return isInside(bounds) && MicroUI.getContext().mousePressed;
	}

	private final boolean isLongPressed() {
		return currentPressDuration >= longPressThreshold;
	}

	private final boolean isDoubleClicked() {
		if (clickCount == 2) {
			clickCount = 0;
			return true;
		}
		return false;
	}
	
	private final boolean isDragged() {
		if(isDragging()) { isDragged = true; }
		
		return isDragged;
	}
	
	private final boolean isDragging() {
		if(isPressed(bounds)) {
			if(MicroUI.getContext().mouseX != MicroUI.getContext().pmouseX || MicroUI.getContext().mouseY != MicroUI.getContext().pmouseY) {
				return true;
			}
		}
		return false;
	}
	
	private final boolean isShaking() {
		if(Math.abs(MicroUI.getContext().mouseX - MicroUI.getContext().pmouseX) > distForShake || Math.abs(MicroUI.getContext().mouseY - MicroUI.getContext().pmouseY) > distForShake) {
			return true;
		}
		return false;
	}
	
	private final void updateDeltaTimeBetweenClicks() {
		clickedTimePrev = clickedTimeNow;
		clickedTimeNow = System.currentTimeMillis();
		delta = clickedTimeNow-clickedTimePrev;
	}
	
	private final void clearList(List<?> list) {
		if(list != null) { list.clear(); }
	}
	
}