package microUI.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import init.TestInit;
import microUI.MicroUI;
import processing.core.PApplet;

class EventTest extends TestInit {
	private PApplet app = MicroUI.getContext();
	private static Event event;
	
	@BeforeAll
	static void initEvent() {
		event = new Event();
		event.listen(0,0,100,100);
		
	}
	
	@Test
	void pressedTest() {
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		assertTrue(event.pressed());
	}
	
	@Test
	void insideTest() {
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		
		
		assertTrue(event.inside());
	}
	
	@Test
	void ousideTest() {
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		
		
		assertFalse(event.outside());
	}
	
	@Test
	void longPressedTest() {
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		final int FRAMES_FOR_THREE_SECONDS = 3*60;
		for(int i = 0; i < FRAMES_FOR_THREE_SECONDS; i++) {
			app.frameCount++;
			event.listen(0,0,100,100);
		}
		
		assertTrue(event.longPressed(3));
		
	}
	
	@Test
	void enableTest() {
		
		event.setEnable(true);
		assertTrue(event.isEnable());
	}
	
	@Test
	void movedTest() {
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		event.listen(0,0,100,100);
		
		assertTrue(event.holding());
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = false;
		
		event.listen(0,0,100,100);
		
		assertFalse(event.holding());
		
	}
	
	@Test
	void clickedTest() {
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		event.listen(0,0,100,100);
		
		app.mousePressed = false;
		
		assertTrue(event.clicked());
	}
	
	@Test
	void multiClickedTest() {
		
		app.mouseX = 50;
		app.mouseY = 50;
		
		{
		final int FRAMES_COUNT = 2;
		boolean result = false;
		for(int i = 0; i < FRAMES_COUNT; i++) {
			app.frameCount++;
			
			app.mousePressed = true;
			event.listen(0,0,100,100);
			app.mousePressed = false;
			
			result = event.clicked(2);
		}
		event.resetState();
		
		assertTrue(result);
		}
		
		{
			final int FRAMES_COUNT = 3;
			boolean result = false;
			for(int i = 0; i < FRAMES_COUNT; i++) {
				app.frameCount++;
				
				app.mousePressed = true;
				event.listen(0,0,100,100);
				app.mousePressed = false;
				
				result = event.clicked(3);
			}
			event.resetState();
			
			assertTrue(result);
		}
		
		{
			final int FRAMES_COUNT = 4;
			boolean result = false;
			for(int i = 0; i < FRAMES_COUNT; i++) {
				app.frameCount++;
				
				app.mousePressed = true;
				event.listen(0,0,100,100);
				app.mousePressed = false;
				
				result = event.clicked(4);
			}
			event.resetState();
			
			assertTrue(result);
		}
		
	}
	
	@Test
	void keyPressedTest() {
		final char symbolA = 'A',symbolB = 'B';
		app.key = symbolA;
		Event.keyPressed();
		
		assertTrue(Event.checkKey(symbolA));
		
		Event.keyReleased();
		
		app.key = symbolA;
		Event.keyPressed();
		app.key = symbolB;
		Event.keyPressed();
		
		assertTrue(Event.checkKey(symbolA) && Event.checkKey(symbolB));
		
		Event.keyReleased();
		
		assertFalse(Event.checkKey(symbolA) && Event.checkKey(symbolB));
	}
}