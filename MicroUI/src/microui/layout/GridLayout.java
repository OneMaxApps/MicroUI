package microui.layout;

import microui.core.base.Component;

public final class GridLayout extends LayoutManager {
	private int columns,rows;
	
	public GridLayout(int columns, int rows) {
		super();
		setColumns(columns);
		setRows(rows);
		
	}

	@Override
	public void recalculate() {

		float x = getContainer().getContentX();
		float y = getContainer().getContentY();
		float w = getContainer().getContentWidth();
		float h = getContainer().getContentHeight();
		
		float colWidth = w/getColumns();
		float rowHeight = h/getRows();
		
		for(int i = 0; i < getComponentList().size(); i++) {
			Component component = getComponentList().get(i).getComponent();
			GridLayoutParams params = (GridLayoutParams) getComponentList().get(i).getParams();
			
			component.setConstrainDimensionsEnabled(false);
			
			checkIsOutOfGrid(params);

			float componentPrefferedWidth = colWidth*params.getColSpan();
			float componentPrefferedHeight = rowHeight*params.getRowSpan();
			
			component.setAbsoluteWidth(componentPrefferedWidth);
			component.setAbsoluteHeight(componentPrefferedHeight);
			component.setAbsoluteX(x+colWidth*params.getCol());
			component.setAbsoluteY(y+rowHeight*params.getRow());
		}
	}
	
	@Override
	public void onAddComponent(Component component) {
	}

	@Override
	public void onRemoveComponent(Component component) {
	}
	
	public final int getColumns() {
		return columns;
	}

	public final int getRows() {
		return rows;
	}
	
	private void setColumns(int columns) {
		if(columns < 1) { throw new IllegalArgumentException("columns in grid layout cannot be less than zero"); }
		this.columns = columns;
	}

	private void setRows(int rows) {
		if(rows < 1) { throw new IllegalArgumentException("rows in grid layout cannot be less than zero"); }
		this.rows = rows;
	}
	
	private void checkIsOutOfGrid(GridLayoutParams params) {
		if(params.getCol()+(params.getColSpan()-1) >= getColumns() || (params.getRow()+params.getRowSpan()-1) >= getRows()) {
			throw new IndexOutOfBoundsException("out of grid layout");
		}
	}
}