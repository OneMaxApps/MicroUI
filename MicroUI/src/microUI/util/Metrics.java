package microUI.util;

public  class Metrics {
	private static boolean absolute;
	
	private Metrics() {}
	
	public static final boolean isAbsolute() {
		return absolute;
	}

	public static final void setAbsolute(boolean absolute) {
		Metrics.absolute = absolute;
	}

	public final static class Component {
		private static int buttons,
						   checkBoxes,
						   dials,
						   editTexts,
						   menuButtons,
						   scrolls,
						   sliders,
						   textFields,
						   textViews;
		
		private Component() {}
		
		public static void registerButton() {
			buttons++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 1;
			
			Component.textViews-=ADJUSTMENT;
			Core.views-=ADJUSTMENT;
			Core.components-=ADJUSTMENT;
			Core.bounds-=ADJUSTMENT;
			
		}
		public static int getButtons() { return buttons; }
		
		public static void registerCheckBox() {
			checkBoxes++;
		}
		public static int getCheckBoxes() { return checkBoxes; }
		
		public static void registerDial() {
			dials++;
			
			if(absolute) { return; }
			final int ADJUSTMENT_FOR_VIEWS = 3;
			final int ADJUSTMENT_FOR_BOUNDS = 3;
			final int DEC_ADJUSTMENT = 1;
			
			Core.views-=ADJUSTMENT_FOR_VIEWS;
			Core.components-=DEC_ADJUSTMENT;
			Core.bounds-=ADJUSTMENT_FOR_BOUNDS;
			Component.textViews-=DEC_ADJUSTMENT;
		}
		
		public static int getDials() { return dials; }
		
		public static void registerEditText() {
			editTexts++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 2;
			
			Core.views-=ADJUSTMENT;
			Core.components-=ADJUSTMENT;
			Core.bounds-=ADJUSTMENT;
			Component.buttons-=ADJUSTMENT;
			Component.scrolls-=ADJUSTMENT;
			
		}
		public static int getEditTexts() { return editTexts; }
		
		public static void registerMenuButton() {
			buttons--;
			menuButtons++;
		}
		
		public static int getMenuButtons() { return menuButtons; }
		
		public static void registerScroll() {
			scrolls++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 1;
			
			Core.views-=ADJUSTMENT;
			Core.components-=ADJUSTMENT;
			Core.bounds-=ADJUSTMENT;
			Component.buttons-=ADJUSTMENT;
		}
		
		public static int getScrolls() { return scrolls; }
		
		public static void registerSlider() {
			sliders++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 1;
			
			Core.views-=ADJUSTMENT;
			Core.bounds-=ADJUSTMENT;
		}
		public static int getSliders() { return sliders; }
		
		public static void registerTextField() {
			textFields++;
		}
		public static int getTextFields() { return textFields; }
		
		public static void registerTextView() {
			textViews++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 1;
			
			Core.views-=ADJUSTMENT;
		}
		public static int getTextViews() { return textViews; }
		
		public static void printAll() {
			System.out.println("////////// ALL COMPONENTS //////////");
			System.out.println("Buttons: "+buttons);
			System.out.println("CheckBoxes: "+checkBoxes);
			System.out.println("Dials: "+dials);
			System.out.println("EditTexts: "+editTexts);
			System.out.println("MenuButtons: "+menuButtons);
			System.out.println("Scrolls: "+scrolls);
			System.out.println("Sliders: "+sliders);
			System.out.println("TextFields: "+textFields);
			System.out.println("TextViews: "+textViews);
			System.out.println("////////////////////////////////////\n");
		}
	}

	public final static class Container {
		
		private Container() {}
		private static int containerManagers,
						   
						   layouts,
						   columnLayouts,
						   rowLayouts,
						   gridLayouts,
						   edgeLayouts,
						   linearLayouts;
		
		public static void registerLayout() {
			layouts++;
		}
		public static int getLayouts() { return layouts; }
		
		public static void registerColumnLayout() {
			columnLayouts++;
		}
		public static int getColumnLayouts() { return columnLayouts; }
		
		public static void registerRowLayout() {
			rowLayouts++;
		}
		public static int getRowLayouts() { return rowLayouts; }
		
