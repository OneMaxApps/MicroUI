package microUI;

import java.util.ArrayList;
import java.util.List;

import microUI.utils.Rectangle;
import processing.core.PApplet;

public final class Editor extends Rectangle {
	private final static int COPY = 0x03, PASTE = 0x16, CUT = 0x18;
	private List<EditText> linesList;
	private int linesCount;
	private String buffer;

	public Editor(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		linesList = new ArrayList<EditText>();
		linesCount = 10;
		buffer = "";
		setBasicFX(false);
		fill.set(255);
		
		for(int i = 0; i < linesCount; i++) {
			linesList.add(new EditText(app,x,y+(h/linesCount)*i,w,h/linesCount));
		}
	}

	public Editor(PApplet app) {
		this(app,app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}

	@Override
	public final void draw() {
		super.draw();
		for(int i = linesList.size()-1; i >= 0; i--) {
			EditText line = linesList.get(i);
			line.draw();
		}
	}

	public final void keyPressed() {
		for(int i = linesList.size()-1; i >= 0; i--) {
			EditText line = linesList.get(i);
			line.keyPressed();
			if(line.isFocused()) {
				if(app.key == COPY) {
					buffer = line.select.getBuffer();
				}
				
				if(app.key == PASTE) {
					
				}
				
				if(app.key == CUT) {
					
				}
			} else {
				line.select.setBuffer(buffer);
			}
			
			boolean correctSymbol = Character.isLetter(app.key) || Character.isDigit(app.key) || Character.isWhitespace(app.key);
			if(correctSymbol) {
				autoDownFocus(i,line);
			}
		}
	}
	
	
	private final void autoDownFocus(int i, EditText line) {
		if(i != linesList.size()-1) {
			if(line.isFocused()) {
				if(line.text.isFull()) {
					line.setFocused(false);
					linesList.get(i+1).setFocused(true);
				}
			}
		}
	}
}