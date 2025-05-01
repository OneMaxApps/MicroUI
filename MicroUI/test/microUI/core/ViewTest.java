package microUI.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import microUI.MicroUI;
import processing.core.PApplet;

class ViewTest {

	@Test
	void visible() {
		MicroUI.setContext(new PApplet());
		View view = new View() { public void update() {} };
		
		view.setVisible(true);
		assertEquals(true,view.isVisible());
		
		view.setVisible(false);
		assertEquals(true,view.isInvisible());
		
		view.visible();
		assertEquals(true,view.isVisible());
		
		view.invisible();
		assertEquals(true,view.isInvisible());
	}
	
}