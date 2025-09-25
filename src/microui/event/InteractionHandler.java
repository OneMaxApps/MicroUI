package microui.event;

import java.util.ArrayList;
import java.util.EnumMap;

import microui.core.base.Component;

public final class InteractionHandler {
	private final EventDispatcher dispatcher;
	private Component component;

	public InteractionHandler(Component component) {
		super();

		dispatcher = new EventDispatcher();

		if (component == null) {
			throw new NullPointerException("component cannot be null");
		}

		this.component = component;
	}

	public void listen() {
		dispatcher.update();
	}

	public void addListener(EventType eventType, Listener listener) {
		dispatcher.addListenerSafe(eventType, listener);
	}

	private final class EventDispatcher {
		private final EnumMap<EventType, ArrayList<Listener>> listeners;

		public EventDispatcher() {
			super();
			listeners = new EnumMap<>(EventType.class);
		}

		public void update() {
			listeners.forEach((eventType, listenerList) -> {

				switch (eventType) {

				case PRESS:
					
					listenerList.forEach((listener) -> listener.action());
					
					break;

				case CLICK:
					break;
				case DOUBLE_CLICK:
					break;
				case DRAGGING:
					break;
				case DRAG_END:
					break;
				case DRAG_START:
					break;
				case ENTER:
					break;
				case ENTER_LONG:
					break;
				case LEAVE:
					break;
				case LEAVE_LONG:
					break;
				case LONG_PRESS:
					break;
				case PRESSED:
					break;
				case RELEASE:
					break;
				case RELEASED:
					break;
				default:
					break;
				}
			});
		}

		public void addListenerSafe(EventType eventType, Listener listener) {
			if (eventType == null) {
				throw new NullPointerException("eventType cannot be null");
			}

			if (listener == null) {
				throw new NullPointerException("listener cannot be null");
			}

			listeners.putIfAbsent(eventType, new ArrayList<Listener>());
			listeners.get(eventType).add(listener);
		}
		
		public void removeListenerSafe(EventType eventType, Listener listener) {
			if (eventType == null) {
				throw new NullPointerException("eventType cannot be null");
			}
			
			if (listener == null) {
				throw new NullPointerException("listener cannot be null");
			}
			
			if(listeners.get(eventType) == null || !listeners.get(eventType).contains(listener)) {
				throw new IllegalStateException("listener is not found");
			}
			
			listeners.get(eventType).remove(listener);
		}
	}

}