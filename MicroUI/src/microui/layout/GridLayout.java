package microui.layout;

import microui.core.base.Component;
import microui.core.base.Container.ComponentEntry;

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
		
		for(int i = 0; i < getComponentEntryList().size(); i++) {
			Component component = getComponentEntryList().get(i).getComponent();
			GridLayoutParams params = (GridLayoutParams) getComponentEntryList().get(i).getParams();
			
			component.setConstrainDimensionsEnabled(false);
			
			checkIsOutOfGrid(params);

			float componentPrefferedWidth = colWidth*params.getColSpan();
			float componentPrefferedHeight = rowHeight*params.getRowSpan();
			
			component.setAbsoluteBounds(x+colWidth*params.getCol(),y+rowHeight*params.getRow(),componentPrefferedWidth,componentPrefferedHeight);

		}
	}
	
	@Override
	public void onAddComponent() {
		checkGridOnEqualsCellsOfComponents();
		//getContainer().setMinWidth(getMinWidth());
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
	
	private void checkGridOnEqualsCellsOfComponents() {
		for(ComponentEntry entry : getComponentEntryList()) {
			GridLayoutParams params = (GridLayoutParams) entry.getParams();
			for(ComponentEntry otherEntry : getComponentEntryList()) {
				GridLayoutParams paramsOther = (GridLayoutParams) otherEntry.getParams();
				
				int pc = params.getCol(),
					pcs = params.getColSpan(),
					pr = params.getRow(),
					prs = params.getRowSpan();
				
				int opc = paramsOther.getCol(),
					opcs = paramsOther.getColSpan(),
					opr = paramsOther.getRow(),
					oprs = paramsOther.getRowSpan();
				
				if(params != paramsOther) {
					if(pc > opc-pcs && pc < opc+opcs && pr > opr-prs && pr < opr+oprs) {
						throw new IllegalArgumentException("several components cannot be in one cell of grid");
					}
				}
			}
		}
	}
	
	private float getMinWidth() {
		int[][] grid = new int[getColumns()][getRows()];
		for(ComponentEntry entry : getComponentEntryList()) {
			Component component = entry.getComponent();
			GridLayoutParams params = (GridLayoutParams) entry.getParams();
			grid[params.getCol()][params.getRow()] += component.getMinWidth();
		}
		
		int tmpMaxWidth = 0;
		for(int i = 0; i < grid.length; i++) {
			int tmpSumOfCols = 0;
			for(int j = 0; j < grid[i].length; j++) {
				tmpSumOfCols += grid[i][j];
				if(tmpSumOfCols > tmpMaxWidth) { tmpMaxWidth = tmpSumOfCols; }
			}
		}
		
		return tmpMaxWidth;
	}
}