package microui.container.layout;

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

import java.util.ArrayList;

import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Container;
import microui.core.base.Layout;

public class GridLayout extends Layout {
	  private boolean fillTheGrid;
	  private int cols,rows;
	  private final ArrayList<Integer> columnList,rowList;
	  private final ArrayList<Float> elementDefaultWidth,elementDefaultHeight;
	  
	  public GridLayout() {
		  this(1);
	  }
	  
	  public GridLayout(int cells) {
		  this(0,0,app.width,app.height,cells,cells);
	  }
	  
	  public GridLayout(int rows, int columns) {
		  this(0,0,app.width,app.height,rows,columns);
	  }
	  
	  public GridLayout(float x, float y, float w, float h) { this(x,y,w,h,3,3); }
	  
	  public GridLayout(float x, float y, float w, float h, int rows, int columns) {
	    super(x,y,w,h);
	    
	    setGrid(rows,columns);
	    setElementsResizable(true);
	    
	    columnList = new ArrayList<Integer>();
	    rowList = new ArrayList<Integer>();
	    elementDefaultWidth = new ArrayList<Float>();
	    elementDefaultHeight = new ArrayList<Float>();
	  }
	  
	  @Override
		public void draw() {
			super.draw();
			elementsDraw();
	  }
	  
	  @Override
	  public void update() {
		  super.update();
		  gridDraw();
		  
	  }
	  
	  public final boolean isEmpty() {
		  return elementList.isEmpty();
	  }
	  
	  public final boolean isFull() {
		  return max(1,cols)*max(1,rows) == elementList.size();
	  }
	  
	  private void elementsDraw() {
		  	if(elementList.isEmpty()) { return; }
		  	app.pushStyle();
		  	elementList.forEach(elements -> elements.draw());
		  	app.popStyle();
	  }
	  
	  private void gridDraw() {
		  app.pushStyle();
			  app.noFill();
			  app.stroke(0);
		      for(float x = getX(); x < getX()+getWidth(); x += getWidth()/getColumns()) {
		        for(float y = getY(); y < getY()+getHeight(); y += getHeight()/getRows()) {
		          if(ceil(x) < getX()+getWidth() && ceil(y) < getY()+getHeight()) {
		        	  app.rect(x,y,getWidth()/cols,getHeight()/rows);
		          }
		        }
		      }
		    app.popStyle();
	  }
	  
	  public void setColumns(int cols) { this.cols = cols; }
	  public int getColumns() { return cols; }
	  
	  public void setRows(int rows) { this.rows = rows; }
	  public int getRows() { return rows; }
	  
	  public void setGrid(int columns, int rows) {
	    setColumns(max(1,columns));
	    setRows(max(1,rows));
	  }
	  
	  public GridLayout add(Bounds bounds, int row, int column) {
		if(row < 0 || row > getColumns()-1 || column < 0 || column > getRows()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		if(!isEmptyCell(row,column)) { return this; }
	    
		addIfAbsent(bounds,row,column);
		
		return this;
	  }
	  
	  private final boolean isEmptyCell(final int row, final int column) {
		  boolean empty = true;
		  
		  for(int i = 0; i < elementList.size(); i++) {
			  if(row == columnList.get(i) && column == rowList.get(i)) { empty = false; }
		  }
		  
		  return empty;
	  }
	  
	  public GridLayout add(Bounds bounds) {
		if(isFull()) { return this; }
		
		mainLoop :
		for(int column = 0; column < rows; column++) {
			for(int row = 0; row < cols; row++) {
				if(isEmptyCell(row,column)) {
					add(bounds,row,column);
					break mainLoop;
				}
			}
		}
		
	    return this;
	  }
	  
	  public GridLayout add(String txt, int column, int row) {
		add(new TextView(txt),column,row);
		return this; 
	  }
	  
	  private void addIfAbsent(Bounds bounds, int row, int column) {
		    for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == bounds) {
		    		return;
		    	}
		    }
		    	elementList.add(bounds);
		    	columnList.add(row);
				rowList.add(column);
				elementDefaultWidth.add(bounds.getWidth());
				elementDefaultHeight.add(bounds.getHeight());
				
				recalcListState();
	  }

	  public void remove(Bounds bounds) {
		  if(elementList.isEmpty()) { return; }
		  for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == bounds) {
		    		columnList.remove(i);
					rowList.remove(i);
		    		elementList.remove(i);
		    		elementDefaultWidth.remove(i);
					elementDefaultHeight.remove(i);
		    	}
		    }
	  }
	  
	  public void remove(int index) {
		  if(elementList.isEmpty()) { return; }
		  elementList.remove(index);
		  columnList.remove(index);
		  rowList.remove(index);
		  elementDefaultWidth.remove(index);
		  elementDefaultHeight.remove(index);
	  }
	  
	  public boolean isFillTheGrid() {
			return fillTheGrid;
	  }

	  public void setFillTheGrid(boolean f) {
			fillTheGrid = f;
	  }

	  @Override
	  protected final void recalcListState() {
		  
		  for(int i = 0; i < elementList.size(); i++) {
			  Bounds bounds = elementList.get(i);
			  
			  if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			        bounds.setSize(getWidth()/getColumns(),getHeight()/getRows());
			      } else {
			    	bounds.setSize(min(elementDefaultWidth.get(i),getWidth()/getColumns()), min(elementDefaultHeight.get(i),getHeight()/getRows()));
			    }
			  }
			  
			  if(bounds instanceof Container container) {
				  container.setX(map(columnList.get(i),0,cols,getX(),getX()+getWidth())+((getWidth()/getColumns()/2)-container.getRealWidth()/2));
				  container.setY(map(rowList.get(i),0,rows,getY(),getY()+getHeight())+((getHeight()/getRows()/2)-container.getRealHight()/2));

			    } else {
			    	
			    	bounds.setPosition(
				    		map(columnList.get(i),0,GridLayout.this.cols,getX(),getX()+getWidth())+((getWidth()/getColumns())/2)-bounds.getWidth()/2,
				    		map(rowList.get(i),0,GridLayout.this.rows,getY(),getY()+getHeight())+((getHeight()/getRows())/2)-bounds.getHeight()/2
				    );
			    }
			  
			  }
	  }
	  
} 