package microui.event;

import microui.MicroUI;
import microui.core.base.Component;
import microui.util.Metrics;

public abstract class AbstractEventSystem {
	private Component component;
	private final EventDetector detector;

	public AbstractEventSystem(Component component) {
		if (component == null) {
			throw new NullPointerException("component cannot be null");
		}

		this.component = component;

		detector = new EventDetector();

		Metrics.register(this);
	}
	
	public abstract void listen();

	public final EventDetector getDetector() {
		return detector;
	}

	public final long getEnterLongThreshold() {
		return detector.enterLongDetector.getEnterLongThreshold();
	}
	
	public final void setEnterLongThreshold(long enterLongThreshold) {
		detector.enterLongDetector.setEnterLongThreshold(enterLongThreshold);
	}

	public final long getLongPressThreshold() {
		return detector.longPressDetector.getLongPressThreshold();
	}

	public final void setLongPressThreshold(long longPressThreshold) {
		detector.longPressDetector.setLongPressThreshold(longPressThreshold);
	}

	public final long getLeaveLongThreshold() {
		return detector.leaveLongDetector.getLeaveLongThreshold();
	}

	public final void setLeaveLongThreshold(long leaveLongThreshold) {
		detector.leaveLongDetector.setLeaveLongThreshold(leaveLongThreshold);
	}

	public final long getDoubleClickThreshold() {
		return detector.doubleClickDetector.getThreshold();
	}

	public final void setDoubleClickThreshold(long threshold) {
		detector.doubleClickDetector.setThreshold(threshold);
	}

	protected final class EventDetector {
		private static final long DEFAULT_THRESHOLD = 500;
		private static final long DEFAULT_DOUBLE_CLICK_THRESHOLD = 200;
		private long enterTime, leaveTime;
		private boolean isHover;

		private final PressDetector pressDetector;
		private final ReleaseDetector releaseDetector;
		private final LongPressDetector longPressDetector;
		private final EnterDetector enterDetector;
		private final LeaveDetector leaveDetector;
		private final EnterLongDetector enterLongDetector;
		private final LeaveLongDetector leaveLongDetector;
		private final ClickDetector clickDetector;
		private final DoubleClickDetector doubleClickDetector;
		private final DragStartDetector dragStartDetector;
		private final DraggingDetector draggingDetector;
		private final DragEndDetector dragEndDetector;

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
			doubleClickDetector = new DoubleClickDetector();
			dragStartDetector = new DragStartDetector();
			draggingDetector = new DraggingDetector();
			dragEndDetector = new DragEndDetector();
		}

