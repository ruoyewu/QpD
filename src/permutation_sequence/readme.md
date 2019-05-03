### Question

The set `[1,2,3,...,*n*]` contains a total of *n*! unique permutations.

By listing and labeling all of the permutations in order, we get the following sequence for *n* = 3:

1.  `"123"`
2.  `"132"`
3.  `"213"`
4.  `"231"`
5.  `"312"`
6.  `"321"`

Given *n* and *k*, return the *k*th permutation sequence.

**Note:**

-   Given *n* will be between 1 and 9 inclusive.
-   Given *k* will be between 1 and *n*! inclusive.

**Example 1:**

```
Input: n = 3, k = 3
Output: "213"
```

**Example 2:**

```
Input: n = 4, k = 9
Output: "2314"
```

### Solution

在一个数字 n 的全排列的序列中，找到第 k 个排列，当然这个全排列的顺序是有规则的：

1.  每一位从较小的数开始取
2.  左边的数优先于右边选择

如对于 3 而言，首先第一位取 1 ，第二位最小取 2 ，第三位只能取 3 ，所以第一位结果是“123”，然后下一个，从右边开始数起，靠右边的先增大，得到下一个“132”，以此类推，总的结果为`[123,132,213,231,312,321]`。

那么这样一个序列有什么样的规律？如何判断某一个排列属于全排列的哪一位置，又如何根据位置求出这一排列的具体内容？

首先，对于一个 n 个数字的排列来说，有 n! 中排列情况，而题目中给出了数字的个数，和所求排列的位置，求这个排列的具体内容。排列必然包含 n 个数字，需要考虑的就是每个位置应该是什么数字，比如说第一个位置应该是哪个数字该怎么求？首先，它必然是`[1,...,n]`中的一个，其次，它的选择必然是从 1 开始，一直到 n ，当第一个位置的数字确定了之后，可以继续确定之后的 n-1 个数字的排列，而这 n-1 个数字的排列必然有 (n-1)! 个，也就是说，以 1 开头的排列数量一定有 (n-1)! 个，同理，以 2 开头的排列数量也是有 (n-1)! 个，所以就可以根据这个规律来确定第一个位置应该是哪一个数字。以题目要求的排列位置，除以这里的 (n-1)! ，所得结果，就是应该选择的数字所在的位置，比如例题 4、9，首先有 (4-1)! = 6，而 9/6 = 1 ，由此可以得到第一个数字是选择 2 的，完了之后剩余的是，数字`[1,3,4]`的排列，而所求的就是这三个数字排列中，处于位置 3（ 9/6 余为 3） 的排列。依旧按照上面的方法，再次求这一位置的数字即可，不过有一点需要注意，这时剩余的数字为`[1,3,4]`，它们并不是一个连续的序列，但是仍然需要按照位置依次选择，当这次的结果 3/2 = 1 时，要选择的应该是 3 而不是 2（2 已经被选过了），然后下一步就是从`[1,4]`组成的排列中选择位置为 1 的排列。

然而，题目所给的 k 是求这个位置的排列，也就是说，在这个位置之前有 k-1 个排列，按照上面的逻辑，实际求的并不是第 k 个排列，而是跳过了 k 个排列之后的第 k+1 个排列，所以在求解的时候要注意到这一点。

最终肯定能求出一个确定的序列。代码如下：

```java
public static String getPermutation(int n, int k) {
    if (n == 1) return "1";
    int[] map = new int[n];
    map[1] = map[0] = 1;
    for (int i = 2; i < n; i++) {
        map[i] = i * map[i-1];
    }
    boolean[] select = new boolean[n];
    k -= 1;
    char[] result = new char[n];
    int pos = 0;
    while (pos < n) {
        int base = map[n-pos-1];
        int skip = k / base;
        k = k % base;
        for (int i = 0; i < n; i++) {
            if (!select[i]) {
                if (skip == 0) {
                    select[i] = true;
                    result[pos++] = (char) (i+1+48);
                    break;
                } else {
                    skip--;
                }
            }
        }
    }
    return new String(result);
}
```

这种方法采取的是保存一个已选择数组，每次选择数字的时候在原数组的基础上跳过这些已选择的。

另外也可以使用另一种方法保证不重复选择数字，仍旧以 4、9 为例，如果直接计算，那么那么所得结果应该是`[2,2,1,1]`，这中间出现了重复，那么如何消除重复？梳理一下规律，对于长度为 n 的排列而言，如果在第一个位置选择了数字 i ，那么在第二个位置中，如果计算出的数字大于/等于 i ，那么需要在这个数字的基础上再加 1，如果是小于 i ，则前面的选择不会对当前的结果造成影响。所以，按照这样一个规律，就可以对求出来的`[2,2,1,1]`进行修改。

修改过程应该是倒着来的，首先判断倒数第二个选择数字 1，在此之后，所有的大于/等于这个数字 1 的值，都应该 +1 ，则得到结果`[2,2,1,2]`，然后是倒数第三个位置 2 ，同理得到结果`[2,2,1,3]`，最后判断第一个位置，同理得到`[2,3,1,4]`，这就是最终的解，没有重复，也刚好符合。代码如下：

```java
public static String getPermutation(int n, int k) {
    if (n == 1) return "1";
    int[] map = new int[n];
    map[1] = map[0] = 1;
    for (int i = 2; i < n; i++) {
        map[i] = i * map[i-1];
    }
    k -= 1;
    char[] result = new char[n];
    int pos = 0;
    while (pos < n) {
        int base = map[n-pos-1];
        result[pos] = (char) (k / base + 49);
        k %= base;
        pos++;
    }
    for (int i = n-2; i >= 0; --i) {
        for (int j = i+1; j < n; j++) {
            if (result[i] <= result[j]) {
                result[j]++;
            }
        }
    }
    return new String(result);
}
```

