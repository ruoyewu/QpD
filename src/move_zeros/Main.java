package move_zeros;

/**
 * User: wuruoye
 * Date: 2019-02-02 16:24
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static void moveZeros(int[] nums) {
        int pos = 0, i = 0;
        while (pos < nums.length && nums[pos] != 0) {
            pos++;
            i++;
        }
        for (; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[pos++] = nums[i];
                nums[i] = 0;
            }
        }
    }

    public static void moveZeros2(int[] nums) {
        int[] tmp = new int[nums.length];
        System.arraycopy(nums, 0, tmp, 0, nums.length);

        int pos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (tmp[i] != 0) {
                nums[pos++] = nums[i];
            }
        }

        for (int i = pos; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
