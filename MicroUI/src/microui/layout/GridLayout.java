package microui.layout;

import microui.core.base.Component;
import microui.core.base.Container.ComponentEntry;

public final class GridLayout extends LayoutManager {
	private int columns, rows;

	public GridLayout(int columns, int rows) {
		super();
		setColumns(columns);
		setRows(rows);

	}

	@Override
	public void debugOnDraw() {
		ctx.pushStyle();
		ctx.stroke(0, 128);
		ctx.noFill();
		for (int col = 0; col < columns; col++) {
			for (int row = 0; row < rows; row++) {
				ctx.rect(getContainer().getContentX() + (getContainer().getContentWidth() / columns) * col,
						getContainer().getContentY() + (getContainer().getContentHeight() / rows) * row,
						getContainer().getContentWidth() / columns, getContainer().getContentHeight() / rows);
			}
		}
		ctx.popStyle();
	}

	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if (!(layoutParams instanceof GridLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for GridLayout");
		}
	}

	@Override
	public void recalculate() {
		float containerContentX = getContainer().getContentX();
		float containerContentY = getContainer().getContentY();
		float containerContentW = getContainer().getContentWidth();
		float containerContentH = getContainer().getContentHeight();

		float colWidth = containerContentW / getColumns();
		float rowHeight = containerContentH / getRows();

		for (int i = 0; i < getComponentEntryList().size(); i++) {
			Component component = getComponentEntryList().get(i).getComponent();
			GridLayoutParams params = (GridLayoutParams) getComponentEntryList().get(i).getLayoutParams();

			checkOutOfGrid(params);

			float requiredCellX = containerContentX + colWidth * params.getColumn();
			float requiredCellY = containerContentY + rowHeight * params.getRow();
			float requiredCellWidth = colWidth * params.getColumnSpan();
			float requiredCellHeight = rowHeight * params.getRowSpan();
			
			switch (getContainer().getContainerMode()) {
			case IGNORE_CONSTRAINTS:
				component.setConstrainDimensionsEnabled(false);
				component.setAbsoluteSize(requiredCellWidth, requiredCellHeight);
				component.setAbsolutePosition(requiredCellX, requiredCellY);
				break;

			case RESPECT_CONSTRAINTS:
				// in this mode container not ignore constrains of component(s), so any changes
				// may be not round into the cell
				// setting absolute size can change real size of component but only if component
				// allowed it
				// also this mode don't control constrain mode for them, that need to be
				// controlled with user settings like setConstrainDimensionsEnabled(boolean) etc
				
				// if constrain in the component is enabled, them it's not guaranteed about correct resize
				component.setAbsoluteSize(requiredCellWidth, requiredCellHeight);
				
				float alignXLeft = requiredCellX;
				float alignXCenter = requiredCellX+requiredCellWidth/2-component.getAbsoluteWidth()/2;
				float alignXRight = requiredCellX+requiredCellWidth-component.getAbsoluteWidth();
				
				float alignYTop = requiredCellY;
				float alignYCenter = requiredCellY+requiredCellHeight/2-component.getAbsoluteHeight()/2;
				float alignYBottom = requiredCellY+requiredCellHeight-component.getAbsoluteHeight();
				
				float correctPosX = params.getAlignX() == -1 ? alignXLeft : params.getAlignX() == 1 ? alignXRight : alignXCenter;
				float correctPosY =  params.getAlignY() == -1 ? alignYTop : params.getAlignY() == 1 ? alignYBottom : alignYCenter;
	
				component.setAbsolutePosition(correctPosX, correctPosY);

				break;
			}

		}
	}

	@Override
	public void onAddComponent(ComponentEntry componentEntry) {
		super.onAddComponent(componentEntry);
		checkComponentsForOverlap();
	}

	public final int getColumns() {
		return columns;
	}

	public final int getRows() {
		return rows;
	}

	private void setColumns(int columns) {
		if (columns < 1) {
			throw new IllegalArgumentException("columns in grid layout cannot be less than 1");
		}
		this.columns = columns;
	}

	private void setRows(int rows) {
		if (rows < 1) {
			throw new IllegalArgumentException("rows in grid layout cannot be less than 1");
		}
		this.rows = rows;
	}

	private void checkOutOfGrid(GridLayoutParams params) {
		if (params.getColumn() + (params.getColumnSpan() - 1) >= getColumns()
				|| (params.getRow() + params.getRowSpan() - 1) >= getRows()) {
			throw new IndexOutOfBoundsException("component is out of grid layout");
		}
	}

	private void checkComponentsForOverlap() {
		for (ComponentEntry entry : getComponentEntryList()) {

			GridLayoutParams params = (GridLayoutParams) entry.getLayoutParams();

			for (ComponentEntry otherEntry : getComponentEntryList()) {

				GridLayoutParams paramsOther = (GridLayoutParams) otherEntry.getLayoutParams();

				int pc = params.getColumn(), pcs = params.getColumnSpan(), pr = params.getRow(),
						prs = params.getRowSpan();

				int opc = paramsOther.getColumn(), opcs = paramsOther.getColumnSpan(), opr = paramsOther.getRow(),
						oprs = paramsOther.getRowSpan();

				if (params != paramsOther) {
					if (pc > opc - pcs && pc < opc + opcs && pr > opr - prs && pr < opr + oprs) {
						throw new IllegalArgumentException("several components cannot be in one cell of grid");
					}
				}

			}
		}
	}

}