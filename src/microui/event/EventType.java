package microui.event;

public enum EventType {
	// EDGE type is calling only once
	// LEVEL type calling always without hooks logic
	PRESS, // EDGE [1]
	PRESSED, // LEVEL [1]
	RELEASE, // EDGE [1]
	RELEASED, // LEVEL [1]
	
	LONG_PRESS, // EDGE [1] 
	LONG_PRESSED, // LEVEL [1]
	
	ENTER, // EDGE [1]
	LEAVE, // EDGE [1] 
	ENTER_LONG, // EDGE [1]
	LEAVE_LONG, // EDGE [1]

	CLICK, // EDGE [1]
	DOUBLE_CLICK, // EDGE  here

	DRAG_START, // EDGE
	DRAGGING, // LEVEL
	DRAG_END, // EDGE
	
	HOVER; // LEVEL [1]
}