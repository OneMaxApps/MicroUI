package microUI.container;

import java.util.ArrayList;
import java.util.List;

import microUI.core.Focusable;
import microUI.core.View;

public final class ContainerManager extends View {
	private final List<Focusable> containerList;
	private View container;
	
	public ContainerManager() {
		containerList = new ArrayList<Focusable>();
		visible();
		
	}

	@Override
	public void update() {
		if(container == null) { return; }
		
		for(Focusable element : containerList) {
			if(container == element) {
				((View) element).draw();
			}
		}
	}
	
	public final void add(View... baseForm) {
		for(int i = 0; i < baseForm.length; i++) {
			add(baseForm[i]);
		}
	}
	
	public final void add(View container) {
		if(container instanceof Focusable) {
			containerList.add((Focusable) container);
		}
	}
	
	public final void remove(View container) {
		if(container instanceof Focusable) {
			containerList.remove((Focusable) container);
		}
	}
	
	public final boolean isFocused() {
		return container != null;
	}
	
	public final void setFocusOn(Focusable focusable) {
		container = (View) focusable;
	}
	
}