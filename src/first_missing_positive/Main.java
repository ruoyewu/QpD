package first_missing_positive;

/**
 * User: wuruoye
 * Date: 2019-03-26 20:52
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{1,1};
        System.out.println(firstMissingPositive(nums));
    }

    public static int firstMissingPositive(int[] nums) {
        int pos = 0;
        while (pos < nums.length) {
            int start = nums[pos++];
            if (start == pos) continue;
            nums[pos-1] = 0;
            while (start > 0 && start <= nums.length) {
                int next = nums[start-1];
                nums[start-1] = start;
                if (start == next) break;
                start = next;
            }
        }

        pos = 0;
        while (pos < nums.length && nums[pos] == pos+1) pos++;
        return pos+1;
    }

    public static int firstMissingPositive2(int[] nums) {
        int pos = 0;
        while (pos < nums.length) {
            int num = nums[pos];
            if (num < 1 || num > nums.length || num == pos+1) {
                ++pos;
            } else if (nums[pos] != nums[num-1]) {
                int tmp = nums[pos];
                nums[pos] = nums[num-1];
                nums[num-1] = tmp;
            } else {
                ++pos;
            }
        }

        pos = 0;
        while (pos < nums.length && nums[pos] == pos+1) pos++;
        return pos+1;
    }
}
