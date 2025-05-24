package microuitest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import init.TestInit;
import microUI.util.UnsafeBounds;

class UnsafeBoundsTest extends TestInit {
private static UnsafeBounds unsafeBounds;
	
	@BeforeAll
	static void initUnsafeBounds() {
		unsafeBounds = new UnsafeBounds() { public void update() {} };
		unsafeBounds.allowNegativeDimensions(true);
	}
	
	@Test
	void setNegativeW() {
		unsafeBounds.setW(-100);
		assertEquals(-100,unsafeBounds.getW());
	}
	
	@Test
	void setNegativeH() {
		unsafeBounds.setH(-100);
		assertEquals(-100,unsafeBounds.getH());
	}
	
}