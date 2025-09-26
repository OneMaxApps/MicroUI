package microui.event;

import microui.MicroUI;
import microui.core.base.Component;
import microui.core.base.SpatialView;

public final class Event {
	private final ClickDetector clickDetector;
	private final DoubleClickDetector doubleClickDetector;
	private final ReleaseDetector releaseDetector;
	private final DraggingDetector draggingDetector;
	private SpatialView spatialView;
	
	public Event(SpatialView spatialView) {
		super();
		
		clickDetector = new ClickDetector();
		doubleClickDetector = new DoubleClickDetector();
		releaseDetector = new ReleaseDetector();
		draggingDetector = new DraggingDetector();
		
		if(spatialView == null) {
			throw new NullPointerException("spatialView in Event cannot be null");
		}
		
		this.spatialView = spatialView;
	}
	
	public void listen() {
		releaseDetector.update();
	}
	
	public boolean isEnter() {
		int mx = MicroUI.getContext().mouseX;
		int my = MicroUI.getContext().mouseY;
		
		int sx,sy,sw,sh;
		
		if(spatialView instanceof Component c) {
			sx = (int) c.getPadX();
			sy = (int) c.getPadY();
			sw = (int) c.getPadWidth();
			sh = (int) c.getPadHeight();
		} else {
			sx = (int) spatialView.getX();
			sy = (int) spatialView.getY();
			sw = (int) spatialView.getWidth();
			sh = (int) spatialView.getHeight();
		}
		
		return (mx > sx && mx < sx+sw) && (my > sy && my < sy+sh);
	}
	
	public boolean isLeave() {
		return !isEnter();
	}
	
	public boolean isPressed() {
		return MicroUI.getContext().mousePressed && isEnter();
	}
	
	public boolean isClicked() {
		return clickDetector.isDetected();
	}
	
	public boolean isDoubleClicked() {
		return doubleClickDetector.isDetected();
	}
	
	public boolean isDragging() {
		return draggingDetector.isDetected();
	}
	
	public long getDoubleClickThreshold() {
		return doubleClickDetector.getThreshold();
	}

	public void setDoubleClickThreshold(long threshold) {
		doubleClickDetector.setThreshold(threshold);
	}
	
	private final class ClickDetector {
		private boolean isPressed, isEnter, isCanCallHook;

		public boolean isDetected() {
			isPressed = isPressed();
			isEnter = isEnter();

			if (isCanCallHook && !isPressed && isEnter) {
				isCanCallHook = false;
				return true;
			}

			if (isPressed) {
				isCanCallHook = true;
			}

			if (!isEnter) {
				isCanCallHook = false;
			}

			return false;
		}
	}
	
	private final class DoubleClickDetector {
		private static final long DEFAULT_DOUBLE_CLICK_THRESHOLD = 200;
		private ClickDetector clickDetector;
		private long threshold;
		private int clickCount;

		public DoubleClickDetector() {
			super();
			threshold = DEFAULT_DOUBLE_CLICK_THRESHOLD;
			clickDetector = new ClickDetector();
		}

		public boolean isDetected() {	
			if(System.currentTimeMillis()-releaseDetector.getReleaseTime() > threshold) {
				clickCount = 0;
			}
			
			if(clickDetector.isDetected()) {
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
			if(threshold < 0) {
				throw new IllegalArgumentException("double click threshold cannot be less than 0");
			}
			this.threshold = threshold;
		}
		
	}
	
	private final class ReleaseDetector {
		private boolean isReleaseHookCalled;
		private long releaseTime;
		
		public ReleaseDetector() {
			isReleaseHookCalled = true;
			releaseTime = System.currentTimeMillis();
		}

		public boolean update() {
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
	
	private final class DragStartDetector {
		private boolean isHookCalled;
		
		public boolean isDetected() {
			if(!MicroUI.getContext().mousePressed) { isHookCalled = false; }
			
			if(!isHookCalled && isPressed() && isMouseMoved()) {
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
			if(dragStartDetector.isDetected()) {
				isDragging = true;
			}
			
			if(!MicroUI.getContext().mousePressed) {
				isDragging = false;
			}
			
			return isDragging;
		}
		
		
	}
}