package microUI.layouts;

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;

import java.util.ArrayList;

import microUI.Button;
import microUI.CircleSeekBar;
import microUI.Scroll;
import microUI.Slider;
import microUI.utils.BaseForm;
import microUI.utils.Text;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class GridLayout extends Layout {
	  private boolean fillTheGrid;
	  private int rows,columns;
	  private ArrayList<BaseForm> elementList;
	  private ArrayList<Integer> elementRowList,elementColumnList;
	  
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
	    elementList = new ArrayList<BaseForm>();
	    elementRowList = new ArrayList<Integer>();
	    elementColumnList = new ArrayList<Integer>();
	  }
	  
	  @Override
	  public void draw() {
	  
		applet.pushStyle();
		
		applet.pushStyle();
		if(!elementList.isEmpty()) {
		    for(int i = elementList.size()-1; i >= 0; i--) {
		    	updateTransforms(elementList.get(i),i);
		    	elementList.get(i).draw();
		    }
		}
	    applet.popStyle();
	    
	    if(isVisible()) {
		  super.draw();
		  applet.noFill();
		  applet.stroke(0);
	      for(float x = getX(); x < getX()+getW(); x += getW()/getRows()) {
	        for(float y = getY(); y < getY()+getH(); y += getH()/getColumns()) {
	          if(ceil(x) < getX()+getW() && ceil(y) < getY()+getH()) {
	        	  applet.rect(x,y,getW()/rows,getH()/columns);
	          }
	        }
	      }
	    }
	    
	    applet.popStyle();
	  }
	  
	  public void setRows(int rows) { this.rows = rows; }
	  public int getRows() { return rows; }
	  
	  public void setColumns(int columns) { this.columns = columns; }
	  public int getColumns() { return columns; }
	  
	  public void setGrid(int rows, int columns) {
	    setRows(PApplet.constrain(rows,1,rows));
	    setColumns(PApplet.constrain(columns,1,columns));
	  }
	  
	  public GridLayout add(BaseForm baseForm, int row, int column) {
		  if(row < 0 || row > getRows()-1 || column < 0 || column > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		    baseForm.setPosition(
		    		map(row,0,this.rows,x,x+w)+((getW()/getRows())/2)-baseForm.getW()/2,
		    		map(column,0,this.columns,y,y+h)+((getH()/getColumns())/2)-baseForm.getH()/2
		    );
		    
		    
		    if(baseForm instanceof CircleSeekBar) {
		    	// resize of UI element
			    if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			    	if(getRows() >= getColumns()) {
			    		baseForm.setSize(w/getRows(),w/getRows());
			    	} else {
			    		baseForm.setSize(h/getColumns(),h/getColumns());
			    	}
			        
			      } else {
			    	  if(getRows() >= getColumns()) {
			    		  if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setSize(getW()/getRows(),getW()/getRows()); }
			    		  if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setSize(getW()/getRows(),getW()/getRows()); }
			    	  } else {
			    		  if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setSize(getH()/getColumns(),getH()/getColumns()); }
			    		  if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setSize(getH()/getColumns(),getH()/getColumns()); }
			    	  }
			      }
			    }
		    } else {
		    	// resize of UI element
			    if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			        baseForm.setSize(w/getRows(),h/getColumns());
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
		return this;
	  }
	  
	  public GridLayout add(PApplet applet, String txt, int row, int column) {
		  Text baseForm = new Text(applet,txt,0,0,0,0);
		  if(row < 0 || row > getRows()-1 || column < 0 || column > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		    baseForm.setPosition(
		    		map(row,0,this.rows,x,x+w)+((getW()/getRows())/2)-baseForm.getW()/2,
		    		map(column,0,this.columns,y,y+h)+((getH()/getColumns())/2)-baseForm.getH()/2
		    );
		    
		    // resize of UI element
		    if(isElementsResizable()) {
		      if(isFillTheGrid()) {
		        baseForm.setSize(w/getRows(),h/getColumns());
		      } else {
		        if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setW(getW()/getRows()); }
		        if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setH(getH()/getColumns()); }
		      }
		    }
	    
		checkObject(baseForm,row,column);
		
		return this; 
	  }
	  
	  private void checkObject(BaseForm baseForm, int row, int column) {
		  boolean newObject = true;
		    for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == baseForm) {
		    		newObject = false;
		    	}
		    }
		    if(newObject) {
		    	elementList.add(baseForm);
		    	elementRowList.add(row);
				elementColumnList.add(column);
		    }
	  }
	  
	  public void updateTransforms(BaseForm baseForm, int index) {
		  if(elementRowList.get(index) < 0 || elementRowList.get(index) > getRows()-1 || elementColumnList.get(index) < 0 || elementColumnList.get(index) > getColumns()-1) { throw new IndexOutOfBoundsException("index out of bounds of grid"); }
		    baseForm.setPosition(
		    		map(elementRowList.get(index),0,this.rows,x,x+w)+((getW()/getRows())/2)-baseForm.getW()/2,
		    		map(elementColumnList.get(index),0,this.columns,y,y+h)+((getH()/getColumns())/2)-baseForm.getH()/2
		    );
		    
		    if(baseForm instanceof CircleSeekBar) {		    	
			    if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			    	if(getRows() >= getColumns()) {
			    		baseForm.setSize(w/getRows(),w/getRows());
			    	} else {
			    		baseForm.setSize(h/getColumns(),h/getColumns());
			    	}
			        
			      } else {
			    	  if(getRows() >= getColumns()) {
			    		  if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setSize(getW()/getRows(),getW()/getRows()); }
			    		  if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setSize(getW()/getRows(),getW()/getRows()); }
			    	  } else {
			    		  if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setSize(getH()/getColumns(),getH()/getColumns()); }
			    		  if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setSize(getH()/getColumns(),getH()/getColumns()); }
			    	  }
			      }
			    }
		    } else {
			    if(isElementsResizable()) {
			      if(isFillTheGrid()) {
			        baseForm.setSize(w/getRows(),h/getColumns());
			      } else {
			        if(baseForm.getW() > getW()/getRows() || baseForm.getW() < getW()/getRows()/2) { baseForm.setW(getW()/getRows()); }
			        if(baseForm.getH() > getH()/getColumns() || baseForm.getH() < getH()/getColumns()/2) { baseForm.setH(getH()/getColumns()); }
			      }
			    }
		    }
	  }
	  
	  public void remove(BaseForm baseForm) {
		  if(elementList.isEmpty()) { return; }
		  for(int i = 0; i < elementList.size(); i++) {
		    	if(elementList.get(i) == baseForm) {
		    		elementRowList.remove(i);
					elementColumnList.remove(i);
		    		elementList.remove(i);
		    	}
		    }
	  }
	  
	  public void remove(int index) {
		  if(elementList.isEmpty()) { return; }
		  if(index < 0 || index > elementList.size()-1) { return; }
		  elementList.remove(index);
		  elementRowList.remove(index);
		  elementColumnList.remove(index);
	  }
	  
	  public ArrayList<BaseForm> getElementList() { return elementList; }
	  
	  public void mouseWheelInit(MouseEvent e) {
		  for(int i = 0; i < elementList.size(); i++) {
			  if(elementList.get(i) instanceof Slider) {
				  ((Slider) elementList.get(i)).scrolling.init(e);
			  }
			  
			  if(elementList.get(i) instanceof Scroll) {
				  ((Scroll) elementList.get(i)).scrolling.init(e);
			  }

			  if(elementList.get(i) instanceof CircleSeekBar) {
				  ((CircleSeekBar) elementList.get(i)).scrolling.init(e);
			  }
			  
			  if(elementList.get(i) instanceof GridLayout) {
				  ((GridLayout) elementList.get(i)).mouseWheelInit(e);
			  }
		  }
	  }
	  
	  public boolean isFillTheGrid() {
			return fillTheGrid;
	  }

	  public void setFillTheGrid(boolean f) {
			fillTheGrid = f;
	  }
	  
	  public void setVisibleTotal(boolean visible) {
		  setVisible(visible);
		  for(BaseForm form : elementList) {
			  if(form instanceof Layout) {
				  ((Layout) (form)).setVisible(visible);
			  }
			  
			  
			  if(form instanceof GridLayout) {
				  for(BaseForm formInner : ((GridLayout) (form)).getElementList()) {
					  if(formInner instanceof GridLayout)
					  ((GridLayout) (formInner)).setVisible(visible);
				  }
			  }
			  
		  }
	  }
} 