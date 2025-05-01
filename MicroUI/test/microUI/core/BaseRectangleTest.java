package microUI.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import microUI.MicroUI;
import processing.core.PApplet;

class BaseRectangleTest {
	
	@Test
	void setW() {
		MicroUI.setContext(new PApplet());
		AbstractRectangle baseRectangle = new AbstractRectangle() { public void update() {} };
		baseRectangle.setW(-100);
		assertEquals(0,baseRectangle.getW());
	}
	
	@Test
	void setH() {
		MicroUI.setContext(new PApplet());
		AbstractRectangle baseForm = new AbstractRectangle() { public void update() {} };
		baseForm.setH(-100);
		assertEquals(0,baseForm.getH());
	}
}