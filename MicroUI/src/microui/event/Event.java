package microui.event;

import static java.util.Objects.requireNonNull;
import static microui.MicroUI.getContext;

import microui.MicroUI;
import microui.core.base.Component;
import microui.core.base.SpatialView;
import microui.util.Metrics;
import processing.core.PApplet;

public class Event {
	private static PApplet app = getContext();
	private static final int MIN_SHAKE_DIST = 3;
	private static final boolean[] keys = new boolean[Character.MAX_VALUE + 1];

	private float x, y, w, h;
	private byte wasPressed, longPressed, clickCounter;
	private int secondsSinceMouseInside;
	private boolean isHolding, isDragged, isEnabled;
	private SpatialView spatialView;

	public Event(SpatialView spatialView) {
		setListener(spatialView);
		isEnabled = true;
		Metrics.register(this);
	}
	
	public Event() {
		isEnabled = true;
		Metrics.register(this);
	}

	public final void setListener(SpatialView spatialView) {
		this.spatialView = requireNonNull(spatialView, "spatialView cannot be null");
	}

	public void listen() {
		if(spatialView == null) {
			throw new NullPointerException("Event system not have object for listening");
		}
		if (spatialView instanceof Component component) {
			listener(component.getPadX(), component.getPadY(), component.getPadWidth(), component.getPadHeight());
		} else {
			listener(spatialView.getX(), spatialView.getY(), spatialView.getWidth(), spatialView.getHeight());
		}
	}

	public void listen(float x, float y, float w, float h) {
		listener(x, y, w, h);
	}

	private final void listener(final float x, final float y, final float w, final float h) {
		if (!isEnabled) {
			return;
		}

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		if (!app.mousePressed) {
			isHolding = isDragged = false;
		}

		if (pressed()) {
			wasPressed = 1;
			if (app.frameCount % 60 == 0) {
				longPressed++;
			}
		} else {
			longPressed = 0;
		}

		if (outside()) {
			wasPressed = 0;
			secondsSinceMouseInside = 0;
		}

		if (isMouseShaking()) {
			secondsSinceMouseInside = 0;
		}

	}

	public final void setEnable(boolean enable) {
		this.isEnabled = enable;
	}

	public final boolean isEnable() {
		return isEnabled;
	}

	public static final boolean isMouseShaking() {
		return app.mouseX - app.pmouseX > MIN_SHAKE_DIST || app.mouseY - app.pmouseY > MIN_SHAKE_DIST;
	}

	public boolean inside() {
		if (w < 0 && h < 0) {
			return app.mouseX > x + w && app.mouseX < x && app.mouseY > y + h && app.mouseY < y;
		}

		if (w < 0) {
			return app.mouseX > x + w && app.mouseX < x && app.mouseY > y && app.mouseY < y + h;
		}

		if (h < 0) {
			return app.mouseX > x && app.mouseX < x + w && app.mouseY > y + h && app.mouseY < y;
		}

		return app.mouseX > x && app.mouseX < x + w && app.mouseY > y && app.mouseY < y + h;
	}

	public boolean inside(final int targetOfSeconds) {

		if (inside() && secondsSinceMouseInside != targetOfSeconds) {
			if (app.frameCount % 60 == 0) {
				secondsSinceMouseInside++;
			}
		}

		return inside() && secondsSinceMouseInside == targetOfSeconds;
	}

	public boolean outside() {
		return !inside();
	}

	public boolean pressed() {
		return inside() && app.mousePressed;
	}

	public boolean longPressed(int seconds) {
		if (pressed() && longPressed >= seconds) {
			return true;
		}
		return false;
	}

	public boolean isHolding() {
		if (pressed()) {
			return isHolding = true;
		} else {
			return isHolding;
		}

	}

	public boolean dragging() {
		return isHolding() && (app.mouseX != app.pmouseX || app.mouseY != app.pmouseY);
	}

	public boolean dragged() {
		if (dragging()) {
			isDragged = true;
		}

		return isDragged;
	}

	public boolean clicked() {
		if (inside() && !pressed() && wasPressed == 1) {
			wasPressed = 0;
			secondsSinceMouseInside = 0;
			return true;
		}
		return false;
	}

	public boolean clicked(int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("count cannot be less than 1");
		}
		if (clickCounter == count) {
			clickCounter = 0;
		} else {
			if (clicked()) {
				clickCounter++;
			} else {
				if (clickCounter != 0 && app.frameCount % 30 == 0) {
					clickCounter--;
				}
			}
		}
		return clickCounter == count;
	}

	/**
	 * Updating inside states of keys.
	 * <p>
	 * He must be called first in PApplet method keyPressed() for correct
	 * initialization.
	 * </p>
	 */
	public static final void keyPressed() {
		keys[MicroUI.getContext().key] = true;
		keys[MicroUI.getContext().keyCode] = true;
	}

	public static final boolean checkKey(int ch) {
		return keys[ch];
	}

	/**
	 * Updating inside states of keys.
	 * <p>
	 * He must be called first in PApplet method keyReleased() for correct
	 * initialization.
	 * </p>
	 */
	public static final void keyReleased() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}

	}

	public final void resetState() {
		keyReleased();
		longPressed = wasPressed = clickCounter = 0;
		secondsSinceMouseInside = 0;
		isHolding = isDragged = false;
	}

}