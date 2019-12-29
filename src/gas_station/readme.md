### Question

There are *N* gas stations along a circular route, where the amount of gas at station *i* is `gas[i]`.

You have a car with an unlimited gas tank and it costs `cost[i]` of gas to travel from station *i* to its next station (*i*+1). You begin the journey with an empty tank at one of the gas stations.

Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.

**Note:**

- If there exists a solution, it is guaranteed to be unique.
- Both input arrays are non-empty and have the same length.
- Each element in the input arrays is a non-negative integer.

**Example 1:**

```
Input: 
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]

Output: 3

Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
```

**Example 2:**

```
Input: 
gas  = [2,3,4]
cost = [3,4,3]

Output: -1

Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.
```

### Solution

题目给出了两个数组，二者各位置相对应，gas 数组表示从当前位置出发前能增加多少的油量，cost 数组表示从当前位置出发到下一位置需要消耗多少的有量，数组可以看作是一个头尾相连的环，题目的要求是找出一个可以作为起始点的位置，并且能够走完整个环路回到起始点。

那么一种方法就是，遍历每一个位置，判断这个位置是否可以作为起始点，如果在环路中走到某个位置后没有足够的油量出发到下一位置，就表示这个起始点不满足要求，再去判断下一个点即可：

```java
public static int canCompleteCircuit(int[] gas, int[] cost) {
    int len = gas.length;
    int left;
    for (int i = 0; i < len; i++) {
        left = 0;
        for (int j = 0; j < len; j++) {
            int pos = (i + j) % len;
            left += gas[pos] - cost[pos];
            if (left < 0) {
                break;
            }
            if (j == len-1) {
                return i;
            }
        }
    }
    return -1;
}
```

此时时间复杂度为$O(n^2)$，空间复杂度为$O(1)$。

但是上述解答凸显出一个问题，每一个检测过程都是独立的，互不影响，这就导致每一个起始点都需要进行一个完整的判断。但是它们实际上处于同一个环路内，且都是逐步向下一个位置移动，所以针对本题的优化，就在于将各起始点的判断联系到一起，使一个起始点能够利用上一个起始点或前几个起始点判断之后得到的结果，这样一来就必然能够减少判断一个起始点消耗的时间。

那么再看题目，题中提到每一题最多只有一个结果，即除了某一个满足条件的起始点之外，其他所有点都不满足。首先可以确定的是，如果一个起始点 i 满足条件，那么必然有 gas[i] >= cost[i] ，这样车才能在这个点起步，然后走到下一个点 i+1，如果有 left(i,i) + gas[i+1] >= cost[i+1] ，（left(i,j) 表示从 位置 i 作为起始点，从位置 j 出发之后的剩余油量）那么车也可以从这个位置出发，直到走到了位置 j 之后，出现了 left + gas[j] < cost[j] 的情况，意味着此时车不能再从位置 j 出发了，起始点 i 不满足条件，那么下一个应该判断的起始点应该是哪里？在上一种解答中，下一个待判断的起始点是 i+1 ，但是在此时，下一个应该判断的起始点应该是 j+1 ，为什么？下面将说明为什么从 i+1 到 j 都是不满足条件的起始点。

先看 i+1 ，由于车可以从位置 i 开到 位置 i+1 ，也就是 left(i,i) >= 0，那么如果位置 i+1 是满足条件的起始点，那么必然有 left(i+1, j) >= 0，那么有 left(i,j) = left(i,i+1) + left(i+1, j) >= 0，但是经过判断 left(i,j) < 0，所以同理得到 left(i+1, j) < 0， 所以 i+1 不是满足条件的起始点，同理，从 i+1 到 j 中的任何一点都不满足条件。

所以，下一个待判断的起始点是 j+1 ，按照同样的判断方法，一直判断到最后一个位置 n-1 ，如果判断到 n-1 的时候还有 left(j+1, n-1) >= 0, 那么就意味着起始点 j+1 是一种可能解，然后接下来还要判断从这个起始点开始是否能走一个环路，即判断是否满足 left(j+1, n-1) + left(0, j) >= 0 是否成立。如果成立，那么 j+1 确定就是一个满足条件的起始点，而如果不满足，则就表示题目给定的条件没有满足条件的解。j+2 到 n-1 中的所有起始点是否都不满足条件？同上面 i+1 到 j 都不满足的说明，不再赘述。

所以，代码为：

```java
int len = gas.length, left = 0, result = -1;
boolean isOk = false;
for (int i = 0; i < len; i++) {
    gas[i] -= cost[i];
    if (gas[i] >= 0 && left == 0) {
        isOk = true;
        result = i;
    }
    left += gas[i];
    if (left < 0) {
        left = 0;
        isOk = false;
    }
}
if (!isOk) {
    return -1;
} else {
    for (int i = 0; i < result; i++) {
        left += gas[i];
        if (left < 0) {
            return -1;
        }
    }
}
return result;
```

此时代码的时间复杂度为$O(n)$，空间复杂度为$O(1)$。