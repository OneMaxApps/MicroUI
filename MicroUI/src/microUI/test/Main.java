package microUI.test;

import microUI.Button;
import microUI.Slider;
import microUI.Spinner;
import microUI.layouts.ColumnLayout;
import microUI.layouts.GridLayout;
import microUI.layouts.RowLayout;
import microUI.utils.FX;
import microUI.utils.View;
import processing.core.PApplet;

public class Main extends PApplet {
	FX fx;
	Spinner spMaxHz,spWaveForm;
	Button btnPs,btnMs;
	Slider sl;
	
	ColumnLayout mainLayout;
	RowLayout control;
	Wave wave;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(360,720); }
	
	public void setup() {
		spMaxHz = new Spinner(this,"Max. Hz");
		spMaxHz.add("808","5000","20000");
		spMaxHz.setShowSelectedItem(true);
		
		spWaveForm = new Spinner(this,"Wave form");
		spWaveForm.add("Sin","Saw","Square");
		spWaveForm.setShowSelectedItem(true);
		
		btnPs = new Button(this,"+");
		btnMs = new Button(this,"-");
		sl = new Slider(this);
		
		wave = new Wave(this);
		
		control = new RowLayout(this).add(btnMs, .1f).add(sl, .8f).add(btnPs, .1f);
		control.initShadow();
		
		mainLayout = new ColumnLayout(this);
		mainLayout.margin.set(10,10,20,40);
		mainLayout.add(new RowLayout(this).add(spMaxHz, .5f).add(spWaveForm, .5f), .1f);
		mainLayout.add(new View(this) {
			@Override
			public void draw() {
				if(sl.event.moved() || btnMs.event.pressed() || btnPs.event.pressed()) {
				fill(234);
				textSize(h/4);
				textAlign(CENTER,CENTER);
				text(String.valueOf((int) sl.getValue()) + " Hz",x,y,w,h);
				}}}, .3f);
		
		mainLayout.add(wave, .3f).add(new View(this), .1f);
		mainLayout.add(control, .1f);
		mainLayout.add(new GridLayout(this,3,1).setVisibleTotal(false), .2f);
		
		
		mainLayout.setVisible(false);
		
		fx = new FX(this);
		fx.spinners.before.text.setTextSize(spMaxHz.getH()/3);
		fx.add(spMaxHz,spWaveForm,sl);
		btnPs.shadowDestroy();
		btnMs.shadowDestroy();
		sl.shadowDestroy();
		
		sl.setMinMax(0,808);
		sl.setValue(0);
		sl.showText(false);

	}
	
	public void draw() {
		background(32);
		fx.init();
		
		mainLayout.draw();
		
		switch(spMaxHz.getSelect()) {
			case 0 : sl.setMax(808); break;
			case 1 : sl.setMax(5000); break;
			case 2 : sl.setMax(20000); break;
		}
		
		if(btnMs.event.clicked() || btnMs.event.longPressed(2)) {
			sl.appendValue(-1);
		} else {
			if(btnPs.event.clicked()  || btnPs.event.longPressed(2)) {
				sl.appendValue(1);
			}
		}
		
		if(mouseButton == RIGHT) {
			mainLayout.setSize(mouseX, mouseY);
		}
	}
	
	class Wave extends View {
		private int[] waveX;
		public Wave(PApplet applet) {
			super(applet);
		}
		
		@Override
		public void draw() {
			if(waveX == null) {
				waveX = new int[(int) getW()];
				for(int i = 0; i < waveX.length-1; i++) {
					waveX[i] = i;
				}
			}
			pushStyle();
			stroke(0);
			strokeWeight(1);
			fill(0,32);
			rect(x,y,w,h);
			
			beginShape();
			for(int i = 0; i < getW(); i++) {
				noFill();
				stroke(0,234,0);
				vertex(getX()+waveX[i],getY()+getH()/2+sin(frameCount*.1f+(frameCount+i)*sl.getValue()*.0002f)*getH()/2);
			}
			endShape();
			
			popStyle();
			
		}
	}
}