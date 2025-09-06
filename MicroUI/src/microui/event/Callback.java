package microui.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Objects;

import microui.MicroUI;
import microui.core.base.Bounds;
import microui.core.base.Component;
import processing.core.PApplet;

public final class Callback {
	private static PApplet app = MicroUI.getContext();
	private static final int ONE_SECOND = 1000, TWO_SECONDS = 2000, THREE_SECONDS = 3000, THREE_PIXELS = 3;

	private Bounds bounds;

	private final EnumMap<EventType, ArrayList<Listener>> eventList = new EnumMap<>(EventType.class);

	private boolean hasClickedStateTriggered, isHold, isEnabled, isClicked, isDragged;

	private int clickCount, distForShake;

	private long clickedTimePrev, clickedTimeNow, delta, pressStartTime, currentPressDuration, longPressThreshold,
			doubleClickThreshold, insideTimerStart, timerInside, insideTimeThreshold;

	public Callback(Bounds otherBounds) {
		bounds = otherBounds;
		isEnabled = true;

		longPressThreshold = THREE_SECONDS;
		doubleClickThreshold = ONE_SECOND;

		distForShake = THREE_PIXELS;

		insideTimeThreshold = TWO_SECONDS;
	}

	public final void listen() {
		if (!isEnabled) {
			return;
		}

		inputUpdate();
		listeners();

	}

	public final void setListener(Bounds bounds) {
		this.bounds = bounds;
	}

	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

	public final long getLongPressThreshold() {
		return longPressThreshold;
	}

	public final void setLongPressThreshold(long longPressThreshold) {
		if (longPressThreshold <= 0) {
			return;
		}
		this.longPressThreshold = longPressThreshold;
	}

	public final long getDoubleClickThreshold() {
		return doubleClickThreshold;
	}

	public final void setDoubleClickThreshold(long doubleClickThreshold) {
		if (doubleClickThreshold <= 0) {
			return;
		}
		this.doubleClickThreshold = doubleClickThreshold;
	}

	public final int getDistForShake() {
		return distForShake;
	}

	public final void setDistForShake(int distForShake) {
		if (distForShake <= 0) {
			return;
		}
		this.distForShake = distForShake;
	}

	public final long getInsideTimeThreshold() {
		return insideTimeThreshold;
	}

	public final void setInsideTimeThreshold(long insideTimeThreshold) {
		if (insideTimeThreshold <= 0) {
			return;
		}
		this.insideTimeThreshold = insideTimeThreshold;
	}

