package microuitest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import microui.util.Value;

class ValueTest {
	private Value value = new Value(0,10,5);
	
	@Test
	void setMaxValue() {
		assertEquals(10,value.getMax());
		value.setMax(20);
		assertEquals(20,value.getMax());
	}
	
	@Test
	void setMinValue() {
		assertEquals(0,value.getMin());
		value.setMin(1);
		assertEquals(1,value.getMin());
	}
	
	@Test
	void setValue() {
		value.set(-100,100,0);
		
		value.set(-200);
		assertEquals(value.get(),value.getMin());
		
		value.set(200);
		assertEquals(value.get(),value.getMax());
		
	}
	
	@Test
	void appendValue() {
		value.set(0,10,5);
		
		value.append(100);
		assertEquals(value.get(),value.getMax());
		value.append(-100);
		assertEquals(value.get(),value.getMin());
	}
}