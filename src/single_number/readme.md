### Question

Given a **non-empty** array of integers, every element appears *twice* except for one. Find that single one.

**Note:**

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

**Example 1:**

```
Input: [2,2,1]
Output: 1
```

**Example 2:**

```
Input: [4,1,2,1,2]
Output: 4
```

### Solution

#### S1:排序法

要求出一个数组中未重复的那一个数字，可以先对这个数组进行排序，那么所有的重复的数字必然是连续的，所以，只要找到第一个落单的数字，它必然就是解。

```java
public static int singleNumber(int[] nums) {
    Arrays.sort(nums);
    for (int i = 1; i < nums.length; i += 2) {
        if (nums[i] != nums[i-1]) {
            return nums[i-1];
        }
    }
    return nums[nums.length-1];
}
```

#### S2:去重法

看下列等式：

$2 * (a + b + c) - (a + b + c + a + b) = c$，其中 c 便是这个未曾重复的数字，故而，只要能够求出该数组的一个无重复解的新的数组，然后利用上述关系式，便可以求出 c 。可以使用哈希表去重：

```java
public static int singleNumber(int[] nums) {
    Set<Integer> set = new HashSet<>();
    int sum = 0;
    for (int n : nums) {
        set.add(n);
        sum += n;
    }
    for (Integer i : set) {
        sum -= i * 2;
    }
    return -sum;
}
```

还有一种去重是，遍历整个数组，如果之前未遇到过某个数，将其暂时保存起来；如果之前遇到过这个数，则删除之前保存的。那么，遍历完一遍之后，必然有一个数字剩余下来，这个数就是本题的解。

```java
public static int singleNumber(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
        if (set.contains(num)) {
            set.remove(num);
        } else {
            set.add(num);
        }
    }
    return set.iterator().next();
}
```



#### S3:异或求解

看如下公式：

$a ⊕ b ⊕ a = (a ⊕ a) ⊕ b = 0 ⊕ b = b$，即异或操作满足交换律，那么，如果将整个数组进行异或的话，因为交换律，必然有$n1 ⊕ n2 ⊕ ... ⊕ nn = (n1 ⊕ ni) ⊕ (n2 ⊕ nj) ⊕ ... ⊕ nn = nn$，其中 nn 为非重复数字，所以，只需要一次遍历，将所有的数字异或运算即可。

```java
public static int singleNumber(int[] nums) {
    int result = 0;
    for (int n : nums) {
        result ^= n;
    }
    return result;
}
```

