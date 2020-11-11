package lru_cache;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache {
    private final int mCapacity;
    private final LinkedList<Integer> mKeyList;
    private final HashMap<Integer, Integer> mMap;

    public LRUCache(int capacity) {
        mCapacity = capacity;
        mKeyList = new LinkedList<>();
        mMap = new HashMap<>(capacity);
    }

    public int get(int key) {
        if (!mMap.containsKey(key)) return -1;
        refresh(key);
        return mMap.get(key);
    }

    public void put(int key, int value) {
        if (mKeyList.size() >= mCapacity && mMap.get(key) == null) {
            evict();
        }

        mMap.put(key, value);
        refresh(key);
    }

    private void evict() {
        int removeKey = mKeyList.pollFirst();
        mMap.remove(removeKey);
    }

    private void refresh(int key) {
        mKeyList.remove(new Integer(key));
        mKeyList.add(key);
    }
}
