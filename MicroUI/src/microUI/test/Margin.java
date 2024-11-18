package microUI.test;

import microUI.Button;
import microUI.Scroll;
import microUI.Slider;
import microUI.layouts.ColumnLayout;
import microUI.layouts.EdgeLayout;
import microUI.layouts.GridLayout;
import microUI.layouts.RowLayout;
import processing.core.PApplet;

// make margin working correct for all and special in GridLayout
public class Margin extends PApplet {
	GridLayout g;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Margin");
	}
	
	@Override
	public void settings() { size(400,400); }
	
	
	@Override
	public void setup() {
		g = new GridLayout(this,2);
		ColumnLayout c = new ColumnLayout(this);
		EdgeLayout e = new EdgeLayout(this);
		GridLayout g1 = new GridLayout(this,2);
		RowLayout r = new RowLayout(this);
		
		c.margin.set(10);
		e.margin.set(10);
		g1.margin.set(10);
		r.margin.set(10);
		
		
		c.add(new Button(this), .25f);
		c.add(new Slider(this), .25f);
		c.add(new Scroll(this), .25f);
		c.add(new Button(this), .25f);
		
		e.set(new Button(this));
		e.setDown(true);
		
		g1.add(new Button(this), 0,0);
		g1.add(new Slider(this), 1,0);
		g1.add(new Scroll(this), 0,1);
		g1.add(new Button(this), 1,1);
		
		r.add(new Button(this), .25f);
		r.add(new Slider(this), .25f);
		r.add(new Scroll(this), .25f);
		r.add(new Button(this), .25f);
		
		g.add(c, 0, 0);
		g.add(e, 1, 0);
		g.add(g1, 0, 1);
		g.add(r, 1, 1);
		
	}
	
	@Override
	public void draw() {
		background(232);
		g.draw();
		g.setSize(mouseX, mouseY);
	}
	
}
