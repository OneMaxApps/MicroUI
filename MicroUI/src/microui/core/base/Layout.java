package microui.core.base;

import java.util.ArrayList;

import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import processing.event.MouseEvent;

public abstract class Layout extends Container {
	protected final ArrayList<Bounds> elementList;
	
	public Layout(float x, float y, float w, float h) {
		super(x, y, w, h);

		elementList = new ArrayList<Bounds>();
	}
	
	public ArrayList<Bounds> getElements() {
		return elementList;
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {
		elementList.forEach(element -> {
			if(element instanceof Scrollable sc) {
				sc.mouseWheel(e);
			}
		});
	}

	@Override
	public void keyPressed() {
		elementList.forEach(element -> {
			if(element instanceof KeyPressable kp) {
				kp.keyPressed();
			}
		});
	}

	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		
		recalcListState();
	}
	
	/**
	 * Changing visible state for all inner layouts and for himself
	 */
	public void setVisibleLayouts(boolean visible) {
		setVisible(visible);
		
		for(View view : elementList) {
			if(view instanceof Layout layout) {
				layout.setVisibleLayouts(visible);
			}
		}
		
	}
	
	/**
	 * Changing visible state for all inner components
	 */
	public void setVisibleComponents(boolean visible) {
		
		for(View view : elementList) {
			if(view instanceof Component component) {
				component.setVisible(visible);
			}
		}
		
	}
	
	protected abstract void recalcListState();
	
}