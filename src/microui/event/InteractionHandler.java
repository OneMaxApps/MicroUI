package microui.event;

import java.util.ArrayList;
import java.util.EnumMap;

import microui.MicroUI;
import microui.core.base.Component;

public final class InteractionHandler {
	private final EventDetecor detector;
	private final EventDispatcher dispatcher;
	private Component component;

	public InteractionHandler(Component component) {
		super();
		
		detector = new EventDetecor();
		
		dispatcher = new EventDispatcher();

		if (component == null) {
			throw new NullPointerException("component cannot be null");
		}

		this.component = component;
	}

	public void listen() {
		detector.update();
		
		if(detector.isPress()) {
			dispatcher.dispatch(EventType.PRESS);
		}
		
		if(detector.isPressed()) {
			dispatcher.dispatch(EventType.PRESSED);
		}
		
		if(detector.isRelease()) {
			dispatcher.dispatch(EventType.RELEASE);
		}
		
		if(detector.isReleased()) {
			dispatcher.dispatch(EventType.RELEASED);
		}
		
		if(detector.isLongPress()) {
			dispatcher.dispatch(EventType.LONG_PRESS);
		}
		
		if(detector.isLongPressed()) {
			dispatcher.dispatch(EventType.LONG_PRESSED);
		}
		
		if(detector.isEnter()) {
			dispatcher.dispatch(EventType.ENTER);
		}
		
		if(detector.isLeave()) {
			dispatcher.dispatch(EventType.LEAVE);
		}

		if(detector.isEnterLong()) {
			dispatcher.dispatch(EventType.ENTER_LONG);
		}
		
		if(detector.isLeaveLong()) {
			dispatcher.dispatch(EventType.LEAVE_LONG);
		}
		
		if(detector.isClick()) {
			dispatcher.dispatch(EventType.CLICK);
		}
		
		if(detector.isHover()) {
			dispatcher.dispatch(EventType.HOVER);
		}
		
		
	}

	public void addListener(EventType eventType, Listener listener) {
		dispatcher.addListenerSafe(eventType, listener);
	}
	
	public void removeListener(EventType eventType, Listener listener) {
		dispatcher.removeListenerSafe(eventType, listener);
	}
	
	private class EventDetecor {
		private static final long DEFAULT_THRESHOLD = 500;
		private long prevPressTime,nowPressTime;
		private long enterTime,leaveTime;
		private long longPressThreshold,enterLongThreshold,leaveLongThreshold;
		private boolean isPressHookCalled , isReleaseHookCalled ,
						isLongPressHookCalled , isEnterHookCalled ,
						isLeaveHookCalled, isEnterLongHookCalled ,
						isLeaveLongHookCalled , isClickHookCalled, isHover;
		
		public EventDetecor() {
			super();
			longPressThreshold = DEFAULT_THRESHOLD;
			enterLongThreshold = DEFAULT_THRESHOLD;
			leaveLongThreshold = DEFAULT_THRESHOLD;
			
			prevPressTime = nowPressTime = System.currentTimeMillis();
			
		}
		
		public void update() {
			if(!MicroUI.getContext().mousePressed) {
				isPressHookCalled = false;
				isLongPressHookCalled = false;
			}
		}
		
		public boolean isPress() {
			if(!isPressHookCalled && isHover() && isPressed()) {
				isPressHookCalled = true;
				prevPressTime = nowPressTime;
				nowPressTime = System.currentTimeMillis();
				return true;
			}
			return false;
		}
		
		public boolean isPressed() {
			return isHover && MicroUI.getContext().mousePressed;
		}
		
		public boolean isRelease() {
			if(isPressed()) {
				isReleaseHookCalled = false;
			}
			
			if(!MicroUI.getContext().mousePressed && !isReleaseHookCalled) {
				isReleaseHookCalled = true;
				return true;
			}
			
			return false;
		}
		
		public boolean isReleased() {
			return !isPressed();
		}
		
		public boolean isLongPress() {
			if(!isLongPressHookCalled && isPressed() && System.currentTimeMillis()-nowPressTime  >= longPressThreshold) {
				isLongPressHookCalled = true;
				return true;
			}
			
			return false;
		}
		
		public boolean isLongPressed() {
			if(isPressed() && System.currentTimeMillis()-nowPressTime  >= longPressThreshold) {
				return true;
			}
			
			return false;
		}
		
