package microui.layout;

public final class GridLayoutParams implements LayoutParams {
	private int col,row,colSpan,rowSpan;

	public GridLayoutParams(int col, int row, int colSpan, int rowSpan) {
		super();
		if(col < 0 || row < 0 || colSpan < 0 || rowSpan < 0) {
			throw new IllegalArgumentException("grid layout params cannot be less than zero");
		}
		this.col = col;
		this.row = row;
		this.colSpan = colSpan;
		this.rowSpan = rowSpan;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getColSpan() {
		return colSpan;
	}

	public int getRowSpan() {
		return rowSpan;
	}
	
}