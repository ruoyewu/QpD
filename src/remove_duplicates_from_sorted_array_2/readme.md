### Question

Given a sorted array *nums*, remove the duplicates [**in-place**](https://en.wikipedia.org/wiki/In-place_algorithm) such that duplicates appeared at most *twice* and return the new length.

Do not allocate extra space for another array, you must do this by **modifying the input array in-place** with O(1) extra memory.

**Example 1:**

```
Given nums = [1,1,1,2,2,3],

Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.

It doesn't matter what you leave beyond the returned length.
```

**Example 2:**

```
Given nums = [0,0,1,1,1,1,2,3,3],

Your function should return length = 7, with the first seven elements of nums being modified to 0, 0, 1, 1, 2, 3 and 3 respectively.

It doesn't matter what values are set beyond the returned length.
```

**Clarification:**

Confused why the returned value is an integer but your answer is an array?

Note that the input array is passed in by **reference**, which means modification to the input array will be known to the caller as well.

Internally you can think of this:

```
// nums is passed in by reference. (i.e., without making a copy)
int len = removeDuplicates(nums);

// any modification to nums in your function would be known by the caller.
// using the length returned by your function, it prints the first len elements.
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
```

### Solution

是 remove duplicates from sorted array 一题的扩展版本，要求时可以出现连续的重复，但是重复次数不能超过 2 ，原则上来说还是一样的解法，如果将数组分成逻辑上的新数组和原数组，那么只要保持在新数组中添加的数不能与前两个重复即可，所以可以保存新数组的最后两位数，然后遍历原数组，逐个判断，并将满足条件的加入新数组。

```java
public static int removeDuplicates(int[] nums) {
    if (nums.length < 3) return nums.length;
    int p = nums[1], pp = nums[0], pos = 2;
    for (int i = 2; i < nums.length; i++) {
        int num = nums[i];
        if (num != p || num != pp) {
            nums[pos++] = num;
            pp = p;
            p = num;
        }
    }
    return pos;
}
```

按照上面的描述，代码写出来就是上面这个样子。但是，是否上面的 p 和 pp 都是有必要的？判断条件`if(num != p || num != pp)`是否存在拖沓？题目已经给出，这是一个有序数组，那么就意味着，相同的数字必然在一起，于是有，如果`num != p`满足，必然有`num != pp`满足，因为三者之间的顺序为`[pp, p, ..., num]`，所以上面的 if 条件，其实只需要保留后面的判断就可以了，二者是一样的效果。如下：

```java
public static int removeDuplicates(int[] nums) {
    if (nums.length < 3) return nums.length;
    int pos = 2;
    for (int i = 2; i < nums.length; i++) {
        if (nums[i] != nums[pos-2]) {
            nums[pos++] = nums[i];
        }
    }
    return pos;
}
```

此时就没有了额外变量的限制，如果再出一个题目，要求连续的重复不能超过 100 ？按照第一种想法岂不是要创建额外 100 个变量。从这个问题的出来的一点思考是，上面这两种方法的变化，其实就是我们经常写代码的过程。首先，根据人的惯性思维，先将算法实现出来，当这种算法写出来的时候，如果有另一个人来看，也很容易就能够进入代码编写者的思维中去，正是因为代码中包含着“人性思维”，这种特点虽然方便理解，但是在程序执行的时候，可能会因为执行过多不必要的步骤而影响效率。此时，程序员的工作是，抛开第一思维的束缚，考虑到一些可优化的方面，如整合重复代码、跳过无用步骤、增加缓存等方式，使得程序执行起来更加高效。