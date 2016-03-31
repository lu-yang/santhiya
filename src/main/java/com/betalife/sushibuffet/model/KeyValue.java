package com.betalife.sushibuffet.model;

import java.io.Serializable;

public class KeyValue<K, V> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private K key;
	private V value;

	public KeyValue() {
	}

	public KeyValue(final K key, final V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public K setKey(final K key) {
		if (key == this) {
			throw new IllegalArgumentException("DefaultKeyValue may not contain itself as a key.");
		}

		final K old = this.key;
		this.key = key;
		return old;
	}

	public V setValue(final V value) {
		if (value == this) {
			throw new IllegalArgumentException("DefaultKeyValue may not contain itself as a value.");
		}

		final V old = this.value;
		this.value = value;
		return old;
	}

}
