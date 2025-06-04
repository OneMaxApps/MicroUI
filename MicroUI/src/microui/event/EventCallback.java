package microui.event;

import java.util.ArrayList;
import java.util.List;

import microui.MicroUI;
import microui.core.base.Bounds;

public final class EventCallback extends MicroUI {
	private final Bounds bounds;

	private ArrayList<OnClickListener> onClickList;
	private ArrayList<OnInsideListener> onInsideList;
	private ArrayList<OnOutsideListener> onOutsideList;
	private ArrayList<OnPressedListener> onPressedList;
	private ArrayList<OnLongPressedListener> onLongPressedList;
	private ArrayList<OnHoldingListener> onHoldingList;
	private ArrayList<OnDoubleClickListener> onDoubleClickList;
	private ArrayList<OnDraggedListener> onDraggedList;
	private ArrayList<OnDraggingListener> onDraggingList;
	private ArrayList<OnShakeListener> onShakeList;
	private ArrayList<OnInsideLongListener> onInsideLongList;
	
	private boolean hasClickedStateTriggered, isHolding, isEnable, isClicked, isDragged;

	private int clickCount,distForShake;
	
	private long clickedTimePrev,clickedTimeNow,delta,
				 pressStartTime,currentPressDuration,
				 longPressThreshold,doubleClickThreshold,
				 insideTimerStart,insideTimer,insideThreshold;

	public EventCallback(Bounds otherBounds) {
		bounds = otherBounds;
		isEnable = true;
		final int ONE_SECOND = 1000, TWO_SECONDS = 2000, THREE_SECONDS = 3000, THREE_PIXELS = 3;
		longPressThreshold = THREE_SECONDS;
		doubleClickThreshold = ONE_SECOND;
		
		distForShake = THREE_PIXELS;
		
		insideThreshold = TWO_SECONDS;
	}

	public final void listen() {
		if (!isEnable) { return; }
		inputUpdate();
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
	
	public final long getInsideThreshold() {
		return insideThreshold;
	}

	public final void setInsideThreshold(long insideThreshold) {
		if(insideThreshold <= 0) { return; }
		this.insideThreshold = insideThreshold;
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
		clearList(onInsideLongList);
	}
	
	public final void clear(EventType type) {
		switch(type) {
			case CLICKED : clearList(onClickList); break;
			case DOUBLE_CLICKED : clearList(onDoubleClickList); break; 
			case DRAGGED : clearList(onDraggedList); break; 
			case DRAGGING : clearList(onDraggingList); break; 
			case HOLDING : clearList(onHoldingList); break; 
			case INSIDE : clearList(onInsideList); break; 
			case OUTSIDE : clearList(onOutsideList); break; 
			case LONG_PRESSED : clearList(onLongPressedList); break; 
			case PRESSED : clearList(onPressedList); break;
			case SHAKE : clearList(onShakeList); break;
			case INSIDE_LONG : clearList(onInsideLongList); break;
		}
	}
	
	public final void remove(EventType type, int index) {
		switch(type) {
			case CLICKED : safeRemove(onClickList, index); break;
			case DOUBLE_CLICKED : safeRemove(onDoubleClickList, index); break; 
			case DRAGGED : safeRemove(onDraggedList, index); break; 
			case DRAGGING : safeRemove(onDraggingList, index); break; 
			case HOLDING : safeRemove(onHoldingList, index); break; 
			case INSIDE : safeRemove(onInsideList, index); break; 
			case OUTSIDE : safeRemove(onOutsideList, index); break; 
			case LONG_PRESSED : safeRemove(onLongPressedList, index); break; 
			case PRESSED : safeRemove(onPressedList, index); break;
			case SHAKE : safeRemove(onShakeList, index); break;
			case INSIDE_LONG : safeRemove(onInsideLongList, index); break;
		}
		
	}
	
	private static final <T> void safeRemove(ArrayList<T> list, int index) {
		if(list == null) { return; }
		if(list.isEmpty()) { return; }
		if(index < 0 || index > list.size()-1) { return; }
		list.remove(index);
	}

	public final void addOnClickListener(OnClickListener onClick) {
		onClickList = createIfAbsent(onClickList);
		onClickList.add(onClick);
	}

	public final void addOnInsideListener(OnInsideListener onInside) {
		onInsideList = createIfAbsent(onInsideList);
		onInsideList.add(onInside);
	}

	public final void addOnOutsideListener(OnOutsideListener onOutside) {
		onOutsideList = createIfAbsent(onOutsideList);
		onOutsideList.add(onOutside);
	}

	public final void addOnPressedListener(OnPressedListener onPressed) {
		onPressedList = createIfAbsent(onPressedList);
		onPressedList.add(onPressed);
	}

	public final void addOnLongPressedListener(OnLongPressedListener onLongPressed) {
		onLongPressedList = createIfAbsent(onLongPressedList);
		onLongPressedList.add(onLongPressed);
	}

	public final void addOnHoldingListener(OnHoldingListener onHolding) {
		onHoldingList = createIfAbsent(onHoldingList);
		onHoldingList.add(onHolding);
	}

	public final void addOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
		onDoubleClickList = createIfAbsent(onDoubleClickList);
		onDoubleClickList.add(onDoubleClickListener);
	}
	
