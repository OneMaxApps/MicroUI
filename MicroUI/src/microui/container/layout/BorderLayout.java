package microui.container.layout;

import microui.core.base.Layout;

//TODO For start you need to make a stable and powerful foundation of Container base system

public class BorderLayout extends Layout {

	public BorderLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		
	}
	
	public BorderLayout() {
		this(0,0,app.width,app.height);
		
	}

	@Override
	protected void recalcListState() {
		
	}
	
}