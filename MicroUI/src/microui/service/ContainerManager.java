package microui.service;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;
import static microui.service.ContainerManager.AnimationType.SLIDE_LEFT;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;

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
	private final Animation animation;
	private Container prevContainer, currentContainer;

	public ContainerManager() {
		setVisible(true);
		containerList = new ArrayList<Container>();
		animation = new Animation();
		
	}

	@Override
	public void update() {
		animation.draw();
	}

	@Override
	public void keyPressed() {
		if (currentContainer == null) {
			return;
		}
		currentContainer.keyPressed();
	}

	@Override
	public void mouseWheel(MouseEvent mouseEvent) {
		if (currentContainer == null) {
			return;
		}
		currentContainer.mouseWheel(mouseEvent);
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

	public void add(Container container, String textID) {
		requireNonNull(container, "container cannot be null");
		requireNonNull(textID, "textID cannot be null");
		if (textID.isEmpty()) {
			throw new IllegalArgumentException("textID cannot be empty");
		}
		if (containerList.contains(container)) {
			throw new IllegalArgumentException("container cannot be added twice");
		}

		container.setTextID(textID);
		container.setSize(ctx.width, ctx.height);

		containerList.add(container);

		if (currentContainer == null) {
			currentContainer = container;
		}
	}

	public void add(Container container, int id) {
		requireNonNull(container, "container cannot be null");
		if (containerList.contains(container)) {
			throw new IllegalArgumentException("container cannot be added twice");
		}

		container.setID(id);

		containerList.add(container);

		if (currentContainer == null) {
			currentContainer = container;
		}
	}

	public void remove(final Container... containers) {
		requireNonNull(containers, "containers cannot be null");

		for (Container container : containers) {
			requireNonNull(container, "container cannot be null");
			if (!containerList.removeIf(innerContainer -> {

				boolean isFound = container == innerContainer;

				if (isFound && currentContainer == container) {
					currentContainer = null;
				}

				return isFound;

			})) {
				throw new IllegalArgumentException("container is not found in to Container Manager");
			}
		}

	}

	public void remove(final String... textID) {
		requireNonNull(textID, "textID cannot be null");

		for (String currentTextID : textID) {
			if (containerList.removeIf(container -> {
				boolean isFound = container.getTextID().equals(currentTextID);

				if (isFound) {
					if (currentContainer == container) {
						currentContainer = null;
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

//	public void switchOn(Container container) {
//		if (this.currentContainer == container) {
//			return;
//		}
//
//		requireNonNull(container, "container object cannot be null");
//		if (!containerList.contains(container)) {
//			throw new IllegalArgumentException("container is not found");
//		}
//
//		currentContainer = container;
//	}

//	public void switchOn(int id) {
//		if (currentContainer.getID() == id) {
//			return;
//		}
//
//		boolean isFound = false;
//		for (Container container : containerList) {
//			if (container.getID() == id) {
//				isFound = true;
//				currentContainer = container;
//				return;
//			}
//		}
//
//		if (!isFound) {
//			throw new IllegalArgumentException("container id: \"" + id + "\" is not found in to ContainerManager");
//		}
//	}

	public void switchOn(String textId) {
		if (currentContainer.getTextID().equals(textId)) {
			return;
		}

		boolean isFound = false;
		for (Container container : containerList) {
			if (container.getTextID().equals(textId)) {
				isFound = true;
				lauchContainer(container);
				return;
			}
		}

		if (!isFound) {
			throw new IllegalArgumentException(
					"container text id: \"" + textId + "\" is not found in to ContainerManager");
		}
	}

	public static enum AnimationType {
		SLIDE_LEFT, SLIDE_RIGHT, SLIDE_TOP, SLIDE_BOTTOM, SLIDE_RANDOM;
	}

	private void lauchContainer(Container container) {
		prevContainer = currentContainer;
		currentContainer = container;
		animation.setStart(true);
	}

	private final class Animation extends View {
		private AnimationType animationType;
		private float speed;
		private int typeOfRandomSlide;
		private boolean isStart, isNewContainerPrepared, isEasing;

		public Animation() {
			super();
			setVisible(true);
			setSpeed(max(1, ctx.width * .2f));
			setAnimationType(SLIDE_LEFT);
			setEasingEnabled(true);
		}

		@Override
		protected void update() {
			if (isStart()) {
				prevContainer.draw();

				switch (animationType) {
				case SLIDE_LEFT:
					if (!isNewContainerPrepared) {
						currentContainer.setX(ctx.width);
						isNewContainerPrepared = true;
					}
					prevContainer.appendX(-getSpeedInternal());
					currentContainer.appendX(-getSpeedInternal(), 0, ctx.width);
					
					if ((int) currentContainer.getX() == 0) {
						complete();
					}
					break;
				case SLIDE_RIGHT:
					if (!isNewContainerPrepared) {
						currentContainer.setX(-ctx.width);
						isNewContainerPrepared = true;
					}
					prevContainer.appendX(getSpeedInternal());
					currentContainer.appendX(getSpeedInternal(), -ctx.width, 0);
					if ((int) currentContainer.getX() == 0) {
						complete();
					}
					break;
				case SLIDE_TOP:
					if (!isNewContainerPrepared) {
						currentContainer.setY(ctx.height);
						isNewContainerPrepared = true;
					}
					prevContainer.appendY(-getSpeedInternal());
					currentContainer.appendY(-getSpeedInternal(), 0, ctx.height);
					if ((int) currentContainer.getY() == 0) {
						complete();
					}
					break;
				case SLIDE_BOTTOM:
					if (!isNewContainerPrepared) {
						currentContainer.setY(-ctx.height);
						isNewContainerPrepared = true;
					}
					prevContainer.appendY(getSpeedInternal());
					currentContainer.appendY(getSpeedInternal(), -ctx.height, 0);
					if ((int) currentContainer.getY() == 0) {
						complete();
					}
					break;
				case SLIDE_RANDOM:
					if (!isNewContainerPrepared) {
						typeOfRandomSlide = (int) ctx.random(4);
						isNewContainerPrepared = true;
						
						switch (typeOfRandomSlide) {
						case 0:
							currentContainer.setX(ctx.width);
							break;
						case 1:
							currentContainer.setX(-ctx.width);
							break;
						case 2:
							currentContainer.setY(ctx.height);
							break;
						case 3:
							currentContainer.setY(-ctx.height);
							break;
						}
						
					}

					switch (typeOfRandomSlide) {
					case 0:
						prevContainer.appendX(-getSpeedInternal());
						currentContainer.appendX(-getSpeedInternal(), 0, ctx.width);
						if ((int) currentContainer.getX() == 0) {
							complete();
						}
						break;
					case 1:
						prevContainer.appendX(getSpeedInternal());
						currentContainer.appendX(getSpeedInternal(), -ctx.width, 0);
						if ((int) currentContainer.getX() == 0) {
							complete();
						}
						break;
					case 2:
						prevContainer.appendY(-getSpeedInternal());
						currentContainer.appendY(-getSpeedInternal(), 0, ctx.height);
						if ((int) currentContainer.getY() == 0) {
							complete();
						}
						break;
					case 3:
						prevContainer.appendY(getSpeedInternal());
						currentContainer.appendY(getSpeedInternal(), -ctx.height, 0);
						if ((int) currentContainer.getY() == 0) {
							complete();
						}
						break;
					}

					
					
					break;
				}

				currentContainer.draw();

			} else {
				if (currentContainer != null) {
					currentContainer.draw();
				}
			}

		}

		boolean isStart() {
			return isStart;
		}

		void setStart(boolean isStart) {
			this.isStart = isStart;
		}

		void complete() {
			prevContainer.setBounds(0,0,ctx.width,ctx.height);
			currentContainer.setBounds(prevContainer);
			setStart(false);
			isNewContainerPrepared = false;
		}

		
		
		float getRawSpeed() {
			return speed;
		}

		void setSpeed(float speed) {
			if(speed <= 0) {
				throw new IllegalArgumentException("animation speed cannot be less or equal to zero");
			}
			this.speed = speed;
		}

		AnimationType getAnimationType() {
			return animationType;
		}

		void setAnimationType(AnimationType animationType) {
			this.animationType = animationType;
		}
		
		boolean isEasingEnabled() {
			return isEasing;
		}

		void setEasingEnabled(boolean isEasing) {
			this.isEasing = isEasing;
		}

		private float getSpeedInternal() {
			if(isEasing) {
			float dist = max(abs(currentContainer.getX()),abs(currentContainer.getY()));
				return map(abs(dist),0,dist(0, 0, ctx.width, ctx.height),.1f,speed);
			}
			return speed;
		}

	}
}