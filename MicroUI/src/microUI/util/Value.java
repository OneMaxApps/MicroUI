package microUI.util;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

public class Value {
private float min,max,value;;

public Value(float min, float max, float value) {
	this.min = min;
	this.max = max;
	this.value = value;
}

public Value(float max) {
	set(0,max,0);
}

public Value() {
	set(0,100,0);
}

public float getMin() {
	return min;
}

public void setMin(float min) {
	if(min > max) { return; }
	this.min = min;
	action();
}

public float getMax() {
	return max;
}

public void setMax(float max) {
	if(max < min) { return; }
	this.max = max;
	action();
}

public float get() {
	return value;
}

public void set(float value) {
	if(value < min(min,max)) { this.value = min(min,max); return; }
	if(value > max(min,max)) { this.value = max(min,max); return; }
	this.value = value;
	action();
}

public void set(float min, float max, float value) {
	setMin(min);
	setMax(max);
	set(value);
	action();
}

public void setMinMax(float min, float max) {
	setMin(min);
	setMax(max);
	action();
}

public void append(float append) {
	value = constrain(value+append,min(min,max),max(min,max));
	action();
}

public void setWithoutActions(final float value) {
	this.value = value;
}

public void action() {}

}