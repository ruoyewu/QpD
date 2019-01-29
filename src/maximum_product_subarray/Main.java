package maximum_product_subarray;

/**
 * User: wuruoye
 * Date: 2019/1/28 18:10
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(maxProduct2(new int[]{1,2,-1,-2,2,1,-2,1}));
    }

    public static int maxProduct(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int product = 1;
            for (int j = i; j < nums.length; j++) {
                product *= nums[j];
                max = Math.max(max, product);
            }
        }
        return max;
    }

    public static int maxProduct2(int[] nums) {
        if (nums.length == 0) return 0;
        int[] max = new int[nums.length];
        max[0] = nums[0];
        int maxProduct = nums[0];
        int lastNeg = nums[0] < 0 ? 0 : -1;

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (num > 0) {
                max[i] = num;
                if (max[i-1] > 0) {
                    max[i] *= max[i-1];
                }
            } else if (num < 0) {
                max[i] = num;
                if (lastNeg >= 0) {
                    int j = i-1;
                    while (j >= lastNeg) {
                        max[i] *= nums[j--];
                    }
                    if (lastNeg-1 >= 0 && max[lastNeg-1] > 0) {
                        max[i] *= max[lastNeg-1];
                    }
                }
                lastNeg = i;
            } else {
                max[i] = 0;
            }
            maxProduct = Math.max(maxProduct, max[i]);
        }

        return maxProduct;
    }

    public static int maxProduct3(int[] nums) {
        int cur = 1, max = nums[0];

        for (int i = 0; i < nums.length; i++) {
            cur *= nums[i];
            if (cur > max) {
                max = cur;
            }
            if (nums[i] == 0) {
                cur = 1;
            }
        }

        cur = 1;
        for (int i= nums.length-1; i >= 0; i--) {
            cur *= nums[i];
            if (cur > max) {
                max = cur;
            }
            if (nums[i] == 0) {
                cur = 1;
            }
        }

        return max;
    }

    public static int maxProduct4(int[] nums) {
        if (nums.length == 0) return 0;
        int max = nums[0], min = nums[0], result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int tmpMax = max;
            max = Math.max(max * nums[i], Math.max(min * nums[i], nums[i]));
            min = Math.min(tmpMax * nums[i], Math.min(min * nums[i], nums[i]));
            if (max > result) {
                result = max;
            }
        }

        return result;
    }
}
