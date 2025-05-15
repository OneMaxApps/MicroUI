package microUI.container.layout;

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PApplet.min;
import static processing.core.PApplet.max;

import java.util.ArrayList;

import microUI.component.TextView;
import microUI.core.Bounds;
import microUI.util.Metrics;
import processing.core.PApplet;

public class GridLayout extends Layout {
	  public final Transforming transforming;
	  private boolean fillTheGrid;
	  private int rows,columns;
	  private ArrayList<Integer> rowList,columnList;
	  private ArrayList<Float> elementDefaultWidth,elementDefaultHeight;
	  
	  
	  public GridLayout() {
		  this(1);
	  }
	  
	  public GridLayout(int cells) {
		  this(app,0,0,app.width,app.height,cells,cells);
	  }
	  
	  public GridLayout(int rows, int columns) {
		  this(app,0,0,app.width,app.height,rows,columns);
	  }
	  
	  public GridLayout(PApplet app, float x, float y, float w, float h) { this(app,x,y,w,h,3,3); }
	  
	  public GridLayout(PApplet app, float x, float y, float w, float h, int rows, int columns) {
	    super(x,y,w,h);
	    Metrics.Container.registerGridLayout();
	    
	    setGrid(rows,columns);
	    setElementsResizable(true);
	    
	    rowList = new ArrayList<Integer>();
	    columnList = new ArrayList<Integer>();
	    elementDefaultWidth = new ArrayList<Float>();
	    elementDefaultHeight = new ArrayList<Float>();
	    transforming = new Transforming() {
	    	@Override
	    	public void updateForce() {
				  
				  for(int i = 0; i < elementList.size(); i++) {
				  Bounds bounds = elementList.get(i);
				  
				  if(rowList.get(i) < 0 || rowList.get(i) > getRows()-1 || columnList.get(i) < 0 || columnList.get(i) > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
				    
				  if(bounds instanceof Layout) {
				    	Layout layout = (Layout) bounds;
					  	layout.setPosition(
					    		map(rowList.get(i),0,GridLayout.this.rows,getX(),getX()+getW())+((getW()/getRows())/2)-layout.getW()/2-layout.margin.getLeft(),
					    		map(columnList.get(i),0,GridLayout.this.columns,getY(),getY()+getH())+((getH()/getColumns())/2)-layout.getH()/2-layout.margin.getUp()
					    );
				    } else {
				    	bounds.setPosition(
					    		map(rowList.get(i),0,GridLayout.this.rows,getX(),getX()+getW())+((getW()/getRows())/2)-bounds.getW()/2,
					    		map(columnList.get(i),0,GridLayout.this.columns,getY(),getY()+getH())+((getH()/getColumns())/2)-bounds.getH()/2
					    );
				    }
				    
					    if(isElementsResizable()) {
					      if(isFillTheGrid()) {
					        bounds.setSize(getW()/getRows(),getH()/getColumns());
					      } else {
					    	bounds.setSize(min(elementDefaultWidth.get(i),getW()/getRows()), min(elementDefaultHeight.get(i),getH()/getColumns()));
					    }
					  }
				
				  }
			  }
	    };
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
		  if(app.frameCount == 1 || app.frameCount%60*60 == 0) { transforming.updateForce(); }
	  }
	  
	  public final boolean isEmpty() {
		  return elementList.isEmpty();
	  }
	  
	  public final boolean isFull() {
		  return max(1,rows)*max(1,columns) == elementList.size();
	  }
	  
	  private void elementsDraw() {
			app.pushStyle();
			if(!elementList.isEmpty()) {
			    for(int i = elementList.size()-1; i >= 0; i--) {
			    	elementList.get(i).draw();
			    }
			}
			
			
		    app.popStyle();
	  }
	  
	  private void gridDraw() {
		  app.pushStyle();
			  app.noFill();
			  app.stroke(0);
		      for(float x = getX(); x < getX()+getW(); x += getW()/getRows()) {
		        for(float y = getY(); y < getY()+getH(); y += getH()/getColumns()) {
		          if(ceil(x) < getX()+getW() && ceil(y) < getY()+getH()) {
		        	  app.rect(x,y,getW()/rows,getH()/columns);
		          }
		        }
		      }
		    app.popStyle();
	  }
	  
	  public void setRows(int rows) { this.rows = rows; }
	  public int getRows() { return rows; }
	  
	  public void setColumns(int columns) { this.columns = columns; }
	  public int getColumns() { return columns; }
	  
	  public void setGrid(int rows, int columns) {
	    setRows(constrain(rows,1,rows));
	    setColumns(constrain(columns,1,columns));
	  }
	  
	  public GridLayout add(Bounds bounds, int row, int column) {
		  if(row < 0 || row > getRows()-1 || column < 0 || column > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		  if(!isEmptyCell(row,column)) { return this; }
		  
		  bounds.setPosition(
		    		map(row,0,this.rows,getX(),getX()+getW())+((getW()/getRows())/2)-bounds.getW()/2,
		    		map(column,0,this.columns,getY(),getY()+getH())+((getH()/getColumns())/2)-bounds.getH()/2
		  );
		    
			    if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			        bounds.setSize(getW()/getRows(),getH()/getColumns());
			      } else {
			        if(bounds.getW() > getW()/getRows() || bounds.getW() < getW()/getRows()/2) { bounds.setW(getW()/getRows()); }
			        if(bounds.getH() > getH()/getColumns() || bounds.getH() < getH()/getColumns()/2) { bounds.setH(getH()/getColumns()); }
			      }
			    }
	    
		checkObject(bounds,row,column);
		
		transforming.updateForce();
		
		
		return this;
	  }
	  
