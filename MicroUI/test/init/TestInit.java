package init;

import org.junit.jupiter.api.BeforeAll;

import microUI.MicroUI;
import processing.core.PApplet;

public class TestInit {
	
	@BeforeAll
	static void initContext() {
		MicroUI.setContext(new PApplet());
	}
	
	
}