### Question

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

-   push(x) -- Push element x onto stack.
-   pop() -- Removes the element on top of the stack.
-   top() -- Get the top element.
-   getMin() -- Retrieve the minimum element in the stack.

**Example:**

```
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.
```

### Solution

构建一个栈，使得能够在$O(1)$的时间内获得这个栈中的最小值，那么就需要额外的空间来保存这个最小值数据，如可以单独建立一个数组，保存从栈底到当前位置的最小值：

```java
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
```

利用 ArrayList 中的变长数组思想，通过扩充的方法解决变长问题，而不是直接使用 ArrayList 保存数据和最小值，能够减少一点不必要的消耗。