		public void update() {
			if (isHover) {
				updateLeaveTime();
			} else {
				updateEnterTime();
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

		public boolean isDoubleClick() {
			return doubleClickDetector.isDetected();
		}

		public boolean isDragStart() {
			return dragStartDetector.isDetected();
		}

		public boolean isDragging() {
			return draggingDetector.isDetected();
		}

		public boolean isDragEnd() {
			return dragEndDetector.isDetected();
		}

		public boolean isPressed() {
			return isHover && MicroUI.getContext().mousePressed;
		}

		public boolean isReleased() {
			return !isPressed();
		}

		public boolean isLongPressed() {
			if (isPressed() && System.currentTimeMillis() - pressDetector.getPressTime() >= longPressDetector
					.getLongPressThreshold()) {
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

			isHover = (mx > cx && mx < cx + cw && my > cy && my < cy + ch);

			return isHover;
		}

		private void updateEnterTime() {
			enterTime = System.currentTimeMillis();
		}

		private void updateLeaveTime() {
			leaveTime = System.currentTimeMillis();
		}

		private final class PressDetector {
			private boolean isPressHookCalled;
			private long pressTime;

			public PressDetector() {
				super();
				pressTime = System.currentTimeMillis();
			}

			public boolean isDetected() {
				if (!MicroUI.getContext().mousePressed) {
					isPressHookCalled = false;
				}

				if (!isPressHookCalled && isHover() && isPressed()) {
					isPressHookCalled = true;
					pressTime = System.currentTimeMillis();
					return true;
				}
				return false;
			}

			public long getPressTime() {
				return pressTime;
			}

		}

		private final class ReleaseDetector {
			private boolean isReleaseHookCalled;
			private long releaseTime;

			public ReleaseDetector() {
				isReleaseHookCalled = true;
				releaseTime = System.currentTimeMillis();
			}

			public boolean isDetected() {
				if (isPressed()) {
					isReleaseHookCalled = false;
				}

				if (!MicroUI.getContext().mousePressed && !isReleaseHookCalled) {
					isReleaseHookCalled = true;
					releaseTime = System.currentTimeMillis();
					return true;
				}

				return false;
			}

			public long getReleaseTime() {
				return releaseTime;
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
				if (!MicroUI.getContext().mousePressed) {
					isLongPressHookCalled = false;
				}

				if (!isLongPressHookCalled && isPressed()
						&& System.currentTimeMillis() - pressDetector.getPressTime() >= longPressThreshold) {
					isLongPressHookCalled = true;
					return true;
				}

				return false;
			}

			public long getLongPressThreshold() {
				return longPressThreshold;
			}

			public void setLongPressThreshold(long longPressThreshold) {
				if (longPressThreshold < 0) {
					throw new IllegalArgumentException("long press threshold cannot be less than 0");
				}

				this.longPressThreshold = longPressThreshold;
			}

		}

		private final class EnterDetector {
			private boolean isEnterHookCalled;

			public boolean isDetected() {
				if (!isHover()) {
					isEnterHookCalled = false;
				}

				if (!isEnterHookCalled) {
					if (isHover()) {
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
				if (isHover()) {
					isLeaveHookCalled = false;
				}

				if (!isLeaveHookCalled) {
					if (!isHover()) {
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
				isEnterLongHookCalled = false;
			}

			public boolean isDetected() {

				if (!isHover()) {
					isEnterLongHookCalled = false;
				}

				if (!isEnterLongHookCalled) {
					if (isHover() && System.currentTimeMillis() - enterTime >= enterLongThreshold) {
						isEnterLongHookCalled = true;
						return true;
					}
				}

				return false;
			}

			public long getEnterLongThreshold() {
				return enterLongThreshold;
			}

			public void setEnterLongThreshold(long enterLongThreshold) {
				if (enterLongThreshold < 0) {
					throw new IllegalArgumentException("enter long threshold cannot be less than 0");
				}
				this.enterLongThreshold = enterLongThreshold;
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
				if (isHover()) {
					isLeaveLongHookCalled = false;
				}

				if (!isLeaveLongHookCalled) {
					if (!isHover() && System.currentTimeMillis() - leaveTime >= leaveLongThreshold) {
						isLeaveLongHookCalled = true;
						return true;
					}
				}

				return false;
			}

			public long getLeaveLongThreshold() {
				return leaveLongThreshold;
			}

			public void setLeaveLongThreshold(long leaveLongThreshold) {
				if (leaveLongThreshold < 0) {
					throw new IllegalArgumentException("leave long threshold cannot be less than 0");
				}

				this.leaveLongThreshold = leaveLongThreshold;
			}

		}

		private final class ClickDetector {
			private boolean isPressed, isHover, isCanCallHook;

			public boolean isDetected() {
				isPressed = isPressed();
				isHover = isHover();

				if (isCanCallHook && !isPressed && isHover) {
					isCanCallHook = false;
					return true;
				}

				if (isPressed) {
					isCanCallHook = true;
				}

				if (!isHover) {
					isCanCallHook = false;
				}

				return false;
			}
		}

		private final class DoubleClickDetector {
			private ClickDetector clickDetector;
			private long threshold;
			private int clickCount;

			public DoubleClickDetector() {
				super();
				threshold = DEFAULT_DOUBLE_CLICK_THRESHOLD;
				clickDetector = new ClickDetector();
			}

			public boolean isDetected() {
				if (System.currentTimeMillis() - releaseDetector.getReleaseTime() > threshold) {
					clickCount = 0;
				}

				if (clickDetector.isDetected()) {
					clickCount++;
				}

				if (clickCount == 2) {
					clickCount = 0;
					return true;
				}

				return false;
			}

			public long getThreshold() {
				return threshold;
			}

			public void setThreshold(long threshold) {
				if (threshold < 0) {
					throw new IllegalArgumentException("double click threshold cannot be less than 0");
				}
				this.threshold = threshold;
			}

		}

		private final class DragStartDetector {
			private boolean isHookCalled;

			public boolean isDetected() {
				if (!MicroUI.getContext().mousePressed) {
					isHookCalled = false;
				}

				if (!isHookCalled && isPressed() && isMouseMoved()) {
					isHookCalled = true;
					return true;
				}

				return false;
			}

			private boolean isMouseMoved() {
				int mx = MicroUI.getContext().mouseX;
				int my = MicroUI.getContext().mouseY;
				int pmx = MicroUI.getContext().pmouseX;
				int pmy = MicroUI.getContext().pmouseY;

				return mx != pmx || my != pmy;
			}
		}

		private final class DraggingDetector {
			private final DragStartDetector dragStartDetector;

			private boolean isDragging;

			public DraggingDetector() {
				super();
				dragStartDetector = new DragStartDetector();
			}

			public boolean isDetected() {
				if (dragStartDetector.isDetected()) {
					isDragging = true;
				}

				if (!MicroUI.getContext().mousePressed) {
					isDragging = false;
				}

				return isDragging;
			}

		}

		private final class DragEndDetector {
			private final DragStartDetector dragStartDetector;
			private boolean isDragEnd;

			public DragEndDetector() {
				super();
				this.dragStartDetector = new DragStartDetector();
			}

			public boolean isDetected() {

				if (dragStartDetector.isDetected()) {
					isDragEnd = true;
				}

				if (!dragStartDetector.isDetected() && isDragEnd && !MicroUI.getContext().mousePressed) {
					isDragEnd = false;
					return true;
				}

				return false;
			}

		}
	}

}