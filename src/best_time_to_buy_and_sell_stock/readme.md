### Question

Say you have an array for which the *i*th element is the price of a given stock on day *i*.

If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.

Note that you cannot sell a stock before you buy one.

**Example 1:**

```
Input: [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
             Not 7-1 = 6, as selling price needs to be larger than buying price.
```

**Example 2:**

```
Input: [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.
```

### Solution

#### S1:二重遍历

要求最大的差值，那么将所有的可能的差值进行比较，选择最大的那个即可。所以可以采用二重遍历：

```java
public static int maxProfit(int[] prices) {
    int max = 0;
    for (int i = 0; i < prices.length; i++) {
        for (int j = i+1; j < prices.length; j++) {
            max = Math.max(max, prices[j]-prices[i]);
        }
    }
    return max;
}
```

此时时间复杂度为$O(n^2)$。

#### S2:动态规划

换种思考方法，如果要使得在某一天卖出的利润最大，那么购买的那天一定是在这一天之前价格最低的时候，所以可以得出，某天卖出的最大收益与这一天之前的价格有关，而这些天中受益最大的一天就是本题的解。所以可以采用动态规划的方法：

```java
public static int maxProfit2(int[] prices) {
    if (prices.length <= 1) return 0;
    int max = 0;
    int min = prices[0];
    for (int i = 1; i < prices.length; i++) {
        int price = prices[i];
        if (price <= min) {
            min = price;
        } else {
            max = Math.max(max, price-min);
        }
    }
    return max;
}
```

用一个变量保存这一天之前的最小价格，然后利用每天的卖出价格减去这一天之前的最小价格就是最高收益，一次遍历即可求解。