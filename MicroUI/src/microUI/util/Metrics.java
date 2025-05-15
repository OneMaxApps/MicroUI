package microUI.util;

public  class Metrics {
	private static int views,
					   bounds,
					   unsafeBounds,
					   containerManagers,
					   hovers,
					   ripples,
					   shadows,
					   images,
					   
					   components,
						   buttons,
						   checkBoxes,
						   dials,
						   editTexts,
						   menuButtons,
						   scrolls,
						   sliders,
						   textFields,
						   textViews,
						   
					   layouts,
						   columnLayouts,
						   rowLayouts,
						   gridLayouts,
						   edgeLayouts,
						   linearLayouts;
	
	private Metrics() {}
	
	public static void registerView() { views++; }
	public static int getViews() { return views; }
	
	public static void registerBounds() { bounds++; }
	public static int getBounds() { return bounds; }
	
	public static void registerUnsafeBounds() {
		bounds--;
		unsafeBounds++;
	}
	public static int getUnsafeBounds() { return unsafeBounds; }
	
	public static void registerContainerManager() { containerManagers++; }
	public static int getContainerManagers() { return containerManagers; }
	
	public static void registerHover() {
		views--;
		hovers++;
	}
	
	public static int getHovers() { return hovers; }
	
	public static void registerRipples() {
		views--;
		ripples++;
	}
	
	public static int getRipples() { return ripples; }
	
	public static void registerShadow() {
		views--;
		shadows++;
	}
	
	public static int getShadows() { return shadows; }
	
	public static void registerImage() { images++; }
	public static int getImages() { return images; }
	
	
	
	public static void registerComponent() { components++; }
	public static int getComponents() { return components; }
	
	public static void registerButton() { buttons++; }
	public static int getButtons() { return buttons; }
	
	public static void registerCheckBox() { checkBoxes++; }
	public static int getCheckBoxes() { return checkBoxes; }
	
	public static void registerDial() { dials++; }
	public static int getDials() { return dials; }
	
	public static void registerEditText() { editTexts++; }
	public static int getEditTexts() { return editTexts; }
	
	public static void registerMenuButton() {
		buttons--;
		menuButtons++;
	}
	
	public static int getMenuButtons() { return menuButtons; }
	
	public static void registerScroll() { scrolls++; }
	public static int getScrolls() { return scrolls; }
	
	public static void registerSlider() { sliders++; }
	public static int getSliders() { return sliders; }
	
	public static void registerTextField() { textFields++; }
	public static int getTextFields() { return textFields; }
	
	public static void registerTextView() {
		views--;
		textViews++;
	}
	public static int getTextViews() { return textViews; }
	
	
	public static void registerLayout() { layouts++; }
	public static int getLayouts() { return layouts; }
	
	public static void registerColumnLayout() { columnLayouts++; }
	public static int getColumnLayouts() { return columnLayouts; }
	
	public static void registerRowLayout() { rowLayouts++; }
	public static int getRowLayouts() { return rowLayouts; }
	
	public static void registerGridLayout() { gridLayouts++; }
	public static int getGridLayouts() { return gridLayouts; }
	
	public static void registerEdgeLayout() { edgeLayouts++; }
	public static int getEdgeLayouts() { return edgeLayouts; }
	
	public static void registerLinearLayout() { linearLayouts++; }
	public static int getLinearLayouts() { return linearLayouts; }
	
	
	public static void printAll() {
		System.out.println("////////// ALL METRICS //////////");
		System.out.println("  Views: "+views);
		System.out.println("  Bounds: "+bounds);
		System.out.println("  UnsafeBounds: "+unsafeBounds);
		System.out.println("  ContainerManagers: "+containerManagers);
		System.out.println("  Hovers: "+hovers);
		System.out.println("  Ripples: "+ripples);
		System.out.println("  Shadows: "+shadows);
		System.out.println("  Images: "+images);
		
		printAllComponents();
		printAllContainers();
	}
	
	public static void printAllComponents() {
		System.out.println("////////// ALL COMPONENTS //////////");
		System.out.println("Components: "+components);
		System.out.println("  Buttons: "+buttons);
		System.out.println("  CheckBoxes: "+checkBoxes);
		System.out.println("  Dials: "+dials);
		System.out.println("  EditTexts: "+editTexts);
		System.out.println("  MenuButtons: "+menuButtons);
		System.out.println("  Scrolls: "+scrolls);
		System.out.println("  Sliders: "+sliders);
		System.out.println("  TextFields: "+textFields);
		System.out.println("  TextViews: "+textViews);
	}
	
	public static void printAllContainers() {
		System.out.println("////////// ALL CONTAINERS //////////");
		System.out.println("Layouts: "+layouts);
		System.out.println("  ColumnLayouts: "+columnLayouts);
		System.out.println("  RowLayouts: "+rowLayouts);
		System.out.println("  GridLayouts: "+gridLayouts);
		System.out.println("  EdgeLayouts: "+edgeLayouts);
		System.out.println("  LinearLayouts: "+linearLayouts);
	}

}
