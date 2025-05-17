package microUI.util;

import java.util.LinkedHashMap;
import java.util.Map;

import microUI.core.MicroUI;

public final class Metrics {
	private static final Map<String,Integer> metrics = new LinkedHashMap<String,Integer>();
	
	private Metrics() {}
	
	public static final void register(MicroUI microUI) {
		final String key = microUI.getClass().getSimpleName();
		metrics.put(key, metrics.getOrDefault(key, 0)+1);
	}
	
	public static final void printAll() {
		for(Map.Entry<String, Integer> entry : metrics.entrySet()) {
			System.out.println(entry.getKey()+" : "+ entry.getValue());
		}
	}
	
	public static final void print(final String className) {
		System.out.println(metrics.getOrDefault(className, 0));
	}
}
