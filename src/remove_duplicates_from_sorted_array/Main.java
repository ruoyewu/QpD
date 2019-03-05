package remove_duplicates_from_sorted_array;

/**
 * User: wuruoye
 * Date: 2019-03-05 21:14
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return 1;
        int pos = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i-1]) {
                nums[pos++] = nums[i];
            }
        }
        return pos;
    }
}
