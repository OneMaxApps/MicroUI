package microui.event;

import java.util.ArrayList;
import java.util.EnumMap;

import microui.MicroUI;
import microui.core.base.Component;

public final class InteractionHandler {
	private final EventDetector detector;
	private final EventDispatcher dispatcher;
	private Component component;

	public InteractionHandler(Component component) {
		super();
		
		detector = new EventDetector();
		
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
	
	private class EventDetector {
		private static final long DEFAULT_THRESHOLD = 500;
		private long enterTime,leaveTime;
		private boolean isHover;
		
		private final PressDetector pressDetector;
		private final ReleaseDetector releaseDetector;
		private final LongPressDetector longPressDetector;
		private final EnterDetector enterDetector;
		private final LeaveDetector leaveDetector;
		private final EnterLongDetector enterLongDetector;
		private final LeaveLongDetector leaveLongDetector;
		private final ClickDetector clickDetector;
		
		public EventDetector() {
			super();
			
			pressDetector = new PressDetector();
			releaseDetector = new ReleaseDetector();
			longPressDetector = new LongPressDetector();
			enterDetector = new EnterDetector();
			leaveDetector = new LeaveDetector();
			enterLongDetector = new EnterLongDetector();
			leaveLongDetector = new LeaveLongDetector();
			clickDetector = new ClickDetector();
		}
		
		public void update() {
			if(isHover) {
				leaveTime = 0;
				updateEnterTime();
			} else {
				enterTime = 0;
				updateLeaveTime();
			}
		}
		
		public boolean isPress() {
			return pressDetector.isDetected();
		}
		
		public boolean isRelease() {
			return releaseDetector.isDetected();
		}
		
		public boolean isLongPress() {
			return longPressDetector.isDetected();
		}
		
		public boolean isEnter() {
			return enterDetector.isDetected();
		}
		
		public boolean isLeave() {
			return leaveDetector.isDetected();
		}
		
		public boolean isEnterLong() {
			return enterLongDetector.isDetected();
		}
		
		public boolean isLeaveLong() {
			return leaveLongDetector.isDetected();
		}
		
		public boolean isClick() {
			return clickDetector.isDetected();
		}
		
		public boolean isPressed() {
			return isHover && MicroUI.getContext().mousePressed;
		}

		public boolean isReleased() {
			return !isPressed();
		}
		
		public boolean isLongPressed() {
			if(isPressed() && System.currentTimeMillis()-pressDetector.getNowPressTime()  >= longPressDetector.getLongPressThreshold()) {
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
			
			return isHover;
		}
		
		private void updateEnterTime() {
			if(enterTime == 0) {
				enterTime = System.currentTimeMillis();
			}
		}
		
		private void updateLeaveTime() {
			if(leaveTime == 0) {
				leaveTime = System.currentTimeMillis();
			}
		}
		
		private final class PressDetector {
			private boolean isPressHookCalled;
			private long prevPressTime,nowPressTime;
			
			public PressDetector() {
				super();
				prevPressTime = nowPressTime = System.currentTimeMillis();
			}



			public boolean isDetected() {
				if(!MicroUI.getContext().mousePressed) {
					isPressHookCalled = false;
				}
				
				if(!isPressHookCalled && isHover() && isPressed()) {
				isPressHookCalled = true;
				prevPressTime = nowPressTime;
				nowPressTime = System.currentTimeMillis();
				return true;
				}
				return false;
			}

			public long getPrevPressTime() {
				return prevPressTime;
			}

			public long getNowPressTime() {
				return nowPressTime;
			}

		}
		
		private final class ReleaseDetector {
			private boolean isReleaseHookCalled;
			
			public ReleaseDetector() {
				isReleaseHookCalled = true;
			}
			
			public boolean isDetected() {
				if(isPressed()) {
					isReleaseHookCalled = false;
				}
				
				if(!MicroUI.getContext().mousePressed && !isReleaseHookCalled) {
					isReleaseHookCalled = true;
					return true;
				}
				
				return false;
			}
			
		}
		
		private final class LongPressDetector {
			private boolean isLongPressHookCalled;
			private long longPressThreshold;
			
			
			public LongPressDetector() {
				super();
				longPressThreshold = DEFAULT_THRESHOLD;
			}

			public boolean isDetected() {
				if(!MicroUI.getContext().mousePressed) {	
					isLongPressHookCalled = false;
				}
				
				if(!isLongPressHookCalled && isPressed() && System.currentTimeMillis()-pressDetector.getNowPressTime()  >= longPressThreshold) {
					isLongPressHookCalled = true;
					return true;
				}
				
				return false;
			}
			
			public long getLongPressThreshold() {
				return longPressThreshold;
			}
		}
		
		private final class EnterDetector {
			private boolean isEnterHookCalled;
			
			public boolean isDetected() {
				if(!isHover()) { isEnterHookCalled = false; }
				
				if(!isEnterHookCalled) {
					if(isHover()) {
						isEnterHookCalled = true;
						return true;
					}
				}
				
				return false;
			}
		}
		
		private final class LeaveDetector {
			private boolean isLeaveHookCalled;
			
			public LeaveDetector() {
				super();
				isLeaveHookCalled = true;
			}

			public boolean isDetected() {
				if(isHover()) { isLeaveHookCalled = false; }
				
				if(!isLeaveHookCalled) {
					if(!isHover()) {
						isLeaveHookCalled = true;
						return true;
					}
				}
				
				return false;
			}
			
		}
		
		private final class EnterLongDetector {
			private boolean isEnterLongHookCalled;
			private long enterLongThreshold;
			
			public EnterLongDetector() {
				super();
				enterLongThreshold = DEFAULT_THRESHOLD;
			}

			public boolean isDetected() {
				if(!isHover()) { isEnterLongHookCalled = false; }
				
				if(!isEnterLongHookCalled) {
					if(isHover() && System.currentTimeMillis()-enterTime >= enterLongThreshold) {
						isEnterLongHookCalled = true;
						return true;
					}
				}
				
				return false;
			}
			
		}
		
		private final class LeaveLongDetector {
			private boolean isLeaveLongHookCalled;
			private long leaveLongThreshold;
			
			public LeaveLongDetector() {
				super();
				leaveLongThreshold = DEFAULT_THRESHOLD;
				isLeaveLongHookCalled = true;
			}

			public boolean isDetected() {
				if(isHover()) { isLeaveLongHookCalled = false; }
				
				if(!isLeaveLongHookCalled) {
					if(!isHover() && System.currentTimeMillis()-leaveTime >= leaveLongThreshold) {
						isLeaveLongHookCalled = true;
						return true;
					}
				}
				
				return false;
			}
			
		}
		
		private final class ClickDetector {
			private boolean isPressed,isHover,isCanCallHook;
			
			public boolean isDetected() {
				isPressed = isPressed();
				isHover = isHover();
				
				if(isCanCallHook && !isPressed && isHover) {
					isCanCallHook = false;
					return true;
				}
				
				if(isPressed) {
					isCanCallHook = true;
				}
				
				if(!isHover) {
					isCanCallHook = false;
				}
				
				return false;
			}
		}
	}

	private final class EventDispatcher {
		private final EnumMap<EventType, ArrayList<Listener>> listeners;

		public EventDispatcher() {
			super();
			listeners = new EnumMap<>(EventType.class);
		}

		public void dispatch(EventType eventType) {
			if(listeners.get(eventType) != null) {
				System.out.println("dispatch called");
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