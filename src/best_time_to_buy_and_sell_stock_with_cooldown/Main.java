package best_time_to_buy_and_sell_stock_with_cooldown;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-02-04 17:37
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int maxProfit(int[] prices) {
        int[] saved = new int[prices.length];
        Arrays.fill(saved, -1);
        return max(0, 0, 0, prices, saved);
    }

    private static int max(int day, int lastState, int price, int[] prices, int[] saved) {
        // lastState : 0->idle, 1->buy, 2->sell, 3->cooldown
        if (day >= prices.length) return 0;
        if (saved[day] >= 0) return saved[day];
        int max;
        if (lastState == 0) {
            // skip
            int m1 = max(day+1, 0, 0, prices, saved);
            // buy
            int m2 = max(day+1, 1, prices[day], prices, saved);
            max = Math.max(m1, m2);
        } else {
            // skip
            int m1 = max(day+1, 1, price, prices, saved);
            // sell and skip cooldown
            int m2 = max(day+2, 0, 0, prices, saved) + prices[day] - price;
            max = Math.max(m1, m2);
        }
//        saved[day] = max;
        return max;
    }

    private static int maxProfit2(int[] prices) {
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
}