		public static void registerGridLayout() {
			gridLayouts++;
		}
		public static int getGridLayouts() { return gridLayouts; }
		
		public static void registerEdgeLayout() {
			edgeLayouts++;
		}
		public static int getEdgeLayouts() { return edgeLayouts; }
		
		public static void registerLinearLayout() {
			linearLayouts++;
		}
		public static int getLinearLayouts() { return linearLayouts; }
		
		public static void registerContainerManager() {
			containerManagers++;
		}
		public static int getContainerManagers() { return containerManagers; }
		
		
		public static void printAll() {
			System.out.println("////////// ALL CONTAINERS //////////");
			
			System.out.println("ContainerManagers: "+containerManagers);
			
			System.out.println("  Layouts: "+layouts);
			System.out.println("    ColumnLayouts: "+columnLayouts);
			System.out.println("    RowLayouts: "+rowLayouts);
			System.out.println("    GridLayouts: "+gridLayouts);
			System.out.println("    EdgeLayouts: "+edgeLayouts);
			System.out.println("    LinearLayouts: "+linearLayouts);
			
			System.out.println("////////////////////////////////////\n");
		}
		
	}
	
	public final static class Core {
		
		private Core() {}
		private static int views,
						   components,
						   bounds,
						   unsafeBounds,
						   images;
		
		public static void registerView() {
			views++;
			
		}
		public static int getViews() { return views; }
		
		public static void registerComponent() {
			components++;
		}
		public static int getComponents() { return components; }
		
		public static void registerBounds() {
			bounds++;
		}
		public static int getBounds() { return bounds; }
		
		public static void registerUnsafeBounds() {
			bounds--;
			unsafeBounds++;
		}
		
		public static int getUnsafeBounds() { return unsafeBounds; }
		
		public static void registerImage() {
			images++;
		}
		public static int getImages() { return images; }
		
		public static void printAll() {
			System.out.println("/////////////// CORE ///////////////");
			System.out.println("Views: "+views);
			System.out.println("Components: "+components);
			System.out.println("Bounds: "+bounds);
			System.out.println("UnsafeBounds: "+unsafeBounds);
			System.out.println("Images: "+images);
			System.out.println("////////////////////////////////////\n");
		}
	}
	
	public final static class Effect {
		
		private Effect() {}
		private static int hovers,
						   ripples,
						   shadows;
		
		public static void registerHover() {
			hovers++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 1;
			
			Core.views-=ADJUSTMENT;
		}
		
		public static int getHovers() { return hovers; }
		
		public static void registerRipples() {
			ripples++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 1;
			
			Core.views-=ADJUSTMENT;
		}
		
		public static int getRipples() { return ripples; }
		
		public static void registerShadow() {
			shadows++;
			
			if(absolute) { return; }
			
			final int ADJUSTMENT = 1;
			
			Core.views-=ADJUSTMENT;
		}
		
		public static int getShadows() { return shadows; }
		
		public static void printAll() {
			System.out.println("//////////// ALL EFFECTS ///////////");
			System.out.println("Hovers: "+hovers);
			System.out.println("Ripples: "+ripples);
			System.out.println("Shadows: "+shadows);
			System.out.println("////////////////////////////////////\n");
		}
	}
	
	public final static class Util {
		
		private Util() {}
		
		private static int colors,strokes,values;
		
		public static void registerColor() {
			colors++;
		}
		
		public static void registerStroke() {
			strokes++;
		}
		
		public static void registerValue() {
			values++;
		}

		public static final int getColors() {
			return colors;
		}

		public static final int getStrokes() {
			return strokes;
		}

		public static final int getValues() {
			return values;
		}
		
		public static void printAll() {
			System.out.println("///////////// ALL UTILS ////////////");
			System.out.println("Colors: "+colors);
			System.out.println("Strokes: "+strokes);
			System.out.println("Values: "+values);
			System.out.println("////////////////////////////////////\n");
		}
	}

	
	public static void printAll() {
		System.out.println("////////// ALL METRICS /////////////\n");
		Component.printAll();
		Container.printAll();
		Core.printAll();
		Effect.printAll();
		Util.printAll();
		System.out.println("////////////////////////////////////\n");
	}
	
	
	
}
