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
public final class ContainerManager extends View implements Scrollable, KeyPressable {
	private final List<Container> containerList;
	private Container currectContainer;

	public ContainerManager() {
		setVisible(true);
		containerList = new ArrayList<Container>();

	}

	@Override
	public void update() {
		if (currectContainer == null) {
			return;
		}
		if (containerList.isEmpty()) {
			return;
		}

		currectContainer.draw();
	}

	@Override
	public void keyPressed() {
		if (currectContainer == null) {
			return;
		}
		currectContainer.keyPressed();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		if (currectContainer == null) {
			return;
		}
		currectContainer.mouseWheel(event);
	}

	public final void add(Container container, String textID) {
		requireNonNull(container, "container cannot be null");
		requireNonNull(textID, "textID cannot be null");
		if (textID.isEmpty()) {
			throw new IllegalArgumentException("textID cannot be empty");
		}
		if (containerList.contains(container)) {
			throw new IllegalArgumentException("container cannot be added twice");
		}

		container.setTextID(textID);

		containerList.add(container);
	}

	public final void remove(final Container... containers) {
		requireNonNull(containers, "containers cannot be null");

		for (Container container : containers) {
			requireNonNull(container, "container cannot be null");
			if (!containerList.removeIf(innerContainer -> {

				boolean isFound = container == innerContainer;

				if (isFound && this.currectContainer == container) {
					this.currectContainer = null;
				}

				return isFound;

			})) {
				throw new IllegalArgumentException("container is not found in to Container Manager");
			}
		}

	}

	public final void remove(final String... textID) {
		requireNonNull(textID, "textID cannot be null");

		for (String currentTextID : textID) {
			if (containerList.removeIf(container -> {
				boolean isFound = container.getTextID().equals(currentTextID);

				if (isFound) {
					if (currectContainer == container) {
						currectContainer = null;
					}
				}

				return isFound;
			})) {

			} else {
				throw new IllegalArgumentException(
						"container with textID: " + textID + " is not found in to Container Manager");
			}

		}

	}

	public final boolean isFocused() {
		return currectContainer != null;
	}

	public final void setFocusOn(Container container) {
		if (this.currectContainer == container) {
			return;
		}

		requireNonNull(container, "container object cannot be null");
		if (!containerList.contains(container)) {
			throw new IllegalArgumentException("container is not found");
		}
		this.currectContainer = container;
	}

}