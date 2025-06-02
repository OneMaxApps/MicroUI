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

	private boolean clickedStateTrigger, isHolding, isEnable, clicked;

	private float pressedCurrentDuration, pressedDurationMax;
	private int countOfClicks;

	public EventCallback(Bounds otherBounds) {
		bounds = otherBounds;
		isEnable = true;
		pressedDurationMax = 3;
	}

	public final void listen() {
		if (!isEnable) {
			return;
		}

		clicked = clickedStateUpdate();

		if (pressed()) {
			clickedStateTrigger = true;
		} else {
			isHolding = false;
		}

		if (outside()) {
			clickedStateTrigger = false;
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

	public final float getPressedDurationMax() {
		return pressedDurationMax;
	}

	public final void setPressedDurationMax(float pressedDurationMax) {
		if (pressedDurationMax <= 0) {
			return;
		}
		this.pressedDurationMax = pressedDurationMax;
	}

	public final void clearAll() {
		onClickList.clear();
		onInsideList.clear();
		onOutsideList.clear();
		onPressedList.clear();
		onLongPressedList.clear();
		onHoldingList.clear();
		onDoubleClickList.clear();
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
		if (!MicroUI.getContext().mousePressed && inside() && clickedStateTrigger) {
			clickedStateTrigger = false;
			countOfClicks++;
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
		if (pressed()) {
			if (pressedCurrentDuration < pressedDurationMax) {
				if (MicroUI.getContext().millis() % 60 == 0) {
					pressedCurrentDuration++;
				}
			}
		} else {
			pressedCurrentDuration = 0;
		}

		return pressed() && pressedCurrentDuration >= pressedDurationMax;
	}

	private final boolean holding() {
		if (pressed()) {
			isHolding = true;
		}

		return MicroUI.getContext().mousePressed && isHolding;
	}

	private final boolean doubleClicked() {
		if (MicroUI.getContext().millis() % 60 == 0) {
			if (countOfClicks != 0) {
				countOfClicks--;
			}
		}

		if (countOfClicks == 2) {
			countOfClicks = 0;
			return true;
		}

		return false;
	}

	public interface OnClickListener {
		void onClick();
	}

	public interface OnInsideListener {
		void onInside();
	}

	public interface OnOutsideListener {
		void onOutside();
	}

	public interface OnPressedListener {
		void onPressed();
	}

	public interface OnLongPressedListener {
		void onLongPressed();
	}

	public interface OnHoldingListener {
		void onHolding();
	}

	public interface OnDoubleClickListener {
		void onDoubleClick();
	}
}