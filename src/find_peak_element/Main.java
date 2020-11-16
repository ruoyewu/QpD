package find_peak_element;

public class Main {
    public static void main(String[] args) {
        int[] nums = new int[] {1, 2};
        System.out.println(new Main().findPeakElement(nums));
    }

    public int findPeakElement(int[] nums) {
        int lLast = Integer.MIN_VALUE;
        int last = nums[0];

        for (int i = 1; i < nums.length + 1; i++) {
            int cur = i < nums.length ? nums[i] : Integer.MIN_VALUE;
            if (lLast < last && last > cur) {
                return i - 1;
            } else {
                lLast = last;
                last = cur;
            }
        }
        return 0;
    }
}
