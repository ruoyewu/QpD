package lru_cache;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache2 {
    private HashMap<Integer, Node> map;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        } else {
            removeNode(node);
            addNode(node);
            return node.value;
        }
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (map.size() >= capacity && node == null) {
            map.remove(head.key);
            removeNode(head);
        }

        if (node == null) {
            node = new Node(tail, null, key, value);
            addNode(node);
            map.put(key, node);
        } else {
            node.value = value;
            removeNode(node);
            addNode(node);
        }
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        node.next = null;
        node.prev = null;
    }

    private void addNode(Node node) {
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    private class Node {
        Node prev;
        Node next;
        int key;
        int value;

        Node(Node prev, Node next, int key, int value) {
            this.prev = prev;
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }
}
