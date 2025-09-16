package microui.component;

import static microui.constants.ContainerMode.RESPECT_CONSTRAINTS;

import microui.core.base.Component;
import microui.core.base.Container;
import microui.core.effect.Hover;
import microui.core.style.Color;
import microui.layout.RowLayout;
import microui.layout.RowLayoutParams;

public final class LabeledCheckBox extends Component {
	private final Container container;
	private final CheckBox checkBox;
	private final TextView text;
	private final Hover hover;
	
	public LabeledCheckBox(float x, float y, float width, float height) {
		super(x, y, width, height);
		setVisible(true);
		setConstrainDimensionsEnabled(true);
		setEventListener(this);
		
		container = new Container(new RowLayout(), x, y, width, height);

		checkBox = new CheckBox();
		
		text = new TextView();
		
		hover = new Hover(container);
		hover.setColor(new Color(32,16));
		
		setMinSize(checkBox.getAbsoluteWidth(),checkBox.getAbsoluteHeight());
		setMaxSize(ctx.width,checkBox.getAbsoluteHeight());
		onClick(() -> {
			if(checkBox.isMouseOutside()) {
				checkBox.toggle();	
			}
		});
		
		container.setConstrainDimensionsEnabled(true);
		container.setMinSize(checkBox.getAbsoluteWidth(),checkBox.getAbsoluteHeight());
		container.setMaxSize(ctx.width,checkBox.getAbsoluteHeight());
		container.setContainerMode(RESPECT_CONSTRAINTS);
		
		checkBox.setPriority(1);
		
		text.setPaddingLeft(10);
		text.setConstrainDimensionsEnabled(false);
		text.setAutoResizeModeEnabled(false);
		text.setCenterMode(false);
		text.setTextSize(checkBox.getHeight());
		
		text.setText("i accept this condition");
		
		container.addComponent(checkBox, new RowLayoutParams(.2f));
		container.addComponent(text, new RowLayoutParams(.8f));
		
	}
	
	public LabeledCheckBox() {
		this(ctx.width * .3f, ctx.height * .45f, ctx.width * .4f,ctx.height * .1f);	
	}

	@Override
	protected void render() {
		container.draw();
		hover.draw();
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if(container != null) {
			container.setBoundsFrom(this);
		}
	}
	
	
}