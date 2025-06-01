package microui.event;

import microui.MicroUI;
import microui.core.base.Bounds;

public final class EventCallBack {
	private final Bounds bounds;
	
	private OnClickListener   		onClickListener;
	private OnInsideListener  		onInsideListener;
	private OnOutsideListener 		onOutsideListener;
	private OnPressedListener 		onPressedListener;
	private OnLongPressedListener 	onLongPressedListener;
	private OnHoldingListener 		onHoldingListener;
	
	private boolean pressed,holding,enable;
	
	private float pressedCurrentDuration,pressedDurationMax;
	
	public EventCallBack(Bounds otherBounds) {
		bounds = otherBounds;
		enable = true;
		pressedDurationMax = 3;
	}
	
	public final void listen() {
		if(!enable) { return; }
		
		if(pressed()) { pressed = true; } else { holding = false; }
		
		if(!inside()) { pressed = false; }
		
		if(onClickListener != null && clicked()) 			{ onClickListener.onClick(); }
		if(onInsideListener != null && inside()) 			{ onInsideListener.onInside(); }
		if(onOutsideListener != null && outside())		    { onOutsideListener.onOutside(); }
		if(onPressedListener != null && pressed())			{ onPressedListener.onPressed(); }
		if(onLongPressedListener != null && longPressed())  { onLongPressedListener.onLongPressed(); }
		if(onHoldingListener != null && holding()) 			{ onHoldingListener.onHolding(); }
	}
	
	public final boolean isEnable() { return enable; }
	public final void setEnable(boolean enable) { this.enable = enable; }
	
	public final float getPressedDurationMax() { return pressedDurationMax; }
	public final void setPressedDurationMax(float pressedDurationMax) {
		if(pressedDurationMax <= 0) { return; }
		this.pressedDurationMax = pressedDurationMax;
	}
	
	public final void setOnClickListener(OnClickListener onClick) { this.onClickListener = onClick; }
	public final void setOnInsideListener(OnInsideListener onInside) { this.onInsideListener = onInside; }
	public final void setOnOutsideListener(OnOutsideListener onOutside) { this.onOutsideListener = onOutside; }
	public final void setOnPressedListener(OnPressedListener onPressed) { this.onPressedListener = onPressed; }
	public final void setOnLongPressedListener(OnLongPressedListener onLongPressed) { this.onLongPressedListener = onLongPressed; }
	public final void setOnHoldingListener(OnHoldingListener onHolding) { this.onHoldingListener = onHolding; }
	
	private final boolean clicked() {
		if(!MicroUI.getContext().mousePressed && inside() && pressed) {
			pressed = false;
			return true;
		}
		
		return false;
	}
	private final boolean inside() {
		return MicroUI.getContext().mouseX > bounds.getX()
			&& MicroUI.getContext().mouseX < bounds.getX()+bounds.getWidth()
			&& MicroUI.getContext().mouseY > bounds.getY()
			&& MicroUI.getContext().mouseY < bounds.getY()+bounds.getHeight();
	}
	private final boolean outside() {
		return !inside();
	}
	private final boolean pressed() {
		return inside() && MicroUI.getContext().mousePressed;
	}
	private final boolean longPressed() {
		if(pressed()) {
			if(pressedCurrentDuration < pressedDurationMax) {
				if(MicroUI.getContext().frameCount%60 == 0) { pressedCurrentDuration++; }
			}
		} else {
			pressedCurrentDuration = 0;
		}
		
		return pressed() && pressedCurrentDuration >= pressedDurationMax;
	}
	private final boolean holding() {
		if(pressed()) { holding = true; }
		
		return MicroUI.getContext().mousePressed && holding;
	}

	public interface OnClickListener { void onClick(); }
	public interface OnInsideListener { void onInside(); }
	public interface OnOutsideListener { void onOutside(); }
	public interface OnPressedListener { void onPressed(); }
	public interface OnLongPressedListener { void onLongPressed(); }
	public interface OnHoldingListener { void onHolding(); }
}