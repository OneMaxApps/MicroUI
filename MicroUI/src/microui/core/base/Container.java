package microui.core.base;

import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;
import static microui.MicroUI.isDebugModeEnabled;
import static microui.constants.ContainerMode.RESPECT_CONSTRAINTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import microui.constants.ContainerMode;
import microui.core.Texture;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Listener;
import microui.layout.LayoutManager;
import microui.layout.LayoutParams;
import processing.core.PImage;
import processing.event.MouseEvent;


public final class Container extends Component implements KeyPressable, Scrollable {
	private final List<ComponentEntry> componentEntryList;
	private final LayoutManager layoutManager;
	private Texture backgroundImage;
	private ContainerMode containerMode;
	private int maxPriority;
	
	public Container(LayoutManager layoutManager, float x, float y, float width, float height) {
		super(x, y, width, height);
		setConstrainDimensionsEnabled(false);
		setMinMaxSize(1,1,width,height);
		
		getMutableBackgroundColor().set(0,0);

		componentEntryList = new ArrayList<ComponentEntry>();

		this.layoutManager = requireNonNull(layoutManager, "layout manager cannot be null");
		layoutManager.setContainer(this);

		setContainerMode(RESPECT_CONSTRAINTS);

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

		for(int i = 0; i < componentEntryList.size(); i++) {
			Component component = componentEntryList.get(i).getComponent();
			if(component instanceof Scrollable scrollable) {
				scrollable.mouseWheel(event);
			}
		}

	}

	@Override
	public void keyPressed() {
		if (componentEntryList.isEmpty()) {
			return;
		}

		for(int i = 0; i < componentEntryList.size(); i++) {
			Component component = componentEntryList.get(i).getComponent();
			if(component instanceof KeyPressable pressable) {
				pressable.keyPressed();
			}
		}
		
	}

	public Component getComponentById(final int id) {
		for(int i = 0; i < componentEntryList.size(); i++) {
			Component component = componentEntryList.get(i).getComponent();
			if(component.getId() == id) {
				return component;
			}
		}
		
		throw new IllegalArgumentException("component with id: " + id + " is not found in to this Container");
	}
	
	public Container getContainerById(final int id) {
		return (Container) getComponentById(id);
	}

	public Component getComponentByTextId(final String textId) {
		for(int i = 0; i < componentEntryList.size(); i++) {
			Component component = componentEntryList.get(i).getComponent();
			if(component.getTextId().equals(textId)) {
				return component;
			}
		}
		
		throw new IllegalArgumentException("component with text id: " + textId + " is not found in to this Container");
	}
	
	public Component getContainerByTextId(final String textId) {
		return (Container) getComponentByTextId(textId);
	}

	public Container addComponent(Component component, LayoutParams layoutParams, int id) {
		addComponentSafe(component, layoutParams);
		component.setId(id);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, int id, Listener onClickListener) {
		addComponent(component, layoutParams, id);
		component.onClick(onClickListener);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, String textId) {
		addComponentSafe(component, layoutParams);
		component.setTextId(textId);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, String textId,Listener onClickListener) {
		addComponent(component, layoutParams, textId);
		component.onClick(onClickListener);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams) {
		addComponentSafe(component, layoutParams);
		return this;
	}

	public Container addComponent(Component component, LayoutParams layoutParams, Listener onClickListener) {
		addComponent(component, layoutParams);
		component.onClick(onClickListener);
		return this;
	}

	public Container removeComponent(Component component) {
		removeComponentSafe(component);
		return this;
	}

	public Container removeComponentById(int id) {	
		for(int i = 0; i < componentEntryList.size(); i++) {
			Component component = componentEntryList.get(i).getComponent();
			if(component.getId() == id) {
				removeComponentSafe(component);
				return this;
			}
		}
		
		throw new IllegalArgumentException("component with id: \" "+ id +"\" is not found in to Container");
	}
	
	public Container removeComponentByTextId(String textId) {
		if(textId == null) {
			throw new NullPointerException("textId cannot be null");
		}
		
		for(int i = 0; i < componentEntryList.size(); i++) {
			Component component = componentEntryList.get(i).getComponent();
			if(component.getTextId().equals(textId)) {
				removeComponentSafe(component);
				return this;
			}
		}
		
		throw new IllegalArgumentException("component with text id: \""+ textId +"\" is not found in to Container");
	}

