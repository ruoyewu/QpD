### Question

Given an unsorted integer array, find the smallest missing positive integer.

**Example 1:**

```
Input: [1,2,0]
Output: 3
```

**Example 2:**

```
Input: [3,4,-1,1]
Output: 2
```

**Example 3:**

```
Input: [7,8,9,11,12]
Output: 1
```

**Note:**

Your algorithm should run in *O*(*n*) time and uses constant extra space.

### Solution

对于一个无序的数组，求在这个数组里不存在的最小的自然数（不含0）。这种问题当然要使用排序来解决了，一次排序加一次遍历，轻轻松松解决问题，但是本题有要求，时间复杂度$O(n)$，空间复杂度$O(1)$，不仅排序，这下连额外空间都不能用，那么该使用何种方式解本题？

如果没有上面的那种条件，一般有两种解法：

1.  排序，此时需要的时间为$O(n\log_n)$，不需要额外空间
2.  使用额外空间，定义一个状态数组（状态数组位置 i 处的值 state[i] 保存 i+1 这个数字是否出现过），遍历原数组，将出现过的数字都保存到状态数组中，此时空间需要为$O(n)$，时间为$O(n)$

但是毫无疑问，上面的方法都不行。是否可以将上面的方法进行改进，使之空间、时间复杂度降到可接受程度？对于第一种排序的方法，一种优化是使用小根堆求解，每次使用$\log_n$的时间得到一个最小值，但是这种方法的最差情况还是$O(n\log_n)$，不可行。对于第二种方法，也有一种常用的解决方法：使用数组本身保存状态信息。但是这种方法的前提是，能够判断出哪些位置的数据可以被覆盖，因为一旦某个位置被覆盖了之后，这个数据就会丢失。

本题有一个比较隐晦的提示，那就是这第一个 missing 的数字，一定是不大于数组的长度 +1 的，因为要从 1 开始数。这就意味着，在长度为 n 的数组中，所有大于 n+1 的数字都是无效数据，第一个丢失的数字一定是小于这个数字的。同样的，小于 1 的数字因为没有它们的位置，也是需要忽略的。而剩下的有用的数字，必然是能够在数组中占一个位置的。例如数组`[3,4,-1,1]`，按照上面的方法过滤一遍，得出有用数组`[3,4,1,-]`，无用数字 -1 。

既然所有的有用数字都能够在数组中占有自己的位置，那么是否能够将这些数字都放回到自己的位置上？如将上面的数组再变成`[1,-,3,4]`，这样一来，first missing 的数字不就是显而易见了，再利用一个遍历便可以轻轻松松找出来。如何能够将数字放在它应该在的位置？这个简单，直接通过数字的大小索引到对应的位置，如对于数字 3 来说，它应该在数组中的 2 位置。直接将 3 赋值给`nums[2]`即可。但是这又有了一个问题，原本`nums[2]`有自己的值，直接赋值会造成数据丢失，万万不能。这该如何解决？再定义一个变量`next`保存这个位置的值不就好了，然后呢？下一步应该找下一个数字应该在的位置，以数组`[3,4,1,-1]`为例，第一个找到了 3 的位置，同时保存了`nums[2]`中的数值 1 ，按理说下一步应该找 4 所在的位置，并将`nums[3]`原本的数值保存起来，这样是否可以？不可以，这样还需要再定义一个变量保存`nums[3]`处原本的数值，依此下去，空间复杂度就会变成$O(n)$。还有一种方法，下一步去找 next 的位置，将其存放到`nums[next-1]`处，并将这个位置原来的值再保存到 next 里面，直到 next 的值变成无效值。此时再转回头找下一个数字 4 的位置，并将`nums[3]`保存到 next 里，这样就可以完成只需要一个变量完成所有有效数字位置的移动了。

代码如下：

```java
public static int firstMissingPositive(int[] nums) {
    int pos = 0;
    while (pos < nums.length) {
        int start = nums[pos++];
        if (start == pos) continue;
        nums[pos-1] = 0;
        while (start > 0 && start <= nums.length) {
            int next = nums[start-1];
            nums[start-1] = start;
            if (start == next) break;
            start = next;
        }
    }
    
    pos = 0;
    while (pos < nums.length && nums[pos] == pos+1) pos++;
    return pos+1;
}
```

上面第一重循环里面，找出一个不在自己应该在的位置的数字`(start != pos)`，然后就去找它的正确位置，并再利用一个 while 循环找出所有 next 的正确位置。当所有数字都移到自己的位置后，再通过一个遍历，找到 first missing 。第二个循环中，`if(start == next) break;`语句判断了重复的情况，如例`[1,1]`，第一个位置正常会跳过，判断第二个位置，这里是个 1 ，那么需要将它放到`nums[0]`，同时保存`nums[0]`处当前的值 next=1 ，然后再寻找 next 的位置，此时如果不加判断就会出现死循环。

但是这个算法中，寻找位置使用到了两个 while 循环，此时的时间复杂度是多少？到底是$O(n)$还是$O(n^2)$？从理论上分析，在第二个循环中，每一次遍历都会找到一个数字的正确位置，所以，第二个循环最多执行 n 次，同理，第一个循环中，每次遍历都会找到一个非正确位置的数字，所以，所有在第二个循环中已经找到正确位置的数字，都会通过`if(start == pos) continues;`语句执行下一次遍历。由此可以得出，两个循环加起来的最多执行次数是 2n ，满足时间复杂度$O(n)$。同时，只使用到了两个变量，空间复杂度也满足$O(1)$。