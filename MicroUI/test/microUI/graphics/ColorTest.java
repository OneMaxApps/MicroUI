package microUI.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ColorTest {
	
	@Test
	void getRed() {
		Color color = new Color();
		color.setRed(123);
		assertEquals(123,(int) color.getRed());
	}
	
	@Test
	void getGreen() {
		Color color = new Color();
		color.setGreen(123);
		assertEquals(123,(int) color.getGreen());
	}
	
	@Test
	void getBlue() {
		Color color = new Color();
		color.setBlue(123);
		assertEquals(123,(int) color.getBlue());
	}
	
	@Test
	void getAlpha() {
		Color color = new Color();
		color.setAlpha(123);
		assertEquals(123,(int) color.getAlpha());
	}
	
	@Test
	void isTransparent() {
		Color color = new Color(123,212,222,0);
		assertEquals(true,color.isTransparent());
	}
}