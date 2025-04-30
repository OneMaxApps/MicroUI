package microUI.container.layout;

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PApplet.min;

import java.util.ArrayList;

import microUI.component.Button;
import microUI.component.CircleSeekBar;
import microUI.core.BaseForm;
import microUI.util.Text;
import processing.core.PApplet;

public class GridLayout extends Layout {
	  public final Transforming transforming;
	  private boolean fillTheGrid;
	  private int rows,columns;
	  private ArrayList<Integer> elementRowList,elementColumnList;
	  private ArrayList<Float> elementDefaultWidth,elementDefaultHeight;
	  
	  
	  public GridLayout(PApplet app) {
		  this(app,1);
	  }
	  
	  public GridLayout(PApplet app, int cells) {
		  this(app,0,0,app.width,app.height,cells,cells);
	  }
	  
	  public GridLayout(PApplet app, int rows, int columns) {
		  this(app,0,0,app.width,app.height,rows,columns);
	  }
	  
	  public GridLayout(PApplet app, float x, float y, float w, float h) { this(app,x,y,w,h,3,3); }
	  
	  public GridLayout(PApplet app, float x, float y, float w, float h, int rows, int columns) {
	    super(app,x,y,w,h);
	    setGrid(rows,columns);
	    setElementsResizable(true);
	    
	    elementRowList = new ArrayList<Integer>();
	    elementColumnList = new ArrayList<Integer>();
	    elementDefaultWidth = new ArrayList<Float>();
	    elementDefaultHeight = new ArrayList<Float>();
	    transforming = new Transforming() {
	    	@Override
	    	public void updateForce() {
				  
				  for(int i = 0; i < elementList.size(); i++) {
				  BaseForm baseForm = elementList.get(i);
				  
				  if(elementRowList.get(i) < 0 || elementRowList.get(i) > getRows()-1 || elementColumnList.get(i) < 0 || elementColumnList.get(i) > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
				    
				  if(baseForm instanceof Layout) {
				    	Layout l = ((Layout) baseForm);
					  	l.setPosition(
					    		map(elementRowList.get(i),0,GridLayout.this.rows,getX(),getX()+getW())+((getW()/getRows())/2)-l.getW()/2-l.margin.getLeft(),
					    		map(elementColumnList.get(i),0,GridLayout.this.columns,getY(),getY()+getH())+((getH()/getColumns())/2)-l.getH()/2-l.margin.getUp()
					    );
				    } else {
				    	baseForm.setPosition(
					    		map(elementRowList.get(i),0,GridLayout.this.rows,getX(),getX()+getW())+((getW()/getRows())/2)-baseForm.getW()/2,
					    		map(elementColumnList.get(i),0,GridLayout.this.columns,getY(),getY()+getH())+((getH()/getColumns())/2)-baseForm.getH()/2
					    );
				    }
				    if(baseForm instanceof CircleSeekBar) {
					    if(isElementsResizable()) {
					      if(isFillTheGrid()) {
					    	baseForm.setSize(getW()/getRows(),getH()/getColumns());
					      } else {
					    	  baseForm.setSize(min(getW()/getRows(),elementDefaultWidth.get(i)),min(getH()/getColumns(),elementDefaultHeight.get(i)));
					      }
					    }
				    } else {
				    	
					    if(isElementsResizable()) {
					      if(isFillTheGrid()) {
					        baseForm.setSize(getW()/getRows(),getH()/getColumns());
					      } else {
					    	baseForm.setSize(min(elementDefaultWidth.get(i),getW()/getRows()), min(elementDefaultHeight.get(i),getH()/getColumns()));
					    }
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
	  
	  public GridLayout add(BaseForm baseForm, int row, int column) {
		  if(row < 0 || row > getRows()-1 || column < 0 || column > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		    
		  baseForm.setPosition(
		    		map(row,0,this.rows,getX(),getX()+getW())+((getW()/getRows())/2)-baseForm.getW()/2,
		    		map(column,0,this.columns,getY(),getY()+getH())+((getH()/getColumns())/2)-baseForm.getH()/2
		  );
		    
		    
		  if(baseForm instanceof CircleSeekBar) {
			    if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			    	baseForm.setSize(getW()/getRows(),getH()/getColumns());
			      }
			    }
		    } else {
		 
			    if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			        baseForm.setSize(getW()/getRows(),getH()/getColumns());
			      } else {
			        if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setW(getW()/getRows()); }
			        if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setH(getH()/getColumns()); }
			      }
			    }
		    }
		    
	    
		if(baseForm instanceof Button) {
			((Button) baseForm).text.setTextSize(baseForm.getH()/3);
		}
	    
		checkObject(baseForm,row,column);
		
		transforming.updateForce();
		
		
		return this;
	  }
	  
	  public GridLayout add(String txt, int row, int column) {
		  Text baseForm = new Text(app,txt,0,0,0,0);
		  if(row < 0 || row > getRows()-1 || column < 0 || column > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
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
	  
	  private void checkObject(BaseForm baseForm, int row, int column) {
		    for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == baseForm) {
		    		return;
		    	}
		    }
		    	elementList.add(baseForm);
		    	elementRowList.add(row);
				elementColumnList.add(column);
				elementDefaultWidth.add(baseForm.getW());
				elementDefaultHeight.add(baseForm.getH());
	  }

	  
	  public void remove(BaseForm baseForm) {
		  if(elementList.isEmpty()) { return; }
		  for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == baseForm) {
		    		elementRowList.remove(i);
					elementColumnList.remove(i);
		    		elementList.remove(i);
		    		elementDefaultWidth.remove(i);
					elementDefaultHeight.remove(i);
		    	}
		    }
	  }
	  
	  public void remove(int index) {
		  if(elementList.isEmpty()) { return; }
		  elementList.remove(index);
		  elementRowList.remove(index);
		  elementColumnList.remove(index);
		  elementDefaultWidth.remove(index);
		  elementDefaultHeight.remove(index);
	  }
	  
	  public ArrayList<BaseForm> getElementList() { return elementList; }
	  
	  public boolean isFillTheGrid() {
			return fillTheGrid;
	  }

	  public void setFillTheGrid(boolean f) {
			fillTheGrid = f;
	  }

	  	@Override
		public void setX(float x) {
			super.setX(x);
			if(transforming != null) {
				transforming.autoUpdate();
			}
		}
	
		@Override
		public void setY(float y) {
			super.setY(y);
			if(transforming != null) {
				transforming.autoUpdate();
			}
		}
	
		@Override
		public void setW(float w) {
			super.setW(w);
			if(transforming != null) {
				transforming.autoUpdate();
			}
		}
	
		@Override
		public void setH(float h) {
			super.setH(h);
			if(transforming != null) {
				transforming.autoUpdate();
			}
		}

	  
} 