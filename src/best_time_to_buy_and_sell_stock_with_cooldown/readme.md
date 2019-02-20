### Question

Say you have an array for which the *i*th element is the price of a given stock on day *i*.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

-   You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
-   After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)

**Example:**

```
Input: [1,2,3,0,2]
Output: 3 
Explanation: transactions = [buy, sell, cooldown, buy, sell]
```

### Solution

这是一个数组类问题，首先考虑的是动态规划是否能解决问题。本题有三个动作：buy、sell 和 cooldown，然后看一下条件：

1.  sell 需要发生在 buy 后
2.  sell 后需要跟一个 cooldown

按理来说，这三个动作是可以发生在整个数组的任何一个位置的，不过还有一下条件：

1.  buy

    因为 buy 与 sell 需要交替出现，那么当某一个位置可以选择 buy 动作的时候，在它的前面出现的应该是 sell ，并且他的上一个位置不能是 sell（条件2）

2.  sell

    在他的前面出现的应该是 buy

3.  cooldown

    没有条件

本题要求的是一连串买卖之后的利润，那么如果假设一个人最开始拥有的钱是 0 （可以为负），那么最终这个人手里的钱就是他的利润。如果使用动态规划的话，需要声明一个数组，这个数组位置 i 的值，就是到 i 为止，这个人手中的钱，也就是他的利润。但是在本题中，某一个位置需要判断的有两种情况，buy 或者 sell （cooldown 可看作是无动作，直接继承前一位置即可）。并且因为 sell 与 buy 是交替出现的，也就是说，当前位置如果是 buy ，那么需要前面某个位置动作为 sell 的结果作为依赖，相反，如果当前位置选择 sell 动作，需要前面某个位置动作为 buy 的结果作为依赖。如此一来，每个位置需要两个数，分别保存当前位置为 buy 和 sell 的结果。也就是说整个解题过程需要两个数组保存这些结果。

那么，便有如下公式：

```java
buy[i] = max(buy[i-1], sell[i-2] - prices[i]);
sell[i] = max(sell[i-1], buy[i-1] + prices[i]);
```

或者将其分开来看，对于位置为 i 的解：

1.  如果这一位置选择 buy

    需要在上一个 sell 的基础上执行 buy 动作，又因为条件 1 ，需要再隔一个位置：`buy[i] = sell[i-2] - prices[i]`

2.  如果这一位置选择 sell

    需要在上一个 buy 的基础上执行 sell 动作，则`sell[i] = buy[i-1] + prices[i]`

3.  如果选择 cooldown

    直接继承上一位置解，即`buy[i] = buy[i-1]`, `sell[i] = sell[i-1]`

为了求最优解，buy 和 sell 两个数组都要保存这三种情况中数值最大的解（即手中的钱最多，也就是利润最多）。所以，将其整理为代码如下：

```java
private static int maxProfit(int[] prices) {
    if (prices == null || prices.length <= 1) return 0;
    int[] buy = new int[prices.length];
    int[] sell = new int[prices.length];
    buy[0] = -prices[0];
    sell[0] = 0;
    for (int i = 1; i < prices.length; i++) {
        buy[i] = Math.max(buy[i-1], (i > 1 ? sell[i - 2] : 0) - prices[i]);
        sell[i] = Math.max(sell[i-1], buy[i-1] + prices[i]);
    }
    return sell[prices.length-1];
}
```

另外，因为只有完成了完整 buy-sell 动作，才能算作是利润，所以，最终代表利润的应该是 sell 数组的最后一位。