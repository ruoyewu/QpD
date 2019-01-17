### Question

Given a collection of intervals, merge all overlapping intervals.

**Example 1:**

```
Input: [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
```

**Example 2:**

```
Input: [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
```

### Solution

#### S1:合并法

将一系列有重叠的数字对合并成没有重叠的数字对，如果按照一一合并的方法，直接合并 n 个数组肯定是不好办的，可以是先合并前两个，得到的结果与第三个合并，直到合并到第 n 个，也就结束了。所以这种方法需要解决的问题是，如何判断两个数字对是否能够合并，它能够与前面的一个数字对合并还是能够与多个数字对合并，于是，每次判断一个数字对是否能够合并的时候，与已经存在的所有数字对比较并对可以合并的加以合并，为了这个查找效率稍微高一点，我们可以维护一个有序的合并后的序列，对于一个合并后的有序序列`[s1,s2,...]`，必然有`s1.start<=s1.end<s2.start<=s2.end....`，那么在查找一个数字对`s`是否可以和之前的某些数字对合并的时候，有如下几种情况：

1.  不可以合并，这种情况的条件是，`s1.end<s.start<=s.end<s2.start`，这时我们不仅能够判断`s`不能合并，而且也确定了`s`应该插入的位置，即`s1`之后。
2.  可以合并多个数字对，如果`s`能够与`s1`和`s2`合并的话，应该是`s.start<=s1.end, s2.start<=s.end`，如果`s1`和`s2`中间还有别的数字对，那么`s`必然也能与这些合并，我们只需要找到这些数字对的起始与结束的那两个就够了。
3.  可以合并单个数字对，如果`s`能与`s1`合并，那么应该有`s.start<=s1.start<=s.end, s.start<=s1.end<=s.end, s1.start<=s.start<=s.end<=s1.end`这么三种情况，这时合并它们两个就行了。

代码如下：

```java
public static List<Interval> merge(List<Interval> intervals) {
    if (intervals.size() <= 1) {
        return intervals;
    }
    List<Interval> result = new ArrayList<>();
    result.add(intervals.remove(0));
    for (int i = 0; i < intervals.size(); i++) {
        int start = -1, end = -1, pos = 0;
        Interval interval = intervals.get(i);
        for (int j = 0; j < result.size(); j++) {
            if (start == -1 && interval.start <= result.get(j).end) {
                start = j;
            }
            if (interval.start > result.get(j).end) {
                pos = j + 1;
            }
            if (interval.end >= result.get(j).start) {
                end = j;
            } else {
                break;
            }
        }
        if (start <= end && start >= 0) {
            Interval s = result.get(start);
            Interval e = result.get(end);
            s.start = Math.min(s.start, interval.start);
            s.end = Math.max(e.end, interval.end);
            for (int j = start+1; j <= end; j++) {
                result.remove(start+1);
            }
        } else {
            result.add(pos, interval);
        }
    }
    return result;
}
```

#### S2:排序法

现在给出两个数字对序列，一个可以合并，一个不可以合并，并将它们按照起始值的大小升序排序，有：

```
[[1,2],[3,4],[5,6]]
[[1,3],[2,4],[5,6]]
```

观察不可合并序列，我们可以发现，对于序列`[s1,s2,...,sn]`而言，必然有`s1.start<=s1.end<s2.start<=s2.end<...<sn.start`，即下一个数字对的起始值必然大于前一个数字对的结束值。而对于可合并的序列，如上第二列，因为有`3>2`，则`[1,3],[2,4]`必然可以合并，而又因为`4<5`，所以`[2,4],[5,6]`不能合并，基于这种思想，有如下一种计算方法：

```java
public static List<Interval> merge3(List<Interval> intervals) {
    if (intervals.size() <= 1) {
        return intervals;
    }
    Interval[] ia = new Interval[intervals.size()];
    intervals.toArray(ia);
    intervals.clear();
    Arrays.sort(ia, new Comparator<Interval>() {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    });
    Interval last = ia[0];
    intervals.add(last);
    for (int i = 1; i < ia.length; i++) {
        Interval interval = ia[i];
        if (interval.start <= last.end) {
            last.end = Math.max(interval.end, last.end);
        } else {
            intervals.add(interval);
            last = interval;
        }
    }
    return intervals;
}
```

这种方法的时间复杂度为$O(n\log n)$。

如果再换种思路，将一个不可合并的有序序列融合到一起，必有`s1.end<s2.start, s2.end<s3.start, ...`，那么对于一个合并后的序列而言，应该有多个序列合并之后变成同一个序列，如`[1,10], [2,3] `和`[1,3], [2,10]`，不管合并之前它的序列是什么样，如果它们合并之后是相同的，那就可以认定它们是一个**等价序列**，并且等价序列之间的转变不会影响最终的合并结果。现有一种等价变换：

将数字对的起始值与结束值分别排序，并按照从小到大的顺序依次组成新的数字对，这种序列的变化不会影响最终的合并结果。

于是就有了相对于上面，要对整个 Interval 数组进行排序的优化，如下：

```java
public static List<Interval> merge2(List<Interval> intervals) {
    int n = intervals.size();
    if (n <= 1) {
        return intervals;
    }
    int[] start = new int[n];
    int[] end = new int[n];
    int i = 0;
    for (Interval interval : intervals) {
        start[i] = interval.start;
        end[i++] = interval.end;
    }
    Arrays.sort(start);
    Arrays.sort(end);
    List<Interval> result = new ArrayList<>();
    int j = 0;
    int s = start[0];
    for (i = 1; i < n; i++, j++) {
        if (start[i] > end[j]) {
            result.add(new Interval(s, end[j]));
            s = start[i];
        }
    }
    result.add(new Interval(s, end[n-1]));
    return result;
}
```

这个合并的原理依旧是基于上面`s1.start<=s1.end<s2.start<=s2.end<...<sn.start`这样的方法合并的，时间复杂度也是$O(n\log n)$，不过可能是因为没有了对 Interval 对象的排序和读取操作，运行时间上相对上面一种方法是要少上不少的。