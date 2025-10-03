package microui.feedback;

import microui.core.base.Component;
import microui.core.base.SpatialView;
import microui.service.TooltipManager;
import microui.util.Metrics;

public final class Tooltip extends SpatialView {
	private TooltipContent content;
	
	public Tooltip(Component component) {
		super();
		setConstrainDimensionsEnabled(false);
		setNegativeDimensionsEnabled(false);
		
		component.onEnterLong(() -> {
			if(content != null && content.isPrepared()) {
				setVisible(true);
			}
		});

		component.onLeave(() -> {
			setVisible(false);
		});
		
		component.onPress(() -> setVisible(false));

		Metrics.register(this);

	}

	public void listen() {
		if (isVisible()) {
			TooltipManager.setTooltip(this);
		}
	}

	public final TooltipContent getContent() {
		return content;
	}

	public void setContent(TooltipContent content) {
		if(content == null) {
			throw new NullPointerException("the content for tooltip cannot be null");
		}
		this.content = content;
		content.setTooltip(this);
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