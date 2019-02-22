package coin_change;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-02-21 11:37
 * Description:
 */
public class Main {
    private static int result;

    public static void main(String[] args) {
        int[] coins = new int[]{1,2};
        int amount = 2;
        System.out.println(coinChange(coins, amount));
        System.out.println(coinChange4(coins, amount));
        System.out.println(coinChange5(coins, amount));
    }

    public static int coinChange(int[] coins, int amount) {
        int min = min(coins, amount, new int[amount+1]);
        if (min == Integer.MAX_VALUE) return -1;
        return min;
    }

    private static int min(int[] coins, int left, int[] saved) {
        if (left == 0) return 0;
        if (saved[left] != 0) return saved[left];
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (left >= coin) {
                min = Math.min(min, min(coins, left-coin, saved));
            }
        }
        if (min == Integer.MAX_VALUE) {
            saved[left] = min;
            return min;
        }else {
            saved[left] = min+1;
            return min+1;
        }
    }

    public static int coinChange2(int[] coins, int amount) {
        int max = amount + 1;
        int[] saved = new int[max];
        Arrays.fill(saved, Integer.MAX_VALUE);
        saved[0] = 0;
        for (int i = 1; i < max; i++) {
            for (int coin : coins) {
                if (i >= coin && saved[i-coin] < saved[i]) {
                    saved[i] = saved[i-coin] + 1;
                }
            }
        }
        return saved[amount] == Integer.MAX_VALUE ? -1 : saved[amount];
    }

    public static int coinChange3(int[] coins, int amount) {
        Arrays.sort(coins);
        return min(coins, coins.length-1, amount);
    }

    private static int min(int[] coins, int pos, int left) {
        int coin = coins[pos];
        if (pos == 0) {
            if (left % coin == 0) {
                return left / coin;
            }
        } else {
            int min = -1;
            for (int i = left/coin; i >= 0; i--) {
                int m = min(coins, pos-1, left - i*coin);
                if (m != -1 && (m < min-i || min == -1)) {
                    min = m+i;
                }
            }
            return min;
        }
        return -1;
    }

    public static int coinChange4(int[] coins, int amount) {
        result = Integer.MAX_VALUE;
        Arrays.sort(coins);
        min(coins, amount, coins.length-1, 0);
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private static void min(int[] coins, int left, int pos, int res) {
        int coin = coins[pos];
        if (pos == 0) {
            if (left % coin == 0) {
                result = Math.min(result, left/coin + res);
            }
        } else {
            for (int i = left / coin; i >= 0 && res + i < result; i--) {
                min(coins, left - i*coin, pos-1, res + i);
            }
        }
    }

    public static int coinChange5(int[] coins, int amount) {
        if (coins.length == 0) return -1;
        Arrays.sort(coins);
        return min(coins, amount, coins.length-1, new int[amount+1][coins.length]);
    }

    private static int min(int[] coins, int left, int pos, int[][] saved) {
        if (saved[left][pos] != 0) return saved[left][pos];
        int coin = coins[pos];
        if (pos == 0) {
            if (left % coin == 0) {
                return left / coin;
            }
        } else {
            int min = Integer.MAX_VALUE;
            for (int i = left/coin; i >= 0; i--) {
                int m = min(coins, left - i*coin, pos-1, saved);
                if (m >= 0 && m+i < min) {
                    min = m+i;
                }
            }
            saved[left][pos] = (min == Integer.MAX_VALUE ? -1 : min);
            return saved[left][pos];
        }
        return -1;
    }
}
