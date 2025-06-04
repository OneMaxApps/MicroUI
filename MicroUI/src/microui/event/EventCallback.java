package microui.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Objects;

import microui.MicroUI;
import microui.core.base.Bounds;

public final class EventCallback extends MicroUI {
	private final Bounds bounds;
	
	private EnumMap<EventType, ArrayList<Listener>> eventList = new EnumMap<>(EventType.class);
	
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
		eventList.forEach((event, list) -> {
			if(eventList.get(event) != null) {
				clearList(event);
			}
		});
	}
	
	public final void clear(EventType type) {
		clearList(type);
	}
	
	public final void remove(EventType type, int index) {
		safeRemove(eventList.get(type), index);
	}
	
	public final void addListener(EventType type, Listener listener) {
		if(eventList.get(type) == null) { eventList.put(type, new ArrayList<Listener>()); }
		eventList.get(type).add(listener);
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
		eventList.forEach((event, list) -> {
			if(Objects.nonNull(list)) {
				switch(event) {
					case CLICKED :
						if(isClicked) { list.forEach(listener -> listener.action()); }
					break;
					
					case INSIDE :
						if(isInside(bounds)) { list.forEach(listener -> listener.action()); }
					break;
					
					case OUTSIDE :
						if(isOutside(bounds)) { list.forEach(listener -> listener.action()); }
					break;
					
					case PRESSED :
						if(isPressed(bounds)) { list.forEach(listener -> listener.action()); }
					break;
					
					case LONG_PRESSED :
						if(isLongPressed()) { list.forEach(listener -> listener.action()); }
					break;
					
					case HOLDING :
						if(isHolding) { list.forEach(listener -> listener.action()); }
					break;
					
					case DOUBLE_CLICKED :
						if(isDoubleClicked()) { list.forEach(listener -> listener.action()); }
					break;
					
					case DRAGGED :
						if(isDragged()) { list.forEach(listener -> listener.action()); }
					break;
					
					case DRAGGING :
						if(isDragging(bounds)) { list.forEach(listener -> listener.action()); }
					break;
					
					case SHAKE :
						if(isShaking(distForShake)) { list.forEach(listener -> listener.action()); }
					break;
					
					case INSIDE_LONG :
						if(insideTimer > insideThreshold) { list.forEach(listener -> listener.action()); }
					break;
				}
			}
		});
		
	}

	// TODO: Replace it to method inside in Event
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
	
	private final void clearList(EventType type) {
		if(eventList.get(type) != null) {
			eventList.get(type).clear();
		}
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
	
	private static final <T> void safeRemove(ArrayList<T> list, int index) {
		if(list == null) { return; }
		if(list.isEmpty()) { return; }
		if(index < 0 || index > list.size()-1) { return; }
		list.remove(index);
	}
}