	public final void addOnDraggedListener(OnDraggedListener onDraggedListener) {
		onDraggedList = createIfAbsent(onDraggedList);
		onDraggedList.add(onDraggedListener);
	}
	
	public final void addOnDraggingListener(OnDraggingListener onDraggingListener) {
		onDraggingList = createIfAbsent(onDraggingList);
		onDraggingList.add(onDraggingListener);
	}
	
	public final void addOnShakeListener(OnShakeListener onShakeListener) {
		onShakeList = createIfAbsent(onShakeList);
		onShakeList.add(onShakeListener);
	}
	
	public final void addOnInsideLongListener(OnInsideLongListener onInsideLongListener) {
		onInsideLongList = createIfAbsent(onInsideLongList);
		onInsideLongList.add(onInsideLongListener);
	}
	
	private static final <T> ArrayList<T> createIfAbsent(ArrayList<T> list) {
		if(list == null) { list = new ArrayList<T>(); }
		return list;
	}
	
	private final boolean isClicked() {
		if (!app.mousePressed && isInside(bounds) && hasClickedStateTriggered) {
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
		
		if(onDraggingList != null && isDragging(bounds)) {
			onDraggingList.forEach(listener -> listener.onDragging());
		}
		
		if(onShakeList != null && isShaking(distForShake)) {
			onShakeList.forEach(listener -> listener.onShake());
		}
		
		if(onInsideLongList != null && insideTimer > insideThreshold) {
			onInsideLongList.forEach(listener -> listener.onInsideLong());
		}
	}

	private static final boolean isInside(Bounds bounds) {
		return app.mouseX > bounds.getX()
			&& app.mouseX < bounds.getX() + bounds.getWidth()
			&& app.mouseY > bounds.getY()
			&& app.mouseY < bounds.getY() + bounds.getHeight();
	}

	private static final boolean isOutside(Bounds bounds) {
		return !isInside(bounds);
	}

	private static final boolean isPressed(Bounds bounds) {
		return isInside(bounds) && app.mousePressed;
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
		if(isDragging(bounds)) { isDragged = true; }
		
		return isDragged;
	}
	
	private static final boolean isDragging(Bounds bounds) {
		if(isPressed(bounds)) {
			if(app.mouseX != app.pmouseX || app.mouseY != app.pmouseY) {
				return true;
			}
		}
		return false;
	}
	
	private static final boolean isShaking(int distForShake) {
		if(Math.abs(app.mouseX - app.pmouseX) > distForShake || Math.abs(app.mouseY - app.pmouseY) > distForShake) {
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
	
	private final void inputUpdate() {
		if(isInside(bounds)) {
			isClicked = isClicked();
			if(insideTimer == 0) { insideTimerStart = System.currentTimeMillis(); }
			insideTimer = (System.currentTimeMillis()-insideTimerStart)+1;
		} else {
			hasClickedStateTriggered = false;
			insideTimer = 0;
		}
		
		if (isPressed(bounds)) {
			isHolding = true;
			hasClickedStateTriggered = true;
			if(pressStartTime == 0) { pressStartTime = System.currentTimeMillis(); }
			currentPressDuration = System.currentTimeMillis()-pressStartTime;
		} else {
			pressStartTime = currentPressDuration = 0;
		}
		
		if(!app.mousePressed) {
			isHolding = false;
			isDragged = false;
		}
	}
}