		public boolean isEnter() {
			if(!isHover()) { isEnterHookCalled = false; }
			
			if(!isEnterHookCalled) {
				if(isHover()) {
					isEnterHookCalled = true;
					return true;
				}
			}
			
			return false;
		}
		
		public boolean isLeave() {
			if(isHover()) { isLeaveHookCalled = false; }
			
			if(!isLeaveHookCalled) {
				if(!isHover()) {
					isLeaveHookCalled = true;
					return true;
				}
			}
			
			return false;
		}
		
		public boolean isEnterLong() {
			if(!isHover()) { isEnterLongHookCalled = false; }
			
			if(!isEnterLongHookCalled) {
				if(isHover() && System.currentTimeMillis()-enterTime >= enterLongThreshold) {
					isEnterLongHookCalled = true;
					return true;
				}
			}
			
			return false;
		}
		
		public boolean isLeaveLong() {
			if(isHover()) { isLeaveLongHookCalled = false; }
			
			if(!isLeaveLongHookCalled) {
				if(!isHover() && System.currentTimeMillis()-leaveTime >= leaveLongThreshold) {
					isLeaveLongHookCalled = true;
					return true;
				}
			}
			
			return false;
		}
		
		public boolean isClick() {
			
			if(isPressed()) {
				isClickHookCalled = false;
			}
			
			if(!isHover()) {
				isClickHookCalled = true;
			}
			
			if(!isClickHookCalled && isHover() && !isPressed()) {
				isClickHookCalled = true;
				return true;
			}
			
			return false;
		}
		
		public boolean isHover() {
			
			int mx = MicroUI.getContext().mouseX;
			int my = MicroUI.getContext().mouseY;
			
			int cx = (int) component.getPadX();
			int cy = (int) component.getPadY();
			int cw = (int) component.getPadWidth();
			int ch = (int) component.getPadHeight();
			
			isHover = (mx > cx && mx < cx+cw && my > cy && my < cy+ch);
			
			if(isHover) {
				leaveTime = 0;
				
				if(enterTime == 0) {
					enterTime = System.currentTimeMillis();
				}
				
			} else {
				enterTime = 0;
				
				if(leaveTime == 0) {
					leaveTime = System.currentTimeMillis();
				}
				
			}
			
			return isHover;
		}
		
		
		
	}

	private final class EventDispatcher {
		private final EnumMap<EventType, ArrayList<Listener>> listeners;

		public EventDispatcher() {
			super();
			listeners = new EnumMap<>(EventType.class);
		}

//		public void update() {
//			listeners.forEach((eventType, listenerList) -> {
//
//				switch (eventType) {
//
//				case PRESS:
//					
//					listenerList.forEach((listener) -> listener.action());
//					
//					break;
//
//				case CLICK:
//					break;
//				case DOUBLE_CLICK:
//					break;
//				case DRAGGING:
//					break;
//				case DRAG_END:
//					break;
//				case DRAG_START:
//					break;
//				case ENTER:
//					break;
//				case ENTER_LONG:
//					break;
//				case LEAVE:
//					break;
//				case LEAVE_LONG:
//					break;
//				case LONG_PRESS:
//					break;
//				case PRESSED:
//					break;
//				case RELEASE:
//					break;
//				case RELEASED:
//					break;
//				default:
//					break;
//				}
//			});
//		}

		public void dispatch(EventType eventType) {
			if(listeners.get(eventType) != null) {
				listeners.get(eventType).forEach(Listener::action);
			}
		}
		
		public void addListenerSafe(EventType eventType, Listener listener) {
			if (eventType == null) {
				throw new NullPointerException("eventType cannot be null");
			}

			if (listener == null) {
				throw new NullPointerException("listener cannot be null");
			}

			listeners.putIfAbsent(eventType, new ArrayList<Listener>());
			listeners.get(eventType).add(listener);
		}
		
		public void removeListenerSafe(EventType eventType, Listener listener) {
			if (eventType == null) {
				throw new NullPointerException("eventType cannot be null");
			}
			
			if (listener == null) {
				throw new NullPointerException("listener cannot be null");
			}
			
			if(listeners.get(eventType) == null || !listeners.get(eventType).contains(listener)) {
				throw new IllegalStateException("listener is not found");
			}
			
			listeners.get(eventType).remove(listener);
		}
	}

}