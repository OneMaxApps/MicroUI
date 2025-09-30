package microui.component;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

import java.util.ArrayList;
import java.util.List;

import microui.core.GraphicsBuffer;
import microui.core.base.Component;
import microui.core.base.SpatialView;
import microui.core.base.View;
import microui.core.style.Color;
import processing.core.PGraphics;

public final class TextArea extends Component {
	
	private final Items items;
	
	public TextArea() {
		super();
		setMinMaxSize(4, 4, ctx.width/2, ctx.height/2);
		setSize(getMaxWidth(),getMaxHeight());
		setPosition(ctx.width/2-getMaxWidth()/2,ctx.height/2-getMaxHeight()/2);
		
		items = new Items();
	}

	@Override
	protected void render() {
		getMutableBackgroundColor().apply();
		ctx.rect(getPadX(), getPadY(), getPadWidth(), getPadHeight());
		items.draw();
	}
	
	private final class Items extends View {
		private static final int DEFAULT_TEXT_SIZE = 12;
		private final GraphicsBuffer graphics;
		private final Color textColor;
		private int textSize;
		private final List<Item> itemList;
		
		public Items() {
			super();
			setVisible(true);
			graphics = new GraphicsBuffer();
			textColor = new Color(0);
			textSize = DEFAULT_TEXT_SIZE;
			itemList = new ArrayList<Item>();
			addItem("line of text");
		}

		public void addItem(String text) {
			if(text == null) {
				throw new NullPointerException("the text cannot be null");
			}
			
			itemList.add(new Item(text));
			
			recalculateDefaultItemsBounds();
		}
		
		@Override
		protected void render() {
			for(int i = 0; i < itemList.size(); i++) {
				itemList.get(i).draw();
			}
		}
		
		private void recalculateDefaultItemsBounds() {
			float itemsTotalHeight = 0;
			for(int i = 0; i < itemList.size(); i++) {
				final Item item = itemList.get(i);
				itemsTotalHeight += item.getHeight();
				item.setWidth(getWidth());
				item.setHeight(textSize);
				item.setX(getX());
				item.setY(itemsTotalHeight);
				
			}
			
		}
		
		private final class Item extends SpatialView {
			private String text;
			
			public Item(String text) {
				super();
				setVisible(true);
				
				if(text == null) {
					throw new NullPointerException("the text cannot be null");
				}
				
				this.text = text;
			}

			@Override
			protected void render() {
				PGraphics g = graphics.getGraphics();
				
				g.beginDraw();
				textColor.apply(g);
				g.textSize(textSize);
				g.textAlign(LEFT,CENTER);
				g.text(text,getX(),getY(),getWidth(),getHeight());
				g.endDraw();
				
				graphics.draw();
			}
			
		}
	}
}