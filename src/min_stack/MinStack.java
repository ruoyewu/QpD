package min_stack;

/**
 * User: wuruoye
 * Date: 2019/1/29 10:04
 * Description:
 */
public class MinStack {
    private int capacity;
    private int size;
    private int[] data;
    private int[] min;

    public MinStack() {
        capacity = 4;
        size = 0;
        data = new int[capacity];
        min = new int[capacity];
    }

    public void push(int x) {
        if (size >= capacity) {
            expand();
        }
        int min = size > 0 ?this.min[size-1] : Integer.MAX_VALUE;
        data[size] = x;
        this.min[size++] = x > min ? min : x;
    }

    public void pop() {
        size--;
    }

    public int top() {
        return size > 0 ? data[size-1] : -1;
    }

    public int getMin() {
        return size > 0 ? min[size-1] : -1;
    }

    private void expand() {
        int newCapacity = capacity * 2;
        int[] newData = new int[newCapacity];
        int[] newMin = new int[newCapacity];
        System.arraycopy(data, 0, newData, 0, capacity);
        System.arraycopy(min, 0, newMin, 0, capacity);
        capacity = newCapacity;
        data = newData;
        min = newMin;
    }
}
