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

	private boolean hasClickedStateTriggered, isHolding, isEnable, clicked;

	private int countOfClicks;
	
	private long clickedTimePrev,clickedTimeNow,delta, millisToPressed,millisToPressedNow,millisToPressedMax;

	public EventCallback(Bounds otherBounds) {
		bounds = otherBounds;
		isEnable = true;
		final int THREE_SECONDS = 3000;
		millisToPressedMax = THREE_SECONDS;
	}

	public final void listen() {
		if (!isEnable) {
			return;
		}

		clicked = clickedStateUpdate();

		if (pressed()) {
			hasClickedStateTriggered = true;
			if(millisToPressed == 0) { millisToPressed = System.currentTimeMillis(); }
			millisToPressedNow = System.currentTimeMillis()-millisToPressed;
		} else {
			isHolding = false;
			millisToPressed = millisToPressedNow = 0;
		}

		if (outside()) {
			hasClickedStateTriggered = false;
		}

		if (onClickList != null && clicked) {
			onClickList.forEach(listener -> listener.onClick());
		}

		if (onInsideList != null && inside()) {
			onInsideList.forEach(listener -> listener.onInside());
		}

		if (onOutsideList != null && outside()) {
			onOutsideList.forEach(listener -> listener.onOutside());
		}
		
		if (onPressedList != null && pressed()) {
			onPressedList.forEach(listener -> listener.onPressed());
		}
		
		if (onLongPressedList != null && longPressed()) {
			onLongPressedList.forEach(listener -> listener.onLongPressed());
		}
		
		if (onHoldingList != null && holding()) {
			onHoldingList.forEach(listener -> listener.onHolding());
		}
		
		if (onDoubleClickList != null && doubleClicked()) {
			onDoubleClickList.forEach(listener -> listener.onDoubleClick());
		}

	}

	public final boolean isEnable() {
		return isEnable;
	}

	public final void setEnable(boolean enable) {
		this.isEnable = enable;
	}

	public final long getMillisToPressedMax() {
		return millisToPressedMax;
	}

	public final void setMillisToPressedMax(long millisToPressedMax) {
		if(millisToPressedMax <= 0) { return; }
		this.millisToPressedMax = millisToPressedMax;
	}

	public final void clearAll() {
		if(onClickList != null) { onClickList.clear(); }
		if(onInsideList != null) { onInsideList.clear(); }
		if(onOutsideList != null) { onOutsideList.clear(); }
		if(onPressedList != null) { onPressedList.clear(); }
		if(onLongPressedList != null) { onLongPressedList.clear(); }
		if(onHoldingList != null) { onHoldingList.clear(); }
		if(onDoubleClickList != null) { onDoubleClickList.clear(); }
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

	private final boolean clickedStateUpdate() {
		if (!MicroUI.getContext().mousePressed && inside() && hasClickedStateTriggered) {
			hasClickedStateTriggered = false;
			updateDeltaTimeBetweenClicks();
			if(delta < 1000) { countOfClicks++; } else { countOfClicks = 0; }
			return true;
		}

		return false;
	}

	private final boolean inside() {
		return MicroUI.getContext().mouseX > bounds.getX()
			&& MicroUI.getContext().mouseX < bounds.getX() + bounds.getWidth()
			&& MicroUI.getContext().mouseY > bounds.getY()
			&& MicroUI.getContext().mouseY < bounds.getY() + bounds.getHeight();
	}

	private final boolean outside() {
		return !inside();
	}

	private final boolean pressed() {
		return inside() && MicroUI.getContext().mousePressed;
	}

	private final boolean longPressed() {
		System.out.println(millisToPressedNow);
		return millisToPressedNow >= millisToPressedMax;
	}

	private final boolean holding() {
		if (pressed()) {
			isHolding = true;
		}

		return MicroUI.getContext().mousePressed && isHolding;
	}

	private final boolean doubleClicked() {
		if (countOfClicks == 2) {
			countOfClicks = 0;
			return true;
		}
		return false;
	}
	
	private final void updateDeltaTimeBetweenClicks() {
		clickedTimePrev = clickedTimeNow;
		clickedTimeNow = System.currentTimeMillis();
		delta = clickedTimeNow-clickedTimePrev;
	}
	
	
}