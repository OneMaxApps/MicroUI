package microui.core.base;

import java.util.ArrayList;

import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import processing.event.MouseEvent;

public abstract class Layout extends Container {
	protected boolean isElementsResizable;
	protected final ArrayList<Bounds> elementList;
	
	public Layout(float x, float y, float w, float h) {
		super(x, y, w, h);

		elementList = new ArrayList<Bounds>();
	}
	
	public boolean isElementsResizable() {
		return isElementsResizable;
	}

	public void setElementsResizable(boolean r) {
		isElementsResizable = r;
	}
	
	public ArrayList<Bounds> getElements() {
		return elementList;
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {
		elementList.forEach(element -> {
			if(element instanceof Scrollable) {
				((Scrollable) element).mouseWheel(e);
			}
		});
	}

	@Override
	public void keyPressed() {
		elementList.forEach(element -> {
			if(element instanceof KeyPressable) {
				((KeyPressable) element).keyPressed();
			}
		});
	}

	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		
		if(elementList == null) { return; }
		recalcListState();
	}
	
	public void setVisibleContainers(boolean v) {
		setVisible(v);
		
		for(View view : elementList) {
			if(view instanceof Layout layout) {
				layout.setVisibleContainers(v);
			}
			if(view instanceof Container container) {
				container.setVisible(v);
			}
		}
		
	}
	
	public void setVisibleComponents(boolean v) {
		for(View view : elementList) {
			if(view instanceof Component component) {
				component.setVisible(v);
			}
		}	
	}
	
	protected abstract void recalcListState();
	
}