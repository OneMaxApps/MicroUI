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
	protected void isCorrectParams() {
		getComponentEntryList().forEach(entry -> {
			if(!(entry.getLayoutParams() instanceof GridLayoutParams)) {
				throw new RuntimeException("using not correct layout params for GridLayout");
			}
		});
	}

	@Override
	public void recalculate() {
		float x = getContainer().getContentX();
		float y = getContainer().getContentY();
		float w = getContainer().getContentWidth();
		float h = getContainer().getContentHeight();

		float colWidth = w / getColumns();
		float rowHeight = h / getRows();

		for (int i = 0; i < getComponentEntryList().size(); i++) {
			Component component = getComponentEntryList().get(i).getComponent();
			GridLayoutParams params = (GridLayoutParams) getComponentEntryList().get(i).getLayoutParams();

			checkIsOutOfGrid(params);

			float componentPreferredX = x + colWidth * params.getColumn();
			float componentPreferredY = y + rowHeight * params.getRow();
			float componentPreferredWidth = colWidth * params.getColumnSpan();
			float componentPreferredHeight = rowHeight * params.getRowSpan();

			switch (getContainer().getContainerMode()) {
			case STRICT:
				component.setConstrainDimensionsEnabled(false);
				component.setAbsoluteSize(componentPreferredWidth, componentPreferredHeight);
				component.setAbsolutePosition(componentPreferredX, componentPreferredY);
				break;

			case FLEXIBLE:
				// in this mode container not ignore constrains of component(s), so any changes may be not round into the cell
				// setting absolute size can change real size of component but only if component allowed it
				// also this mode don't control constrain mode for them, that need to be controlled with user settings like setConstrainDimensionsEnabled(boolean) etc
				component.setAbsoluteSize(componentPreferredWidth, componentPreferredHeight);

				float centerXOnCell = componentPreferredX+componentPreferredWidth/2-component.getAbsoluteWidth()/2;
				float centerYOnCell = componentPreferredY+componentPreferredHeight/2-component.getAbsoluteHeight()/2;
				boolean isCellWidthBigger = component.getAbsoluteWidth() < componentPreferredWidth;
				boolean isCellHeightBigger = component.getAbsoluteHeight() < componentPreferredHeight;
				
				component.setAbsoluteX(isCellWidthBigger ? centerXOnCell : componentPreferredX);
				component.setAbsoluteY(isCellHeightBigger ? centerYOnCell : componentPreferredY);
				
				break;
			}

		}
	}

	@Override
	public void onAddComponent() {
		super.onAddComponent();
		checkComponentsForOverlap();
		isCorrectParams();
	}

	@Override
	public void onRemoveComponent() {
		super.onRemoveComponent();

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

	private void checkIsOutOfGrid(GridLayoutParams params) {
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