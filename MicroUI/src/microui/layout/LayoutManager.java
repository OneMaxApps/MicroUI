package microui.layout;

import java.util.List;

import microui.core.base.Component;
import microui.core.base.Container;
import microui.core.base.Container.ComponentEntry;

public abstract class LayoutManager {
	private Container container;
	
	public abstract void recalculate();

	public abstract void onAddComponent();

	public abstract void onRemoveComponent(Component component);

	public final Container getContainer() {
		return container;
	}

	public final void setContainer(Container container) {
		this.container = container;
	}

	protected final List<ComponentEntry> getComponentEntryList() {
		return container.getComponentEntryList();
	}
}