	public final void clearAll() {
		eventList.forEach((event, list) -> {
			if (eventList.get(event) != null) {
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

	public final void remove(EventType type, Listener listener) {
		safeRemove(eventList.get(type), listener);
	}

	public final void remove(Listener listener) {
		for (EventType type : eventList.keySet()) {
			safeRemove(eventList.get(type), listener);
		}

	}

	public final void addListener(EventType type, Listener listener) {
		eventList.putIfAbsent(type, new ArrayList<Listener>());
		eventList.get(type).add(listener);

	}

	public final void resetInsideTimer() {
		timerInside = 0;
	}

	private final boolean isClicked() {
		if (!app.mousePressed && isInside(bounds) && hasClickedStateTriggered) {
			hasClickedStateTriggered = false;
			updateDeltaTimeBetweenClicks();
			if (delta < doubleClickThreshold) {
				clickCount++;
			} else {
				clickCount = 1;
			}
			return true;
		}
		return false;
	}

	private final void listeners() {
		eventList.forEach((event, list) -> {
			if (Objects.nonNull(list)) {
				switch (event) {
				case CLICK:
					if (isClicked) {
						list.forEach(listener -> listener.action());
					}
					break;

				case MOUSE_INSIDE:
					if (isInside(bounds)) {
						list.forEach(listener -> listener.action());
					}
					break;

				case MOUSE_OUTSIDE:
					if (isOutside()) {
						list.forEach(listener -> listener.action());
					}
					break;

				case PRESS:
					if (isPressed(bounds)) {
						list.forEach(listener -> listener.action());
					}
					break;

				case LONG_PRESSED:
					if (isLongPressed()) {
						list.forEach(listener -> listener.action());
					}
					break;

				case HOLD_START:
					if (isHold) {
						list.forEach(listener -> listener.action());
					}
					break;

				case DOUBLE_CLICK:
					if (isDoubleClicked()) {
						list.forEach(listener -> listener.action());
					}
					break;

				case DRAGGED:
					if (isDragged()) {
						list.forEach(listener -> listener.action());
					}
					break;

				case DRAGGING:
					if (isDragging(bounds)) {
						list.forEach(listener -> listener.action());
					}
					break;

				case SHAKE:
					if (isShaking(distForShake)) {
						list.forEach(listener -> listener.action());
					}
					break;

				case MOUSE_INSIDE_LONG:
					if (timerInside > insideTimeThreshold) {
						list.forEach(listener -> listener.action());
					}
					break;

				case RELEASE:
					if (!isPressed(bounds)) {
						list.forEach(listener -> listener.action());
					}
					break;

				case HOLD_END:
					if (!isHold) {
						list.forEach(listener -> listener.action());
					}
					break;
				}
			}
		});

	}

	private static final boolean isInside(final Bounds bounds) {

		if (bounds instanceof Component component) {
			if (component.getContentWidth() < 0 && component.getContentHeight() < 0) {
				return app.mouseX > component.getContentX() + component.getContentWidth()
						&& app.mouseX < component.getContentX()
						&& app.mouseY > component.getContentY() + component.getContentHeight()
						&& app.mouseY < component.getContentY();
			}
			if (component.getContentWidth() < 0) {
				return app.mouseX > component.getContentX() + component.getContentWidth()
						&& app.mouseX < component.getContentX() && app.mouseY > component.getContentY()
						&& app.mouseY < component.getContentY() + component.getContentHeight();
			}
			if (component.getContentHeight() < 0) {
				return app.mouseX > component.getContentX()
						&& app.mouseX < component.getContentX() + component.getContentWidth()
						&& app.mouseY > component.getContentY() + component.getContentHeight()
						&& app.mouseY < component.getContentY();
			}
			return app.mouseX > component.getContentX()
					&& app.mouseX < component.getContentX() + component.getContentWidth()
					&& app.mouseY > component.getContentY()
					&& app.mouseY < component.getContentY() + component.getContentHeight();
		}

		if (bounds.getWidth() < 0 && bounds.getHeight() < 0) {
			return app.mouseX > bounds.getX() + bounds.getWidth() && app.mouseX < bounds.getX()
					&& app.mouseY > bounds.getY() + bounds.getHeight() && app.mouseY < bounds.getY();
		}
		if (bounds.getWidth() < 0) {
			return app.mouseX > bounds.getX() + bounds.getWidth() && app.mouseX < bounds.getX()
					&& app.mouseY > bounds.getY() && app.mouseY < bounds.getY() + bounds.getHeight();
		}
		if (bounds.getHeight() < 0) {
			return app.mouseX > bounds.getX() && app.mouseX < bounds.getX() + bounds.getWidth()
					&& app.mouseY > bounds.getY() + bounds.getHeight() && app.mouseY < bounds.getY();
		}
		return app.mouseX > bounds.getX() && app.mouseX < bounds.getX() + bounds.getWidth()
				&& app.mouseY > bounds.getY() && app.mouseY < bounds.getY() + bounds.getHeight();
	}

	public final boolean isInside() {
		return isInside(bounds);
	}

	public final boolean isOutside() {
		return !isInside();
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
		if (isDragging(bounds)) {
			isDragged = true;
		}

		return isDragged;
	}

	private static final boolean isDragging(Bounds bounds) {
		if (isPressed(bounds)) {
			if (app.mouseX != app.pmouseX || app.mouseY != app.pmouseY) {
				return true;
			}
		}
		return false;
	}

	private static final boolean isShaking(int distForShake) {
		if (Math.abs(app.mouseX - app.pmouseX) > distForShake || Math.abs(app.mouseY - app.pmouseY) > distForShake) {
			return true;
		}
		return false;
	}

	private final void updateDeltaTimeBetweenClicks() {
		if (clickedTimeNow == 0) {
			clickedTimePrev = System.currentTimeMillis();
		}
		clickedTimePrev = clickedTimeNow;
		clickedTimeNow = System.currentTimeMillis();
		delta = clickedTimeNow - clickedTimePrev;
		if (delta >= doubleClickThreshold) {
			clickCount = 0;
		}
	}

	private final void clearList(EventType type) {
		if (eventList.get(type) != null) {
			eventList.get(type).clear();
		}
	}

	private final void inputUpdate() {
		if (isInside(bounds)) {
			isClicked = isClicked();
			if (timerInside == 0) {
				insideTimerStart = System.currentTimeMillis();
			}
			timerInside = (System.currentTimeMillis() - insideTimerStart) + 1;
		} else {
			hasClickedStateTriggered = false;
			timerInside = 0;
			clickCount = 0;
			isClicked = false;
		}

		if (isPressed(bounds)) {
			isHold = true;
			hasClickedStateTriggered = true;
			if (pressStartTime == 0) {
				pressStartTime = System.currentTimeMillis();
			}
			currentPressDuration = System.currentTimeMillis() - pressStartTime;
		} else {
			pressStartTime = currentPressDuration = 0;
		}

		if (!app.mousePressed) {
			isHold = false;
			isDragged = false;
		}
	}

	private static final <T> void safeRemove(ArrayList<T> list, int index) {
		if (list == null) {
			return;
		}
		if (list.isEmpty()) {
			return;
		}
		if (index < 0 || index > list.size() - 1) {
			return;
		}
		list.remove(index);
	}

	private static final <T> void safeRemove(ArrayList<T> list, Listener listener) {
		if (list == null) {
			return;
		}
		if (list.isEmpty()) {
			return;
		}
		if (listener == null) {
			return;
		}
		if (!list.contains(listener)) {
			return;
		}

		list.remove(listener);
	}
}