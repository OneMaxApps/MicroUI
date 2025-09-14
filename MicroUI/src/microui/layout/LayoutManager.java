package microui.layout;

import java.util.List;

import microui.MicroUI;
import microui.core.base.Container;
import microui.core.base.Container.ComponentEntry;
import microui.layout.params.LayoutParams;
import processing.core.PApplet;

public abstract class LayoutManager {
	protected PApplet ctx = MicroUI.getContext();
	private Container container;
	private List<Container.ComponentEntry> componentEntryList;

	public abstract void recalculate();
	
	public abstract void debugOnDraw();

	public void onAddComponent(ComponentEntry componentEntry) {
		checkCorrectParams(componentEntry.getLayoutParams());
		recalculate();
	}

	public void onRemoveComponent() {
		recalculate();
	}

	public final Container getContainer() {
		return container;
	}

	public final void setContainer(Container container) {
		if(container == null) {
			throw new NullPointerException("container cannot be null");
		}
		this.container = container;
		componentEntryList = container.getComponentEntryList();
	}

	
	protected final List<ComponentEntry> getComponentEntryList() {
		return componentEntryList;
	}
	
	protected abstract void checkCorrectParams(LayoutParams layoutParams);
}