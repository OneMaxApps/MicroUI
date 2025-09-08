package microui.layout;

import java.util.HashMap;

import microui.core.base.Component;

public final class GridLayout extends LayoutManager {
	private final HashMap<Component, Constrants> componentConstrants;
	private int columns,rows;
	
	public GridLayout(int columns, int rows) {
		super();
		setColumns(columns);
		setRows(rows);
		
		componentConstrants = new HashMap<Component, Constrants>();
	}

	@Override
	public void recalculate() {
		for(int i = 0; i < getComponentList().size(); i++) {
			
		}
	}
	
	@Override
	public void onAddComponent(Component component) {
		componentConstrants.putIfAbsent(component, new Constrants(1,1,1,1));
	}

	@Override
	public void onRemoveComponent(Component component) {
		componentConstrants.remove(component);
	}
	
	public final void setComponentOn(Component component, int column, int row, int columnSpan, int rowSpan) {
		componentConstrants.getOrDefault(component, new Constrants(column,row, columnSpan, rowSpan));
		recalculate();
	}

	public final int getColumns() {
		return columns;
	}

	public final int getRows() {
		return rows;
	}
	
	private void setColumns(int columns) {
		if(columns < 0) { throw new IllegalArgumentException("columns in grid layout cannot be less than zero"); }
		this.columns = columns;
	}

	private void setRows(int rows) {
		if(rows < 0) { throw new IllegalArgumentException("rows in grid layout cannot be less than zero"); }
		this.rows = rows;
	}
	
	private final class Constrants {
		private int col,row,colSpan,rowSpan;

		public Constrants(int col, int row, int colSpan, int rowSpan) {
			super();
			this.col = col;
			this.row = row;
			this.colSpan = colSpan;
			this.rowSpan = rowSpan;
		}
		
	}
}