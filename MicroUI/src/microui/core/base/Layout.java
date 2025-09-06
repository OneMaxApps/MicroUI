//package microui.core.base;
//
//import java.util.ArrayList;
//
//import microui.core.interfaces.KeyPressable;
//import microui.core.interfaces.Scrollable;
//import processing.event.MouseEvent;
//
//public abstract class Layout extends Container {
//	protected final ArrayList<Bounds> elementList;
//	protected int depth;
//	
//	public Layout(float x, float y, float w, float h) {
//		super(x, y, w, h);
//		elementList = new ArrayList<Bounds>();
//		
//	}
//	
//	public final ArrayList<Bounds> getElements() {
//		return elementList;
//	}
//	
//	protected final void drawElements() {
//		if(!elementList.isEmpty()) {
//			if(depth == 0) {
//				elementList.forEach(element -> element.draw());
//			} else {
//				for(int i = 0; i <= depth; i++) {
//					for(View view : elementList) {
//						if(i == view.getPriority()) {
//							view.draw();
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	@Override
//	public final void mouseWheel(MouseEvent e) {
//		elementList.forEach(element -> {
//			if(element instanceof Scrollable sc) {
//				sc.mouseWheel(e);
//			}
//		});
//	}
//
//	@Override
//	public final void keyPressed() {
//		elementList.forEach(element -> {
//			if(element instanceof KeyPressable kp) {
//				kp.keyPressed();
//			}
//		});
//	}
//
//	@Override
//	protected void onChangeBounds() {
//		super.onChangeBounds();
//		
//		recalcListState();
//	}
//	
//	/**
//	 * Changing visible state for all inner layouts and for himself
//	 */
//	public void setVisibleLayouts(boolean visible) {
//		setVisible(visible);
//		
//		for(View view : elementList) {
//			if(view instanceof Layout layout) {
//				layout.setVisibleLayouts(visible);
//			}
//		}
//		
//	}
//	
//	/**
//	 * Changing visible state for all inner components
//	 */
//	public void setVisibleComponents(boolean visible) {
//		
//		for(View view : elementList) {
//			if(view instanceof Component component) {
//				component.setVisible(visible);
//			}
//		}
//		
//	}
//	
//	public final int getDepth() {
//		return depth;
//	}
//
//	public final void setDepth(int depth) {
//		if(depth < 0) { return; }
//		this.depth = depth;
//	}
//	
//	public final void setPriority(View view, int priority) {
//		if(view == null || priority < 0) { return; }
//		
//		elementList.forEach(element -> {
//			if(element == view) {
//				element.setPriority(priority);
//				if(depth < priority) { depth = priority; }
//			}
//		});
//	}
//	
//	public final void updatePriorities() {
//		elementList.forEach(element -> {
//			if(depth < element.getPriority()) { depth = element.getPriority(); }
//		});
//	}
//
//	protected abstract void recalcListState();
//	
//}