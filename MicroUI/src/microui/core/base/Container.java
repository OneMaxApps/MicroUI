package microui.core.base;

import static microui.constants.ContainerMode.STRICT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import microui.MicroUI;
import microui.constants.ContainerMode;
import microui.core.interfaces.Focusable;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import microui.layout.LayoutManager;
import microui.layout.LayoutParams;
import processing.event.MouseEvent;

public final class Container extends Component implements Focusable, KeyPressable, Scrollable {
	private final List<ComponentEntry> componentEntryList;
	private LayoutManager layoutManager;
	private ContainerMode containerMode;
	private int maxPriority;

	public Container(LayoutManager layoutManager, float x, float y, float width, float height) {
		super(x, y, width, height);
		setVisible(true);
		setNegativeDimensionsEnabled(false);
		setConstrainDimensionsEnabled(true);
		setPaddingEnabled(true);
		setMarginEnabled(true);
		setMaxSize(width, height);
		setMinSize(1);

		getMutableColor().set(255, 0);

		componentEntryList = new ArrayList<ComponentEntry>();

		setLayoutManager(layoutManager);

		setContainerMode(STRICT);

	}

	public Container(LayoutManager layoutManager) {
		this(layoutManager, 0, 0, ctx.width, ctx.height);
	}

	@Override
	protected void update() {
		getMutableColor().apply();
		ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());

		debugOnDraw();

		componentsOnDraw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		if (componentEntryList.isEmpty()) {
			return;
		}

		componentEntryList.forEach(entry -> {
			if (entry.getComponent() instanceof Scrollable c) {
				c.mouseWheel(event);
			}
		});
		
	}

	@Override
	public void keyPressed() {
		if (componentEntryList.isEmpty()) {
			return;
		}

		componentEntryList.forEach(entry -> {
			if (entry.getComponent() instanceof KeyPressable k) {
				k.keyPressed();
			}
		});
	}
	
	public View getViewByID(final int ID) {
		for(ComponentEntry entry : componentEntryList) {
			Component component = entry.getComponent();
			System.out.println(component.getID());
			if(ID == component.getID()) { return component; }
			if(component instanceof Container container) {
				return container.getViewByID(ID);
			}
		}
		throw new IllegalArgumentException("ID is not found");
	}

	public Container addComponent(Component component, LayoutParams params) {
		if (isComponentNotNull(component) && isLayoutParamsCorrect(params)) {
			if (isNotAddedComponent(component)) {
				componentEntryList.add(new ComponentEntry(component, params));
				layoutManager.onAddComponent();
				recalculateMaxPriority();
			}
		}
		
		requestUpdateForInnerComponents();
		
		return this;
	}

	public Container removeComponent(Component component) {
		if (isComponentNotNull(component)) {
			componentEntryList.removeIf(c -> c.getComponent() == component);
			layoutManager.onRemoveComponent();
			recalculateMaxPriority();
		}

		return this;
	}

	public void removeAllComponents() {
		componentEntryList.clear();
	}

	public List<ComponentEntry> getComponentEntryList() {
		return Collections.unmodifiableList(componentEntryList);
	}

	public final ContainerMode getContainerMode() {
		return containerMode;
	}

	public final void setContainerMode(ContainerMode containerMode) {
		if (this.containerMode == containerMode) { return; }
		
		this.containerMode = containerMode;

		requestUpdate();

		layoutManager.recalculate();

	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if (layoutManager != null) {
			layoutManager.recalculate();
			requestUpdateForInnerComponents();
		}
	}

	private void setLayoutManager(LayoutManager layoutManager) {
		if (layoutManager == null) {
			throw new NullPointerException("layout manager cannot be null");
		}

		this.layoutManager = layoutManager;

		layoutManager.setContainer(this);
		layoutManager.recalculate();
	}

	private void componentsOnDraw() {
		if (componentEntryList.isEmpty()) {
			return;
		}

		for (int i = 0; i <= getMaxPriority(); i++) {
			for (ComponentEntry entry : componentEntryList) {
				Component component = entry.getComponent();
				if (component.getPriority() == i) {
					component.draw();
				}
			}
		}

	}

	private void debugOnDraw() {
		if (MicroUI.isDebugModeEnabled()) {
			ctx.pushStyle();
			ctx.noStroke();
			ctx.fill(0, 0, 255, 32);
			ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());
			ctx.popStyle();

			debugLayoutOnDraw();
		}
	}

	private void debugLayoutOnDraw() {
		ctx.pushStyle();
		ctx.stroke(0, 128);
		ctx.noFill();
		if (layoutManager instanceof GridLayout grid) {
			for (int col = 0; col < grid.getColumns(); col++) {
				for (int row = 0; row < grid.getRows(); row++) {
					ctx.rect(getContentX() + (getContentWidth() / grid.getColumns()) * col,
							getContentY() + (getContentHeight() / grid.getRows()) * row,
							getContentWidth() / grid.getColumns(), getContentHeight() / grid.getRows());
				}
			}
		}
		ctx.popStyle();
	}

	private boolean isComponentNotNull(Component component) {
		if (component == null) {
			throw new NullPointerException("component cannot be null");
		}
		return true;
	}

	private boolean isLayoutParamsCorrect(LayoutParams params) {
		if (params == null) {
			throw new NullPointerException("params cannot be null");
		}

		if ((layoutManager instanceof GridLayout) && !(params instanceof GridLayoutParams)) {
			throw new IllegalStateException("you need use GridLayoutParams for GridLayout");
		}

		return true;
	}

	private int getMaxPriority() {
		return maxPriority;
	}

	private void recalculateMaxPriority() {
		int tmpPriority = 0;

		for (ComponentEntry component : componentEntryList) {
			if (component.getComponent().getPriority() > tmpPriority) {
				tmpPriority = component.getComponent().getPriority();
			}
		}

		maxPriority = tmpPriority;
	}

	private boolean isNotAddedComponent(Component component) {
		componentEntryList.forEach(componentEntry -> {
			if (componentEntry.getComponent() == component) {
				throw new IllegalArgumentException("component cannot be added twice");
			}
		});

		return true;
	}

	private void requestUpdateForInnerComponents() {
		componentEntryList.forEach(entry -> {
			Component component = entry.getComponent();
			component.requestUpdate();
			
			if(component instanceof Container container) {
				container.requestUpdateForInnerComponents();
			}
		});
	}

	public static class ComponentEntry {
		private Component component;
		private LayoutParams layoutParams;

		private ComponentEntry(Component component, LayoutParams layoutParams) {
			super();
			this.component = component;
			this.layoutParams = layoutParams;
		}

		public final Component getComponent() {
			return component;
		}

		public final LayoutParams getLayoutParams() {
			return layoutParams;
		}

	}
}