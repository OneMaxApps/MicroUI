package microui.container.layout;

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

import java.util.ArrayList;

import microui.component.Dial;
import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Container;
import microui.core.base.Layout;

public class GridLayout extends Layout {
	  private boolean fillTheGrid;
	  private int rows,cols;
	  private final ArrayList<Integer> columnList,rowList;
	  private final ArrayList<Float> elementDefaultWidth,elementDefaultHeight;
	  
	  public GridLayout() {
		  this(1);
	  }
	  
	  public GridLayout(int cells) {
		  this(0,0,app.width,app.height,cells,cells);
	  }
	  
	  public GridLayout(int columns, int rows) {
		  this(0,0,app.width,app.height,columns,rows);
	  }
	  
	  public GridLayout(float x, float y, float w, float h) { this(x,y,w,h,3,3); }
	  
	  public GridLayout(float x, float y, float w, float h, int columns, int rows) {
	    super(x,y,w,h);
	    
	    setGrid(columns,rows);

	    columnList = new ArrayList<Integer>();
	    rowList = new ArrayList<Integer>();
	    elementDefaultWidth = new ArrayList<Float>();
	    elementDefaultHeight = new ArrayList<Float>();
	  }
	  
	  @Override
		public void draw() {
			super.draw();
			drawElements();
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
	    
	    recalcListState();
	  }
	  
	  public GridLayout add(Bounds bounds, int col, int row) {
		if(col < 0 || col > getColumns()-1 || row < 0 || row > getRows()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		if(!isEmptyCell(col,row)) { return this; }
	    
		addIfAbsent(bounds,col,row);
		
		return this;
	  }
	  
	  private final boolean isEmptyCell(final int column, final int row) {
		  boolean empty = true;
		  
		  for(int i = 0; i < elementList.size(); i++) {
			  if(column == columnList.get(i) && row == rowList.get(i)) { empty = false; }
		  }
		  
		  return empty;
	  }
	  
	  public GridLayout add(Bounds bounds) {
		if(isFull()) { return this; }
		
		mainLoop :
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < cols; column++) {
				if(isEmptyCell(column,row)) {
					add(bounds,column,row);
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
	  
	  public GridLayout add(String txt) {
		add(new TextView(txt));
		return this; 
	  }
	  
	  private void addIfAbsent(Bounds bounds, int column, int row) {
		    for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == bounds) {
		    		return;
		    	}
		    }
		    	elementList.add(bounds);
		    	columnList.add(column);
				rowList.add(row);
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
		  if(elementList == null) { return; }
		  
		  for(int i = 0; i < elementList.size(); i++) {
			  Bounds bounds = elementList.get(i);
			  
			  	if(isFillTheGrid()) {
		    	  bounds.setSize(getWidth()/getColumns(),getHeight()/getRows());
		      	} else {
		      	  bounds.setSize(min(elementDefaultWidth.get(i),getWidth()/getColumns()), min(elementDefaultHeight.get(i),getHeight()/getRows()));
		      	}
			  
			  if(bounds instanceof Container container) {
				  container.setX(map(columnList.get(i),0,cols,getX(),getX()+getWidth())+((getWidth()/getColumns()/2)-container.getRealWidth()/2));
				  container.setY(map(rowList.get(i),0,rows,getY(),getY()+getHeight())+((getHeight()/getRows()/2)-container.getRealHeight()/2));

			    } else {
			    	if(bounds instanceof Dial dial) {
			    		dial.setPosition(
					    		map(columnList.get(i),0,GridLayout.this.cols,getX(),getX()+getWidth())+((getWidth()/getColumns())/2)-(Math.min(dial.getWidth(),dial.getHeight()))/2,
					    		map(rowList.get(i),0,GridLayout.this.rows,getY(),getY()+getHeight())+((getHeight()/getRows())/2)-(Math.min(dial.getWidth(),dial.getHeight()))/2
					    );
			    	} else {
				    	bounds.setPosition(
					    		map(columnList.get(i),0,GridLayout.this.cols,getX(),getX()+getWidth())+((getWidth()/getColumns())/2)-bounds.getWidth()/2,
					    		map(rowList.get(i),0,GridLayout.this.rows,getY(),getY()+getHeight())+((getHeight()/getRows())/2)-bounds.getHeight()/2
					    );
			    	}
			    }
			  
			  }
	  }
	  
} 