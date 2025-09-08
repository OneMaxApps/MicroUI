package microui.core.base;

import java.util.ArrayList;
import java.util.List;

import microui.MicroUI;
import microui.core.interfaces.Focusable;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.layout.LayoutManager;
import microui.layout.LayoutParams;
import processing.event.MouseEvent;

public final class Container extends Component implements Focusable, KeyPressable, Scrollable {
	private final List<ComponentEntry> componentList;
	private LayoutManager layoutManager;
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

		componentList = new ArrayList<ComponentEntry>();
		
		setLayoutManager(layoutManager);
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
		if (componentList.isEmpty()) {
			return;
		}

		componentList.forEach(entry -> {
			if (entry.getComponent() instanceof Scrollable c) {
				c.mouseWheel(event);
			}
		});
	}

	@Override
	public void keyPressed() {
		if (componentList.isEmpty()) {
			return;
		}

		componentList.forEach(entry -> {
			if (entry.getComponent() instanceof KeyPressable k) {
				k.keyPressed();
			}
		});
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		if (layoutManager != null) {
			layoutManager.recalculate();
		}
	}

	public void addComponent(Component component, LayoutParams params) {

		if (isComponentNotNull(component) && isParamsNotNull(params)) {
			boolean isComponentExists = false;

			for (int i = 0; i < componentList.size(); i++) {
				if (componentList.get(i).getComponent() == component) {
					throw new IllegalArgumentException(
							"component cannot be added twice");
				}
			}

			if (!isComponentExists) {
				componentList.add(new ComponentEntry(component, params));
			}
		}

		layoutManager.onAddComponent();
		layoutManager.recalculate();

		recalculateMaxPriority();
	}

	public void removeComponent(Component component) {

		if (isComponentNotNull(component)) {
			boolean isComponentExists = false;

			for (int i = 0; i < componentList.size(); i++) {
				if (componentList.get(i).getComponent() == component) {
					isComponentExists = true;
					componentList.remove(i);
				}
			}

			if (!isComponentExists) {
				throw new IllegalArgumentException(
						"component cannot be removed, because he is not exists in container");
			}
		}

		// layoutManager.onRemoveComponent(component);
		layoutManager.recalculate();

		recalculateMaxPriority();
	}

	public List<ComponentEntry> getComponentEntryList() {
		return componentList;
	}

	public final LayoutManager getLayoutManager() {
		return layoutManager;
	}

	public final void setLayoutManager(LayoutManager layoutManager) {
		if (layoutManager == null) {
			throw new NullPointerException("layout manager cannot be null");
		}
		this.layoutManager = layoutManager;
		layoutManager.setContainer(this);
		layoutManager.recalculate();
	}

	private void componentsOnDraw() {
		if (componentList.isEmpty()) {
			return;
		}

		for (int i = 0; i <= getMaxPriority(); i++) {
			for (ComponentEntry entry : componentList) {
				if (entry.getComponent().getPriority() == i) {
					entry.getComponent().draw();
				}
			}
		}

	}

	private void debugOnDraw() {
		if (MicroUI.isDebugModeEnabled()) {
			ctx.push();
			ctx.noStroke();
			ctx.fill(0, 0, 255, 32);
			ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());
			ctx.pop();
		}
	}

	private boolean isComponentNotNull(Component component) {
		if (component == null) {
			throw new NullPointerException("component cannot be null");
		}
		return true;
	}
	
	private boolean isParamsNotNull(LayoutParams params) {
		if (params == null) {
			throw new NullPointerException("params cannot be null");
		}
		return true;
	}

	private int getMaxPriority() {
		return maxPriority;
	}

	private void recalculateMaxPriority() {
		int tmpPriority = 0;

		for (ComponentEntry component : componentList) {
			if (component.getComponent().getPriority() > tmpPriority) {
				tmpPriority = component.getComponent().getPriority();
			}
		}

		maxPriority = tmpPriority;
	}

	public static class ComponentEntry {
		private Component component;
		private LayoutParams params;

		private ComponentEntry(Component component, LayoutParams params) {
			super();
			this.component = component;
			this.params = params;
		}

		public final Component getComponent() {
			return component;
		}

		public final LayoutParams getParams() {
			return params;
		}

	}
}