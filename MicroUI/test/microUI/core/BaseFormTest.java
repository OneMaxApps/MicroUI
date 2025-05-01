package microUI.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import microUI.MicroUI;
import processing.core.PApplet;

class BaseFormTest {
	
	@Test
	void setW() {
		MicroUI.setContext(new PApplet());
		BaseForm baseForm = new BaseForm() { public void update() {} };
		baseForm.setW(-100);
		assertEquals(0,baseForm.getW());
	}
	
	@Test
	void setH() {
		MicroUI.setContext(new PApplet());
		BaseForm baseForm = new BaseForm() { public void update() {} };
		baseForm.setH(-100);
		assertEquals(0,baseForm.getH());
	}
}