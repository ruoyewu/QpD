### Question

Given a set of *non-overlapping* intervals, insert a new interval into the intervals (merge if necessary).

You may assume that the intervals were initially sorted according to their start times.

**Example 1:**

```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
```

**Example 2:**

```
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
```

**NOTE:** input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

### Solution

在一个数对数组中插入一个数对，并使其保持各数对之间没有交集（将有交集的数对合并）。本题的关键点就是合并，首先需要找出需要合并的数对有哪些。

因为数对之间是按照从小到大排序的，所有需要合并的数对必然是相连的一串，所以问题可以变更为，寻找需要合并的数对的位置，将需要合并的合并之后，再与不需要合并的放到一起，就构成了新的数对数组。

如何需要到需要合并的数对？这一段需要合并的数对，可以看作是原始数对数组中的一个子数组，需要确定这个子数组的起止位置。起止位置各该如何确定？根据题目描述可知，假如将位置为 i 的数对标识为 si 和 ei ，待插入数组为 start 和 end，那么有：

1.  从左到右，第一次满足 ei >= start 的位置 i 为起始位置
2.  从右到左，第一次满足 si > end 的位置 i 为结束位置

当明确了如何判断可合并子数组的位置之后，代码如下：

```java
public static int[][] insert(int[][] intervals, int[] newInterval) {
    if (intervals.length == 0) return new int[][]{newInterval};
    int from = 0, to = 0;
    int start = newInterval[0], end = newInterval[1];
    boolean sOk = false, eOk = false;
    int min = start, max = end;
    int pos = 0;
    while (pos < intervals.length) {
        int s = intervals[pos][0], e = intervals[pos][1];
        if (!eOk) {
            if (s > end) {
                eOk = true;
            } else {
                to = pos+1;
                max = Math.max(e, max);
            }
        }
        if (!sOk) {
            if (e >= start) {
                sOk = true;
                from = pos;
                min = Math.min(s, min);
            } else {
                from = pos+1;
            }
        }
        if (eOk) break;
        ++pos;
    }
    int newLength = intervals.length - (to - from) + 1;
    int[][] result = new int[newLength][2];
    for (int i = 0; i < newLength; i++) {
        if (i < from) {
            result[i] = intervals[i];
        } else if (i == from) {
            result[i] = new int[]{min, max};
        } else {
            result[i] = intervals[i + (to - from) - 1];
        }
    }
    return result;
}
```

在第一个 while 循环中，根据上述方法找到可合并子数组的起止位置，并记录合并之后这个数对的值，然后在 for 循环中，将原数组分为三部分：合并位置之前的部分，需要合并的部分，合并位置之后的部分，将三部分合并到同一个数组中就是最终的结果。