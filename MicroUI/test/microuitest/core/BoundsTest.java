package microuitest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import init.TestInit;
import microui.core.base.Bounds;

class BoundsTest extends TestInit {
	private static Bounds bounds;
	
	@BeforeAll
	static void initBounds() {
		bounds = new Bounds() { public void update() {} };
	}
	
	@Test
	void setW() {
		bounds.setWidth(-100);
		assertEquals(0,bounds.getWidth());
	}
	
	@Test
	void setH() {
		bounds.setHeight(-100);
		assertEquals(0,bounds.getHeight());
	}
	
}