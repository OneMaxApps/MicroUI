package microui.core.effect;

import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;
import microui.core.style.Theme;
import microui.util.Metrics;
import processing.core.PGraphics;

//Status: STABLE - Do not modify
//Last Reviewed: 16.09.2025
public final class Ripples extends View {
	private final Animation animation;
	private final Component component;
	private boolean isEnabled, isLaunched;
	private PGraphics pg;

	public Ripples(Component component) {
		super();
		setVisible(true);
		
		animation = new Animation();
		
		this.component = requireNonNull(component, "component cannot be null");
		initCallbackForComponent();
		
		setEnabled(true);
	}

	@Override
	public void draw() {
		if (!isEnabled()) {
			return;
		}
		super.draw();
	}

	@Override
	protected void render() {
		if (pg == null || !isLaunched()) {
			return;
		}

		pg.beginDraw();
		pg.clear();
		pg.pushStyle();
		animation.render(pg);
		pg.popStyle();
		pg.endDraw();

		ctx.image(pg, component.getPadX(), component.getPadY(), component.getPadWidth(), component.getPadHeight());
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public Color getColor() {
		return animation.getColor();
	}

	public void setColor(Color color) {
		animation.setColor(color);
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isLaunched() {
		return isLaunched;
	}

	public void launch() {
		isLaunched = true;
		animation.resetState();
		animation.preparePosition();
	}

	private void createGraphics() {
		pg = ctx.createGraphics((int) max(1, component.getPadWidth()), (int) max(1, component.getPadHeight()),
				ctx.sketchRenderer());
		Metrics.register(pg);
	}

	private boolean isComponentResized() {
		return pg.width != (int) component.getPadWidth() || pg.height != (int) component.getPadHeight();
	}
	
	private void initCallbackForComponent() {
		component.onClick(() -> {
			if (pg == null) {
				createGraphics();
				animation.recalculateMaxRadius();
			}

			if (isComponentResized()) {
				createGraphics();
				animation.recalculateMaxRadius();
			}

			launch();
		});
	}

	private final class Animation {
		private final Color color;
		private float maxRadius;
		private int startX, startY, radius;
		private boolean isPositionPrepared;

		Animation() {
			color = new Color(Theme.getTheme().getRipplesColor());
		}

		void render(PGraphics pg) {
			if (!isLaunched) {
				return;
			}
			
			pg.noStroke();
			color.apply(pg);
			pg.fill(color.getRed(), color.getGreen(), color.getBlue(), max(0, 190 - map(radius, 0, maxRadius, 0, 190)));
			pg.circle(startX, startY, radius);

			radius += getSpeed();

			if (radius > maxRadius) {
				complete();
			}
		}

		Color getColor() {
			return new Color(color);
		}

		void setColor(Color color) {
			if (color == null) {
				throw new NullPointerException("color cannot be null");
			}

			this.color.set(color);
		}

		void preparePosition() {
			if (!isPositionPrepared) {
				startX = (int) (ctx.mouseX - component.getPadX());
				startY = (int) (ctx.mouseY - component.getPadY());
				isPositionPrepared = true;
			}
		}

		void resetState() {
			startX = startY = -1;
			isPositionPrepared = false;
			radius = 0;
		}

		void recalculateMaxRadius() {
			maxRadius = dist(0, 0, component.getPadWidth(), component.getPadHeight())*2;
		}

		void complete() {
			resetState();
			isLaunched = false;
		}
		
		float getSpeed() {
			return ((maxRadius + (radius * .2f)) * .04f);
		}
	}
}