package microUI.util;

public final class Metrics {
	private static int countOfViews,
					   countOfBounds,
					   countOfContainerManagers,
					   countOfHovers,
					   countOfRipples,
					   countOfShadows,
					   countOfImages,
					   
					   countOfComponents,
						   countOfButtons,
						   countOfCheckBoxes,
						   countOfDials,
						   countOfEditTexts,
						   countOfMenuButtons,
						   countOfScrolls,
						   countOfSliders,
						   countOfTextFields,
						   countOfTextViews,
						   
					   countOfLayouts,
						   countOfColumnLayouts,
						   countOfRowLayouts,
						   countOfGridLayouts,
						   countOfEdgeLayouts,
						   countOfLinearLayouts;
	
	private Metrics() {}
	
	public static final void registerView() { countOfViews++; }
	public static final int getCountOfViews() { return countOfViews; }
	
	public static final void registerBounds() { countOfBounds++; }
	public static final int getCountOfBounds() { return countOfBounds; }
	
	public static final void registerContainerManager() { countOfContainerManagers++; }
	public static final int getContainerManagers() { return countOfContainerManagers; }
	
	public static final void registerHover() { countOfHovers++; }
	public static final int getHovers() { return countOfHovers; }
	
	public static final void registerRipples() { countOfRipples++; }
	public static final int getRipples() { return countOfRipples; }
	
	public static final void registerShadow() { countOfShadows++; }
	public static final int getShadows() { return countOfShadows; }
	
	public static final void registerImage() { countOfImages++; }
	public static final int getImages() { return countOfImages; }
	
	
	
	public static final void registerComponent() { countOfComponents++; }
	public static final int getComponents() { return countOfComponents; }
	
	public static final void registerButton() { countOfButtons++; }
	public static final int getButtons() { return countOfButtons; }
	
	public static final void registerCheckBox() { countOfCheckBoxes++; }
	public static final int getCheckBoxes() { return countOfCheckBoxes; }
	
	public static final void registerDial() { countOfDials++; }
	public static final int getDials() { return countOfDials; }
	
	public static final void registerEditText() { countOfEditTexts++; }
	public static final int getEditTexts() { return countOfEditTexts; }
	
	public static final void registerMenuButton() {
		countOfComponents--;
		countOfButtons--;
		countOfMenuButtons++;
	}
	public static final int getMenuButtons() { return countOfMenuButtons; }
	
	public static final void registerScroll() { countOfScrolls++; }
	public static final int getScrolls() { return countOfScrolls; }
	
	public static final void registerSlider() { countOfSliders++; }
	public static final int getSliders() { return countOfSliders; }
	
	public static final void registerTextField() { countOfTextFields++; }
	public static final int getTextFields() { return countOfTextFields; }
	
	public static final void registerTextView() { countOfTextViews++; }
	public static final int getTextViews() { return countOfTextViews; }
	
	
	public static final void registerLayout() { countOfLayouts++; }
	public static final int getLayouts() { return countOfLayouts; }
	
	public static final void registerColumnLayout() { countOfColumnLayouts++; }
	public static final int getColumnLayouts() { return countOfColumnLayouts; }
	
	public static final void registerRowLayout() { countOfRowLayouts++; }
	public static final int getRowLayouts() { return countOfRowLayouts; }
	
	public static final void registerGridLayout() { countOfGridLayouts++; }
	public static final int getGridLayouts() { return countOfGridLayouts; }
	
	public static final void registerEdgeLayout() { countOfEdgeLayouts++; }
	public static final int getEdgeLayouts() { return countOfEdgeLayouts; }
	
	public static final void registerLinearLayout() { countOfLinearLayouts++; }
	public static final int getLinearLayouts() { return countOfLinearLayouts; }
	
	
	public static final void printAll() {
		System.out.println("////////// ALL METRICS //////////");
		System.out.println("  Views: "+countOfViews);
		System.out.println("  Bounds: "+countOfBounds);
		System.out.println("  ContainerManagers: "+countOfContainerManagers);
		System.out.println("  Hovers: "+countOfHovers);
		System.out.println("  Ripples: "+countOfRipples);
		System.out.println("  Shadows: "+countOfShadows);
		System.out.println("  Images: "+countOfImages);
		
		printAllComponents();
		printAllContainers();
	}
	
	public static final void printAllComponents() {
		System.out.println("////////// ALL COMPONENTS //////////");
		System.out.println("Components: "+countOfComponents);
		System.out.println("  Buttons: "+countOfButtons);
		System.out.println("  CheckBoxes: "+countOfCheckBoxes);
		System.out.println("  Dials: "+countOfDials);
		System.out.println("  EditTexts: "+countOfEditTexts);
		System.out.println("  MenuButtons: "+countOfMenuButtons);
		System.out.println("  Scrolls: "+countOfScrolls);
		System.out.println("  Sliders: "+countOfSliders);
		System.out.println("  TextFields: "+countOfTextFields);
		System.out.println("  TextViews: "+countOfTextViews);
	}
	
	public static final void printAllContainers() {
		System.out.println("////////// ALL CONTAINERS //////////");
		System.out.println("Layouts: "+countOfLayouts);
		System.out.println("  ColumnLayouts: "+countOfColumnLayouts);
		System.out.println("  RowLayouts: "+countOfRowLayouts);
		System.out.println("  GridLayouts: "+countOfGridLayouts);
		System.out.println("  EdgeLayouts: "+countOfEdgeLayouts);
		System.out.println("  LinearLayouts: "+countOfLinearLayouts);
	}

}
