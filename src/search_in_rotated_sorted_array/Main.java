package search_in_rotated_sorted_array;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2019/1/14 16:24
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n != -1) {
            System.out.println("input array list: ");
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = scanner.nextInt();
            }
            int target = scanner.nextInt();
            while (target != -1) {
                System.out.println(search(nums, target));
                System.out.println("input search target: ");
                target = scanner.nextInt();
            }

            System.out.println("input array length: ");
            n = scanner.nextInt();
        }
    }

    public static int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int start = 0, end = nums.length-1, mid, m;
        int first = nums[0];
        boolean left = target >= first;
        while (start < nums.length && end > -1 && start <= end) {
            mid = (start + end) / 2;
            m = nums[mid];
            if (m == target) {
                return mid;
            }
            if (m >= first) {
                if (m > target) {
                    if (left) {
                        end = mid - 1;
                    } else {
                        start = mid + 1;
                    }
                } else {
                    start = mid + 1;
                }
            } else {
                if (m > target) {
                    end = mid - 1;
                } else {
                    if (left) {
                        end = mid - 1;
                    } else {
                        start = mid + 1;
                    }
                }
            }
        }
        return -1;
    }


}