	  private final boolean isEmptyCell(final int row, final int column) {
		  boolean empty = true;
		  
		  for(int i = 0; i < elementList.size(); i++) {
			  if(row == rowList.get(i) && column == columnList.get(i)) { empty = false; }
		  }
		  
		  return empty;
	  }
	  
	  public GridLayout add(Bounds bounds) {
		if(isFull()) { return this; }
		
		mainLoop :
		for(int column = 0; column < columns; column++) {
			for(int row = 0; row < rows; row++) {
				if(isEmptyCell(row,column)) {
					add(bounds,row,column);
					break mainLoop;
				}
			}
		}
		
	    return this;
	  }
	  
	  public GridLayout add(String txt, int row, int column) {
		  if(!isEmptyCell(row,column)) { return this; }
		  if(row < 0 || row > getRows()-1 || column < 0 || column > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		  
		  TextView baseForm = new TextView(txt,0,0,0,0);
		    baseForm.setPosition(
		    		map(row,0,this.rows,getX(),getX()+getW())+((getW()/getRows())/2)-baseForm.getW()/2,
		    		map(column,0,this.columns,getY(),getY()+getH())+((getH()/getColumns())/2)-baseForm.getH()/2
		    );
		    
		    if(isElementsResizable()) {
		      if(isFillTheGrid()) {
		        baseForm.setSize(getW()/getRows(),getH()/getColumns());
		      } else {
		        if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setW(getW()/getRows()); }
		        if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setH(getH()/getColumns()); }
		      }
		    }
	    
		checkObject(baseForm,row,column);
		
		transforming.updateForce();
		
		return this; 
	  }
	  
	  private void checkObject(Bounds bounds, int row, int column) {
		    for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == bounds) {
		    		return;
		    	}
		    }
		    	elementList.add(bounds);
		    	rowList.add(row);
				columnList.add(column);
				elementDefaultWidth.add(bounds.getW());
				elementDefaultHeight.add(bounds.getH());
	  }

	  public void remove(Bounds baseForm) {
		  if(elementList.isEmpty()) { return; }
		  for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == baseForm) {
		    		rowList.remove(i);
					columnList.remove(i);
		    		elementList.remove(i);
		    		elementDefaultWidth.remove(i);
					elementDefaultHeight.remove(i);
		    	}
		    }
	  }
	  
	  public void remove(int index) {
		  if(elementList.isEmpty()) { return; }
		  elementList.remove(index);
		  rowList.remove(index);
		  columnList.remove(index);
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
	  public void inTransforms() {
			super.inTransforms();
			if(transforming != null) { transforming.autoUpdate(); }	
	  }

	  
} 