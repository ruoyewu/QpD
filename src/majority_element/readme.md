### Question

Given an array of size *n*, find the majority element. The majority element is the element that appears **more than** `⌊ n/2 ⌋` times.

You may assume that the array is non-empty and the majority element always exist in the array.

**Example 1:**

```
Input: [3,2,3]
Output: 3
```

**Example 2:**

```
Input: [2,2,1,1,1,2,2]
Output: 2
```

### Solution

#### S1:先保存再选择

要求一个出现次数大于一半的数，可以先将整个数组中所有数字出现的次数记录下来，然后再求出这些数中出现次数最大的即可。

```java
public static int majorityElement(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        Integer num = map.get(nums[i]);
        map.put(nums[i], num == null ? 1 : num+1);
    }
    int major = 0, num = Integer.MIN_VALUE;
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        if (entry.getValue() > num) {
            num = entry.getValue();
            major = entry.getKey();
        }
    }
    return major;
}
```

#### S2:增减法

```java
public static int majorityElement(int[] nums)
    int majority = nums[0];
    int cnt = 1;
    for (int i = 1; i < nums.length; i++) {
        int num = nums[i];
        if (majority == num) {
            cnt++;
        } else {
            cnt--;
        }
        if (cnt < 0) {
            cnt = 1;
            majority = num;
        }
    }
    return majority;
}
```

#### S3:排序法

将数组排序，此时同一数字位于相邻位置，直接遍历即可：

```java
public static int majorityElement3(int[] nums) {
    Arrays.sort(nums);
    int major = nums[0], len = 1;
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] == major) {
            len++;
        }else {
            if (len > nums.length/2) {
                return major;
            } else {
                major = nums[i];
                len = 1;
            }
        }
    }
    return major;
}
```

#### S4:二分法

将数组不断划分为小数组，求出解之后再整合：

```java
public static int majorityElement4(int[] nums) {
    return majorityElementRange(nums, 0, nums.length-1);
}
private static int majorityElementRange(int[] nums, int start, int end) {
    if (start == end) {
        return nums[start];
    }
    int mid = (start + end) / 2;
    int left = majorityElementRange(nums, start, mid);
    int right = majorityElementRange(nums, mid+1, end);
    if (left == right) {
        return left;
    }
    return countElement(nums, start, mid, left) >
            countElement(nums, mid+1, end, right) ? left : right;
}
private static int countElement(int[] nums, int start, int end, int num) {
    int cnt = 0;
    for (int i = start; i <= end; i++) {
        if (nums[i] == num) {
            cnt++;
        }
    }
    return cnt;
}
```

