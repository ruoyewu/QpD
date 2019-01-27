package best_time_to_buy_and_sell_stock;

/**
 * User: wuruoye
 * Date: 2019/1/27 12:15
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i+1; j < prices.length; j++) {
                max = Math.max(max, prices[j]-prices[i]);
            }
        }
        return max;
    }

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
}
