### Question

Given an array `nums` of *n* integers and an integer `target`, are there elements *a*, *b*, *c*, and *d* in `nums`such that *a* + *b* + *c* + *d* = `target`? Find all unique quadruplets in the array which gives the sum of `target`.

**Note:**

The solution set must not contain duplicate quadruplets.

**Example:**

```
Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.

A solution set is:
[
  [-1,  0, 0, 1],
  [-2, -1, 1, 2],
  [-2,  0, 0, 2]
]
```

### Solution

**优化之路**

与 three sum 一题类似的求解。但是对于一个有序数组而言，最多一次只能计算两个数字和，所以，这一题需要三层循环，第一层决定第一个数，第二层决定第二个数，第三层求出剩余两个数，如下：

```java
public List<List<Integer>> fourSum(int[] nums, int target) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < nums.length - 3; i++) {
        if (i > 0 && nums[i] == nums[i-1]) continue;
        for (int j = i + 1; j < nums.length - 2; j++) {
            if (j > i+1 && nums[j] == nums[j-1]) continue;
            int left = j + 1, right = nums.length-1;
            while (left < right) {
                int sum = nums[i] + nums[j] + nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                    while (left < right && nums[right--] == nums[right]) ;
                    while (left < right && nums[left++] == nums[left]) ;
                } else if (sum > target) {
                    while (left < right && nums[right--] == nums[right]) ;
                } else {
                    while (left < right && nums[left++] == nums[left]) ;
                }
            }
        }
    }
    return result;
}
```

这是一个可以优化的算法，优化的原理如下：

假设已经选择了四个数中最小的数 i ，那么：

1.  如果剩余的数组中可选的最小的三个数之和还要大于 target ，那么无解
2.  如果剩余数组中可选的最大的三个数之和还要小于 target ，同样无解

对于选择了第二个小的数 j 之后，也是同样。故而有：

```java
public static List<List<Integer>> fourSum(int[] nums, int target) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    if (nums.length < 4) return result;
    for (int i = 0; i < nums.length - 3; i++) {
        if (nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target) break;
        if (nums[i] + nums[nums.length-1] + nums[nums.length-2]
                + nums[nums.length-3] < target) continue;
        if (i > 0 && nums[i] == nums[i-1]) continue;
        for (int j = i + 1; j < nums.length - 2; j++) {
            if (nums[i] + nums[j] + nums[j+1] + nums[j+2] > target) break;
            if (nums[i] + nums[j] + nums[nums.length-1]
                    + nums[nums.length-2] < target) continue;
            if (j > i+1 && nums[j] == nums[j-1]) continue;
            int left = j + 1, right = nums.length-1;
            while (left < right) {
                int sum = nums[i] + nums[j] + nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                    while (left < right && nums[right--] == nums[right]) ;
                    while (left < right && nums[left++] == nums[left]) ;
                } else if (sum > target) {
                    while (left < right && nums[right--] == nums[right]) ;
                } else {
                    while (left < right && nums[left++] == nums[left]) ;
                }
            }
        }
    }
    return result;
}
```

