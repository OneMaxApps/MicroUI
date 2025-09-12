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
//Last Reviewed: 12.09.2025
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
	public void render() {
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

	public void remove(final String... textId) {
		requireNonNull(textId, "textId's cannot be null");

		for (String currentTextId : textId) {
			removeContainerSafe(currentTextId);
		}

	}

	public void switchOn(Container container) {
		if (this.currentContainer == container) {
			return;
		}

		requireNonNull(container, "container object cannot be null");
		if (!containerList.contains(container)) {
			throw new IllegalArgumentException("container is not found");
		}

		lauchContainer(container);
	}
	
	public void switchOn(Container container, AnimationType animationType) {
		setAnimationType(animationType);
		switchOn(container);
	}

	public void switchOn(int id) {
		if (currentContainer.getId() == id) {
			return;
		}

		boolean isFound = false;
		for (Container container : containerList) {
			if (container.getId() == id) {
				isFound = true;
				lauchContainer(container);
				return;
			}
		}

		if (!isFound) {
			throw new IllegalArgumentException("container id: \"" + id + "\" is not found in to ContainerManager");
		}
	}
	
	public void switchOn(int id, AnimationType animationType) {
		setAnimationType(animationType);
		switchOn(id);
	}

	public void switchOn(String textId) {
		if (currentContainer.getTextId().equals(textId)) {
			return;
		}

		boolean isFound = false;
		for (Container container : containerList) {
			if (container.getTextId().equals(textId)) {
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
	
	public void switchOn(String textId, AnimationType animationType) {
		setAnimationType(animationType);
		switchOn(textId);
	}
	
	public Container getContainerById(int id) {
		return containerList.stream()
			  .filter(container -> container.getId() == id)
			  .findFirst()
			  .orElseThrow(() -> new RuntimeException("container is not found"));
	}
	
	public Container getContainerByTextId(String textId) {
		return containerList.stream()
			  .filter(container -> container.getTextId() == textId)
			  .findFirst()
			  .orElseThrow(() -> new RuntimeException("container is not found"));
	} 

	public static enum AnimationType {
		SLIDE_LEFT, SLIDE_RIGHT, SLIDE_TOP, SLIDE_BOTTOM, SLIDE_RANDOM;
	}

	private void lauchContainer(Container container) {
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
		
		containerList.add(container);

		if (currentContainer == null) {
			currentContainer = container;
		}
	}
	
	private void removeContainerSafe(Container container) {
		requireNonNull(container, "container cannot be null");
		
		Container correctContainer = containerList.stream().filter(c -> c == container).findFirst().orElseThrow(() -> new RuntimeException("container is not found in to ContainerManager"));
		
		containerList.remove(correctContainer);
		
		if(prevContainer == correctContainer) { prevContainer = null; }
		if(currentContainer == correctContainer) { currentContainer = null; }
		
	}
	
	private void removeContainerSafe(String textId) {
		requireNonNull(textId, "textId cannot be null");
		
		Container correctContainer = containerList.stream().filter(c -> c.getTextId().equals(textId)).findFirst().orElseThrow(() -> new RuntimeException("container is not found in to ContainerManager"));
		
		containerList.remove(correctContainer);
		
		if(prevContainer == correctContainer) { prevContainer = null; }
		if(currentContainer == correctContainer) { currentContainer = null; }
		
	}
	
	private final class Animation extends View {
		private AnimationType animationType;
		private float speed;
		private int typeOfRandomSlide;
		private boolean isStart, isNewContainerPrepared, isEasing;

		private Animation() {
			super();
			setVisible(true);
			setSpeed(max(1, ctx.width * .2f));
			setAnimationType(SLIDE_LEFT);
			setEasingEnabled(true);
		}

		@Override
		protected void render() {
			if (isStart()) {
				
				if(prevContainer != null) {
					prevContainer.draw();
				}

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
			currentContainer.setBoundsFrom(prevContainer);
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