package microui.event;

public enum EventType {
	PRESS, // EDGE
	PRESSED, // LEVEL
	RELEASE, // EDGE
	RELEASED, // LEVEL
	LONG_PRESS, // EDGE

	ENTER, // EDGE
	LEAVE, // EDGE
	ENTER_LONG, // EDGE
	LEAVE_LONG, // EDGE

	CLICK, // EDGE
	DOUBLE_CLICK, // EDGE

	DRAG_START, // EDGE
	DRAGGING, // LEVEL
	DRAG_END // EDGE
}