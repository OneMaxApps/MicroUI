package microui.service;

import static microui.MicroUI.getContext;

import java.util.ArrayList;
import java.util.List;

import microui.core.base.View;

public final class Render {
	private static final List<View> viewList = new ArrayList<View>();
	
	public Render() {
		getContext().registerMethod("draw", this);
	}

	
	public void draw() {
		for(int i = 0; i < viewList.size(); i++) {
			viewList.get(i).draw();
		}
		
		GlobalTooltip.draw();
	}
	
	public void addView(View view) {
		if(view == null) {
			throw new NullPointerException("view cannot be null");
		}
		
		if(viewList.contains(view)) {
			throw new IllegalArgumentException("view object cannot be added twice");
		}
		
		viewList.add(view);
	}
}