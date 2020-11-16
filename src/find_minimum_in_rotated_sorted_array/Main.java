package find_minimum_in_rotated_sorted_array;

public class Main {
    public static void main(String[] args) {
        int[] nums = new int[] {2,2,2,0,1};
        System.out.println(new Main().findMin(nums));
    }

    public int findMin(int[] nums) {
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.min(nums[0], nums[1]);

        return findMin(nums, 0, nums.length - 1, Integer.MAX_VALUE);
    }

    private int findMin(int[] nums, int from, int to, int min) {
        if (from == to) return Math.min(nums[from], min);

        int mid = (from + to) / 2;
        if (nums[from] < nums[mid]) {
            return findMin(nums, mid + 1, to, Math.min(nums[from], min));
        } else if (nums[from] > nums[mid]) {
            return findMin(nums, from, mid, min);
        } else {
            return Math.min(findMin(nums, from, mid, Math.min(nums[from], min)), findMin(nums, mid + 1, to, Math.min(nums[from], min)));
        }
    }
}
