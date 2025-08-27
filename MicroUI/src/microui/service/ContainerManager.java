package microui.service;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import microui.core.base.View;
import microui.core.interfaces.Focusable;

//Status: STABLE - Do not modify
//Last Reviewed: 27.08.2025
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
		requireNonNull(containers, "containers cannot be null");
		for(Focusable container : containers) {
			add(container);
		}
	}
	
	public final void remove(final Focusable... containers) {
		requireNonNull(containers, "containers cannot be null");
		
		for(Focusable container : containers) {
			containerList.remove(container);
			if(this.container == container) { this.container = null; }
		}
	}
	
	public final boolean isFocused() {
		return container != null;
	}
	
	public final void setFocusOn(Focusable focusable) {
		requireNonNull(focusable, "focusable object cannot be null");
		container = focusable;
	}
	
}