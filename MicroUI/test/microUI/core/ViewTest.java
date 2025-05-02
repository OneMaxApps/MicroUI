package microUI.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import init.TestInit;

class ViewTest extends TestInit {

	@Test
	void visible() {
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