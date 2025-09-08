package microui.core.base;

import java.util.ArrayList;
import java.util.List;

import microui.MicroUI;
import microui.core.interfaces.Focusable;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.layout.LayoutManager;
import processing.event.MouseEvent;

public final class Container extends Component implements Focusable, KeyPressable, Scrollable {
	private final List<Component> componentList;
	private LayoutManager layoutManager;
	private int maxPriority;

	public Container(float x, float y, float width, float height) {
		super(x, y, width, height);
		setVisible(true);
		setNegativeDimensionsEnabled(false);
		setConstrainDimensionsEnabled(true);
		setPaddingEnabled(true);
		setMarginEnabled(true);
		setMinSize(100);

		getMutableColor().set(255, 0);

		componentList = new ArrayList<Component>();
	}

	public Container() {
		this(0, 0, ctx.width, ctx.height);
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

		componentList.forEach(component -> {
			if (component instanceof Scrollable c) {
				c.mouseWheel(event);
			}
		});
	}

	@Override
	public void keyPressed() {
		if (componentList.isEmpty()) {
			return;
		}

		componentList.forEach(component -> {
			if (component instanceof KeyPressable k) {
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

	public void addComponent(Component... components) {
		for (Component component : components) {

			if (isComponentNotNull(component) && componentList.contains(component)) {
				throw new IllegalArgumentException("component cannot be added twice");
			}

			componentList.add(component);
			layoutManager.onAddComponent(component);
			layoutManager.recalculate();
		}

		recalculateMaxPriority();
	}

	public void removeComponent(Component... components) {
		for (Component component : components) {

			if (isComponentNotNull(component) && !componentList.contains(component)) {
				throw new IllegalArgumentException(
						"component cannot be removed, because he is not exists in container");
			}

			componentList.remove(component);
			layoutManager.onRemoveComponent(component);
			layoutManager.recalculate();
		}

		recalculateMaxPriority();
	}

	public List<Component> getComponentList() {
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
		layoutManager.recalculate();
	}

	private void componentsOnDraw() {
		if (componentList.isEmpty()) {
			return;
		}
			
		for (int i = 0; i <= getMaxPriority(); i++) {
			for(Component component : componentList) {
				if (component.getPriority() == i) {
					component.draw();
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

	private int getMaxPriority() {
		return maxPriority;
	}

	private void recalculateMaxPriority() {
		int tmpPriority = 0;

		for (Component component : componentList) {
			if (component.getPriority() > tmpPriority) {
				tmpPriority = component.getPriority();
			}
		}

		maxPriority = tmpPriority;
	}

}