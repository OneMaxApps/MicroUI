package microUI.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import microUI.MicroUI;
import processing.core.PApplet;

class EventTest {
	
	@Test
	void pressedTest() {
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
		event.listen(0,0,100,100);
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		
		
		assertTrue(event.pressed());
	}
	
	@Test
	void insideTest() {
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
		event.listen(0,0,100,100);
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		
		
		assertTrue(event.inside());
	}
	
	@Test
	void ousideTest() {
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
		event.listen(0,0,100,100);
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		
		
		assertFalse(event.outside());
	}
	
	@Test
	void longPressedTest() {
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
		event.listen(0,0,100,100);
		
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
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
		event.setEnable(true);
		assertTrue(event.isEnable());
	}
	
	@Test
	void movedTest() {
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		
		event.listen(0,0,100,100);
		
		assertTrue(event.moved());
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = false;
		
		event.listen(0,0,100,100);
		
		assertFalse(event.moved());
		
	}
	
	@Test
	void clickedTest() {
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
		app.mouseX = 50;
		app.mouseY = 50;
		app.mousePressed = true;
		event.listen(0,0,100,100);
		
		app.mousePressed = false;
		
		assertTrue(event.clicked());
	}
	
	@Test
	void multiClickedTest() {
		MicroUI.setContext(new PApplet());
		PApplet app = MicroUI.getContext();
		Event event = new Event();
		
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
		MicroUI.setContext(new PApplet());
		PApplet app = new PApplet();
		final char symbolA = 'A',symbolB = 'B';
		app.key = symbolA;
		Event.keyPressed(app);
		
		assertTrue(Event.checkKey(symbolA));
		
		Event.keyReleased();
		
		app.key = symbolA;
		Event.keyPressed(app);
		app.key = symbolB;
		Event.keyPressed(app);
		
		assertTrue(Event.checkKey(symbolA) && Event.checkKey(symbolB));
		
		Event.keyReleased();
		
		assertFalse(Event.checkKey(symbolA) && Event.checkKey(symbolB));
	}
}