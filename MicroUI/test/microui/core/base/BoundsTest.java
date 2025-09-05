package microui.core.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import microui.MicroUI;
import processing.core.PApplet;

class BoundsTest {

	@BeforeAll
	static void initMicroUI() {
		MicroUI.setContext(new PApplet());
	}
	
	@Test
	void initBoundsTest() {
		Bounds bounds = new Bounds(10,20,30,40) {
			@Override
			protected void update() {
			}
		};
		
		assertEquals(bounds.getX(),10);
		assertEquals(bounds.getY(),20);
		assertEquals(bounds.getWidth(),30);
		assertEquals(bounds.getHeight(),40);
		
	}
	
	@Test
	void setBoundsTest() {
		Bounds bounds = new Bounds(0,0,0,0) {
			@Override
			protected void update() {
			}
		};
		
		bounds.setBounds(10,20,30,40);
		
		assertEquals(bounds.getX(),10);
		assertEquals(bounds.getY(),20);
		assertEquals(bounds.getWidth(),30);
		assertEquals(bounds.getHeight(),40);
		
	}

	@Test
	void setConstrainDimensionsTest() {
		Bounds bounds = new Bounds(0,0,0,0) {
			@Override
			protected void update() {
			}
		};
		bounds.setConstrainDimensionsEnabled(true);
		bounds.setMaxSize(5);
		
		bounds.setSize(100,100);

		assertEquals(bounds.getWidth(),5);
		assertEquals(bounds.getHeight(),5);
		
		bounds.setMaxSize(10);
		
		bounds.setSize(100,100);

		assertEquals(bounds.getWidth(),10);
		assertEquals(bounds.getHeight(),10);
		
		bounds.setMinSize(5);
		
		bounds.setSize(1,1);

		assertEquals(bounds.getWidth(),5);
		assertEquals(bounds.getHeight(),5);
		
		bounds.setMinSize(8);
		
		bounds.setSize(1,1);

		assertEquals(bounds.getWidth(),8);
		assertEquals(bounds.getHeight(),8);
		
		bounds.setConstrainDimensionsEnabled(false);
		
		bounds.setSize(100,100);
		
		assertEquals(bounds.getWidth(),100);
		assertEquals(bounds.getHeight(),100);
		
		bounds.setConstrainDimensionsEnabled(true);
		
		assertEquals(bounds.getWidth(),10);
		assertEquals(bounds.getHeight(),10);
	}
	
	@Test
	void setNegativeDimensionsTest() {
		Bounds bounds = new Bounds(0,0,0,0) {
			{
				setNegativeDimensionsEnabled(true);
			}
			
			@Override
			protected void update() {
			}
			
		};
		
		bounds.setBounds(10,20,-30,-40);

		assertEquals(bounds.getWidth(),-30);
		assertEquals(bounds.getHeight(),-40);
		
	}
}
