### Question

There are *N* children standing in a line. Each child is assigned a rating value.

You are giving candies to these children subjected to the following requirements:

- Each child must have at least one candy.
- Children with a higher rating get more candies than their neighbors.

What is the minimum candies you must give?

**Example 1:**

```
Input: [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
```

**Example 2:**

```
Input: [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
             The third child gets 1 candy because it satisfies the above two conditions.
```

### Solution

首先，本题的基本思想是，只有一个位置的值比其相邻的两个值（任意一个）大，才会受到其他位置的值的影响，比如`[1,3,1]`这个数组，只有 3 会受到相邻值的影响，会随着相邻值的糖果的数量增加而增加，反过来，不管 3 的糖果数量增加到多少，1 的糖果数量都不会改变，最少是 1，只需要保持这个糖果数就可以了，于是，按照最少的糖果分配，如果直接遍历数组 rating，便有以下结论：

1. rating[0] 的糖果数量初始为 1
2. 如果`rating[1] > rating[0]`，那么rating[1] 的初始糖果需要比rating[0]的糖果数量大 1，并且一旦遍历到rating[1] 之后，rating[0]的糖果数量就可以确定是 1 了，因为它不比相邻的值大，所以不会受到相邻值的糖果数量的影响
3. 如果`rating[1] < rating[0]`，那么由于每一个位置的糖果数量最少是 1，所以 rating[1] 的糖果初始值至少为 1，那么为了满足条件，需要把 rating[0] 的糖果数量增加（假如 rating[0] 前面还有 rating[-1] ，那么如果`rating[-1] > rating[0]`，还需要再判断 rating[-1] 的糖果数量是否满足大于 rating[0]，以此往前类推）
4. 如果`rating[1] == rating[0]`，根据题目条件，rating[1] 的初始糖果最少可以设置为 1

所以，根据以上逻辑，有如下代码：

```java
public int candy(int[] ratings) {
    int length = ratings.length;
    int total = 0;
    int cur = 0;
    int level = 0;
    int start = -1;
    int last = Integer.MIN_VALUE;
    int lastLevel = 0;
    while (cur < length) {
        int c = ratings[cur];
        if (c > last) {
            start = cur;
            level ++;
            total += level;
            lastLevel = level;
        } else {
            if (c == last) {
                start = cur;
                lastLevel = 1;
                total += 1;
            } else {
                int dif = cur - start;
                if (lastLevel <= dif) {
                    lastLevel ++;
                    total ++;
                }
                total += dif;
            }
            level = 1;
        }
        last = c;
        cur++;
    }
    return total;
}
```

total 为所需糖果总数，cur 为当前遍历到的位置，level 表示当前位置的糖果数，start 表示 [start,cur] 这段数组是一个不增子数组，也就是说 [start,cur] 中的任何一个位置的糖果数发生改变，都需要保证 rating[start] 的糖果数不小于后面所有的糖果数，rating[start+1] 有不小于后面的所有糖果数...... lastLevel 记录的就是 rating[start] 的糖果数，last 记录上一个位置的值。