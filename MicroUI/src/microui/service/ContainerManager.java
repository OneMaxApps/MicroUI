package microui.service;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;
import static microui.MicroUI.getContext;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;

import java.util.ArrayList;
import java.util.List;

import microui.core.base.Container;
import microui.core.base.View;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Event;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

//Status: STABLE - Do not modify
//Last Reviewed: 12.09.2025
public final class ContainerManager extends View implements Scrollable, KeyPressable {
	private static ContainerManager instance;
	private final List<Container> containerList;
	private final Animation animation;
	private boolean isAnimationEnabled;
	private Container prevContainer, currentContainer;

	private ContainerManager() {
		setVisible(true);
		containerList = new ArrayList<Container>();
		animation = new Animation();
		setAnimationEnabled(true);
		getContext().registerMethod("draw", this);
		getContext().registerMethod("keyPressed", this);
		getContext().registerMethod("keyEvent", this);
		getContext().registerMethod("mouseEvent", this);

	}

	@Override
	public void render() {
		if (isAnimationEnabled()) {
			animation.draw();
		} else {
			if (currentContainer != null) {
				currentContainer.draw();
			}
		}
	}

	@Override
	public void keyPressed() {
		if (currentContainer == null) {
			return;
		}
		currentContainer.keyPressed();
	}

	public void keyEvent(KeyEvent keyEvent) {
		if (keyEvent.getAction() == KeyEvent.PRESS) {
			Event.keyPressed();
			keyPressed();
		}
		if (keyEvent.getAction() == KeyEvent.RELEASE) {
			Event.keyReleased();
		}
	}

	@Override
	public void mouseWheel(MouseEvent mouseEvent) {
		if (currentContainer == null) {
			return;
		}

		currentContainer.mouseWheel(mouseEvent);
	}

	public void mouseEvent(MouseEvent mouseEvent) {
		mouseWheel(mouseEvent);
	}

	public AnimationType getAnimationType() {
		return animation.getAnimationType();
	}

	public void setAnimationType(AnimationType animationType) {
		animation.setAnimationType(animationType);
	}

	public float getAnimationSpeed() {
		return animation.getRawSpeed();
	}

	public void setAnimationSpeed(float speed) {
		animation.setSpeed(speed);
	}

	public boolean isAnimationEasingEnabled() {
		return animation.isEasingEnabled();
	}

	public void setAnimationEasingEnabled(boolean isEasing) {
		animation.setEasingEnabled(isEasing);
	}

	public void add(Container container) {
		addContainerSafe(container);
	}

	public void add(Container container, String textId) {
		requireNonNull(textId, "textId cannot be null");
		if (textId.isEmpty()) {
			throw new IllegalArgumentException("textId cannot be empty");
		}

		addContainerSafe(container);

		container.setTextId(textId);
	}

	public void add(Container container, int id) {
		addContainerSafe(container);
		container.setId(id);
	}

	public void remove(final Container... containers) {
		requireNonNull(containers, "container's cannot be null");

		for (Container container : containers) {
			removeContainerSafe(container);
		}

	}

	public void remove(final int... id) {
		requireNonNull(id, "id cannot be null");

		for (int i : id) {
			removeContainerSafe(i);
		}

	}

	public void remove(final String... textId) {
		requireNonNull(textId, "textId cannot be null");

		for (String currentTextId : textId) {
			removeContainerSafe(currentTextId);
		}

	}

	public void switchOn(Container container) {
		lauchContainer(container);
	}

	public void switchOn(Container container, AnimationType animationType) {
		setAnimationType(animationType);
		switchOn(container);
	}

	public void switchOn(int id) {
		lauchContainer(getContainerById(id));
	}

	public void switchOn(int id, AnimationType animationType) {
		setAnimationType(animationType);
		switchOn(id);
	}

	public void switchOn(String textId) {
		lauchContainer(getContainerByTextId(textId));
	}

	public void switchOn(String textId, AnimationType animationType) {
		setAnimationType(animationType);
		switchOn(textId);
	}

	public Container getContainerById(int id) {
		return containerList.stream().filter(container -> container.getId() == id).findFirst()
				.orElseThrow(() -> new RuntimeException("container is not found"));
	}

	public Container getContainerByTextId(String textId) {
		return containerList.stream().filter(container -> container.getTextId().equals(textId)).findFirst()
				.orElseThrow(() -> new RuntimeException("container is not found"));
	}

	public boolean isAnimationEnabled() {
		return isAnimationEnabled;
	}

	public void setAnimationEnabled(boolean isAnimationEnabled) {
		this.isAnimationEnabled = isAnimationEnabled;
	}

	public static enum AnimationType {
		SLIDE_LEFT, SLIDE_RIGHT, SLIDE_TOP, SLIDE_BOTTOM, SLIDE_RANDOM;
	}

	private void lauchContainer(Container container) {
		// if(animation.isStart()) { return; }

		if (container == null) {
			throw new NullPointerException("container cannot be null");
		}

		if (!containerList.contains(container)) {
			throw new IllegalArgumentException("container is not found in to ContainerManager");
		}

		prevContainer = currentContainer;
		currentContainer = container;
		animation.setStart(true);
	}

