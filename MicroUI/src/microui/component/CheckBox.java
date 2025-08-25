package microui.component;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static microui.event.EventType.CLICK;
import static processing.core.PConstants.CENTER;

import microui.core.AbstractButton;
import microui.core.style.Color;

public class CheckBox extends AbstractButton {
	private boolean included;
	private final Color checkMarkColor;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		Color tmpColor = new Color();
		
		tmpColor.set(100);
		setStrokeColor(tmpColor);
		
		color.set(200);
		
		tmpColor.set(0,100,255,24);
		hover.setColor(tmpColor);
		
		callback.addListener(CLICK, () -> included = !included);

		checkMarkColor = new Color(0);
	}

	public CheckBox(boolean include) {
		this();
		setState(include);
	}

	public CheckBox() {
		this(app.width * .4f, app.height * .4f, max(app.width * .2f, app.height * .2f),max(app.width * .2f, app.height * .2f));
	}

	@Override
	protected void update() {
		super.update();

		if (included) {
			app.pushStyle();
			app.strokeWeight((min(w,h)/5)+1);
			app.stroke(checkMarkColor.get());
			app.noFill();
			app.rectMode(CENTER);
			app.rect(x + w / 2, y + h / 2, w / 2, h / 2);
			app.popStyle();
		}
	}

	public boolean isIncluded() {
		return included;
	}

	public void setState(boolean state) {
		included = state;
	}

	public final Color getCheckMarkColor() {
		return new Color(checkMarkColor);
	}
	
	public final void setCheckMarkColor(Color color) {
		checkMarkColor.set(color);
	}
}
