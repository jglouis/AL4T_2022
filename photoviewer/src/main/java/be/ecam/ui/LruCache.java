package be.ecam.ui;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LruCache<K, V> {
    private final Map<K, V> cache;

    public LruCache(int capacity) {
        // Initial capacity, load factor = 0.75f, accessOrder = true (LRU)
        Map<K, V> map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
        // Rendre l'accès thread-safe pour les contextes concurrents
        this.cache = Collections.synchronizedMap(map);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    public int size() {
        return cache.size();
    }
}