	private void addContainerSafe(Container container) {
		requireNonNull(container, "container cannot be null");
		if (containerList.contains(container)) {
			throw new IllegalArgumentException("container cannot be added twice");
		}

		container.setConstrainDimensionsEnabled(false);
		container.setSize(ctx.width, ctx.height);
		container.setColor(new Color(200));

		containerList.add(container);

		if (currentContainer == null) {
			currentContainer = container;
		}
	}

	private void removeContainerSafe(Container container) {
		requireNonNull(container, "container cannot be null");

		if (containerList.contains(container)) {
			containerList.remove(container);

			if (prevContainer == container) {
				prevContainer = null;
			}

			if (currentContainer == container) {
				currentContainer = null;
			}

		} else {
			throw new RuntimeException("container is not found in to ContainerManager");
		}
	}

	private void removeContainerSafe(int id) {
		requireNonNull(id, "id cannot be null");

		removeContainerSafe(containerList.stream().filter(c -> c.getId() == id).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("id: " + id + " is not found in to ContainerManager")));
	}

	private void removeContainerSafe(String textId) {
		requireNonNull(textId, "textId cannot be null");

		removeContainerSafe(containerList.stream().filter(c -> c.getTextId().equals(textId)).findFirst().orElseThrow(
				() -> new IllegalArgumentException("text id: " + textId + " is not found in to ContainerManager")));
	}

	private final class Animation extends View {
		private static final float MAX_DIST = dist(0, 0, ctx.width, ctx.height);
		private AnimationType animationType;
		private float speed;
		private int randDirX, randDirY;
		private boolean isStart, isNewContainerPrepared, isEasing;

		private Animation() {
			super();
			setVisible(true);
			setSpeed(max(1, ctx.width * .2f));
			setAnimationType(AnimationType.SLIDE_RANDOM);
			setEasingEnabled(true);
		}

		@Override
		protected void render() {
			if (isStart()) {

				if (prevContainer != null) {
					prevContainer.draw();
				}

				switch (animationType) {
				case SLIDE_LEFT:
					slideDirection(-1, 0);
					break;
				case SLIDE_RIGHT:
					slideDirection(1, 0);
					break;
				case SLIDE_TOP:
					slideDirection(0, -1);
					break;
				case SLIDE_BOTTOM:
					slideDirection(0, 1);
					break;
				case SLIDE_RANDOM:
					if (!isNewContainerPrepared) {
						do {
							randDirX = (int) ctx.random(-1, 2);
							randDirY = (int) ctx.random(-1, 2);
						} while (randDirX != 0 && randDirY != 0 || randDirX == randDirY);

					}
					slideDirection(randDirX, randDirY);
					break;
				}

			}

			if (currentContainer != null) {
				currentContainer.draw();
			}
		}

		boolean isStart() {
			return isStart;
		}

		void setStart(boolean isStart) {
			this.isStart = isStart;
		}

		void complete() {
			prevContainer.setBounds(0, 0, ctx.width, ctx.height);
			currentContainer.setBoundsFrom(prevContainer);
			setStart(false);
			isNewContainerPrepared = false;
		}

		float getRawSpeed() {
			return speed;
		}

		void setSpeed(float speed) {
			if (speed <= 0) {
				throw new IllegalArgumentException("animation speed cannot be less or equal to zero");
			}
			this.speed = speed;
		}

		AnimationType getAnimationType() {
			return animationType;
		}

		void setAnimationType(AnimationType animationType) {
			if (animationType == null) {
				throw new NullPointerException("animationType cannot be null");
			}
			this.animationType = animationType;
		}

		boolean isEasingEnabled() {
			return isEasing;
		}

		void setEasingEnabled(boolean isEasing) {
			this.isEasing = isEasing;
		}

		private float getSpeedInternal() {
			if (isEasing) {
				float dist = max(abs(currentContainer.getX()), abs(currentContainer.getY()));
				return map(abs(dist), 0, MAX_DIST, .1f, speed);
			}
			return speed;
		}

		private void slideDirection(int dirX, int dirY) {
			if (!isNewContainerPrepared) {
				currentContainer.setPosition(dirX == -1 ? ctx.width : dirX == 1 ? -ctx.width : 0,
						dirY == -1 ? ctx.height : dirY == 1 ? -ctx.height : 0);
				isNewContainerPrepared = true;
			}

			if (dirX == -1) {
				prevContainer.appendX(-getSpeedInternal());
				currentContainer.appendX(-getSpeedInternal(), 0, ctx.width);
			}

			if (dirX == 1) {
				prevContainer.appendX(getSpeedInternal());
				currentContainer.appendX(getSpeedInternal(), -ctx.width, 0);
			}

			if (dirY == -1) {
				prevContainer.appendY(-getSpeedInternal());
				currentContainer.appendY(-getSpeedInternal(), 0, ctx.height);
			}

			if (dirY == 1) {
				prevContainer.appendY(getSpeedInternal());
				currentContainer.appendY(getSpeedInternal(), -ctx.height, 0);
			}

			if (dirX != 0) {
				if ((int) currentContainer.getX() == 0) {
					complete();
				}
			}

			if (dirY != 0) {
				if ((int) currentContainer.getY() == 0) {
					complete();
				}
			}
		}

	}

	public static ContainerManager getInstance() {
		if (instance == null) {
			instance = new ContainerManager();
		}

		return instance;
	}
}