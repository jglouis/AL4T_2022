package be.ecam.ui;

import be.ecam.domain.Photo;
import javax.swing.ImageIcon;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThumbnailCache {
    
    private final int capacity;
    private final LinkedHashMap<Photo, ImageIcon> cache;

    public ThumbnailCache(int capacity) {
        this.capacity = capacity;
        
        this.cache = new LinkedHashMap<Photo, ImageIcon>(capacity, 0.75f, true) { 
            @Override
            protected boolean removeEldestEntry(Map.Entry<Photo, ImageIcon> eldest) {
                return size() > capacity;
            }
        };
    }

    public ImageIcon get(Photo photo) {
        return cache.get(photo);
    }

    public void put(Photo photo, ImageIcon icon) {
        cache.put(photo, icon);
    }
}