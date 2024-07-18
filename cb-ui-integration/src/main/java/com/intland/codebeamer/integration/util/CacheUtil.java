package com.intland.codebeamer.integration.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CacheUtil {

	
	@FunctionalInterface
	public interface Function<T, R> {

	    R apply(T t) throws Exception;
	
	}
	
	public static <K, V> LoadingCache<K, V> build(Function<K, V> function) {
		return CacheBuilder.newBuilder().build(from(function));
	}
	
	public static <K, V> CacheLoader<K, V> from(Function<K, V> function) {
		return new FunctionToCacheLoader<>(function);
	}

	private static final class FunctionToCacheLoader<K, V> extends CacheLoader<K, V> implements Serializable {
		
		private final Function<K, V> computingFunction;

		public FunctionToCacheLoader(Function<K, V> computingFunction) {
			this.computingFunction = checkNotNull(computingFunction);
		}

		@Override
		public V load(K key) throws Exception {
			return computingFunction.apply(checkNotNull(key));
		}

	}
	
}
