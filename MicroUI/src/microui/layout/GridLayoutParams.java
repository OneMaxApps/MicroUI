package microui.layout;

public final class GridLayoutParams implements LayoutParams {
	private int column,row,columnSpan,rowSpan;

	public GridLayoutParams(int column, int row, int columnSpan, int rowSpan) {
		super();
		if(column < 0 || row < 0 || columnSpan < 0 || rowSpan < 0) {
			throw new IllegalArgumentException("grid layout params cannot be less than zero");
		}
		this.column = column;
		this.row = row;
		this.columnSpan = columnSpan;
		this.rowSpan = rowSpan;
	}
	
	public GridLayoutParams(int column, int row) {
		this(column,row,1,1);
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public int getColumnSpan() {
		return columnSpan;
	}

	public int getRowSpan() {
		return rowSpan;
	}
	
}