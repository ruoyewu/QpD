package remove_duplicates_from_sorted_array_2;

/**
 * User: wuruoye
 * Date: 2019-05-04 17:18
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int removeDuplicates(int[] nums) {
        if (nums.length < 3) return nums.length;

        int last = nums[0], count = 1, pos = 1;
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (num == last) {
                if (count < 2) {
                    nums[pos++] = num;
                    count++;
                }
            } else {
                nums[pos++] = num;
                count = 1;
                last = num;
            }
        }

        return pos;
    }

    public static int removeDuplicates2(int[] nums) {
        if (nums.length < 3) return nums.length;

        int p = nums[1], pp = nums[0], pos = 2;
        for (int i = 2; i < nums.length; i++) {
            int num = nums[i];
            if (num != p || num != pp) {
                nums[pos++] = num;
                pp = p;
                p = num;
            }
        }

        return pos;
    }

    public static int removeDuplicates3(int[] nums) {
        if (nums.length < 3) return nums.length;

        int pos = 2;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] != nums[pos-2]) {
                nums[pos++] = nums[i];
            }
        }

        return pos;
    }
}
