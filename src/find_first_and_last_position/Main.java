package find_first_and_last_position;

/**
 * User: wuruoye
 * Date: 2019/1/14 19:15
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        searchRange2(nums, 7);
    }

    public static int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        int start = 0, end = nums.length - 1, pos = 0;
        while (start <= end) {
            pos = (start + end) / 2;
            if (nums[pos] == target) {
                break;
            }
            if (nums[pos] > target) {
                end = pos - 1;
            } else {
                start = pos + 1;
            }
        }

        if (nums[pos] != target) {
            return new int[]{-1, -1};
        }
        int i = 0;
        start = end = pos;
        while (true) {
            boolean change = false;
            if (pos - i >= 0 && nums[pos-i] == target) {
                start = pos-i;
                change = true;
            }
            if (pos + i < nums.length && nums[pos+i] == target) {
                end = pos+i;
                change = true;
            }
            if (!change) {
                break;
            }
            i++;
        }

        return new int[]{start, end};
    }

    /**
     * @param left: true, return the start
     *            false, return the end
     */
    public static int binarySearch(int[] nums, int target, boolean left) {
        int start = 0, end = nums.length - 1, mid = 0;
        while (start <= end) {
            mid = (start + end) / 2;
            if (left) {
                if (nums[mid] >= target) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if (nums[mid] <= target) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        if (left) {
            return nums[mid] == target ? mid : mid+1;
        } else {
            return nums[mid] == target ? mid : mid-1;
        }
    }

    public static int[] searchRange2(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }

        int left = binarySearch(nums, target, true);
        int right = binarySearch(nums, target, false);
        if (left > right) {
            return new int[]{-1, -1};
        }
        return new int[]{left, right};
    }
}
