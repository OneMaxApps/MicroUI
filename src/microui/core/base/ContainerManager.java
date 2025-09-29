package microui.core.base;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;
import static microui.MicroUI.getContext;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;

import java.util.ArrayList;
import java.util.List;

import microui.MicroUI;
import microui.core.ImageBuffer;
import microui.core.exception.RenderException;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.event.KeyboardManager;
import microui.service.GlobalTooltipOld;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

//Status: STABLE - Do not modify
//Last Reviewed: 26.09.2025
public final class ContainerManager extends View implements Scrollable, KeyPressable {
	private static ContainerManager instance;
	private final List<Container> containerList;
	private final Animation animation;
	private static boolean isInitialized, isCanDraw;
	private boolean isAnimationEnabled;
	private Container prevContainer, currentContainer;

	private ContainerManager() {
		setVisible(true);
		containerList = new ArrayList<Container>();
		animation = new Animation();
		setAnimationEnabled(true);

		getContext().registerMethod("keyPressed", this);
		getContext().registerMethod("keyEvent", this);
		getContext().registerMethod("mouseEvent", this);
		new Render();
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
	public void draw() {
		if (!isCanDraw) {
			throw new RenderException("ContainerManager calling draw() only inside");
		}
		super.draw();
		GlobalTooltipOld.draw();
		debugOnDraw();
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
			KeyboardManager.keyPressed();
			keyPressed();
		}
		if (keyEvent.getAction() == KeyEvent.RELEASE) {
			KeyboardManager.keyReleased();
		}
	}

