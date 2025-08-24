package microui.service;

import java.util.ArrayList;
import java.util.List;

import microui.core.base.View;
import microui.core.interfaces.Focusable;

public final class ContainerManager extends View {
	private final List<Focusable> containerList;
	private Focusable container;
	
	public ContainerManager() {
		containerList = new ArrayList<Focusable>();
		setVisible(true);
		
	}

	@Override
	public void update() {
		if(container == null) { return; }
		if(containerList.isEmpty()) { return; }
		
		containerList.forEach(container -> {
			if(this.container == container) {
				((View) container).draw();
			}
		});
		
	}
	
	public final void add(final Focusable... containers) {
		for(Focusable container : containers) {
			add(container);
		}
	}
	
	public final void add(final Focusable container) {
		containerList.add((Focusable) container);
	}
	
	public final void remove(final Focusable container) {
		if(containerList == null) { return; }
		containerList.remove(container);
		if(this.container == container) { this.container = null; }
	}
	
	public final void remove(final Focusable... containers) {
		for(Focusable container : containers) {
			remove(container);
		}
	}
	
	public final boolean isFocused() {
		return container != null;
	}
	
	public final void setFocusOn(Focusable focusable) {
		container = focusable;
	}
	
}