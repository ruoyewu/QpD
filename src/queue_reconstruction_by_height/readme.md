### Question

Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers `(h, k)`, where `h` is the height of the person and `k` is the number of people in front of this person who have a height greater than or equal to `h`. Write an algorithm to reconstruct the queue.

**Note:**
The number of people is less than 1,100.

 

**Example**

```
Input:
[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

Output:
[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
```

### Solution

#### S1:穷举

按照题目里面指出的规则，最简单的，可以采取穷举的方式，一个一个判断是否可以：

```java
public static int[][] reconstructQueue(int[][] people) {
    if (people.length == 0) return people;
    LinkedList<int[]> left = new LinkedList<>();
    int[] min = null;
    for (int[] p : people) {
        if (p[1] == 0 && (min == null || min[0] > p[0])) {
            if (min != null) left.add(min);
            min = p;
        } else {
            left.add(p);
        }
    }
    people[0] = min;
    construct(people, 1, left);
    return people;
}
private static boolean construct(int[][] people, int pos, LinkedList<int[]> left) {
    if (pos == people.length) return true;
    for (int i = 0; i < left.size(); i++) {
        int[] p = left.get(i);
        int k = 0, h = p[0];
        for (int j = 0; j < pos; j++) {
            if (people[j][0] >= h) {
                k++;
            }
        }
        if (k == p[1]) {
            // 可选
            people[pos] = p;
            left.remove(i);
            if (construct(people, pos+1, left)) {
                return true;
            }
            left.add(i, p);
        }
    }
    return false;
}
```

但这种方法太过于浪费时间。

#### S2:排序

换种思考方式，题目里面说了，每个位置的 k 的值，等于在这个位置之前 h 比它大的位置的数量。如果要向一个队列中插入一个人，要判断的就是插入的位置。那么如果可以确定，当前队列中的所有人的 h 值都比待插入的这个人的 h 值大，那么这个人的 k 值就是他应该插入的位置。并且可以确定，如果插入的是一个 h 值小于当前队列中所有人的 h 值的人，那么无论他的位置在哪，都不会影响当前队列中所有人的 k 值，即不会破坏当前队列的正确性。

再看本题，我们只需要将这个队列按照 h 从高到低排序，然后依次插入到一个新的队列，这样就能保证新插入的人的 h 值始终小于（或等于）当前队列中所有人的 h 值，满足了上面的条件。

再有一个问题，如果两个人的 h 值相同的时候该如何排序？如一个队列`[[7,1],[7,0]]`，因为题目中已经指出了：每个人的 k 值，是在他的前面所有 h 值大于（或等于）他的 h 的人的个数，那么对于两个 h 值相同的人，他们的 k 值肯定是不同的，并且能够确定，k 值大的人应该排在后面。

所以有：

```java
public static int[][] reconstructQueue2(int[][] people) {
    Arrays.sort(people, new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
        }
    });
    List<int[]> result = new ArrayList<>();
    for (int[] p : people) {
        result.add(p[1], p);
    }
    return result.toArray(people);
}
```

这种方法相对于上面的方法来说，简单了很多。这种方法的要点就在于准确判断一个队列的正确性的影响因素，分析在队列中的每个人的 k 值的影响因素，利用题目给出的条件，比较轻松地判断出每个人的相对正确位置，当所有的人都插入到这个队列中的时候，由于相对位置还是正确的，那么此时每个人的位置就是最终正确位置。