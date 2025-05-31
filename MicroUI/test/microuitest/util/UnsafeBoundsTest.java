package microuitest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import init.TestInit;
import microui.util.UnsafeBounds;

class UnsafeBoundsTest extends TestInit {
private static UnsafeBounds unsafeBounds;
	
	@BeforeAll
	static void initUnsafeBounds() {
		unsafeBounds = new UnsafeBounds() { public void update() {} };
		unsafeBounds.allowNegativeDimensions(true);
	}
	
	@Test
	void setNegativeW() {
		unsafeBounds.setWidth(-100);
		assertEquals(-100,unsafeBounds.getWidth());
	}
	
	@Test
	void setNegativeH() {
		unsafeBounds.setHeight(-100);
		assertEquals(-100,unsafeBounds.getHeight());
	}
	
}