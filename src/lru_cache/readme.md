### Question

Design a data structure that follows the constraints of a **[Least Recently Used (LRU) cache](https://en.wikipedia.org/wiki/Cache_replacement_policies#LRU)**.

Implement the `LRUCache` class:

- `LRUCache(int capacity)` Initialize the LRU cache with **positive** size `capacity`.
- `int get(int key)` Return the value of the `key` if the key exists, otherwise return `-1`.
- `void put(int key, int value)` Update the value of the `key` if the `key` exists. Otherwise, add the `key-value` pair to the cache. If the number of keys exceeds the `capacity` from this operation, **evict** the least recently used key.

**Follow up:**
Could you do `get` and `put` in `O(1)` time complexity?

 

**Example 1:**

```
Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
```

 

**Constraints:**

- `1 <= capacity <= 3000`
- `0 <= key <= 3000`
- `0 <= value <= 104`
- At most `3 * 104` calls will be made to `get` and `put`.

### Solution

首先，存储方式可以直接通过一个 HashMap，此处可以解决存取的时间复杂度 O(1)，但是要解决 lru 的调度，就还需要维护一个优先级列表，单从列表的角度，可以实现增删结点的时候实现 O(1) 复杂度（删除头节点，增加至尾结点），但是当需要定向移除的时候，对于一个列表而言就只能遍历了，此时的复杂度就会升高，所以需要能够在 O(1) 时间内定位到待移除结点位置，然后还要在 O(1) 时间内完成移除。

然而能够实现快速移除的，只有链表，而能够实现只找到其中一个就能完成移除的，就需要双向链表。那么现在解决问题所需的条件就清楚了，

1. 一个 HashMap 保存 key value
2. 一个双向链表
3. 一个 HashMap 保存 key node

接下来就是实现。首先，从双向链表的角度，一方面有自己的头尾结点，另一方面寄身于 HashMap 中；从 HashMap 的角度，一个存放链表结点，一个存放 value；从对应关系上看，一个 key 对应一个 value，也对应一个链表结点。

于是可以得到，value 也可以放到结点中，那么此时就只需要维护一个双向链表和一个 HashMap 即可。

```java
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
```

