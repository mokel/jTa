package fr.mokel.trade.gui2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

	private static Map<EventType, List<EventListener>> eventRegistar = new HashMap<EventType, List<EventListener>>();

	public enum EventType {
		IndicatorAdded
	}

	public static void addListener(EventType e, EventListener ae) {
		if (!eventRegistar.containsKey(e)) {
			eventRegistar.put(e, new ArrayList<EventListener>());
		}
		eventRegistar.get(e).add(ae);
	}

	public static void fireEvent(EventType et, Object... args) {
		List<EventListener> listeners = eventRegistar.get(et);
		if(listeners != null) {
			Event e = new Event(args);
			for (EventListener el : listeners) {
				el.eventOccured(e);
			}
		}
	}

	public static class Event {

		private Object[] args;

		public Event(Object... args) {
			this.args = args;
		}

		public Object[] getArgs() {
			return args;
		}
	}

	public static interface EventListener {

		public void eventOccured(Event e);
	}
}
