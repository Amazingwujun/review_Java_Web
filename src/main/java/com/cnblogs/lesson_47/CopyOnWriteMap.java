package com.cnblogs.lesson_47;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class CopyOnWriteMap<K, V> implements Map<K, V>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private volatile Map<K, V> map = new HashMap<>();

	private Object lock = new Object();

	public CopyOnWriteMap(HashMap<K, V> map) {
		this.map = map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		synchronized (lock) {
			//复制旧的Map
			Map<K, V> newMap = new HashMap<>(map);
			//向新Map中添加数据
			newMap.put(key, value);
			//让引用指向新的Map
			map = newMap;
			return value;
		}
	}

}
