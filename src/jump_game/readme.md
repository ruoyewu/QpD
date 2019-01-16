### Question

Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

**Example 1:**

```
Input: [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
```

**Example 2:**

```
Input: [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum
             jump length is 0, which makes it impossible to reach the last index.
```

### Solution

#### S1:深度优先遍历

要判断从第一点是否能走到最后一点，只需要把所有能走的路都走一遍就好了，如果发现有某一条路确实能够到达最后一点，就可以确定有解。如果将所有可能走的路径组成一棵树，那么我们要找的就是能够到达最后一点的那条路径，所以可以采取深度优先遍历，如下：

```java
public static boolean canJump(int[] nums) {
    if (nums.length == 1) {
        return true;
    }
    return jump(nums, 0);
}
public static boolean jump(int[] nums, int i) {
    if (nums[i] == 0) {
        return false;
    }
    if (nums[i] != 0) {
        for (int j = 1; j <= nums[i]; j++) {
            if (i + j < nums.length-1) {
                if (jump(nums, i+j)) {
                    return true;
                }
            } else if (i + j >= nums.length-1) {
                return true;
            }
        }
    }
    return false;
}
```

但是这种方法实在是太耗时间了，很容易就会运行超时。

#### S2:正向推进

那么换种方法，是否可以经过一次遍历，找到所有的可达点，那么如果最后一个点是可达点，问题就解决了。那么如何找到可达点，经过分析有如下的递推关系：

1.  第一个点肯定是可达点
2.  对于非第一个点，如果它之前的某个可达点能够到达这个点，那么这个点也是可达点，否则为不可达点
3.  判断最后一个点是否可达

如下：

```java
public static boolean canJump2(int[] nums) {
    boolean[] reach = new boolean[nums.length];
    Arrays.fill(reach, false);
    reach[0] = true;
    for (int i = 1; i < nums.length; i++) {
        for (int j = i-1; j >= 0; j--) {
            if (reach[j] && j + nums[j] >= i) {
                reach[i] = true;
                break;
            }
        }
    }
    return reach[nums.length-1];
}
```

这种方法的所需的时间从$n$到$n^2$不等，如对于数组`[10, 0, 0, 0, 0, 0, 0, 0]`而言，虽然每个位置都可达，但是要判断出这种结果的时间复杂度为$O(n^2)$。

#### S3:反向推进

我们可以选择从正向递推的方式判断是否可达，是否也可以从反向开始递推？对于最后一个点，如果我们找到了距离它最近的并能够到达它的一点，那是不是就意味着我们只需要找到从第一个点到这个点是否可达就可以了？这样一来，似乎问题的规模就缩小了，我们可以将初始的数组削去一段，如对于数组`[S0...S1...S2]`,如果`S1`到`S2`可达，那么可以将数组变为`[S0...S1]`，只要后者有解，前者也必有解，但是我们只有证明前者有解是后者有解的充分必要条件才能这么用，必要条件已经证明了，现证明充分条件：

已知条件数组`[S0...S1...S2]`有解且`S1`到`S2`可达，若假设`[S0...S1]`无解，则有`[S0...]`这段子数组都不能到达`S1`以及它后面的位置，即从子数组`[S0...]`到子数组`[...S1]`都是不可达的，也就意味着`S0`到`S2`不可达，显然与条件冲突，所以充分性也成立。

所以基于这种思想，可以一次遍历求解：

```java
public static boolean canJump3(int[] nums) {
    int last = nums.length-1;
    for (int i = last; i >= 0; i--) {
        if (nums[i] + i >= last) {
            last = i;
        }
    }
    return last == 0;
}
```

此时时间复杂度为$O(n)$，对于此类问题，应该是最优解了。