	public void removeAllComponents() {
		componentEntryList.clear();
	}

	public List<ComponentEntry> getComponentEntryList() {
		return Collections.unmodifiableList(componentEntryList);
	}

	public ContainerMode getContainerMode() {
		return containerMode;
	}

	public Container setContainerMode(ContainerMode containerMode) {
		if (containerMode == null) {
			throw new NullPointerException("containerMode cannot be null");
		}
		
		if (this.containerMode == containerMode) {
			return this;
		}

		this.containerMode = containerMode;

		requestUpdate();

		return this;
	}

	public void setBackgroundImage(PImage image) {
		ensureBackgroundImage();
		
		if(image == backgroundImage.get()) { return; }
		
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
		}
		
		if (backgroundImage != null) {
			backgroundImage.setBounds(getPadX(), getPadY(), getPadWidth(), getPadHeight());
		}
	}
	
	private void recalculateMaxPriority() {
		maxPriority = 0;
		for(int i = 0; i < componentEntryList.size(); i++) {
			int priority = componentEntryList.get(i).getComponent().getPriority();
			maxPriority = max(maxPriority, priority);
		}
	}

	private void addComponentSafe(Component component, LayoutParams layoutParams) {
		checkComponentNotNull(component);
		checkLayoutParamsNotNull(layoutParams);
		checkComponentAlreadyAddedInList(component);
		
		ComponentEntry componentEntry = new ComponentEntry(component, layoutParams);
		componentEntryList.add(componentEntry);
		layoutManager.onAddComponent(componentEntry);

		
		recalculateMaxPriority();
	}

	private void componentsOnDraw() {
		if (componentEntryList.isEmpty()) {
			return;
		}
		
		for(int priority = 0; priority <= maxPriority; priority++) {
			for(int i = 0; i < componentEntryList.size(); i++) {
				Component component = componentEntryList.get(i).getComponent();
				if(component.getPriority() == priority) {
					component.draw();
				}
			}
		}
		
	}

	private void debugOnDraw() {
		if (isDebugModeEnabled()) {
			
			ctx.pushStyle();
			ctx.noStroke();
			ctx.fill(0, 0, 255, 32);
			ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());
			ctx.popStyle();

			layoutManager.debugOnDraw();
		}
	}

	private void checkComponentNotNull(Component component) {
		if (component == null) {
			throw new NullPointerException("component cannot be null");
		}
	}

	private void checkLayoutParamsNotNull(LayoutParams layoutParams) {
		if (layoutParams == null) {
			throw new NullPointerException("layoutParams cannot be null");
		}
	}

	private void checkComponentAlreadyAddedInList(Component component) {
		for(int i = 0; i < componentEntryList.size(); i++) {
			if(componentEntryList.get(i).getComponent() == component) {
				throw new IllegalArgumentException("component cannot be added twice");
			}
		}
		
	}

	private void backgroundOnDraw() {
		if (backgroundImage != null && backgroundImage.isLoaded()) {
			backgroundImage.draw();
		} else {
			ctx.noStroke();
			getMutableBackgroundColor().apply();
			ctx.rect(getPadX(), getPadY(), getPadWidth(), getPadHeight());
		}
	}

	private void ensureBackgroundImage() {
		if (backgroundImage != null) {
			return;
		}
		backgroundImage = new Texture();
	}
	
	private void removeComponentSafe(Component component) {
		checkComponentNotNull(component);
		checkComponentExistInList(component);
		
		for(int i = 0; i < componentEntryList.size(); i++) {
			Component c = componentEntryList.get(i).getComponent();
			if(c == component) {
				componentEntryList.remove(i);
			}
		}
		
		layoutManager.onRemoveComponent();
		
		recalculateMaxPriority();
		
	}
	
	private void checkComponentExistInList(Component component) {
		for(int i = 0; i < componentEntryList.size(); i++) {
			if(componentEntryList.get(i).getComponent() == component) {
				return;
			}
		}

		throw new IllegalArgumentException("component not found in to Container");
	}

	public final static class ComponentEntry {
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