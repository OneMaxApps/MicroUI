package microUI.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import microUI.util.View;
import processing.core.PApplet;

class ViewTest {

	@Test
	void visible() {
		View view = new View(new PApplet()) { public void update() {} };
		
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