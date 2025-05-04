package init;

import org.junit.jupiter.api.BeforeAll;

import microUI.MicroUI;
import processing.core.PApplet;

public class TestInit {
	
	@BeforeAll
	static void initContext() {
		final PApplet app = new PApplet();
		app.width = 1080;
		app.height = 720;
		MicroUI.setContext(app);
	}
	
	
}