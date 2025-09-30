package microui.feedback;

import microui.core.base.Component;
import microui.core.base.SpatialView;
import microui.service.TooltipManager;
import microui.util.Metrics;

public final class Tooltip extends SpatialView {
	private TooltipContent content;

	public Tooltip(Component component) {
		super();
		setConstrainDimensionsEnabled(true);
		setMinMaxSize(4, 4, ctx.width / 2, ctx.height / 2);

		component.onEnterLong(() -> {
			if(content != null && content.isPrepared()) {
				setVisible(true);
			}
		});

		component.onLeave(() -> {
			setVisible(false);
		});

		Metrics.register(this);

	}

	public void listen() {
		if (isVisible()) {
			TooltipManager.setTooltip(this);
		}
	}
	
	public TooltipContent getContent() {
		return content;
	}

	public void setContent(TooltipContent content) {
		if(content == null) {
			throw new NullPointerException("the content for tooltip cannot be null");
		}
		this.content = content;
		
		content.setBoundsFrom(this);
	}

	@Override
	protected void render() {
		content.draw();
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		content.setBoundsFrom(this);
	}

}