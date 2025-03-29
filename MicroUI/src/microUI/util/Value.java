package microUI.util;

import static processing.core.PApplet.constrain;

public final class Value {
private float min,max,value;;

public Value(float min, float max, float value) {
	this.min = min;
	this.max = max;
	this.value = value;
}

public float getMin() {
	return min;
}

public void setMin(float min) {
	if(min > max) { return; }
	this.min = min;
}

public float getMax() {
	return max;
}

public void setMax(float max) {
	if(max < min) { return; }
	this.max = max;
}

public float getValue() {
	return value;
}

public void setValue(float value) {
	if(value < min || value > max) {
		System.out.println("The value must be between the minimum and maximum");
		return;
	}
	this.value = value;
}

public void set(float min, float max, float value) {
	setMin(min);
	setMax(max);
	setValue(value);
}

public void setMinMax(float min, float max) {
	setMin(min);
	setMax(max);
}

public void append(float append) { value = constrain(value+append,min,max); }

}
