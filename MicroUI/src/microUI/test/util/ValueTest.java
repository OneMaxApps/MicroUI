package microUI.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import microUI.util.Value;

class ValueTest {
	
	@Test
	void setMaxValue() {
		Value value = new Value(0,10,5);
		assertEquals(10,value.getMax());
		value.setMax(20);
		assertEquals(20,value.getMax());
	}
	
	@Test
	void setMinValue() {
		Value value = new Value(0,10,5);
		assertEquals(0,value.getMin());
		value.setMin(1);
		assertEquals(1,value.getMin());
	}
	
	@Test
	void setValue() {
		Value value = new Value(0,10,5);
		
		float tmpValue = value.get();
		value.set(-10);
		assertEquals(tmpValue,value.get());
		
		tmpValue = value.get();
		value.set(20);
		assertEquals(tmpValue,value.get());
	}
	
	@Test
	void appendValue() {
		Value value = new Value(0,10,5);
		value.append(100);
		assertEquals(value.get(),value.getMax());
		value.append(-100);
		assertEquals(value.get(),value.getMin());
	}
}