package microui.layout;

import java.util.List;

import microui.core.base.Container;
import microui.core.base.Container.ComponentEntry;

public abstract class LayoutManager {
	private Container container;
	
	public abstract void recalculate();

	public void onAddComponent() {
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
	}

	protected final List<ComponentEntry> getComponentEntryList() {
		return container.getComponentEntryList();
	}
}