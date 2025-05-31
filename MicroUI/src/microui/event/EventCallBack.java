package microui.event;

import microui.MicroUI;
import microui.core.base.Bounds;

public final class EventCallBack {
	private final Bounds bounds;
	private OnClick onClick;
	private OnInside onInside;
	private OnOutside onOutside;
	private OnPressed onPressed;
	private OnLongPressed onLongPressed;
	private OnHolding onHolding;
	private boolean pressed,holding,enable;
	private float pressedCurrentDuration,pressedDurationMax;
	
	public EventCallBack(Bounds otherBounds) {
		bounds = otherBounds;
		enable = true;
		pressedDurationMax = 3;
	}
	
	
	public final void listen() {
		if(!enable) { return; }
		
		if(MicroUI.getContext().mousePressed) {
			pressed = true;
		} else {
			holding = false;
		}
		if(!inside()) { pressed = false; }
		
		if(onClick != null) { 
			if(clicked()) { onClick.action(); }
		}
		
		if(onInside != null) {
			if(inside()) { onInside.action(); }
		}
		
		if(onOutside != null) {
			if(outside()) { onOutside.action(); }
		}
		
		if(onPressed != null) {
			if(pressed()) { onPressed.action(); }
		}
		
		if(onLongPressed != null) {
			if(longPressed()) { onLongPressed.action(); }
		}
		
		if(onHolding != null) {
			if(holding()) { onHolding.action(); }
		}
	}
	
	public final boolean isEnable() {
		return enable;
	}

	public final void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public final float getPressedDurationMax() {
		return pressedDurationMax;
	}

	public final void setPressedDurationMax(float pressedDurationMax) {
		if(pressedDurationMax <= 0) { return; }
		this.pressedDurationMax = pressedDurationMax;
	}
	
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
	
	public interface OnClick { void action(); }
	
	public final void onClick(OnClick onClick) {
		this.onClick = onClick;
	}
	
	
	public interface OnInside { void action(); }
	
	public final void onInside(OnInside onInside) {
		this.onInside = onInside;
	}
	
	
	public interface OnOutside { void action(); }
	
	public final void onOutside(OnOutside onOutside) {
		this.onOutside = onOutside;
	}
	
	
	public interface OnPressed { void action(); }
	
	public final void onPressed(OnPressed onPressed) {
		this.onPressed = onPressed;
	}
	
	public interface OnLongPressed { void action(); }
	
	public final void onLongPressed(OnLongPressed onLongPressed) {
		this.onLongPressed = onLongPressed;
	}
	
	public interface OnHolding { void action(); }
	
	public final void onHolding(OnHolding onHolding) {
		this.onHolding = onHolding;
	}
}