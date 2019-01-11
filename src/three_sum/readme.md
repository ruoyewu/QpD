### Question

Given an array `nums` of *n* integers, are there elements *a*, *b*, *c* in `nums` such that *a* + *b* + *c* = 0? Find all unique triplets in the array which gives the sum of zero.

**Note:**

The solution set must not contain duplicate triplets.

**Example:**

```
Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
```

### Solve

本题的解为，在一个数组中任选三个数，使得其和为 0 。所以最容易想到的就是三重循环遍历，但是这样会使时间复杂度上升到$O(n^3)$，显然不是一个优解。

涉及到数字和的问题，一般情况下会想到先对数组进行排序，因为数字和是一个定值 0 ，所以每组解必然是分布在 0 所在位置的两侧，这时解的正负情况有以下几种：

1.  3零
2.  1零1正1负
3.  2正1负
4.  2负1正

其中 3零 的为特殊情况，可单独判断，剩余的则是同时含有正负的，如果将数的正负情况划分为两种：负和非负，那么一般解就只有两种情况：

1.  2负1非负
2.  2非负1负

那么为使这三个数和为 0 ，那么必有其中两个相加等于另一个数的相反数，结合上述的两种解的情况，则有解：

1.  对于一个负数，有两个非负数相加等于这一负数的相反数
2.  对于一个非负数，有两个负数相加等于这一非负数相反数

所以本题的解题步骤为：

1.  对原数组排序
2.  找到第一个非负数所在的位置，将数组划分为负数和非负数两个部分
3.  遍历数组，对于每个负数，在非负数部分中找到两个数，反之亦然

Java 代码为：

```java
public static List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    HashSet<Integer> seen = new HashSet<>();    //去重辅助集合
    
    //找到第一个非负数
    int mid = 0;
    while (mid < nums.length && nums[mid] < 0) {
        mid++;
    }
    
    //特殊情况判断 3零
    if (mid+2 < nums.length && nums[mid] == 0) {
        if (nums[mid] == nums[mid+1] && nums[mid+1] == nums[mid+2] && nums[mid+2] == 0) {
            result.add(Arrays.asList(0, 0, 0));
        }
    }
    for (int i = 0; i < nums.length; i++) {
        int target, left, right;
        target = -nums[i];
        if (seen.contains(target)) continue;    //去重
        //区分负数部分和非负数部分
        if (i < mid) {
            left = mid;
            right = nums.length-1;
        } else {
            left = 0;
            right = mid-1;
        }
        for ( ; left < right ; ) {
            int res = nums[left] + nums[right];
            if (res == target) {
                result.add(Arrays.asList(-target, nums[left], nums[right]));
                while (nums[left] == nums[++left] && nums[right] == nums[--right] && left < right) ;    //去重
            } else if (res > target) {
                right--;
            } else {
                left++;
            }
        }
        seen.add(target);
    }
    return result;
}
```

