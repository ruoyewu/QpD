### Question

Given an array `nums` of *n* integers and an integer `target`, find three integers in `nums` such that the sum is closest to `target`. Return the sum of the three integers. You may assume that each input would have exactly one solution.

**Example:**

```
Given array nums = [-1, 2, 1, -4], and target = 1.

The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
```

### Solution

**一种解法，三步优化**

求一个数组中的某三个数，使其和最接近给定的数字，即差值的绝对值达到最小。遇到有关大小的数组问题，可以首先考虑的就是将数组排序，对于一个有序的数组，其和更好判断，但是更好判断的不是三个数字之和，而是两个数字之和：对于一个有序数组，求两个数字，使之和与某个给定的数字之差最小。判断这个再简单不过了，如果大了就把较大的数左移，如果小了就把较小的数右移，总会找到满足差最小的两个数字。

那么第一个问题，如何将三个数字之和转化为两个数字之和？先取出一个即可。即利用一个二重遍历，外围对先取的一个进行遍历，内围求出这么两个数即可。如下：

```java
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closest = Integer.MAX_VALUE, cur, min;
    for (int i = 0; i < nums.length - 2; i++) {
        int left = i+1, right = nums.length-1;
        while (left < right) {
            min = nums[left] + nums[right] + nums[i] - target;
            if (Math.abs(min) < Math.abs(closest)) closest = min;
            if (min > 0) {
                right--;
            } else {
                left++;
            }
        }
    }
    return closest + target;
}
```

将原数组排序好了之后，便是求解。那么对于作为解的三个数字，采用分步确定，对于一个有序数组，先假定出三个数里面最小的一个数，再求出两外两个数即可。

#### 优化1:过滤重复值

对于数组`[1,2,2,2,2,3,4,5]`而言，假如选择的最小的数是 2 ，那么剩余可选的为`[2,2,2,3,4,5]`或者是`[3,4,5]`，显然，当需要在这样两个数组中求出两个数字 left 和 right 使得`left + right + 2 = target`的时候，如果 left right 存在于数组`[3,4,5]`，那么它们也必然存在于`[2,2,2,3,4,5]`，只需要求出`[2,2,2,3,4,5]`中的解即可，从表现上看，就是去除了重复的数字 2 。此时代码为：

```java
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closest = Integer.MAX_VALUE, min;
    for (int i = 0; i < nums.length - 2; i++) {
        if (i == 0 || nums[i] != nums[i-1]) {
            int left = i+1, right = nums.length-1;
            while (left < right) {
                min = nums[left] + nums[right] + nums[i] - target;
                if (Math.abs(min) < Math.abs(closest)) closest = min;
                if (min > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
    }
    return closest + target;
}
```

#### 优化2:剪枝

上述的过程中，就假定的第一个值而言，进行了整个数组的遍历，这是否一定是必要的？本题意在求出三个数之和与目标值 target 差最小，而差是有一个最小限度的，那就是 0 。那么意味着，在这样一个遍历的过程中，如果已经能够确定找到了三个数，使之和与目标值的差为 0 ，后续的计算也就不是必要的了。

```java
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closest = Integer.MAX_VALUE, min;
    for (int i = 0; i < nums.length - 2; i++) {
        if (i == 0 || nums[i] != nums[i-1]) {
            int left = i+1, right = nums.length-1;
            while (left < right) {
                min = nums[left] + nums[right] + nums[i] - target;
                if (min == 0) return target;
                if (Math.abs(min) < Math.abs(closest)) closest = min;
                if (min > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
    }
    return closest + target;
}
```

#### 优化3:去除绝对值

在上面那份代码中，计算中存在着函数调用，这无疑是影响性能的，应该想一种方法不使用绝对值也能正确得到结果。需要使用绝对值函数的数据有两个：min 和 closest ，如果知道了它们的正负情况，就可以不使用绝对值函数了，而上面正好也有对 min 的判断，所以，显而易见的可以使用后面的对 min 的判断，去除对 min 的绝对值的计算：

```java
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closest = Integer.MAX_VALUE, min;
    for (int i = 0; i < nums.length - 2; i++) {
        if (i == 0 || nums[i] != nums[i-1]) {
            int left = i+1, right = nums.length-1;
            while (left < right) {
                min = nums[left] + nums[right] + nums[i] - target;
                if (min == 0) return target;
                if (min > 0) {
                    if (min < Math.abs(closest)) closest = min;
                    right--;
                } else {
                    if (-min < Math.abs(closest)) closest = min;
                    left++;
                }
            }
        }
    }
    return closest + target;
}
```

还需要去掉对 closest 的绝对值计算才行。如何能够确定 closest 的符号？从 closest 被赋值的地方找即可。事实上 closest 只被 min 赋值，而且，赋值语句都在对 min 的正负判断里面，这也就意味着，所有 closest 的值改变的地方，都可以得知此时 closest 的正负情况，那么，使用一个标志保存起来即可：

```java
public static int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closest = Integer.MAX_VALUE, min, symbol = 1;
    for (int i = 0; i < nums.length - 2; i++) {
        if (i == 0 || nums[i] != nums[i-1]) {
            int left = i+1, right = nums.length-1;
            while (left < right) {
                min = nums[left] + nums[right] + nums[i] - target;
                if (min == 0) return target;
                if (min > 0) {
                    if (min < closest) {
                        closest = min;
                        symbol = 1;
                    }
                    right--;
                } else {
                    if (-min < closest) {
                        closest = -min;
                        symbol = -1;
                    }
                    left++;
                }
            }
        }
    }
    return closest * symbol + target;
}
```

至此，对这个算法的优化算是告一段落。