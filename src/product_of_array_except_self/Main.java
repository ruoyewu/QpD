package product_of_array_except_self;

/**
 * User: wuruoye
 * Date: 2019-02-01 15:46
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        int mul = 1;
        int zero = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                mul *= nums[i];
            } else {
                if (zero >= 0) return result;
                zero = i;
            }
        }

        if (zero >= 0) {
            result[zero] = mul;
            return result;
        }
        for (int i = 0; i < nums.length; i++) {
            result[i] = mul / nums[i];
        }
        return result;
    }

    public static int[] productExceptSelf2(int[] nums) {
        int[] result = new int[nums.length];
        int mul = 1;
        for (int i = 0; i < nums.length; i++) {
            result[i] = mul;
            mul *= nums[i];
        }

        mul = 1;
        for (int i = nums.length-1; i >= 0; i--) {
            result[i] *= mul;
            mul *= nums[i];
        }
        return result;
    }
}
