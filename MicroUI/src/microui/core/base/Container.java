package microui.core.base;

import static java.util.Objects.requireNonNull;
import static microui.constants.ContainerMode.IGNORE_CONSTRAINTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import microui.MicroUI;
import microui.constants.ContainerMode;
import microui.core.Texture;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Listener;
import microui.layout.LayoutManager;
import microui.layout.params.LayoutParams;
import processing.core.PImage;
import processing.event.MouseEvent;

public final class Container extends Component implements KeyPressable, Scrollable {
	private final List<ComponentEntry> componentEntryList;
	private final LayoutManager layoutManager;
	private Texture backgroundImage;
	private ContainerMode containerMode;

	public Container(LayoutManager layoutManager, float x, float y, float width, float height) {
		super(x, y, width, height);
		setVisible(true);
		setNegativeDimensionsEnabled(false);
		setConstrainDimensionsEnabled(false);
		setPaddingEnabled(true);
		setMarginEnabled(true);
		setMinMaxSize(1,1,width,height);
		
		getMutableColor().set(255, 0);

		componentEntryList = new ArrayList<ComponentEntry>();

		this.layoutManager = requireNonNull(layoutManager, "layout manager cannot be null");
		layoutManager.setContainer(this);

		setContainerMode(IGNORE_CONSTRAINTS);

	}

	public Container(LayoutManager layoutManager) {
		this(layoutManager, 0, 0, ctx.width, ctx.height);
	}

	@Override
	protected void render() {
		backgroundOnDraw();

		debugOnDraw();

		componentsOnDraw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		if (componentEntryList.isEmpty()) {
			return;
		}

		componentEntryList.forEach(entry -> {
			if (entry.getComponent() instanceof Scrollable s) {
				s.mouseWheel(event);
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

	public Component getComponentById(final int id) {
		return componentEntryList.stream().map(entry -> (Component) entry.getComponent())
				.filter(component -> component.getId() == id).findFirst()
				.orElseThrow(() -> new RuntimeException("component with id: " + id + " is not found in to this Container"));
	}

	public Component getComponentByTextId(final String textId) {
		return componentEntryList.stream().map(component -> (Component) component.getComponent())
				.filter(component -> component.getTextId().equals(textId)).findFirst()
				.orElseThrow(() -> new RuntimeException(
						"component with text id: " + textId + " is not found in to this Container"));
	}

	public Container addComponent(Component component, LayoutParams layoutParams, int id) {
		addComponentCorrect(component, layoutParams);
		component.setId(id);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, int id, Listener onClickListener) {
		addComponent(component, layoutParams, id);
		component.onClick(onClickListener);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, String textId) {
		addComponentCorrect(component, layoutParams);
		component.setTextId(textId);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, String textId,
			Listener onClickListener) {
		addComponent(component, layoutParams, textId);
		component.onClick(onClickListener);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams) {
		addComponentCorrect(component, layoutParams);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, Listener onClickListener) {
		addComponentCorrect(component, layoutParams);
		component.onClick(onClickListener);
		return this;
	}

	public Container removeComponent(Component component) {
		if (isComponentNotNull(component)) {
			componentEntryList.removeIf(c -> c.getComponent() == component);
			layoutManager.onRemoveComponent();
			sortPriorityOfComponents();
		}

		return this;
	}

	public Container removeComponentById(int id) {	
		componentEntryList.removeIf(c -> c.getComponent().getId() == id);
		return this;
		
	}
	
	public Container removeComponentByTextId(String textId) {
		componentEntryList.removeIf(c -> c.getComponent().getTextId().equals(textId));
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

	public final Container setContainerMode(ContainerMode containerMode) {
		if (containerMode == null) {
			throw new NullPointerException("containerMode cannot be null");
		}
		
		if (this.containerMode == containerMode) {
			return this;
		}

		this.containerMode = containerMode;

		requestUpdate();

		// not need here because it's calling inside to onChangeBounds()
		// layoutManager.recalculate();
		return this;
	}

	public void setBackgroundImage(PImage image) {
		ensureBackgroundImage();
		backgroundImage.set(image);
		requestUpdate();
	}

	public void setBackgroundImageColor(Color color) {
		ensureBackgroundImage();
		backgroundImage.setColor(color);
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();

		if (layoutManager != null) {
			layoutManager.recalculate();
			requestUpdateForInnerComponents();

			if (backgroundImage != null) {
				backgroundImage.setBounds(getContentX(), getContentY(), getContentWidth(), getContentHeight());
			}
		}
	}

	private void addComponentCorrect(Component component, LayoutParams layoutParams) {
		if (isComponentNotNull(component) && isLayoutParamsNotNull(layoutParams)) {
			if (isNotAddedComponent(component)) {
				ComponentEntry componentEntry = new ComponentEntry(component, layoutParams);
				componentEntryList.add(componentEntry);
				layoutManager.onAddComponent(componentEntry);
				sortPriorityOfComponents();
			}
		}

		requestUpdateForInnerComponents();
	}

	private void componentsOnDraw() {
		if (componentEntryList.isEmpty()) {
			return;
		}

		componentEntryList.forEach(entry -> entry.getComponent().draw());

	}

	private void debugOnDraw() {
		if (MicroUI.isDebugModeEnabled()) {
			ctx.pushStyle();
			ctx.noStroke();
			ctx.fill(0, 0, 255, 32);
			ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());
			ctx.popStyle();

			layoutManager.debugOnDraw();
		}
	}

	private boolean isComponentNotNull(Component component) {
		if (component == null) {
			throw new NullPointerException("component cannot be null");
		}
		return true;
	}

	private boolean isLayoutParamsNotNull(LayoutParams params) {
		if (params == null) {
			throw new NullPointerException("params cannot be null");
		}

		return true;
	}

	private void sortPriorityOfComponents() {
		componentEntryList.sort(Comparator.comparingInt(entry -> entry.getComponent().getPriority()));
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

			if (component instanceof Container container) {
				container.requestUpdateForInnerComponents();
			}
		});
	}

	private void backgroundOnDraw() {
		if (backgroundImage != null && backgroundImage.isLoaded()) {
			backgroundImage.draw();
		} else {
			getMutableColor().apply();
			ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());
		}
	}

	private void ensureBackgroundImage() {
		if (backgroundImage != null) {
			return;
		}
		backgroundImage = new Texture();
	}

	public static class ComponentEntry {
		private final Component component;
		private final LayoutParams layoutParams;

		private ComponentEntry(Component component, LayoutParams layoutParams) {
			super();
			if(component == null) {
				throw new NullPointerException("component cannot be null");
			}
			
			if(layoutParams == null) {
				throw new NullPointerException("layoutParams cannot be null");
			}
			
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