	@Override
	public void mouseWheel(MouseEvent mouseEvent) {
		if (currentContainer == null) {
			return;
		}

		if (mouseEvent.getAction() == MouseEvent.WHEEL) {
			currentContainer.mouseWheel(mouseEvent);
		}

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

	public void switchOnPreviousContainer() {
		if (animation.isAnimationRunningEnabled()) {
			return;
		}

		int currentContainerIndex = 0;
		for (int i = 0; i < containerList.size(); i++) {
			if (currentContainer == containerList.get(i)) {
				currentContainerIndex = i;
			}
		}

		if (currentContainerIndex > 0) {
			switchOn(containerList.get(currentContainerIndex - 1));
		} else {
			switchOn(containerList.get(containerList.size() - 1));
		}
	}

	public void switchOnNextContainer() {
		if (animation.isAnimationRunningEnabled()) {
			return;
		}
		int currentContainerIndex = 0;

		for (int i = 0; i < containerList.size(); i++) {
			if (currentContainer == containerList.get(i)) {
				currentContainerIndex = i;
			}
		}

		if (currentContainerIndex < containerList.size() - 1) {
			switchOn(containerList.get(currentContainerIndex + 1));
		} else {
			switchOn(0);
		}
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

	public static final boolean isInitialized() {
		return isInitialized;
	}

	public static final boolean isCanDraw() {
		return isCanDraw;
	}

	public static enum AnimationType {
		SLIDE_LEFT, SLIDE_RIGHT, SLIDE_TOP, SLIDE_BOTTOM, SLIDE_RANDOM;
	}

	public static ContainerManager getInstance() {
		if (instance == null) {
			instance = new ContainerManager();
			isInitialized = true;
		}

		return instance;
	}

	public final class Render {

		private Render() {
			getContext().registerMethod("draw", this);
		}

		public void draw() {
			isCanDraw = true;
			ContainerManager.this.draw();
			isCanDraw = false;
		}
	}

	private void lauchContainer(Container container) {
		if (containerList.size() <= 1) {
			throw new IllegalStateException(
					"cannot switch container when ContainerManager have only 1 container inner");
		}

		if (container == null) {
			throw new NullPointerException("container cannot be null");
		}

		if (!containerList.contains(container)) {
			throw new IllegalArgumentException("container is not found in to ContainerManager");
		}

		prevContainer = currentContainer;
		currentContainer = container;
		animation.setAnimationRunningEnabled(true);
	}

	private void addContainerSafe(Container container) {
		requireNonNull(container, "container cannot be null");
		if (containerList.contains(container)) {
			throw new IllegalArgumentException("container cannot be added twice");
		}

		container.setConstrainDimensionsEnabled(true);
		container.setMaxSize(ctx.width, ctx.height);
		container.setMinSize(ctx.width, ctx.height);

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

	private void debugOnDraw() {
		if (MicroUI.isDebugModeEnabled()) {
			ctx.push();
			ctx.fill(255);
			ctx.textSize(24);
			ctx.text("fps: " + (int) ctx.frameRate, 10, 24);
			ctx.pop();
		}
	}

	private final class Animation extends View {
		private static final float MAX_DIST = dist(0, 0, ctx.width, ctx.height);
		private AnimationType animationType;
		private final ImageBuffer prevContainerImage,currentContainerImage;
		private float speed;
		private int randDirX, randDirY;
		private boolean isAnimationRunningEnabled, isNewContainerPrepared, isEasing;

		private Animation() {
			super();
			setVisible(true);
			
			prevContainerImage = new ImageBuffer();
			currentContainerImage = new ImageBuffer();
			
			prevContainerImage.setSize(ctx.width,ctx.height);
			currentContainerImage.setSize(ctx.width,ctx.height);
			
			setSpeed(max(1, ctx.width * .1f));
			setAnimationType(AnimationType.SLIDE_RANDOM);
			setEasingEnabled(true);
		}

		@Override
		protected void render() {
			if (isAnimationRunningEnabled()) {
				if(!prevContainerImage.isLoaded()) {
					prevContainer.draw();
					prevContainerImage.set(getScreenBuffer());
					return;
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
				
				
				if (isAnimationRunningEnabled()) {
					if(!currentContainerImage.isLoaded()) {
						currentContainer.draw();
						currentContainerImage.set(getScreenBuffer());
					}
					
					prevContainerImage.draw();
					currentContainerImage.draw();
				}
			
			}
			
			if(!isAnimationRunningEnabled()) {
				prevContainerImage.removeTexture();
				currentContainerImage.removeTexture();
				
				currentContainer.draw();
			}
		}

		PImage getScreenBuffer() {
			return ctx.get(0,0,ctx.width,ctx.height);
		
		}
		
		boolean isAnimationRunningEnabled() {
			return isAnimationRunningEnabled;
		}

		void setAnimationRunningEnabled(boolean isAnimationRunningEnabled) {
			this.isAnimationRunningEnabled = isAnimationRunningEnabled;
		}

		void complete() {
			prevContainerImage.setBounds(0, 0, ctx.width, ctx.height);
			currentContainerImage.setBoundsFrom(prevContainerImage);
			setAnimationRunningEnabled(false);
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

		private int getSpeedInternal() {
			int additionalMinSpeed = 1;
			if (isEasing) {
				float dist = max(abs(currentContainerImage.getX()), abs(currentContainerImage.getY()));
				return (int) (additionalMinSpeed + map(abs(dist), 0, MAX_DIST, .1f, speed));
			}
			return (int) (additionalMinSpeed + speed);
		}

		private void slideDirection(int dirX, int dirY) {
			if (!isNewContainerPrepared) {
				currentContainerImage.setPosition(dirX == -1 ? ctx.width : dirX == 1 ? -ctx.width : 0,
						dirY == -1 ? ctx.height : dirY == 1 ? -ctx.height : 0);
				isNewContainerPrepared = true;
			}

			if (dirX == -1) {
				prevContainerImage.appendX(-getSpeedInternal());
				currentContainerImage.appendX(-getSpeedInternal(), 0, ctx.width);
			}

			if (dirX == 1) {
				prevContainerImage.appendX(getSpeedInternal());
				currentContainerImage.appendX(getSpeedInternal(), -ctx.width, 0);
			}

			if (dirY == -1) {
				prevContainerImage.appendY(-getSpeedInternal());
				currentContainerImage.appendY(-getSpeedInternal(), 0, ctx.height);
			}

			if (dirY == 1) {
				prevContainerImage.appendY(getSpeedInternal());
				currentContainerImage.appendY(getSpeedInternal(), -ctx.height, 0);
			}

			if (dirX != 0) {
				if ((int) currentContainerImage.getX() == 0) {
					complete();
				}
			}

			if (dirY != 0) {
				if ((int) currentContainerImage.getY() == 0) {
					complete();
				}
			}
		}

	}

}