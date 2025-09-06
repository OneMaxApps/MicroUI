//package microui.component;
//
//import static java.lang.Math.min;
//
//import microui.container.BorderContainer;
//import microui.core.base.Bounds;
//import microui.core.base.Component;
//import microui.core.interfaces.Scrollable;
//import processing.event.MouseEvent;
//
//public class Dial extends Component implements Scrollable {
//	private final BorderContainer container;
//	
//	public Dial(float x, float y, float width, float height) {
//		super(x, y, width, height);
//		setVisible(true);
//		setConstrainDimensionsEnabled(true);
//		setMaxSize(200);
//		setMinSize(20);
//		
//		container = new BorderContainer(x,y,width,height);
//		container.set(new Content());
//	}
//	
//	public Dial() {
//		this(ctx.width*.4f,ctx.height*.4f,ctx.width*.2f,ctx.height*.2f);
//	}
//
//	@Override
//	protected void update() {
//		container.draw();
//	}
//	
//	@Override
//	public void mouseWheel(MouseEvent e) {
//
//	}
//
//	@Override
//	protected void onChangeBounds() {
//		super.onChangeBounds();
//		if(container != null) {
//			container.setBoundsProperty(this);
//			container.getElement().setMaxSize(min(getWidth(),getHeight()),min(getWidth(),getHeight()));
//			container.getElement().setMinSize(this);
//		}
//		
//	}
//	
//	private final class Content extends Bounds {
//
//		public Content() {
//			super();
//			setVisible(true);
//			setConstrainDimensionsEnabled(true);
//			
//		}
//
//		@Override
//		protected void update() {
//			setEventListener(this);
//			
//			getMutableColor().apply();
//			ctx.ellipse(getX()+getWidth()/2,getY()+getHeight()/2,getWidth(),getHeight());
//		}
//	
//	}
//}