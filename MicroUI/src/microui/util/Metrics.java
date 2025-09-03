package microui.util;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Map;

import microui.core.base.View;
import microui.event.Event;
import processing.core.PGraphics;

//Status: STABLE - Do not modify
//Last Reviewed: 27.08.2025
public final class Metrics {
	private static final Map<String,Integer> metrics = new LinkedHashMap<String,Integer>();
	
	private Metrics() {}
	
	public static void register(Event event) {
		final String key = requireNonNull(event,"event cannot be null").getClass().getSimpleName();
		metrics.put(key, metrics.getOrDefault(key, 0)+1);
	}
	
	public static void register(PGraphics pGraphics) {
		final String key = requireNonNull(pGraphics,"pGraphics cannot be null").getClass().getSimpleName();
		metrics.put(key, metrics.getOrDefault(key, 0)+1);
	}
	
	public static void register(View view) {
		final String key = requireNonNull(view,"view cannot be null").getClass().getSimpleName();
		metrics.put(key, metrics.getOrDefault(key, 0)+1);
	}
	
	public static void register(String key) {
		metrics.put(requireNonNull(key,"key cannot be null"), metrics.getOrDefault(key, 0)+1);
	}
	
	public static void printAll() {
		if(metrics.isEmpty()) { return; }
		
		System.out.println("\n////////////////////");
		metrics.forEach((k, v) -> {
			System.out.println(k+" : "+v);
		});
		System.out.println("////////////////////\n");
	}
	
	public static void print(String className) {
		System.out.println(requireNonNull(className,"className cannot be null")+ " : " +metrics.getOrDefault(className, 0));
	}
	
	public static void clear() {
		metrics.clear();
	}
	
}
