package microui.util;

import java.util.LinkedHashMap;
import java.util.Map;

import microui.MicroUI;
import processing.core.PGraphics;

public final class Metrics {
	private static final Map<String,Integer> metrics = new LinkedHashMap<String,Integer>();
	
	private Metrics() {}
	
	public static final void register(PGraphics pg) {
		final String key = pg.getClass().getSimpleName();
		metrics.put(key, metrics.getOrDefault(key, 0)+1);
	}
	
	public static final void register(MicroUI obj) {
		final String key = obj.getClass().getSimpleName();
		metrics.put(key, metrics.getOrDefault(key, 0)+1);
	}
	
	public static final void register(String key) {
		metrics.put(key, metrics.getOrDefault(key, 0)+1);
	}
	
	public static final void printAll() {
		if(metrics.isEmpty()) { return; }
		
		System.out.println("\n////////////////////");
		metrics.forEach((k, v) -> {
			System.out.println(k+" : "+v);
		});
		System.out.println("////////////////////\n");
	}
	
	public static final void print(final String className) {
		System.out.println(className+ " : " +metrics.getOrDefault(className, 0));
	}
	
	public static final void clear() {
		metrics.clear();
	}
}
