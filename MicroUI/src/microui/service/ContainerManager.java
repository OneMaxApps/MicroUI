package microui.service;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import microui.core.base.Container;
import microui.core.base.View;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import processing.event.MouseEvent;

//Status: STABLE - Do not modify
//Last Reviewed: 10.09.2025
public final class ContainerManager extends View implements Scrollable, KeyPressable{
	private final List<View> containerList;
	private Container container;
	
	public ContainerManager() {
		setVisible(true);
		containerList = new ArrayList<View>();
		
	}

	@Override
	public void update() {
		if(container == null) { return; }
		if(containerList.isEmpty()) { return; }
		
		containerList.forEach(container -> {
			if(this.container == container) {
				container.draw();
			}
		});
		
	}
	
	@Override
	public void keyPressed() {
		container.keyPressed();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		container.mouseWheel(event);
	}

	public final void add(final Container... containers) {
		requireNonNull(containers, "containers cannot be null");
		for(Container container : containers) {
			containerList.add(container);
		}
	}
	
	public final void remove(final Container... containers) {
		requireNonNull(containers, "containers cannot be null");
		
		for(Container container : containers) {
			containerList.remove(container);
			if(this.container == container) { this.container = null; }
		}
	}
	
	public final boolean isFocused() {
		return container != null;
	}
	
	public final void setFocusOn(Container container) {
		requireNonNull(container, "focusable object cannot be null");
		this.container = container;
	}
	
}