package microUI.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import microUI.core.BaseForm;
import processing.core.PApplet;

class PhysicsTest {
	
	@Test
	void collisionTest() {
		PApplet app = new PApplet();
		
		BaseForm b,b1;
		b = new ComponentTest(app,0,0,100,100);
		b1 = new ComponentTest(app,50,50,100,100);
		
		assertTrue(Physics.collision(b, b1));
	}
	
	@Test
	void constrainTest() {
		PApplet app = new PApplet();
		
		BaseForm b,b1;
		b = new ComponentTest(app,0,0,100,100);
		b1 = new ComponentTest(app,50,50,100,100);
		
		Physics.constrain(50,50,b, b1);
		
		assertFalse(Physics.collision(b, b1));
	}
	
	private final class ComponentTest extends BaseForm {

		public ComponentTest(PApplet app, float x, float y, float w, float h) {
			super(app, x, y, w, h);
			
		}

		@Override
		public void update() {

		}
		
	}
}