package microui.util;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Map;

//Status: STABLE - Do not modify
//Last Reviewed: 26.09.2025
public final class Metrics {
	private static final Map<String,Integer> metrics = new LinkedHashMap<String,Integer>();
	
	private Metrics() {}
	
	public static void register(Object object) {
		if(object == null) {
			throw new NullPointerException("object for registration in Metrics cannot be null");
		}
		metrics.put(object.getClass().getSimpleName(),metrics.getOrDefault(object.getClass().getSimpleName(), 0)+1);
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
