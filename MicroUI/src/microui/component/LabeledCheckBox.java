package microui.component;

import static java.util.Objects.requireNonNull;
import static microui.component.CheckBox.DEFAULT_SIZE;

import microui.constants.ContainerMode;
import microui.core.base.Component;
import microui.core.base.Container;
import microui.core.effect.Hover;
import microui.core.style.Color;
import microui.layout.RowLayout;
import microui.layout.RowLayoutParams;
import processing.core.PFont;

public final class LabeledCheckBox extends Component {
	private final Container container;
	private final CheckBox checkBox;
	private final TextView textView;
	private final Hover hover;
	
	public LabeledCheckBox(float x, float y, float width, float height) {
		super(x, y, width, height);
		setMinSize(DEFAULT_SIZE);
		setMaxSize(ctx.width,DEFAULT_SIZE);
		
		checkBox = new CheckBox();
		checkBox.setPriority(1);
		checkBox.setMarginLeft(10);
		
		textView = new TextView();
		
		hover = new Hover(this);
		hover.setColor(new Color(32,16));
		
		onClick(() -> {
			if(checkBox.isMouseOutside()) {
				checkBox.toggle();	
			}
		});
		
		textView.setPadding(20,0);
		textView.setAutoResizeModeEnabled(false);
		textView.setCenterModeEnabled(false);
		textView.setTextSize(DEFAULT_SIZE);
		textView.setText("i accept this condition");
		textView.setConstrainDimensionsEnabled(true);
		textView.setMaxSize(ctx.width,DEFAULT_SIZE);
		
		container = new Container(new RowLayout(), x, y, width, height);
		container.setContainerMode(ContainerMode.RESPECT_CONSTRAINTS);
		container.addComponent(checkBox, new RowLayoutParams(.1f));
		container.addComponent(textView, new RowLayoutParams(.9f));
		
	}
	
	public LabeledCheckBox(String label) {
		this(ctx.width * .3f, ctx.height * .45f, ctx.width * .4f,ctx.height * .1f);
		setText(label);
	}
	
	public LabeledCheckBox() {
		this(ctx.width * .3f, ctx.height * .45f, ctx.width * .4f,ctx.height * .1f);	
	}

	@Override
	protected void render() {
		hover.draw();
		container.draw();
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if(container != null) {
			container.setBoundsFrom(this);
		}
	}
	
	public boolean isHoverEnabled() {
		return hover.isEnabled();
	}

	public void setHoverEnabled(boolean enabled) {
		hover.setEnabled(enabled);
	}
	
	public float getHoverSpeed() {
		return hover.getSpeed();
	}

	public void setHoverSpeed(float speed) {
		hover.setSpeed(speed);
	}
	
	public String getText() {
		return textView.getText();
	}

	public void setText(String text) {
		this.textView.setText(text);
	}

	public PFont getFont() {
		return textView.getFont();
	}

	public void setFont(PFont font) {
		textView.setFont(requireNonNull(font, "font cannot be null"));
	}

	public Color getTextColor() {
		return textView.getColor();
	}

	public void setTextColor(Color color) {
		textView.setColor(color);
	}

	public boolean isTextVisible() {
		return textView.isVisible();
	}

	public void setTextVisible(boolean isVisible) {
		textView.setVisible(isVisible);
	}
}