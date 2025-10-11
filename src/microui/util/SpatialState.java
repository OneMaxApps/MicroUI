package microui.util;

import java.util.Objects;

import microui.core.base.SpatialView;

public record SpatialState(float x, float y, float width, float height) {
	
	public SpatialState(SpatialView source) {
		this(Objects.requireNonNull(source.getX(),"the source object for SpatialState cannot be null"),source.getY(),source.getWidth(),source.getHeight());
	}
	
	public SpatialState(SpatialState source) {
		this(Objects.requireNonNull(source.x(),"the source object for SpatialState cannot be null"),source.y(),source.width(),source.height());
	}
}