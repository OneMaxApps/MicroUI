package microuitest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import microui.util.Clipboard;

class ClipboardTest {
	private final String TEST_WORD = "TEST";
	private final String[] LINES = {"One","Two","Three"};
	
	@Test
	void setAndGetValueFromLocalBuffer() {
		Clipboard.usingLocalBuffer();
		Clipboard.set(TEST_WORD);
		assertEquals(TEST_WORD,Clipboard.get());
	}
	
	@Test
	void setAndGetValueFromSystemBuffer() {
		Clipboard.usingSystemBuffer();
		Clipboard.set(TEST_WORD);
		assertEquals(TEST_WORD,Clipboard.get());
	}
	
	@Test
	void getAsArray() {
		final int COUNT_OF_LINES = LINES.length;
		Clipboard.set(LINES);
		assertEquals(COUNT_OF_LINES,Clipboard.getAsArray().length);
	}
	
}