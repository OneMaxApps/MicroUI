package microui.core.base;

import java.util.ArrayList;

import microui.container.EdgeContainer;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import processing.event.MouseEvent;

// [] TODO: add method setVisibleComponents()
// [] TODO: add method setVisibleAllComponents()

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
	
	public void setVisibleTotal(boolean v) {
		
		setVisible(v);
		for(Bounds form : elementList) {
			if(form instanceof EdgeContainer) {
				EdgeContainer e = (EdgeContainer) form;
				if(e.getElement() instanceof Layout) {
					((Layout) (e.getElement())).setVisibleTotal(v);
				}
			} else {
				if(form instanceof Layout) {
					((Layout) (form)).setVisibleTotal(v);
				} 	
			}
			
		}
		
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
	public void inTransforms() {
		super.inTransforms();
		
		if(elementList == null) { return; }
		recalcListState();
	}
	
	protected abstract void recalcListState();
	
}